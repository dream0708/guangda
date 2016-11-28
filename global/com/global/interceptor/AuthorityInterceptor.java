package com.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			Authority authPassport = ((HandlerMethod) handler).getMethodAnnotation(Authority.class);
			// 没有声明需要权限,或者声明不验证权限
			if (authPassport == null || authPassport.validateUser() == false)
				return true;
			else {
				String name = "zhangsan";
				// 在这里实现自己的权限验证逻辑
				if (name.equals("zhangsan")){
					return true;
				}else{
					response.sendRedirect("account/login");
					return false;
				}
			}
		} else{
			return true;
		}
	}

}
