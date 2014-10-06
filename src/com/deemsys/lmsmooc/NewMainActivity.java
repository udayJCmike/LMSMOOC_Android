
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
    Fragment fragment4 = new InboxFragment();
    Fragment fragment5 = new MyCourses();
    Fragment fragment6 = new MyFavoriteCourses();
    Fragment fragment7 = new MyFavoriteCategory();
    Fragment fragment8 = new MyFavoriteAuthor();
    Fragment fragment9 = new BillingFragment();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
 static String sampletestvale="8";
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        
        setContentView(R.layout.drawer_main);
       
        mTitle = mDrawerTitle = getTitle();
        getActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + mDrawerTitle + "</font>"));
		
    	
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
      
        title = new String[] {Config.firstname , "Profile","Change Password","Inbox","My Courses","My Favorites","My Categories","My Authors","Billing","Logout"};
 
       
        subtitle = new String[] { "Subtitle Fragment 1", "Subtitle Fragment 2" };
 
      
        icon = new int[] { R.drawable.click, R.drawable.profile,R.drawable.changepassword,R.drawable.inbox,R.drawable.courses,R.drawable.favorites,R.drawable.category,R.drawable.author,R.drawable.billing,R.drawable.logout,R.drawable.ic_launcher };
 
       
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 
       
        mDrawerList = (ListView) findViewById(R.id.listview_drawer);
 
        mMenuAdapter = new MenuListAdapter(getApplicationContext(), title, subtitle,icon);
 
       
        mDrawerList.setAdapter(mMenuAdapter);
 
       
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
 
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 
        mDrawerToggle  = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.drawer, R.string.app_name,
                R.string.app_name) {
        	
            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(view);
            }
            
            
            public void onDrawerOpened(View drawerView) {
                
            	getActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + mDrawerTitle + "</font>"));
           
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
      
        switch (position) {
        case 0:
            ft.replace(R.id.content_frame, new Fragmetns2());
            break;
        case 1:
            ft.replace(R.id.content_frame, fragment2);
            break;
        case 2:
            ft.replace(R.id.content_frame, fragment3);
            break;
        case 3:
            ft.replace(R.id.content_frame, fragment4);
            break;
        case 4:
            ft.replace(R.id.content_frame, fragment5);
            break;
        case 5:
            ft.replace(R.id.content_frame, fragment6);
            break;
        case 6:
            ft.replace(R.id.content_frame, fragment7);
            break;
        case 7:
            ft.replace(R.id.content_frame, fragment8);
            break;
        case 8:
            ft.replace(R.id.content_frame, fragment9);
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
			setTitle(Html.fromHtml("<font color=\"white\">" + "Courses" + "</font>"));
		}
		else
		{
			setTitle(Html.fromHtml("<font color=\"white\">" + title[position] + "</font>"));
		
		}
		mDrawerLayout.closeDrawer(mDrawerList);
       
       
     
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
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
 
    @Override
    public void onBackPressed() {
 
//        FragmentManager manager = getSupportFragmentManager();
//        if (manager.getBackStackEntryCount() > 0) {
//            
//            manager.popBackStack();
// 
//        } else {
//         
//            super.onBackPressed();
//        }
    }
    
//	 @Override
//	 public void onBackPressed() 
//  {
//
//  }
//    
}