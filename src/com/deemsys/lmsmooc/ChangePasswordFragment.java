package com.deemsys.lmsmooc;





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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePasswordFragment extends Fragment {
	 private static final String TAG_SUCCESS = "success";
	
	 private static final String TAG_SRESL= "serviceresponse";
	 public ProgressDialog pDialog;
	EditText oldpasswordedit,newpasswordedt,confirmpasswordedit;
	Boolean isInternetPresent = false;
	 ConnectionDetector cd;
	String oldpass,newpass,confirmpass,successL;
	Button getpassword;
	public static String updateurl;
	public ChangePasswordFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.changepasswordfrag, container, false);
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.changepasslayout);
        pDialog = new ProgressDialog(getActivity());
        cd = new ConnectionDetector(getActivity());
        updateurl=Config.ServerUrl+"ChangePassword.php?service=changepass";
        oldpasswordedit=(EditText)rootView.findViewById(R.id.oldpassword);
        newpasswordedt=(EditText)rootView.findViewById(R.id.newpassword);
        confirmpasswordedit=(EditText)rootView.findViewById(R.id.confirmpassword);
        getpassword = (Button)rootView.findViewById(R.id.getpassword);
        layout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }

			
        });
        getpassword.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
			@Override
            public void onClick(View v) {
            	isInternetPresent = cd.isConnectingToInternet();
            	oldpass=oldpasswordedit.getText().toString();
            	newpass=newpasswordedt.getText().toString();
            	confirmpass=confirmpasswordedit.getText().toString();
            	if(!oldpass.equalsIgnoreCase("")&&!newpass.equalsIgnoreCase("")&&!confirmpass.equalsIgnoreCase(""))
            	{
            		if(oldpass.equalsIgnoreCase(Config.password))
            		{
            			if(newpass.equals(confirmpass))
            			{
            				if(isInternetPresent)
                			{
                				
            					Config.password=newpass;
                				System.out.println("inside attempt changepass");
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
            			else
        	    		{
        	    			AlertDialog alertDialog = new AlertDialog.Builder(
        							getActivity()).create();

        					alertDialog.setTitle("INFO!");

        					alertDialog.setMessage("New password mismatch with confirm password.");

        					alertDialog.setIcon(R.drawable.delete);
        					
        					alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

        								public void onClick(final DialogInterface dialog,
        										final int which) {
        									
        								}
        							});

        					
        					alertDialog.show();
        	    			
        	    		}
            		}
            		else
    	    		{
    	    			AlertDialog alertDialog = new AlertDialog.Builder(
    							getActivity()).create();

    					alertDialog.setTitle("INFO!");

    					alertDialog.setMessage("Old password is wrong.");

    					alertDialog.setIcon(R.drawable.delete);
    					
    					alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    								public void onClick(final DialogInterface dialog,
    										final int which) {
    									
    								}
    							});

    					
    					alertDialog.show();
    	    			
    	    		}
            	}
            	else
	    		{
	    			AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

					alertDialog.setTitle("INFO!");

					alertDialog.setMessage("Enter all fields.");

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
        return rootView;
    }


	protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
             params1.add(new BasicNameValuePair("username", Config.username));
            // params1.add(new BasicNameValuePair("oldpassword", oldpasswordedit.getText().toString()));
             params1.add(new BasicNameValuePair("newpassword", newpasswordedt.getText().toString()));
           //  params1.add(new BasicNameValuePair("email", confirmpasswordedit.getText().toString()));
    		Config.password=newpasswordedt.getText().toString();
            
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
    								 final Intent intentSignUP=new Intent(getActivity(),ProfileActivity.class);
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

    	}
 }
}
