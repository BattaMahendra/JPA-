package com.mahendra.jpal.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Parent {
	
	public Parent() {
		super();
	}
	public Parent(String name, String mobile) {
		super();
		this.parentName = name;
		this.parentMobile = mobile;
	}
	
	
	String parentName;
	String parentMobile;
	
	
	public String getName() {
		return parentName;
	}
	public void setName(String name) {
		this.parentName = name;
	}
	public String getMobile() {
		return parentMobile;
	}
	public void setMobile(String mobile) {
		this.parentMobile = mobile;
	}

}
