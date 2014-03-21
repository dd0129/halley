package com.dianping.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date string2Date(String s) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sdf.parse(s);
    }

    public static String Date2String(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMddHHmmssSSS");
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return sdf.format(c.getTime());
    }


    public static String getAppointDay(String time_id, String type, int gap) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = sdf.parse(time_id);
        c.setTime(date);
        //c.add(Calendar.DAY_OF_MONTH, -1);
        if (type.equals("D")) {
            c.add(Calendar.DAY_OF_MONTH, -gap);
            return sdf.format(c.getTime());
        } else if (type.equals("M")) {
            c.add(Calendar.MONDAY, -gap);
            return sdf.format(c.getTime());
        } else if (type.equals("H")) {
            c.add(Calendar.HOUR, -gap);
            return sdf.format(c.getTime());
        } else {
            return null;
        }
    }

    public static String getLastDay8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(c.getTime());
    }

    public static String getLastDay10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(c.getTime());
    }

    public static String getDay8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    public static String getDay10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    public static String getLastMonth7() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime());
    }

    public static String getLastMonth7M() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy#MM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime()).replace("#", "M");
    }

    public static String getMonth7(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy#MM");
        return sdf.format(d).replace("#", "M");
    }

    public static String getDay10(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(d);
    }

    public static String getDay8(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        return sdf.format(d);
    }

    public static String getLastDay10(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(new Date(d.getTime() - 86400 * 1000));
    }

    public static String getLastDay8(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        return sdf.format(new Date(d.getTime() - 86400 * 1000));
    }

    public static String getFistDayMonth8() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }

    public static String getFistDayLastMonth10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getLastDayLastMonth10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getFirstDayLastMonth10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getLastDayLastMonth8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getFirstDayLastMonth10(String time_id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = sdf.parse(time_id);
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getLastDayLastMonth10(String time_id) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Date date = sdf.parse(time_id);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    public static String getFistDayLastMonth8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(cal.getTime());
    }

    public static String getNextDay10(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            sdf.applyPattern("yyyy-MM-dd");
            Date d = sdf.parse(day);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            c.add(Calendar.DATE, 1);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFirstDayWeek10() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return sdf.format(cal.getTime());
    }

    public static String getFirstDayWeek8() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return sdf.format(cal.getTime());
    }


    public static String get_cal_dt(String time_id, String offset_type, String offset) throws Exception {
        if (offset_type.equals("offset")) {
            String type1 = new String(offset);
            String type2 = type1.substring(0, 1);
            int gap = new Integer(type1.substring(1, type1.length()));
            if (gap >= 1000) {
                throw new Exception("the cal_dt is more than 1000");
            }
            return DateUtils.getAppointDay(time_id, type2, gap);
        } else if (offset_type.equals("appoint")) {
            if (offset.equals("${LYYYY-MM-01}")) {
                return DateUtils.getFirstDayLastMonth10(time_id);
            } else if (offset.equals("${LYYYY-MM-31}")) {
                return DateUtils.getLastDayLastMonth10(time_id);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getNCal_dt(String cal_dt, String pattern) {

        if (pattern.equals("${ncal_dt}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public static String getEndDayThisMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${end_day_this_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DAY_OF_MONTH, -1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getEndDayThisMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${end_day_this_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DAY_OF_MONTH, -1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastWeek8(String cal_dt, String pattern) {
        if (pattern.equals("${last_week8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -6);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastWeek10(String cal_dt, String pattern) {
        if (pattern.equals("${last_week10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, -6);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayThisMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_this_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getCal_dt8(String cal_dt, String pattern) {
        if (pattern.equals("${cal_dt8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getNCal_dt8(String cal_dt, String pattern) {
        if (pattern.equals("${ncal_dt8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayThisMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_this_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayLastMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_last_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getFirstDayLastMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${first_day_last_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getNdays_cal_dt(String cal_dt, String pattern) {
        //if(pattern.equals("${Ndays_cal_dt}")){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date date = sdf.parse(cal_dt);
            c.setTime(date);
            String s = pattern.replace("${", "").replace("days_cal_dt}", "");
            Integer interval = new Integer(pattern.replace("${", "").replace("days_cal_dt}", ""));
            c.add(Calendar.DAY_OF_MONTH, -interval);
            return sdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //}
        //return null;
    }

    public static String getLastDayLastMonth8(String cal_dt, String pattern) {
        if (pattern.equals("${last_day_last_month8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DATE, -1);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getLastDayLastMonth10(String cal_dt, String pattern) {
        if (pattern.equals("${last_day_last_month10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.DATE, -1);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getThisHour(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    public static String getMonNextWeek8(String cal_dt, String pattern) {
        if (pattern.equals("${monday_next_week8}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return sdf.format(c.getTime()).replace("-", "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getMonNextWeek10(String cal_dt, String pattern) {
        if (pattern.equals("${monday_next_week10}")) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = sdf.parse(cal_dt);
                c.setTime(date);
                c.add(Calendar.WEEK_OF_YEAR, 1);
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return sdf.format(c.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            System.out.println(DateUtils.getReplaceCal(" \"@{cal_dt}=${cal_dt}  @{ncal_dt}=${30days_cal_dt}\"", "offset", "D0", new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getReplaceCal(String para, String cal_dt) {
        if (para == null || para.trim().equals("")) {
            return null;
        }
        String ncal_dt = DateUtils.getNCal_dt(cal_dt, "${ncal_dt}");
        String cal_dt8 = DateUtils.getCal_dt8(cal_dt, "${cal_dt8}");
        String ncal_dt8 = DateUtils.getNCal_dt8(cal_dt, "${ncal_dt8}");

        String last_week8 = DateUtils.getLastWeek8(cal_dt, "${last_week8}");
        String last_week10 = DateUtils.getLastWeek10(cal_dt, "${last_week10}");

        String end_day_last_month8 = DateUtils.getEndDayThisMonth8(cal_dt, "${end_day_this_month8}");
        String end_day_last_month10 = DateUtils.getEndDayThisMonth10(cal_dt, "${end_day_this_month10}");

        String mon_next_week8 = DateUtils.getMonNextWeek8(cal_dt, "${monday_next_week8}");
        String mon_next_week10 = DateUtils.getMonNextWeek10(cal_dt, "${monday_next_week10}");

        String first_day_this_month8 = DateUtils.getFirstDayThisMonth8(cal_dt, "${first_day_this_month8}");
        String first_day_this_month10 = DateUtils.getFirstDayThisMonth10(cal_dt, "${first_day_this_month10}");

        String first_day_last_month8 = DateUtils.getFirstDayLastMonth8(cal_dt, "${first_day_last_month8}");
        String first_day_last_month10 = DateUtils.getFirstDayLastMonth10(cal_dt, "${first_day_last_month10}");

        String last_day_last_month8 = DateUtils.getLastDayLastMonth8(cal_dt, "${last_day_last_month8}");
        String last_day_last_month10 = DateUtils.getLastDayLastMonth10(cal_dt, "${last_day_last_month10}");

        return para.replace("${cal_dt}", cal_dt)
                .replace("${ncal_dt}", ncal_dt)
                .replace("${cal_dt8}", cal_dt8)
                .replace("${ncal_dt8}", ncal_dt8)
                .replace("${last_week8}", last_week8)
                .replace("${last_week10}", last_week10)
                .replace("${monday_next_week8}", mon_next_week8)
                .replace("${monday_next_week10}", mon_next_week10)
                .replace("${end_day_this_month8}", end_day_last_month8)
                .replace("${end_day_this_month10}", end_day_last_month10)
                .replace("${first_day_this_month8}", first_day_this_month8)
                .replace("${first_day_this_month10}", first_day_this_month10)
                .replace("${first_day_last_month8}", first_day_last_month8)
                .replace("${first_day_last_month10}", first_day_last_month10)
                .replace("${last_day_last_month8}", last_day_last_month8)
                .replace("${last_day_last_month10}", last_day_last_month10)
                .replace("${30days_cal_dt}", DateUtils.getNdays_cal_dt(cal_dt, "${30days_cal_dt}"));

    }

    public static String getReplaceCal(String para, String offset_type, String offset, Date init_date) throws Exception {
        String cal_dt = DateUtils.get_cal_dt(DateUtils.getLastDay10(init_date), offset_type, offset);
        if (para == null || para.trim().equals("")) {
            return null;
        }
        String ncal_dt = DateUtils.getNCal_dt(cal_dt, "${ncal_dt}");
        String cal_dt8 = DateUtils.getCal_dt8(cal_dt, "${cal_dt8}");
        String ncal_dt8 = DateUtils.getNCal_dt8(cal_dt, "${ncal_dt8}");

        String last_week8 = DateUtils.getLastWeek8(cal_dt, "${last_week8}");
        String last_week10 = DateUtils.getLastWeek10(cal_dt, "${last_week10}");

        String end_day_last_month8 = DateUtils.getEndDayThisMonth8(cal_dt, "${end_day_this_month8}");
        String end_day_last_month10 = DateUtils.getEndDayThisMonth10(cal_dt, "${end_day_this_month10}");

        String mon_next_week8 = DateUtils.getMonNextWeek8(cal_dt, "${monday_next_week8}");
        String mon_next_week10 = DateUtils.getMonNextWeek10(cal_dt, "${monday_next_week10}");

        String first_day_this_month8 = DateUtils.getFirstDayThisMonth8(cal_dt, "${first_day_this_month8}");
        String first_day_this_month10 = DateUtils.getFirstDayThisMonth10(cal_dt, "${first_day_this_month10}");

        String first_day_last_month8 = DateUtils.getFirstDayLastMonth8(cal_dt, "${first_day_last_month8}");
        String first_day_last_month10 = DateUtils.getFirstDayLastMonth10(cal_dt, "${first_day_last_month10}");

        String last_day_last_month8 = DateUtils.getLastDayLastMonth8(cal_dt, "${last_day_last_month8}");
        String last_day_last_month10 = DateUtils.getLastDayLastMonth10(cal_dt, "${last_day_last_month10}");

        String this_hour = DateUtils.getThisHour(init_date);

        String Ndays_cal_dt = DateUtils.getNdays_cal_dt(cal_dt, "${30days_cal_dt}");

        return para.replace("${cal_dt}", cal_dt)
                .replace("${ncal_dt}", ncal_dt)
                .replace("${cal_dt8}", cal_dt8)
                .replace("${ncal_dt8}", ncal_dt8)
                .replace("${last_week8}", last_week8)
                .replace("${last_week10}", last_week10)
                .replace("${monday_next_week8}", mon_next_week8)
                .replace("${monday_next_week10}", mon_next_week10)
                .replace("${end_day_this_month8}", end_day_last_month8)
                .replace("${end_day_this_month10}", end_day_last_month10)
                .replace("${first_day_this_month8}", first_day_this_month8)
                .replace("${first_day_this_month10}", first_day_this_month10)
                .replace("${first_day_last_month8}", first_day_last_month8)
                .replace("${first_day_last_month10}", first_day_last_month10)
                .replace("${last_day_last_month8}", last_day_last_month8)
                .replace("${last_day_last_month10}", last_day_last_month10)
                .replace("${this_hour}", this_hour)
                .replace("${30days_cal_dt}", Ndays_cal_dt);

    }

    public static String get_gap_dt(String offset, String time_id) throws Exception {
        String type1 = new String(offset);
        String type2 = type1.substring(0, 1);
        int gap = new Integer(type1.substring(1, type1.length()));
        if (gap >= 1000) {
            throw new Exception();
        }
        return DateUtils.getAppointDay(time_id, type2, gap);
    }


    public static String getPre_sts_id(String task_sts_id, String task_type, String pre_id, String gap) throws Exception {
        String type = gap.substring(0, 1);
        int interval = new Integer(gap.substring(1));

        String str_date = null;
        if (task_type.equals("mi")) {
            str_date = task_sts_id.substring(task_sts_id.length() - 12, task_sts_id.length() - 2);
        } else {
            str_date = task_sts_id.substring(task_sts_id.length() - 10);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Date d = sdf.parse(str_date);

        if (type.equals("H")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.HOUR, -1 * interval);

            String pre_str_date = sdf.format(cal.getTime());

            return pre_id + pre_str_date;
        } else if (type.equals("D")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE, -1 * interval);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("M")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM0100");
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MONTH, -1 * interval);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("W")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.WEEK_OF_YEAR, -1 * interval);
            cal.set(Calendar.DAY_OF_WEEK, 2);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else {
            throw new Exception("error input cycle type " + type);
        }

    }

    public static String generateInstanceID(String task_id, String type, Date init_date) throws Exception {
        if (type.equals("H")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            return task_id + sdf.format(init_date);
        } else if (type.equals("D")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd00");
            return task_id + sdf.format(init_date);
        } else if (type.equals("W")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMWW00");
            return task_id + sdf.format(init_date);
        } else if (type.equals("M")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM0100");
            return task_id + sdf.format(init_date);
        } else if (type.equals("mi")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            return task_id + sdf.format(init_date);
        } else {
            throw new Exception("error input cycle type " + type);
        }
    }

    @Deprecated
    public static String generateInstanceID_bak(String task_id, String type, Date init_date) throws Exception {
        if (type.equals("H")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            return task_id + sdf.format(init_date);
        } else if (type.equals("D")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd00");
            return task_id + sdf.format(init_date);
        } else if (type.equals("W")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMWW00");
            return task_id + sdf.format(init_date);
        } else if (type.equals("M")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM0100");
            return task_id + sdf.format(init_date);
        } else if (type.equals("mi")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            return task_id + sdf.format(init_date);
        } else {
            throw new Exception("error input cycle type " + type);
        }
    }

    public static String generateRelaInstanceID(String pre_id, Long fire_time, String gap) throws Exception {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(fire_time);
        Date date = cal1.getTime();

        String type = gap.substring(0, 1);
        int interval = new Integer(gap.substring(1));

        if (type.equals("H")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, -1 * interval);

            String pre_str_date = sdf.format(cal.getTime());

            return pre_id + pre_str_date;
        } else if (type.equals("D")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -1 * interval);

            String pre_str_date = sdf.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("M")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM0100");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1 * interval);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else if (type.equals("W")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMWW00");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.WEEK_OF_YEAR, -1 * interval);
            //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            String pre_str_date = sdf1.format(cal.getTime());
            return pre_id + pre_str_date;
        } else {
            throw new Exception("error input cycle type " + type);
        }

    }

}
