package com.esc_project.DatabaseConnection;

public class Product {

	String name;
	String price_now;
	String price_before_one;
	String price_before_two;
	String price_before_three;
	String price_before_six;
	String description;
	String manufacturer;
	String imgURL;
	String type;
	String x;
	String y;
	
	public Product( String name, String price_now, String price_before_one, String price_before_two, String price_before_three,
			String price_before_six, String description, String manufacturer, String imgURL, String type, String x, String y) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price_now = price_now;
		this.price_before_one = price_before_one;
		this.price_before_two = price_before_two;
		this.price_before_three = price_before_three;
		this.price_before_six = price_before_six;
		this.description = description;
		this.manufacturer = manufacturer;
		this.imgURL = imgURL;
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPriceNow(String price_now) {
		this.price_now = price_now;
	}
	
	public void setPriceBeforeOne(String price_before_one) {
		this.price_before_one = price_before_one;
	}
	
	public void setPriceBeforeTwo(String price_before_two) {
		this.price_before_two = price_before_two;
	}
	
	public void setPriceBeforeThree(String price_before_three) {
		this.price_before_three = price_before_three;
	}
	
	public void setPriceBeforeSix(String price_before_six) {
		this.price_before_six = price_before_six;
	}
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public void setManufacturer ( String manufacturer ) {
		this.manufacturer = manufacturer;
	}
	
	public void setImgURL( String imgURL ) {
		this.imgURL = imgURL;
	}
	
	public void setType( String type ) {
		this.type = type;
	}
	
	public void setLocation(String x, String y) { 
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public String getPriceNow() {
		return price_now;
	}
	
	public String getPriceBeforeOne() {
		return price_before_one;
	}
	
	public String getPriceBeforeTwo() {
		return price_before_two;
	}
	
	public String getPriceBeforeThree() {
		return price_before_three;
	}
	
	public String getPriceBeforeSix() {
		return price_before_six;
	}
	
	public String getDescription( ) {
		return this.description;
	}
	
	public String getManufacturer ( ) {
		return this.manufacturer;
	}
	
	public String getImgURL( ) {
		return this.imgURL;
	}

	public String getType( ) { 
		return this.type;
	}
	
	public String getX() { 
		return this.x;
	}
	
	public String getY() { 
		return this.y;
	}
	
}
