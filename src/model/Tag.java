package model;

import java.io.Serializable;

public class Tag implements Serializable{
	
	//private String name, value;
	
	//Changed to public:
	public String name, value;
	
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	
	//Setter methods
	public void setName() {
		this.name = name;
	}
	public void setValue() {
		this.value = value;
	}
	
	//Getter methods
	public  String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return "Name:" +name + " - value: " + value;
	}
	
	public boolean equals(Tag another) {
		if (another == null){
			return false;
		}
		else {
			return name.equals(another.name ) && value.equals(another.value);
		}
	}
}