package com.deemsys.lmsmooc;

import android.graphics.Bitmap;

public class Course {
	String course_id = null;
	String bittu;
	 String instructor_id = null;
 String course = null;
 String name = null;
 String continent = null;
 String cost = null;
 Double lifeExpectancy = null;
 Double gnp = null;
 Double surfaceArea = null;
 int population = 0;
 Bitmap bit;
 String rating;
 String course_description;
//  
// public Course(String authorname, String course_name,String costw, String course_id2, String instructorid, Bitmap bitmap) {
//	// TODO Auto-generated constructor stub
//	 course_id=course_id2;
//	 instructor_id=instructorid;
//	 course=course_name;
//	 name=authorname;
//	 cost=costw;
//	 bit=bitmap;
//}
// public Course(String authorname, String course_name,String costw, String course_id2, String instructorid) {
//	// TODO Auto-generated constructor stub
//	 course_id=course_id2;
//	 instructor_id=instructorid;
//	 course=course_name;
//	 name=authorname;
//	 cost=costw;
//	
//}
 public Course(String authorname, String course_name,String costw, String course_id2, String instructorid, String bitmap,String rat) {
		// TODO Auto-generated constructor stub
		 course_id=course_id2;
		 instructor_id=instructorid;
		 course=course_name;
		 name=authorname;
		 cost=costw;
		 bittu=bitmap;
		 rating=rat;
		 
	}
 public Course(String authorname, String course_name,String costw, String course_id2, String instructorid, String bitmap,String rat,String coures_desp) {
		// TODO Auto-generated constructor stub
		 course_id=course_id2;
		 instructor_id=instructorid;
		 course=course_name;
		 name=authorname;
		 cost=costw;
		 bittu=bitmap;
		 rating=rat;
		 course_description=coures_desp;
	}
public String getcourseid() {
	  return course_id;
	 }
	 public void setcourseid(String code) {
	  this.course_id = code;
	 }
	 public String getinsid() {
	  return instructor_id;
	 }
	 public void setins_id(String name) {
	  this.instructor_id = name;
	 }
public String getCode() {
  return course;
 }
 public void setCode(String code) {
  this.course = code;
 }
 public String getName() {
  return name;
 }
 public void setName(String name) {
  this.name = name;
 }
 public String getstringurl() {
  return bittu;
 }
 public void setstringurl(String bittu) {
  this.bittu = bittu;
 }
 public String getRegion() {
  return cost;
 }
 public void setRegion(String cost) {
  this.cost = cost;
 }
 public String getrating() {
  return rating;
 }
 public void setrating(String rating) {
  this.rating = rating;
 }
 public Double getGnp() {
  return gnp;
 }
 public void setGnp(Double gnp) {
  this.gnp = gnp;
 }
 public Double getSurfaceArea() {
  return surfaceArea;
 }
 public void setSurfaceArea(Double surfaceArea) {
  this.surfaceArea = surfaceArea;
 }
 public String getdescription() {
  return course_description;
 }
 public void setdescription(String descp) {
  this.course_description = descp;
 }
 public Bitmap getBitmap() {
		return bit;
					}
 public void setBitmap(Bitmap bitmap) {
		this.bit= bitmap;
					}
}