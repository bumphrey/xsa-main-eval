package com.systemagmbh.xsa.eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer implements Runnable{

	@Override
	public void run() {
		System.out.println("Running EchoServer");
		
		int portNumber = 4444;
        
        try(
            ServerSocket serverSocket = new ServerSocket(portNumber);
        		
            Socket clientSocket = serverSocket.accept();     
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
           ){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	System.out.println(inputLine);
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.err.println("Exception on port " + portNumber );
            System.err.println(e.getMessage());
        }		
	}

}
