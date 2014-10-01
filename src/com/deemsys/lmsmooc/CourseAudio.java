package com.deemsys.lmsmooc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;



import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;

import android.view.MotionEvent;
import android.view.View;

import android.view.View.OnTouchListener;

import android.widget.ImageButton;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CourseAudio extends SherlockActivity implements OnTouchListener, OnCompletionListener, OnBufferingUpdateListener{
	private static final String TAG_SUCCESS = "success";
	 private static final String TAG_CONTENT = "audio";
	  String videouri,audio_url;
	String lect_id,lect_name,lect_type,sect_id,sect_name,course_contents,successL;
   public TextView startTimeField,endTimeField;
   private MediaPlayer mediaPlayer;
   private double startTime = 0;
   private double finalTime = 0;
   private Handler myHandler = new Handler();;
   private int forwardTime = 5000; 
   private int backwardTime = 5000;
   private SeekBar seekbar;
   private ImageButton playButton,pauseButton;
   Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	 public ProgressDialog pDialog;
   public static int oneTimeOnly = 0;
   @SuppressWarnings("deprecation")
@Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.course_audio);
      ActionBar ab = getSupportActionBar();
      ab.setDisplayHomeAsUpEnabled(true);
 ab.setHomeButtonEnabled(true);
      ab.setTitle(Html.fromHtml("<font color='#ffffff'>Search</font>"));
	    
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
        Intent i = getIntent();
        lect_id = i.getExtras().getString("lect_id","");
        lect_name = i.getExtras().getString("lecture_name","");
        lect_type =i.getExtras().getString("lect_type","");
        sect_id=i.getExtras().getString("section_id","");
        sect_name=i.getExtras().getString("section_name","");
   	 audio_url=i.getExtras().getString("audio_url","");
      startTimeField =(TextView)findViewById(R.id.textView1);
      endTimeField =(TextView)findViewById(R.id.textView2);
      seekbar = (SeekBar)findViewById(R.id.seekBar1);
      playButton = (ImageButton)findViewById(R.id.imageButton1);
      pauseButton = (ImageButton)findViewById(R.id.imageButton2);
      mediaPlayer = new MediaPlayer();
      seekbar.setOnTouchListener(this);
      playButton.setEnabled(false);
      pauseButton.setEnabled(false);
      cd = new ConnectionDetector(getApplicationContext());
      getActionBar().setTitle(Html.fromHtml("<font color=\"white\">" + lect_name + "</font>"));
  	isInternetPresent = cd.isConnectingToInternet();
  	if(isInternetPresent)
		{
			
			System.out.println("inside getting course");
			  new getaudiourl().execute();

		
		}
	else
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				CourseAudio.this).create();

		alertDialog.setTitle("INFO!");

		alertDialog.setMessage("No network connection.");

		alertDialog.setIcon(R.drawable.delete);
		
		alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

					public void onClick(final DialogInterface dialog,
							final int which) {
						
					}
				});

		
		alertDialog.show();
		
	}
	
      getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
    

   }

   public void play(View view){
   Toast.makeText(getApplicationContext(), "Playing sound", 
   Toast.LENGTH_SHORT).show();
   try {
	   String datasource=LoginActivity.avatar_url+CourseDetails.course_id+"/"+sect_id+"/"+lect_id+"/"+course_contents;
	   System.out.println("datasource value"+datasource);
	mediaPlayer.setDataSource(LoginActivity.avatar_url+CourseDetails.course_id+"/"+sect_id+"/"+lect_id+"/"+course_contents);
	//
	mediaPlayer.prepare();
} catch (IllegalArgumentException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SecurityException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IllegalStateException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
      mediaPlayer.start();
      finalTime = mediaPlayer.getDuration();
      startTime = mediaPlayer.getCurrentPosition();
      if(oneTimeOnly == 0){
         seekbar.setMax((int) finalTime);
         oneTimeOnly = 1;
      } 

      endTimeField.setText(String.format("%d min, %d sec", 
         TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
         TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - 
         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
         toMinutes((long) finalTime)))
      );
      startTimeField.setText(String.format("%d min, %d sec", 
         TimeUnit.MILLISECONDS.toMinutes((long) startTime),
         TimeUnit.MILLISECONDS.toSeconds((long) startTime) - 
         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
         toMinutes((long) startTime)))
      );
      seekbar.setProgress((int)startTime);
  	mediaPlayer.setOnBufferingUpdateListener(this);
	mediaPlayer.setOnCompletionListener(this);
      myHandler.postDelayed(UpdateSongTime,100);
      pauseButton.setEnabled(true);
      playButton.setEnabled(false);
   }

   private Runnable UpdateSongTime = new Runnable() {
      public void run() {
         startTime = mediaPlayer.getCurrentPosition();
         startTimeField.setText(String.format("%d min, %d sec", 
            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
            TimeUnit.MILLISECONDS.toSeconds((long) startTime) - 
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
            toMinutes((long) startTime)))
         );
         seekbar.setProgress((int)startTime);
         myHandler.postDelayed(this, 100);
      }
   };
   public void pause(View view){
      Toast.makeText(getApplicationContext(), "Pausing sound", 
      Toast.LENGTH_SHORT).show();

      mediaPlayer.pause();
      pauseButton.setEnabled(false);
      playButton.setEnabled(true);
   }	
   public void forward(View view){
      int temp = (int)startTime;
      if((temp+forwardTime)<=finalTime){
         startTime = startTime + forwardTime;
         mediaPlayer.seekTo((int) startTime);
      }
      else{
         Toast.makeText(getApplicationContext(), 
         "Cannot jump forward 5 seconds", 
         Toast.LENGTH_SHORT).show();
      }

   }
   public void rewind(View view){
      int temp = (int)startTime;
      if((temp-backwardTime)>0){
         startTime = startTime - backwardTime;
         mediaPlayer.seekTo((int) startTime);
      }
      else{
         Toast.makeText(getApplicationContext(), 
         "Cannot jump backward 5 seconds",
         Toast.LENGTH_SHORT).show();
      }

   }
   @Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId() == R.id.seekBar1){
			
			if(mediaPlayer.isPlaying()){
		    	SeekBar sb = (SeekBar)v;
				int playPositionInMillisecconds = (mediaPlayer.getDuration() / 10) * sb.getProgress();
				mediaPlayer.seekTo(playPositionInMillisecconds);
			}
		}
		return false;
	}
  
   @Override
	public void onCompletion(MediaPlayer mp) {
	
		playButton.setImageResource(R.drawable.button_play);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
	
		seekbar.setSecondaryProgress(percent);
	}

	class getaudiourl extends AsyncTask<String, String, String> {
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CourseAudio.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

		@Override
		protected String doInBackground(String... params) {
			
			 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			 params1.add(new BasicNameValuePair("course_id",CourseDetails.course_id));
             params1.add(new BasicNameValuePair("lecture_id",lect_id ));
             params1.add(new BasicNameValuePair("section_id", sect_id));

             JsonParser jLogin = new JsonParser();
            
             JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl+Config.coursecontentaudio,"POST", params1);
           System.out.println("value for json::"+json);
             if(json!=null)
             {
                 try
                 {
                	 if(json != null)
                	 {
                	 System.out.println("json value::"+json);
                	
                	 JSONObject jUser = json.getJSONObject("serviceresponse");
                	 successL = jUser.getString(TAG_SUCCESS);
                	 course_contents = jUser.getString(TAG_CONTENT);
                
                	
                	 }
                	
                }
                 
                 catch(JSONException e)
                 {
                	 e.printStackTrace();
                	 
                 }
              }
             else{
                	 
            	 successL ="No"; 
	    			  } 
                	
                 
    			
    			return null;
    		}
		@SuppressWarnings("deprecation")
		@Override
		 protected void onPostExecute(String file_url) {
	    	   super.onPostExecute(file_url);
	        System.out.println("in post execute");
	    	   pDialog.dismiss();
	          if(JsonParser.jss.equals("empty"))
	          {
	       	   System.out.println("json null value");
	       	AlertDialog alertDialog = new AlertDialog.Builder(
					getApplicationContext()).create();

			// Setting Dialog Title
			alertDialog.setTitle("INFO!");

			// Setting Dialog Message
			alertDialog.setMessage("Server not connected.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.delete);
			

			// Setting OK Button
			alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

						public void onClick(final DialogInterface dialog,
								final int which) {
							// Write your code here to execute after dialog
							// closed
							
						}
					});

			// Showing Alert Message
			alertDialog.show();
	    
					 // pDialog.dismiss();
	          }
	          else if(successL.equalsIgnoreCase("No")){
	        	  AlertDialog alertDialog = new AlertDialog.Builder(
	        			  getApplicationContext()).create();

    				// Setting Dialog Title
    				alertDialog.setTitle("INFO!");

    				// Setting Dialog Message
    				alertDialog.setMessage("Invalid username or password.");

    				// Setting Icon to Dialog
    				alertDialog.setIcon(R.drawable.delete);
    				

    				// Setting OK Button
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								// Write your code here to execute after dialog
    								// closed
    							
    	                                dialog.dismiss();
    							}
    						});

    				// Showing Alert Message
    				alertDialog.show();
	      
	  			//  pDialog.dismiss();
	           }
	          else
	          {
	        	  videouri="http://users.skynet.be/fa046054/home/P22/track06.mp3";
	        	  System.out.println("video uri in course details"+videouri);
	        	  playButton.setEnabled(true);
	        	  
	        	 
	          }
	     

		
		 }

 }
	@Override
	 public void onBackPressed() 
{

}
 @Override
   public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
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