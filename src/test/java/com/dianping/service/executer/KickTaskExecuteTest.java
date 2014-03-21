package com.dianping.service.executer;

import com.dianping.model.TaskStatus;
import junit.framework.TestCase;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * KickTaskExecute Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/07/2014</pre>
 */
public class KickTaskExecuteTest extends TestCase {
    public ApplicationContext context = null;

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-kickTask.xml"
        );
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: execute()
     */
    public void testExecute() throws Exception {
        KickTaskExecute service = (KickTaskExecute)context.getBean("kickTaskService");
        TaskStatus ts = this.generateInstance("100012013120300");
        StandardExecute.runningQueue.put(ts.getTask_status_id(),ts);
        service.execute();
    }

    private TaskStatus generateInstance(String instanceID){
        TaskStatus ts = new TaskStatus();
        ts.setTask_status_id(instanceID);
//        ts.setPara1((String) map.get("para1"));
//        ts.setPara2((String) map.get("para2"));
//        ts.setPara3((String) map.get("para3"));
//        ts.setLog_path((String) map.get("log_path"));
//        ts.setTask_obj((String) map.get("task_obj"));
//        ts.setTask_id((Integer) map.get("task_id"));
//        ts.setCycle((String) map.get("cycle"));
//        ts.setTime_id((String) map.get("time_id"));
//        ts.setType((Integer) map.get("type"));
//        ts.setSuccess_code((String) map.get("success_code"));
//        ts.setWait_code((String) map.get("wait_code"));
//        ts.setIf_wait((Integer) map.get("if_wait"));
//        ts.setTask_name((String) map.get("task_name"));
        ts.setInQueueTimeMillis(DateUtils.addHours(new Date(),-5).getTime());
        //ts.setInQueueTimeMillis(System.currentTimeMillis());



        return ts;

    }
} 
