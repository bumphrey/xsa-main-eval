package com.systemagmbh.xsa.eval;

import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

public class Publisher extends Messaging implements Runnable{

	protected MessageProducer producer;
	
	public Publisher() {
		super();
	}
	
	public void sendMessage() throws Exception { 		 
		
		producer = session.createProducer(destination); 
		producer.setDeliveryMode(DeliveryMode.PERSISTENT); 
		
		
		
		for(int i = 0; i < MAX_MESSAGES; i++) {
		  //send custom message 
		  TextMessage textMessage = session.createTextMessage("My important message nr. " + i);
		  producer.send(textMessage); 
		  System.out.println("sent Message! ->" + textMessage.getText());
		} 
		//close producer, session and connection 
		producer.close(); 
		session.close(); 
		connection.close(); 
	}

	@Override
	public void run() {
		System.out.println("Publisher: sending ");
		try {
			getCredentials();
			setupDestination();
			sendMessage();
		} catch (Exception e) {
			System.err.println(e.getMessage());			
		}		
	} 
}
