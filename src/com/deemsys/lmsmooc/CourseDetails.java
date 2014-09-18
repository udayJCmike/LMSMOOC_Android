package com.deemsys.lmsmooc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CourseDetails extends SherlockFragmentActivity{
	TextView course_nam;
	String course_id,course_name;
	static String course_description,instructorid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_details);
		course_nam=(TextView)findViewById(R.id.coursename);
		 Intent i = getIntent();
		 course_id = i.getExtras().getString("courseid","");
		 course_name = i.getExtras().getString("course_name","");
		 course_description =i.getExtras().getString("course_description","");
		 instructorid=i.getExtras().getString("instructor_id","");
		 System.out.println("instructor id value"+instructorid);
	     course_nam.setText(course_name);
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);  
        mViewPager.setAdapter(new CourseDetailPageAdapter(getSupportFragmentManager()));
	}

}
