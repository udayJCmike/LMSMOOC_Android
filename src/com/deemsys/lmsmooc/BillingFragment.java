package com.deemsys.lmsmooc;





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
import com.deemsys.lmsmooc.BillingArrayAdapter;
import com.deemsys.lmsmooc.ProfileActivity;
import com.deemsys.lmsmooc.InboxFragment.Details;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.FragmentActivity;
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
import android.content.Intent;
import android.graphics.Typeface;

public class BillingFragment extends Fragment {
	
	
		
	public BillingFragment(){}
	
	
	//public class InboxFragment1 extends Activity  {
	
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public  ArrayList<String> billinglist= new ArrayList<String>();
	public  List<Billing> allbilling= new ArrayList<Billing>();
    JSONArray user=null;
    private static final String TAG_BILLINGARRAY = "Billing";
	private static final String TAG_SRES= "serviceresponse";

    private static final String TAG_STUDENT_BILLING_ID = "student_billing_id";
	private static final String TAG_STUDENT_ID= "student_id";
	private static final String TAG_COURSE_ID= "course_id";
	private static final String TAG_COURSE_NAME= "course_name";
	private static final String TAG_PROMOCODE= "promocode";
	private static final String TAG_REDUCTION= "reduction";
	private static final String TAG_AMOUNT_PAID= "amount_paid";
	private static final String TAG_COURSE_AUTHOR= "course_author";
	private static final String TAG_TRANSACTION_DATE= "transaction_date";
	private static final String TAG_PURCHASED_DATE= "purchased_date";
	private static final String TAG_TRANSACTION_ID= "transaction_id";
	private static final String TAG_STATUS= "status";

	public ProgressDialog cDialog;
	TextView welcome;
	//String student_regno;
	static String student_billing_id;
	static String student_id;
	static	String course_id;
	static	String course_name;
	static   String promocode;
	static   String reduction;
	static   String amount_paid;
	static   String course_author;
	static   String transaction_date;
	static   String purchased_date;
	static   String transaction_id;
	static   String status;
	
	
	ListView lstvw;
	Context context=this.getActivity();
//	Activity activity = (Activity) context;

	ListView list2;
	TextView numbers;
	
	
	
	Billing user1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
 
        View rootView = inflater.inflate(R.layout.billingfragment, container, false);
         
       
        
		 cd = new ConnectionDetector(getActivity());
		 isInternetPresent = cd.isConnectingToInternet();


