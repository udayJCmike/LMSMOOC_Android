package com.deemsys.lmsmooc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutCourseFragment  extends Fragment
{
	TextView aboutcourse;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.aboucoursefragment, container, false);
	        setHasOptionsMenu(true);
	        aboutcourse=(TextView)v.findViewById(R.id.aboutcourse_text);
	        aboutcourse.setText(Html.fromHtml(CourseDetails.course_description));
			return v;
	        
	 }
}
