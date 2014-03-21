package com.dianping.service.executer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ETLExecute Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/07/2014</pre>
 */
public class ETLExecuteTest extends TestCase {
    public ApplicationContext context;

    public ETLExecuteTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-ETLexecute.xml");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: setWormhole_max_num(int wormhole_max_num)
     */
    public void testSetWormhole_max_num() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setConcurrency(int concurrency)
     */
    public void testSetConcurrency() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setDatabase_src(String database_src)
     */
    public void testSetDatabase_src() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: execute()
     */
    public void testExecute() throws Exception {
//TODO: Test goes here...
        ETLExecute service = (ETLExecute)context.getBean("mysql_DianPing_Service");
        service.execute();
    }


    /**
     * Method: getRunningExecuteNum()
     */
    public void testGetRunningExecuteNum() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ETLExecute.getClass().getMethod("getRunningExecuteNum"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getRunningJobNum()
     */
    public void testGetRunningJobNum() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ETLExecute.getClass().getMethod("getRunningJobNum"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getNextTask()
     */
    public void testGetNextTask() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ETLExecute.getClass().getMethod("getNextTask"); 
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
   Method method = ETLExecute.getClass().getMethod("getSameJobRunCnt", TaskStatus.class); 
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
   Method method = ETLExecute.getClass().getMethod("executeTask", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: recordLog(TaskStatus model, Object[] para)
     */
    public void testRecordLog() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ETLExecute.getClass().getMethod("recordLog", TaskStatus.class, Object[].class); 
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
   Method method = ETLExecute.getClass().getMethod("sendEmail", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }


    public static Test suite() {
        return new TestSuite(ETLExecuteTest.class);
    }
} 
