package com.dianping.service.initer;

import com.dianping.constant.SystemConstant;
import com.dianping.model.TaskStatus;
import com.dianping.tools.DateUtils;
import com.dianping.tools.ParameterUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service
public class PrePareInit {

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private String[] cols_rela = new String[]{"task_status_id", "task_id", "pre_id", "pre_sts_id"};

    private static final Logger logger = LoggerFactory.getLogger(PrePareInit.class);

    private Map<Integer, Integer> task_queue = new HashMap<Integer, Integer>();

    private List<Integer[]> rela_queue = new ArrayList<Integer[]>();

    private int getInitTaskNum(TaskStatus model) throws Exception {
        String sql = "select count(1) as num from etl_task_status where task_status_id=?";
        return this.jdbcTemplate.queryForInt(sql, new Object[]{model.getTask_status_id()});
    }

    private Map<Integer, Integer> getAllTask() {
        String sql = "select task_id,prio_lvl from etl_task_cfg where if_val=1";
        List<Map<String, Object>> list = PrePareInit.this.jdbcTemplate.queryForList(sql);
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        for (Map<String, Object> map : list) {
            result.put((Integer) map.get("task_id"), (Integer) map.get("prio_lvl"));
        }
        return result;
    }

    private List<Integer[]> getAllTaskRela() {
        String sql = "select task_id,task_pre_id from etl_taskrela_cfg";
        List<Map<String, Object>> list = PrePareInit.this.jdbcTemplate.queryForList(sql);
        List<Integer[]> result = new ArrayList<Integer[]>();
        for (Map<String, Object> map : list) {
            Integer[] tmp = new Integer[2];
            tmp[0] = (Integer) map.get("task_id");
            tmp[1] = (Integer) map.get("task_pre_id");
            result.add(tmp);
        }
        return result;
    }

    private List<Map<String, Object>> getTaskList() {
        String sql = "select "
                + "task_id,        "
                + "task_group_id,  "
                + "task_name,      "
                + "task_obj,       "
                + "para1,          "
                + "para2,          "
                + "para3,          "
                + "cycle,          "
                + "prio_lvl,       "
                + "log_home,       "
                + "log_file,       "
                + "type,           "
                + "offset,         "
                + "offset_type,    "
                + "table_name,     "
                + "database_src,   "
                + "freq,           "
                + "owner,          "
                + "recall_code,    "
                + "success_code,   "
                + "wait_code,   "
                + "if_wait,   "
                + "if_recall,   "
                + "if_pre,          "
                + "recall_interval,          "
                + "recall_limit,          "
                + "timeout          "
                + "from etl_task_cfg where if_val = ? ";
        return this.jdbcTemplate.queryForList(sql,SystemConstant.TASK_IF_VAL);
    }

