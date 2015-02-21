package com.esc_project;

import com.esc_project.ClientConnection.ClientController;
import com.esc_project.DatabaseConnection.DBController;

public class TcpServerManager {

	ClientController mClientController;
	
	public TcpServerManager() {
		// TODO Auto-generated constructor stub
		mClientController = new ClientController(20);
	}
	
	public void start() {
		mClientController.start();
	}
}
