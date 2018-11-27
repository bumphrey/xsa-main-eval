package com.systemagmbh.xsa.eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	public static EchoServer worker = null;
	
	public static void main(String args[]){
		System.out.println("xsa-main-eval Started");
		
        worker = new EchoServer();
        Thread workerTh = new Thread(worker);
        // workerTh.setDaemon(true);
        workerTh.start();
		
		System.out.println("xsa-main-eval Main Ending");
	}
	
}