package com.deemsys.lmsmooc;




import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsys.lmsmooc.JsonParser;

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
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;


public class StudentSignup extends Activity {
    
   
    Boolean isInternetPresent;

    EditText fstname,pass,confirmpass,username1;
    EditText lstname,emailid;
     CheckBox check;
    static String firstname;
    static String lastname;
    static String email;
    static String username;
    static  String password;
    static  String confirmpassword;
    static  String successL;
    static  String rolestr;
    static  String enabledstr;
    static  String userid;

   

    
	  final Context context=this;
	    JSONObject jsonE;
   
	    
	    
	    
	    private static final String TAG_SUCCESS1 = "success";
//		private static final String TAG_USERNAME = "username";
//		private static final String TAG_PASSWORD = "password";
//		private static final String TAG_EMAIL = "email";
//		private static final String TAG_ROLE = "role";
//		private static final String TAG_ENABLED= "enabled";
		private static final String TAG_SRESL= "serviceresponse";
		//private static final String TAG_USERID= "user_id";


		private static String selecturl1 =Config.ServerUrl+Config.studentSignup;
		private static String selecturl2 = Config.ServerUrl+Config.studentSignup1;

	    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		setContentView(R.layout.student_signup);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutt);
		 ActionBar actions = getActionBar();
	        actions.setTitle(Html.fromHtml("<font color='#000000'>Registration</font>"));
	        actions.setDisplayHomeAsUpEnabled(true);
	        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent= cd.isConnectingToInternet();
//      
        

        layout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }

			
        });
     fstname = (EditText)findViewById(R.id.e1);
     lstname = (EditText)findViewById(R.id.e2);
     username1 = (EditText)findViewById(R.id.e3);
      emailid = (EditText)findViewById(R.id.e4);
      pass = (EditText)findViewById(R.id.e5);
     confirmpass = (EditText)findViewById(R.id.e6);
     check = (CheckBox)findViewById(R.id.checkBox1);
   
		
		final	Button signbtn=(Button)findViewById(R.id.btn1);
		
		
		fstname.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
