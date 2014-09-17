package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.InboxFragment.Details;
import com.deemsys.lmsmooc.InboxFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.DialogInterface;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class InboxDetails extends SherlockFragmentActivity   {
	public ProgressDialog cDialog;
	FragmentManager context=this.getFragmentManager();

	String senderstr;
	String receiverstr;
	String datestr;

	String messagestr;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	Inbox in;
	String id,receiver,roles;

	
	
	 TextView from,date,to,message;
	 Button importantstatus,unimportantstatus,back;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      ActionBar ab = getSupportActionBar();
	      ab.setDisplayHomeAsUpEnabled(true);
	      getSupportActionBar().setHomeButtonEnabled(true);
	      ab.setTitle(Html.fromHtml("<font color='#000000'>Inbox</font>"));
		    
	        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
	      setContentView(R.layout.inbox_details);
	     

			id=InboxFragment.inbid;
			receiver=InboxFragment.receiverid;
			roles=InboxFragment.role;

				  from = (TextView)findViewById(R.id.fromans);
			       to = (TextView)findViewById(R.id.toans);
					message = (TextView)findViewById(R.id.msgans);
					date = (TextView)findViewById(R.id.dateans);
				unimportantstatus=(Button)findViewById(R.id.redbtn);
					
				importantstatus=(Button)findViewById(R.id.greenbtn);
				//back=(Button)findViewById(R.id.back);
					
				from.setText(InboxFragment.sender);
				to.setText(InboxFragment.receiver);
				message.setText(InboxFragment.msg);
				date.setText(InboxFragment.date);
				

			 if(InboxFragment.important.equals("0"))
			    {
			    	
			    	unimportantstatus.setVisibility(View.VISIBLE);
		        	importantstatus.setVisibility(View.INVISIBLE);

			    	unimportantstatus.setBackgroundResource(R.drawable.unimportant);
			    	  
			    
			    	
			    }
			    
			    
			    else if(InboxFragment.important.equals("1"))
			    {
			    	
			    

			    	importantstatus.setVisibility(View.VISIBLE);
		        	unimportantstatus.setVisibility(View.INVISIBLE);
			    	importantstatus.setBackgroundResource(R.drawable.important);
			    	  
			    	
			    	

			    }
			   
				unimportantstatus.setOnClickListener(new OnClickListener() {
	    	        @Override
	    	        public void onClick(final View v) {
	    	        	importantstatus.setVisibility(View.VISIBLE);
	    	        	unimportantstatus.setVisibility(View.INVISIBLE);
	    	        	importantstatus.setBackgroundResource(R.drawable.important);
	    	        	
	    	        	System.out.println("updating value 1");
	    	        	
	    	        	

	    	        	  System.out.println("id value:"+id);
					       System.out.println("receiver value:"+receiver);
					       System.out.println("role value:"+roles);
	    	        	

	    		        new UpdateOne().execute(); 
	    		        
	    		        
	    	        	
	    	        }
	    	    });
				
				
				importantstatus.setOnClickListener(new OnClickListener() {
	    	        @Override
	    	        public void onClick(final View v) {
	    	        	unimportantstatus.setVisibility(View.VISIBLE);
	    	        	importantstatus.setVisibility(View.INVISIBLE);
	    	        	

	    	        	unimportantstatus.setBackgroundResource(R.drawable.unimportant);
	    	        	
	    	        	System.out.println("updating value 0");
	    	        	
	    	        	
	    	        	

	    	        	  System.out.println("id value:"+id);
					       System.out.println("receiver value:"+receiver);
					       System.out.println("role value:"+roles);
	    	        	

	    		        new UpdateZero().execute(); 

	    	        	
	    	        }
	    	    });
			
				
				 if(InboxFragment.read.equalsIgnoreCase("0"))
				    {
					 
		
				    	
	    	        	System.out.println("read update$%$^&^&&&**&((*&*(&*(");
	    		        new ReadUpdateOne().execute(); 
				    	
				    }
				    
				
				  
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
	
	
	
	
				class UpdateOne extends AsyncTask<String,String,String>{

					   private ProgressDialog pDialog;
					  
					
					
					    JSONObject jsonE;


					@Override
				  protected void onPreExecute() {
				     
				      
				  } 
					    
					 @Override
						protected String doInBackground(String... params) {
						 
							List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
						
							 paramsE.add(new BasicNameValuePair("inbox_id",id));

								 paramsE.add(new BasicNameValuePair("receiver_id",receiver));
								 paramsE.add(new BasicNameValuePair("role",roles));

				       

		    	        	  System.out.println("id value:"+id);
						       System.out.println("receiver value:"+receiver);
						       System.out.println("role value:"+roles);
		    	        	


							 JsonParser jLogin1 = new JsonParser();
							 
							 JSONObject json1 = jLogin1.makeHttpRequest(Config.ServerUrl+Config.importantstatusone,"POST", paramsE);
							 System.out.println("url called");
				     	 System.out.println("value for json::"+json1);
				     	 

				     

							return null;
						
				  				  }
					 
					 
					@SuppressWarnings("deprecation")
					@Override
					 protected void onPostExecute(String file_url) {
				 	   super.onPostExecute(file_url);
				     System.out.println("in post execute");
		           
				 	  // pDialog.dismiss();
				       if(JsonParser.jss.equals("empty"))
				       {
				    	   System.out.println("json null value");
				    	AlertDialog alertDialog = new AlertDialog.Builder(
								InboxDetails.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("Error connecting database.");

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
				  
				       }
				       
				       
				   
				     


				}
					}
				
				
				class UpdateZero extends AsyncTask<String,String,String>{

					   private ProgressDialog pDialog;
					
					
					  
					    JSONObject jsonE;


					@Override
				  protected void onPreExecute() {
//				      super.onPreExecute();
//				      pDialog = new ProgressDialog(InboxDetails.this);
//
//				      pDialog.setMessage("Please wait...");
//				      
//				      pDialog.setIndeterminate(false);
//				      pDialog.setCancelable(true);
//				      pDialog.show();
				      
				  } 
					    
					 @Override
						protected String doInBackground(String... params) {
						 
							List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
						
							 paramsE.add(new BasicNameValuePair("inbox_id",id));

							
							 paramsE.add(new BasicNameValuePair("receiver_id",receiver));
							 paramsE.add(new BasicNameValuePair("role",roles));

		    	        	  System.out.println("id value:"+id);
						       System.out.println("receiver value:"+receiver);
						       System.out.println("role value:"+roles);
		    	        	

							 JsonParser jLogin1 = new JsonParser();
							 
							 JSONObject json1 = jLogin1.makeHttpRequest(Config.ServerUrl+Config.importatntstatuszero,"POST", paramsE);
				     	 System.out.println("value for json::"+json1);
				     	 

							return null;
						
				  				  }
					 
					 
					@SuppressWarnings("deprecation")
					@Override
					 protected void onPostExecute(String file_url) {
				 	   super.onPostExecute(file_url);
				     System.out.println("in post execute");
				  
				       if(JsonParser.jss.equals("empty"))
				       {
				    	   System.out.println("json null value");
				    	AlertDialog alertDialog = new AlertDialog.Builder(
								InboxDetails.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("Error connecting database.");

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
				  
				       }
//				     
				     


				}
					}
	       
	        
			
				class ReadUpdateOne extends AsyncTask<String,String,String>{

					   private ProgressDialog pDialog;
					  
					
					
				 
					    JSONObject jsonE;


					@Override
				  protected void onPreExecute() {
				     
				      
				  } 
					    
					 @Override
						protected String doInBackground(String... params) {
						 
							List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
						
							 paramsE.add(new BasicNameValuePair("inbox_id",id));

								
								 paramsE.add(new BasicNameValuePair("receiver_id",receiver));
								 paramsE.add(new BasicNameValuePair("role",roles));

				       
							   System.out.println("subject:"+id);
						       System.out.println("subject:"+roles);
						       System.out.println("subject:"+receiver);

							 JsonParser jLogin1 = new JsonParser();
							 
							 JSONObject json1 = jLogin1.makeHttpRequest(Config.ServerUrl+Config.inboxreadstatus,"POST", paramsE);
							 System.out.println("url called");
				     	 System.out.println("value for json::"+json1);
				     	 

				     

							return null;
						
				  				  }
					 
					 
					@SuppressWarnings("deprecation")
					@Override
					 protected void onPostExecute(String file_url) {
				 	   super.onPostExecute(file_url);
				     System.out.println("in post execute");
		           
				     

				     
				 	  // pDialog.dismiss();
				       if(JsonParser.jss.equals("empty"))
				       {
				    	   System.out.println("json null value");
				    	AlertDialog alertDialog = new AlertDialog.Builder(
								InboxDetails.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("Error connecting database.");

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
				  
				       }
				       
				       
				      
				     


				}
					}


				 @Override
				 public void onBackPressed() 
			  {

			  }

}


