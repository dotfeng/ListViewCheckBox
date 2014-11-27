package net.fengg.app.lcb.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 6287420905418813028L;
	
	private int id;
	private String content;
	private double price;
	private int quantity;
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
