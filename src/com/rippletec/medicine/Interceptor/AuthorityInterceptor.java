package com.rippletec.medicine.Interceptor;

import java.io.Writer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rippletec.medicine.model.User;
import com.rippletec.medicine.utils.ErrorCode;
import com.rippletec.medicine.utils.JsonUtil;

/**
 * Spring请求拦截器
 * @author Liuyi
 *
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter{
    
    private int type;
    
    @Resource(name = JsonUtil.NAME)
    private JsonUtil jsonUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler) throws Exception {
	HttpSession session = request.getSession();
	Object typeAttr = session.getAttribute(User.TYPE);
	if(typeAttr == null){
	    Logger.getLogger(AuthorityInterceptor.class)
	    	  .warn("errorCode:"+ErrorCode.ILLEGAL_ACCESS_ERROR+" from："+getIpAddress(request));
	    response.setContentType("application/json;charset=UTF-8");
	    Writer writer =  response.getWriter();
	    writer.append(jsonUtil.setFailRes(ErrorCode.ILLEGAL_ACCESS_ERROR).toJson());
	    writer.flush();
	    writer.close();
	    return false;
	}
	int userType = (int) typeAttr;
	if(userType != type){
	    //指定类型的url，只能有指定类型的用访问
	    Logger.getLogger(AuthorityInterceptor.class)
	    	  .warn("errorCode:"+ErrorCode.PERMISSION_DENIED_ERROR+" from："+getIpAddress(request));
	    response.setContentType("application/json;charset=UTF-8");
	    Writer writer =  response.getWriter();
	    writer.append(jsonUtil.setFailRes(ErrorCode.PERMISSION_DENIED_ERROR).toJson());
	    writer.flush();
	    writer.close();
	    return false;
	}
	return true;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
    } 
    
    

}
