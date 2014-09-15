package com.deemsys.lmsmooc;





import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class ProfileFragment extends Fragment {
	 public ProgressDialog pDialog;
	 private static final String TAG_SUCCESS = "success";
//	 private static final String TAG_USERNAME = "username";
//	 private static final String TAG_PASSWORD = "password";
//	 private static final String TAG_ROLE = "role";
//	 private static final String TAG_ENABLED= "enabled";
	 private static final String TAG_SRESL= "serviceresponse";
//	 private static final String TAG_STUDENT_ID= "student_id";
//	 private static final String TAG_FIRSTNAME= "firstname";
//	 private static final String TAG_LASTNAME= "lastname";
//	 private static final String TAG_EMAIL= "email";
//	 private static final String TAG_INTERESTED_IN= "interested_in";
//	 private static final String TAG_GENDER= "gender";
//	 private static final String TAG_AVATAR= "avatar";
//	 private static final String TAG_LOGINS= "logins";
//	 private static final String TAG_GENCODE= "gencode";
//	 private static final String TAG_AVATAR_URL= "avatar_url";
	 String selectedimage=null;;
	
	EditText firstnameedit,lastnameedit,usernameedit,emailedit,avataredit,passwordedit;
	Boolean isInternetPresent = false;
	 ConnectionDetector cd;
	 int genderspinpos,interestedinspin;
	String firstname,lastname,username,email,interestedin,avatar,logins,password,genderstring;
	public static String updateurl,avatarurl;
	int i,count;
	String selectiongender,successL;
	ImageButton buttt;
	Button savechanges;
	TextView genderselecthid;
	ImageButton but1,but2,but3,but4,but5,but6,but7,but8,but9,but10,but11,but12,but13,but14;
	ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	 Bitmap bitmap;
	 ImageView image;
	 Spinner gender,interstedinspin;
	 String[] interested = {
		      
		      "-Select your interested topics-",
		      "Subject",
		      "Courses"
		  };
	 String[] gendersel = {
		   
		      "-Select Gender-",
		      "Male",
		      "Female"
		  };
	public ProfileFragment()
	{
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.profilefrag, container, false);
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.proffrag);
        pDialog = new ProgressDialog(getActivity());
        updateurl=Config.ServerUrl+"UpdateProfile.php?service=updateprof";
        avatarurl=Config.AvatarUrl+LoginActivity.avatar;
        firstnameedit=(EditText)rootView.findViewById(R.id.firstname);
        lastnameedit=(EditText)rootView.findViewById(R.id.lastname);
        emailedit=(EditText)rootView.findViewById(R.id.email);
        usernameedit=(EditText)rootView.findViewById(R.id.username);
        passwordedit=(EditText)rootView.findViewById(R.id.password);
        genderselecthid=(TextView)rootView.findViewById(R.id.proftextgender);
        genderselecthid.setVisibility(View.INVISIBLE);
       firstnameedit.setText(Config.firstname);
       lastnameedit.setText(Config.lastname);
       usernameedit.setText(Config.username);
       emailedit.setText(Config.email);
       passwordedit.setText(Config.password);
       interstedinspin = (Spinner)rootView.findViewById(R.id.spinner1);
       gender = (Spinner)rootView.findViewById(R.id.spinner2);
     
       genderstring=Config.gender;
       System.out.println("gender strinfg value::"+genderstring);
       interestedin=Config.interested_in;
       System.out.println("intert strinfg value::"+interestedin);
       savechanges = (Button)rootView.findViewById(R.id.savechagnes);
