package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.BillingFragment.Details;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.RootElement;
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
import android.widget.ListView;
import android.widget.TextView;

public class BillingArrayAdapter extends ArrayAdapter<Billing> {
	private final Context context;
	public ProgressDialog cDialog;
	
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public  ArrayList<String> billinglist= new ArrayList<String>();
	public  List<Billing> allbilling= new ArrayList<Billing>();
  
	
	
  
	String importantstatus;
	TextView welcome;
	//String student_regno;
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
	
	
	
	 Billing user;
	int layoutResourceId;
	public static ArrayList<Billing> data = new ArrayList<Billing>();

	  TextView num;

 
	public BillingArrayAdapter(Activity context, List<Billing> allbilling, int layoutResourceId) {
		super(context, R.layout.billinglist, allbilling);
		
	  

		this.layoutResourceId = layoutResourceId;
		this.context = context;
		System.out.println("billing all value::"+allbilling);
		data = (ArrayList<Billing>)allbilling;
		
	   
	}
	
	
	

	class UserHolder {
	 	    TextView cou_name,pur_date,promo,reduc,amou;
	 	  
	   
	}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			System.out.println("in::userholder");
		    View row = convertView;  
		    final UserHolder holder;
		
		    if (row== null) {
		    	
	
		    	LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		  	row = inflater.inflate(R.layout.billinglist,parent, false);
		    	
			holder = new UserHolder();
			holder.cou_name = (TextView) row.findViewById(R.id.coursenameans1);
			holder.pur_date = (TextView) row.findViewById(R.id.purdateans1);
			holder.promo = (TextView) row.findViewById(R.id.promoans1);
			holder.reduc = (TextView) row.findViewById(R.id.reductionans1);
			holder.amou = (TextView) row.findViewById(R.id.amountans1);


		//	holder.importantstatus=(ImageView) row.findViewById(R.id.logo);
			
			
			System.out.println("bill all value::"+holder.cou_name);
			System.out.println("bill all value::"+holder.pur_date);
			System.out.println("bill all value::"+holder.promo);
			System.out.println("bill all value::"+holder.reduc);

			System.out.println("bill all value::"+holder.amou);

           
			row.setTag(holder);
		    } else {
			holder = (UserHolder) row.getTag();
		    }
		 
		    user = data.get(position);
		  
		    
		    holder.cou_name.setText(user.getcoursename());
		    holder.pur_date.setText(user.getpurchaseddate());
		    holder.reduc.setText("$"+user.getreduction());
		    holder.amou.setText("$"+user.getamountpaid());
		    
		    
		
		    
		    
		    
		    if(user.getpromocode().equals("0"))
		    {
			    holder.promo.setText("No");

		    }
		    else if(user.getpromocode().equals("1"))
		    {
		    	  holder.promo.setText("Yes");
		    }
		    
		   
    	
		    return row; 

		}
		
		}