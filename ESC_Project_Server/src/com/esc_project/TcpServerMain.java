package com.esc_project;

public class TcpServerMain{
	
	private static TcpServerManager server;
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		server = new TcpServerManager();
		server.start();
	}

}
