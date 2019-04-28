package top.aranlzh.sell.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Aranlzh
 * @create 2019-03-27 19:44
 * @desc 日期转换
 **/
public class DateUtil {
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static Date strToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getStartTime(String date){
        Date d = strToDate(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date startTime = calendar.getTime();
        String startStr = threadLocal.get().format(startTime);
        Date st = null;
        try {
            st = threadLocal.get().parse(startStr);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return st;
    }

    public static Date getEndTime(String date){
        Date d = strToDate(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date endTime = calendar.getTime();
        String endStr = threadLocal.get().format(endTime);
        Date et = null;
        try {
            et = threadLocal.get().parse(endStr);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return et;
    }
}
