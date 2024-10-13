package service.core;

import service.core.Product;

import java.io.Serializable;

/**
 * Data Class that contains client information
 */
public class ClientInfo implements Serializable{
	public ClientInfo() {
	}
	public ClientInfo(int id, String name, int age, int creditScore, double balance) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.creditScore = creditScore;
		this.balance = balance;
	}
	public ClientInfo(int id, String name, int age){
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public ClientInfo(int id, int age, Product product) {
		this.id = id;
		this.age = age;
		this.product = product;
	}

	public ClientInfo(int id, int creditScore, double balance){
		this.id = id;
		this.creditScore = creditScore;
		this.balance = balance;
	}
	public ClientInfo(String name, String password, int age){
		this.name = name;
		this.password = password;
		this.age = age;
	}
	public String password;
	public Product product = null;



	public String name = null;

	public int age;
	public int id;
	public int creditScore = 0;



	public double balance = 0.0;

	public void setBalance(double balance){
		this.balance = balance;
	}
	public double getBalance() {return balance;}


	public String getName() {return name;}

	public void setName(String name) {this.name = name;}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getUserId(){
		return id;
	}
	public void setUserId(int id){this.id = id;}

	public void setCreditScore(int creditScore){
		this.creditScore = creditScore;
	}
	public int getCreditScore(){return creditScore;}
}



//package service.core;
//
//import java.io.Serializable;
//import service.core.Product;
//
//public class ClientInfo implements Serializable{
//
//	public ClientInfo(int id, String name, int age, int creditScore, double balance) {
//		this.id = id;
//		this.name = name;
//		this.age = age;
//		this.creditScore = creditScore;
//		this.balance = balance;
//	}
//	public ClientInfo(int id, String name, int age){
//		this.id = id;
//		this.name = name;
//		this.age = age;
//	}
//	public ClientInfo(int id, int age, Product product) {
//		this.id = id;
//		this.age = age;
//		this.product = product;
//	}
//
//	public ClientInfo(int id, int creditScore, double balance){
//		this.id = id;
//		this.creditScore = creditScore;
//		this.balance = balance;
//	}
//	public Product product = null;
//	public String name = null;
//	public int age = 0;
//	public int id = 0;
//	public int creditScore = 0;
//	public double balance = 0.0;
//}