    private TaskStatus inputModel(Map<String, Object> map, Date init_date) throws Exception {
        String log_tail = DateUtils.getDay8();
        String cycle = DateUtils.getDay10(init_date);
        String cycle1 = DateUtils.getLastDay10(init_date);

        TaskStatus model = new TaskStatus();
        String cal_dt = null;
        String task_obj = null;
        String para1 = null;
        String para2 = null;
        String para3 = null;
        int status = SystemConstant.JOB_INIT;
        Integer task_id = (Integer) map.get("task_id");
        String task_status_id = DateUtils.generateInstanceID(new Integer(task_id).toString(), (String) map.get("cycle"), init_date);
        String sts_desc = null;
        try {
            cal_dt = DateUtils.get_cal_dt(cycle1, (String) map
                    .get("offset_type"), (String) map.get("offset"));
            String offset_type = (String) map.get("offset_type");
            String offset = (String) map.get("offset");

            task_obj = ParameterUtils.resourceParamHandle(DateUtils.getReplaceCal((String) map.get("task_obj"), offset_type, offset, init_date));

            para1 = (String) map.get("para1");
            if (para1 != null && !para1.trim().equals("")) {
                para1 = ParameterUtils.resourceParamHandle(DateUtils.getReplaceCal(para1, offset_type, offset, init_date))
                        .replace("${task_id}", String.valueOf(task_id))
                        .replace("${instance_id}", task_status_id)
                        .replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
            }
            para2 = (String) map.get("para2");
            if (para2 != null && !para2.trim().equals("")) {
                para2 = DateUtils.getReplaceCal(para2, offset_type, offset, init_date)
                        .replace("${task_id}", String.valueOf(task_id))
                        .replace("${instance_id}", task_status_id)
                        .replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
            }

            para3 = (String) map.get("para3");
            if (para3 != null && !para3.trim().equals("")) {
                para3 = DateUtils.getReplaceCal((String) map.get("para3"), offset_type, offset, init_date)
                        .replace("${task_id}", String.valueOf(task_id))
                        .replace("${instance_id}", task_status_id)
                        .replace("${unix_timestamp}", new Long(init_date.getTime() / 1000).toString());
            }

            sts_desc = "INIT";
        } catch (Exception e) {
            logger.error(task_id + " init error ");
            logger.error(e.getMessage(), e);
            cal_dt = null;
            task_obj = (String) map.get("task_obj");
            sts_desc = "INIT_ERROR";
            status = SystemConstant.JOB_INIT_ERROR;
        }

        String log_home = (String) map.get("log_home");
        String log_file = (String) map.get("log_file");


        Integer task_group_id = (Integer) map.get("task_group_id");
        String task_name = (String) map.get("task_name");
        String owner = (String) map.get("owner");
        String recall_code = (String) map.get("recall_code");
        String wait_code = (String) map.get("wait_code");
        Integer if_recall = (Integer) map.get("if_recall");
        Integer if_wait = (Integer) map.get("if_wait");

        //init recall parameters
        Integer recall_interval = (Integer) map.get("recall_interval");
        Integer recall_limit = (Integer) map.get("recall_limit");

        String log_path =
                ParameterUtils.resourceParamHandle(log_home).concat(File.separator).concat(log_file.trim()).concat(".").concat(task_status_id).concat(".").concat(log_tail);
        String time_id = cycle;
        Integer prio_lvl = (Integer) map.get("prio_lvl");
        Integer run_num = 0;
        Integer recall_num = 0;
        Integer type = (Integer) map.get("type");
        String table_name = (String) map.get("table_name");

        String database_src = (String) map.get("database_src");
        String freq = (String) map.get("freq");
        Integer if_pre = (Integer) map.get("if_pre");
        Integer timeout = (Integer) map.get("timeout");
        String success_code = (String) map.get("success_code");

        model.setTask_status_id(task_status_id);
        model.setTask_id(task_id);

        if (task_group_id != null) {
            model.setTask_group_id(new Integer(task_group_id));
        }

        model.setTask_name(task_name);
        model.setTask_obj(task_obj);
        model.setPara1(para1);
        model.setPara2(para2);
        model.setPara3(para3);
        model.setLog_path(log_path);
        model.setCycle((String) map.get("cycle"));
        model.setTime_id(time_id);
        model.setPrio_lvl(prio_lvl);
        model.setRun_num(run_num);
        model.setType(type);
        model.setTask_name(task_name);
        model.setDatabase_src(database_src);
        model.setTable_name(table_name);
        model.setFreq(freq);
        model.setCal_dt(cal_dt);
        model.setIf_pre(if_pre);
        model.setSts_desc(sts_desc);
        model.setStatus(status);
        model.setRecall_num(recall_num);
        model.setOwner(owner);
        model.setRecall_code(recall_code);
        model.setSuccess_code(success_code);
        model.setIf_recall(if_recall);
        model.setIf_wait(if_wait);
        model.setWait_code(wait_code);
        model.setTimeout(timeout);
        model.setRecall_interval(recall_interval);
        model.setRecall_limit(recall_limit);
        return model;
    }

