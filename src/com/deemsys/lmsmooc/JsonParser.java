package com.deemsys.lmsmooc;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 

import android.content.Context;





import android.app.ProgressDialog;


import android.util.Log;

import android.widget.TextView;
 
public class JsonParser {
 
    InputStream is = null;
    JSONObject jsonObject = null;
    String json = "";
    TextView nameView;
     TextView ageView;
     TextView jobView;
     JSONArray jArray=null;
     public ProgressDialog pDialog;
     final Context context=null;
     public static String jss;
     public static String jss1;
     //RegisterActivity alert12;
    
    public JsonParser() {
 
    }
   
   
    public JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params) {
 
       
        try {
 
        	json=null;
        	jsonObject= null;
            if(method.equalsIgnoreCase("POST")){
               
            	
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                
                httpPost.setEntity(new UrlEncodedFormEntity(params));
 
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                
               // System.out.println(is);
                
              //  System.out.println("getting the content");
 
            }else if(method.equalsIgnoreCase("GET")){
            
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                 
            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
      
        System.out.println("getting  all the values ");
        
        try {	 
        	json = null;
        	jsonObject =null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			
			
	       StringBuilder sb = new StringBuilder();
			String line = null;
		
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		
			is.close();
		
            json = sb.toString();	 
        	
         
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
      
        try {
        	
        	Log.i("tagconvertstr1", "["+json+"]");
        	
        	if((json!=null)&&(json!=""))
        	{
        		jss= "not empty";
        		System.out.println("json is not equal to null");
        	jsonObject = new JSONObject(json);
        	  System.out.println("json object parse finished");
        	
        	Log.i("tagconvertstr2","["+json+"]");
        	    	
        	}
        	
        	else
        	{
        		System.out.println(" it is null value ");
        		jss= "empty";
        		System.out.println("jss value is" +jss);
        		
        	}
            
            
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        
        
        System.out.println("jsson object is" + json);
       
        return jsonObject;
 
    }
    
public JSONObject getJSONFromUrl(String url) {
		
		        try {
		        	json = null;
		        	jsonObject =null;
		            DefaultHttpClient httpClient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(url);
		 
		            HttpResponse httpResponse = httpClient.execute(httpPost);
		            HttpEntity httpEntity = httpResponse.getEntity();
		            is = httpEntity.getContent();           
		            //json = EntityUtils.toString(httpEntity);
		            //System.out.println("json  url is" +json);
		        } catch (UnsupportedEncodingException e) {
		            e.printStackTrace();
		        } catch (ClientProtocolException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        try {
		        	
		            BufferedReader reader = new BufferedReader(new InputStreamReader(
		                    is, "iso-8859-1"), 8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
		            }
		            is.close();
		            json = sb.toString();
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }
		    
		        try {
		        	Log.i("tagconvertstr", "["+json+"]");
		        	
		        	
		        	if((json!=null)&&(json!=""))
		        	{
		        		jss1="not empty";
		        	jsonObject = new JSONObject(json);
		        	Log.i("tagconvertstr", "["+json+"]");
		        	
		        	}
		        	else
		        	{
		        		System.out.println(" it is null value ");
		        		jss1= "empty";
		        		//System.out.println("jss1 value is" +jss1);
		        		
		        	}
		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }
		 
		     System.out.println("json object is");
		        return jsonObject;  
		
	}
}

