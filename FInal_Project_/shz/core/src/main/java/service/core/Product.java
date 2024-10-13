package service.core;

import java.io.Serializable;

/*
 * Class to store the products returned by the financial product services
 */
public class Product implements Serializable {
	public Product(int id, String name, int term, double interestRate, String description, double recommendedInverstment) {
		this.id = id;
		this.name = name; 
		this.term = term; 
		this.interestRate = interestRate; 
		this.description = description;
		this.recommendedInverstment = recommendedInverstment; 
	}
	public Product(int id, String name, int term, double interestRate, double recommendedInverstment) {
		this.id = id;
		this.name = name;
		this.term = term;
		this.interestRate = interestRate;
		this.recommendedInverstment = recommendedInverstment;
	}

	public Product() {}

	public int term, id;
	public String name, description;
	public double interestRate, recommendedInverstment;

	public int getProductID(){
		return id;
	}
	public String getProductName(){
		return name;
	}

	public double getInverstment(){
		return recommendedInverstment;
	}
}