    private void initTaskSts(TaskStatus model, Long trigger_time) throws Exception {
        String statusSql = "insert into etl_task_status"
                + "  (task_status_id,"
                + "   task_id,       "
                + "   task_group_id, "
                + "   task_name,     "
                + "   task_obj,      "
                + "   para1,         "
                + "   para2,         "
                + "   para3,         "
                + "   log_path,      "
                + "   cycle,         "
                + "   time_id,       "
                + "   status,        "
                + "   prio_lvl,      "
                + "   run_num,       "
                + "   type,    "
                + "   table_name,    "
                + "   cal_dt,    "
                + "   database_src,    "
                + "   if_pre,    "
                + "   if_wait,    "
                + "   if_recall,    "
                + "   sts_desc,    "
                + "   recall_num,    "
                + "   owner,    "
                + "   trigger_time,    "
                + "   recall_code,    "
                + "   success_code,    "
                + "   wait_code,    "
                + "   job_code,    "
                + "   freq,"
                + "   timeout,"
                + "   recall_interval,"
                + "   recall_limit,"
                + "   running_prio,"
                + "   time_stamp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";

        String cal_dt = model.getCal_dt();
        String task_status_id = model.getTask_status_id();
        Integer task_id = model.getTask_id();
        Integer task_group_id = model.getTask_group_id();
        String task_name = model.getTask_name();
        String task_obj = model.getTask_obj();
        String para1 = model.getPara1();
        String para2 = model.getPara2();
        String para3 = model.getPara3();
        String log_path = model.getLog_path();
        String model_cycle = model.getCycle();
        String time_id = model.getTime_id();
        Integer prio_lvl = model.getPrio_lvl();
        Integer run_num = model.getRun_num();
        Integer type = model.getType();
        String table_name = model.getTable_name();
        String database_src = model.getDatabase_src();
        String freq = model.getFreq();
        Integer if_pre = model.getIf_pre();
        String sts_desc = model.getSts_desc();
        Integer recall_num = model.getRecall_num();
        Integer status = model.getStatus();
        String owner = model.getOwner();
        String recall_code = model.getRecall_code();
        Integer if_wait = model.getIf_wait();
        Integer if_recall = model.getIf_recall();
        String wait_code = model.getWait_code();
        String success_code = model.getSuccess_code();
        Integer timeout = model.getTimeout();
        Integer recall_interval = model.getRecall_interval();
        Integer recall_limit = model.getRecall_limit();
        Integer running_prio = model.getRunning_prio();

        this.jdbcTemplate.update(statusSql, new Object[]{task_status_id, task_id, task_group_id, task_name, task_obj,
                para1, para2, para3, log_path, model_cycle, time_id, status, prio_lvl, run_num,
                type, table_name, cal_dt, database_src, if_pre, if_wait,
                if_recall, sts_desc, recall_num, owner, trigger_time, recall_code, success_code,
                wait_code, SystemConstant.REAL_JOB_INIT, freq, timeout, recall_interval, recall_limit, running_prio});

        String sql =
                "  select a.task_status_id,a.task_Id,a.time_id,a.cycle,b.task_pre_id,b.cycle_gap from "
                        + "  etl_task_status a ,etl_taskrela_cfg b where a.task_id =b.task_id and a.task_status_id=? ";

        String relaSql = "insert into etl_taskrela_status(task_status_id,task_id," +
                "pre_id,time_stamp,pre_sts_id)" +
                "values(?,?,?,now(),?)";

        List<Map<String, Object>> list_result = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> map : this.jdbcTemplate.queryForList(sql, new Object[]{model.getTask_status_id()})) {
            Map<String, Object> data_map1 = new HashMap<String, Object>();
            String time_id1 = (String) map.get("time_id");

            data_map1.put("task_status_id", model.getTask_status_id());
            data_map1.put("task_id", model.getTask_id());
            data_map1.put("time_id", time_id1);
            data_map1.put("pre_id", map.get("task_pre_id"));
            data_map1.put("pre_sts_id", DateUtils.generateRelaInstanceID(((Integer) map.get("task_pre_id")).toString(), trigger_time, (String) map.get("cycle_gap")));

            list_result.add(data_map1);
        }
        this.batchInsert(relaSql, list_result, cols_rela);
    }

    public void preInit() {
        logger.info("the init thread starts");
        try {
            this.task_queue = this.getAllTask();
            this.rela_queue = this.getAllTaskRela();

            Date begin = new Date();
            Date end = new Date(begin.getTime() + 1000 * 3600 * 2);

            for (Map<String, Object> map : this.getTaskList()) {
                Date fireTime = begin;
                CronExpression expression = null;
                try {
                    expression = new CronExpression((String) map.get("freq"));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    logger.error(map.get("task_id") + " init error");
                    continue;
                }
                while (true) {
                    fireTime = expression.getNextValidTimeAfter(fireTime);
                    TaskStatus model = null;
                    try {
                        model = this.inputModel(map, fireTime);
                        if (fireTime.getTime() > end.getTime()) {
                            break;
                        }
                        int num = this.getInitTaskNum(model);
                        if (num == 0) {
                            DynamicPriority dp = new DynamicPriority(model.getPrio_lvl());
                            int score = dp.calculateScore(model.getTask_id(), model.getPrio_lvl());
                            model.setRunning_prio(score);
                            this.initTaskSts(model, fireTime.getTime());
                            logger.info(new StringBuilder().append(model.getTask_status_id()).append("(").
                                    append(model.getTask_name()).append(") init successful;").append("score :=")
                                    .append(model.getRunning_prio()).toString());
                        }
                    } catch (Exception e) {
                        logger.error(new StringBuilder().append(model.getTask_status_id()).append("(").append(model.getTask_name()).append(")")
                            .append(" init failure ").toString(),e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("scheduler init error",e);
        } finally {
            logger.info("the init thread ends");
        }

    }

    public void batchInsert(String sql, List<Map<String, Object>> data_list, String[] parmas) {
        final List<Map<String, Object>> data_tmp = data_list;
        final String[] para_tmp = parmas;
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                for (int j = 0; j < para_tmp.length; j++) {
                    ps.setObject(j + 1, data_tmp.get(i).get(para_tmp[j]));
                }
            }
            public int getBatchSize() {
                return data_tmp.size();
            }
        });
    }

    class DynamicPriority {
        DynamicPriority(int level) {
            if (level == 1) {
                point = mid_limit + 1;
                limit = high_limit;
            } else if (level == 2) {
                point = low_limit + 1;
                limit = mid_limit;
            } else {
                point = 1;
                limit = low_limit;
            }

        }

        private double point;
        private int limit;
        private int quotiety = 1;
        private int high_limit = 400;
        private int mid_limit = 200;
        private int low_limit = 50;
        private Integer root_node_id = null;

        private Map<Integer, Integer> taskMap = new HashMap<Integer, Integer>();


        int calculateScore(int task_id, int prio_lvl) {
            if (prio_lvl < 1) {
                return 401;
            }
            if (prio_lvl > 3) {
                return 0;
            }
            root_node_id = task_id;
            this.getTaskMap(task_id);
            for (Integer prio : taskMap.values()) {
                if (prio != null) {
                    point = point + 1d / prio * this.quotiety;
                }
            }
            if (point >= this.limit) {
                return this.limit;
            }
            return new Long(Math.round(point)).intValue();
        }

        private void getTaskMap(int pk) {
            for (Integer[] tmp : rela_queue) {
                if (tmp[1] == pk) {
                    Integer child_id = tmp[0];
                    if (this.root_node_id == child_id) {
                        return;
                    }
                    Integer prio_lvl = task_queue.get(child_id);
                    if (taskMap.containsKey(child_id)) {
                        return;
                    }
                    this.taskMap.put(tmp[0], prio_lvl);
                    this.getTaskMap(child_id);
                }
            }
        }
    }


}
