package com.dianping.main;


import com.dianping.constant.GlobalResource;
import com.dianping.service.ResetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleMain {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleMain.class);

    public static void main(String[] args) {
        logger.info(GlobalResource.CONF_PATH);
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-db.xml",
                "classpath:spring/applicationContext-reset.xml");
        ResetService service = (ResetService) context.getBean("resetService");
        if (service.reset()) {
            new ClassPathXmlApplicationContext(
                    "spring/applicationContext-db.xml",
                    "spring/applicationContext-init.xml",
                    "spring/applicationContext-execute1.xml",
                    //"spring/applicationContext-ETLexecute.xml",
                    "spring/applicationContext-recall.xml",
                    "spring/applicationContext-wait.xml",
                    "spring/applicationContext-ready.xml",
                    "spring/applicationContext-timeout.xml",
                    "spring/applicationContext-kickTask.xml"
            );
        }
    }
}
;