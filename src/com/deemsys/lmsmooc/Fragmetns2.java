package com.deemsys.lmsmooc;


import java.lang.reflect.Field;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.SearchView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 
public class Fragmetns2 extends SherlockFragment implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener{
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,           Bundle savedInstanceState) {
    	 
        View view = inflater.inflate(R.layout.viewpager_main, container, false);
        setHasOptionsMenu(true);
        PagerTabStrip pagerTabStrip = (PagerTabStrip)view. findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#3399FF"));
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
       
        mViewPager.setBackgroundColor(Color.WHITE);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        return view;
    }
 
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
 
  
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chosecategory, menu);

        

    }
  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.category:
        	Intent intentSignUP=new Intent(getActivity(),CategorySelectionStudentBased.class);
			startActivity(intentSignUP);
          
            return true;
        case R.id.menu_search:
        	Intent intentsearch=new Intent(getActivity(),SearchStudentBased.class);
			startActivity(intentsearch);
          
            return true;
        }
        return false;
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
       return true;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
      return false;
  }

  @Override
  public boolean onSuggestionSelect(int position) {
      return false;
  }

  @Override
  public boolean onSuggestionClick(int position) {
//      Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
//      String query = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
//      Toast.makeText(this, "Suggestion clicked: " + query, Toast.LENGTH_LONG).show();
      return true;
  }

}