		 list2= (ListView)rootView.findViewById(R.id.list);
				

					
					  return rootView;
	}
	
	
	
	  
	
	 
	public class Details extends AsyncTask<String,String,String>{

	
		

			@Override
		    protected void onPreExecute() {
				  cDialog = new ProgressDialog(BillingFragment.this.getActivity());
		          cDialog.setMessage("Please wait...");
		          cDialog.setIndeterminate(false);
		          cDialog.setCancelable(false);
		          cDialog.show();
		          
				
		          
		          System.out.println("pre execute");
		  
			}
				
				

				@Override
				protected String doInBackground(String... args) {
					// TODO Auto-generated method stub
				
				      
				
					
					allbilling.clear();
					
					billinglist.clear();
					
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		             
		             params1.add(new BasicNameValuePair("student_id",Config.student_id));
                    
		             jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.billingdetailsurl, "POST", params1);
				
				   Log.i("tagconvertstr", "["+jArray+"]");
				    
				    try
				    {
				    	if(jArray != null){
				    	
				    	JSONObject c = jArray.getJSONObject(TAG_SRES);
				    	Log.i("c object value", "["+c+"]");
				    	user = c.getJSONArray(TAG_BILLINGARRAY);
				    	Log.i("user array value", "["+user+"]");
				    	
				    	for(int i=0;i<user.length();i++)
				    	{
				    		System.out.println("forloop1");
				    		JSONObject c1 = user.getJSONObject(i);
				    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
				    		
				    		student_billing_id = c2.getString(TAG_STUDENT_BILLING_ID);
				    		
				    		student_id = c2.getString(TAG_STUDENT_ID);
				     
				    		course_id = c2.getString(TAG_COURSE_ID);
				    		course_name=c2.getString(TAG_COURSE_NAME);
				    		promocode=c2.getString(TAG_PROMOCODE);
				    		reduction=c2.getString(TAG_REDUCTION);
				    		amount_paid=c2.getString(TAG_AMOUNT_PAID);
				    		course_author=c2.getString(TAG_COURSE_AUTHOR);
				    		transaction_date=c2.getString(TAG_TRANSACTION_DATE);
				    		purchased_date=c2.getString(TAG_PURCHASED_DATE);
				    		transaction_id=c2.getString(TAG_TRANSACTION_ID);
				    		status=c2.getString(TAG_STATUS);

				        	
				        	billinglist.add(course_name);
				        	billinglist.add(purchased_date);
				        
				        	billinglist.add(promocode);
				        	billinglist.add(reduction);

				        	billinglist.add(amount_paid);

				       	
				        	 Billing cnt = new Billing(student_id, course_id,  course_name,promocode, reduction, amount_paid, course_author, transaction_date,purchased_date, transaction_id, status);

							    cnt.setstudentid(student_id);
							  
							    cnt.setcourseid(course_id);
							    //cnt.setdate(license_expiry);
							    cnt.setcoursename(course_name);
							    cnt.setpromocode(promocode);
							    cnt.setreduction(reduction);
							    cnt.setamountpaid(amount_paid);
							    cnt.setcourseauthor(course_author);
							    cnt.settransactiondate(transaction_date);
							    cnt.setpurchaseddate(purchased_date);
							    cnt.settransactionid(transaction_id);
							    cnt.setstatus(status);

					               allbilling.add(cnt);
				               
				               
				               
				           
				    	}
				    	
				    	}
				    	
				    	}catch (JSONException e) {
				        e.printStackTrace();
				    }
				    cDialog.dismiss();
					return null;
				}
				
				
				
				@Override
				protected void onPostExecute(String file_url) {
					  System.out.println("post execute");
					 super.onPostExecute(file_url);
					
					 list2.setAdapter(new BillingArrayAdapter(BillingFragment.this.getActivity(), allbilling,R.layout.billinglist));
					  
			          System.out.println("pre execute");
			          System.out.println("pre execute:"+allbilling);
			          System.out.println("pre execute"+billinglist);
					 list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
						 

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								System.out.println("in item click"+arg2);
								 course_name = allbilling.get(arg2).getcoursename(); 
								 course_author = allbilling.get(arg2).getcourseauthor(); 
								 purchased_date = allbilling.get(arg2).getpurchaseddate(); 
								 promocode = allbilling.get(arg2).getpromocode(); 
								 reduction=allbilling.get(arg2).getreduction();
								 amount_paid=allbilling.get(arg2).getamountpaid();
								 transaction_date=allbilling.get(arg2).gettransactiondate();
								 transaction_id=allbilling.get(arg2).gettransactionid();

								
								
								
							
						         
								 Intent i= new Intent(BillingFragment.this.getActivity(),BillingDetails.class);
								 
								  i.putExtra("course_name", course_name);
								  i.putExtra("course_author", course_author);
								  i.putExtra("purchased_date", purchased_date);
								  i.putExtra("amount_paid", amount_paid);
								  i.putExtra("promocode", promocode);
								  i.putExtra("reduction", reduction);
								  i.putExtra("transaction_date", transaction_date);
								  i.putExtra("transaction_id", transaction_id);

								 startActivity(i);
							}
							
					        });

					 

				
					 

					 
			} 
				
			}
	@Override
    public void onResume() {
        super.onResume();
        
        if (isInternetPresent) {
			 
			 
			// onResume();
		        new Details().execute(); 
		      
				 }
        
        else{
        	
        	
        	
        }
       System.out.println("RESUME METHOD CALLED");
	   
   
    }
	
	 
	
}
