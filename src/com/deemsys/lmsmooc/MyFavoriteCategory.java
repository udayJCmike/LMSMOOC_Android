package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.app.SherlockFragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MyFavoriteCategory extends SherlockFragment {
	String category_name, success;
	public ProgressDialog cDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	ListView list2;
	public static ArrayList<String> vehiclelist = new ArrayList<String>();
	public static List<Category> category = new ArrayList<Category>();
	JSONArray user = null;

	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_Category_ARRAY = "Category List";

	private static final String TAG_Category_NAME = "category_name";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.categories, container, false);
		setHasOptionsMenu(true);
		// cd = new ConnectionDetector(getActivity());
		// isInternetPresent = cd.isConnectingToInternet();
		// if (isInternetPresent) {
		// new CategoryDetails().execute();
		// }

		list2 = (ListView) v.findViewById(R.id.listView);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.addmyfavcategory, menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.addmyfav:
			Intent intentSignUP = new Intent(getActivity(),
					Categoryunfavlist.class);
			startActivity(intentSignUP);

			return true;

		}
		return false;
	}

	class CategoryDetails extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(getActivity());
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			cDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			if (user.length() == 0) {
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("No data found.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed

							}
						});

				// Showing Alert Message
				alertDialog.show();
			}
			list2.setAdapter(new CategoryArrayAdapter(getActivity(), category,
					R.layout.category_each_item));
			list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					String item = category.get(arg2).getcategory();

					Intent i = new Intent(getActivity(),
							MyFavoriteCategoryCourses.class);

					i.putExtra("category_name", item);

					startActivity(i);
				}
			});

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			CategoryArrayAdapter.data.clear();
			category.clear();

			vehiclelist.clear();
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			
			jArray = jsonParser.makeHttpRequest(Config.ServerUrl
					+ Config.myfavoritecategoryurl, "POST", params1);

			

			try {
				if (jArray != null) {

					JSONObject c = jArray.getJSONObject(TAG_SRESL);
					
					user = c.getJSONArray(TAG_Category_ARRAY);
					

					for (int i = 0; i < user.length(); i++) {
						
						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRESL);
						category_name = c2.getString(TAG_Category_NAME);

						Category cnt = new Category(category_name);
						cnt.setcategory(category_name);

						category.add(cnt);

						

					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			cDialog.dismiss();
			return null;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onResume() {
		super.onResume();

		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new CategoryDetails().execute();
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();

			alertDialog.setTitle("INFO!");

			alertDialog.setMessage("No network connection.");

			alertDialog.setIcon(R.drawable.delete);

			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(final DialogInterface dialog,
						final int which) {

				}
			});

			alertDialog.show();

		}
	}
}