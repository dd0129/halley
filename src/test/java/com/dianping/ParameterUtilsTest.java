package com.dianping;

import com.dianping.tools.ParameterUtils;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

/**
 * ParameterUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>02/07/2014</pre>
 */
public class ParameterUtilsTest extends TestCase {
    public ParameterUtilsTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: resourceParamHandle(String para)
     */
    public void testResourceParamHandle() throws Exception {
//TODO: Test goes here...
        System.out.println(ParameterUtils.resourceParamHandle("${deploy_home} asefasef saefasefasef ${calculate_home} ${wormhole_log_home} ${wormhole_job_home}"));
    }
} 
