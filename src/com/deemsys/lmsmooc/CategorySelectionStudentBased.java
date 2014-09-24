package com.deemsys.lmsmooc;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class CategorySelectionStudentBased extends SherlockActivity {
	String category_name;
	public ProgressDialog cDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	ListView list2;
	public static ArrayList<String> vehiclelist= new ArrayList<String>();
	public static List<Category> category= new ArrayList<Category>();
    JSONArray user = null;

	    private static final String TAG_SRESL= "serviceresponse";
	    private static final String TAG_Category_ARRAY = "Category List";
		
		private static final String TAG_Category_NAME= "category_name";
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
			  
			setContentView(R.layout.categories);
			ActionBar ab = getSupportActionBar();
		      ab.setDisplayHomeAsUpEnabled(true);
		      getSupportActionBar().setHomeButtonEnabled(true);
		      ab.setTitle(Html.fromHtml("<font color='#000000'>Categories</font>"));
			    
		        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
			 cd = new ConnectionDetector(getApplicationContext());
			 isInternetPresent = cd.isConnectingToInternet();
			 if (isInternetPresent) {
				 runOnUiThread(new Runnable() {
					 public void run() {
			        new CategoryDetails().execute(); 
					 }
				 });
					 }
			 else
     		{
     			AlertDialog alertDialog = new AlertDialog.Builder(
     					CategorySelectionStudentBased.this).create();

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

			 list2= (ListView) findViewById(R.id.listView);
}
		
		class CategoryDetails extends AsyncTask<String,String,String>{
			@Override
		    protected void onPreExecute() {
				  cDialog = new ProgressDialog(CategorySelectionStudentBased.this);
		          cDialog.setMessage("Please wait...");
		          cDialog.setIndeterminate(false);
		          cDialog.setCancelable(false);
		          cDialog.show();
			}
				
				@Override
				protected void onPostExecute(String file_url) {
			   
					 super.onPostExecute(file_url);
					
							 list2.setAdapter(new CategoryArrayAdapter(CategorySelectionStudentBased.this, category,R.layout.category_each_item));
								
						
					 list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
						 

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								System.out.println("in item click"+arg2);
								String item = category.get(arg2).getcategory(); 
								
								 Intent i= new Intent(CategorySelectionStudentBased.this,CategoryCoursesStudentBased.class);
								 
								  i.putExtra("category_name", item);
								 
								 startActivity(i);
							}
					        });

				
			} 

				@Override
				protected String doInBackground(String... args) {
					// TODO Auto-generated method stub
					 CategoryArrayAdapter.data.clear();
				      category.clear();
					
					vehiclelist.clear();
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		             
		             params1.add(new BasicNameValuePair("org_id", Config.username));
		           
		         jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.categoryurl, "POST", params1);
				
				    Log.i("tagconvertstr", "["+jArray+"]");
				    
				    try
				    {
				    	if(jArray != null){
				    	
				    	JSONObject c = jArray.getJSONObject(TAG_SRESL);
				    	Log.i("tagconvertstr", "["+c+"]");
				    	user = c.getJSONArray(TAG_Category_ARRAY);
				    	Log.i("tagconvertstr1", "["+user+"]");
				    	
				    	for(int i=0;i<user.length();i++)
				    	{
				    		System.out.println("forloop1");
				    		JSONObject c1 = user.getJSONObject(i);
				    		JSONObject c2 = c1.getJSONObject(TAG_SRESL);
				    	    category_name = c2.getString(TAG_Category_NAME);
				    		
				           
				        	
				        	 Category cnt = new Category(category_name);
							    cnt.setcategory(category_name);
							  
				               category.add(cnt);
				    		
				    		System.out.println("size of aray list"+category);
				    		
				    	}
				    	
				    	}
				    	
				    	}catch (JSONException e) {
				        e.printStackTrace();
				    }
				    cDialog.dismiss();
					return null;
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