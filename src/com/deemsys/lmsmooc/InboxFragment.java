package com.deemsys.lmsmooc;





import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InboxFragment extends Fragment {
	
	public InboxFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
        NewMainActivity.sampletestvale="sentahamil";
        return rootView;
    }
	 @Override
	 public void onDestroy() {
	     getActivity().getActionBar().removeAllTabs();
	  //  getActivity().getActionBar().setNavigationMode(ActionBar.);
	     super.onDestroy();
	 }
}
