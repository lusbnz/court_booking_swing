package model;

import java.io.Serializable;

public class MiniCourt  implements Serializable{
	private int id;
	private String name;
	private String type;
	private float price;
	private String desc;
	
	public MiniCourt() {
		super();
	}
	
	public MiniCourt(String name, String type, float price, String desc) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
