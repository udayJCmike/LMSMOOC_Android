package com.deemsys.lmsmooc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.deemsys.lmsmooc.InboxFragment.Details;
import com.squareup.picasso.Picasso;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyFavoriteCourses extends Fragment {
	Bitmap bitmap;

	public ProgressDialog cDialog, pDialog;
	public static ArrayList<String> coursetotallist = new ArrayList<String>();
	public static ArrayList<String> imagelist = new ArrayList<String>();
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
	String course_subtitle,course_subtitle_topass;
	private static final String TAG_COURSE_SUBTITLE = "course_sub_title";
	String rating_count;
	private static final String TAG_ENROLLED_STUDENT = "course_enrolled";
	String promocheck;
	
	private static final String Promo_Check = "promocheck";
	private static final String TAG_SUCCESS = "success";
	String course_description;
	private static final String TAG_COURSE_DESCRIPTION = "course_description";
	String course_name, authorname, student_enrolled, ratingcouont, cost,
			course_id, instructorid, numofrows, course_cover_image,
			ifmycoursepresent, audiourl, audiourlpassing;
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

	private static final String TAG_NUMBER_OF_ROWS = "number_of_rows";
	private static final String TAG_COURSE_PROMO_VIDEO = "course_promo_video";
	String courseidurl, instructoridurl, pur_url, checkstatus;
	String course_id_topass, course_name_to_pass, course_descript_to_pass,
			course_enrolled, course_enrolled_passing;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.allcourses, container, false);
		setHasOptionsMenu(true);

		listView = (ListView) v.findViewById(R.id.listView1);
		listView.setLongClickable(true);
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		
		

		return v;
	}

	public void grabURL(String url) {

		new GrabURL().execute(url);
	}

	protected void removeItemFromList(int position, String courseid,
			String coursename) {
		final int deletePosition = position;
		final String course_id_get = courseid;

		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Delete");
		alert.setMessage("Do you want delete this item?");
		alert.setPositiveButton("YES", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				new removefromfav().execute(course_id_get);
				courselist.remove(deletePosition);
				// dataAdapter.remove(dataAdapter.getItem(deletePosition));
				// dataAdapter.notifyDataSetChanged();
				dataAdapter.removeElementAtPosition(deletePosition);
				// dataAdapter.notifyDataSetInvalidated();

			}
		});
		alert.setNegativeButton("CANCEL", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		alert.show();

	}

	class removefromfav extends AsyncTask<String, String, String> {
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
			String identifier = params[0];
			params1.add(new BasicNameValuePair("course_id", identifier));
			// params1.add(new BasicNameValuePair("course_name", course_name));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.removefromfavcourseurl, "POST", params1);

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

	class GrabURL extends AsyncTask<String, Void, String> {
		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();
		final HttpParams params = httpclient.getParams();
		HttpResponse response;
		private String content = null;

		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(getActivity());
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			cDialog.show();
		}

		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			courselist = new ArrayList<Course>();
			dataAdapter = new MyCustomAdapter(getActivity(),
					R.layout.course_overview, courselist);
			listView.setAdapter(dataAdapter);
			
			
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
						System.out.println("status check" + checkstatus);
						courseidurl = country.getcourseid();
						instructoridurl = country.getinsid();
						rating_count = country.getrating();
						course_subtitle_topass=country.getsubtitle();
						new fetchpurnumber().execute();

					}
				}
			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					System.out.println("postition value::" + position);

					Course country = (Course) arg0.getItemAtPosition(position);
					if (position < courselist.size()) {
						courseidurl = country.getcourseid();
						course_name_to_pass = country.getCode();
						removeItemFromList(position, courseidurl,
								course_name_to_pass);
					}
					return true;
				}
			});
			displayCourseList(content);
			dataAdapter.notifyDataSetChanged();
			cDialog.dismiss();

		}

		@SuppressWarnings("deprecation")
		private void displayCourseList(String response) {

			try {
				courselist.clear();
				JSONObject c = jArray.getJSONObject(TAG_SRES);

				JSONArray countryListObj = c.getJSONArray(TAG_Course_ARRAY);
//				if (courselist.size() == 0 && start == 0&&countryListObj.length() == 0) {
//					AlertDialog alertDialog = new AlertDialog.Builder(
//							getActivity()).create();
//
//					// Setting Dialog Title
//					alertDialog.setTitle("Sorry User");
//
//					// Setting Dialog Message
//					alertDialog.setMessage("No data found.");
//
//					// Setting Icon to Dialog
//					alertDialog.setIcon(R.drawable.delete);
//
//					// Setting OK Button
//					alertDialog.setButton("OK",
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(
//										final DialogInterface dialog,
//										final int which) {
//									// Write your code here to execute after
//									// dialog
//									// closed
//
//								}
//							});
//
//					// Showing Alert Message
//					alertDialog.show();
//				}
				if (countryListObj.length() == 0)

				{
					if (courselist.size() == 0 && start == 0) {
						AlertDialog alertDialog = new AlertDialog.Builder(
								getActivity()).create();

						// Setting Dialog Title
						alertDialog.setTitle("Sorry User");

						// Setting Dialog Message
						alertDialog.setMessage("No data found.");

						// Setting Icon to Dialog
						alertDialog.setIcon(R.drawable.delete);

						// Setting OK Button
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {
										// Write your code here to execute after
										// dialog
										// closed

									}
								});

						// Showing Alert Message
						alertDialog.show();
					}

					// listView.removeFooterView(loadMoreView);
				} else

				{

					for (int i = 0; i < countryListObj.length(); i++)

					{
						start++;
						System.out.println("countryListObj length"
								+ countryListObj.length());
						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);
						authorname = c2.getString(TAG_COURSE_AUTHOR);
						instructorid = c2.getString(TAG_INSTRUCTOR_ID);
						course_id = c2.getString(TAG_COURSE_ID);
						course_name = c2.getString(TAG_COURSE_NAME);
						audiourl = c2.getString(TAG_COURSE_PROMO_VIDEO);
						course_enrolled = c2.getString(TAG_ENROLLED_STUDENT);
						course_cover_image = c2
								.getString(TAG_course_cover_image);
						cost = c2.getString(TAG_COURSE_COST);
						ifmycoursepresent = c2.getString(TAG_Check_);
						ratingcouont = c2.getString(TAG_COURSE_RATINGS);
						promocheck = c2.getString(Promo_Check);
						coursetotallist.add(course_enrolled);
						course_description = c2
								.getString(TAG_COURSE_DESCRIPTION);
						course_subtitle = c2.getString(TAG_COURSE_SUBTITLE);
						coursetotallist.add(authorname);
						coursetotallist.add(course_name);
						coursetotallist.add(ratingcouont);
						coursetotallist.add(ifmycoursepresent);
						coursetotallist.add(audiourl);
						coursetotallist.add(course_enrolled);
						coursetotallist.add(course_subtitle);
						imagelist.add(course_cover_image);

						Course cnt = new Course(authorname, course_name, cost,
								course_id, instructorid, course_cover_image,
								ratingcouont, ifmycoursepresent, audiourl,
								promocheck);
						cnt.setName(authorname);
						cnt.setCode(course_name);
						cnt.setins_id(instructorid);
						cnt.setcourseid(course_id);
						cnt.setrating(ratingcouont);
						cnt.setifmycourse(ifmycoursepresent);
						cnt.setstringurl(course_cover_image);
						cnt.setaudiourl(audiourl);
						cnt.setstudentsenrolled(course_enrolled);
						cnt.setdescription(course_description);
						cnt.setpromocheck(promocheck);
						cnt.setsubtitle(course_subtitle);
						courselist.add(cnt);

						System.out.println("size fo country list"
								+ courselist.size());
						System.out
								.println("value fo country list" + courselist);
						dataAdapter.add(cnt);
						System.out.println("bitmap" + bitmap);
						System.out.println("i value" + i);
						if (i == 9) {

						}
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
						2);
				nameValuePairs.add(new BasicNameValuePair("start", String
						.valueOf(start)));
				nameValuePairs.add(new BasicNameValuePair("limit", String
						.valueOf(limit)));
				nameValuePairs.add(new BasicNameValuePair("student_id",
						Config.student_id));
				jArray = jsonParser.makeHttpRequest(Config.ServerUrl
						+ Config.myfavoritecourseurl, "POST", nameValuePairs);
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
			//TextView subs;
			TextView code;
			TextView name;
			ImageView cover;
			TextView cost;
			ImageView ratingshow;
			ImageView promoimage;
			TextView enroll;
		}

		public void add(Course country) {

			this.countryList.add(country);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.course_overview, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView
						.findViewById(R.id.coursename);
				holder.name = (TextView) convertView.findViewById(R.id.author);
				holder.cost = (TextView) convertView.findViewById(R.id.cost);
				holder.cover = (ImageView) convertView.findViewById(R.id.cover);
				holder.ratingshow = (ImageView) convertView
						.findViewById(R.id.ratingimage);
				holder.promoimage = (ImageView) convertView
						.findViewById(R.id.promoimage);
				holder.enroll = (TextView) convertView
						.findViewById(R.id.enroll);
//				holder.subs = (TextView) convertView
//						.findViewById(R.id.coursesubtitle);
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
		//	holder.subs.setText(country.getsubtitle());
			Picasso.with(getActivity()).load(country.getstringurl())
					.into(holder.cover);

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

		public void removeElementAtPosition(int position) {
			this.countryList.remove(position);
			notifyDataSetChanged();
		}

	}

	public class DownloadTask extends AsyncTask<String, Void, Boolean> {
		ImageView v;
		String url;
		Bitmap bm;

		public DownloadTask(ImageView v) {
			this.v = v;
		}

		@Override
		protected Boolean doInBackground(String... params) {
			url = params[0];
			bm = loadBitmap(url);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			v.setImageBitmap(bm);
			cDialog.dismiss();
		}

		public Bitmap loadBitmap(String url) {
			try {
				URL newurl = new URL(url);
				Bitmap b = BitmapFactory.decodeStream(newurl.openConnection()
						.getInputStream());
				return b;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	class fetchpurnumber extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
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
			System.out.println("value for json::" + json);
			if (json != null) {
				try {
					if (json != null) {
						System.out.println("json value::" + json);

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						numofrows = jUser.getString(TAG_NUMBER_OF_ROWS);
						System.out.println("number of rows value:::"
								+ numofrows);

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
			System.out.println("in post execute");
			pDialog.dismiss();

			if (checkstatus.equalsIgnoreCase("1")) {
				Intent i = new Intent(getActivity(), CourseDetails.class);
				i.putExtra("courseid", course_id_topass);
				i.putExtra("course_name", course_name_to_pass);
				i.putExtra("course_description", course_descript_to_pass);
				i.putExtra("instructor_id", instructoridurl);
				i.putExtra("enroll_students", course_enrolled_passing);
				i.putExtra("audio_url", audiourlpassing);
				i.putExtra("rating", rating_count);
				i.putExtra("course_subtitle", course_subtitle_topass);
				startActivity(i);
			}

			// String
			// url="http://208.109.248.89:8085/OnlineCourse/Student/student_view_Course?course_id=50&authorid=1&pur=0&catcourse=&coursetype=";
			else {
				String url = Config.common_url + "?course_id=" + courseidurl
						+ "&authorid=" + instructoridurl + "&pur=" + numofrows
						+ "&catcourse=&coursetype=";
				System.out.println("url value" + url);
				Intent ii = new Intent(Intent.ACTION_VIEW);
				ii.setData(Uri.parse(url));
				getActivity().startActivity(ii);
			}

		}

	}
	@SuppressWarnings("deprecation")
	@Override
	public void onResume() {
		super.onResume();

		if (isInternetPresent) {
			grabURL(Config.ServerUrl + Config.mycourseurl);
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();

			alertDialog.setTitle("Sorry User");

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