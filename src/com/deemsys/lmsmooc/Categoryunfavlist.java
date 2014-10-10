package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.ListView;

public class Categoryunfavlist extends SherlockActivity {
	String category_name, success;
	public ProgressDialog cDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	ListView list2;
	String[] strArray;
	String selectedcourse;
	private static final String TAG_SUCCESS = "success";
	public static ArrayList<String> vehiclelist = new ArrayList<String>();
	public static List<UnfavoriteCourses> category = new ArrayList<UnfavoriteCourses>();
	JSONArray user = null;
	String str = "";
	UnFavoriteCategoryAdapter unfavadapter;
	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_Category_ARRAY = "Category List";

	private static final String TAG_Category_NAME = "category_name";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		setContentView(R.layout.unfavcategory);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		ab.setTitle(Html.fromHtml("<font color='#ffffff'>Categories</font>"));
		list2 = (ListView) findViewById(R.id.listView);
		list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new CategoryDetails().execute();
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(
					Categoryunfavlist.this).create();

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

	class CategoryDetails extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(Categoryunfavlist.this);
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			cDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			if (category.size() == 0) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						Categoryunfavlist.this).create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("No data found.");

				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();
			}
			unfavadapter = new UnFavoriteCategoryAdapter(
					Categoryunfavlist.this,
					(ArrayList<UnfavoriteCourses>) category);
			list2.setAdapter(unfavadapter);

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
					+ Config.unfavoritecategoryurl, "POST", params1);

			

			try {
				if (jArray != null) {

					JSONObject c = jArray.getJSONObject(TAG_SRESL);
					
					user = c.getJSONArray(TAG_Category_ARRAY);
					

					for (int i = 0; i < user.length(); i++) {
						
						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRESL);
						category_name = c2.getString(TAG_Category_NAME);

						UnfavoriteCourses cnt = new UnfavoriteCourses(
								category_name, false);
						cnt.setName(category_name);

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

	@Override
	public void onBackPressed() {

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {

		case R.id.done:

			String result = "";

			for (UnfavoriteCourses p : unfavadapter.getBox()) {
				if (p.selected) {
					result += p.name + ",";

				}
			}
			strArray = result.split("\\,");
			
			if (result.equalsIgnoreCase("")) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						Categoryunfavlist.this).create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("No category selected.");

				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();
			} else {
				for (String selectedcourse : strArray) {
					if (selectedcourse.length() == 0) {

					} else {
						new addtomyfav().execute(selectedcourse);
					}

				}

			}
			return true;
		case android.R.id.home:

			finish();

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.done, menu);
		return true;
	}

	class addtomyfav extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getApplicationContext());
			// pDialog.setMessage("Please wait...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(true);
			// pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			String favcourse = params[0];
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			params1.add(new BasicNameValuePair("category_name", favcourse));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.addtomyfavoritecategoryurl, "POST", params1);
			
			if (json != null) {
				try {
					if (json != null) {
						
						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						success = jUser.getString(TAG_SUCCESS);
						

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}

			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
		

			finish();

		}

	}
}