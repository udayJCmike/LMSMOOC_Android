package com.deemsys.lmsmooc;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Html;

public class BrowseCourse extends SherlockFragmentActivity {
	static Boolean netcheck = false;
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browsecourse);

		Intent intent1 = new Intent();

		intent1.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
				"com.deemsys.lmsmooc.SplashActivity");
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", true);
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", false);
		intent1.putExtra(
				"com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
				"com.deemsys.lmsmooc");

		sendBroadcast(intent1);
		cd = new ConnectionDetector(getApplicationContext());
		netcheck = cd.isConnectingToInternet();
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		ab.setTitle(Html.fromHtml("<font color='#ffffff'>Courses</font>"));
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.parseColor("#3399FF"));
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);

		mViewPager.setOffscreenPageLimit(2);
		mViewPager
				.setAdapter(new ViewPagerAdapter1(getSupportFragmentManager()));

	}

	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.chosecategory, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.category:

			Intent intentSignUP = new Intent(this,
					CategorySelectionActivity.class);
			startActivity(intentSignUP);

			return true;
		case R.id.menu_search:
			Intent intentsearch = new Intent(this, SearchActivity.class);
			startActivity(intentsearch);
			return true;
		case android.R.id.home:
			Intent intentfignUP = new Intent(this, MainActivity.class);
			startActivity(intentfignUP);
		default:

		}
		return false;
	}

	@Override
	public void onBackPressed() {

	}

}
