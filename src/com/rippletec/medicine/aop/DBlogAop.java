package com.rippletec.medicine.aop;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.rippletec.medicine.model.DBLog;
import com.rippletec.medicine.service.DBLoger;

@Component
@Aspect
public class DBlogAop {

    @Resource(name = DBLoger.NAME)
    private DBLoger dbLoger;

    // 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(* update*(..))")
    public void aspectUpdate() {
    }

    @Pointcut("execution(* delete*(..))")
    public void aspectDelete() {
    }

    @Pointcut("execution(* save*(..))")
    public void aspectSave() {
    }
    
    @Pointcut("@target (com.rippletec.medicine.annotation.DBLogModel ) ")
    public void aspectTarget() {
    }

    // 配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspectTarget() && aspectUpdate()")
    public void afterUpdate(JoinPoint joinPoint) {
	System.out.println("invoke update");
	Object[] args = joinPoint.getArgs();
	if (args != null && args.length > 0) {
	    Object arg = args[0];
	    Class argClass = arg.getClass();
	    try {
		Field idField = argClass.getDeclaredField("id");
		idField.setAccessible(true);
		Field tableNameField = argClass.getDeclaredField("TABLE_NAME");
		tableNameField.setAccessible(true);
		int argId = (int) idField.get(arg);
		String tableName = (String) tableNameField.get(arg);
		DBLog log = new DBLog(argId, dbLoger.getVersion(), tableName,
			DBLog.ACTION_UPDATE, new Date());
		dbLoger.uniqueSave(log);
	    } catch (NoSuchFieldException | SecurityException
		    | IllegalArgumentException | IllegalAccessException e) {
		// log待定
		e.printStackTrace();
	    } catch (IOException e) {
		System.out.println("数据库版本配置文件读取失败");
		e.printStackTrace();
	    }

	}
    }

    // 配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspectTarget() && aspectDelete()")
    public void afterDelete(JoinPoint joinPoint) {
	System.out.println("invoke delete");
	Object[] args = joinPoint.getArgs();
	if (args != null && args.length > 0) {
	    int argId = (int) args[0];
	    Object target = joinPoint.getTarget();
	    Class targetClass = target.getClass();
	    try {
		Method method = targetClass.getDeclaredMethod("getPersistClass");
		Class targetObjectClass = (Class) method.invoke(target);
		Field tableNameField = targetObjectClass.getDeclaredField("TABLE_NAME");
		tableNameField.setAccessible(true);
		String tableName = (String) tableNameField.get(null);
		DBLog log = new DBLog(argId, dbLoger.getVersion(), tableName,
			DBLog.ACTION_DELETE, new Date());
		dbLoger.uniqueSave(log);
	    } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
		// log待定
		e.printStackTrace();
	    } catch (IOException e) {
		System.out.println("数据库版本配置文件读取失败");
		e.printStackTrace();
	    }

	}
    }

    // 配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspectTarget() && aspectSave()")
    public void afterSave(JoinPoint joinPoint) {
	System.out.println("invoke save");
	Object[] args = joinPoint.getArgs();
	if (args != null && args.length > 0) {
	    Object arg = args[0];
	    Class argClass = arg.getClass();
	    try {
		Field idField = argClass.getDeclaredField("id");
		idField.setAccessible(true);
		Field tableNameField = argClass.getDeclaredField("TABLE_NAME");
		tableNameField.setAccessible(true);
		int argId = (int) idField.get(arg);
		String tableName = (String) tableNameField.get(arg);
		DBLog log = new DBLog(argId, dbLoger.getVersion(), tableName,
			DBLog.ACTION_SAVE, new Date());
		dbLoger.uniqueSave(log);
	    } catch (NoSuchFieldException | SecurityException
		    | IllegalArgumentException | IllegalAccessException e) {
		// log待定
		e.printStackTrace();
	    } catch (IOException e) {
		System.out.println("数据库版本配置文件读取失败");
		e.printStackTrace();
	    }

	}
    }

}
