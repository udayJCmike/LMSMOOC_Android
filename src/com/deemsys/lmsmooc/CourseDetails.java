package com.deemsys.lmsmooc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CourseDetails extends SherlockFragmentActivity{
	TextView course_nam,enrol;
	String course_id,course_name,course_enrolls;
	static String course_description,instructorid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_details);
		
		course_nam=(TextView)findViewById(R.id.coursename);
		enrol=(TextView)findViewById(R.id.enrollcourse);
		 Intent i = getIntent();
		 course_id = i.getExtras().getString("courseid","");
		 course_name = i.getExtras().getString("course_name","");
		 course_description =i.getExtras().getString("course_description","");
		 instructorid=i.getExtras().getString("instructor_id","");
		 course_enrolls=i.getExtras().getString("enroll_students","");
		 System.out.println("instructor id value"+instructorid);
	     course_nam.setText(course_name);
	     enrol.setText(course_enrolls);
	     ActionBar ab = getSupportActionBar();
	     ab.setDisplayHomeAsUpEnabled(true);
	     getSupportActionBar().setHomeButtonEnabled(true);
	     setTitle(Html.fromHtml("<font color=\"black\">" + course_name + "</font>"));
	     ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);  
        mViewPager.setAdapter(new CourseDetailPageAdapter(getSupportFragmentManager()));
	}

}
