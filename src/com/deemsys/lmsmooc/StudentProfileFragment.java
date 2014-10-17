package com.deemsys.lmsmooc;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

public class StudentProfileFragment extends Fragment {
	static ImageView img;
	public ProgressDialog pDialog;
	Bitmap bitmap;

	public StudentProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.student_profile, container,
				false);
		img = (ImageView) rootView.findViewById(R.id.avatarimage);
		// new LoadImage().execute(ProfileActivity.avatar_whole_url);
		return rootView;
	}

}
