package com.systemagmbh.xsa.eval;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

public class RequestServer extends Messaging implements MessageListener,Runnable {

	MessageProducer replyProducer;
	
	@Override
	public void onMessage(final Message request) {
		try {
			String requestMsg = ((TextMessage) request).getText();
			System.out.println("Received request message: " + requestMsg);
			
			Destination replyDestination = request.getJMSReplyTo();
			
			System.out.println("Reply to queue: " + replyDestination);
			
            TextMessage replyMessage = session.createTextMessage("reply to: "+ requestMsg);
			replyMessage.setJMSCorrelationID(request.getJMSCorrelationID());
			
			replyProducer.send(replyDestination, replyMessage);
			System.out.println("Repy sent:" + replyMessage.getText());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
