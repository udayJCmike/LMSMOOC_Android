package com.deemsys.lmsmooc;


 
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
 
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.text.Html;
 
public class NewMainActivity extends SherlockFragmentActivity {
 
    // Declare Variables
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    MenuListAdapter mMenuAdapter;
    String[] title;
    String[] subtitle;
    int[] icon;
    Fragment fragment1 = new Fragmetns2();
    Fragment fragment2 = new ProfileFragment();
    Fragment fragment3 = new ChangePasswordFragment();
    Fragment fragment4 = new Fourthpage();
    Fragment fragment5 = new Fifthpage();
    Fragment fragment6 = new Secondpage();
    Fragment fragment7 = new Firstpage();
    Fragment fragment8 = new Secondpage();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
 static String sampletestvale="8";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from drawer_main.xml
        setContentView(R.layout.drawer_main);
       
        mTitle = mDrawerTitle = getTitle();
        getActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + mDrawerTitle + "</font>"));
		
    	
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        // Generate title
        title = new String[] {Config.firstname , "Profile","Change Password","Inbox","My Courses","My Favorites","My Categories"+sampletestvale,"My Authors","Billing","Logout"};
 
        // Generate subtitle
        subtitle = new String[] { "Subtitle Fragment 1", "Subtitle Fragment 2" };
 
        // Generate icon
        icon = new int[] { R.drawable.ic_launcher, R.drawable.ban1,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher };
 
        // Locate DrawerLayout in drawer_main.xml
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 
        // Locate ListView in drawer_main.xml
        mDrawerList = (ListView) findViewById(R.id.listview_drawer);
 
        // Set a custom shadow that overlays the main content when the drawer
        // opens
//        mDrawerLayout.setDrawerShadow(R.drawable.ic_launcher,
//                GravityCompat.START);
// 
        // Pass string arrays to MenuListAdapter
        mMenuAdapter = new MenuListAdapter(getApplicationContext(), title, subtitle,icon);
 
        // Set the MenuListAdapter to the ListView
        mDrawerList.setAdapter(mMenuAdapter);
 
        // Capture listview menu item click
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
        // Enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.nav_drawer, R.string.app_name,
                R.string.app_name) {
 
            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(view);
            }
 
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                // Set the title on the action when drawer open
            	getActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + mDrawerTitle + "</font>"));
              //  getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerOpened(drawerView);
            }
        };
 
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
        if (item.getItemId() == android.R.id.home) {
 
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
 
        return super.onOptionsItemSelected(item);
    }
 
    // ListView click listener in the navigation drawer
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            selectItem(position);
        }
    }
 
    private void selectItem(int position) {
 
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Locate Position
        switch (position) {
        case 0:
            ft.replace(R.id.content_frame, fragment1);
            break;
        case 1:
            ft.replace(R.id.content_frame, fragment2);
            break;
        case 2:
            ft.replace(R.id.content_frame, fragment3);
            break;
        case 3:
            ft.replace(R.id.content_frame, fragment2);
            break;
        case 4:
            ft.replace(R.id.content_frame, fragment1);
            break;
        case 5:
            ft.replace(R.id.content_frame, fragment2);
            break;
        case 6:
            ft.replace(R.id.content_frame, fragment1);
            break;
        case 7:
            ft.replace(R.id.content_frame, fragment2);
            break;
        case 8:
            ft.replace(R.id.content_frame, fragment1);
            break;
        default:
        	 Intent intentSignUP=new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(intentSignUP);
            break;
        }
        ft.commit();
        mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		if(position==0)
		{
			setTitle(Html.fromHtml("<font color=\"black\">" + "LMSMOOC" + "</font>"));
		}
		else
		{
			setTitle(Html.fromHtml("<font color=\"black\">" + title[position] + "</font>"));
		//setTitle(navMenuTitles[position]);
		}
		mDrawerLayout.closeDrawer(mDrawerList);
       
       
      //  mDrawerLayout.closeDrawer(mDrawerList);
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
 
    @Override
    public void onBackPressed() {
 
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();
 
        } else {
            // Otherwise, ask user if he wants to leave :)
            super.onBackPressed();
        }
    }
}