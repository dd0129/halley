package com.dianping.service.executer;

import com.dianping.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class TimeoutExecute {
    private static final Logger logger = LoggerFactory.getLogger(TimeoutExecute.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private List<Map<String, Object>> getTaskList() {
        String sql = "select task_status_id,UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(start_time) `interval`,timeout from etl_task_status a where status =?";
        return this.jdbcTemplate.queryForList(sql, new Object[]{SystemConstant.JOB_RUNNING});

    }

    private void updateTimeoutJob(String task_status_id) {
        String sql = "update etl_task_status set status = ?,sts_desc=? where task_status_id = ?";
        this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_TIMEOUT, "TIMEOUT", task_status_id});
    }


    public void execute() {
        try{
            logger.info("the timeout execute process starts");
            for (Map<String, Object> map : this.getTaskList()) {
                try {
                    String task_status_id = (String) map.get("task_status_id");
                    Long interval = (Long) map.get("interval");
                    Integer timeout = (Integer) map.get("timeout");
                    if (interval > timeout * 60) {
                        logger.info("the job " + task_status_id + " running time " + interval + " seconds, More than timeout time");
                        this.updateTimeoutJob(task_status_id);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }finally{
            logger.info("the timeout execute process ends");
        }
    }
}
