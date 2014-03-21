package com.dianping.service.executer;

import com.dianping.Resouce.ResourceManager;
import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hongdi.tang
 * Date: 13-11-10
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
@Service
public class KickTaskExecute {
    private static final Logger logger = LoggerFactory.getLogger(KickTaskExecute.class);

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    private Integer interval;

    public void execute() {
        boolean flag = false;
        TaskStatus ts = null;
        try {
            logger.info("the kickTask thread starts");
            for (Map.Entry<String, TaskStatus> entry : StandardExecute.runningQueue.entrySet()) {
                ts = entry.getValue();
                Long inQueueTime = ts.getInQueueTimeMillis();
                logger.info("current time " + System.currentTimeMillis() + "; "+ts.getTask_status_id()+"("+ts.getTask_name()+
                        ") push queue time " + ts.getInQueueTimeMillis());
                Integer status = this.getInstanceStatus(ts);
                logger.info("task status " + status);
                if (System.currentTimeMillis() - inQueueTime > interval * 1000) {
                    if (status != SystemConstant.JOB_RUNNING && status != SystemConstant.JOB_TIMEOUT) {
                        logger.info("the task " + ts.getTask_status_id() + "(" + ts.getTask_name() + ") push queue at " + String.valueOf(ts.getInQueueTimeMillis()) + " has been kicked");
                        StandardExecute.runningQueue.remove(entry.getKey());
                        flag = true;
                    }
                }
            }
        } finally {
            if(ts!= null && flag){
                ResourceManager.releaseResource(ts.getDatabase_src());
            }
            logger.info("the kickTask thread ends");
        }
    }

    private Integer getInstanceStatus(TaskStatus instance) {
        String sql = "select status from etl_task_status where task_status_id=?";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{instance.getTask_status_id()});
    }

}

