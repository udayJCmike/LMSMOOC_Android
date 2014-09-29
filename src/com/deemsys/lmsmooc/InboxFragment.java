package com.deemsys.lmsmooc;





import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.ConnectionDetector;
import com.deemsys.lmsmooc.JsonParser;
import com.deemsys.lmsmooc.LoginActivity;
import com.deemsys.lmsmooc.InboxArrayAdapter;

import com.deemsys.lmsmooc.NewMainActivity;

import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;

@SuppressWarnings("unused")
public class InboxFragment extends Fragment {
	
	
		
	public InboxFragment()
	{
	
	}
	
	
	
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public  ArrayList<String> inboxlist= new ArrayList<String>();
	public  List<Inbox> allinbox= new ArrayList<Inbox>();
    JSONArray user=null;
    private static final String TAG_INBOXARRAY = "Inbox";

    private static final String TAG_SENDER_ID = "sender_id";
	private static final String TAG_SRES= "serviceresponse";
	private static final String TAG_INBOXID= "inbox_id";
	private static final String TAG_SENDER_NAME= "sender_name";
	private static final String TAG_RECEIVER_ID= "receiver_id";
	private static final String TAG_RECEIVER_NAME= "receiver_name";
	private static final String TAG_ROLE= "role";
	private static final String TAG_SUBJECT= "subject";
	private static final String TAG_MESSAGE= "message";
	private static final String TAG_READ_STATUS= "read_status";
	private static final String TAG_SENT_DATE= "sent_date";
	private static final String TAG_IMPORTANT_STATUS= "important_status";
	public ProgressDialog cDialog;
	String importantstatus;
	TextView welcome;
	static String inboxid ;
	static String sendername;
	static String receiverid ;
	static String receivername;
	static String role;
	static String subject;
	static String message;
	String readstatus;
	static String sentdate;
	static String sender;
	static String receiver;
	static String date;
	static String msg;
	static String important;
	static String read;
	static String inbid;


	int a=0;
	int b=0;
	String senderid;
	
	ListView lstvw;
	Context context=this.getActivity();

	ListView list2;
	TextView numbers;
	
	
	
	Inbox user1;
	public View mAnimatingAway;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
 
        View rootView = inflater.inflate(R.layout.inboxfragment, container, false);
         
        

				
				
       

				
				
			

				
				 ActionBar ab = getActivity().getActionBar();

			        ab.setTitle(Html.fromHtml("<font color=\"white\">" +"Inbox" + "("+ String.valueOf(a)+"/"+allinbox.size() +")"+ "</font>"));
				


    
        
		 cd = new ConnectionDetector(getActivity());
		 isInternetPresent = cd.isConnectingToInternet();
		


