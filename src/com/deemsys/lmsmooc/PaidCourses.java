package com.deemsys.lmsmooc;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.AllCourses.fetchpurnumber;
import com.google.gson.Gson;




import android.support.v4.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView.OnScrollListener;

public class PaidCourses  extends Fragment {
	public ProgressDialog cDialog,pDialog;
	public static ArrayList<String> coursetotallist= new ArrayList<String>();
	ArrayList<Course> courselist;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	 MyCustomAdapter dataAdapter = null;
	 int start = 0;
	 int limit = 10;
	 boolean loadingMore = false;
	 View loadMoreView;
	 JSONArray user = null;
	 static ListView listView ;
	 String course_name,authorname,student_enrolled,ratingcouont,cost,course_id,instructorid,numofrows;
	    private static final String TAG_Course_ARRAY = "CourseList";
	    private static final String TAG_NUMBER_OF_ROWS = "number_of_rows";
		private static final String TAG_SRES= "serviceresponse";
		private static final String TAG_COURSE_NAME= "course_name";
		private static final String TAG_COURSE_AUTHOR= "course_author";
		private static final String TAG_COURSE_SUBTITLE= "course_sub_title";
		private static final String TAG_COURSE_COST= "course_price";
		private static final String TAG_COURSE_RATINGS= "user_ratings";
		private static final String TAG_course_cover_image= "course_cover_image";
		private static final String TAG_route_no= "route_no";
		private static final String TAG_driver_status= "device_status";
		private static final String TAG_status_date= "status_date";
		private static final String TAG_ADDRS= "address";
		private static final String TAG_SPEED= "speed";
		private static final String TAG_INSTRUCTOR_ID= "instructor_id";
		private static final String TAG_COURSE_ID= "course_id";
		String courseidurl,instructoridurl,pur_url;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View v = inflater.inflate(R.layout.allcourses, container, false);
        setHasOptionsMenu(true);
       
        listView = (ListView)v.findViewById(R.id.listView1);
        
        getActivity();
		loadMoreView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
          .inflate(R.layout.loadmore, null, false);
        listView.addFooterView(loadMoreView);

