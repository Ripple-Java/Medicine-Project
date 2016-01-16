package com.rippletec.medicine.Interceptor;

import java.io.Writer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rippletec.medicine.model.User;
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
	    response.setContentType("application/json;charset=UTF-8");
	    Writer writer =  response.getWriter();
	    writer.append(jsonUtil.setResultFail("用户未登录，无法访问此接口").toJsonString());
	    writer.flush();
	    writer.close();
	    return false;
	}
	int userType = (int) typeAttr;
	if(userType != type){
	    //指定类型的url，只能有指定类型的用访问
	    response.setContentType("application/json;charset=UTF-8");
	    Writer writer =  response.getWriter();
	    writer.append(jsonUtil.setResultFail("用户权限不足！").toJsonString());
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
    
    
    
    

}
