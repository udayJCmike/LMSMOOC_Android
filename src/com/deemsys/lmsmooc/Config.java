package com.deemsys.lmsmooc;

public class Config
{ 
	
	//http://208.109.248.89/mobile/android/Services/
    static String ServerUrl ="http://208.109.248.89/mobile/android/Services/" ;
	//static String ServerUrl = "http://192.168.1.71:8080/LmsmoocAndroid/Services/";
	//static String ServerUrl = "http://169.254.164.230:8080/LmsmoocAndroid/Services/";
	static String AvatarUrl = "http://208.109.248.89:8087/OnlineCourse/resources/images/users/";
	static String allcourseurl="ViewCourses.php?service=viewallcourse";
	static String freecourseurl="ViewCourses.php?service=viewfreecourse";
	static String paidcourseurl="ViewCourses.php?service=viewpaidcourse";
	static String purchasenumberselection="ViewCourses.php?service=countnumber";
	static String categoryurl="Category.php?service=allcategory";
	static String categoryselectionurl="SearchAndCategory.php?service=viewcategorycourse";
	static String categoryselectionurlbrowse="Category.php?service=viewcategorycoursebrowse";
	static String searchselecturl="SearchAndCategory.php?service=viewsearchcourse";
	static String searchselecturlbrowse="SearchCourse.php?service=viewsearchcourse";
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
    
	static String unfavoritecategoryurl="MyFavoriteCategory.php?service=unfavcategory";
	static String addtomyfavoritecategoryurl="MyFavoriteCategory.php?service=addfavcategory";
	
	static String allcoursebrowseurl="BrowseCourses.php?service=viewallcourse";
	static String freecoursbrowseeurl="BrowseCourses.php?service=viewfreecourse";
	static String paidcoursebrowseurl="BrowseCourses.php?service=viewpaidcourse";
	
	
	static String studentSignup="Studentsignup.php?service=selectloginusername";
	static String studentSignup1="Studentsignup.php?service=selectloginemail";
	static String signup="Studentsignup.php?service=signup";
	static String logininsert="Studentsignup.php?service=logininsert";
	
	static String coursecontenttext="CourseContent.php?service=coursecontenttext";
	static String coursecontentvideo="CourseContent.php?service=coursecontentvideo";
	static String coursecontentaudio="CourseContent.php?service=coursecontentaudio";
	
	
	
	static String uploadpicone="Uploadpicture.php";
	static String uploadpictwo="Uploadpicture.php?service=imageinsert";
	
	
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
	static String URL_COMMON;
	static String browsecommon_url;
}
