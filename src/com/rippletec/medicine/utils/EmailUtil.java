package com.rippletec.medicine.utils;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailUtil {
    
    public static final String HOST = "112.74.131.194";
    public static  String user_email = "liuyi@ripple.medicine.com";
    public static  String user_name = "liuyi";
    public static  String user_password = "19941213";
    
    public static boolean sendEmail(String email, String content, String subject) {
	JavaMailSenderImpl jSender = new JavaMailSenderImpl();
	jSender.setHost(HOST);
	MimeMessage message = jSender.createMimeMessage();
	try {
	    MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"GB2312");
	    messageHelper.setTo(email);
	    messageHelper.setFrom(user_email);
	    messageHelper.setSubject(subject);
	    // true 表示启动HTML格式的邮件
	    messageHelper.setText(content,true);
	    jSender.setUsername(user_name);
	    jSender.setPassword(user_password);
	    Properties prop = new Properties();
	    prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
	    prop.put("mail.smtp.timeout", "25000");
	    jSender.setJavaMailProperties(prop);
	    jSender.send(message);
	    return true;
	} catch (MessagingException e) {
	    e.printStackTrace();
	    return false;
	}
    }

}
