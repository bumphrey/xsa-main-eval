package com.systemagmbh.xsa.eval;

public class Main {
	
	public static void main(String args[]){
		System.out.println("xsa-main-eval Started");
		
		System.out.println("xsa-main-eval number of command line arguments : " + args.length);
		String arg = null;
		for(int i=0; i < args.length; i++) {
			System.out.println("arg["+ i + "] : "+ args[i]);
		}
		
		System.out.println("xsa-main-eval Ending");
	}
	
}