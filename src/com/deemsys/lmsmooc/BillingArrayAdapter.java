package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;

public class BillingArrayAdapter extends ArrayAdapter<Billing> {
	private final Context context;
	public ProgressDialog cDialog;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public ArrayList<String> billinglist = new ArrayList<String>();
	public List<Billing> allbilling = new ArrayList<Billing>();
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
	Billing user;
	int layoutResourceId;
	public static ArrayList<Billing> data = new ArrayList<Billing>();
	TextView num;

	public BillingArrayAdapter(Activity context, List<Billing> allbilling,
			int layoutResourceId) {
		super(context, R.layout.billinglist, allbilling);

		this.layoutResourceId = layoutResourceId;
		this.context = context;

		data = (ArrayList<Billing>) allbilling;

	}

	class UserHolder {
		TextView cou_name, pur_date, promo, reduc, amou;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		final UserHolder holder;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.billinglist, parent, false);

			holder = new UserHolder();
			holder.cou_name = (TextView) row.findViewById(R.id.coursenameans1);
			holder.pur_date = (TextView) row.findViewById(R.id.purdateans1);
			holder.promo = (TextView) row.findViewById(R.id.promoans1);
			holder.reduc = (TextView) row.findViewById(R.id.reductionans1);
			holder.amou = (TextView) row.findViewById(R.id.amountans1);

			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();
		}

		user = data.get(position);

		holder.cou_name.setText(user.getcoursename());
		holder.pur_date.setText(user.getpurchaseddate());
		holder.reduc.setText("$" + user.getreduction());
		holder.amou.setText("$" + user.getamountpaid());

		if (user.getpromocode().equals("0")) {
			holder.promo.setText("No");

		} else if (user.getpromocode().equals("1")) {
			holder.promo.setText("Yes");
		}

		return row;

	}

}