        return v;
    }
    public void grabURL(String url) {
    	  Log.v("Android Spinner JSON Data Activity", url);
    	  new GrabURL().execute(url);
    	 }
    public static PaidCourses newInstance(String text) {

    	PaidCourses f = new PaidCourses();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    
    }
    class GrabURL extends AsyncTask<String,Void,String>{
    	private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		  private static final int WAIT_TIMEOUT = 30 * 1000;
		  private final HttpClient httpclient = new DefaultHttpClient();
		  final HttpParams params = httpclient.getParams();
		  HttpResponse response;
		  private String content =  null;
		  private boolean error = false;
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
				
				 cDialog.dismiss();
				   Toast toast;
				   if (error) {
				    toast = Toast.makeText(getActivity(), 
				      content, Toast.LENGTH_LONG);
				    toast.setGravity(Gravity.TOP, 25, 400);
				    toast.show();
				   } else {
				    displayCountryList(content);
				   }
				  }

			private void displayCountryList(String response){
				 
				  JSONObject responseObj = null; 
				 
				  try {
					  
				   Gson gson = new Gson();
				   JSONObject c = jArray.getJSONObject(TAG_SRES);
			    	Log.i("tagconvertstr", "["+c+"]");
			 
			    	Log.i("tagconvertstr1", "["+user+"]");
				   responseObj = new JSONObject(response); 
				
				   JSONArray countryListObj = c.getJSONArray(TAG_Course_ARRAY);
				 
				   if(countryListObj.length() == 0){
			
				    AllCourses.listView.removeFooterView(loadMoreView);
				   }
				   else {
				    for (int i=0; i<countryListObj.length(); i++){
				    start++;
				    System.out.println("forloop1");
		    		JSONObject c1 = user.getJSONObject(i);
		    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
		    		 authorname = c2.getString(TAG_COURSE_AUTHOR);
			    		instructorid=c2.getString(TAG_INSTRUCTOR_ID);
			    		course_id=c2.getString(TAG_COURSE_ID);
			            course_name = c2.getString(TAG_COURSE_NAME);
			          
			        	cost= c2.getString(TAG_COURSE_COST);
			        	coursetotallist.add(authorname);
			        	coursetotallist.add(course_name);
			        	 Course cnt = new Course(authorname,course_name,cost,course_id,instructorid);
			        	 cnt.setName(authorname);
			        	 cnt.setCode(course_name);
						  cnt.setins_id(instructorid);
						  cnt.setcourseid(course_id);
		       String countryInfo = countryListObj.getJSONObject(i).toString();
				  
				    System.out.println("country in fo"+countryInfo);
				  	// Course country = gson.fromJson(countryInfo,Course.class);
				   
				    courselist.add(cnt);
				    System.out.println("size fo country list"+courselist.size());
				    System.out.println("value fo country list"+courselist);
				    dataAdapter.add(cnt);
				    }
				 
				    dataAdapter.notifyDataSetChanged();
				    loadingMore = false;
				   }
				 
				  } catch (JSONException e) {
				   e.printStackTrace();
				  }
				 
				 }	
			
		

			@Override
			protected String doInBackground(String... urls) {
				// TODO Auto-generated method stub
					    String URL = null;
			    loadingMore = true;
			  
			    try
			    {
			    	URL = urls[0];
			    HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
			    HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
			    ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
			  
			    HttpPost httpPost = new HttpPost(URL);
			  
			    //add name value pair for the country code
			    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			    nameValuePairs.add(new BasicNameValuePair("start",String.valueOf(start)));
			    nameValuePairs.add(new BasicNameValuePair("limit",String.valueOf(limit)));
			    jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.paidcourseurl, "POST", nameValuePairs);
			    JSONObject c = jArray.getJSONObject(TAG_SRES);
		    	Log.i("tagconvertstr", "["+c+"]");
		    	user = c.getJSONArray(TAG_Course_ARRAY);
		    	Log.i("tagconvertstr1", "["+user+"]");
//			    System.out.println("jarray"+jArray);
//			    try
//			    {
//			    	if(jArray != null)
//			    	{
//			    	
//			    	JSONObject c = jArray.getJSONObject(TAG_SRES);
//			    	Log.i("tagconvertstr", "["+c+"]");
//			    JSONArray user = c.getJSONArray(TAG_Course_ARRAY);
//			    	Log.i("tagconvertstr1", "["+user+"]");
//			    	
//			    	for(int i=0;i<user.length();i++)
//			    	{
//			    		System.out.println("forloop1");
//			    		JSONObject c1 = user.getJSONObject(i);
//			    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
//			    	String ins     = c2.getString("instructor_id");
//			    	System.out.println("instructor"+ins);
//			    	}
//			    	}
			    
			    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
			    response = httpclient.execute(httpPost);
			  
			     StatusLine statusLine = response.getStatusLine();
			     if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			      ByteArrayOutputStream out = new ByteArrayOutputStream();
		      response.getEntity().writeTo(out);
			      out.close();
			      content = out.toString();
			     } else{
			      //Closes the connection.
			      Log.w("HTTP1:",statusLine.getReasonPhrase());
			      response.getEntity().getContent().close();
			      throw new IOException(statusLine.getReasonPhrase());
			     }
			    } catch (ClientProtocolException e) {
			     Log.w("HTTP2:",e );
			     content = e.getMessage();
			     error = true;
			     cancel(true);
			    } catch (IOException e) {
			     Log.w("HTTP3:",e );
			     content = e.getMessage();
			     error = true;
			     cancel(true);
			    }catch (Exception e) {
			     Log.w("HTTP4:",e );
			     content = e.getMessage();
			     error = true;
			     cancel(true);
			    }
//			    }catch(JSONException e)
//			    {
//			    	  e.printStackTrace();
//			    }
//			   
//			        
			    		
