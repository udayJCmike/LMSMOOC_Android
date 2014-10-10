package com.deemsys.lmsmooc;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandablelistAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<Child>> _listDataChild;

	public ExpandablelistAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<Child>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
	
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Child child = (Child) getChild(groupPosition, childPosition);
	

		String lecttype = child.getlecttype().toString(); 
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listitem, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);
		ImageView lecturetype = (ImageView) convertView.findViewById(R.id.flag);

		txtListChild.setText(Integer.toString(childPosition + 1) + "."
				+ child.getName().toString());
		
		if (lecttype.equalsIgnoreCase("Text")) {
			lecturetype.setBackgroundResource(R.drawable.text);
		} else if (lecttype.equalsIgnoreCase("Audio")) {
			lecturetype.setBackgroundResource(R.drawable.audio);
		} else if (lecttype.equalsIgnoreCase("Video")) {
			lecturetype.setBackgroundResource(R.drawable.video);
		} else if (lecttype.equalsIgnoreCase("no lecture found")) {
			txtListChild.setText("No lecture found");
			lecturetype.setBackgroundResource(R.drawable.click);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (this._listDataChild.size() == 0) {
			return 1;
		} else {
			int childcount = this._listDataChild.get(
					this._listDataHeader.get(groupPosition)).size();
			
			if (childcount == 0) {
				return 0;
			} else {
				return childcount;
			}
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.listheader, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}