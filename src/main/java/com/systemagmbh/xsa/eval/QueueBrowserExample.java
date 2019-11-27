package com.systemagmbh.xsa.eval;

import java.util.Enumeration;
import java.util.Set;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class QueueBrowserExample extends Messaging implements Runnable{
    final static int NUM_MSGS = 10;
	
	@Override
	public void run() {
		System.out.println("QueueBrowser: ");
		try {
			printJMXproperties();
			getCredentials();
			setupDestination();
			browseQueue();
		} catch (Exception e) {
			System.err.println(e.getMessage());			
		}
	}
	
	public void printJMXproperties() {
		String    jmxRemote = System.getProperty("com.sun.management.jmxremote");
		String    localOnly = System.getProperty("com.sun.management.jmxremote.local.only");
		String         port = System.getProperty("com.sun.management.jmxremote.port");
		String      rmiPort = System.getProperty("com.sun.management.jmxremote.rmi.port");
		String authenticate = System.getProperty("com.sun.management.jmxremote.authenticate");
		String          ssl = System.getProperty("com.sun.management.jmxremote.ssl");
		String    hostname = System.getProperty("java.rmi.server.hostname");
		
		System.out.println("jmxremote:"+jmxRemote);
		System.out.println("local.only:"+localOnly);
		System.out.println("port:"+port);
		System.out.println("rmi.port:"+rmiPort);
		System.out.println("authenticate:"+authenticate);
		System.out.println("ssl:"+ssl);
		System.out.println("hostname:"+hostname);
		
	}
	
	
	public void browseQueue() {
		try {
			
			
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);			
			producer.setTimeToLive(300000);
			
			
			
			String task = "Task";
			int i;
			for(i= 0;i<NUM_MSGS;i++) {
				String payload = task + i;
				Message msg = session.createTextMessage(payload);
				System.out.println("Sending text '"+payload+"'");
				producer.send(msg);
			}
									
			System.out.println("Browse through the elements in queue");
			QueueBrowser browser = session.createBrowser((Queue)destination);
			
			Enumeration e = browser.getEnumeration();
			i=0;
			while(e.hasMoreElements()) {
				TextMessage message = (TextMessage) e.nextElement();
				
				System.out.println("Browse ["+ (i++) +"]:"+message.getText());
			}
			
			System.out.println("Done Browsing");
			
			browser.close();
			
			MessageConsumer consumer = session.createConsumer(destination);
			connection.start();
			
			for(i = 0; i<NUM_MSGS;i++) {
				TextMessage textMsg = (TextMessage) consumer.receive();
				System.out.println(textMsg);
				System.out.println("Received: "+ textMsg.getText());
			}
			
			
			consumer.close();
			producer.close();
			session.close();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*
	public void printQueuesAndTopics() {
		ActiveMQConnection amqConnection = (ActiveMQConnection) connection;		
		DestinationSource destinationSource = amqConnection.getDestinationSource();
		
		Set<ActiveMQQueue> queueSet = destinationSource.getQueues();
		
		for(ActiveMQQueue amqQueue : queueSet) {
			System.out.println("qualified queue name="+amqQueue.getQueueName());
			System.out.println("qualified queue name="+amqQueue.getQualifiedName());
			System.out.println("physical queue name="+amqQueue.getPhysicalName());
		}
		
		Set<ActiveMQTopic> topicSet = destinationSource.getTopics();
		for(ActiveMQTopic amqTopic : topicSet) {
			System.out.println("qualified topic name="+amqTopic.getTopicName());
			System.out.println("qualified topic name="+amqTopic.getQualifiedName());
			System.out.println("physical topic name="+amqTopic.getPhysicalName());
		}
	} */

}
