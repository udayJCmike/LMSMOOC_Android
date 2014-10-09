package com.deemsys.lmsmooc;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fifthpage extends Fragment {

	Button signin, register;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fifthpage, container, false);
		setHasOptionsMenu(true);

		signin = (Button) v.findViewById(R.id.signin);
		register = (Button) v.findViewById(R.id.register);
		signin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(getActivity(), LoginActivity.class);
				startActivity(i);

			}

		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), StudentSignup.class);
				startActivity(i);
				System.out.println("register clciked");

			}

		});

		return v;
	}

	public static Fifthpage newInstance(String text) {

		Fifthpage f = new Fifthpage();
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
		inflater.inflate(R.menu.browsecourse, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.browsecourse:
			// search action
			System.out.println("browse clicked");
			Intent i = new Intent(getActivity(), BrowseCourse.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}