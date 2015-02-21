package com.esc_project.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
	/**
	 *  - DBController Ŭ���� - *
	 *  
	 * 1. �����ͺ��̽��� ���� 
	 * 2. ��ɾ�� �ٸ��� ����Ǵ� �޼ҵ� ����
	 * 3. ��ɾ�� ������ ��ü ���·� ��ȯ
	 * 
	 * **/

	/** ���� MySql ���� **/
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String DBName = "test";
	private final String dbURL = "jdbc:mysql://localhost/"+DBName;
	
	/** DB ���̺� ���� **/
	private Product mProduct;
	
	/** MySql Connection �������� **/
	private Connection con;
	private Statement stmt;
	private ResultSet result;
	
	public DBController() {
		// TODO Auto-generated constructor stub
	}
	
	/** �ܺ� DB�� MySql�� ���� **/
	private void connectDB() {
		
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(dbURL, "root", "apmsetup");
			stmt = con.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Mysql DB Connection");
	}
	
	/** �ܺ� DB�� MySql�� ���� ���� **/
	private void closeDB() {
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Mysql DB Close");
	}
	
	/** ��ɾ� Constants.Uid_Info�� ���� ���� **/
	public Product Uid_Info(String uid) {
		
		String SQL ;
		
		try {
			
			connectDB();
			
			SQL = "select * from product where uid=\"" + uid + "\"";
			result = stmt.executeQuery(SQL);
			while(result.next()) {
				mProduct = new Product(result.getString("uid"), result.getString("name"), result.getString("price"));
			}
			
			closeDB();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mProduct;
	}
	
}
