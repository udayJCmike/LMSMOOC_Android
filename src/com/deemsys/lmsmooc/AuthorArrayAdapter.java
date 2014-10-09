package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AuthorArrayAdapter extends ArrayAdapter<Author> {
	private final Context context;
	Author user;
	int layoutResourceId;
	public static ArrayList<Author> data = new ArrayList<Author>();

	public AuthorArrayAdapter(Activity context, List<Author> vehicleall,
			int layoutResourceId) {
		super(context, R.layout.author_each_item, vehicleall);
		this.layoutResourceId = layoutResourceId;
		this.context = context;

		data = (ArrayList<Author>) vehicleall;

	}

	class UserHolder {
		TextView authname;
		TextView number;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		UserHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.author_each_item, parent, false);

			holder = new UserHolder();
			holder.authname = (TextView) row.findViewById(R.id.authorname);
			holder.number = (TextView) row.findViewById(R.id.numberofcour);

			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();
		}

		user = data.get(position);

		holder.authname.setText(user.getauthname());
		holder.number.setText(user.getauthcoursecount());

		return row;

	}

}
