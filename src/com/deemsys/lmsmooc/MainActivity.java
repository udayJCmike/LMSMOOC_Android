package com.deemsys.lmsmooc;


import android.app.ActionBar;
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
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity  {
	 static ViewPager pager;
	LinearLayout mDotsScrollbarHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     
        ActionBar actions = getActionBar();
     
        actions.setTitle(Html.fromHtml("<font color='#000000'>LMS MOOC</font>"));
        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
         pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
      pager.setOnPageChangeListener(new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			System.out.println("page selected"+arg0);
			
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
     int item= pager.getCurrentItem();
     System.out.println("item number:::"+item);

    }
   
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

            case 0: return Firstpage.newInstance("FirstFragment, Instance 1");
            case 1: return Secondpage.newInstance("SecondFragment, Instance 1");
            case 2:return Thirdpage.newInstance("ThirdFragment, Instance 1");
            case 3: return Fourthpage.newInstance("Fourth, Instance 1");
            default: return Firstpage.newInstance("ThirdFragment, Default");
            }
        }
       
        @Override
        public int getCount() {
            return 4;
        }
     
     
    }
    }