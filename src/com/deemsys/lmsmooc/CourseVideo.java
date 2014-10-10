package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.widget.MediaController;
import android.widget.VideoView;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CourseVideo extends SherlockFragmentActivity {
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CONTENT = "video";
	String videouri, audio_url;
	String lect_id, lect_name, lect_type, sect_id, sect_name, course_contents,
			successL;

	VideoView videoview;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public ProgressDialog pDialog;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#3399FF")));
		getActionBar()
				.setTitle(
						Html.fromHtml("<font color=\"white\">" + lect_name
								+ "</font>"));
		getActionBar().hide();
		setContentView(R.layout.course_video);
		videoview = (VideoView) findViewById(R.id.videoview);
		Intent i = getIntent();
		lect_id = i.getExtras().getString("lect_id", "");
		lect_name = i.getExtras().getString("lecture_name", "");
		lect_type = i.getExtras().getString("lect_type", "");
		sect_id = i.getExtras().getString("section_id", "");
		sect_name = i.getExtras().getString("section_name", "");
		audio_url = i.getExtras().getString("audio_url", "");
		// course_content=(TextView)findViewById(R.id.course_text);
		cd = new ConnectionDetector(getApplicationContext());

		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {

		
			new getCourse().execute();

		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(CourseVideo.this)
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

	class getCourse extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CourseVideo.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("course_id",
					CourseDetails.course_id));
			params1.add(new BasicNameValuePair("lecture_id", lect_id));
			params1.add(new BasicNameValuePair("section_id", sect_id));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.coursecontentvideo, "POST", params1);
		
			if (json != null) {
				try {
					if (json != null) {
					

						JSONObject jUser = json
								.getJSONObject("serviceresponse");
						successL = jUser.getString(TAG_SUCCESS);
						course_contents = jUser.getString(TAG_CONTENT);

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

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
		
			pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {
			
				AlertDialog alertDialog = new AlertDialog.Builder(
						getApplicationContext()).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Server not connected.");

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

				// pDialog.dismiss();
			} else if (successL.equalsIgnoreCase("No")) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						getApplicationContext()).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Invalid username or password.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed

								dialog.dismiss();
							}
						});

				// Showing Alert Message
				alertDialog.show();

				// pDialog.dismiss();
			} else {
				// videouri=LoginActivity.avatar_url+"54"+"/"+audio_url;
				videouri = LoginActivity.avatar_url + CourseDetails.course_id
						+ "/" + sect_id + "/" + lect_id + "/" + course_contents;
			
				try {
					// Start the MediaController
					MediaController mediacontroller = new MediaController(
							CourseVideo.this);
					mediacontroller.setAnchorView(videoview);
				
					Uri video = Uri.parse(videouri);
					videoview.setMediaController(mediacontroller);
					videoview.setVideoURI(video);

				} catch (Exception e) {
					
					e.printStackTrace();
					AlertDialog alertDialog = new AlertDialog.Builder(
							CourseVideo.this).create();

					alertDialog.setTitle("INFO!");

					alertDialog.setMessage("Video cannot be played.");

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

				videoview.requestFocus();
				videoview.setOnPreparedListener(new OnPreparedListener() {

					public void onPrepared(MediaPlayer mp) {
						pDialog.dismiss();
					
						videoview.start();
					}
				});

			}

		}

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}