//		    	CharSequence ss = s;
//		    	 String mStr = fstname.getText().toString();
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	fstname.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		lstname.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	CharSequence ss = s;
		    	 String mStr = lstname.getText().toString();
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	lstname.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		emailid.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	emailid.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		
		username1.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	username1.setText("");
		            }else{
		               
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		
		pass.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	pass.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		
	
//	btn3.setOnClickListener(new OnClickListener() {
//		 
//		@Override
//		public void onClick(View v) {
//
//		    fstname.setText("");
//		    lstname.setText("");
//		    email1.setText("");
//		    organistn.setText("");
//		    mob.setText("");
//		    add1.setText("");
//		  
//		    city1.setText("");
//		    state1.setText("");
//		 //  firstname="1";
//		    
//		}
//	});
	
		((EditText)findViewById(R.id.e3)).setOnFocusChangeListener(new OnFocusChangeListener() {

		    public void onFocusChange(View v, boolean hasFocus) {
		    /* When focus is lost check that the text field
		    * has valid values.
		    */
		      if (!hasFocus) {
		       //validateInput(v);
		    	  System.out.println("username value:" +username1);
		    	  new SelectUsername1().execute();
		      }
		    }
		 });
		((EditText)findViewById(R.id.e4)).setOnFocusChangeListener(new OnFocusChangeListener() {

		    public void onFocusChange(View v, boolean hasFocus) {
		    /* When focus is lost check that the text field
		    * has valid values.
		    */
		      if (!hasFocus) {
		       //validateInput(v);
		    	  new SelectEmail1().execute();
		      }
		    }
		 });
	
		   signbtn.setOnClickListener(new View.OnClickListener()
           {
             
                  int a;
			
				@SuppressWarnings("deprecation")
				public void onClick(View view)
                    {
                        
                       // ended.setEnabled(false);

         
					if(isInternetPresent)
        			{
					
						  firstname = fstname.getText().toString();
						  System.out.println("first name value::"+firstname);
						     lastname = lstname.getText().toString();
						     System.out.println("last name value::"+lastname);
						    email = emailid.getText().toString(); 
						    System.out.println("email  value::"+email);
						    
						    username = username1.getText().toString(); 
						    System.out.println("username value::"+username);
						   
						  
						    
						    password = pass.getText().toString(); 
						    System.out.println("password value::"+password);
						    
						     confirmpassword = confirmpass.getText().toString(); 
						     System.out.println("confirmpassword value::"+confirmpassword);
						     
						  
						    if(fstname.length()>0 && lstname.length()>0 && emailid.length()>0&& username1.length()>0&& pass.length()>0&&confirmpass.length()>0){
						    	 a=1;
						    	
								   {
									   
									    if (firstname.length()>3 &&firstname.length()<=15&&isValidName(firstname)) {
									    	{
											    if (lastname.length()>3&&lastname.length()<=15&& isValidName(lastname)) {
											    	{
													    if (username.length()>=6&&username.length()<=25&&isValidOther(username)) {
													    	{
													    		 if (email.length()>=10&&email.length()<=40&&isValidEmail(email)) {
																    	  
																	    {
																		    if (password.length()>=8&&password.length()<=25&&isValidOther(password)) {
																		    	  
																			    {
																			    	   
																							    if (password.equals(confirmpassword)) {
																			    		   
																							    	  {
																										    if (check.isChecked()) {
																						    		   
																									    
																									    	a=1;
																											
																										}
																									   
																								
																							
																						   
																				    else{
																				    	
																				    	a=0;
																				    	AlertDialog alertDialog = new AlertDialog.Builder(
																								StudentSignup.this).create();

																						// Setting Dialog Title
																						alertDialog.setTitle("Info");

																						// Setting Dialog Message
																						alertDialog.setMessage("Please agreee to the terms of services" );

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
																				    	
																				    	
																				    }}
																				
																			}
																							    else{
																							    	
																							    	a=0;
																							    	AlertDialog alertDialog = new AlertDialog.Builder(
																											StudentSignup.this).create();

																									// Setting Dialog Title
																									alertDialog.setTitle("Invalid confirmpassword");

																									// Setting Dialog Message
																									alertDialog.setMessage("Password and confirm password should be same." );

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
																							    	
																							    	
																							    }}
																							
																						}
																							    
																							    
																			    	   
																		    else{
																		    	
																		    	a=0;
																		    	AlertDialog alertDialog = new AlertDialog.Builder(
																						StudentSignup.this).create();

																				// Setting Dialog Title
																				alertDialog.setTitle("Invalid password");

																				// Setting Dialog Message
																				alertDialog.setMessage("Should contain 1 alphabet.Should contain 1 number.Should be 8 to 25 characters.Should contain 1 Special character." );

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
																		  
																		    	
																		    }}
																		
																	}
																    else{
																    	
																    	a=0;
																    	AlertDialog alertDialog = new AlertDialog.Builder(
																				StudentSignup.this).create();

																		// Setting Dialog Title
																		alertDialog.setTitle("Invalid Email");

																		// Setting Dialog Message
																		alertDialog.setMessage("Should contain  alphabets.Should contain  numbers.Should be 10 to 40 characters.Should contain 1 Special character." );

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
																    
																    }}
															
														}
													    else{
													    	
													    	a=0;
													    	AlertDialog alertDialog = new AlertDialog.Builder(
																	StudentSignup.this).create();

															// Setting Dialog Title
															alertDialog.setTitle("Invalid username");

															// Setting Dialog Message
															alertDialog.setMessage("Should contain alphabets.Should contain numbers.Should be 6 to 25 characters." );

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
													
													    }}
													
												}
											    else{
											    	
											    	a=0;
											    	AlertDialog alertDialog = new AlertDialog.Builder(
															StudentSignup.this).create();

													// Setting Dialog Title
													alertDialog.setTitle("Invalid lastname");

													// Setting Dialog Message
													alertDialog.setMessage("Should contain only alphabets.Should be 3 to 15 characters." );

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
											    
											    	
											    }}
											
										}
									    else{
									    	
									    	a=0;
									    	AlertDialog alertDialog = new AlertDialog.Builder(
													StudentSignup.this).create();

											// Setting Dialog Title
											alertDialog.setTitle("Invalid Firstname");

											// Setting Dialog Message
											alertDialog.setMessage("Should contain only alphabets.Should be 3 to 15 characters." );

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
									    
									    	
									    }}
								    
								    
								    }
						    else{
						    	
						    	a=0;
						     	AlertDialog alertDialog = new AlertDialog.Builder(
										StudentSignup.this).create();

								// Setting Dialog Title
								alertDialog.setTitle("INFO!");

								// Setting Dialog Message
								alertDialog.setMessage("Please enter all fields." );

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
						  
						     
						if(a==1){
        				new SelectUsername().execute();
						}
        				
        			}
					
					
					
        			else
        			{
        			 	AlertDialog alertDialog = new AlertDialog.Builder(
        						StudentSignup.this).create();

        				// Setting Dialog Title
        				alertDialog.setTitle("INFO!");

        				// Setting Dialog Message
        				alertDialog.setMessage("No network connection.");

        				// Setting Icon to Dialog
        				alertDialog.setIcon(R.drawable.delete);
        				

        				// Setting OK Button
        				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

        							public void onClick(final DialogInterface dialog,
        									final int which) {
        								
        								
        							}
        						});

        				// Showing Alert Message
        				alertDialog.show();
        				
        				
        			}


        
                    }
				
				
				private boolean isValidEmail(String email) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
								+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(email);
						return matcher.matches();
					}
					
				private boolean isValidName(String names) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(names);
						return matcher.matches();
					}
				private boolean isValidNumber(String number) {
					// TODO Auto-generated method stub
					
					
					String PHONE_REGEX ="\\([1-9]{1}[0-9]{2}\\) [0-9]{3}\\-[0-9]{4}$";

						Pattern pattern = Pattern.compile(PHONE_REGEX);
						Matcher matcher = pattern.matcher(number);
						return matcher.matches();
					}
				private boolean isValidOther(String other) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[a-zA-Z0-9]+[a-zA-Z0-9@_.,-/\n ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(other);
						return matcher.matches();
					}
				private boolean isValidOther1(String names) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(names);
						return matcher.matches();
					}
				
                    });
         
       
         
           }
	
	
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	         case android.R.id.home:
	        	 
	        	 Intent intentSignUP=new Intent(getApplicationContext(),MainActivity.class);
	        		startActivity(intentSignUP);
	      }
	     return true;
	 }
	 class SelectUsername1 extends AsyncTask<String,String,String>{

		 private ProgressDialog userDialog;
		 //System.out.println("");

			@Override
		     protected void onPreExecute() {
				  super.onPreExecute();
				  
			      userDialog = new ProgressDialog(StudentSignup.this);

			      userDialog.setMessage("Please wait...");
			      
			      userDialog.setIndeterminate(false);
			      userDialog.setCancelable(true);
			      userDialog.show();
			      
		     } 
			    
			 @Override
				protected String doInBackground(String... params) {
				 
				 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				 
		         
		         String usernames=username1.getText().toString();
				params1.add(new BasicNameValuePair("username",usernames));

		         JsonParser jLogin = new JsonParser();
		         System.out.println("passing value:"+usernames);
		         System.out.println(username1.getText().toString());
		         JSONObject json = jLogin.makeHttpRequest(selecturl1,"POST", params1);
		         System.out.println("value for json::"+json);
		         //successL="No";
		         if(json!=null)
		         {
		             try
		             {
		            	 if(json != null)
		            	 {
		            		 System.out.println("call");
		            	 System.out.println("json value::"+json);
		            	
		            	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
		            	 
		            	 successL = jUser.getString(TAG_SUCCESS1);
		            	 
		            	 System.out.println("success value: "+successL);
		            	 System.out.println("juser value"+jUser);
		            	 
		            	 }
		            	
		            }
		             
		             catch(JSONException e)
		             {
		            	 e.printStackTrace();
		            	 
		             }
		          }
		         else{
		            	 
		        	// successL ="No"; 
		        	 System.out.println("we are in select");
		    			  } 
		         
		         
					return null;
				
		     				  }
			 
			 
			@SuppressWarnings("deprecation")
			@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		        System.out.println("in post execute");
		        
		      System.out.println("vijay successL:"+successL);
		       if (successL.equalsIgnoreCase("No")){  userDialog.dismiss();}
//		    	 {
//		    	 
//
//		    	   
//		    	   userDialog.dismiss();
//		      		 
//		       		AlertDialog alertDialog = new AlertDialog.Builder(
//								StudentSignup.this).create();
//
//						// Setting Dialog Title
//						alertDialog.setTitle("INFO!");
//
//						// Setting Dialog Message
//						alertDialog.setMessage("username registered.");
//
//						// Setting Icon to Dialog
//						alertDialog.setIcon(R.drawable.tick);
//						
//		alertDialog.setCancelable(false);
//						// Setting OK Button
//						alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {
//
//									public void onClick(final DialogInterface dialog,
//											final int which) {
//									
//									 //   username1.setText("");
//										    
//									}
//								});
//
//						// Showing Alert Message
//						alertDialog.show();
//						
//		       	
//		       
//		    	   
//		      	 }
		      	 
		       else{
		    	   
		    	   userDialog.dismiss();
		     		 //user name or email id alreadsdy exist
		      		 
		       		AlertDialog alertDialog = new AlertDialog.Builder(
								StudentSignup.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("username already exist.");

						// Setting Icon to Dialog
						alertDialog.setIcon(R.drawable.delete);
						
		alertDialog.setCancelable(false);
						// Setting OK Button
						alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

									public void onClick(final DialogInterface dialog,
											final int which) {
									
									    username1.setText("");
										    
									}
								});

						// Showing Alert Message
						alertDialog.show();
						
		       	
		       }
		     
		       
		       
		     //   pDialog.dismiss();
		          if(JsonParser.jss.equals("empty"))
		          {
		       	   System.out.println("json null value");
		       	AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

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

		          
		        
		}
			
			}
	
	 
	 
	 class SelectEmail1 extends AsyncTask<String,String,String>{


		 private ProgressDialog emailDialog;
		
		
		@Override
	     protected void onPreExecute() {
			 super.onPreExecute();
			 emailDialog = new ProgressDialog(StudentSignup.this);

			 emailDialog.setMessage("Please wait...");
		      
			 emailDialog.setIndeterminate(false);
			 emailDialog.setCancelable(true);
			 emailDialog.show();
	     } 
		    
		 @Override
			protected String doInBackground(String... params) {
			 
			 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	         
	       String emails=emailid.getText().toString();
		params1.add(new BasicNameValuePair("email", emails));

	         JsonParser jLogin = new JsonParser();
	         System.out.println( emailid.getText().toString());
	         JSONObject json = jLogin.makeHttpRequest(selecturl2,"POST", params1);
	         System.out.println("value for json::"+json);
	       // successL="No";
	         if(json!=null)
	         {
	             try
	             {
	            	 if(json != null)
	            	 {
	            	 System.out.println("json value::"+json);
	            	
	            	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
	            	 
	            	 successL = jUser.getString(TAG_SUCCESS1);
	            	 
	            	 System.out.println("success value"+successL);
	            	 System.out.println("juser value"+jUser);
	            	 
	          
	            	 }
	            	
	            }
	             
	             catch(JSONException e)
	             {
	            	 e.printStackTrace();
	            	 
	             }
	          }
	         else{
	            	 
	        	// successL ="No"; 
	        	 System.out.println("we are in select");
	    			  } 
	         
	         
				return null;
			
	     				  }
		 
		 
		@SuppressWarnings("deprecation")
		@Override
		 protected void onPostExecute(String file_url) {
	    	   super.onPostExecute(file_url);
	        System.out.println("in post execute");
	        
	    	if (successL.equalsIgnoreCase("No")){emailDialog.dismiss();}
//	     	 {
//
//	      		 //user name or email id alreadsdy exist
//	       		emailDialog.dismiss();
//	        		AlertDialog alertDialog = new AlertDialog.Builder(
//							StudentSignup.this).create();
//
//					// Setting Dialog Title
//					alertDialog.setTitle("INFO!");
//
//					// Setting Dialog Message
//					alertDialog.setMessage("Email registered.");
//
//					// Setting Icon to Dialog
//					alertDialog.setIcon(R.drawable.tick);
//					
//	alertDialog.setCancelable(false);
//					// Setting OK Button
//					alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {
//
//								public void onClick(final DialogInterface dialog,
//										final int which) {
//									
//									 //   emailid.setText("");
//								  
//								}
//							});
//
//					
//					alertDialog.show();
//					
//	       	 }
	       	 
	       	 else
	       	 {
	      		 //user name or email id alreadsdy exist
	       		emailDialog.dismiss();
	        		AlertDialog alertDialog = new AlertDialog.Builder(
							StudentSignup.this).create();

					// Setting Dialog Title
					alertDialog.setTitle("INFO!");

					// Setting Dialog Message
					alertDialog.setMessage("Email already exist.");

					// Setting Icon to Dialog
					alertDialog.setIcon(R.drawable.delete);
					
	alertDialog.setCancelable(false);
					// Setting OK Button
					alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

								public void onClick(final DialogInterface dialog,
										final int which) {
									
									    emailid.setText("");
								  
								}
							});

					
					alertDialog.show();
					
	        	
	       		 
	       	 }
	        
	        
	     //   pDialog.dismiss();
	          if(JsonParser.jss.equals("empty"))
	          {
	       	   System.out.println("json null value");
	       	AlertDialog alertDialog = new AlertDialog.Builder(
					StudentSignup.this).create();

			// Setting Dialog Title
			alertDialog.setTitle("INFO!");

			// Setting Dialog Message
			alertDialog.setMessage("Error connecting database.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.delete);
			

			// Setting OK Button
			alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

						public void onClick(final DialogInterface dialog,
								final int which) {
							
							
						}
					});

			// Showing Alert Message
			alertDialog.show();
	     
	          }
	         
	        
	}
		
		}
