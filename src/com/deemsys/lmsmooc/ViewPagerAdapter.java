package com.deemsys.lmsmooc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	// Declare the number of ViewPager pages
	final int PAGE_COUNT = 3;
	private String titles[] = new String[] { "All Courses", "Free Course",
			"Paid Course" };

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {

		// Open FragmentTab1.java
		case 0:
			AllCourses fragmenttab1 = new AllCourses();
			return fragmenttab1;

			// Open FragmentTab2.java
		case 1:
			FreeCourses fragmenttab2 = new FreeCourses();
			return fragmenttab2;
		case 2:
			PaidCourses fragmenttab3 = new PaidCourses();
			return fragmenttab3;
		}
		return null;
	}

	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub

		FragmentManager manager = ((Fragment) object).getFragmentManager();
		FragmentTransaction trans = manager.beginTransaction();
		trans.remove((Fragment) object);
		trans.commit();

		super.destroyItem(container, position, object);
	}
}