package com.dianping;

import com.dianping.service.executer.ETLExecute;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ETLExecute Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/04/2014</pre>
 */
public class ETLExecuteTest extends TestCase {
    public ApplicationContext context;

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-ETLexecute.xml"
        );
    }

    /**
     * Method: execute()
     */
    public void testExecute() throws Exception {
//TODO: Test goes here...
        ETLExecute executer = (ETLExecute)context.getBean("salesforce_Service");
        executer.execute();
    }





} 
