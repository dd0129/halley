package com.dianping.service.initer;

import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import com.dianping.tools.DateUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

@Deprecated
public class HistoryInit {


    private String[] cols_rela = new String[]{"task_status_id", "task_id", "pre_id", "pre_sts_id"};

    private static final Logger logger = LoggerFactory.getLogger(PrePareInit.class);

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    private List<Map<String, Object>> getTaskList(Integer task_id) {
        String sql = "select "
                + "task_id,        "
                + "task_group_id,  "
                + "task_name,      "
                + "task_obj,       "
                + "para1,          "
                + "para2,          "
                + "para3,          "
                + "cycle,          "
                + "prio_lvl,       "
                + "log_home,       "
                //+"log_load,       "
                + "log_file,       "
                + "type,           "
                //+"server,         "
                + "offset,         "
                + "offset_type,    "
                + "table_name,     "
                + "database_src,   "
                //+"business_area,  "
                + "freq,           "
                + "owner,          "
                + "recall_code,    "
                + "success_code,   "
                + "wait_code,   "
                + "if_wait,   "
                + "if_recall,   "
                + "timeout,   "
                + "recall_interval,   "
                + "recall_limit,   "
                + "if_pre          "
                + "from etl_task_cfg where task_id=? ";
//logger.info(sql);		
        //return this.dao.queryForList(sql, null);
        return this.jdbcTemplate.queryForList(sql, task_id);
    }

