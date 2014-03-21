package com.dianping.tools;

import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessUtilBak {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtilBak.class);

    public static Object[] executeCommand(TaskStatus ts) {
        logger.info("cmd process starts");
        FileOutputStream out = null;
        FileOutputStream snapshot = null;
        InputStream is = null;
        String logFile = ts.getLog_path();
        String snapshotFile = ts.getLog_path().concat(".snapshot");
        logger.debug("logpath := ".concat(logFile));
        try {
            //logger.debug(ts.get);
            String para1 = ts.getPara1() == null ? "" : ts.getPara1();
            String para2 = ts.getPara2() == null ? "" : ts.getPara2();
            String para3 = ts.getPara3() == null ? "" : ts.getPara3();
            logger.info("cmd :".concat(ts.getTask_obj()).concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3)));

            ProcessBuilder pb = new ProcessBuilder(ts.getTask_obj(), para1, para2, para3);
            pb.redirectErrorStream(true);
            Process pro = pb.start();
            File outputFile = new File(logFile);
            File snapshotOutputFile = new File(snapshotFile);

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            if (!snapshotOutputFile.exists()) {
                snapshotOutputFile.createNewFile();
            }
            out = new FileOutputStream(outputFile, true);
            snapshot = new FileOutputStream(snapshotOutputFile, false);

            is = pro.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;

            while ((line = br.readLine()) != null) {
                logger.debug(line);
                out.write((line + "\r\n").getBytes());
                snapshot.write((line + "\r\n").getBytes());
            }
            Integer rtn = pro.waitFor();
            logger.debug("check rtn :=" + String.valueOf(rtn));
            return new Object[]{rtn, null};
        } catch (Exception e) {
            logger.error("shell or log file not found", e);
            return new Object[]{SystemConstant.RUNTIME_LOGFILE_NOTFOUND, e.getMessage()};
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (snapshot != null) {
                    snapshot.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("cmd process ends");
        }
    }

    public static Object[] executeWormholeCommand(TaskStatus ts) {
        logger.info("cmd process starts");
        FileOutputStream out = null;
        FileOutputStream snapshot = null;
        InputStream is = null;
        String logFile = ts.getLog_path();
        String snapshotPath = ts.getLog_path().concat(".snapshot");
        logger.debug("logpath := ".concat(logFile));
        try {
            String para1 = ts.getPara1() == null ? "" : ts.getPara1();
            String para2 = ts.getPara2() == null ? "" : ts.getPara2();
            String para3 = ts.getPara3() == null ? "" : ts.getPara3();

            ProcessBuilder pb = new ProcessBuilder(ts.getTask_obj(), para1, para2, para3);
            logger.info("cmd :".concat(ts.getTask_obj()).concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3)));
            pb.redirectErrorStream(true);
            Process pro = pb.start();

            File outputFile = new File(ts.getLog_path());
            File snapshotFile = new File(snapshotPath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            if (!snapshotFile.exists()) {
                snapshotFile.createNewFile();
            }

            Integer rtn = null;

            final String rtnMatch = "return code-";
            final Pattern pattern = Pattern.compile(rtnMatch);

            out = new FileOutputStream(outputFile, true);
            snapshot = new FileOutputStream(snapshotFile, false);

            is = pro.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            String rtnCodeStr = null;

            while ((line = br.readLine()) != null) {
                logger.debug(line);
                out.write((line + "\r\n").getBytes());
                snapshot.write((line + "\r\n").getBytes());
                Matcher match = pattern.matcher(line);
                if (match.find()) {
                    rtnCodeStr = line;
                    logger.debug("stand_buf :=" + line);
                }
            }

            int systemRtn = pro.waitFor();

            try {
                logger.debug("rtn_code_str := " + rtnCodeStr);
                int index = rtnCodeStr.indexOf(rtnMatch);
                String tmp2 = rtnCodeStr.substring(index).replace(rtnMatch, "").trim();
                rtn = Integer.valueOf(tmp2);
                logger.debug("rtn :=" + rtn);
                return new Object[]{rtn, null};
            } catch (Exception e) {
                logger.error("not found rtn_code_str", e);
                return new Object[]{systemRtn, "mutual kill"};
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("shell or log file not found", e);
            return new Object[]{SystemConstant.RUNTIME_LOGFILE_NOTFOUND, e.getMessage()};
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (snapshot != null) {
                    snapshot.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("cmd process ends");
        }
    }


}
