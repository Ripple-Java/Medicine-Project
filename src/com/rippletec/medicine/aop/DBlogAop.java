package com.rippletec.medicine.aop;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.mysql.fabric.xmlrpc.base.Data;
import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.service.DBLoger;

@Component
@Aspect
public class DBlogAop {

    @Resource(name = DBLoger.NAME)
    private DBLoger dbLoger;

    // 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("@annotation(DBlogUpdate)")
    public void aspectUpdate() {
    }
    @Pointcut("@annotation(DBlogDelete)")
    public void aspectDelete() {
    }
    @Pointcut("@annotation(DBlogSave)")
    public void aspectSave() {
    }

    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
     */
    @Before("")
    public void before(JoinPoint joinPoint) {

    }

    // 配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspectUpdate()")
    public void afterUpdate(JoinPoint joinPoint) {
	Object[] args = joinPoint.getArgs();
	if(args != null && args.length >0 ){
	    Object arg = args[0];
	    Class argClass = arg.getClass();
	    try {
		Field idField = argClass.getDeclaredField("id");
		Field tableNameField = argClass.getDeclaredField("TABLE_NAME");
		int argId = (int) idField.get(arg);
		String tableName = (String) tableNameField.get(arg);
		DBLog log = new DBLog(argId, DBLoger.getVersion(), tableName, DBLog.ACTION_UPDATE, new Data());
		dbLoger.save(log);
	    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
		//log待定
		e.printStackTrace();
	    }
	    
	}
    }

}
