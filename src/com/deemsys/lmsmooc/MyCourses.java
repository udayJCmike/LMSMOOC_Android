package com.deemsys.lmsmooc;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
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

import com.squareup.picasso.Picasso;

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MyCourses  extends Fragment {
	Bitmap bitmap;

	public ProgressDialog cDialog,pDialog;
	public static ArrayList<String> coursetotallist= new ArrayList<String>();
	public static ArrayList<String> imagelist= new ArrayList<String>();
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
	 String course_id_topass,course_name_to_pass,course_descript_to_pass,course_enrolled;
	 String course_name,authorname,student_enrolled,ratingcouont,cost,course_id,instructorid,numofrows,course_cover_image,course_description,audiourl,audiourlpassing;
	 private static final String TAG_SRESL= "serviceresponse";
	    private static final String TAG_Course_ARRAY = "CourseList";
		private static final String TAG_SRES= "serviceresponse";
		private static final String TAG_COURSE_NAME= "course_name";
		private static final String TAG_COURSE_AUTHOR= "course_author";
		private static final String TAG_COURSE_PROMO_VIDEO= "course_promo_video";
		private static final String TAG_COURSE_COST= "course_price";
		private static final String TAG_COURSE_RATINGS= "user_ratings";
		private static final String TAG_course_cover_image= "course_cover_image";
		private static final String TAG_COURSE_DESCRIPTION= "course_description";
		private static final String TAG_INSTRUCTOR_ID= "instructor_id";
		private static final String TAG_COURSE_ID= "course_id";
		private static final String TAG_ENROLLED_STUDENT= "course_enrolled";
		
		private static final String TAG_NUMBER_OF_ROWS = "number_of_rows";
	 String courseidurl,instructoridurl,pur_url,course_enrolled_passing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.allcourses, container, false);
        setHasOptionsMenu(true);
        
         listView = (ListView)v.findViewById(R.id.listView1);
         cd = new ConnectionDetector(getActivity());
		 isInternetPresent = cd.isConnectingToInternet();
		 if (isInternetPresent) {
			 grabURL(Config.ServerUrl+Config.mycourseurl); 
				 }
		 courselist = new ArrayList<Course>();
       dataAdapter = new MyCustomAdapter(getActivity(),
        R.layout.course_overview, courselist);
       listView.setAdapter(dataAdapter);
       listView.setOnItemClickListener(new OnItemClickListener() {
    	   public void onItemClick(AdapterView<?> parent, View view,
    	     int position, long id) {
    	   
    	    Course country = (Course) parent.getItemAtPosition(position);
    	    if(position<courselist.size())
    	    {
    	    	audiourlpassing=country.getaudiourl();
    	    	course_descript_to_pass=country.getdescription();
    	    	  course_id_topass=country.getcourseid();
    	    courseidurl=country.getcourseid();
    	    instructoridurl=country.getinsid();
    	    course_name_to_pass=country.getCode();
    	    course_enrolled_passing=country.getstudentsenrolled();
    	    new fetchpurnumber().execute();
    	   
    	    }
    	   }
    	  });
       

        return v;
    }
    
    public void grabURL(String url) {
    	  Log.v("Android Spinner JSON Data Activity", url);
    	  new GrabURL().execute(url);
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
	          cDialog.setCancelable(true);
	          cDialog.show();
		}
			
			@Override
			protected void onPostExecute(String file_url) {
		   
				 super.onPostExecute(file_url);
				
				
				
				    displayCourseList(content);
				 
				    cDialog.dismiss();
				  
				  }

			private void displayCourseList(String response){
				 
				
				 
				  try {
					  
				
				   JSONObject c = jArray.getJSONObject(TAG_SRES);
			    	Log.i("tagconvertstr", "["+c+"]");
			 
			    	Log.i("tagconvertstr1", "["+user+"]");
				 
				
				   JSONArray countryListObj = c.getJSONArray(TAG_Course_ARRAY);
				 
				   
				   if(countryListObj.length() == 0)
				   
				   {
					   if(courselist.size() == 0&&start==0)
						 {
							 	AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								// Setting Dialog Title
								alertDialog.setTitle("INFO!");

								// Setting Dialog Message
								alertDialog.setMessage("No courses enrolled");

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
			
				    listView.removeFooterView(loadMoreView);
				   }
				   else 
				   
				   {
				   
					   for (int i=0; i<countryListObj.length(); i++)
					   
					   {
				    start++;
				   
		    		JSONObject c1 = user.getJSONObject(i);
		    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
		    	    authorname = c2.getString(TAG_COURSE_AUTHOR);
		    		instructorid=c2.getString(TAG_INSTRUCTOR_ID);
		    		course_id=c2.getString(TAG_COURSE_ID);
		            course_name = c2.getString(TAG_COURSE_NAME);
		         course_description=c2.getString(TAG_COURSE_DESCRIPTION);
		            course_cover_image=c2.getString(TAG_course_cover_image);
		        	cost= c2.getString(TAG_COURSE_COST);
		        	ratingcouont=c2.getString(TAG_COURSE_RATINGS);
		        	course_enrolled=c2.getString(TAG_ENROLLED_STUDENT);
		        	audiourl=c2.getString(TAG_COURSE_PROMO_VIDEO);
		        	coursetotallist.add(course_description);
		        	coursetotallist.add(authorname);
		        	coursetotallist.add(course_name);
		        	coursetotallist.add(ratingcouont);
		        	coursetotallist.add(course_enrolled);
		        	coursetotallist.add(audiourl);
		        	imagelist.add(course_cover_image);


		        	 Course cnt = new Course(authorname,course_name,cost,course_id,instructorid,course_cover_image,ratingcouont,course_description,audiourl);
		        	 cnt.setName(authorname);
		        	 cnt.setCode(course_name);
					  cnt.setins_id(instructorid);
					  cnt.setcourseid(course_id);
					  cnt.setrating(ratingcouont);
					  cnt.setdescription(course_description);
					  cnt.setstudentsenrolled(course_enrolled);
		              cnt.setstringurl(course_cover_image);
		             cnt.setaudiourl(audiourl);
				    courselist.add(cnt);
				 
				 
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
			   // System.out.println("start while posting"+start);
			    nameValuePairs.add(new BasicNameValuePair("start",String.valueOf(start)));
			    nameValuePairs.add(new BasicNameValuePair("limit",String.valueOf(limit)));
			    nameValuePairs.add(new BasicNameValuePair("student_id",Config.student_id));
			    jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.mycourseurl, "POST", nameValuePairs);
			    JSONObject c = jArray.getJSONObject(TAG_SRES);
		    	//Log.i("tagconvertstr", "["+c+"]");
		    	user = c.getJSONArray(TAG_Course_ARRAY);
		    	//Log.i("tagconvertstr1", "["+user+"]");

			    
			    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
			    response = httpclient.execute(httpPost);
			  
			     StatusLine statusLine = response.getStatusLine();
			     if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			      ByteArrayOutputStream out = new ByteArrayOutputStream();
		      response.getEntity().writeTo(out);
			      out.close();
			      content = out.toString();
			     } else{
			      
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
    	   ImageView cover;
    	   TextView cost;
    	   ImageView ratingshow;
    	  }
    	 
    	  public void add(Course country){
    	
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
       holder.cover = (ImageView) convertView.findViewById(R.id.cover);
      holder.ratingshow= (ImageView) convertView.findViewById(R.id.ratingimage);
    	   convertView.setTag(holder);
    	 
    	   } else {
    	    holder = (ViewHolder) convertView.getTag();
    	   }
    	 
    	   Course country = this.countryList.get(position);
    	   holder.code.setText(country.getCode());
    	   holder.name.setText(country.getName());
    	   holder.cost.setText("$ "+country.getRegion());
    	   holder.cover.setImageBitmap(country.getBitmap());
//    	   aQuery = new AQuery(getActivity());
//    	    aQuery.id(R.id.cover).image(country.getstringurl(),true,true);
    	    Picasso.with(getActivity()).load(country.getstringurl()).into(holder.cover);
    	  
//    	   new DownloadTask((ImageView) convertView.findViewById(R.id.cover))
//           .execute((String) country.getstringurl());
    	   if(country.getrating().equalsIgnoreCase("0"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.zero);  
    	   }
    	   else  if(country.getrating().equalsIgnoreCase("1"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.one);  
    	   }
    	   else  if(country.getrating().equalsIgnoreCase("2"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.two);  
    	   }
    	   else  if(country.getrating().equalsIgnoreCase("3"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.three);  
    	   }
    	   else  if(country.getrating().equalsIgnoreCase("4"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.four);  
    	   }
    	   else  if(country.getrating().equalsIgnoreCase("5"))
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.five);  
    	   }
    	   else 
    	   {
    		   holder.ratingshow.setImageResource(R.drawable.zero);  
    	   }
    	   return convertView;
    	 
    	  }
    	 
    }
    public class DownloadTask extends AsyncTask<String, Void, Boolean> {
        ImageView v;
        String url;
        Bitmap bm;

        public DownloadTask(ImageView v) {
            this.v = v;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            url = params[0];
            bm = loadBitmap(url);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            v.setImageBitmap(bm);
            cDialog.dismiss();
        }

        public Bitmap loadBitmap(String url) {
            try {
                URL newurl = new URL(url);
                Bitmap b = BitmapFactory.decodeStream(newurl.openConnection()
                        .getInputStream());
                return b;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
    class fetchpurnumber extends AsyncTask<String, String, String> {
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
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
                	
                	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
                	
                	 numofrows=  jUser.getString(TAG_NUMBER_OF_ROWS);
                	
                	 
                	
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
	    	   pDialog.dismiss();
	    	 
	    	   Intent i=new Intent(getActivity(),CourseDetails.class);
	    	   i.putExtra("courseid", course_id_topass);
	    	   i.putExtra("course_name",   course_name_to_pass);
	    	   i.putExtra("course_description",   course_descript_to_pass);
	    	   i.putExtra("instructor_id",   instructoridurl);
	    	   i.putExtra("enroll_students",   course_enrolled_passing);
	    	 i.putExtra("audio_url", audiourlpassing);
				startActivity(i);
	            
	    	   
//	    	//  String url="http://208.109.248.89:8085/OnlineCourse/Student/student_view_Course?course_id=50&authorid=1&pur=0&catcourse=&coursetype=";
//	    	   String url = Config.common_url+"/student_view_Course?course_id="+courseidurl+"&authorid="+instructoridurl+"&pur="+numofrows+"&catcourse=&coursetype=";
//				System.out.println("url value"+url);
//	    	   Intent i = new Intent(Intent.ACTION_VIEW);
//				i.setData(Uri.parse(url));
//				getActivity().startActivity(i);
//	         
	     

		
		 }

 }
    
    
}