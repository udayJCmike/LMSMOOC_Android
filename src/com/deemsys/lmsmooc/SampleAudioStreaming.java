package com.deemsys.lmsmooc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SampleAudioStreaming extends Activity implements OnTouchListener, OnCompletionListener, OnBufferingUpdateListener{

   public TextView startTimeField,endTimeField;
   private MediaPlayer mediaPlayer;
   private double startTime = 0;
   private double finalTime = 0;
   private Handler myHandler = new Handler();;
   private int forwardTime = 5000; 
   private int backwardTime = 5000;
   private SeekBar seekbar;
   private ImageButton playButton,pauseButton;
   public static int oneTimeOnly = 0;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.course_audio);
     
      startTimeField =(TextView)findViewById(R.id.textView1);
      endTimeField =(TextView)findViewById(R.id.textView2);
      seekbar = (SeekBar)findViewById(R.id.seekBar1);
      playButton = (ImageButton)findViewById(R.id.imageButton1);
      pauseButton = (ImageButton)findViewById(R.id.imageButton2);
      mediaPlayer = new MediaPlayer();
      seekbar.setOnTouchListener(this);
   
      pauseButton.setEnabled(false);
    

   }

   public void play(View view){
   Toast.makeText(getApplicationContext(), "Playing sound", 
   Toast.LENGTH_SHORT).show();
   try {
	mediaPlayer.setDataSource("http://users.skynet.be/fa046054/home/P22/track06.mp3");
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
   public boolean onCreateOptionsMenu(Menu menu) {
  
   getMenuInflater().inflate(R.menu.main, menu);
   return true;
   }
   @Override
	public void onCompletion(MediaPlayer mp) {
	
		playButton.setImageResource(R.drawable.button_play);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
	
		seekbar.setSecondaryProgress(percent);
	}

	
 }