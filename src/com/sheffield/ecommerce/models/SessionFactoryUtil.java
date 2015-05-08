package com.sheffield.ecommerce.models;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Manages the hibernate sessions
 */
public class SessionFactoryUtil {

	private static final SessionFactory sessionFactory;

	static {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
    public static SessionFactory getSessionFactory() {
    	return sessionFactory;
    }

}
