package com.esc_project.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
	/**
	 *  - DBController 클래스 - *
	 *  
	 * 1. 데이터베이스를 접속 
	 * 2. 명령어별로 다르게 수행되는 메소드 구현
	 * 3. 명령어별로 적합한 객체 형태로 반환
	 * 
	 * **/

	/** 서버 MySql 정보 **/
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String DBName = "test";
	private final String dbURL = "jdbc:mysql://localhost/"+DBName;
	
	/** DB 테이블 선언 **/
	private Product mProduct;
	
	/** MySql Connection 지역변수 **/
	private Connection con;
	private Statement stmt;
	private ResultSet result;
	
	public DBController() {
		// TODO Auto-generated constructor stub
	}
	
	/** 외부 DB인 MySql에 접속 **/
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
	
	/** 외부 DB인 MySql과 접속 해제 **/
	private void closeDB() {
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Mysql DB Close");
	}
	
	/** 명령어 Constants.Uid_Info의 수행 동작 **/
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