class SelectUsername extends AsyncTask<String,String,String>{


	
	@Override
     protected void onPreExecute() {
//		  super.onPreExecute();
//	      userDialog = new ProgressDialog(StudentSignup.this);
//
//	      userDialog.setMessage("Please wait...");
//	      
//	      userDialog.setIndeterminate(false);
//	      userDialog.setCancelable(true);
//	      userDialog.show();
     } 
	    
	 @Override
		protected String doInBackground(String... params) {
		 
		 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
         
         params1.add(new BasicNameValuePair("username",StudentSignup.username));

         JsonParser jLogin = new JsonParser();
         System.out.println(username1.getText().toString());
         JSONObject json = jLogin.makeHttpRequest(selecturl1,"POST", params1);
         System.out.println("value for json::"+json);
         successL="No";
         if(json!=null)
         {
             try
             {
            	 if(json != null)
            	 {
            	 System.out.println("json value::"+json);
            	
            	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
            	 
            	 successL = jUser.getString(TAG_SUCCESS1);
            	 
            	 System.out.println("success value"+successL);
            	 System.out.println("juser value"+jUser);
            	 
            	 }
            	
            }
             
             catch(JSONException e)
             {
            	 e.printStackTrace();
            	 
             }
          }
         else{
            	 
        	 successL ="No"; 
        	 System.out.println("we are in select");
    			  } 
         
         
			return null;
		
     				  }
	 
	 
	@SuppressWarnings("deprecation")
	@Override
	 protected void onPostExecute(String file_url) {
    	   super.onPostExecute(file_url);
        System.out.println("in post execute");
        
      
       if (successL.equalsIgnoreCase("No"))
    	 {
    	 
//    	   userDialog.dismiss();

    	   new SelectEmail().execute();
    	   
    	   
      	 }
      	 
       else{
    	   
    	//   userDialog.dismiss();
     		 //user name or email id alreadsdy exist
      		 
       		AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("username already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);
				
alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
							
							    username1.setText("");
								    
							}
						});