//       image = (ImageView)rootView.findViewById(R.id.imageView1);
//      
//      image.setVisibility(View.INVISIBLE);
//      System.out.println("login avatar url"+Config.avatar);
//       if(!Config.avatar.equalsIgnoreCase(""))
//       {
//    	   image.setVisibility(View.VISIBLE);
//    	 //  new LoadImage().execute(ProfileActivity.avatar_whole_url);
//    	  
//    	
//    	   
//       }
       if(genderstring.equalsIgnoreCase("Male"))
       {
    	 //  gender.setVisibility(View.INVISIBLE);
    	 //  genderselecthid.setVisibility(View.VISIBLE);
    	   genderselecthid.setText("Male");
    	   genderspinpos=1;
       }
       else if(genderstring.equalsIgnoreCase("Female"))
       {
    	   genderspinpos=2;
    	  // gender.setVisibility(View.INVISIBLE);
    	 //  genderselecthid.setVisibility(View.VISIBLE);
    	   genderselecthid.setText("Female");
       }
       else if(genderstring.equalsIgnoreCase("null"))
       {
    	 //  genderselecthid.setVisibility(View.INVISIBLE);
    	   genderspinpos=0;
    	   
       }
       if(interestedin.equalsIgnoreCase("Male"))
       {
    	   interestedinspin=1;
       }
       else if(interestedin.equalsIgnoreCase("Female"))
       {
    	   interestedinspin=2;
       }
       else
       {
    	   interestedinspin=0;
    	   
       }
        cd = new ConnectionDetector(getActivity());
        layout.setOnTouchListener(new OnTouchListener()
	        {
	            @Override
	            public boolean onTouch(View view, MotionEvent ev)
	            {
	                hideKeyboard(view);
	                return false;
	            }

				
	        });
       
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, interested);
        interstedinspin.setAdapter(adapter);
        interstedinspin.setSelection(genderspinpos);
        interstedinspin.setOnItemSelectedListener(
                      new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                  int arg2, long arg3) {
                            int position = interstedinspin.getSelectedItemPosition();
                            if(position==0)
                            {
                            	interestedin="null";
                            }
                            else if(position==1)
                            {
                            	interestedin="subject";
                            }
                            else if(position==2)
                            {
                            	interestedin="course";
                            }
                    }
                          @Override
                          public void onNothingSelected(AdapterView<?> arg0) {
                              // TODO Auto-generated method stub
                          }
                      }
                  );
        savechanges.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
			@Override
            public void onClick(View v) {
            	isInternetPresent = cd.isConnectingToInternet();
            	firstname=firstnameedit.getText().toString();
            	lastname=lastnameedit.getText().toString();
            	username=usernameedit.getText().toString();
            	password=passwordedit.getText().toString();
            	email=emailedit.getText().toString();
            	
            	
            	if(isInternetPresent)
    			{
    				
    				
    				System.out.println("inside attempt login");
    				new UpdateProf().execute();

    			
    			}
    		else
    		{
    			AlertDialog alertDialog = new AlertDialog.Builder(
						getActivity()).create();

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
            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gendersel);
        gender.setAdapter(adapter1);
        gender.setSelection(genderspinpos);
        gender.setOnItemSelectedListener(
                      new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                  int arg2, long arg3) {
                        	 
                            int position = arg2;
                            System.out.println("positin value"+position);
                            imagelist.clear();
                            if(position==1)
                            {
                            	genderstring="male";
                            	selectedimage="/resources/images/users/1.png";
                              count=13;
                            for(i=1;i<=13;i++)
                            {
                          //  new DownloadImageTask().execute(Config.AvatarUrl+i+".png");
                            }
                            }
                            else if(position==2)
                            {
                              count=14;
                              genderstring="female";
                              selectedimage="/resources/images/users/g1.png";
                            for(i=1;i<=14;i++)
                            {
                         //   new DownloadImageTask().execute(Config.AvatarUrl+"g"+i+".png");
                            }
                            }
                            else if(position==0)
                            {
                            	genderstring="null";
                            	
                            }
                            
                            
                          }
                          @Override
                          public void onNothingSelected(AdapterView<?> arg0) {
                              
                          }
                      }
                  );
        return rootView;
    }
	protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 }
	private class DownloadImageTask extends AsyncTask<String, Void, String> {
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
          
//            if(pDialog.isShowing())
//            {
//            	System.out.println("dialog presences is true");
//            	pDialog.dismiss();
//            }
            
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
	  
@Override
	    protected String doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	            imagelist.add(mIcon11);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return null;
	    }
