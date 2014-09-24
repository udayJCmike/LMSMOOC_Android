package com.deemsys.lmsmooc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.media.MediaPlayer.OnPreparedListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class CourseDetails extends SherlockFragmentActivity{
	TextView course_nam,enrol;
	static String course_id;
	String course_name;
	String course_enrolls;
	static String course_description,instructorid;
	ProgressDialog pDialog;
  VideoView videoview;
    String audio_url;
    String videouri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_details);
		
		course_nam=(TextView)findViewById(R.id.coursename);
		enrol=(TextView)findViewById(R.id.enrollcourse);
		 Intent i = getIntent();
		 course_id = i.getExtras().getString("courseid","");
		 course_name = i.getExtras().getString("course_name","");
		 course_description =i.getExtras().getString("course_description","");
		 instructorid=i.getExtras().getString("instructor_id","");
		 course_enrolls=i.getExtras().getString("enroll_students","");
		 audio_url=i.getExtras().getString("audio_url","");
		 System.out.println("instructor id value"+instructorid);
	     course_nam.setText(course_name);
	     enrol.setText(course_enrolls);
	     videoview=(VideoView)findViewById(R.id.videoView1);
	     ActionBar ab = getSupportActionBar();
	     ab.setDisplayHomeAsUpEnabled(true);
	     getSupportActionBar().setHomeButtonEnabled(true);
	     setTitle(Html.fromHtml("<font color=\"white\">" + course_name + "</font>"));
	     ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);  
        mViewPager.setAdapter(new CourseDetailPageAdapter(getSupportFragmentManager()));
//        pDialog = new ProgressDialog(CourseDetails.this);
//        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
//        // Set progressbar message
//        pDialog.setMessage("Buffering...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//      
//        pDialog.show();
  videouri=LoginActivity.avatar_url+"54"+"/"+audio_url;
 System.out.println("video uri in course details"+videouri);
 videoview.setOnTouchListener(new View.OnTouchListener()
 {
     @Override
     public boolean onTouch(View v, MotionEvent event) {

    	 try {
	            // Start the MediaController
	            MediaController mediacontroller = new MediaController(
	                    CourseDetails.this);
	            mediacontroller.setAnchorView(videoview);
	           System.out.println("in video onclick");
	            Uri video = Uri.parse(videouri);
	            videoview.setMediaController(mediacontroller);
	            videoview.setVideoURI(video);
	           
	 
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	 
	        videoview.requestFocus();
	        videoview.setOnPreparedListener(new OnPreparedListener() {
	            
	            public void onPrepared(MediaPlayer mp) {
	                pDialog.dismiss();
	                System.out.println("in video start");
	                videoview.start();
	            }
	        });

         return false;
     }

	
 });

	}
	  @Override
		 public void onBackPressed() 
	 {

	 }
	  @Override
	    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
	        switch (item.getItemId()) {

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
		
}
