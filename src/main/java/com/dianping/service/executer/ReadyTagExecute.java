package com.dianping.service.executer;

import com.dianping.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ReadyTagExecute {

    private static final Logger logger = LoggerFactory.getLogger(StandardExecute.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private List<Map<String, Object>> getTaskList() {
        String sql = "select task_status_id,if_pre from etl_task_status a where status =? and trigger_time <= ?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{SystemConstant.JOB_INIT, System.currentTimeMillis()});
    }

    private void updateDependent(String task_status_id, Integer if_pre) {
        if (if_pre == 0) {
            String sql = "update etl_task_status set status = ? where task_status_id = ?";
            this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_READY, task_status_id});
            return;
        } else if (if_pre == 1) {
            String sql = " select a.task_status_id, b.pre_sts_id pre_sts_id,c.task_status_id,c.task_name task_name,c.status status"
                    + " from etl_task_status a "
                    + " inner join etl_taskrela_status b on a.task_status_id = b.task_status_id	"
                    + " left join  etl_task_status  c on b.pre_sts_id = c.task_status_id "
                    + " where a.task_status_id = ?";
            boolean flag = true;

            for (Map<String, Object> map : this.jdbcTemplate.queryForList(sql, new Object[]{task_status_id})) {
                String pre_sts_id = (String) map.get("pre_sts_id");
                String task_name = (String) map.get("task_name");
                if (map.get("status") == null) {
                    flag = false;
                    logger.info(task_status_id + " pre job is not ready,pre job " + pre_sts_id + "(" + task_name + ")" + " does not have initialization ");
                    break;
                } else {
                    Integer status = (Integer) map.get("status");
                    if (status != SystemConstant.JOB_SUCCESS) {
                        logger.info(task_status_id + " pre job is not ready,pre job " + pre_sts_id + "(" + task_name + ") status is " + status);
                        flag = false;
                        break;
                    } else {
                        logger.info("pre job " + pre_sts_id + "(" + task_name + ") is ok! ");
                    }
                }
            }
            if (flag) {
                String update_sql = "update etl_task_status set status = ? where task_status_id = ?";
                this.jdbcTemplate.update(update_sql, new Object[]{SystemConstant.JOB_READY, task_status_id});
                return;
            }
            return;
        } else {
            String sql = "update etl_task_status set status=? where task_status_id = ?";
            this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_SUSPEND, task_status_id});
            return;
        }

    }

    public void execute() {
        try{
            logger.info("the tag execute process starts");
            for (Map<String, Object> map : this.getTaskList()) {
                try {
                    String task_status_id = (String) map.get("task_status_id");
                    Integer if_pre = (Integer) map.get("if_pre");
                    this.updateDependent(task_status_id, if_pre);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        }finally{
            logger.info("the tag execute process ends");
        }
    }
}
