package com.esc_project.DatabaseConnection;

public class Product {
	
	private String mUid ;
	private String mName;
	private String mPrice;
	
	public Product(String uid, String name, String price) {
		// TODO Auto-generated constructor stub
		this.mUid = uid;
		this.mName = name;
		this.mPrice = price;
	}
	
	public void setUid(String uid) {
		this.mUid = uid;
	}
	
	public void setName(String name) {
		this.mName = name;
	}
	
	public void setPrice(String price) {
		this.mPrice = price;
	}
	
	public String getUid() {
		return mUid;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getPrice() {
		return mPrice;
	}
}
