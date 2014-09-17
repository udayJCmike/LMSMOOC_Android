package com.deemsys.lmsmooc;


public class Billing {
	  String student_billing_id;
	 String student_id;
	String course_id;
	String course_name;
	 String promocode;
	 String reduction;
	 String amount_paid;     
	 String course_author;
	 String transaction_date;
	 String purchased_date;
	 String transaction_id;
	 String status;

	 public String getstudentid() {
			return this.student_id;
		    }

		 
		    public void setstudentid(String student_id) {
			this.student_id = student_id;
		    }
		    
		    public String getcourseid() {
				return this.course_id;
			    }

			 
			    public void setcourseid(String course_id) {
				this.course_id = course_id;
			    }
			    
			    public String getcoursename() {
					return this.course_name;
				    }

				 
				    public void setcoursename(String course_name) {
					this.course_name = course_name;
				    }
				    
	 
	
	 public String getpromocode() {
			return this.promocode;
		    }

		 
		    public void setpromocode(String promocode) {
			this.promocode = promocode;
		    }
		    
		    public String getreduction() {
		  	  return reduction;
		  	 }

		  	 public void setreduction(String reduction) {
		  	  this.reduction = reduction;
		  	 }		    
	 public String getamountpaid() {
	  return amount_paid;
	 }

	 public void setamountpaid(String amount_paid) {
	  this.amount_paid = amount_paid;
	 }

	 public String getcourseauthor() {
	  return course_author;
	 }

	 public void setcourseauthor(String course_author) {
	  this.course_author = course_author;
	 }

	 public String gettransactiondate() {
	  return transaction_date;
	 }
	 public void settransactiondate(String transaction_date) {
		  this.transaction_date = transaction_date;
		 }
	 public String getpurchaseddate() {
		  return purchased_date;
		 }
		 public void setpurchaseddate(String purchased_date) {
			  this.purchased_date = purchased_date;
			 }


	 public String gettransactionid() {
		  return transaction_id;
		 }
	 public void settransactionid(String transaction_id) {
		  this.transaction_id = transaction_id;
		 }

	 public String getstatus() {
		  return status;
		 }
	 public void setstatus(String status) {
		  this.status = status;
		 }
	
	
		 public Billing(String student_id, String course_id, String course_name,
					String promocode,String reduction,String amount_paid,String course_author,String transaction_date,
					String purchased_date,String transaction_id,String status) {
				
				
				  this.student_id=student_id;
				  this.course_id = course_id;
				  this.course_name = course_name;
				  this.promocode=promocode;

				  this.reduction = reduction;
				  this.amount_paid=amount_paid;
				  this.course_author = course_author;
				  this.transaction_date = transaction_date;
				  this.purchased_date=purchased_date;
				  
				  this.transaction_id=transaction_id;
				
					 this.status=status;

			}

	}