				// Showing Alert Message
				alertDialog.show();
				
       	
       }
     
       
       
     //   pDialog.dismiss();
          if(JsonParser.jss.equals("empty"))
          {
       	   System.out.println("json null value");
       	AlertDialog alertDialog = new AlertDialog.Builder(
				StudentSignup.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("INFO!");

		// Setting Dialog Message
		alertDialog.setMessage("Error connecting database.");

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

          
        
}
	
	}


class SelectEmail extends AsyncTask<String,String,String>{


	 
	
	
	@Override
     protected void onPreExecute() {
//		 super.onPreExecute();
//		 emailDialog = new ProgressDialog(StudentSignup.this);
//
//		 emailDialog.setMessage("Please wait...");
//	      
//		 emailDialog.setIndeterminate(false);
//		 emailDialog.setCancelable(true);
//		 emailDialog.show();
     } 
	    
	 @Override
		protected String doInBackground(String... params) {
		 
		 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
         
       params1.add(new BasicNameValuePair("email", StudentSignup.email));

         JsonParser jLogin = new JsonParser();
         System.out.println( emailid.getText().toString());
         JSONObject json = jLogin.makeHttpRequest(selecturl2,"POST", params1);
         System.out.println("value for json::"+json);
        successL="No";
         if(json!=null)
         {
             try
             {
            	 if(json != null)
            	 {
            	 System.out.println("json value::"+json);
            	
            	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
            	 
            	 successL = jUser.getString(TAG_SUCCESS1);
            	 
            	 System.out.println("success value"+successL);
            	 System.out.println("juser value"+jUser);
            	 
          
            	 
            	 
            	 }
            	
            }
             
             catch(JSONException e)
             {
            	 e.printStackTrace();
            	 
             }
          }
         else{
            	 
        	 successL ="No"; 
        	 System.out.println("we are in select");
    			  } 
         
         
			return null;
		
     				  }
	 
	 
	@SuppressWarnings("deprecation")
	@Override
	 protected void onPostExecute(String file_url) {
    	   super.onPostExecute(file_url);
        System.out.println("in post execute");
        
    	if (successL.equalsIgnoreCase("No"))
     	 {
       		//emailDialog.dismiss();

       		 new Login().execute();
       	 }
       	 
       	 else
       	 {
      		 //user name or email id alreadsdy exist
       	//	emailDialog.dismiss();
        		AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Email already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);
				
alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								
								    emailid.setText("");
							  
							}
						});

				
				alertDialog.show();
				
        	
       		 
       	 }
        
        
     //   pDialog.dismiss();
          if(JsonParser.jss.equals("empty"))
          {
       	   System.out.println("json null value");
       	AlertDialog alertDialog = new AlertDialog.Builder(
				StudentSignup.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("INFO!");

		// Setting Dialog Message
		alertDialog.setMessage("Error connecting database.");

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.delete);
		

		// Setting OK Button
		alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

					public void onClick(final DialogInterface dialog,
							final int which) {
						
						
					}
				});

		// Showing Alert Message
		alertDialog.show();
     
          }
         
        
}
	
	}