@Override
	    protected void onPostExecute(String file_url) {
	
	    	//imagelist.add(result);
	    	System.out.println("number of images::"+imagelist.size());
	    	System.out.println(imagelist);
	     //   image.setImageBitmap(result);
	    	if(count==14&&imagelist.size()==14)
	        {
	        	pDialog.dismiss();
	        LayoutInflater li = LayoutInflater.from(getActivity());
			final View promptsView = li.inflate(R.layout.avatar_selection, null);
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

		
			alertDialogBuilder.setView(promptsView);
			buttt= (ImageButton) promptsView
					.findViewById(R.id.ImageButton1);
			buttt.setImageBitmap(imagelist.get(0));
			but2= (ImageButton) promptsView
					.findViewById(R.id.ImageButton2);
			but2.setImageBitmap(imagelist.get(1));
			but3= (ImageButton) promptsView
					.findViewById(R.id.ImageButton3);
			but3.setImageBitmap(imagelist.get(2));
			but4= (ImageButton) promptsView
					.findViewById(R.id.ImageButton4);
			but4.setImageBitmap(imagelist.get(3));
			but5= (ImageButton) promptsView
					.findViewById(R.id.ImageButton5);
			but5.setImageBitmap(imagelist.get(4));
			but6= (ImageButton) promptsView
					.findViewById(R.id.ImageButton6);
			but6.setImageBitmap(imagelist.get(5));
			but7= (ImageButton) promptsView
					.findViewById(R.id.ImageButton7);
			but7.setImageBitmap(imagelist.get(6));
			but8= (ImageButton) promptsView
					.findViewById(R.id.ImageButton8);
			but8.setImageBitmap(imagelist.get(7));
			but9= (ImageButton) promptsView
					.findViewById(R.id.ImageButton9);
			but9.setImageBitmap(imagelist.get(8));
			but10= (ImageButton) promptsView
					.findViewById(R.id.ImageButton10);
			but10.setImageBitmap(imagelist.get(9));
			but11= (ImageButton) promptsView
					.findViewById(R.id.ImageButton11);
			but11.setImageBitmap(imagelist.get(10));
			but12= (ImageButton) promptsView
					.findViewById(R.id.ImageButton12);
			but12.setImageBitmap(imagelist.get(11));
			but13= (ImageButton) promptsView
					.findViewById(R.id.ImageButton13);
			but13.setImageBitmap(imagelist.get(12));
			but14= (ImageButton) promptsView
					.findViewById(R.id.ImageButton14);
			but14.setImageBitmap(imagelist.get(13));
			
			
			buttt.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(0));
	            	 
	            	  selectedimage="/resources/images/users/g1.png";
	            }
	        });
			but2.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(1));
	            	  selectedimage="/resources/images/users/g2.png";
	            }
	        });
			but3.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(2));
	            	  selectedimage="/resources/images/users/g3.png";
	            }
	        });
			but4.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(3));
	            	  selectedimage="/resources/images/users/g4.png";
	            }
	        });
			but5.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(4));
	            	  selectedimage="/resources/images/users/g5.png";
	            }
	        });
			but6.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(5));
	            	  selectedimage="/resources/images/users/g6.png";
	            }
	        });
			but7.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(6));
	            	  selectedimage="/resources/images/users/g7.png";
	            }
	        });
			but8.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(7));
	            	  selectedimage="/resources/images/users/g8.png";
	            }
	        });
			but9.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(8));
	            	  selectedimage="/resources/images/users/g9.png";
	            }
	        });
			but10.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(9));
	            	  selectedimage="/resources/images/users/g10.png";
	            }
	        });
			but11.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(10));
	            	  selectedimage="/resources/images/users/g11.png";
	            }
	        });
			but12.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(11));
	            	  selectedimage="/resources/images/users/g12.png";
	            }
	        });
			but13.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(12));
	            	  selectedimage="/resources/images/users/g13.png";
	            }
	        });
			but14.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(13));
	            	  selectedimage="/resources/images/users/g14.png";
	            }
	        });
			alertDialogBuilder
			.setCancelable(false).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	    }
	    
	    else if (count==13 &&imagelist.size()==13)
	    {
	    	pDialog.dismiss();
	    	LayoutInflater li = LayoutInflater.from(getActivity());
			final View promptsView = li.inflate(R.layout.avatar_selection, null);
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

		
			alertDialogBuilder.setView(promptsView);
			buttt= (ImageButton) promptsView
					.findViewById(R.id.ImageButton1);
			buttt.setImageBitmap(imagelist.get(0));
			but2= (ImageButton) promptsView
					.findViewById(R.id.ImageButton2);
			but2.setImageBitmap(imagelist.get(1));
			but3= (ImageButton) promptsView
					.findViewById(R.id.ImageButton3);
			but3.setImageBitmap(imagelist.get(2));
			but4= (ImageButton) promptsView
					.findViewById(R.id.ImageButton4);
			but4.setImageBitmap(imagelist.get(3));
			but5= (ImageButton) promptsView
					.findViewById(R.id.ImageButton5);
			but5.setImageBitmap(imagelist.get(4));
			but6= (ImageButton) promptsView
					.findViewById(R.id.ImageButton6);
			but6.setImageBitmap(imagelist.get(5));
			but7= (ImageButton) promptsView
					.findViewById(R.id.ImageButton7);
			but7.setImageBitmap(imagelist.get(6));
			but8= (ImageButton) promptsView
					.findViewById(R.id.ImageButton8);
			but8.setImageBitmap(imagelist.get(7));
			but9= (ImageButton) promptsView
					.findViewById(R.id.ImageButton9);
			but9.setImageBitmap(imagelist.get(8));
			but10= (ImageButton) promptsView
					.findViewById(R.id.ImageButton10);
			but10.setImageBitmap(imagelist.get(9));
			but11= (ImageButton) promptsView
					.findViewById(R.id.ImageButton11);
			but11.setImageBitmap(imagelist.get(10));
			but12= (ImageButton) promptsView
					.findViewById(R.id.ImageButton12);
			but12.setImageBitmap(imagelist.get(11));
			but13= (ImageButton) promptsView
					.findViewById(R.id.ImageButton13);
			but13.setImageBitmap(imagelist.get(12));
			but14= (ImageButton) promptsView
					.findViewById(R.id.ImageButton14);
		//	but14.setImageBitmap(imagelist.get(13));
			but14.setVisibility(View.INVISIBLE);
		
			buttt.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(0));
	            	  selectedimage="/resources/images/users/1.png";
	            }
	        });
			but2.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(1));
	            	  selectedimage="/resources/images/users/2.png";
	            }
	        });
			but3.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(2));
	            	  selectedimage="/resources/images/users/3.png";
	            }
	        });
			but4.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(3));
	            	  selectedimage="/resources/images/users/4.png";
	            }
	        });
			but5.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(4));
	            	  selectedimage="/resources/images/users/5.png";
	            }
	        });
			but6.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(5));
	            	  selectedimage="/resources/images/users/6.png";
	            }
	        });
			but7.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(6));
	            	  selectedimage="/resources/images/users/7.png";
	            }
	        });
			but8.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(7));
	            	  selectedimage="/resources/images/users/8.png";
	            }
	        });
			but9.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(8));
	            	  selectedimage="/resources/images/users/9.png";
	            }
	        });
			but10.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(9));
	            	  selectedimage="/resources/images/users/10.png";
	            }
	        });
			but11.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(10));
	            	  selectedimage="/resources/images/users/11.png";
	            }
	        });
			but12.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(11));
	            	  selectedimage="/resources/images/users/12.png";
	            }
	        });
			but13.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	 image.setVisibility(View.VISIBLE);
	            	  image.setImageBitmap(imagelist.get(12));
	            	  selectedimage="/resources/images/users/13.png";
	            }
	        });
			
			alertDialogBuilder
			.setCancelable(false).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    
			    }
			  })
			  
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	    }
	    }
	 
}
	 class UpdateProf extends AsyncTask<String, String, String> {
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
			protected String doInBackground(String... params)
	    	{
	    		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	            
	             //params1.add(new BasicNameValuePair("id", "1"));
	             System.out.println("");
	             params1.add(new BasicNameValuePair("oldusername", Config.username));
	             params1.add(new BasicNameValuePair("firstname", firstnameedit.getText().toString()));
	             params1.add(new BasicNameValuePair("lastname", lastnameedit.getText().toString()));
	             params1.add(new BasicNameValuePair("email", emailedit.getText().toString()));
	    		
	             params1.add(new BasicNameValuePair("username", usernameedit.getText().toString()));
	             params1.add(new BasicNameValuePair("password", passwordedit.getText().toString()));
	             params1.add(new BasicNameValuePair("interested_in",interestedin));
	             params1.add(new BasicNameValuePair("gender", genderstring));
	             params1.add(new BasicNameValuePair("avatar", selectedimage));
	             Config.firstname=firstnameedit.getText().toString();
	             Config.lastname=lastnameedit.getText().toString();
	             Config.username=usernameedit.getText().toString();
	             Config.email=emailedit.getText().toString();
	             Config.password=passwordedit.getText().toString();
		         Config.interested_in=interestedin;
		         Config.gender=genderstring;
		         Config.avatar=selectedimage;
		            	
	            System.out.println("selected image value"+selectedimage);
	    		JsonParser jLogin = new JsonParser();
	           
	             JSONObject json = jLogin.makeHttpRequest(updateurl,"POST", params1);
             System.out.println("value for json::"+json);
	             if(json!=null)
	             {
	                 try
	                 {
	                	 if(json != null)
	                	 {
	                	 System.out.println("json value::"+json);
	                	
	                	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
	                	 successL = jUser.getString(TAG_SUCCESS);
	                	 System.out.println("successL value"+successL);
	                	// avatar = jUser.getString(TAG_AVATAR_URL );
	                	 System.out.println("avatar url in second async"+avatar);
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
		    	   if(successL.equalsIgnoreCase("Yes"))
		    	   {
		    		   AlertDialog alertDialog = new AlertDialog.Builder(
	    						getActivity()).create();

	    				alertDialog.setTitle("INFO!");

	    				alertDialog.setMessage("Profile updated successfully");

	    				alertDialog.setIcon(R.drawable.delete);
	    				
	    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

	    							public void onClick(final DialogInterface dialog,
	    									final int which) {
	    								 Intent intentSignUP=new Intent(getActivity(),NewMainActivity.class);
	    									startActivity(intentSignUP);
	    							}
	    						});

	    				
	    				alertDialog.show();
	        			
		    	   }
		    	   else
		    	   {
		    		   AlertDialog alertDialog = new AlertDialog.Builder(
	    						getActivity()).create();

	    				alertDialog.setTitle("INFO!");

	    				alertDialog.setMessage("Profile not updated successfully");

	    				alertDialog.setIcon(R.drawable.delete);
	    				
	    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

	    							public void onClick(final DialogInterface dialog,
	    									final int which) {
	    								
	    							}
	    						});

	    				
	    				alertDialog.show();
		    	   }
		    pDialog.dismiss();	
//		    Intent intentSignUP=new Intent(getActivity(),ProfileActivity.class);
//			startActivity(intentSignUP);
	    	}
	 }
	 private class LoadImage extends AsyncTask<String, String, Bitmap> {
		    @Override
		        protected void onPreExecute() {
		            super.onPreExecute();

		    }
		       protected Bitmap doInBackground(String... args) {
		         try {
		               bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
		        } catch (Exception e) {
		              e.printStackTrace();
		        }
		      return bitmap;
		       }
		       protected void onPostExecute(Bitmap image) {
		         if(image != null){
		        	 System.out.println("bitmap"+bitmap);
		           ProfileFragment.this.image.setImageBitmap(image);
		          
		         }else{
		          
		         //  Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
		         }
		       }
		   }
	
}