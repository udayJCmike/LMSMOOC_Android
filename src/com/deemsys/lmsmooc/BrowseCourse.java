package com.deemsys.lmsmooc;



import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;


public class BrowseCourse extends SherlockFragmentActivity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.browsecourse);  
	        ActionBar ab = getSupportActionBar();
		      ab.setDisplayHomeAsUpEnabled(true);
		      getSupportActionBar().setHomeButtonEnabled(true);
		      ab.setTitle(Html.fromHtml("<font color='#000000'>Browse Course</font>"));
			    
		        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
	        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
	        
	        
	        mViewPager.setAdapter(new ViewPagerAdapter1(getSupportFragmentManager()));
	        
	 }
	 public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getSupportMenuInflater().inflate(R.menu.chosecategory, menu);
			return true;
		}
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	        case R.id.category:
	        	System.out.println("in category selection");
	        	Intent intentSignUP=new Intent(this,CategorySelectionActivity.class);
				startActivity(intentSignUP);
	          
	            return true;
	        case R.id.menu_search:
	        	Intent intentsearch=new Intent(this,SearchActivity.class);
				startActivity(intentsearch);
				return true;
	       default:
	           finish();
	        }
	        return false;
	  }
	 @Override
	    public void onBackPressed() {
	 
	 }
}