class Login extends AsyncTask<String,String,String>{

	   private ProgressDialog pDialog;
	
	//public static final String urlE = "http://192.168.1.158:8888/gpsandroid/service/Contact.php?service=insert";
	//public static final String urlE = "http://192.168.1.71:8080/gpsandroid/service/Contact.php?service=insert";
	//public static final String urlE = "http://208.109.248.89:80/gpsandroid/service/Contact.php?service=insert";
	public final String urlE = Config.ServerUrl+Config.signup;
	public  final String urlE2 = Config.ServerUrl+Config.logininsert;

	  
	    JSONObject jsonE;
	    JSONObject jsonE2;


	@Override
  protected void onPreExecute() {
      super.onPreExecute();
      pDialog = new ProgressDialog(StudentSignup.this);

      pDialog.setMessage("Please wait...");
      
      pDialog.setIndeterminate(false);
      pDialog.setCancelable(true);
      pDialog.show();
      
  } 
	    
	 @Override
		protected String doInBackground(String... params) {
		 
			List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
		
			 paramsE.add(new BasicNameValuePair("firstname",StudentSignup.firstname));

          paramsE.add(new BasicNameValuePair("lastname", StudentSignup.lastname));

          paramsE.add(new BasicNameValuePair("username", StudentSignup.username));

          paramsE.add(new BasicNameValuePair("email", StudentSignup.email));

          paramsE.add(new BasicNameValuePair("password", StudentSignup.password));

//
//          paramsE.add(new BasicNameValuePair("address1",ContactUs.address1));
//
//         paramsE.add(new BasicNameValuePair("address2", address2));

//          paramsE.add(new BasicNameValuePair("city",ContactUs.city));
//
//          paramsE.add(new BasicNameValuePair("state", ContactUs.state));
			
			 JsonParser jLogin1 = new JsonParser();
			 
			 JSONObject json1 = jLogin1.makeHttpRequest(urlE,"POST", paramsE);
     	 System.out.println("value for json::"+json1);
     	 

     	 List<NameValuePair> paramsE2 = new ArrayList<NameValuePair>();
  		
			 paramsE2.add(new BasicNameValuePair("username",StudentSignup.username));

          paramsE2.add(new BasicNameValuePair("email", StudentSignup.email));

          paramsE2.add(new BasicNameValuePair("password", StudentSignup.password));

//
//          paramsE.add(new BasicNameValuePair("address1",ContactUs.address1));
//
//         paramsE.add(new BasicNameValuePair("address2", address2));

//          paramsE.add(new BasicNameValuePair("city",ContactUs.city));
//
//          paramsE.add(new BasicNameValuePair("state", ContactUs.state));
			
			 JsonParser jLogin2 = new JsonParser();
			 
			 JSONObject json2 = jLogin2.makeHttpRequest(urlE2,"POST", paramsE2);
     	 System.out.println("value for json1::"+json2);

			return null;
		
  				  }
	 
	 
	@SuppressWarnings("deprecation")
	@Override
	 protected void onPostExecute(String file_url) {
 	   super.onPostExecute(file_url);
     System.out.println("in post execute");
    // new SendEmailAsyncTask().execute();
 	   pDialog.dismiss();
       if(JsonParser.jss.equals("empty"))
       {
    	   System.out.println("json null value");
    	AlertDialog alertDialog = new AlertDialog.Builder(
				StudentSignup.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("INFO!");

		// Setting Dialog Message
		alertDialog.setMessage("Error connecting database.");

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
       else{
     	 
     		AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Message sent successfully.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.tick);
				
alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed
								  fstname.setText("");
								    lstname.setText("");
								    emailid.setText("");
								    username1.setText("");
								    pass.setText("");
								    confirmpass.setText("");
								    check.setChecked(false);
								    pDialog.dismiss();
								   
							}
						});

				// Showing Alert Message
				alertDialog.show();
     	
     	  
       }
     


}
	}

protected void hideKeyboard(View view)
{
    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
}
@Override
public void onBackPressed() 
{
}
}
          

  

