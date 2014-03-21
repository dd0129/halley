package com.dianping.service;

import com.dianping.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResetService {
    private static final Logger logger = LoggerFactory.getLogger(ResetService.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public boolean reset() {
        logger.info("scheduler init starts");
        try{
            String sql = "update etl_task_status set status = ?,sts_desc =null where status=?";
            this.jdbcTemplate.update(sql, new Object[]{SystemConstant.JOB_INIT, SystemConstant.JOB_RUNNING});
            return true;
        }finally{
            logger.info("scheduler init ends");
        }

    }

}
