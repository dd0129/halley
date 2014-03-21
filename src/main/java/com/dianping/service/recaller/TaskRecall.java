package com.dianping.service.recaller;

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
public class TaskRecall {
    private static final Logger logger = LoggerFactory.getLogger(TaskRecall.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void recall() {
        try{
            logger.info("the task recall process starts");
            for (Map<String,Object> map : this.getTaskList()) {
                String task_status_id = (String) map.get("task_status_id");
                this.updateSts(task_status_id);
            }
        }finally{
            logger.info("the task recall process ends");
        }
    }

    private void updateSts(String pk) {
        String sql = "update etl_task_status set status = ?,job_code=?,recall_num = recall_num +1 where task_status_id=?";
        this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_INIT, SystemConstant.REAL_JOB_INIT, pk});
    }

    private List<Map<String, Object>> getTaskList() {
        String sql = "select task_status_id,status,recall_code,job_code from etl_task_status where status =? and if_recall = ? and recall_num < recall_limit and UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(end_time)>=recall_interval *60";
        return this.jdbcTemplate.queryForList(sql, new Object[]{SystemConstant.JOB_FAIL, 1});
    }

}