//			    }catch(Exception e)
//			    {
//			    	  e.printStackTrace();
//			    }
			    return content;
			}
			
    }
    private class MyCustomAdapter extends ArrayAdapter<Course> {
    	 
    	  private ArrayList<Course> countryList;
    	 
    	  public MyCustomAdapter(Context context, int textViewResourceId, 
    	    ArrayList<Course> countryList) {
    	   super(context, textViewResourceId, countryList);
    	   this.countryList = new ArrayList<Course>();
    	   this.countryList.addAll(countryList);
    	  }
    	 
    	  private class ViewHolder {
    	   TextView code;
    	   TextView name;
    	   TextView continent;
    	   TextView cost;
    	  }
    	 
    	  public void add(Course country){
    	  // Log.v("AddView", country.getCode());
    	   this.countryList.add(country);
    	  }
    	 
    	  @Override
    	  public View getView(int position, View convertView, ViewGroup parent) {
    	 
    	   ViewHolder holder = null;
    	   Log.v("ConvertView", String.valueOf(position));
    	   if (convertView == null) {
    	 
    	   LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	   convertView = vi.inflate(R.layout.course_overview, null);
    	 
    	   holder = new ViewHolder();
    	   holder.code = (TextView) convertView.findViewById(R.id.coursename);
    	   holder.name = (TextView) convertView.findViewById(R.id.author);
    	   holder.cost = (TextView) convertView.findViewById(R.id.cost);
    	//   holder.cost = (TextView) convertView.findViewById(R.id.enroll);
    	 
    	   convertView.setTag(holder);
    	 
    	   } else {
    	    holder = (ViewHolder) convertView.getTag();
    	   }
    	 
    	   Course country = this.countryList.get(position);
    	   holder.code.setText(country.getCode());
    	   holder.name.setText(country.getName());
    	   holder.cost.setText("$ "+country.getRegion());
    	//   holder.region.setText(country.getRegion());
    	 
    	   return convertView;
    	 
    	  }
    	 
    }
    boolean _areLecturesLoaded = false;

    @Override
     public void setUserVisibleHint(boolean isVisibleToUser) {
         super.setUserVisibleHint(isVisibleToUser);
         if (isVisibleToUser && !_areLecturesLoaded ) {
          loaddatas(); 
          _areLecturesLoaded = true;
         }
     }
    public void loaddatas()
    {
    	
         courselist = new ArrayList<Course>();
         dataAdapter = new MyCustomAdapter(getActivity(),
           R.layout.course_overview, courselist);
         listView.setAdapter(dataAdapter);
         listView.setTextFilterEnabled(true);
         listView.setOnItemClickListener(new OnItemClickListener() {
         	   public void onItemClick(AdapterView<?> parent, View view,
         	     int position, long id) {
         		  if(position<courselist.size()){
         		  Course country = (Course) parent.getItemAtPosition(position);
          	    courseidurl=country.getcourseid();
          	    instructoridurl=country.getinsid();
          	    new fetchpurnumber().execute();
          	    Toast.makeText(getActivity(),
          	      country.getcourseid(), Toast.LENGTH_SHORT).show();
         	   }
         	   }
         	  });
         	 
         	  listView.setOnScrollListener(new OnScrollListener(){
         	 
         	   @Override
         	   public void onScrollStateChanged(AbsListView view, int scrollState) {}
         	 
         	   @Override
         	   public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
         	 
         	   int lastInScreen = firstVisibleItem + visibleItemCount;    
         	   if((lastInScreen == totalItemCount) && !(loadingMore)){     
         	  
         		   System.out.println(Config.ServerUrl+Config.allcourseurl);
         	    grabURL(Config.ServerUrl+Config.allcourseurl); 
         	   }
         	   }
         	  });
    }
    class fetchpurnumber extends AsyncTask<String, String, String> {
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
		protected String doInBackground(String... params) {
			
			 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
             
             params1.add(new BasicNameValuePair("course_id", courseidurl));
            

             JsonParser jLogin = new JsonParser();
             
             JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl+Config.purchasenumberselection,"POST", params1);
             System.out.println("value for json::"+json);
             if(json!=null)
             {
                 try
                 {
                	 if(json != null)
                	 {
                	 System.out.println("json value::"+json);
                	
                	 JSONObject jUser = json.getJSONObject(TAG_SRES);
                	
                	 numofrows=  jUser.getString(TAG_NUMBER_OF_ROWS);
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
		@SuppressWarnings("deprecation")
		@Override
		 protected void onPostExecute(String file_url) {
	    	   super.onPostExecute(file_url);
	        System.out.println("in post execute");
	    	   pDialog.dismiss();
	    	   
	    	//  String url="http://208.109.248.89:8085/OnlineCourse/Student/student_view_Course?course_id=50&authorid=1&pur=0&catcourse=&coursetype=";
	    	   String url = Config.common_url+"/student_view_Course?course_id="+courseidurl+"&authorid="+instructoridurl+"&pur="+numofrows+"&catcourse=&coursetype=";
				System.out.println("url value"+url);
	    	   Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				getActivity().startActivity(i);
	         
	     

		
		 }

 }
}