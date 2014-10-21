package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChangePasswordFragment extends Fragment {
	private static final String TAG_SUCCESS = "success";

	private static final String TAG_SRESL = "serviceresponse";
	public ProgressDialog pDialog;
	EditText oldpasswordedit, newpasswordedt, confirmpasswordedit;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	String oldpass, newpass, confirmpass, successL;
	Button getpassword;
	static AlertDialog alertDialog;
	public static String updateurl;
	View rootView ;
	public ChangePasswordFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView= inflater.inflate(R.layout.changepasswordfrag,
				container, false);
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.changepasslayout);
		pDialog = new ProgressDialog(getActivity());
		cd = new ConnectionDetector(getActivity());
		updateurl = Config.ServerUrl + "ChangePassword.php?service=changepass";
		oldpasswordedit = (EditText) rootView.findViewById(R.id.oldpassword);
		newpasswordedt = (EditText) rootView.findViewById(R.id.newpassword);
		confirmpasswordedit = (EditText) rootView
				.findViewById(R.id.confirmpassword);
		getpassword = (Button) rootView.findViewById(R.id.getpassword);
		alertDialog = new AlertDialog.Builder(getActivity())
		.create();
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				hideKeyboard(view);
				return false;
			}

		});
		
		((EditText)rootView.findViewById(R.id.oldpassword))
		.setOnFocusChangeListener(new OnFocusChangeListener() {

			@SuppressWarnings("deprecation")
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					oldpass = oldpasswordedit.getText().toString();

					if (oldpass.length() == 0) {

						

						alertDialog.setTitle("Sorry User");

						alertDialog
								.setMessage("Enter oldpassword");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {

									}
								});

						alertDialog.show();
					} else if (oldpass.equalsIgnoreCase(Config.password)) {
						

					} else {
						 

						alertDialog.setTitle("Sorry User");

						alertDialog
								.setMessage("Enter a valid current password");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {
										oldpasswordedit.setText("");
									}
								});

						alertDialog.show();
					}
				}

			}

			

		});
		
		((EditText)rootView.findViewById(R.id.newpassword))
		.setOnFocusChangeListener(new OnFocusChangeListener() {

			@SuppressWarnings("deprecation")
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					newpass = newpasswordedt.getText().toString();

					if (newpass.length() == 0) {

						 

						alertDialog.setTitle("Sorry User");

						alertDialog
								.setMessage("Enter new password");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {

									}
								});

						alertDialog.show();
						//
					} else if (!oldpass.equalsIgnoreCase(newpass))
					{
						if(newpass.length() >= 6
								&& newpass.length() <= 25)
						{
							if(passwordCheck(newpass))
							{
								
							}
							else
							{
								

								alertDialog.setTitle("Sorry User");

								alertDialog
										.setMessage("Enter a valid password");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												newpasswordedt.setText("");
											}
										});

								alertDialog.show();
							}
						}
						else
						{
							 

							alertDialog.setTitle("Sorry User");

							alertDialog
									.setMessage("Enter a valid password");

							alertDialog.setIcon(R.drawable.delete);

							alertDialog.setButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												final DialogInterface dialog,
												final int which) {
											newpasswordedt.setText("");
										}
									});

							alertDialog.show();
						}
						
						

					} else {
						 

						alertDialog.setTitle("Sorry User");

						alertDialog
								.setMessage("New password is same as old password");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {
										newpasswordedt.setText("");
									}
								});

						alertDialog.show();
					}
				}

			}

			

		});
		((EditText)rootView.findViewById(R.id.confirmpassword))
		.setOnFocusChangeListener(new OnFocusChangeListener() {

			@SuppressWarnings("deprecation")
			public void onFocusChange(View v, boolean hasFocus) {

				if (!hasFocus) {
					confirmpass = confirmpasswordedit.getText().toString();

					if (newpass.equals(confirmpass)) {

					} else {
						 

						alertDialog.setTitle("Sorry User");

						alertDialog
								.setMessage("Password doesn't match");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {
										confirmpasswordedit.setText("");
									}
								});

						alertDialog.show();
					}
				}

			}

		});

		getpassword.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				oldpass = oldpasswordedit.getText().toString();
				newpass = newpasswordedt.getText().toString();
				confirmpass = confirmpasswordedit.getText().toString();
				if (!oldpass.equalsIgnoreCase("")
						&& !newpass.equalsIgnoreCase("")
						&& !confirmpass.equalsIgnoreCase("")) {
					if (oldpass.equalsIgnoreCase(Config.password)) {

						if (!oldpass.equalsIgnoreCase(newpass)) {

							if (passwordCheck(newpass) && newpass.length() >= 6
								&& newpass.length() <= 25) {

								if (newpass.equals(confirmpass)) 
								{
									if (isInternetPresent) {

										Config.password = newpass;

										new UpdateProf().execute();

									} else {
										

										alertDialog.setTitle("Sorry User");

										alertDialog
												.setMessage("No network connection.");

										alertDialog.setIcon(R.drawable.delete);

										alertDialog
												.setButton(
														"OK",
														new DialogInterface.OnClickListener() {

															public void onClick(
																	final DialogInterface dialog,
																	final int which) {

															}
														});

										alertDialog.show();

									}

								} else {
									 

									alertDialog.setTitle("Sorry User");

									alertDialog
											.setMessage("New password mismatch with confirm password.");

									alertDialog.setIcon(R.drawable.delete);

									alertDialog
											.setButton(
													"OK",
													new DialogInterface.OnClickListener() {

														public void onClick(
																final DialogInterface dialog,
																final int which) {

														}
													});

									alertDialog.show();

								}
							}

							else {
								

								alertDialog.setTitle("Sorry User");

								alertDialog
										.setMessage("Length of password cannot be less then 6");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {

											}
										});

								alertDialog.show();
							}
						} else {
							
							alertDialog.setTitle("Sorry User");

							alertDialog
									.setMessage("New password is same as current password");

							alertDialog.setIcon(R.drawable.delete);

							alertDialog.setButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												final DialogInterface dialog,
												final int which) {

										}
									});

							alertDialog.show();

						}
					} else {
						 

						alertDialog.setTitle("Sorry User");

						alertDialog.setMessage("Enter a valid old password");

						alertDialog.setIcon(R.drawable.delete);

						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int which) {

									}
								});

						alertDialog.show();

					}
				} else {
					 

					alertDialog.setTitle("Sorry User");

					alertDialog.setMessage("Enter all fields.");

					alertDialog.setIcon(R.drawable.delete);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {

								}
							});

					alertDialog.show();

				}

			}
		});
		return rootView;
	}

	private boolean passwordCheck(String other) {
		// TODO Auto-generated method stub

		String pass_patter = "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%]).{8,25})";

		Pattern pattern = Pattern.compile(pass_patter);
		Matcher matcher = pattern.matcher(other);
		return matcher.matches();
	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	class UpdateProf extends AsyncTask<String, String, String> {
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

			params1.add(new BasicNameValuePair("username", Config.username));

			params1.add(new BasicNameValuePair("newpassword", newpasswordedt
					.getText().toString()));

			Config.password = newpasswordedt.getText().toString();

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(updateurl, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);

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
			if (successL.equalsIgnoreCase("Yes")) {
				 alertDialog = new AlertDialog.Builder(getActivity())
						.create();

				alertDialog.setTitle("Success");

				alertDialog.setMessage("Password updated successfully");

				alertDialog.setIcon(R.drawable.tick);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								final Intent intentSignUP = new Intent(
										getActivity(), NewMainActivity.class);
								startActivity(intentSignUP);
							}
						});

				alertDialog.show();

			} else {
				 

				alertDialog.setTitle("Sorry User");

				alertDialog.setMessage("Profile not updated successfully");

				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();
			}
			pDialog.dismiss();

		}
	}
	@Override
	public void onStop() {
	    super.onStop();
	    rootView.clearFocus();
	//    confirmpasswordedit.requestFocus();
	   
	}
}
