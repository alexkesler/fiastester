package org.kesler.fiastester.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.HibernateException;
import java.util.Properties;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.kesler.fiastester.logic.FIASRecord;
import org.kesler.fiastester.util.OptionsUtil;

public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class.getSimpleName());
	private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;


	private static void createSessionFactory() {

		String database = OptionsUtil.getOption("db.driver");
		String userName = OptionsUtil.getOption("db.user");
		String password = OptionsUtil.getOption("db.password");
		String server   = OptionsUtil.getOption("db.server");

		String driverClass = "";
		String connectionUrl = "";
		String dialect = "";



		if (database.equals("h2 local")) { 					///// для локальной базы данных H2
			driverClass = "org.h2.Driver";
			connectionUrl = "jdbc:h2:" + OptionsUtil.getCurrentDir() + "db/fias";
			dialect = "org.hibernate.dialect.H2Dialect";
		} else if (database.equals("h2 net")) { 					///// для сетевой базы данных H2
			driverClass = "org.h2.Driver";
			connectionUrl = "jdbc:h2:tcp://" + server + "/db/appl2";
			dialect = "org.hibernate.dialect.H2Dialect";
		} else if (database.equals("mysql")) { 			///// для базы данных  MySQL
			driverClass = "com.mysql.jdbc.Driver";
			connectionUrl = "jdbc:mysql://" + server + ":3306/appl2";
			dialect = "org.hibernate.dialect.MySQLDialect";
		}

		Properties hibernateProperties = new Properties();
		// hibernateProperties.setProperty("hibernate.connection.driver_class","org.h2.Driver");
		// hibernateProperties.setProperty("hibernate.connection.url","jdbc:h2:mem:test;INIT=create schema if not exists test");
		// hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
		// hibernateProperties.setProperty("hibernate.connection.username","sa");
		// hibernateProperties.setProperty("hibernate.connection.password","");

		hibernateProperties.setProperty("hibernate.connection.driver_class",driverClass);
		hibernateProperties.setProperty("hibernate.connection.url",connectionUrl);
		hibernateProperties.setProperty("hibernate.dialect",dialect);
		hibernateProperties.setProperty("hibernate.connection.username",userName);
		hibernateProperties.setProperty("hibernate.connection.password",password);

		hibernateProperties.setProperty("hibernate.c3p0.minPoolSize","5");
		hibernateProperties.setProperty("hibernate.c3p0.maxPoolSize","20");
		hibernateProperties.setProperty("hibernate.c3p0.timeout","1800");
		hibernateProperties.setProperty("hibernate.c3p0.max_statement","50");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
		hibernateProperties.setProperty("hibernate.show_sql","true");


		Configuration hibernateConfiguration = new Configuration()
						.addAnnotatedClass(FIASRecord.class)
							.setProperties(hibernateProperties);


        serviceRegistry = new ServiceRegistryBuilder().applySettings(hibernateProperties).buildServiceRegistry();

		log.info("Building Hibernate session factory ...");

		try {

			//creates session factory
			sessionFactory = hibernateConfiguration.buildSessionFactory(serviceRegistry);
		} catch (HibernateException he)  {
			log.error("Hibernate session factory create Error", he);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			createSessionFactory();
		}
		return sessionFactory;
	}

	public static void closeConnection() {
		if (sessionFactory != null) {
			sessionFactory.close();
			sessionFactory = null;
		}	
	}
}