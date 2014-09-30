package com.deemsys.lmsmooc;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



public class LoginActivity extends SherlockActivity  {
	 EditText usname,paswd;
	 String successL;
	 TextView forgetpass;
	 Button signin,back;
	 public static String loginurl,avatarurl;
	 final Context context=this;
	 Boolean isInternetPresent = false;
	 ConnectionDetector cd;
	 public ProgressDialog pDialog;
	 private static final String TAG_SUCCESS = "success";
	 private static final String TAG_USERNAME = "username";
	 private static final String TAG_PASSWORD = "password";
	 private static final String TAG_ROLE = "role";
	 private static final String TAG_ENABLED= "enabled";
	 private static final String TAG_SRESL= "serviceresponse";
	 private static final String TAG_STUDENT_ID= "student_id";
	 private static final String TAG_FIRSTNAME= "firstname";
	 private static final String TAG_LASTNAME= "lastname";
	 private static final String TAG_EMAIL= "email";
	 private static final String TAG_INTERESTED_IN= "interested_in";
	 private static final String TAG_GENDER= "gender";
	 private static final String TAG_AVATAR= "avatar";
	 private static final String TAG_LOGINS= "logins";
	 private static final String TAG_GENCODE= "gencode";
	 private static final String TAG_AVATAR_URL= "avatar_url";
	 public static final String MyPREFERENCES = "MyPrefs0" ;
	   public static final String UserName = "unnameKey"; 
	    public static final String Password = "passKey"; 
	 static String username;
	static String password;
	String role;
	String enabled;
	String serviceresponse;
	static String student_id;
	static String avatar_url;
	static String firstname;
	static String lastname;
	static String email;
	static String interested_in;
	static  String gender;
	static String avatar;
	static  String logins;
	String gencode;	
	CheckBox check1;
    SharedPreferences sharedpreferences;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login); 
	        LinearLayout layout = (LinearLayout) findViewById(R.id.loginlayout);
	        loginurl=Config.ServerUrl+"Login.php?service=login";
	        avatarurl=Config.ServerUrl+"Login.php?service=avatar";
	        check1=(CheckBox)findViewById(R.id.checkBox1);
	        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	        cd = new ConnectionDetector(getApplicationContext());
	        usname= (EditText) findViewById(R.id.uname);
	      
	        paswd = (EditText) findViewById(R.id.pswd);
	        signin = (Button) findViewById(R.id.signin);
	        back = (Button) findViewById(R.id.back);
	        forgetpass = (TextView) findViewById(R.id.forgetpasword);
	        ActionBar actions = getActionBar();
	        actions.setTitle(Html.fromHtml("<font color='#ffffff'>Login</font>"));
	    
	        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));
	        layout.setOnTouchListener(new OnTouchListener()
 	        {
 	            @Override
 	            public boolean onTouch(View view, MotionEvent ev)
 	            {
 	                hideKeyboard(view);
 	                return false;
 	            }

 				
 	        });
 back.setOnClickListener(new OnClickListener() {
	     	   
				
				
				@Override
				public void onClick(View arg0) {
					 Intent intentSignUP=new Intent(getApplicationContext(),MainActivity.class);
						startActivity(intentSignUP);
				}
				});
	        signin.setOnClickListener(new OnClickListener() {
	     	   
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View arg0) {
					isInternetPresent = cd.isConnectingToInternet();
				System.out.println("signin clciked");
				 String username=usname.getText().toString();
        		 String password=paswd.getText().toString();
        		
        		if(!username.equals("")&&!password.equals(""))
        		{
        			
        	     
        			if(isInternetPresent)
        			{
        				System.out.println(username);
        				System.out.println(password);
        				
        				System.out.println("inside attempt login");
        				new AttemptLogin().execute();

        			
        			}
        		else
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this,R.style.MyFragment).create();

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
        		else if(!password.equalsIgnoreCase(""))
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this,R.style.MyFragment).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Please enter username.");

    				
    				alertDialog.setIcon(R.drawable.delete);
    				

    				
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    							}
    						});

    				// Showing Alert Message
    				alertDialog.show();
        			
        			
        		}
        		else if(!username.equalsIgnoreCase(""))
        		{
        			AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this,R.style.MyFragment).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Please enter password.");

    				
    				alertDialog.setIcon(R.drawable.delete);
    				

    				
    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    							public void onClick(final DialogInterface dialog,
    									final int which) {
    								
    							
    							}
    						});

    				
    				alertDialog.show();

              
        		}
				
    			else
    			{
    				AlertDialog alertDialog = new AlertDialog.Builder(
    						LoginActivity.this).create();

    				
    				alertDialog.setTitle("INFO!");

    				
    				alertDialog.setMessage("Enter login credentials.");

    				
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
	       forgetpass.setOnClickListener(new OnClickListener() {
	    	   
				@Override
				public void onClick(View arg0) {

			
				String url = "http://208.109.248.89:8087/OnlineCourse/Student/signup";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				}

			});
	 }
	
	 class Avatarfetch extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	          
	        }
	    	@Override
			protected String doInBackground(String... params)
	    	{
	    		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	            
	             params1.add(new BasicNameValuePair("id", "1"));
	             params1.add(new BasicNameValuePair("student_id", Config.student_id));
	    		JsonParser jLogin = new JsonParser();
	             System.out.println(usname.getText().toString());
	             System.out.println( paswd.getText().toString());
	             JSONObject json = jLogin.makeHttpRequest(avatarurl,"POST", params1);
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
	                	 avatar_url = jUser.getString(TAG_AVATAR_URL );
	                	 Config.common_url=jUser.getString(TAG_AVATAR_URL);;
	                	 System.out.println("avatar url in second async"+avatar_url);
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
	    	@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		    pDialog.dismiss();	
		   calld();
	    	}
	 }

	 class AttemptLogin extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(LoginActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

			@Override
			protected String doInBackground(String... params) {
				
				 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	             params1.add(new BasicNameValuePair("username", usname.getText().toString()));
	             params1.add(new BasicNameValuePair("password", paswd.getText().toString()));

	             JsonParser jLogin = new JsonParser();
	             System.out.println(usname.getText().toString());
	             System.out.println( paswd.getText().toString());
	             JSONObject json = jLogin.makeHttpRequest(loginurl,"POST", params1);
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
	                	 Config.username = jUser.getString(TAG_USERNAME );
	                	 Config.password = jUser.getString(TAG_PASSWORD);
	                	 Config.firstname= jUser.getString(TAG_FIRSTNAME);
	                	 Config.lastname= jUser.getString(TAG_LASTNAME);
	                	 Config.email=jUser.getString(TAG_EMAIL);
	                	 Config.interested_in= jUser.getString(TAG_INTERESTED_IN);
	                	 Config. gender = jUser.getString(TAG_GENDER );
	                	 Config.avatar = jUser.getString(TAG_AVATAR);
	                	 Config.logins = jUser.getString(TAG_LOGINS );
	                	 Config.gencode = jUser.getString(TAG_GENCODE);
	                	 Config.role=jUser.getString(TAG_ROLE);
	                	 Config.enabled=jUser.getString(TAG_ENABLED);
	                	 Config.student_id=jUser.getString(TAG_STUDENT_ID);
	                	 System.out.println("username value:::"+username);
	                	 System.out.println("password value::"+password);
	                	 System.out.println("student id value"+Config.student_id);
	                	
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
						LoginActivity.this).create();

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
		    
						  pDialog.dismiss();
		          }
		          else if(successL.equalsIgnoreCase("No")){
		        	  AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

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
	    								usname.setText("");
	    	                    		paswd.setText("");
	    	                                dialog.dismiss();
	    							}
	    						});

	    				// Showing Alert Message
	    				alertDialog.show();
		      
		  			  pDialog.dismiss();
		           }
		          else
		          {
		        	  new Avatarfetch().execute();
		        	 
		          }
		     

			
			 }

	 }
	 @Override
	    protected void onResume() {
	    	super.onResume();
	    	  System.out.println("Outsside shared pref");
	        if (sharedpreferences.contains(UserName))
	        {
	        	  System.out.println("Inside shared pref");
	      usname.setText(sharedpreferences.getString(UserName, ""));
	      check1.setChecked(true);
	        }
	        if (sharedpreferences.contains(Password))
	        {
	        paswd.setText(sharedpreferences.getString(Password, ""));
          check1.setChecked(true);
	        }
	      
	    }
	 protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 }
//	  @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        switch (item.getItemId()) {
//	        case android.R.id.home:
//	        	System.out.println("on back pressed");
//	   		finish();
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	        }
//	    }
	 @Override
	 public void onBackPressed() {

		 
		
//	     int count = getFragmentManager().getBackStackEntryCount();
//System.out.println("count value::::"+count);
//	     if (count == 0) {
//	         super.onBackPressed();
//	         //additional code
//	     } else {
//	         getFragmentManager().popBackStack();
//	     }

	 }
	 private void calld(){
	    	if(check1.isChecked()==true){
	    	 String n  = usname.getText().toString();
	         System.out.println("In run method::"+n);
	         String u = paswd.getText().toString();
	         System.out.println("In run method::"+u);
	         Editor editor = sharedpreferences.edit();
	         editor.putString(UserName, n);
	         editor.putString(Password, u);
	         editor.commit();
	    	}
	    	else{
	    		 SharedPreferences settings = context.getSharedPreferences("MyPrefs0", Context.MODE_PRIVATE);
	        		settings.edit().clear().commit();
	    	}
	    	 Intent intentSignUP=new Intent(getApplicationContext(),NewMainActivity.class);
				startActivity(intentSignUP);
	             finish();
	     }
	    
}
