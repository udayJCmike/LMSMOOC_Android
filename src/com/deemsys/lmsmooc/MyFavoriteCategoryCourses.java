package com.deemsys.lmsmooc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.squareup.picasso.Picasso;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class MyFavoriteCategoryCourses extends SherlockFragmentActivity {
	public ProgressDialog cDialog, pDialog;
	public static ArrayList<String> imagelist = new ArrayList<String>();
	public static ArrayList<String> coursetotallist = new ArrayList<String>();
	ArrayList<Course> courselist;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	MyCustomAdapter dataAdapter = null;
	int start = 0;
	int limit = 10;
	boolean loadingMore = false;
	View loadMoreView;
	JSONArray user = null;
	static ListView listView;
	Bitmap bitmap;
	String rating_count;
	String promocheck;
	private static final String Promo_Check = "promocheck";
	private static final String TAG_ENROLLED_STUDENT = "course_enrolled";
	String course_description;
	private static final String TAG_COURSE_DESCRIPTION = "course_description";
	String course_name, authorname, student_enrolled, ratingcouont, cost,
			course_id, instructorid, numofrows, category_name,
			course_cover_image, ifmycoursepresent, audiourl, audiourlpassing;
	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_Course_ARRAY = "CourseList";
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_COURSE_NAME = "course_name";
	private static final String TAG_COURSE_AUTHOR = "course_author";
	private static final String TAG_COURSE_COST = "course_price";
	private static final String TAG_COURSE_RATINGS = "user_ratings";
	private static final String TAG_course_cover_image = "course_cover_image";
	private static final String TAG_Check_ = "checkmycourse";
	private static final String TAG_INSTRUCTOR_ID = "instructor_id";
	private static final String TAG_COURSE_ID = "course_id";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NUMBER_OF_ROWS = "number_of_rows";
	private static final String TAG_COURSE_PROMO_VIDEO = "course_promo_video";
	String courseidurl, instructoridurl, pur_url;
	String course_id_topass, course_name_to_pass, course_descript_to_pass,
			course_enrolled, course_enrolled_passing, checkstatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_courses);

		Intent i = getIntent();
		category_name = i.getExtras().getString("category_name", "");

		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setTitle(Html.fromHtml("<font color=\"white\">" + category_name
				+ "</font>"));
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		listView = (ListView) findViewById(R.id.listView1);

		loadMoreView = ((LayoutInflater) getApplicationContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.loadmore, null, false);
		listView.addFooterView(loadMoreView);
		courselist = new ArrayList<Course>();
		dataAdapter = new MyCustomAdapter(this, R.layout.course_overview,
				courselist);
		listView.setAdapter(dataAdapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Course country = (Course) parent.getItemAtPosition(position);
				if (position < courselist.size()) {
					audiourlpassing = country.getaudiourl();
					course_descript_to_pass = country.getdescription();
					course_id_topass = country.getcourseid();
					courseidurl = country.getcourseid();
					instructoridurl = country.getinsid();
					course_name_to_pass = country.getCode();
					course_enrolled_passing = country.getstudentsenrolled();
					checkstatus = country.getifmycourse();

					courseidurl = country.getcourseid();
					instructoridurl = country.getinsid();
					rating_count = country.getrating();
					new fetchpurnumber().execute();

				}
			}
		});

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;
				if ((lastInScreen == totalItemCount) && !(loadingMore)) {

					grabURL(Config.ServerUrl + Config.categoryselectionurl);
				}
			}
		});

	}

	public void grabURL(String url) {

		new GrabURL().execute(url);
	}

	public static AllCourses newInstance(String text) {

		AllCourses f = new AllCourses();
		Bundle b = new Bundle();
		b.putString("msg", text);

		f.setArguments(b);

		return f;

	}

	class GrabURL extends AsyncTask<String, Void, String> {
		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();
		final HttpParams params = httpclient.getParams();
		HttpResponse response;
		private String content = null;

		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(MyFavoriteCategoryCourses.this);
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			// cDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);

			cDialog.dismiss();

			if (user.length() == 0 && start == 0) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						MyFavoriteCategoryCourses.this).create();

				alertDialog.setTitle("Sorry User");

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
			//

			displayCountryList(content);

		}

		private void displayCountryList(String response) {

			try {

				JSONObject c = jArray.getJSONObject(TAG_SRES);

				JSONArray countryListObj = c.getJSONArray(TAG_Course_ARRAY);

				if (countryListObj.length() == 0) {

					listView.removeFooterView(loadMoreView);
				} else {
					for (int i = 0; i < countryListObj.length(); i++) {
						start++;

						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);
						authorname = c2.getString(TAG_COURSE_AUTHOR);
						instructorid = c2.getString(TAG_INSTRUCTOR_ID);
						course_id = c2.getString(TAG_COURSE_ID);
						course_name = c2.getString(TAG_COURSE_NAME);
						ifmycoursepresent = c2.getString(TAG_Check_);
						audiourl = c2.getString(TAG_COURSE_PROMO_VIDEO);
						course_cover_image = c2
								.getString(TAG_course_cover_image);
						cost = c2.getString(TAG_COURSE_COST);
						promocheck = c2.getString(Promo_Check);
						course_enrolled = c2.getString(TAG_ENROLLED_STUDENT);
						course_description = c2
								.getString(TAG_COURSE_DESCRIPTION);
						ratingcouont = c2.getString(TAG_COURSE_RATINGS);
						coursetotallist.add(authorname);
						coursetotallist.add(course_name);
						coursetotallist.add(ratingcouont);
						coursetotallist.add(audiourl);
						coursetotallist.add(ifmycoursepresent);
						coursetotallist.add(course_enrolled);
						imagelist.add(course_cover_image);

						Course cnt = new Course(authorname, course_name, cost,
								course_id, instructorid, course_cover_image,
								ratingcouont, ifmycoursepresent, audiourl,
								promocheck);
						cnt.setName(authorname);
						cnt.setCode(course_name);
						cnt.setins_id(instructorid);
						cnt.setcourseid(course_id);
						cnt.setpromocheck(promocheck);
						cnt.setstudentsenrolled(course_enrolled);
						cnt.setrating(ratingcouont);
						cnt.setdescription(course_description);
						cnt.setifmycourse(ifmycoursepresent);
						cnt.setstringurl(course_cover_image);
						cnt.setstudentsenrolled(course_enrolled);
						cnt.setaudiourl(audiourl);
						courselist.add(cnt);

						dataAdapter.add(cnt);

					}

					dataAdapter.notifyDataSetChanged();
					loadingMore = false;

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			String URL = null;
			loadingMore = true;

			try {
				URL = urls[0];
				HttpConnectionParams.setConnectionTimeout(params,
						REGISTRATION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
				ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

				HttpPost httpPost = new HttpPost(URL);

				// add name value pair for the country code
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						3);
				nameValuePairs.add(new BasicNameValuePair("category_name",
						category_name));
				nameValuePairs.add(new BasicNameValuePair("start", String
						.valueOf(start)));
				nameValuePairs.add(new BasicNameValuePair("limit", String
						.valueOf(limit)));
				nameValuePairs.add(new BasicNameValuePair("student_id",
						Config.student_id));
				jArray = jsonParser.makeHttpRequest(Config.ServerUrl
						+ Config.categoryselectionurl, "POST", nameValuePairs);
				JSONObject c = jArray.getJSONObject(TAG_SRES);

				user = c.getJSONArray(TAG_Course_ARRAY);

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpclient.execute(httpPost);

				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					content = out.toString();
				} else {
					// Closes the connection.

					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {

				content = e.getMessage();

				cancel(true);
			} catch (IOException e) {

				content = e.getMessage();

				cancel(true);
			} catch (Exception e) {

				content = e.getMessage();

				cancel(true);
			}
			return content;
		}

	}

	private class MyCustomAdapter extends ArrayAdapter<Course> {

		private ArrayList<Course> countryList;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<Course> countryList) {
			super(context, textViewResourceId, countryList);
			this.countryList = new ArrayList<Course>();
			this.countryList.addAll(countryList);
		}

		private class ViewHolder {
			TextView code;
			TextView name;
			TextView enroll;
			TextView cost;
			ImageView cover;
			ImageView ratingshow;
			ImageView promoimage;
		}

		public void add(Course country) {
			// Log.v("AddView", country.getCode());
			this.countryList.add(country);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getApplicationContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.course_overview, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView
						.findViewById(R.id.coursename);
				holder.name = (TextView) convertView.findViewById(R.id.author);
				holder.cost = (TextView) convertView.findViewById(R.id.cost);
				holder.cover = (ImageView) convertView.findViewById(R.id.cover);
				holder.promoimage = (ImageView) convertView
						.findViewById(R.id.promoimage);
				holder.enroll = (TextView) convertView
						.findViewById(R.id.enroll);
				holder.ratingshow = (ImageView) convertView
						.findViewById(R.id.ratingimage);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Course country = this.countryList.get(position);
			holder.code.setText(country.getCode());
			holder.name.setText(country.getName());
			holder.cost.setText("$ " + country.getRegion());
			holder.cost.setTextColor(Color.parseColor("#4B9500"));
			holder.cover.setImageBitmap(country.getBitmap());
			// aQuery = new AQuery(getActivity());
			// aQuery.id(R.id.cover).image(country.getstringurl(),true,true);
			Picasso.with(MyFavoriteCategoryCourses.this)
					.load(country.getstringurl()).into(holder.cover);

			// new DownloadTask((ImageView)
			// convertView.findViewById(R.id.cover))
			// .execute((String) country.getstringurl());

			if (country.getpromocheck().equalsIgnoreCase("1")) {
				holder.promoimage.setImageResource(R.drawable.promocode);
			} else {
				holder.promoimage.setImageResource(R.drawable.click);
			}
			if (country.getifmycourse().equalsIgnoreCase("1")) {
				holder.enroll.setVisibility(View.VISIBLE);
			} else {
				holder.enroll.setVisibility(View.INVISIBLE);
			}

			if (country.getrating().equalsIgnoreCase("0")) {
				holder.ratingshow.setImageResource(R.drawable.zero);
			} else if (country.getrating().equalsIgnoreCase("1")) {
				holder.ratingshow.setImageResource(R.drawable.one);
			} else if (country.getrating().equalsIgnoreCase("2")) {
				holder.ratingshow.setImageResource(R.drawable.two);
			} else if (country.getrating().equalsIgnoreCase("3")) {
				holder.ratingshow.setImageResource(R.drawable.three);
			} else if (country.getrating().equalsIgnoreCase("4")) {
				holder.ratingshow.setImageResource(R.drawable.four);
			} else if (country.getrating().equalsIgnoreCase("5")) {
				holder.ratingshow.setImageResource(R.drawable.five);
			} else {
				holder.ratingshow.setImageResource(R.drawable.zero);
			}
			return convertView;

		}

	}

	class fetchpurnumber extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MyFavoriteCategoryCourses.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("course_id", courseidurl));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.purchasenumberselection, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						numofrows = jUser.getString(TAG_NUMBER_OF_ROWS);

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

			pDialog.dismiss();
			if (checkstatus.equalsIgnoreCase("1")) {
				Intent i = new Intent(getApplicationContext(),
						CourseDetails.class);
				i.putExtra("courseid", course_id_topass);
				i.putExtra("course_name", course_name_to_pass);
				i.putExtra("course_description", course_descript_to_pass);
				i.putExtra("instructor_id", instructoridurl);
				i.putExtra("enroll_students", course_enrolled_passing);
				i.putExtra("audio_url", audiourlpassing);
				i.putExtra("rating", rating_count);
				startActivity(i);
			}

			// String
			// url="http://208.109.248.89:8085/OnlineCourse/Student/student_view_Course?course_id=50&authorid=1&pur=0&catcourse=&coursetype=";
			else {
				String url = Config.common_url + "?course_id=" + courseidurl
						+ "&authorid=" + instructoridurl + "&pur=" + numofrows
						+ "&catcourse=&coursetype=";

				Intent ii = new Intent(Intent.ACTION_VIEW);
				ii.setData(Uri.parse(url));
				ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(ii);
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// super.onCreateOptionsMenu(menu, inflater);
		// inflater.inflate(R.menu.chosecategory, menu);

		getSupportMenuInflater().inflate(R.menu.removemyfavorite, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.removefav:
			new removecat().execute();
			finish();
			return true;
		case android.R.id.home:
			finish();

			return true;
		default:
		}
		return false;
	}

	class removecat extends AsyncTask<String, String, String> {
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

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("category_name", category_name));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.removefrommycategoryurl, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						numofrows = jUser.getString(TAG_SUCCESS);

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

			// pDialog.dismiss();

		}

	}

	@Override
	public void onBackPressed() {

	}
}