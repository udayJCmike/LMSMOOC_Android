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
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class StudentSignup extends Activity {

	Boolean isInternetPresent;

	EditText fstname, pass, confirmpass, username1;
	EditText lstname, emailid;
	CheckBox check;
	TextView checktext;
	static String firstname;
	static String lastname;
	static String email;
	static String username;
	static String password;
	static String confirmpassword;
	static String successL;
	static String rolestr;
	static String enabledstr;
	static String userid;
	String fullnamefirst, fullnamelast;
	String captializefirstname, captitalizelastname;
	private static final String TAG_AVATAR_URL = "avatar_url";
	private ProgressDialog pDialog;
	Button back;

	final Context context = this;
	JSONObject jsonE;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_SUCCESS1 = "success";
	// private static final String TAG_USERNAME = "username";
	// private static final String TAG_PASSWORD = "password";
	// private static final String TAG_EMAIL = "email";
	// private static final String TAG_ROLE = "role";
	// private static final String TAG_ENABLED= "enabled";
	private static final String TAG_SRESL = "serviceresponse";
	// private static final String TAG_USERID= "user_id";

	private static String selecturl1 = Config.ServerUrl + Config.studentSignup;
	private static String selecturl2 = Config.ServerUrl + Config.studentSignup1;

	String avatarurl = Config.ServerUrl + "Login.php?service=avatar";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.student_signup);

		// /LinearLayout layout = (LinearLayout) findViewById(R.id.layoutt);
		ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
		sv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				hideKeyboard(v);
				return false;
			}
		});

		ActionBar actions = getActionBar();
		actions.setTitle(Html
				.fromHtml("<font color='#ffffff'>Registration</font>"));
		// actions.setDisplayHomeAsUpEnabled(true);
		actions.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#3399FF")));
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {

			new geturl().execute();

		}
		//

		// layout.setOnTouchListener(new OnTouchListener()
		// {
		// @Override
		// public boolean onTouch(View view, MotionEvent ev)
		// {
		// hideKeyboard(view);
		// return false;
		// }
		//
		//
		// });
		fstname = (EditText) findViewById(R.id.e1);
		lstname = (EditText) findViewById(R.id.e2);
		username1 = (EditText) findViewById(R.id.e3);
		emailid = (EditText) findViewById(R.id.e4);
		pass = (EditText) findViewById(R.id.e5);
		confirmpass = (EditText) findViewById(R.id.e6);
		check = (CheckBox) findViewById(R.id.checkBox1);

		// check.setMovementMethod(LinkMovementMethod.getInstance());
		checktext = (TextView) findViewById(R.id.checktext);
		// check.setText("");

		final Button signbtn = (Button) findViewById(R.id.btn1);

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentSignUP = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intentSignUP);
			}
		});
		fstname.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// CharSequence ss = s;
				// String mStr = fstname.getText().toString();
				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					fstname.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});
		lstname.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// CharSequence ss = s;
				// String mStr = lstname.getText().toString();
				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					lstname.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});
		emailid.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					emailid.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});

		username1.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					username1.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});

		pass.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					pass.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});
		confirmpass.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					pass.setText("");
				} else {

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

		});

		((EditText) findViewById(R.id.e3))
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						/*
						 * When focus is lost check that the text field has
						 * valid values.
						 */
						if (!hasFocus) {
							// validateInput(v);

							new SelectUsername1().execute();
						}
					}
				});
		((EditText) findViewById(R.id.e4))
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					public void onFocusChange(View v, boolean hasFocus) {
						/*
						 * When focus is lost check that the text field has
						 * valid values.
						 */
						if (!hasFocus) {
							// validateInput(v);
							new SelectEmail1().execute();
						}
					}
				});

		signbtn.setOnClickListener(new View.OnClickListener() {

			int a;

			@SuppressWarnings("deprecation")
			public void onClick(View view) {

				// ended.setEnabled(false);

				if (isInternetPresent) {

					firstname = fstname.getText().toString();

					lastname = lstname.getText().toString();

					email = emailid.getText().toString();

					username = username1.getText().toString();

					password = pass.getText().toString();

					confirmpassword = confirmpass.getText().toString();

					if (fstname.length() > 0 && lstname.length() > 0
							&& emailid.length() > 0 && username1.length() > 0
							&& pass.length() > 0 && confirmpass.length() > 0) {
						a = 1;

						{

							if (firstname.length() >= 2
									&& firstname.length() <= 84
									&& isValidName(firstname)) {
								{
									if (lastname.length() >= 2
											&& lastname.length() <= 84
											&& isValidName(lastname)) {
										{
											if (username.length() >= 6
													&& username.length() <= 25
													&& isValidOther(username)) {
												{
													if (email.length() >= 10
															&& email.length() <= 84
															&& isValidEmail(email)) {

														{
															if (password
																	.length() >= 6
																	&& password
																			.length() <= 25
																	&& passwordCheck(password)) {

																{

																	if (password
																			.equals(confirmpassword)) {

																		{
																			if (check
																					.isChecked()) {

																				a = 1;

																			}

																			else {

																				a = 0;
																				AlertDialog alertDialog = new AlertDialog.Builder(
																						StudentSignup.this)
																						.create();

																				// Setting
																				// Dialog
																				// Title
																				alertDialog
																						.setTitle("Sorry User");

																				// Setting
																				// Dialog
																				// Message
																				alertDialog
																						.setMessage("Please agreee to the terms of services");

																				// Setting
																				// Icon
																				// to
																				// Dialog
																				alertDialog
																						.setIcon(R.drawable.delete);

																				// Setting
																				// OK
																				// Button
																				alertDialog
																						.setButton(
																								"OK",
																								new DialogInterface.OnClickListener() {

																									public void onClick(
																											final DialogInterface dialog,
																											final int which) {
																										// Write
																										// your
																										// code
																										// here
																										// to
																										// execute
																										// after
																										// dialog
																										// closed

																									}
																								});

																				// Showing
																				// Alert
																				// Message
																				alertDialog
																						.show();

																			}
																		}

																	} else {

																		a = 0;
																		AlertDialog alertDialog = new AlertDialog.Builder(
																				StudentSignup.this)
																				.create();

																		// Setting
																		// Dialog
																		// Title
																		alertDialog
																				.setTitle("Sorry User");

																		// Setting
																		// Dialog
																		// Message
																		alertDialog
																				.setMessage("Password doesn't match");

																		// Setting
																		// Icon
																		// to
																		// Dialog
																		alertDialog
																				.setIcon(R.drawable.delete);

																		// Setting
																		// OK
																		// Button
																		alertDialog
																				.setButton(
																						"OK",
																						new DialogInterface.OnClickListener() {

																							public void onClick(
																									final DialogInterface dialog,
																									final int which) {
																								// Write
																								// your
																								// code
																								// here
																								// to
																								// execute
																								// after
																								// dialog
																								// closed

																							}
																						});

																		// Showing
																		// Alert
																		// Message
																		alertDialog
																				.show();

																	}
																}

															}

															else {

																a = 0;
																AlertDialog alertDialog = new AlertDialog.Builder(
																		StudentSignup.this)
																		.create();

																// Setting
																// Dialog Title
																alertDialog
																		.setTitle("Sorry User");

																// Setting
																// Dialog
																// Message
																alertDialog
																		.setMessage("Length of password cannot be less then 6");

																// Setting Icon
																// to Dialog
																alertDialog
																		.setIcon(R.drawable.delete);

																// Setting OK
																// Button
																alertDialog
																		.setButton(
																				"OK",
																				new DialogInterface.OnClickListener() {

																					public void onClick(
																							final DialogInterface dialog,
																							final int which) {
																						// Write
																						// your
																						// code
																						// here
																						// to
																						// execute
																						// after
																						// dialog
																						// closed

																					}
																				});

																// Showing Alert
																// Message
																alertDialog
																		.show();

															}
														}

													} else {

														a = 0;
														AlertDialog alertDialog = new AlertDialog.Builder(
																StudentSignup.this)
																.create();

														// Setting Dialog Title
														alertDialog
																.setTitle("Sorry User");

														// Setting Dialog
														// Message
														alertDialog
																.setMessage("Please enter valid email-id");

														// Setting Icon to
														// Dialog
														alertDialog
																.setIcon(R.drawable.delete);

														// Setting OK Button
														alertDialog
																.setButton(
																		"OK",
																		new DialogInterface.OnClickListener() {

																			public void onClick(
																					final DialogInterface dialog,
																					final int which) {
																				// Write
																				// your
																				// code
																				// here
																				// to
																				// execute
																				// after
																				// dialog
																				// closed

																			}
																		});

														// Showing Alert Message
														alertDialog.show();

													}
												}

											} else {

												a = 0;
												AlertDialog alertDialog = new AlertDialog.Builder(
														StudentSignup.this)
														.create();

												// Setting Dialog Title
												alertDialog
														.setTitle("Sorry User");

												// Setting Dialog Message
												alertDialog
														.setMessage("Please enter valid username");

												// Setting Icon to Dialog
												alertDialog
														.setIcon(R.drawable.delete);

												// Setting OK Button
												alertDialog
														.setButton(
																"OK",
																new DialogInterface.OnClickListener() {

																	public void onClick(
																			final DialogInterface dialog,
																			final int which) {
																		// Write
																		// your
																		// code
																		// here
																		// to
																		// execute
																		// after
																		// dialog
																		// closed

																	}
																});

												// Showing Alert Message
												alertDialog.show();

											}
										}

									} else {

										a = 0;
										AlertDialog alertDialog = new AlertDialog.Builder(
												StudentSignup.this).create();

										// Setting Dialog Title
										alertDialog.setTitle("Sorry User");

										// Setting Dialog Message
										alertDialog
												.setMessage("Please enter valid lastname");

										// Setting Icon to Dialog
										alertDialog.setIcon(R.drawable.delete);

										// Setting OK Button
										alertDialog
												.setButton(
														"OK",
														new DialogInterface.OnClickListener() {

															public void onClick(
																	final DialogInterface dialog,
																	final int which) {
																// Write your
																// code here to
																// execute after
																// dialog
																// closed

															}
														});

										// Showing Alert Message
										alertDialog.show();

									}
								}

							} else {

								a = 0;
								AlertDialog alertDialog = new AlertDialog.Builder(
										StudentSignup.this).create();

								// Setting Dialog Title
								alertDialog.setTitle("Sorry User");

								// Setting Dialog Message
								alertDialog
										.setMessage("Please enter valid firstname");

								// Setting Icon to Dialog
								alertDialog.setIcon(R.drawable.delete);

								// Setting OK Button
								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												// Write your code here to
												// execute after dialog
												// closed

											}
										});

								// Showing Alert Message
								alertDialog.show();

							}
						}

					} else {

						a = 0;
						AlertDialog alertDialog = new AlertDialog.Builder(
								StudentSignup.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("Sorry User");

						// Setting Dialog Message
						alertDialog.setMessage("Please enter all fields");

						// Setting Icon to Dialog
						alertDialog.setIcon(R.drawable.delete);

						// Setting OK Button
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {
										// Write your code here to execute after
										// dialog
										// closed

									}
								});

						// Showing Alert Message
						alertDialog.show();

					}

					if (a == 1) {
						new SelectUsername().execute();
					}

				}

				else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							StudentSignup.this).create();

					// Setting Dialog Title
					alertDialog.setTitle("Sorry User");

					// Setting Dialog Message
					alertDialog.setMessage("No network connection.");

					// Setting Icon to Dialog
					alertDialog.setIcon(R.drawable.delete);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
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

			// private boolean isValidNumber(String number) {
			// // TODO Auto-generated method stub
			//
			// String PHONE_REGEX =
			// "\\([1-9]{1}[0-9]{2}\\) [0-9]{3}\\-[0-9]{4}$";
			//
			// Pattern pattern = Pattern.compile(PHONE_REGEX);
			// Matcher matcher = pattern.matcher(number);
			// return matcher.matches();
			// }

			private boolean passwordCheck(String other) {
				// TODO Auto-generated method stub

				String pass_patter = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%]).{8,25})";

				Pattern pattern = Pattern.compile(pass_patter);
				Matcher matcher = pattern.matcher(other);
				return matcher.matches();
			}

			private boolean isValidOther(String other) {
				// TODO Auto-generated method stub

				String EMAIL_PATTERN = "[a-zA-Z0-9]+[a-zA-Z0-9@_.,-/\n ]*$";

				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(other);
				return matcher.matches();
			}

			// private boolean isValidOther1(String names) {
			// // TODO Auto-generated method stub
			//
			// String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";
			//
			// Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			// Matcher matcher = pattern.matcher(names);
			// return matcher.matches();
			// }

		});

	}

	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case android.R.id.home:
	//
	// Intent intentSignUP=new
	// Intent(getApplicationContext(),MainActivity.class);
	// startActivity(intentSignUP);
	// }
	// return true;
	// }
	class SelectUsername1 extends AsyncTask<String, String, String> {

		private ProgressDialog userDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			userDialog = new ProgressDialog(StudentSignup.this);

			userDialog.setMessage("Please wait...");

			userDialog.setIndeterminate(false);
			userDialog.setCancelable(false);
			userDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			String usernames = username1.getText().toString();
			params1.add(new BasicNameValuePair("username", usernames));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(selecturl1, "POST",
					params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						successL = jUser.getString(TAG_SUCCESS1);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}

			return null;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			if (successL.equalsIgnoreCase("No")) {
				userDialog.dismiss();
			}
			// {
			//
			//
			//
			// userDialog.dismiss();
			//
			// AlertDialog alertDialog = new AlertDialog.Builder(
			// StudentSignup.this).create();
			//
			// // Setting Dialog Title
			// alertDialog.setTitle("INFO!");
			//
			// // Setting Dialog Message
			// alertDialog.setMessage("username registered.");
			//
			// // Setting Icon to Dialog
			// alertDialog.setIcon(R.drawable.tick);
			//
			// alertDialog.setCancelable(false);
			// // Setting OK Button
			// alertDialog.setButton("OK", new DialogInterface.OnClickListener()
			// {
			//
			// public void onClick(final DialogInterface dialog,
			// final int which) {
			//
			// // username1.setText("");
			//
			// }
			// });
			//
			// // Showing Alert Message
			// alertDialog.show();
			//
			//
			//
			//
			// }

			else {

				userDialog.dismiss();
				// user name or email id alreadsdy exist

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("username already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

								username1.setText("");

							}
						});

				// Showing Alert Message
				alertDialog.show();

			}

			// pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

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

	class SelectEmail1 extends AsyncTask<String, String, String> {

		private ProgressDialog emailDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			emailDialog = new ProgressDialog(StudentSignup.this);

			emailDialog.setMessage("Please wait...");

			emailDialog.setIndeterminate(false);
			emailDialog.setCancelable(false);
			emailDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			String emails = emailid.getText().toString();
			params1.add(new BasicNameValuePair("email", emails));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(selecturl2, "POST",
					params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						successL = jUser.getString(TAG_SUCCESS1);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}

			return null;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			if (successL.equalsIgnoreCase("No")) {
				emailDialog.dismiss();
			}
			// {
			//
			// //user name or email id alreadsdy exist
			// emailDialog.dismiss();
			// AlertDialog alertDialog = new AlertDialog.Builder(
			// StudentSignup.this).create();
			//
			// // Setting Dialog Title
			// alertDialog.setTitle("INFO!");
			//
			// // Setting Dialog Message
			// alertDialog.setMessage("Email registered.");
			//
			// // Setting Icon to Dialog
			// alertDialog.setIcon(R.drawable.tick);
			//
			// alertDialog.setCancelable(false);
			// // Setting OK Button
			// alertDialog.setButton("OK", new DialogInterface.OnClickListener()
			// {
			//
			// public void onClick(final DialogInterface dialog,
			// final int which) {
			//
			// // emailid.setText("");
			//
			// }
			// });
			//
			//
			// alertDialog.show();
			//
			// }

			else {
				// user name or email id alreadsdy exist
				emailDialog.dismiss();
				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Email already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

								emailid.setText("");

							}
						});

				alertDialog.show();

			}

			// pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				// Showing Alert Message
				alertDialog.show();

			}

		}

	}

	class SelectUsername extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// userDialog = new ProgressDialog(StudentSignup.this);
			//
			// userDialog.setMessage("Please wait...");
			//
			// userDialog.setIndeterminate(false);
			// userDialog.setCancelable(true);
			// userDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("username",
					StudentSignup.username));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(selecturl1, "POST",
					params1);

			successL = "No";
			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						successL = jUser.getString(TAG_SUCCESS1);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

				successL = "No";

			}

			return null;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			if (successL.equalsIgnoreCase("No")) {

				// userDialog.dismiss();

				new SelectEmail().execute();

			}

			else {

				// userDialog.dismiss();
				// user name or email id alreadsdy exist

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("username already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

								username1.setText("");

							}
						});

				// Showing Alert Message
				alertDialog.show();

			}

			// pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

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

	class SelectEmail extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			// emailDialog = new ProgressDialog(StudentSignup.this);
			//
			// emailDialog.setMessage("Please wait...");
			//
			// emailDialog.setIndeterminate(false);
			// emailDialog.setCancelable(true);
			// emailDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("email", StudentSignup.email));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(selecturl2, "POST",
					params1);
			System.out.println("value for json::" + json);
			successL = "No";
			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);

						successL = jUser.getString(TAG_SUCCESS1);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

				successL = "No";

			}

			return null;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			if (successL.equalsIgnoreCase("No")) {
				// emailDialog.dismiss();

				new Login().execute();
			}

			else {
				// user name or email id alreadsdy exist
				// emailDialog.dismiss();
				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Email already exist.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setCancelable(false);
				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

								emailid.setText("");

							}
						});

				alertDialog.show();

			}

			// pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				// Showing Alert Message
				alertDialog.show();

			}

		}

	}

	class Login extends AsyncTask<String, String, String> {

		// public static final String urlE =
		// "http://192.168.1.158:8888/gpsandroid/service/Contact.php?service=insert";
		// public static final String urlE =
		// "http://192.168.1.71:8080/gpsandroid/service/Contact.php?service=insert";
		// public static final String urlE =
		// "http://208.109.248.89:80/gpsandroid/service/Contact.php?service=insert";
		public final String urlE = Config.ServerUrl + Config.signup;
		public final String urlE2 = Config.ServerUrl + Config.logininsert;

		JSONObject jsonE;
		JSONObject jsonE2;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(StudentSignup.this);

			pDialog.setMessage("Please wait...");

			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
			captializefirstname = StudentSignup.firstname;
			final StringBuilder result = new StringBuilder(
					captializefirstname.length());
			String[] words = captializefirstname.split("\\s");
			for (int i = 0, l = words.length; i < l; ++i) {
				if (i > 0)
					result.append(" ");
				result.append(Character.toUpperCase(words[i].charAt(0)))
						.append(words[i].substring(1));

			}
			captitalizelastname = StudentSignup.lastname;
			final StringBuilder caplast = new StringBuilder(
					captitalizelastname.length());
			String[] wordssplit = captitalizelastname.split("\\s");
			for (int i = 0, l = wordssplit.length; i < l; ++i) {
				if (i > 0)
					caplast.append(" ");
				caplast.append(Character.toUpperCase(wordssplit[i].charAt(0)))
						.append(wordssplit[i].substring(1));

			}
			fullnamefirst = result.toString();
			fullnamelast = caplast.toString();
			paramsE.add(new BasicNameValuePair("firstname", result.toString()));

			paramsE.add(new BasicNameValuePair("lastname", caplast.toString()));

			paramsE.add(new BasicNameValuePair("username",
					StudentSignup.username));

			paramsE.add(new BasicNameValuePair("email", StudentSignup.email));

			paramsE.add(new BasicNameValuePair("password",
					StudentSignup.password));

			//
			// paramsE.add(new
			// BasicNameValuePair("address1",ContactUs.address1));
			//
			// paramsE.add(new BasicNameValuePair("address2", address2));

			// paramsE.add(new BasicNameValuePair("city",ContactUs.city));
			//
			// paramsE.add(new BasicNameValuePair("state", ContactUs.state));

			JsonParser jLogin1 = new JsonParser();

			JSONObject json1 = jLogin1.makeHttpRequest(urlE, "POST", paramsE);
			System.out.println("value for json::" + json1);

			List<NameValuePair> paramsE2 = new ArrayList<NameValuePair>();

			paramsE2.add(new BasicNameValuePair("username",
					StudentSignup.username));

			paramsE2.add(new BasicNameValuePair("email", StudentSignup.email));

			paramsE2.add(new BasicNameValuePair("password",
					StudentSignup.password));

			JsonParser jLogin2 = new JsonParser();

			JSONObject json2 = jLogin2.makeHttpRequest(urlE2, "POST", paramsE2);
			System.out.println("value for json1::" + json2);

			return null;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						StudentSignup.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Error connecting database.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed

							}
						});

				// Showing Alert Message
				alertDialog.show();

			} else {

				new sendemailasync().execute();

			}

		}
	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	class geturl extends AsyncTask<String, String, String> {
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			String url = Config.URL_COMMON + "user_view_Termsofuses";

			checktext.setText(Html.fromHtml("<a href='" + url + "'>"
					+ "TERMS AND CONDITIONS</a>"));

			checktext.setMovementMethod(LinkMovementMethod.getInstance());
		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", "4"));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(avatarurl, "POST", params1);
			System.out.println("value for json::" + json);
			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);
						Config.URL_COMMON = jUser.getString(TAG_AVATAR_URL);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

				successL = "No";
			}
			return null;
		}
	}

	class sendemailasync extends AsyncTask<String, String, String> {
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			AlertDialog alertDialog = new AlertDialog.Builder(
					StudentSignup.this).create();

			// Setting Dialog Title
			alertDialog.setTitle("Success");

			// Setting Dialog Message
			alertDialog.setMessage("Registration successfull.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.tick);

			alertDialog.setCancelable(false);
			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

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

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("firstname", fullnamefirst));
			params1.add(new BasicNameValuePair("lastname", fullnamelast));
			params1.add(new BasicNameValuePair("emailid", StudentSignup.email));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ Config.studentSignupsendemail, "POST", params1);
			System.out.println("value for json::" + json);

			return null;
		}
	}

	@Override
	public void onBackPressed() {

	}
}
