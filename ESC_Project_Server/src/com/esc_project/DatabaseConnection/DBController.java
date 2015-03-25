package com.esc_project.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController {
	/**
	 * - DBController 클래스 - *
	 * 
	 * 1. 데이터베이스를 접속 2. 명령어별로 다르게 수행되는 메소드 구현 3. 명령어별로 적합한 객체 형태로 반환
	 * 
	 * **/

	/** 서버 MySql 정보 **/
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String DBName = "esc";
	private final String dbURL = "jdbc:mysql://localhost/" + DBName;

	/** DB 테이블 선언 **/
	private Product mProduct;
	private Notice notices;

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

		String SQL;

		try {

			connectDB();
			
			SQL = "select * from product T1, position T2 where T1.position_type = T2.position_type AND T1.product_uid=\"" + uid + "\"";
			result = stmt.executeQuery(SQL);
			while (result.next()) {
				mProduct = new Product(result.getString("product_name"),
						result.getString("product_price"), result.getString("product_description"),
						result.getString("product_manufacturer"), result.getString("product_imgurl"), 
						result.getString("position_type"), result.getString("position_x"), result.getString("position_y"));
			}

			closeDB();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mProduct;
	}
	
	public ArrayList<Product> All_Products_Info() {

	
		
		ArrayList<Product> dbData = new ArrayList<Product>();
		String SQL;
		
		try {

			connectDB();
			
			SQL = "select * from product T1, position T2 where T1.position_type = T2.position_type";
			result = stmt.executeQuery(SQL);
			while(result.next()) {
				mProduct = new Product(result.getString("product_name"),
						result.getString("product_price"), result.getString("product_description"),
						result.getString("product_manufacturer"), result.getString("product_imgurl"), 
						result.getString("position_type"), result.getString("position_x"), result.getString("position_y"));
				dbData.add(mProduct);
			}
			
			closeDB();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	
		return dbData;
	}
	
	public ArrayList<Notice> RequestNoticeDB ( ) {
		
		ArrayList<Notice> noticeDB  = new ArrayList<Notice>();
		String SQL;
		
		try {

			connectDB();
			
			SQL = "SELECT * FROM customernotice";
			result = stmt.executeQuery(SQL);
			
			while(result.next()) {
				ArrayList<String> content = new ArrayList<String> ( ) ;
				content.add( result.getString("content") ) ;
				notices = new Notice(
						result.getString("number"),
						result.getString("logo"), 
						result.getString("title"),
						result.getString("date"),
						content);
				
				noticeDB.add(notices);
			}
			
			closeDB();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	
		return noticeDB;
	}
}
