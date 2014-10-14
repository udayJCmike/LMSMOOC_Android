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
import com.squareup.picasso.Picasso;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class PaidCoursesbrowse extends Fragment {
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
	String promocheck;
	private static final String Promo_Check = "promocheck";
	String course_description;
	private static final String TAG_COURSE_DESCRIPTION = "course_description";
	String course_name, authorname, student_enrolled, ratingcouont, cost,
			course_id, instructorid, numofrows, course_cover_image,
			ifmycoursepresent;
	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_Course_ARRAY = "CourseList";
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_COURSE_NAME = "course_name";
	private static final String TAG_COURSE_AUTHOR = "course_author";
	// private static final String TAG_COURSE_SUBTITLE= "course_sub_title";
	private static final String TAG_COURSE_COST = "course_price";
	private static final String TAG_COURSE_RATINGS = "user_ratings";
	private static final String TAG_course_cover_image = "course_cover_image";
	static String avatar_url, successL, avatarurl;
	private static final String TAG_AVATAR_URL = "avatar_url";
	private static final String TAG_INSTRUCTOR_ID = "instructor_id";
	private static final String TAG_COURSE_ID = "course_id";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NUMBER_OF_ROWS = "number_of_rows";
	String courseidurl, instructoridurl, pur_url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.allcourses, container, false);
		setHasOptionsMenu(true);

		listView = (ListView) v.findViewById(R.id.listView1);
		avatarurl = Config.ServerUrl + "Login.php?service=avatarbrowse";
		getActivity();
		loadMoreView = ((LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loadmore,
				null, false);
		listView.addFooterView(loadMoreView);

		return v;

	}

	boolean _areLecturesLoaded = false;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && !_areLecturesLoaded) {
			loaddatas();
			_areLecturesLoaded = true;
		}
	}

	public void loaddatas() {

		courselist = new ArrayList<Course>();
		
		dataAdapter = new MyCustomAdapter(getActivity(),
				R.layout.course_overview, courselist);
		listView.setAdapter(dataAdapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (position < courselist.size()) {
					Course country = (Course) parent
							.getItemAtPosition(position);

					courseidurl = country.getcourseid();
					instructoridurl = country.getinsid();
					new Avatarfetch().execute();
					// Toast.makeText(getActivity(),
					// country.getcourseid(), Toast.LENGTH_SHORT).show();
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

					
					grabURL(Config.ServerUrl + Config.paidcourseurl);
				}
			}
		});
	}

	public void grabURL(String url) {
	
		new GrabURL().execute(url);
	}

	public static PaidCoursesbrowse newInstance(String text) {

		PaidCoursesbrowse f = new PaidCoursesbrowse();
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

		// private boolean error = false;
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(getActivity());
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			// cDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			if (user.length() == 0 && start == 0) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						getActivity()).create();

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
			displayCourseList(content);
			cDialog.dismiss();

		}

		private void displayCourseList(String response) {

			// JSONObject responseObj = null;

			try {

				JSONObject c = jArray.getJSONObject(TAG_SRES);
				

				JSONArray countryListObj = c.getJSONArray(TAG_Course_ARRAY);

				if (countryListObj.length() == 0)

				{

					listView.removeFooterView(loadMoreView);
				} else

				{

					for (int i = 0; i < countryListObj.length(); i++)

					{
						start++;
						
						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);
						authorname = c2.getString(TAG_COURSE_AUTHOR);
						instructorid = c2.getString(TAG_INSTRUCTOR_ID);
						course_id = c2.getString(TAG_COURSE_ID);
						course_name = c2.getString(TAG_COURSE_NAME);
						promocheck = c2.getString(Promo_Check);
						// course_cover_image="http://208.109.248.89/lmsvideos/28/coverImage.jpg";
						course_cover_image = c2
								.getString(TAG_course_cover_image);
						course_description = c2
								.getString(TAG_COURSE_DESCRIPTION);
						cost = c2.getString(TAG_COURSE_COST);
						ratingcouont = c2.getString(TAG_COURSE_RATINGS);
						coursetotallist.add(authorname);
						coursetotallist.add(course_name);
						coursetotallist.add(ratingcouont);

						imagelist.add(course_cover_image);

						Course cnt = new Course(authorname, course_name, cost,
								course_id, instructorid, course_cover_image,
								ratingcouont, ifmycoursepresent, promocheck);
						cnt.setName(authorname);
						cnt.setCode(course_name);
						cnt.setins_id(instructorid);
						cnt.setcourseid(course_id);
						cnt.setrating(ratingcouont);
						cnt.setstringurl(course_cover_image);
						cnt.setpromocheck(promocheck);
						cnt.setifmycourse(ifmycoursepresent);
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
						2);
				nameValuePairs.add(new BasicNameValuePair("start", String
						.valueOf(start)));
				nameValuePairs.add(new BasicNameValuePair("limit", String
						.valueOf(limit)));
				jArray = jsonParser.makeHttpRequest(Config.ServerUrl
						+ Config.paidcoursebrowseurl, "POST", nameValuePairs);
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
				// error = true;
				cancel(true);
			} catch (IOException e) {
			
				content = e.getMessage();
				// error = true;
				cancel(true);
			} catch (Exception e) {
				
				content = e.getMessage();
				// error = true;
				cancel(true);
			}
			// }catch(JSONException e)
			// {
			// e.printStackTrace();
			// }
			//
			//

			// }catch(Exception e)
			// {
			// e.printStackTrace();
			// }
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
			ImageView cover;
			ImageView promoimage;
			TextView cost;
			ImageView ratingshow;
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

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Course country = this.countryList.get(position);
			holder.code.setText(country.getCode());
			holder.name.setText(country.getName());
			holder.cost.setText("$ " + country.getRegion());
			holder.cover.setImageBitmap(country.getBitmap());
			// aQuery = new AQuery(getActivity());
			// aQuery.id(R.id.cover).image(country.getstringurl(),true,true);
			Picasso.with(getActivity()).load(country.getstringurl())
					.into(holder.cover);

			// new DownloadTask((ImageView)
			// convertView.findViewById(R.id.cover))
			// .execute((String) country.getstringurl());
			if (country.getpromocheck().equalsIgnoreCase("1")) {
				holder.promoimage.setImageResource(R.drawable.promocode);
			} else {
				holder.promoimage.setImageResource(R.drawable.click);
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
			// pDialog.show();

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

			// String
			// url="http://208.109.248.89:8085/OnlineCourse/Student/student_view_Course?course_id=50&authorid=1&pur=0&catcourse=&coursetype=";
			String url = Config.browsecommon_url + "?course_id=" + courseidurl
					+ "&authorid=" + instructoridurl + "&pur=" + numofrows
					+ "&catcourse=&coursetype=";
		
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			getActivity().startActivity(i);

		}

	}

	
	class Avatarfetch extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", "3"));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(avatarurl, "POST", params1);
		
			if (json != null) {
				try {
					if (json != null) {
						

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);
						avatar_url = jUser.getString(TAG_AVATAR_URL);
						Config.browsecommon_url = jUser
								.getString(TAG_AVATAR_URL);
						;
						
					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

				successL = "No";
			}
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			// pDialog.dismiss();
			new fetchpurnumber().execute();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (pDialog != null)
			pDialog.dismiss();
	}
}