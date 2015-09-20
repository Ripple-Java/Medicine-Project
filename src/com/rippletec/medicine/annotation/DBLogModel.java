package com.rippletec.medicine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author Liuyi
 * 开启DBloger注解标记
 * 此注解标记的类中update(),save(),delete()方法操作将自动记录到db_log表中
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBLogModel {

}
