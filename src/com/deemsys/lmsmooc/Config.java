package com.deemsys.lmsmooc;

public class Config
{
	static String ServerUrl = "http://192.168.1.71:8080/LmsmoocAndroid/Services/";
	
	static String AvatarUrl = "http://208.109.248.89:8085/OnlineCourse/resources/images/users/";
	static String allcourseurl="ViewCourses.php?service=viewallcourse";
	static String freecourseurl="ViewCourses.php?service=viewfreecourse";
	static String paidcourseurl="ViewCourses.php?service=viewpaidcourse";
	static String purchasenumberselection="ViewCourses.php?service=countnumber";
	static String categoryurl="Category.php?service=allcategory";
	static String categoryselectionurl="Category.php?service=viewcategorycourse";
	static String searchselecturl="SearchCourse.php?service=viewsearchcourse";
	static String mycourseurl="MyCourses.php?service=mycourse";
	static String myfavoritecourseurl="MyFavoriteCourse.php?service=myfavoritecourse";
	static String myfavoritecategoryurl="MyFavoriteCategory.php?service=favoritecategory";
	static String myfavoriteauthorurl="MyFavoriteAuthor.php?service=favoriteauthor";
	static String myfavoriteauthorcourseurl="MyFavoriteAuthor.php?service=viewyourauthorcourse";
	
	static  String billingdetailsurl = "Student_billing.php?service=selectbilling";
	static  String inboxdetailsurl = "Inbox.php?service=selectinbox";

    static final String inboxreadstatus = "Inbox.php?service=readstatusupdateone";

    static final String importatntstatuszero = "Inbox.php?service=importantstatusupdatezero";
    static final String importantstatusone = "Inbox.php?service=importantstatusupdateone";
    static  String unfollowauthorurl = "MyFavoriteAuthor.php?service=unfollowauthor";
    
    static  String followauthorurl = "MyFavoriteAuthor.php?service=followauthor";
    static String removefrommycategoryurl="MyFavoriteCategory.php?service=removecategory";
	
	static String coursedetailsurl="CourseDetails.php?service=authordetails";
	
	static String sampleurl="Sample.php?service=sample";
	static String courseurl="Courses.php";
    
	static String student_id;
	static String avatar_url;
	static String username;
	static String firstname;
	static String lastname;
	static String email;
	static String interested_in;
	static  String gender;
	static String avatar;
	static  String logins;
	static  String password;
	static String gencode;	
	static  String role;
	static String enabled;	
	static String common_url;
}
