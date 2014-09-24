package com.deemsys.lmsmooc;



import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;


import org.json.JSONObject;


import com.deemsys.lmsmooc.BillingFragment;


import android.app.FragmentManager;

import android.app.ProgressDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;

import android.text.Html;

import android.widget.Button;

import android.widget.TextView;

public class BillingDetails extends SherlockFragmentActivity   {
//	private final Context context;
	public ProgressDialog cDialog;
	FragmentManager context=this.getFragmentManager();

	String senderstr;
	String receiverstr;
	String datestr;

	String messagestr;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	
	
	
	 TextView cou_name,author,pur_date,promo,reduc,amou1,amou2,trans_date,trans_id;
	 Button back;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      ActionBar ab = getSupportActionBar();
	      ab.setDisplayHomeAsUpEnabled(true);
	      getSupportActionBar().setHomeButtonEnabled(true);
	      
	      ab.setTitle(Html.fromHtml("<font color='#ffffff'>Billing</font>"));
		    
	        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3399FF")));

	      setContentView(R.layout.billingdetails);
	     
	      

				
				  cou_name = (TextView)findViewById(R.id.coursenameans);
			       author = (TextView)findViewById(R.id.authornameans);
					pur_date = (TextView)findViewById(R.id.dateans);
					promo = (TextView)findViewById(R.id.promoans);
					   amou1 = (TextView)findViewById(R.id.priceans);
					
					 reduc = (TextView)findViewById(R.id.reducans);
				       amou2 = (TextView)findViewById(R.id.amountans);
						trans_date = (TextView)findViewById(R.id.trdateans);
						trans_id = (TextView)findViewById(R.id.tridans);
				
					
				cou_name.setText(BillingFragment.course_name);
				author.setText(BillingFragment.course_author);
				pur_date.setText(BillingFragment.purchased_date);
				promo.setText(BillingFragment.promocode);
				
				
				reduc.setText(BillingFragment.reduction);
				amou1.setText("$"+BillingFragment.amount_paid);
				amou2.setText("$"+BillingFragment.amount_paid);
				trans_date.setText(BillingFragment.transaction_date);
				trans_id.setText(BillingFragment.transaction_id);
				
			
		
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
	
	 @Override
	 public void onBackPressed() 
  {

  }	
}