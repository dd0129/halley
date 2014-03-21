package com.dianping.tools;

import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessUtil {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtil.class);

    public static Object[]  executeCommand(TaskStatus ts) {

        logger.info("cmd process starts");
        final File outputFile ;
        final File snapshotOutputFile;
        final FileOutputStream logWriter;
        final FileOutputStream snapshotWriter;
        final InputStream errorStream;
        final InputStream inputStream;

        BufferedReader br;

        try {
            String para1 = ts.getPara1() == null ? "" : ts.getPara1();
            String para2 = ts.getPara2() == null ? "" : ts.getPara2();
            String para3 = ts.getPara3() == null ? "" : ts.getPara3();
            String cmd = ts.getTask_obj().concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3));

            String logFile = ts.getLog_path();
            String snapshotFile = ts.getLog_path().concat(".snapshot");

            logger.info("cmd :".concat(ts.getTask_obj()).concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3)));

            Process pro = Runtime.getRuntime().exec(cmd);
            outputFile = new File(logFile);
            snapshotOutputFile = new File(snapshotFile);

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            if (!snapshotOutputFile.exists()) {
                snapshotOutputFile.createNewFile();
            }

            logWriter = new FileOutputStream(outputFile, true);
            snapshotWriter = new FileOutputStream(snapshotOutputFile, false);

            errorStream = pro.getErrorStream();
            new Thread(new Runnable() {
                public void run() {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
                        String line2 = null;
                        while ((line2 = br.readLine()) != null) {
                            logger.debug(line2);
                            logWriter.write((line2 + "\r\n").getBytes());
                            snapshotWriter.write((line2 + "\r\n").getBytes());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    } finally{
                        try {
                            if(br != null){
                                br.close();
                            }
                        } catch (IOException e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                }
            }).start();

            inputStream = pro.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line1 = null;

            while ((line1 = br.readLine()) != null) {
                logger.debug(line1);
                logWriter.write((line1 + "\r\n").getBytes());
                snapshotWriter.write((line1 + "\r\n").getBytes());
            }
            int rtn = pro.waitFor();
            logger.debug("check rtn :=" + (new Integer(rtn)).toString());
            return new Object[]{rtn, null};
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new Object[]{SystemConstant.RUNTIME_LOGFILE_NOTFOUND, e.getMessage()};
        } finally {
//            final File outputFile ;
//            final File snapshotOutputFile;
//            logWriter;
//            final FileOutputStream snapshotWriter;
//            final InputStream errorStream;
//            final InputStream inputStream;
//
//            try {
//                if(br != null){
//                    br.close();
//                }
//                if(logWriter !=null){
//                    logWriter.close();
//                }
//                if(snapshotWriter !=null){
//                    snapshotWriter.close();
//                }
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
            logger.info("cmd process ends");
        }
    }

    public static Object[] executeWormholeCommand(TaskStatus ts) {
        logger.info("cmd process starts");
        final File outputFile ;
        final File snapshotFile;
        final FileOutputStream out;
        final FileOutputStream snapshot;
        final InputStream inputStream;
        final InputStream errorStream;

        try {
            String para1 = ts.getPara1() == null ? "" : ts.getPara1();
            String para2 = ts.getPara2() == null ? "" : ts.getPara2();
            String para3 = ts.getPara3() == null ? "" : ts.getPara3();
            String cmd = ts.getTask_obj().concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3));

            String logFile = ts.getLog_path();
            String snapshotLogFile = ts.getLog_path().concat(".snapshot");

            logger.info("cmd :".concat(ts.getTask_obj()).concat(" ".concat(para1)).concat(" ".concat(para2)).concat(" ".concat(para3)));

            Process pro = Runtime.getRuntime().exec(cmd);
            outputFile = new File(logFile);
            snapshotFile = new File(snapshotLogFile);

            Integer rtn = null;

            final String rtn_match = "return code-";
            final Pattern pattern = Pattern.compile(rtn_match);

            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            if (!snapshotFile.exists()){
                snapshotFile.createNewFile();
            }
            out = new FileOutputStream(outputFile, true);

            snapshot = new FileOutputStream(snapshotFile, false);

            //final StringBuffer stand_buf = new StringBuffer();

            inputStream = pro.getInputStream();
            errorStream = pro.getErrorStream();

            new Thread(new Runnable() {
                public void run() {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
                    String line2 = null;
                    while ((line2 = br.readLine()) != null) {
                        logger.debug(line2);
                        out.write((line2 + "\r\n").getBytes());
                        snapshot.write((line2 + "\r\n").getBytes());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    e.printStackTrace();
                }
                }
            }).start();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line1 = null;
            String rtn_code_str = null;

            while ((line1 = br.readLine()) != null) {
                logger.debug(line1);
                //tmp1 = line1;
                out.write((line1 + "\r\n").getBytes());
                snapshot.write((line1 + "\r\n").getBytes());

                Matcher match = pattern.matcher(line1);

                if (match.find()) {
                    rtn_code_str = line1;
                    logger.debug("stand_buf :=" + line1);
                }
            }

            int system_rtn = pro.waitFor();

            try {
                logger.debug("rtn_code_str := " + rtn_code_str);
                int index = rtn_code_str.indexOf(rtn_match);
                String tmp2 = rtn_code_str.substring(index).replace(rtn_match, "").trim();
                System.out.println("rtn :=" + tmp2);

                rtn = new Integer(tmp2);
                logger.debug("rtn :=" + (new Integer(rtn)).toString());

                return new Object[]{rtn, null};
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
                return new Object[]{system_rtn, "mutual kill"};
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new Object[]{SystemConstant.RUNTIME_LOGFILE_NOTFOUND, e.getMessage()};
        } finally {
            logger.info("cmd process ends");
        }
    }


}