    private TaskStatus inputModel(Map<String, Object> map, Date init_date) throws Exception {
        String log_tail = DateUtils.getDay8();
        String cycle = DateUtils.getDay10(init_date);
        String cycle1 = DateUtils.getLastDay10(init_date);
        String cycle2 = DateUtils.getDay8(init_date);

        TaskStatus model = new TaskStatus();
        String cal_dt = null;
        String task_obj = null;
        String para1 = null;
        String para2 = null;
        String para3 = null;
        int status = SystemConstant.JOB_INIT;
        Integer task_id = (Integer) map.get("task_id");
        String task_status_id = "pre_" + task_id + "_" + cycle2 + "_" + DateUtils.Date2String(new Date());
        String sts_desc = null;
        try {
            cal_dt = DateUtils.get_cal_dt(cycle1, (String) map
                    .get("offset_type"), (String) map.get("offset"));
            String offset_type = (String) map.get("offset_type");
            String offset = (String) map.get("offset");

//            task_obj = DateUtils.getReplaceCal((String) map.get("task_obj"), offset_type, offset, init_date).replace("${wormhole_home}", GlobalResource.WORMHOLE_HOME)
//                    .replace("${calculate_home}", GlobalSource.CALCULATE_HOME);
//            para1 = (String) map.get("para1");
//            if (para1 != null && !para1.trim().equals("")) {
//                para1 = DateUtils.getReplaceCal(para1, offset_type, offset, init_date).replace("${wormhole_job_home}", GlobalSource.WORMHOLE_JOB_HOME)
//                        .replace("${calculate_job_home}", GlobalResource.CALCULATE_JOB_HOME).replace("${task_id}", String.valueOf(task_id)).
//                                replace("${instance_id}", task_status_id).replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
//            }
            para2 = (String) map.get("para2");
            if (para2 != null && !para2.trim().equals("")) {
                para2 = DateUtils.getReplaceCal(para2, offset_type, offset, init_date).replace("${task_id}", String.valueOf(task_id)).replace("${instance_id}", task_status_id)
                        .replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
            }

            para3 = (String) map.get("para3");
            if (para3 != null && !para3.trim().equals("")) {
                para3 = DateUtils.getReplaceCal((String) map.get("para3"), offset_type, offset, init_date).replace("${task_id}", String.valueOf(task_id))
                        .replace("${instance_id}", task_status_id).replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
            }

            sts_desc = "INIT";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error(task_id + " init error " + e.toString());
            e.printStackTrace();
            cal_dt = null;
            task_obj = (String) map.get("task_obj");
            /*
			para1 = DateUtils.getReplaceCal((String) map.get("para1"),cal_dt).replace("${WORMHOLEJOBHOME}", this.WORMHOLEJOBHOME)
				.replace("${caculate_job_home}", this.caculate_job_home);
			para2 = DateUtils.getReplaceCal((String) map.get("para2"),cal_dt);
			para3 = DateUtils.getReplaceCal((String) map.get("para3"),cal_dt);
			*/
            sts_desc = e.getMessage();
            status = SystemConstant.JOB_INIT_ERROR;
        }

        String log_home = (String) map.get("log_home");
        String log_file = (String) map.get("log_file");


        Integer task_group_id = (Integer) map.get("task_group_id");
        String task_name = (String) map.get("task_name");

        String owner = (String) map.get("owner");
        String recall_code = (String) map.get("recall_code");
        String wait_code = (String) map.get("wait_code");
        Integer if_recall = (Integer) map.get("if_recall");
        Integer if_wait = (Integer) map.get("if_wait");

        Integer recall_limit = (Integer) map.get("recall_limit");
        Integer recall_interval = (Integer) map.get("recall_interval");

//        String log_path = log_home.replace("${wormhole_log_home}", GlobalResource.WORMHOLE_LOG_HOME)
//                .replace("${calculate_log_home}", GlobalResource.CALCULATE_LOG_HOME)
//                + File.separator + log_file + "." + log_tail;

        String time_id = cycle;
        Integer prio_lvl = 5;
        Integer run_num = 0;
        Integer recall_num = 0;
        Integer type = (Integer) map.get("type");
        Integer timeout = (Integer) map.get("timeout");
        String table_name = (String) map.get("table_name");

        String database_src = (String) map.get("database_src");
        String freq = "";
        Integer if_pre = 0;
        String success_code = (String) map.get("success_code");

        model.setTask_status_id(task_status_id);
        model.setTask_id(task_id);

        if (task_group_id != null) {
            model.setTask_group_id(new Integer(task_group_id));
        }
        model.setTask_name(task_name);
        model.setTask_obj(task_obj);
        model.setPara1(para1);
        model.setPara2(para2);
        model.setPara3(para3);
        //model.setLog_path(log_path);
        model.setCycle((String) map.get("cycle"));
        model.setTime_id(time_id);
        model.setPrio_lvl(prio_lvl);
        model.setRun_num(run_num);
        model.setType(type);
        model.setTask_name(task_name);
        model.setDatabase_src(database_src);
        model.setTable_name(table_name);
        model.setFreq(freq);
        model.setCal_dt(cal_dt);
        model.setIf_pre(if_pre);
        model.setSts_desc(sts_desc);
        model.setStatus(status);
        model.setRecall_num(recall_num);
        model.setOwner(owner);
        model.setRecall_code(recall_code);
        model.setSuccess_code(success_code);
        model.setIf_recall(if_recall);
        model.setIf_wait(if_wait);
        model.setTimeout(timeout);
        model.setWait_code(wait_code);
        model.setRecall_interval(recall_interval);
        model.setRecall_limit(recall_limit);
        return model;
    }

