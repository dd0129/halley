package com.dianping.main;

import com.dianping.service.initer.HistoryInit;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

public class HistoryInitMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String conf_path = System.getenv("conf_path");

        DOMConfigurator.configure(conf_path + File.separator + "log4j.xml");

        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[]{"file:" + conf_path + File.separator + "spring/applicationContext-db.xml",
                        "file:" + conf_path + File.separator + "spring/applicationContext-historyInit.xml"});

        HistoryInit init = (HistoryInit) context.getBean("historyInitService");
        init.startInit(args);

    }

}
