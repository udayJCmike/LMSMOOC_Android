package com.deemsys.lmsmooc;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class MainActivity extends FragmentActivity {
	static ViewPager pager;
	LinearLayout mDotsScrollbarHolder;
	PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actions = getActionBar();

		actions.setTitle(Html.fromHtml("<font color='#ffffff'>LMS MOOC</font>"));
		actions.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#3399FF")));

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

		Intent intent2 = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
		intent2.putExtra("badge_count", "");
		intent2.putExtra("badge_count_package_name", getApplicationContext()
				.getPackageName());
		intent2.putExtra("badge_count_class_name",
				"com.deemsys.lmsmooc.SplashActivity");
		getApplicationContext().sendBroadcast(intent2);

		// PackageManager pm = getApplicationContext().getPackageManager();
		// String lastEnabled
		// =GCMIntentService.getLastEnabled(getApplicationContext());
		// if (TextUtils.isEmpty(lastEnabled)) {
		// lastEnabled = "com.deemsys.lmsmooc.SplashActivity";
		// }
		// System.out.println("last enabled value in splash:::"+lastEnabled);
		// ComponentName componentName = new
		// ComponentName("com.deemsys.lmsmooc",
		// lastEnabled);
		// pm.setComponentEnabledSetting(componentName,
		// PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
		// PackageManager.DONT_KILL_APP);
		//
		// lastEnabled="com.deemsys.lmsmooc.SplashActivity";
		// componentName = new ComponentName("com.deemsys.lmsmooc",
		// lastEnabled);
		// pm.setComponentEnabledSetting(componentName,
		// PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
		// PackageManager.DONT_KILL_APP);
		// GCMIntentService.setLastEnabled(lastEnabled,getApplicationContext());
		//
		//

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		pager.setPageTransformer(true, new ZoomOutPageTransformer());
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(pager);

		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {

			case 0:
				return Firstpage.newInstance("FirstFragment, Instance 1");
			case 1:
				return Secondpage.newInstance("SecondFragment, Instance 1");
			case 2:
				return Thirdpage.newInstance("ThirdFragment, Instance 1");
			case 3:
				return Fourthpage.newInstance("Fourth, Instance 1");
			case 4:
				return Fifthpage.newInstance("Fifth, Instance 1");
			default:
				return Firstpage.newInstance("ThirdFragment, Default");
			}
		}

		@Override
		public int getCount() {
			return 5;
		}

	}

	public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				// Modify the default slide transition to shrink the page as
				// well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0) {
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else {
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
						/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}

	}

}