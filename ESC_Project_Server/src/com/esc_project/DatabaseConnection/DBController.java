package com.esc_project.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.esc_project.Recommender.ItemBasedRecommender;
import com.esc_project.Recommender.RecommendProduct;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;

public class DBController {
	/**
	 * - DBController Ŭ���� - *
	 * 
	 * 1. �����ͺ��̽��� ���� 2. ��ɾ�� �ٸ��� ����Ǵ� �޼ҵ� ���� 3. ��ɾ�� ������ ��ü ���·� ��ȯ
	 * 
	 * **/

	/** ���� MySql ���� **/
	private final String driverName = "com.mysql.jdbc.Driver";
	private final String DBName = "esc";
	private final String dbURL = "jdbc:mysql://localhost/" + DBName + "?useUnicode=true&characterEncoding=euckr";

	/** DB ���̺� ���� **/
	private Product mProduct;
	private Notice notices;
	private QuestionAndAnswer questionAndAnswers;
	private RecommendProduct mRecommendProduct;

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

		String SQL;

		try {

			connectDB();
			
			SQL = "select * from product T1, position T2 where T1.position_type = T2.position_type AND T1.product_uid='" + uid + "'";
			result = stmt.executeQuery(SQL);
			
			/** product ��ü �ʱ�ȭ! **/
			//mProduct.InitializeProduct();
			
			while (result.next()) {
				
				mProduct = new Product(
						result.getInt("product_number"),
						result.getString("product_name"),
						result.getString("product_price_now"), 
						result.getString("product_price_before_one"), 
						result.getString("product_price_before_two"),
						result.getString("product_price_before_three"), 
						result.getString("product_price_before_four"), 
						result.getString("product_price_before_five"),
						result.getString("product_price_before_six"), 
						result.getString("product_score"), 
						result.getString("product_description"), 
						result.getString("product_manufacturer"), 
						result.getString("product_imgurl"), 
						result.getString("position_type"), 
						result.getString("position_x"), 
						result.getString("position_y"));
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
				mProduct = new Product(result.getInt("product_number"),
						result.getString("product_name"),
						result.getString("product_price_now"), result.getString("product_price_before_one"), result.getString("product_price_before_two"),
						result.getString("product_price_before_three"), result.getString("product_price_before_four"), result.getString("product_price_before_five"),
						result.getString("product_price_before_six"), result.getString("product_score"), result.getString("product_description"), 
						result.getString("product_manufacturer"), result.getString("product_imgurl"), result.getString("position_type"), 
						result.getString("position_x"), result.getString("position_y"));
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
				notices = new Notice(
						result.getString("number"),
						result.getString("logo"), 
						result.getString("title"),
						result.getString("date"),
						result.getString("content") );
				
				noticeDB.add(notices);
			}
			
			closeDB();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	
		return noticeDB;
	}
	
	public ArrayList<QuestionAndAnswer> RequestQuestionAndAnswer( String questionType ) { 
		ArrayList<QuestionAndAnswer> questionAndAnswerDB = new ArrayList<QuestionAndAnswer> ( ) ;
		String SQL;
		
		SQL = "SELECT * FROM `esc`.`questiontype`  WHERE `questiontype` = '"+questionType +"'" ; 
		
		try {
			
			connectDB( );
			result = stmt.executeQuery(SQL);
			
			while ( result.next() ) {
				questionAndAnswers = new QuestionAndAnswer ( 
						result.getString("questionTitle"), 
						result.getString("answercontent") );
				
				questionAndAnswerDB.add(questionAndAnswers);
			}
			closeDB( );
 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return questionAndAnswerDB;
		
	}
	
	public ArrayList<RecommendProduct> RecommendedProduct_Info(int productNumber) {
		
		ArrayList<RecommendProduct> recommendedProducts = new ArrayList<RecommendProduct>();
		String SQL;
		
		//1.1 ��ǰ ��ȣ�� Long������ ��ȯ�Ѵ�.
		Long prdouctLongNumber = new Long(productNumber);
		
		//2. ��õ�⸦ �����Ѵ�.
		ItemBasedRecommender itemBasedRecommender = new ItemBasedRecommender();
		
		//3. 5���� ��õ ��ǰ�� ��ȯ�޴´�.
		List<RecommendedItem>recommendedItems = itemBasedRecommender.Recommender(prdouctLongNumber);
		
		
		try {

			connectDB();
			
			for(int i=0 ; i<recommendedItems.size() ; i++) {
				
				SQL = "select product_imgurl, product_name, product_price_now from product where product_number=" + String.valueOf(recommendedItems.get(i).getItemID());
				result = stmt.executeQuery(SQL);
				
				
				while(result.next()) {
					mRecommendProduct = new RecommendProduct(
							result.getString("product_imgurl"), 
							result.getString("product_name"),
							result.getString("product_price_now"));
					
					recommendedProducts.add(mRecommendProduct);
				}
			}
			
			closeDB();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return recommendedProducts;
	}
	
	public void CustomerCart_Info_STORE(ArrayList<String> numberList) {
		String SQL;

		try {

			connectDB();

			SQL = "DELETE FROM customercart;";
			stmt.executeUpdate(SQL);

			for (int i = 0; i < numberList.size(); i++) {
				
				SQL = "SELECT `product_number` , `product_name` , `product_price_now` , `product_manufacturer` , `position_type` FROM `product`  WHERE `product_number` =" + numberList.get(i) + "";
				result = stmt.executeQuery(SQL);
				
				while (result.next()) {
					SQL = "INSERT INTO `esc`.`customercart` (`customercart_number` ,`customercart_name` ,`customercart_price` ,`customercart_count`, `customercart_manufacturer` ,`customercart_type` ) VALUES ( '"
							+ result.getInt("product_number")
							+ "', '"
							+ result.getString("product_name")
							+ "', '"
							+ result.getString("product_price_now")
							+ "', '1', '"
							+ result.getString("product_manufacturer")
							+ "', '"
							+ result.getString("position_type") + "');";
				}
				
				stmt.executeUpdate(SQL);

			}

			closeDB();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}
