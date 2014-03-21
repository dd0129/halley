package com.dianping;

import com.dianping.service.executer.StandardExecute;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * StandardExecute Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/07/2014</pre>
 */
public class StandardExecuteTest extends TestCase {
    public ApplicationContext context;

    public StandardExecuteTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-execute.xml");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: execute()
     */
    public void testExecute() throws Exception {
//TODO: Test goes here...
        StandardExecute service = (StandardExecute)context.getBean("hive_executeService");
        service.execute();
    }

    /**
     * Method: setRun_num(int run_num)
     */
    public void testSetRun_num() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setDatabase_src(String database_src)
     */
    public void testSetDatabase_src() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getRunningJobNum()
     */
    public void testGetRunningJobNum() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("getRunningJobNum"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: recordLog(TaskStatus ts, Object[] para)
     */
    public void testRecordLog() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("recordLog", TaskStatus.class, Object[].class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getReadyTask()
     */
    public void testGetReadyTask() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("getReadyTask"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getSameJobRunCnt(TaskStatus ts)
     */
    public void testGetSameJobRunCnt() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("getSameJobRunCnt", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: executeTask(TaskStatus ts)
     */
    public void testExecuteTask() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("executeTask", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: sendEmail(TaskStatus model)
     */
    public void testSendEmail() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = StandardExecute.getClass().getMethod("sendEmail", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }


    public static Test suite() {
        return new TestSuite(StandardExecuteTest.class);
    }
} 
