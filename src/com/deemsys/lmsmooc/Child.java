package com.deemsys.lmsmooc;

public class Child {
	String  sectionid,lectureid,sectionname;
    private String Name;
    private String lecturetype;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getlecttype() {
        return lecturetype;
    }

    public void setlecttype(String lecttype) {
        this.lecturetype = lecttype;
    }
    

    public void setlectureid(String id) {
        this.lectureid = id;
    }
    public String getlectureid() {
        return lectureid;
    }

    public void setsecid(String secid) {
        this.sectionid = secid;
    }
    public String getsecid() {
        return sectionid;
    }
    public void setsecname(String lecttype) {
        this.sectionname = lecttype;
    }
    public String getsecname() {
        return sectionname;
    }
    
}
