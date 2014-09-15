package com.deemsys.lmsmooc;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

public class BrowseCourse extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.browsecourse);  
	        ActionBar actions = getActionBar();
	        actions.setTitle(Html.fromHtml("<font color='#000000'>Browse Course</font>"));
	        actions.setDisplayHomeAsUpEnabled(true);
	        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
	 }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case android.R.id.home:
	        	System.out.println("on back pressed");
	   		finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	 @Override
	 public void onBackPressed() {
		 
	 }
}
