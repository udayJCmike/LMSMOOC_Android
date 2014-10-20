package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.AuthorFragment.InsertFavorite;
import com.deemsys.lmsmooc.AuthorFragment.UpdateFavorite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutCourseFragment extends Fragment {
	TextView aboutcourse;
	Button favunfav;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	JSONArray user = null;
	String favcheck;
	private static final String TAG_FAV_COURSE = "favcheck";
	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_author_list = "Author List";
	private static final String TAG_SUCCESS = "success";
	public ProgressDialog pDialog;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.aboucoursefragment, container, false);
		setHasOptionsMenu(true);
		aboutcourse = (TextView) v.findViewById(R.id.aboutcourse_text);
		aboutcourse.setText(Html.fromHtml(CourseDetails.course_description));
		aboutcourse.setMovementMethod(new ScrollingMovementMethod());
		favunfav = (Button) v.findViewById(R.id.favunfav);
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new FavCourse().execute();
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

		favunfav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isInternetPresent = cd.isConnectingToInternet();
				if (isInternetPresent && favcheck.equalsIgnoreCase("0")) {
					new InsertFavorite().execute();
					favcheck = "1";
					favunfav.setBackgroundColor(Color
							.parseColor("#F0AD4E"));
					favunfav.setText("Remove from favorites");
				} else if (isInternetPresent
						&& favcheck.equalsIgnoreCase("1")) {
					new UpdateFavorite().execute();
					favcheck = "0";
					favunfav.setBackgroundColor(Color
							.parseColor("#5CB85C"));
					favunfav.setText("Add to favorites");
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

					alertDialog.setTitle("Sorry User");

					alertDialog.setMessage("No network connection.");

					alertDialog.setIcon(R.drawable.delete);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {

								}
							});

					alertDialog.show();
				}
			}
		});
		return v;

	}
	class UpdateFavorite extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("course_id",
					CourseDetails.course_id));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.removefromfavorites, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						

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

		}

	}
	class InsertFavorite extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("course_id",
					CourseDetails.course_id));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			params1.add(new BasicNameValuePair("course_name",
					CourseDetails.course_name));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.addtofavorites, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						

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

		}

	}
	class FavCourse extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			pDialog.dismiss();
			if (favcheck.equalsIgnoreCase("0")) {

				favunfav.setBackgroundColor(Color.parseColor("#5CB85C"));
				favunfav.setText("Add to favorites");
			} else if (favcheck.equalsIgnoreCase("1")) {
				favunfav.setBackgroundColor(Color.parseColor("#F0AD4E"));
				favunfav.setText("Remove from favorites");
			}

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("course_id",
					CourseDetails.course_id));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));

			JSONObject json = jsonParser.makeHttpRequest(Config.ServerUrl
					+ Config.ifmyfavcourse, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						favcheck = jUser.getString(TAG_FAV_COURSE);
						System.out.println("fav check value::" + favcheck);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			return null;
		}
	}
}
