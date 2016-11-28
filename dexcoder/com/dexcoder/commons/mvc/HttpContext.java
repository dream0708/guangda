package com.dexcoder.commons.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/**
 * Action父类，继承后可以直接使用HttpServlet对象
 * @author LiuJH
 *
 */
@Service
public class HttpContext{
	
	@Resource
	public HttpServletRequest request;	
	
	@Resource
	public HttpServletResponse response;
	
	
}
