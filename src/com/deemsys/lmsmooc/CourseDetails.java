package com.deemsys.lmsmooc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.media.MediaPlayer.OnPreparedListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;

public class CourseDetails extends SherlockFragmentActivity {
	TextView course_nam, enrol, coursesub;
	static String course_id;
	static String course_name;
	String course_enrolls;
	static String course_description, instructorid;
	ProgressDialog pDialog;
	VideoView videoview;
	String audio_url;
	String videouri;
	String ratingsho;
	ImageView ratingshow;
	ImageButton mPlayButton;
	String numberofrows;
	String course_subtitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_details);

		course_nam = (TextView) findViewById(R.id.coursename);
		coursesub = (TextView) findViewById(R.id.coursesubtitle);
		enrol = (TextView) findViewById(R.id.enrollcourse);
		mPlayButton = (ImageButton) findViewById(R.id.play_button);
		Intent i = getIntent();
		course_id = i.getExtras().getString("courseid", "");
		course_name = i.getExtras().getString("course_name", "");
		course_description = i.getExtras().getString("course_description", "");
		instructorid = i.getExtras().getString("instructor_id", "");
		course_enrolls = i.getExtras().getString("enroll_students", "");
		audio_url = i.getExtras().getString("audio_url", "");
		ratingsho = i.getExtras().getString("rating", "");
		numberofrows = i.getExtras().getString("numofrows", "");
		course_subtitle = i.getExtras().getString("course_subtitle", "");
		System.out.println("gcfg" + course_subtitle);
		course_nam.setText(course_name);
		enrol.setText(course_enrolls);
		coursesub.setText(course_subtitle);
		ratingshow = (ImageView) findViewById(R.id.ratingimage);
		videoview = (VideoView) findViewById(R.id.videoView1);
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		setTitle(Html.fromHtml("<font color=\"white\">" + course_name
				+ "</font>"));
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setBackgroundColor(Color.WHITE);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(new CourseDetailPageAdapter(
				getSupportFragmentManager()));
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.parseColor("#3399FF"));

		if (ratingsho.equalsIgnoreCase("0")) {
			ratingshow.setImageResource(R.drawable.zero);
		} else if (ratingsho.equalsIgnoreCase("1")) {
			ratingshow.setImageResource(R.drawable.one);
		} else if (ratingsho.equalsIgnoreCase("2")) {
			ratingshow.setImageResource(R.drawable.two);
		} else if (ratingsho.equalsIgnoreCase("3")) {
			ratingshow.setImageResource(R.drawable.three);
		} else if (ratingsho.equalsIgnoreCase("4")) {
			ratingshow.setImageResource(R.drawable.four);
		} else if (ratingsho.equalsIgnoreCase("5")) {
			ratingshow.setImageResource(R.drawable.five);
		} else {
			ratingshow.setImageResource(R.drawable.zero);
		}

		// videouri=
		// "rtsp://v3.cache8.c.youtube.com/CiILENy73wIaGQmXovF6e-Rf-BMYDSANFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";
		videouri = LoginActivity.avatar_url + course_id + "/" + audio_url;
		// String path = "android.resource://" + getPackageName() + "/" +
		// R.raw.promo;
		// view.setVideoURI(Uri.parse(path));
		// System.out.println("video uri in course details"+videouri);

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					CourseDetails.this);
			mediacontroller.setAnchorView(videoview);

			Uri video = Uri.parse(videouri);
			videoview.setMediaController(mediacontroller);
			videoview.setVideoURI(video);

		} catch (Exception e) {

			e.printStackTrace();
		}

		videoview.requestFocus();
		videoview.setOnPreparedListener(new OnPreparedListener() {

			public void onPrepared(MediaPlayer mp) {

			}
		});

		mPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				videoview.start();

				mPlayButton.setVisibility(View.GONE);
			}

		});

	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.share:
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			String url = Config.URL_COMMON + "User_view_Course?kfkgd="
					+ course_id;

			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					course_name);
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		case android.R.id.home:
			videoview.stopPlayback();
			videoview.cancelLongPress();
			finish();

			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getSupportMenuInflater().inflate(R.menu.share, menu);
		return true;
	}
}
