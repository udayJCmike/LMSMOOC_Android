package com.deemsys.lmsmooc;

public class Author {
	String authorname = null;
	String numbcourses;
	String ins_id;
	 public Author(String authname,String numbcourss,String insid) {
			authorname=authname;
			numbcourses=numbcourss;
			ins_id=insid;
			
		}
public String getauthname()
 {
 return authorname;
 }
	 public void setauthname(String name) 
 {
  this.authorname = name;
 }
	 public String getauthcoursecount()
	 {
	 return numbcourses;
	 }
		 public void setauthcoursecount(String count) 
	 {
	  this.numbcourses = count;
	 }
		 public String getauthid()
		 {
		 return ins_id;
		 }
			 public void setauthid(String id) 
		 {
		  this.ins_id = id;
		 }
}
