package com.dianping.model;

public class TaskStatus {
    private String task_status_id;
    private int task_id;
    private int task_group_id;
    private String task_name;
    private String task_obj;
    private int running_prio;
    private String para1;
    private String para2;
    private String para3;
    private String log_path;
    private String cycle;
    private String time_id;
    private int status;
    private int prio_lvl;
    private int run_num;
    private int type;
    private String database_src;
    private String table_name;
    private String freq;
    private String cal_dt;
    private int if_pre;
    private String sts_desc;
    private int recall_num;
    private String owner;
    private Long Trigger_time;
    private String recall_code;
    private String success_code;
    private int if_wait;
    private int if_recall;
    private String wait_code;
    private Integer timeout;
    private int recall_limit;
    private int recall_interval;
    private Long inQueueTimeMillis;

    public int getRunning_prio() {
        return running_prio;
    }

    public void setRunning_prio(int running_prio) {
        this.running_prio = running_prio;
    }

    public int getRecall_limit() {
        return recall_limit;
    }

    public void setRecall_limit(int recall_limit) {
        this.recall_limit = recall_limit;
    }

    public int getRecall_interval() {
        return recall_interval;
    }

    public void setRecall_interval(int recall_interval) {
        this.recall_interval = recall_interval;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public int getIf_recall() {
        return if_recall;
    }

    public void setIf_recall(int if_recall) {
        this.if_recall = if_recall;
    }

    public int getIf_wait() {
        return if_wait;
    }

    public void setIf_wait(int if_wait) {
        this.if_wait = if_wait;
    }

    public String getWait_code() {
        return wait_code;
    }

    public void setWait_code(String wait_code) {
        this.wait_code = wait_code;
    }

    public String getSuccess_code() {
        return success_code;
    }

    public void setSuccess_code(String success_code) {
        this.success_code = success_code;
    }

    public String getRecall_code() {
        return recall_code;
    }

    public void setRecall_code(String recall_code) {
        this.recall_code = recall_code;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getTrigger_time() {
        return Trigger_time;
    }

    public void setTrigger_time(Long trigger_time) {
        Trigger_time = trigger_time;
    }

    public int getRecall_num() {
        return recall_num;
    }

    public void setRecall_num(int recall_num) {
        this.recall_num = recall_num;
    }

    public String getSts_desc() {
        return sts_desc;
    }

    public void setSts_desc(String sts_desc) {
        this.sts_desc = sts_desc;
    }

    public int getIf_pre() {
        return if_pre;
    }

    public void setIf_pre(int if_pre) {
        this.if_pre = if_pre;
    }

    public String getCal_dt() {
        return cal_dt;
    }

    public void setCal_dt(String cal_dt) {
        this.cal_dt = cal_dt;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getDatabase_src() {
        return database_src;
    }

    public void setDatabase_src(String database_src) {
        this.database_src = database_src;
    }

    public String getTask_status_id() {
        return task_status_id;
    }

    public void setTask_status_id(String task_status_id) {
        this.task_status_id = task_status_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getTask_group_id() {
        return task_group_id;
    }

    public void setTask_group_id(int task_group_id) {
        this.task_group_id = task_group_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_obj() {
        return task_obj;
    }

    public void setTask_obj(String task_obj) {
        this.task_obj = task_obj;
    }

    public String getPara1() {
        return para1;
    }

    public void setPara1(String para1) {
        this.para1 = para1;
    }

    public String getPara2() {
        return para2;
    }

    public void setPara2(String para2) {
        this.para2 = para2;
    }

    public String getPara3() {
        return para3;
    }

    public void setPara3(String para3) {
        this.para3 = para3;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrio_lvl() {
        return prio_lvl;
    }

    public void setPrio_lvl(int prio_lvl) {
        this.prio_lvl = prio_lvl;
    }

    public int getRun_num() {
        return run_num;
    }

    public void setRun_num(int run_num) {
        this.run_num = run_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getInQueueTimeMillis() {
        return inQueueTimeMillis;
    }

    public void setInQueueTimeMillis(Long inQueueTimeMillis) {
        this.inQueueTimeMillis = inQueueTimeMillis;
    }
}
