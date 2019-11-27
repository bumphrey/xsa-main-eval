package com.systemagmbh.xsa.eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static List<Thread> workerList = new ArrayList<Thread>();
	
	public static void main(String args[]){
		System.out.println("xsa-main-eval Started");
		
		//startSubscriber();
        //startPublisher();    
		startQueueBrowser();  
		
		System.out.println("xsa-main-eval Main Ending");
	}
	
	public static void startPublisher() {
		Runnable publisher = new Publisher();
		Thread workerThread = new Thread(publisher);
		workerList.add(workerThread);
		workerThread.start();		
	}
	public static void startSubscriber() {
		Runnable consumer = new Consumer();
		Thread workerThread = new Thread(consumer);
		workerList.add(workerThread);
		workerThread.start();		
	}		
	
	public static void startQueueBrowser() {
		Runnable queueBrowser = new QueueBrowserExample();
		Thread workerThread = new Thread(queueBrowser);
		workerList.add(workerThread);
		workerThread.start();
	}
	
	public static void startEchoServer() {
		Runnable worker = new EchoServer();
        Thread workerThread = new Thread(worker);
        workerList.add(workerThread);
        // workerThread.setDaemon(true);
        workerThread.start();
	}
	
}