package com.epam.cdp.jmp.jms.banksystem.context;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.epam.cdp.jmp.jms.banksystem.consumer.AsyncConsumer;

public final class BankSystemContextListener implements ServletContextListener {

	private static AsyncConsumer asyncConsumer = null;
	private Connection connection = null;
	private ServletContext context = null;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			InitialContext initCtx = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) initCtx.lookup("java:comp/env/jms/ConnectionFactory");
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("testQueue");
			asyncConsumer = new AsyncConsumer(destination, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.context = event.getServletContext();
		System.out.println("Context is created.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		this.context = null;
		System.out.println("Context is destroyed.");
	}

}