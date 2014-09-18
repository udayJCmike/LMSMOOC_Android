package com.deemsys.lmsmooc;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;






import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

public class MyFavoriteCategory extends Fragment {
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
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.categories, container, false);
	        setHasOptionsMenu(true);
//	        cd = new ConnectionDetector(getActivity());
//			 isInternetPresent = cd.isConnectingToInternet();
//			 if (isInternetPresent) {
//			        new CategoryDetails().execute(); 
//					 }

			 list2= (ListView)v.findViewById(R.id.listView);
			 return  v;
}
		
		class CategoryDetails extends AsyncTask<String,String,String>{
			@Override
		    protected void onPreExecute() {
				  cDialog = new ProgressDialog(getActivity());
		          cDialog.setMessage("Please wait...");
		          cDialog.setIndeterminate(false);
		          cDialog.setCancelable(false);
		          cDialog.show();
			}
				
				@Override
				protected void onPostExecute(String file_url) {
			   
					 super.onPostExecute(file_url);
					 list2.setAdapter(new CategoryArrayAdapter(getActivity(), category,R.layout.category_each_item));
					 list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
						 

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								System.out.println("in item click"+arg2);
								String item = category.get(arg2).getcategory(); 
								
								 Intent i= new Intent(getActivity(),MyFavoriteCategoryCourses.class);
								 
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
		             
		             params1.add(new BasicNameValuePair("student_id", Config.student_id));
		           
		         jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.myfavoritecategoryurl, "POST", params1);
				
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
		  public void onResume() {
		      super.onResume();
		      
		      cd = new ConnectionDetector(getActivity());
				 isInternetPresent = cd.isConnectingToInternet();
				 if (isInternetPresent) {
				        new CategoryDetails().execute(); 
						 }
		}
}