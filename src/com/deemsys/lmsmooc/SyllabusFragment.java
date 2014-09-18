package com.deemsys.lmsmooc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SyllabusFragment  extends Fragment
{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.syllabusfragment, container, false);
	        setHasOptionsMenu(true);
			return v;
	        
	 }
}
