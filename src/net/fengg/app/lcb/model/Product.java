package net.fengg.app.lcb.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 6287420905418813028L;
	
	private int id;
	private String content;

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
