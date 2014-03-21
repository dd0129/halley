package com.dianping.service.initer;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * PrePareInit Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/08/2014</pre>
 */
public class PrePareInitTest extends TestCase {
    public ApplicationContext context;

    public PrePareInitTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-init.xml");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: preInit()
     */
    public void testPreInit() throws Exception {
//TODO: Test goes here...
        PrePareInit service = (PrePareInit)context.getBean("preInitService");
        service.preInit();
    }

    /**
     * Method: batchInsert(String sql, List<Map<String, Object>> data_list, String[] parmas)
     */
    public void testBatchInsert() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: calculateScore(int task_id, int prio_lvl)
     */
    public void testCalculateScore() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getInitTaskNum(TaskStatus model)
     */
    public void testGetInitTaskNum() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("getInitTaskNum", TaskStatus.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getAllTask()
     */
    public void testGetAllTask() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("getAllTask"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getAllTaskRela()
     */
    public void testGetAllTaskRela() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("getAllTaskRela"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getTaskList()
     */
    public void testGetTaskList() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("getTaskList"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: inputModel(Map<String, Object> map, Date init_date)
     */
    public void testInputModel() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("inputModel", Map<String,.class, Date.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: initTaskSts(TaskStatus model, Long trigger_time)
     */
    public void testInitTaskSts() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("initTaskSts", TaskStatus.class, Long.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getTaskMap(int pk)
     */
    public void testGetTaskMap() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = PrePareInit.getClass().getMethod("getTaskMap", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }


    public static Test suite() {
        return new TestSuite(PrePareInitTest.class);
    }
} 
