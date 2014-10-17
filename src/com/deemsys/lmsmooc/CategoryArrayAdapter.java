package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {
	private final Context context;
	Category user;
	int layoutResourceId;
	public static ArrayList<Category> data = new ArrayList<Category>();

	public CategoryArrayAdapter(Activity context, List<Category> vehicleall,
			int layoutResourceId) {
		super(context, R.layout.category_each_item, vehicleall);
		this.layoutResourceId = layoutResourceId;
		this.context = context;

		data = (ArrayList<Category>) vehicleall;

	}

	class UserHolder {
		TextView category;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		UserHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.category_each_item, parent, false);

			holder = new UserHolder();
			holder.category = (TextView) row.findViewById(R.id.categoryname);

			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();
		}

		user = data.get(position);

		holder.category.setText(user.getcategory());

		return row;

	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {

		super.registerDataSetObserver(arg0);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {

		super.unregisterDataSetObserver(arg0);
	}
}
