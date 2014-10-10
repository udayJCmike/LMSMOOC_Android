package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class InboxArrayAdapter extends ArrayAdapter<Inbox> {
	private final Context context;
	public ProgressDialog cDialog;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public ArrayList<String> inboxlist = new ArrayList<String>();
	public List<Inbox> allinbox = new ArrayList<Inbox>();
	String importantstatus;
	TextView welcome;
	static String inboxid;
	String sendername;
	static String receiverid;
	String receivername;
	static String role;
	static String subject;
	String message;
	String readstatus;
	static String sentdate;
	String senderid;
	ListView lstvw;
	ListView list2;
	TextView welcomeusername;
	Inbox user;
	int layoutResourceId;
	public static ArrayList<Inbox> data = new ArrayList<Inbox>();

	public InboxArrayAdapter(Activity context, List<Inbox> allinbox,
			int layoutResourceId) {
		super(context, R.layout.inboxlist, allinbox);

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		data = (ArrayList<Inbox>) allinbox;

	}

	class UserHolder {
		TextView subject, date;
		ImageView importantstatus;
		LinearLayout readstatus;
		RelativeLayout inbox;

		int a = 0;

	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		
		View row = convertView;
		final UserHolder holder;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.inboxlist, parent, false);

			holder = new UserHolder();
			holder.subject = (TextView) row.findViewById(R.id.addrstext);
			holder.date = (TextView) row.findViewById(R.id.veh_reg_no);
			holder.inbox = (RelativeLayout) row
					.findViewById(R.id.abs__list_item);

			holder.importantstatus = (ImageView) row.findViewById(R.id.redbtn);


			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();

		}

		user = data.get(position);

		holder.subject.setText(user.getsubject());
		holder.date.setText(user.getsentdate());

		if (user.getreadstatus().equals("0")) {

			row.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#808080")));

		} else if (user.getreadstatus().equals("1")) {
			row.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#ffffff")));

		}

		if (user.getimportantstatus().equals("0")) {

			holder.importantstatus
					.setBackgroundResource(R.drawable.unimportant);

		}

		else if (user.getimportantstatus().equals("1")) {

			holder.importantstatus.setBackgroundResource(R.drawable.important);

		}

		return row;

	}

}
