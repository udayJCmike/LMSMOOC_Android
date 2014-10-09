package com.deemsys.lmsmooc;

public class Category {
	String categoryname = null;

	public Category(String category) {
		categoryname = category;

	}

	public String getcategory() {
		return categoryname;
	}

	public void setcategory(String code) {
		this.categoryname = code;
	}

}
