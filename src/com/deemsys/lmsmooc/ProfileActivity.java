package com.deemsys.lmsmooc;



import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


public class ProfileActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
    static String avatar_whole_url;
    public ProgressDialog pDialog;
	private CharSequence mDrawerTitle;
    ImageView img;
    int img_id;
	Bitmap bitmap;
	private CharSequence mTitle;

	
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavigationDrawerItem> navDrawerItems;
	private Navigationlistadapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
	
       
		mTitle = mDrawerTitle = getTitle();
       
		avatar_whole_url=LoginActivity.avatar_url+LoginActivity.avatar;
		
	     
		System.out.println("whole avatar url value"+avatar_whole_url);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
    
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavigationDrawerItem>();

		navDrawerItems.add(new NavigationDrawerItem(LoginActivity.firstname, 0 ));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		
        navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		
		navDrawerItems.add(new NavigationDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
		

		
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		
		adapter = new Navigationlistadapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.nav_drawer,
				R.string.app_name,
				R.string.app_name 
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			
			displayView(0);
		}
	}

	
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		
			displayView(position);
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()) {
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	
		return super.onPrepareOptionsMenu(menu);
	}

	
	private void displayView(int position) {
		
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new StudentProfileFragment();
			break;
		case 1:
			fragment = new ProfileFragment();
			break;
		case 2:
			fragment = new InboxFragment();
			break;
//		case 3:
//			fragment = new BagShoes();
//			break;
//		case 4:
//			fragment = new BakeryItems();
//			break;
//		

		default:	fragment = new StudentProfileFragment();
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		
        getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(mTitle);
		 getActionBar().setTitle(Html.fromHtml("<font color='#000000'>Profile</font>"));
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
}
