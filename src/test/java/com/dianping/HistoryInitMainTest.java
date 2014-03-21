package com.dianping;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * HistoryInitMain Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/04/2014</pre>
 */
public class HistoryInitMainTest extends TestCase {
    public ApplicationContext context;
    public HistoryInitMainTest(String name) {

    }

    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext-db.xml",
                "spring/applicationContext-init.xml",
                "spring/applicationContext-execute.xml",
                "spring/applicationContext-ETLexecute.xml",
                "spring/applicationContext-recall.xml",
                "spring/applicationContext-wait.xml",
                "spring/applicationContext-ready.xml",
                "spring/applicationContext-timeout.xml",
                "spring/applicationContext-kickTask.xml"
        );
    }


} 
