package com.dianping.service.executer;

import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import com.dianping.tools.ProcessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ETLExecute {

    private int concurrency;

    private String database_src;

    private static int wormhole_max_num;

    private static final Logger logger = LoggerFactory.getLogger(ETLExecute.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public static void setWormhole_max_num(int wormhole_max_num) {
        ETLExecute.wormhole_max_num = wormhole_max_num;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public void setDatabase_src(String database_src) {
        this.database_src = database_src;
    }

    public void execute() {
        TaskStatus ts = null;
        try {
            logger.info("the " + this.database_src + " executer process starts");
//            int total_num = this.getRunningExecuteNum();
//            if (total_num >= this.wormhole_max_num) {
//                logger.info("the all execute num is " + wormhole_max_num + " the etl process exits");
//                return;
//            }
            int runningNum = this.getRunningJobNum();
            if (runningNum >= this.concurrency) {
                logger.info("the " + this.database_src + " process runnning number :=" + runningNum + " the process exits");
                return;
            }
            ts = this.getReadyTask();

            if (ts == null) {
                return;
            } else {
                StandardExecute.runningQueue.put(ts.getTask_status_id(), ts);
                logger.info(" Running Queue already " + StandardExecute.runningQueue.size() + " tasks");
                logger.info(ts.getTask_status_id() + "(" + ts.getTask_name() + " join to Running Queue");
                Object[] rtn = this.executeTask(ts);
                this.recordLog(ts, rtn);
                this.sendEmail(ts);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(ts != null){
                StandardExecute.runningQueue.remove(ts.getTask_status_id());
            }
            logger.info("the " + this.database_src + " executer process ends");
        }
    }

    private int getRunningExecuteNum() {
        String sql = "select count(1) as num From etl_task_status where status = ? and type =1";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{SystemConstant.JOB_RUNNING});
    }

    private int getRunningJobNum() throws Exception {
        String sql = "select count(1) as num from etl_task_status where database_src=? and status =?";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{this.database_src, SystemConstant.JOB_RUNNING});
    }

    private TaskStatus getReadyTask() throws Exception {
        String sql1 = "select task_status_id,task_name,trigger_time,task_obj,para1,para2,para3,log_path,task_id,cycle,time_id,type,if_pre,if_wait,success_code,wait_code from etl_task_status a " +
                "where status =? and database_src=? order by running_prio desc,prio_lvl,run_num,rand() ";

        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1, new Object[]{SystemConstant.JOB_READY, this.database_src});
        if (list.size() == 0) {
            return null;
        }

        TaskStatus ts = null;
        for (Map<String, Object> map : list) {
            ts = new TaskStatus();
            ts.setTask_status_id((String) map.get("task_status_id"));
            ts.setPara1((String) map.get("para1"));
            ts.setPara2((String) map.get("para2"));
            ts.setPara3((String) map.get("para3"));
            ts.setLog_path((String) map.get("log_path"));
            ts.setTask_obj((String) map.get("task_obj"));
            ts.setTask_id((Integer) map.get("task_id"));
            ts.setCycle((String) map.get("cycle"));
            ts.setTime_id((String) map.get("time_id"));
            ts.setType((Integer) map.get("type"));
            ts.setSuccess_code((String) map.get("success_code"));
            ts.setWait_code((String) map.get("wait_code"));
            ts.setIf_wait((Integer) map.get("if_wait"));
            ts.setTask_name((String) map.get("task_name"));
            ts.setInQueueTimeMillis(System.currentTimeMillis());
            if (ts != null && this.getSameJobRunCnt(ts) > 0) {
                logger.info(ts.getTask_id() + "(" + ts.getTask_name() + ") job has same job running");
                continue;
            } else {
                if (StandardExecute.runningQueue.containsKey(ts.getTask_status_id())) {
                    logger.info("Warning!!" + ts.getTask_status_id() + "(" + ts.getTask_name() + " is already in Running Queue");
                    continue;
                }
                return ts;
            }
        }
        return null;
    }

    /**************
     *
     * @param ts
     * @return
     * @throws Exception
     */
    private int getSameJobRunCnt(TaskStatus ts) throws Exception {
        String sql = "select count(1) num from etl_task_status where status =? and task_id=?";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{SystemConstant.JOB_RUNNING, ts.getTask_id()});
    }

    /***********
     *
     * @param ts
     * @return
     * @throws Exception
     */
    private Object[] executeTask(TaskStatus ts) throws Exception {
        String update_sql = "update etl_task_status set status = ?,sts_desc=?,run_num=run_num +1 ,start_time =now() where task_status_id = ? and status =?";
        this.jdbcTemplate.update(update_sql, new Object[]{SystemConstant.JOB_RUNNING, "RUNNING", ts.getTask_status_id(), SystemConstant.JOB_READY});
        if(ts.getType()==1){
            return ProcessUtil.executeWormholeCommand(ts);
        }else if(ts.getType()==2){
            return ProcessUtil.executeCommand(ts);
        }else{
            logger.error("illegal type parameter");
            throw new IllegalArgumentException("task's type is illegal");
        }
    }

    private void recordLog(TaskStatus model, Object[] para) throws Exception {
        int rtn = (Integer) para[0];
        int result = -1000;
        String desc = null;
        String[] success_code = model.getSuccess_code().split(";");

        for (int i = 0; i < success_code.length; i++) {
            if (rtn == Integer.valueOf(success_code[i])) {
                result = SystemConstant.JOB_SUCCESS;
                desc = "SUCCESS";
                break;
            }
        }
        boolean flag = false;
        if (result != SystemConstant.JOB_SUCCESS) {
            if (model.getIf_wait() == 1) {
                String[] wait_code = model.getWait_code().split(";");
                try {
                    for (int i = 0; i < wait_code.length; i++) {
                        if (rtn % 1000 == new Integer(wait_code[i])) {
                            flag = true;
                            result = SystemConstant.JOB_WAIT;
                            desc = "WAIT";
                            break;
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    e.printStackTrace();
                    result = SystemConstant.JOB_FAIL;
                    desc = "Internal WAIT Error";
                }
            }
            if (flag == false) {
                if (rtn == SystemConstant.RUNTIME_LOGFILE_NOTFOUND) {
                    result = SystemConstant.JOB_FAIL;
                    desc = "Internal Error";
                } else {
                    result = SystemConstant.JOB_FAIL;
                    desc = "FAIL";
                }
            }
        }

        String sql = "update etl_task_status set status =?,end_time=now(),sts_desc=?,job_code=? where task_status_id=? and status in (?,?)";
        this.jdbcTemplate.update(sql, new Object[]{result, desc, rtn, model.getTask_status_id(), SystemConstant.JOB_RUNNING, SystemConstant.JOB_TIMEOUT});
    }

    private void sendEmail(TaskStatus model) {
        String sql = "select recall_limit,task_status_id,task_name,log_path,prio_lvl,if_recall,recall_num,job_code,recall_code,b.owner_name,b.mail,b.phone from etl_task_status a left join dim_owner b on a.owner=b.owner_name where task_status_id=? and status=?";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, new Object[]{model.getTask_status_id(), SystemConstant.JOB_FAIL});
        if (list == null || list.size() == 0) {
            return;
        }
        Map<String, Object> map = list.get(0);

        String task_status_id = (String) map.get("task_status_id");
        String task_name = (String) map.get("task_name");
        String log_path = (String) map.get("log_path");
        String content = "halley send:the task_status_id " + task_status_id + "(" + task_name + ") fail,the log path is on " + log_path;
        String to_user = (String) map.get("mail");
        String subject = "task error report";
        Integer if_recall = (Integer) map.get("if_recall");
        Integer recall_num = (Integer) map.get("recall_num");
        Integer type = 1;
        Integer recall_limit = (Integer) map.get("recall_limit");
        String email_type = "simple";

        if (if_recall == 1 && recall_num < recall_limit) {
            return;
        }
        logger.info(content);
        String sendEmail_sql = "insert into etl_email_log(rule_id,from_user,to_user,cc,`subject`,content,type,sts,email_type,time_stamp) values(?,?,?,?,?,?,?,?,?,now())";
        this.jdbcTemplate.update(sendEmail_sql, new Object[]{999,"dpbi@51ping.com",to_user,null,subject,content,type,0,email_type});
    }

}
