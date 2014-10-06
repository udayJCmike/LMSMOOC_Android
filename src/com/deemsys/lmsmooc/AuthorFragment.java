package com.deemsys.lmsmooc;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AuthorFragment  extends Fragment
{
	Bitmap bitmap;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	 JSONArray user = null;
	 private static final String TAG_SRESL= "serviceresponse";
	    private static final String TAG_author_list = "Author List";
	    private static final String TAG_SUCCESS = "success";
		private static final String TAG_Author_FirstNAME= "auth_firstname";
		private static final String TAG_Author_LastNAME= "auth_lastname";
		private static final String TAG_Author_Education= "auth_education";
		private static final String TAG_Author_avatar= "avatar";
		private static final String TAG_Author_aboutme= "aboutme";
		private static final String TAG_follow_count= "followcheck";
	TextView authorname,education,aboutauthor;
	ImageView avatarimg;
	Button followunfollow;
	
	String author_firstname,author_lastname,author_education,author_avatar_url,about_auhtor,followcheck,numofrows;
	 @SuppressWarnings("deprecation")
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.authorfragment, container, false);
	        setHasOptionsMenu(true);
	        cd = new ConnectionDetector(getActivity());
	        authorname=(TextView)v.findViewById(R.id.textView2);
	        education=(TextView)v.findViewById(R.id.authoredu);
	        aboutauthor=(TextView)v.findViewById(R.id.aboutauthor);
	        followunfollow=(Button)v.findViewById(R.id.unfolowfolow);
	        aboutauthor.setMovementMethod(new ScrollingMovementMethod());
	        avatarimg=(ImageView)v.findViewById(R.id.avatarimg);
	        isInternetPresent = cd.isConnectingToInternet();
	        if(isInternetPresent)
			{
	        new FetchAuthor().execute();
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
	        followunfollow.setOnClickListener(new OnClickListener() {
		     	   
			
				@Override
				public void onClick(View arg0) {
					isInternetPresent = cd.isConnectingToInternet();
					 if(isInternetPresent&&followcheck.equalsIgnoreCase("0"))
						{
				        new InsertFavorite().execute();
				        followcheck="1";
				        followunfollow.setText("Unfollow Author"); 
						}
					 else if (isInternetPresent&&followcheck.equalsIgnoreCase("1"))
						{
					        new UpdateFavorite().execute();
					        followcheck="0";
					        followunfollow.setText("Follow Author"); 
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
	        }
	        );
			return v;
	        
	 }
	 
	 
	 
	 class FetchAuthor extends AsyncTask<String,String,String>{
			@Override
		    protected void onPreExecute() {
				 
			}
				
				@Override
				protected void onPostExecute(String file_url) {
			   
					 super.onPostExecute(file_url);
					 authorname.setText(author_firstname+" "+author_lastname);
					 education.setText(author_education);
					 aboutauthor.setText(about_auhtor);
					 if(followcheck.equalsIgnoreCase("0"))
					 {
						 followunfollow.setText("Follow Author"); 
					 }
					 else if(followcheck.equalsIgnoreCase("1"))
					 {
						 followunfollow.setText("Unfollow Author"); 
					 }
					 new LoadImage().execute(LoginActivity.avatar_url+"UserImages/"+author_avatar_url);
				//System.out.println("avatar url::"+LoginActivity.avatar_url+author_avatar_url);
			} 

				@Override
				protected String doInBackground(String... args) {
					// TODO Auto-generated method stub
					
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		             
		             params1.add(new BasicNameValuePair("instructor_id", CourseDetails.instructorid));
		             params1.add(new BasicNameValuePair("student_id", Config.student_id));
		           
		         jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.coursedetailsurl, "POST", params1);
				
				    Log.i("tagconvertstr", "["+jArray+"]");
				    
				    try
				    {
				    	if(jArray != null){
				    	
				    	JSONObject c = jArray.getJSONObject(TAG_SRESL);
				    	Log.i("tagconvertstr", "["+c+"]");
				    	user = c.getJSONArray(TAG_author_list);
				    	Log.i("tagconvertstr1", "["+user+"]");
				    	
				    	for(int i=0;i<user.length();i++)
				    	{
				    		
				    		JSONObject c1 = user.getJSONObject(i);
				    		JSONObject c2 = c1.getJSONObject(TAG_SRESL);
				    	    author_firstname = c2.getString(TAG_Author_FirstNAME);
				    	    author_lastname = c2.getString(TAG_Author_LastNAME);
				    	    author_education = c2.getString(TAG_Author_Education);
				    	    about_auhtor = c2.getString(TAG_Author_aboutme);
				    	    author_avatar_url = c2.getString(TAG_Author_avatar);
				    		followcheck= c2.getString(TAG_follow_count);
				           
				    		
				    		
				    		
				    	}
				    	
				    	}
				    	
				    	}catch (JSONException e) {
				        e.printStackTrace();
				    }
				   
					return null;
				}
				
				
				
			}	 
	 class UpdateFavorite extends AsyncTask<String, String, String> {
		  	@Override
		      protected void onPreExecute() {
		          super.onPreExecute();


		      }

				@Override
				protected String doInBackground(String... params) {
					
					 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		           
		           params1.add(new BasicNameValuePair("instructor_id", CourseDetails.instructorid));
		           params1.add(new BasicNameValuePair("student_id", Config.student_id));

		           JsonParser jLogin = new JsonParser();
		           
		           JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl+Config.unfollowauthorurl,"POST", params1);
		          
		           if(json!=null)
		           {
		               try
		               {
		              	 if(json != null)
		              	 {
		              	
		              	
		              	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
		              	
		              	 numofrows=  jUser.getString(TAG_SUCCESS);
		              	
		              	
		              	 }
		              	
		              }
		               
		               catch(JSONException e)
		               {
		              	 e.printStackTrace();
		              	 
		               }
		            }
		           else{
		              	 
		           }
		              	
		               
		  			
		  			return null;
		  		}
				
				@Override
				 protected void onPostExecute(String file_url) {
			    	   super.onPostExecute(file_url);
			        System.out.println("in post execute");
			    	//   pDialog.dismiss();
			    	   
			    	

				
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
		           
		           params1.add(new BasicNameValuePair("instructor_id", CourseDetails.instructorid));
		           params1.add(new BasicNameValuePair("student_id", Config.student_id));
		           params1.add(new BasicNameValuePair("course_author", author_firstname+" "+author_lastname));

		           JsonParser jLogin = new JsonParser();
		           
		           JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl+Config.followauthorurl,"POST", params1);
		           System.out.println("value for json::"+json);
		           if(json!=null)
		           {
		               try
		               {
		              	 if(json != null)
		              	 {
		              	 System.out.println("json value::"+json);
		              	
		              	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
		              	
		              	 numofrows=  jUser.getString(TAG_SUCCESS);
		              	 System.out.println("number of rows value:::"+numofrows);
		              	 
		              	
		              	 }
		              	
		              }
		               
		               catch(JSONException e)
		               {
		              	 e.printStackTrace();
		              	 
		               }
		            }
		           else{
		              	 
		           }
		              	
		               
		  			
		  			return null;
		  		}
				
				@Override
				 protected void onPostExecute(String file_url) {
			    	   super.onPostExecute(file_url);
			        System.out.println("in post execute");
			    	//   pDialog.dismiss();
			    	   
			    	

				
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
		        	 avatarimg.setImageBitmap(image);
		          
		         }else{
		          
		        
		         }
		       }
		   }
}
