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

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.sax.RootElement;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class InboxArrayAdapter extends ArrayAdapter<Inbox> {
	private final Context context;
	public ProgressDialog cDialog;
	
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public  ArrayList<String> inboxlist= new ArrayList<String>();
	public  List<Inbox> allinbox= new ArrayList<Inbox>();
 
	
	
	
	
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
	String importantstatus;
	TextView welcome;
	static String inboxid ;
	String sendername;
	static String receiverid ;
	String receivername;
	static String role;
	static String subject;
	String message;
	String readstatus;
	static String sentdate;
	
	String senderid;
	
	ListView lstvw;

	ListView list2;
	TextView welcomeusername;
	private  String inboxdetailsurl = "http://192.168.1.158:8888/LmsmoocAndroid/Services/Inbox.php?service=selectinbox";
	
	
	
	
	 Inbox user;
	int layoutResourceId;
	public static ArrayList<Inbox> data = new ArrayList<Inbox>();


 
	public InboxArrayAdapter(Activity context, List<Inbox> allinbox, int layoutResourceId) {
		super(context, R.layout.inboxlist, allinbox);
		
	  

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		System.out.println("inbox all value::"+allinbox);
		data = (ArrayList<Inbox>)allinbox;
		
	   
	}
	
	
	

	class UserHolder {
	 	    TextView subject,date;
	 	   ImageView importantstatus;
	   LinearLayout readstatus;
	   RelativeLayout inbox;
	
	   int a=0;
	   
	}
	

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			System.out.println("in::userholder");
		    View row = convertView;  
		    final UserHolder holder;
		
		    if (row== null)
		    {
		    	
	
		    	LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		  	row = inflater.inflate(R.layout.inboxlist,parent, false);
		    	
			holder = new UserHolder();
			holder.subject = (TextView) row.findViewById(R.id.addrstext);
			holder.date = (TextView) row.findViewById(R.id.veh_reg_no);
			holder.inbox =(RelativeLayout) row.findViewById(R.id.abs__list_item);
			
			holder.importantstatus=(ImageView) row.findViewById(R.id.redbtn);
			
		//	holder.importantstatus1=(ImageView) row.findViewById(R.id.greenbtn);
			holder.readstatus=(LinearLayout) row.findViewById(R.id.readstatus);
			
			 
           
			row.setTag(holder);
		    }
		    else {
			holder = (UserHolder) row.getTag();
			
		    }
		 
		    user = data.get(position);
		  
		    
		    holder.subject.setText(user.getsubject());
		    holder.date.setText(user.getsentdate());
		    
		    if(user.getreadstatus().equals("0"))
		    {
		    	
		    	 row.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
		    	
           
		    }
		    else  if(user.getreadstatus().equals("1"))
		    {
		    	 row.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#808080")));
		    	
		     
		    }
		    
		    
		 

		    
		   
		  
		    
		   
		    
		    if(user.getimportantstatus().equals("0"))
		    {
		    	
		    	
	    
		    	

		    	holder.importantstatus.setBackgroundResource(R.drawable.unimportant);
		    	  
		    
		    	
		    }
		    
		    
		
    	    
		    
		    else if(user.getimportantstatus().equals("1"))
		    {
		    	
		    

		    	holder.importantstatus.setBackgroundResource(R.drawable.important);
		    	  
		    	
		    	

		    }
		   

		    return row; 

		}
	
}
