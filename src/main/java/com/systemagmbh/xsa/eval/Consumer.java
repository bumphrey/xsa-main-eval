package com.systemagmbh.xsa.eval;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;

public class Consumer extends Messaging implements Runnable{

	public Consumer() {
		super();
	}
	
	public void consumMessage() throws JMSException {
		
		MessageConsumer consumer = session.createConsumer(destination); 
		
		for(int i = 0; i < MAX_MESSAGES; i++) {
		  //consumer starts to receive messages for the given time period - 1000ms
		  Message message = consumer.receive(1000L);
		  if (message != null) { 
		    System.out.println("received message! -> " + (message instanceof TextMessage ? ((TextMessage) message).getText() : String.valueOf(message))); 
		  } 
		}
		//close consumer, session and connection 
		consumer.close(); 
		session.close(); 
		connection.close(); 				
	}

	@Override
	public void run() {
		System.out.println("Consumer: reading ");
		try {
			getCredentials();
			setupDestination();
			consumMessage();
		} catch (Exception e) {
			System.err.println(e.getMessage());			
		}
	}
	
}
