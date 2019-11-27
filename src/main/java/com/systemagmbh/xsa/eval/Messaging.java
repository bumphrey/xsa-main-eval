package com.systemagmbh.xsa.eval;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.qpid.jms.JmsConnectionFactory;

import com.sap.xs.env.Credentials;
import com.sap.xs.env.Service;
import com.sap.xs.env.VcapServices;

public class Messaging implements ExceptionListener{
	
	protected VcapServices services;
	protected Service      activeMQStorage;
	protected Credentials  credentials;
	protected String       user;
	protected String       password;
	protected String       brokerUrl;
	
	protected JmsConnectionFactory connectionFactory;
	protected Connection           connection;
	protected Session              session;
	protected Destination          destination;
	
	protected static int MAX_MESSAGES = 1000;
	
	private static String QUEUE_NAME = "pk.test.queue";
	
	public Messaging() {		
	}
	
	public void setupDestination() throws JMSException {
		//setup jmx connection 
		connectionFactory = new JmsConnectionFactory(brokerUrl);
		connection = connectionFactory.createConnection(user, password);
		connection.start(); 
				 
		// consider setting an exception listener to the connection, 
		// so you could be notified in cases of connectivity problems
		connection.setExceptionListener(this); 
				 
		//create jmx session 
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				 
		//create destination "my.test.queue" that the consumer will read messages from
		destination = session.createQueue(QUEUE_NAME);
	}
	
	public void getCredentials() throws Exception { 
		 
		     services = VcapServices.fromEnvironment(); 
	  activeMQStorage = services.findService("messagingservice", "", ""); 
		  credentials = activeMQStorage.getCredentials(); 
		         user = credentials.getUser(); 
		     password = credentials.getPassword(); 
		     //brokerUrl = "failover:(" + credentials.getUrl() + ")"; 
		     brokerUrl = credentials.getUrl();
		    
		    System.out.println("user: "+user);
		    System.out.println("password: "+password);
		    System.out.println("brokerUrl: "+brokerUrl);
	}

	@Override
	public void onException(JMSException ex) {
		ex.printStackTrace();		
	}
	
}
