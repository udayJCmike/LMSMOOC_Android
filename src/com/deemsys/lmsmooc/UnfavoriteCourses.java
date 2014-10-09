package com.deemsys.lmsmooc;

public class UnfavoriteCourses {
	String name;
	boolean selected;

	public UnfavoriteCourses(String name, Boolean box) {
		this.name = name;
		selected = box;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
