package com.deemsys.lmsmooc;

public class Inbox {
	String importantstatus;
	String student_regno;
	String inboxid;
	String sendername;
	String receiverid;
	String receivername;
	String role;
	String subject;
	String message;
	String readstatus;
	String sentdate;
	String senderid;

	public String getstudentregno() {
		return this.student_regno;
	}

	public void setstudentregno(String student_regno) {
		this.student_regno = student_regno;
	}

	public String getinboxid() {
		return this.inboxid;
	}

	public void setinboxid(String inboxid) {
		this.inboxid = inboxid;
	}

	public String getsendername() {
		return this.sendername;
	}

	public void setsendername(String sendername) {
		this.sendername = sendername;
	}

	public String getreceiverid() {
		return this.receiverid;
	}

	public void setreceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

	public String getreceivername() {
		return receivername;
	}

	public void setreceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getsubject() {
		return subject;
	}

	public void setsubject(String subject) {
		this.subject = subject;
	}

	public String getmessage() {
		return message;
	}

	public void setmessage(String message) {
		this.message = message;
	}

	public String getreadstatus() {
		return readstatus;
	}

	public void setreadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public String getimportantstatus() {
		return importantstatus;
	}

	public void setimportantstatus(String importantstatus) {
		this.importantstatus = importantstatus;
	}

	public String getsentdate() {
		return sentdate;
	}

	public void setsentdate(String sentdate) {
		this.sentdate = sentdate;
	}

	public String getsenderid() {
		return senderid;
	}

	public void setsenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getrole() {
		return role;
	}

	public void setrole(String role) {
		this.role = role;
	}

	public Inbox(String inboxid, String sendername, String receiverid,
			String receivername, String role, String subject, String message,
			String readstatus, String sentdate, String senderid,
			String importantstatus) {

		this.inboxid = inboxid;
		this.sendername = sendername;
		this.receiverid = receiverid;
		this.receivername = receivername;

		this.role = role;
		this.subject = subject;
		this.message = message;
		this.readstatus = readstatus;
		this.sentdate = sentdate;

		this.senderid = senderid;

		this.importantstatus = importantstatus;

	}

}
