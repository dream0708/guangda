package com.global.interceptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.global.log4j.Log;

public class ExceptionInterceptor implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse reponse, Object object, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		// 根据不同错误记录日志信息
		if (ex instanceof NullPointerException) {
			Log.error("【空指针异常】",ex);
		} else if (ex instanceof SQLException) {
			Log.error("【数据库异常】",ex);
		} else if (ex instanceof ClassCastException) {
			Log.error("【类型强制转换错误】",ex);
		} else if (ex instanceof ArrayIndexOutOfBoundsException) {
			Log.error("【数组越界】",ex);
		} else if (ex instanceof StringIndexOutOfBoundsException) {
			Log.error("【字符串索引越界】",ex);
		}else if (ex instanceof IndexOutOfBoundsException) {
			Log.error("【索引越界】",ex);
		} else if (ex instanceof NumberFormatException) {
			Log.error("【字符串转换数字异常】",ex);
		} else if (ex instanceof IOException) {
			Log.error("【输入输出异常】",ex);
		} else if (ex instanceof FileNotFoundException) {
			Log.error("【文件未找到】",ex);
		} else if (ex instanceof ArithmeticException) {
			Log.error("【算术条件异常。譬如：整数除零等。】",ex);
		} else {
			Log.error("【未知错误】",ex);
		}
		
		return new ModelAndView("error/500", model);
		
	}

}
