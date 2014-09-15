package com.deemsys.lmsmooc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class Fourthpage  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourthpage, container, false);

        setHasOptionsMenu(true);
        return v;
    }

    public static Fourthpage newInstance(String text) {

    	Fourthpage f = new Fourthpage();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    } 
    
   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_settings:
            // search action
        	System.out.println("settings clicked");
        	MainActivity.pager.setCurrentItem(5, true);
        	Fifthpage.newInstance("Fifth, Instance 1");
            return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }        
}