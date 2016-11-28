package com.global.listener;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

public class ProxoolListener implements ServletContextListener {

	private static final String XML_FILE_PROPERTY = "xmlFile";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("执行了");
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext(); 
		// 对应servlet的init方法中ServletConfig.getServletContext()
		String appDir = contextEvent.getServletContext().getRealPath("/");
		Properties properties = new Properties();
		Enumeration<?> names = context.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = context.getInitParameter(name);
			if (name.equals(XML_FILE_PROPERTY)) {
				try {
					File file = new File(value);
					if (file.isAbsolute()) {
						JAXPConfigurator.configure(value, false);
					} else {
						JAXPConfigurator.configure(appDir + File.separator+ value, false);
					}
				} catch (ProxoolException e) {
					e.printStackTrace();
				}
			}
		}
		if (properties.size() > 0) {
			try {
				PropertyConfigurator.configure(properties);
				System.out.println("------------------");
			} catch (ProxoolException e) {
				e.printStackTrace();
			}
		}
	}

}
