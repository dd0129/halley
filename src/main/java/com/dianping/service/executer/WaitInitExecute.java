package com.dianping.service.executer;

import com.dianping.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Service
public class WaitInitExecute {
    private static final Logger logger = LoggerFactory.getLogger(WaitInitExecute.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void waitInit() {
        try{
            logger.info("the waitInit starts");
            String sql = "update etl_task_status set status = ?,sts_desc=? where status =?";
            this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_INIT, "INIT", SystemConstant.JOB_WAIT});
        }finally{
            logger.info("the waitInit ends");
        }
    }
}