    private void initTaskSts(TaskStatus model, Long trigger_time) throws Exception {

        String sql = "insert into etl_task_status"
                + "  (task_status_id,"
                + "   task_id,       "
                + "   task_group_id, "
                + "   task_name,     "
                + "   task_obj,      "
                + "   para1,         "
                + "   para2,         "
                + "   para3,         "
                + "   log_path,      "
                + "   cycle,         "
                + "   time_id,       "
                + "   status,        "
                + "   prio_lvl,      "
                + "   run_num,       "
                + "   type,    "
                + "   table_name,    "
                + "   cal_dt,    "
                + "   database_src,    "
                + "   if_pre,    "
                + "   if_wait,    "
                + "   if_recall,    "
                + "   sts_desc,    "
                + "   recall_num,    "
                + "   owner,    "
                + "   trigger_time,    "
                + "   recall_code,    "
                + "   success_code,    "
                + "   wait_code,    "
                + "   job_code,    "
                + "   freq,"
                + "   timeout,"
                + "   recall_interval,"
                + "   recall_limit,"
                + "   time_stamp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";


        String cal_dt = model.getCal_dt();
        String task_status_id = model.getTask_status_id();
        Integer task_id = model.getTask_id();
        Integer task_group_id = model.getTask_group_id();
        String task_name = model.getTask_name();
        String task_obj = model.getTask_obj();
        String para1 = model.getPara1();
        String para2 = model.getPara2();
        String para3 = model.getPara3();
        String log_path = model.getLog_path();
        String model_cycle = model.getCycle();
        String time_id = model.getTime_id();
        Integer prio_lvl = model.getPrio_lvl();
        Integer run_num = model.getRun_num();
        Integer type = model.getType();

        String table_name = model.getTable_name();
        String database_src = model.getDatabase_src();
        String freq = model.getFreq();
        Integer if_pre = model.getIf_pre();
        String sts_desc = model.getSts_desc();
        Integer recall_num = model.getRecall_num();
        int status = model.getStatus();
        String owner = model.getOwner();
        String recall_code = null;
        int if_wait = model.getIf_wait();
        int if_recall = 0;
        String wait_code = model.getWait_code();
        //String recall_code = model.getRecall_code();
        String success_code = model.getSuccess_code();
        //String cycle = model.getCycle();
        int timeout = model.getTimeout();
        int recall_limit = model.getRecall_limit();
        int recall_interval = model.getRecall_interval();


        this.jdbcTemplate.update(sql, new Object[]{task_status_id, task_id, task_group_id, task_name, task_obj,
                para1, para2, para3, log_path, model_cycle, time_id, status, prio_lvl, run_num,
                type, table_name, cal_dt, database_src, if_pre, if_wait,
                if_recall, sts_desc, recall_num, owner, trigger_time, recall_code, success_code,
                wait_code, SystemConstant.REAL_JOB_INIT, freq, timeout, recall_interval, recall_limit});

    }

    private Object[] validParameter(String[] args) throws Exception {
        if (args.length != 6) {
            this.printHelpInfo();
            throw new Exception("error paramerters");
        }
        Object[] result = new Object[3];
        for (int i = 0; i < args.length; i++) {
            if (i == 0 || i == 2 || i == 4) {
                if (args[i].trim().equals("-id")) {
                    result[0] = new Integer(args[i + 1]);
                } else if (args[i].trim().equals("-begin")) {
                    result[1] = args[i + 1];
                } else if (args[i].trim().equals("-end")) {
                    result[2] = args[i + 1];
                }
            }
        }
        for (Object para : result) {
            if (para == null) {
                this.printHelpInfo();
                throw new Exception("error paramerters");
            }
        }
        return result;
    }

    private void printHelpInfo() {
        String help_info = "error paramerters \n" +
                "the right example is : sh /data/deploy/halley/bin/runJob.sh -id XXXXX -begin YYYY-MM-DD -end YYYY-MM-DD";
        logger.info(help_info);
    }

    public void startInit(String[] args) {
        logger.info("the historyInt process starts");
        try {
            Object[] para = this.validParameter(args);

            Date start_date = DateUtils.string2Date((String) para[1]);
            Date end_date = DateUtils.string2Date((String) para[2]);

            Date init_date = start_date;
            Integer task_id = (Integer) para[0];

            List<Map<String, Object>> list = this.getTaskList(task_id);
            Map<String, Object> map = list.get(0);
            String freq = (String) map.get("freq");
            String cycle = (String) map.get("cycle");
            if (!(cycle.equals("D") || cycle.equals("M") || cycle.equals("W"))) {
                logger.info("the freq of the job is not right!");
                this.printHelpInfo();
                return;
            }
            CronExpression ce = new CronExpression(freq);
            Date d = new Date();
            //Date d1 = new Date();
            while (true) {
                init_date = ce.getNextValidTimeAfter(init_date);

                //add date compare future,if NextValidTime is after today + 1day,continue loop
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date tmp1 = sdf.parse(sdf.format(init_date));
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.DATE, +1);
                Date tmp2 = sdf.parse(sdf.format(c.getTime()));

                if (tmp1.compareTo(tmp2) >= 0) {
                    logger.info("the date is more than today + 1day,process will filter the record!");
                    break;
                }

                TaskStatus model = this.inputModel(map, init_date);
                this.initTaskSts(model, d.getTime());

                if (init_date.after(end_date)) {
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("the historyInt process ends");
    }


}

