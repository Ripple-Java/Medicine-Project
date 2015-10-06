package com.rippletec.medicine.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiCorsFilter implements Filter{

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletResponse response = (HttpServletResponse) res;
	HttpServletRequest request = (HttpServletRequest) req;
	  //允许所有外部资源访问，生产环境指定具体的站点提高安全   
        response.setHeader("Access-Control-Allow-Origin", "*");   
        //允许cookies 
        response.setHeader("Access-Control-Allow-Credentials", "true"); 
        //允许访问的方法类型，多个用逗号分隔   
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");   
        //OPTIONS预请求缓存的有效时间 单位秒   
        response.setHeader("Access-Control-Max-Age", "600");   
        //允许自定义的请求头，多个用逗号分隔   
        response.setHeader("Access-Control-Allow-Headers", "content-type, x-requested-with");
        
        chain.doFilter(req, res); 
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
