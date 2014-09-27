package com.deemsys.lmsmooc;



import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UnFavoriteCategoryAdapter extends BaseAdapter {
	Context ctx;
	LayoutInflater lInflater;
	ArrayList<UnfavoriteCourses> objects;

	UnFavoriteCategoryAdapter(Context context, ArrayList<UnfavoriteCourses> products) {
		ctx = context;
		objects = products;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.unfavcheck, parent, false);
		}

		UnfavoriteCourses p = getProduct(position);

		((TextView) view.findViewById(R.id.label)).setText(p.name);
		
		CheckBox cbBuy = (CheckBox) view.findViewById(R.id.check);
		cbBuy.setOnCheckedChangeListener(myCheckChangList);
		cbBuy.setTag(position);
		cbBuy.setChecked(p.selected);
		return view;
	}

	UnfavoriteCourses getProduct(int position) {
		return ((UnfavoriteCourses) getItem(position));
	}

	ArrayList<UnfavoriteCourses> getBox() {
		ArrayList<UnfavoriteCourses> box = new ArrayList<UnfavoriteCourses>();
		for (UnfavoriteCourses p : objects) {
			if (p.selected)
				box.add(p);
		}
		return box;
	}

	OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			getProduct((Integer) buttonView.getTag()).selected = isChecked;
		}
	};
}