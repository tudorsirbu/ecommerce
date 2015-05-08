package com.sheffield.ecommerce.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sheffield.ecommerce.models.SessionFactoryUtil;

/**
 * This class is responsible for closing any open database connections when the server is shutdown
 */
public class ServletListener implements ServletContextListener {
	private static final Logger LOGGER = Logger.getLogger(ServletListener.class.getName());
	
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.log(Level.INFO, "== Servlet ending, closing session factory ==");
		SessionFactoryUtil.getSessionFactory().close();
	}

	public void contextInitialized(ServletContextEvent arg0){}

}
