package com.deemsys.lmsmooc;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.actionbarsherlock.app.SherlockActivity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends SherlockActivity {
	EditText usname, paswd;
	String successL;
	TextView forgetpass;
	Button signin, back;
	public static String loginurl, avatarurl, loginscounturl;
	final Context context = this;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	public ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_ROLE = "role";
	private static final String TAG_ENABLED = "enabled";
	private static final String TAG_SRESL = "serviceresponse";
	private static final String TAG_STUDENT_ID = "student_id";
	private static final String TAG_FIRSTNAME = "firstname";
	private static final String TAG_LASTNAME = "lastname";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_INTERESTED_IN = "interested_in";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_AVATAR = "avatar";
	private static final String TAG_LOGINS = "logins";
	private static final String TAG_GENCODE = "gencode";
	private static final String TAG_AVATAR_URL = "avatar_url";
	public static final String MyPREFERENCES = "MyPrefs0";
	public static final String MyPREFERENCES1 = "MyPrefs1";
	public static final String UserName = "unnameKey";
	public static final String Password = "passKey";
	static String username;
	static String password;
	String role;
	String enabled;
	String serviceresponse;
	static String student_id;
	static String avatar_url;
	static String firstname;
	static String lastname;
	static String email;
	static String interested_in;
	static String gender;
	static String avatar;
	static String logins;
	String gencode;
	CheckBox check1;
	SharedPreferences sharedpreferences, sharedpreferences1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		LinearLayout layout = (LinearLayout) findViewById(R.id.loginlayout);
		loginurl = Config.ServerUrl + "Login.php?service=login";
		avatarurl = Config.ServerUrl + "Login.php?service=avatar";
		loginscounturl = Config.ServerUrl + "Login.php?service=logins";
		check1 = (CheckBox) findViewById(R.id.checkBox1);
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedpreferences1 = getSharedPreferences(MyPREFERENCES1,
				Context.MODE_PRIVATE);
		cd = new ConnectionDetector(getApplicationContext());
		usname = (EditText) findViewById(R.id.uname);
		isInternetPresent = cd.isConnectingToInternet();
		paswd = (EditText) findViewById(R.id.pswd);
		signin = (Button) findViewById(R.id.signin);
		back = (Button) findViewById(R.id.back);
		forgetpass = (TextView) findViewById(R.id.forgetpasword);
		ActionBar actions = getActionBar();
		actions.setTitle(Html.fromHtml("<font color='#ffffff'>Login</font>"));
		if (isInternetPresent) {

			new geturl().execute();

		}
		actions.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#3399FF")));
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				hideKeyboard(view);
				return false;
			}

		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intentSignUP = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intentSignUP);
			}
		});
		signin.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {

				String username = usname.getText().toString();
				String password = paswd.getText().toString();

				if (!username.equals("") && !password.equals("")) {

					if (isInternetPresent) {

						new AttemptLogin().execute();

					} else {
						AlertDialog alertDialog = new AlertDialog.Builder(
								LoginActivity.this).create();

						alertDialog.setTitle("Sorry User");

						alertDialog.setMessage("No network connection.");

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
				} else if (!password.equalsIgnoreCase("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(
							LoginActivity.this).create();

					alertDialog.setTitle("Sorry User");

					alertDialog.setMessage("Please enter username.");

					alertDialog.setIcon(R.drawable.delete);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {

								}
							});

					// Showing Alert Message
					alertDialog.show();

				} else if (!username.equalsIgnoreCase("")) {
					AlertDialog alertDialog = new AlertDialog.Builder(
							LoginActivity.this).create();

					alertDialog.setTitle("Sorry User");

					alertDialog.setMessage("Please enter password.");

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

				else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							LoginActivity.this).create();

					alertDialog.setTitle("Sorry User");

					alertDialog.setMessage("Enter login credentials.");

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
		forgetpass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String url = Config.URL_COMMON + "Student/signup";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}

		});
	}

	class Avatarfetch extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", "3"));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(avatarurl, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);

						Config.common_url = jUser.getString(TAG_AVATAR_URL);
						;

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

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			new urlfetch().execute();

		}
	}

	class urlfetch extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", "1"));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(avatarurl, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);
						Config.mainavatar_url = jUser.getString(TAG_AVATAR_URL);

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

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

			new loginsincrease().execute();

		}
	}

	class loginsincrease extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(loginscounturl, "POST",
					params1);

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

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			calld();

		}
	}

	class geturl extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("id", "4"));
			params1.add(new BasicNameValuePair("student_id", Config.student_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(avatarurl, "POST", params1);

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

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);

		}
	}

	class AttemptLogin extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("username", usname.getText()
					.toString()));
			params1.add(new BasicNameValuePair("password", paswd.getText()
					.toString()));

			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(loginurl, "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);
						Config.username = jUser.getString(TAG_USERNAME);
						Config.password = jUser.getString(TAG_PASSWORD);
						Config.firstname = jUser.getString(TAG_FIRSTNAME);
						Config.lastname = jUser.getString(TAG_LASTNAME);
						Config.email = jUser.getString(TAG_EMAIL);
						Config.interested_in = jUser
								.getString(TAG_INTERESTED_IN);
						Config.gender = jUser.getString(TAG_GENDER);
						Config.avatar = jUser.getString(TAG_AVATAR);
						Config.logins = jUser.getString(TAG_LOGINS);
						Config.gencode = jUser.getString(TAG_GENCODE);
						Config.role = jUser.getString(TAG_ROLE);
						Config.enabled = jUser.getString(TAG_ENABLED);
						Config.student_id = jUser.getString(TAG_STUDENT_ID);

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

			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Server not connected.");

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

				pDialog.dismiss();
			} else if (successL.equalsIgnoreCase("No")) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Sorry User");

				// Setting Dialog Message
				alertDialog.setMessage("Invalid username or password.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed
								usname.setText("");
								paswd.setText("");
								dialog.dismiss();
							}
						});

				// Showing Alert Message
				alertDialog.show();

				pDialog.dismiss();
			} else {
				new Avatarfetch().execute();

			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("sharedpreferences uname val"+sharedpreferences.getString(UserName, ""));
		if (sharedpreferences.contains(UserName)) {
			System.out.println("check wherther one pref has val");
			System.out.println("if sharedpref0 is avail");
			usname.setText(sharedpreferences.getString(UserName, ""));
			check1.setChecked(true);
			if (sharedpreferences.contains(Password)) {
				paswd.setText(sharedpreferences.getString(Password, ""));
				check1.setChecked(true);
				Config.username = sharedpreferences.getString(UserName, "");
				Config.password = sharedpreferences.getString(Password, "");
				Config.firstname = sharedpreferences.getString("firstname", "");
				Config.lastname = sharedpreferences.getString("lastname", "");
				Config.email = sharedpreferences.getString("emailstudent", "");
				Config.interested_in = sharedpreferences.getString(
						"interested_instudent", "");
				Config.gender = sharedpreferences.getString("gender", "");
				Config.avatar = sharedpreferences.getString("avatar", "");
				Config.logins = sharedpreferences.getString("logins", "");
				Config.role = sharedpreferences.getString("role", "");
				Config.enabled = sharedpreferences.getString("enabled", "");
				Config.student_id = sharedpreferences.getString("student_id ",
						"");
				Config.common_url = sharedpreferences
						.getString("commonurl", "");
				Config.URL_COMMON = sharedpreferences.getString("url_common",
						"");
				Config.mainavatar_url = sharedpreferences.getString(
						"avatar_urlmain", "");

				Intent i = new Intent(this, NewMainActivity.class);
				startActivity(i);
			}
		} 
		System.out.println("sharedpreferences1 values"+sharedpreferences1);
		if (sharedpreferences1.contains(UserName)) {
			System.out.println("check wherther one pref has val");
			usname.setText(sharedpreferences1.getString(UserName, ""));
			check1.setChecked(true);
			if (sharedpreferences1.contains(Password)) {
				paswd.setText(sharedpreferences1.getString(Password, ""));
				check1.setChecked(true);
			}
		}

	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public void onBackPressed() {

	}

	private void calld() {
		if (check1.isChecked() == true) {
			String n = usname.getText().toString();

			String u = paswd.getText().toString();

			Editor editor = sharedpreferences.edit();
			 editor.putString(UserName, n);
			 editor.putString(Password, u);
			editor.putString("firstname", Config.firstname);
			editor.putString("lastname", Config.lastname);
			editor.putString("emailstudent", Config.email);
			editor.putString("interested_instudent", Config.interested_in);
			editor.putString("gender", Config.gender);
			editor.putString("avatar", Config.avatar);
			editor.putString("logins", Config.logins);
			editor.putString("gencode", Config.gencode);
			editor.putString("logins", Config.logins);
			editor.putString("role", Config.role);
			editor.putString("enabled", Config.enabled);
			editor.putString("student_id ", Config.student_id);
			editor.putString("commonurl", Config.common_url);
			editor.putString("url_common", Config.URL_COMMON);
			editor.putString("avatar_urlmain", Config.mainavatar_url);

			editor.commit();
			Editor editor1 = sharedpreferences1.edit();
			editor1.putString(UserName, n);
			editor1.putString(Password, u);
			editor1.commit();
		} else {
			SharedPreferences settings = context.getSharedPreferences(
					"MyPrefs0", Context.MODE_PRIVATE);
			settings.edit().clear().commit();
		}
		Intent intentSignUP = new Intent(getApplicationContext(),
				NewMainActivity.class);
		startActivity(intentSignUP);
		pDialog.dismiss();

	}

}