		 list2= (ListView)rootView.findViewById(R.id.list);
				
			
				
				
				
				
				
				
        return rootView;
	}
	
	
	
	public class Details extends AsyncTask<String,String,String>{

	
		

			@Override
		    protected void onPreExecute() {
				  cDialog = new ProgressDialog(InboxFragment.this.getActivity());
		          cDialog.setMessage("Please wait...");
		          cDialog.setIndeterminate(false);
		          cDialog.setCancelable(false);
		          cDialog.show();
		          
				
		       
		     
			}
				
				

				@Override
				protected String doInBackground(String... args) {
					// TODO Auto-generated method stub
				      
					allinbox.clear();
				
				inboxlist.clear();
					
					
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		             
		             params1.add(new BasicNameValuePair("student_id",Config.student_id));
		           
		             System.out.println("stu id: "+Config.student_id);
                    
		             jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.inboxdetailsurl, "POST", params1);
				
				   Log.i("JARRAY Value : ", "["+jArray+"]");
				    
				    try
				    {
				    	if(jArray != null){
				    	
				    	JSONObject c = jArray.getJSONObject(TAG_SRES);
				    	user = c.getJSONArray(TAG_INBOXARRAY);
				    
				    	Log.i("C come User ARRAY: ", "["+user+"]");
				    	
				    	for(int i=0;i<user.length();i++)
				    	{
				    		System.out.println("forloop1");
				    		JSONObject c1 = user.getJSONObject(i);
				    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
				    		
				    	    senderid = c2.getString(TAG_SENDER_ID);
				    		
				            inboxid = c2.getString(TAG_INBOXID);
				     
				        	sendername = c2.getString(TAG_SENDER_NAME);
				        	receiverid=c2.getString(TAG_RECEIVER_ID);
				        	receivername=c2.getString(TAG_RECEIVER_NAME);
				        	role=c2.getString(TAG_ROLE);
				        	subject=c2.getString(TAG_SUBJECT);
				        	message=c2.getString(TAG_MESSAGE);
				        	readstatus=c2.getString(TAG_READ_STATUS);
				        	sentdate=c2.getString(TAG_SENT_DATE);
				        	importantstatus=c2.getString(TAG_IMPORTANT_STATUS);
				        	
				        	inboxlist.add(subject);
				        	inboxlist.add(sentdate);
				        
				       	inboxlist.add(importantstatus);
				       	inboxlist.add(readstatus);

				        	 Inbox cnt = new Inbox(senderid, inboxid, sendername, receiverid, receivername, role, subject,message,readstatus,sentdate,importantstatus);
							    cnt.setsenderid(senderid);
							  
							    cnt.setinboxid(inboxid);
							    cnt.setrole(role);
							    cnt.setsendername(sendername);
							    cnt.setreceiverid(receiverid);
							    cnt.setreceivername(receivername);
							    cnt.setsubject(subject);
							    cnt.setmessage(message);
							    cnt.setreadstatus(readstatus);
							    cnt.setsentdate(sentdate);
							    cnt.setimportantstatus(importantstatus);

							    allinbox.add(cnt);
							    
				    	}
				    	
				    	}
				    	
				    	}catch (JSONException e) {
				        e.printStackTrace();
				    }
				    
					return null;
				}
				
				
				
				@Override
				protected void onPostExecute(String file_url) {
					 
					 super.onPostExecute(file_url);
					
				
					 
					 a=0;int b = 0; 
					 for(int j=0;j<allinbox.size();j++)
					 {
						run(list2, mAnimatingAway, j, j);
							
					 }
					 
					 
					 cDialog.dismiss();
					
					 
					 list2.setAdapter(new InboxArrayAdapter(InboxFragment.this.getActivity(), allinbox,R.layout.inboxlist));
					  
					 
			      
					 list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
						 

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								
								 sender = allinbox.get(arg2).getsendername(); 
								 receiver = allinbox.get(arg2).getreceivername(); 
								date = allinbox.get(arg2).getsentdate(); 
								 msg = allinbox.get(arg2).getmessage(); 
								important=allinbox.get(arg2).getimportantstatus();
								read=allinbox.get(arg2).getreadstatus();
								inbid=allinbox.get(arg2).getinboxid();

								
						        
						      
						         
								 Intent i= new Intent(InboxFragment.this.getActivity(),InboxDetails.class);
								 
								  i.putExtra("sender_name", sender);
								  i.putExtra("receiver_name", receiver);
								  i.putExtra("sent_date", date);
								  i.putExtra("message", msg);
								  i.putExtra("imporatant_status", important);
								  i.putExtra("read_status", read);
								  i.putExtra("inbox_id", inbid);


								 startActivity(i);
								 
							}
							
					        });

					 
			}



				private void run(AdapterView<?> arg0, View arg1, int arg2,
						long arg3)
				{
					// TODO Auto-generated method stub
					read=allinbox.get(arg2).getreadstatus();
				
				
					if(read.equalsIgnoreCase("0"))
					{
						a++;
					
					}
					else if (read.equalsIgnoreCase("1"))
					{
						b++;
					
					}
				
					 ActionBar ab = getActivity().getActionBar();
					 ab.setTitle(Html.fromHtml("<font color=\"white\">" +"Inbox" + "("+ String.valueOf(a)+"/"+allinbox.size() +")"+ "</font>"));
					
					
				} 
				
				
							
	}
	
	
	@Override
	    public void onResume() {
	        super.onResume();
	        
	        if (isInternetPresent) {
				 
				 
			        new Details().execute(); 
			      
					 }
	        
	        else{
	        	
	        	
	        	
	        }
	    
		   
	   
	    }
	
}