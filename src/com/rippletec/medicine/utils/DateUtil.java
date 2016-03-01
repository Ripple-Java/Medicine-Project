package com.rippletec.medicine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
    public static final String YYYY_MM_DD_TIME =  "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDTIME =  "yyyyMMddHHmmss";
    public static final String yyyy_MM =  "yyyy-MM";
    public static final String yyyy_MM_DD =  "yyyy-MM-dd";
    

    public static SimpleDateFormat sformat = new SimpleDateFormat();

    public static String getDateTime(Date date){
        sformat.applyPattern(YYYY_MM_DD_TIME);
        return sformat.format(date);
    }
    
    public static String getDate(Date date){
        sformat.applyPattern(yyyy_MM_DD);
        return sformat.format(date);
    }

    public static String getSimpleDateTime(Date date){
        sformat.applyPattern(YYYYMMDDTIME);
        return sformat.format(date);
    }
    
    
    public static String getYearMonth(Date date){
        sformat.applyPattern(yyyy_MM);
        return sformat.format(date);
    }
    
    public static Date getDateTimeDate(String date) {
	 sformat.applyPattern(YYYY_MM_DD_TIME);
	 try {
	    return sformat.parse(date);
	} catch (ParseException e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
    public static Date getYearMonthDate(String date) {
	 sformat.applyPattern(yyyy_MM);
	 try {
	    return sformat.parse(date);
	} catch (ParseException e) {
	    e.printStackTrace();
	    return null;
	}
   }
    
    public static Date getYearMonthDate(Date date) {
	 return getYearMonthDate(getYearMonth(date));
    }

}
