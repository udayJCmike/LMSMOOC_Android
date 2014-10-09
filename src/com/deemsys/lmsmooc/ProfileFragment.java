package com.deemsys.lmsmooc;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ProfileFragment extends Fragment {

	private static int RESULT_LOAD_IMAGE = 1;

	private String upLoadServerUri = null;
	private int serverResponseCode = 0;
	boolean imageuploaded;

	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	JSONArray user = null;
	String imagepath;
	String stu_id;
	JSONObject jsonE;

	boolean alphabet;
	boolean numbers;
	boolean spl_char;

	public ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";

	private static final String TAG_SRESL = "serviceresponse";

	String selectedimage = "";

	String urlServer = Config.ServerUrl + Config.uploadpictwo;

	String captializefirstname, captitalizelastname;
	EditText firstnameedit, lastnameedit, usernameedit, emailedit, avataredit,
			passwordedit;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	int genderspinpos, interestedinspin;
	String firstname, lastname, username, email, interestedin, avatar, logins,
			password, genderstring;
	public static String updateurl, avatarurl;
	int i, count;
	String selectiongender, successL;
	ImageButton buttt;
	Button savechanges;
	Button browse, upload;
	TextView browseimage;

	RadioButton maley, femaley;
	RadioButton subj, cours;
	TextView genderselecthid;
	ImageButton but1, but2, but3, but4, but5, but6, but7, but8, but9, but10,
			but11, but12, but13, but14;
	ArrayList<Bitmap> imagelist = new ArrayList<Bitmap>();
	Bitmap bitmap;
	ImageView image;

	private RadioGroup radioGroup1, radioGroup2;

	public ProfileFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.profilefrag, container, false);
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.proffrag);
		ScrollView sv = (ScrollView) rootView.findViewById(R.id.scrollView1);
		sv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				hideKeyboard(v);
				return false;
			}
		});
		pDialog = new ProgressDialog(getActivity());
		updateurl = Config.ServerUrl + "UpdateProfile.php?service=updateprof";
		avatarurl = Config.AvatarUrl + LoginActivity.avatar;
		firstnameedit = (EditText) rootView.findViewById(R.id.firstname);
		lastnameedit = (EditText) rootView.findViewById(R.id.lastname);
		emailedit = (EditText) rootView.findViewById(R.id.email);
		usernameedit = (EditText) rootView.findViewById(R.id.username);
		passwordedit = (EditText) rootView.findViewById(R.id.password);
		genderselecthid = (TextView) rootView.findViewById(R.id.proftextgender);
		genderselecthid.setVisibility(View.INVISIBLE);
		firstnameedit.setText(Config.firstname);
		lastnameedit.setText(Config.lastname);
		usernameedit.setText(Config.username);
		emailedit.setText(Config.email);
		passwordedit.setText(Config.password);

		radioGroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
		radioGroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);

		subj = (RadioButton) rootView.findViewById(R.id.radiosubject);
		cours = (RadioButton) rootView.findViewById(R.id.radiocourse);

		maley = (RadioButton) rootView.findViewById(R.id.radiomale);
		femaley = (RadioButton) rootView.findViewById(R.id.radiofemale);

		genderstring = Config.gender;
		System.out.println("gender strinfg value::" + genderstring);
		interestedin = Config.interested_in;
		System.out.println("intert strinfg value::" + interestedin);
		savechanges = (Button) rootView.findViewById(R.id.savechagnes);
		browse = (Button) rootView.findViewById(R.id.browse);
		upload = (Button) rootView.findViewById(R.id.upload);
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiosubject:
					interestedin = "subject";
					break;
				case R.id.radiocourse:
					interestedin = "courses";
					break;

				default:
					break;
				}
			}
		});
		radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiomale:
					genderstring = "male";
					break;
				case R.id.radiofemale:
					genderstring = "female";
					break;

				default:
					break;
				}
			}
		});
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				hideKeyboard(view);
				return false;
			}

		});
		upload.setVisibility(View.INVISIBLE);

		browseimage = (TextView) rootView.findViewById(R.id.browseimage);

		upLoadServerUri = Config.ServerUrl + Config.uploadpicone;

		firstnameedit.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();

				if (str.length() > 0 && str.startsWith(" ")) {

					firstnameedit.setText("");

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
		lastnameedit.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				CharSequence ss = s;
				String mStr = lastnameedit.getText().toString();
				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					firstnameedit.setText("");

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
		emailedit.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					emailedit.setText("");
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

		usernameedit.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					usernameedit.setText("");
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

		passwordedit.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String str = s.toString();
				if (str.length() > 0 && str.startsWith(" ")) {

					passwordedit.setText("");
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

		if (genderstring.equalsIgnoreCase("Male")) {
			genderstring = "male";
			radioGroup2.check(R.id.radiomale);

		} else if (genderstring.equalsIgnoreCase("Female")) {
			genderstring = "female";
			radioGroup2.check(R.id.radiofemale);

		} else if (genderstring.equalsIgnoreCase("")) {

		}
		if (interestedin.equalsIgnoreCase("Subject")) {
			interestedin = "subject";
			radioGroup1.check(R.id.radiosubject);
		} else if (interestedin.equalsIgnoreCase("Courses")) {
			interestedin = "courses";
			radioGroup1.check(R.id.radiocourse);
		} else {

		}

		cd = new ConnectionDetector(getActivity());

		savechanges.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				int a;

				isInternetPresent = cd.isConnectingToInternet();
				firstname = firstnameedit.getText().toString();
				lastname = lastnameedit.getText().toString();
				username = usernameedit.getText().toString();
				password = passwordedit.getText().toString();
				email = emailedit.getText().toString();

				// if(imageuploaded==false){
				//
				// selectedimage="S"+Config.student_id+".jpg";
				//
				//
				// }
				if (selectedimage.equalsIgnoreCase("")
						&& genderstring.equalsIgnoreCase("male")) {
					selectedimage = "Sbdefault.jpg";
				}
				if (selectedimage.equalsIgnoreCase("")
						&& genderstring.equalsIgnoreCase("female")) {
					selectedimage = "Sgdefault.jpg";
				}

				if (firstnameedit.length() > 0 && lastnameedit.length() > 0
						&& emailedit.length() > 0 && usernameedit.length() > 0
						&& passwordedit.length() > 0 && interestedin != "null"
						&& genderstring != "null") {
					a = 1;

					{

						if (firstname.length() >= 3 && firstname.length() <= 15
								&& isValidName(firstname)) {
							{
								if (lastname.length() >= 3
										&& lastname.length() <= 15
										&& isValidName(lastname)) {
									{
										if (username.length() >= 6
												&& username.length() <= 25
												&& isValidOther(username)) {
											{
												if (email.length() >= 10
														&& email.length() <= 40
														&& isValidEmail(email)) {

													{
														if (password.length() >= 8
																&& password
																		.length() <= 25
																&& passwordCheck(password)) {
															if (!subj
																	.isChecked()
																	&& cours.isChecked()
																	|| subj.isChecked()
																	&& !cours
																			.isChecked())

															{
																if (!maley
																		.isChecked()
																		&& femaley
																				.isChecked()
																		|| maley.isChecked()
																		&& !femaley
																				.isChecked())

																{

																	a = 1;
																} else {
																	a = 0;
																	AlertDialog alertDialog = new AlertDialog.Builder(
																			ProfileFragment.this
																					.getActivity())
																			.create();

																	// Setting
																	// Dialog
																	// Title
																	alertDialog
																			.setTitle("Failed");

																	// Setting
																	// Dialog
																	// Message
																	alertDialog
																			.setMessage("Please select your gender.");

																	// Setting
																	// Icon to
																	// Dialog
																	alertDialog
																			.setIcon(R.drawable.delete);

																	// Setting
																	// OK Button
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

															} else {
																a = 0;
																AlertDialog alertDialog = new AlertDialog.Builder(
																		ProfileFragment.this
																				.getActivity())
																		.create();

																// Setting
																// Dialog Title
																alertDialog
																		.setTitle("Failed");

																// Setting
																// Dialog
																// Message
																alertDialog
																		.setMessage("Please select your interested topic.");

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

														else {

															a = 0;
															AlertDialog alertDialog = new AlertDialog.Builder(
																	ProfileFragment.this
																			.getActivity())
																	.create();

															// Setting Dialog
															// Title
															alertDialog
																	.setTitle("Invalid password");

															// Setting Dialog
															// Message
															alertDialog
																	.setMessage("Should contain 1 alphabet.Should contain 1 number.Should be 8 to 25 characters.Should contain 1 Special character.");

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

															// Showing Alert
															// Message
															alertDialog.show();

														}
													}

												} else {

													a = 0;
													AlertDialog alertDialog = new AlertDialog.Builder(
															ProfileFragment.this
																	.getActivity())
															.create();

													// Setting Dialog Title
													alertDialog
															.setTitle("Invalid Email");

													// Setting Dialog Message
													alertDialog
															.setMessage("Should contain  alphabets.Should contain  numbers.Should be 10 to 40 characters.Should contain 1 Special character.");

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
													ProfileFragment.this
															.getActivity())
													.create();

											// Setting Dialog Title
											alertDialog
													.setTitle("Invalid username");

											// Setting Dialog Message
											alertDialog
													.setMessage("Should contain alphabets.Should contain numbers.Should be 6 to 25 characters.");

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
																	// your code
																	// here to
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
											ProfileFragment.this.getActivity())
											.create();

									// Setting Dialog Title
									alertDialog.setTitle("Invalid lastname");

									// Setting Dialog Message
									alertDialog
											.setMessage("Should contain only alphabets.Should be 3 to 15 characters.");

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
															// Write your code
															// here to execute
															// after dialog
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
									ProfileFragment.this.getActivity())
									.create();

							// Setting Dialog Title
							alertDialog.setTitle("Invalid Firstname");

							// Setting Dialog Message
							alertDialog
									.setMessage("Should contain only alphabets.Should be 3 to 15 characters.");

							// Setting Icon to Dialog
							alertDialog.setIcon(R.drawable.delete);

							// Setting OK Button
							alertDialog.setButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												final DialogInterface dialog,
												final int which) {
											// Write your code here to execute
											// after dialog
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
							ProfileFragment.this.getActivity()).create();

					// Setting Dialog Title
					alertDialog.setTitle("INFO!");

					// Setting Dialog Message
					alertDialog.setMessage("Please enter all fields.");

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

				if (isInternetPresent) {
					if (a == 1) {

						new UpdateProf().execute();

					}
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

					alertDialog.setTitle("INFO!");

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

				String PHONE_REGEX = "\\([1-9]{1}[0-9]{2}\\) [0-9]{3}\\-[0-9]{4}$";

				Pattern pattern = Pattern.compile(PHONE_REGEX);
				Matcher matcher = pattern.matcher(number);
				return matcher.matches();
			}

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

			private boolean isValidOther1(String names) {

				String EMAIL_PATTERN = "[a-zA-Z0-9]+[a-zA-Z0-9@_.,-/\n ]*$";

				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(names);
				return matcher.matches();
			}

		});

		browse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
				// upload.setVisibility(View.VISIBLE);

			}
		});

		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					public void run() {

						uploadFile(imagepath);
					}
				}).start();

				if (imageuploaded == false) {

					selectedimage = "S" + Config.student_id + ".jpg";

					Toast.makeText(getActivity(), "Image uploaded",
							Toast.LENGTH_LONG).show();

				} else {

					Toast.makeText(getActivity(), "Image uploaded failed",
							Toast.LENGTH_LONG).show();

				}
			}

		});

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("data value::" + data);
		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

			getActivity();
			if (resultCode != Activity.RESULT_CANCELED) {
				if (data != null && !data.equals("Intent { (has extras) }")) {

					getActivity();
					if (resultCode == Activity.RESULT_OK && data != null) {
						try {
							Uri selectedImageUri = data.getData();
							imagepath = getPath(selectedImageUri);
							Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
							// imageview.setImageBitmap(bitmap);
							browseimage.setText(imagepath);

							if (bitmap != null) {

								upload.setVisibility(View.VISIBLE);

							}
						} catch (Exception e) {
							// Toast.makeText(getActivity(),
							// "Exception" + e, 1000).show();
						}
					} else {

					}

				}
			}

		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().getContentResolver().query(uri,
				projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public int uploadFile(String sourceFileUri) {

		String fileName = "S" + Config.student_id + ".jpg";

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		System.out.println("We are in upload file");
		if (!sourceFile.isFile()) {

			Log.e("uploadFile", "Source File not exist :" + imagepath);

			// runOnUiThread(new Runnable() {
			// public void run() {
			// messageText.setText("Source File not exist :"+ imagepath);
			// }
			// });

			return 0;

		} else {
			try {

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(
						sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();

				Log.i("uploadFile", "HTTP Response is : "
						+ serverResponseMessage + ": " + serverResponseCode);

				if (serverResponseCode == 200) {

					// runOnUiThread(new Runnable() {
					// public void run() {
					// String msg =
					// "File Upload Completed.\n\n See uploaded file here : \n\n"
					// +" F:/wamp/wamp/www/uploads";
					// //messageText.setText(msg);
					// Toast.makeText(MainActivity.this,
					// "File Upload Complete.", Toast.LENGTH_SHORT).show();
					// }
					// });
				}

				// close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {

				// dialog.dismiss();
				ex.printStackTrace();

				// runOnUiThread(new Runnable() {
				// public void run() {
				// messageText.setText("MalformedURLException Exception : check script url.");
				// Toast.makeText(MainActivity.this, "MalformedURLException",
				// Toast.LENGTH_SHORT).show();
				// }
				// });

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {

				// dialog.dismiss();
				e.printStackTrace();

			}

			imageuploaded = true;
			return serverResponseCode;

		}
	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
				imagelist.add(mIcon11);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {

			// imagelist.add(result);
			System.out.println("number of images::" + imagelist.size());
			System.out.println(imagelist);
			// image.setImageBitmap(result);
			if (count == 14 && imagelist.size() == 14) {
				pDialog.dismiss();
				LayoutInflater li = LayoutInflater.from(getActivity());
				final View promptsView = li.inflate(R.layout.avatar_selection,
						null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());

				alertDialogBuilder.setView(promptsView);
				buttt = (ImageButton) promptsView
						.findViewById(R.id.ImageButton1);
				buttt.setImageBitmap(imagelist.get(0));
				but2 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton2);
				but2.setImageBitmap(imagelist.get(1));
				but3 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton3);
				but3.setImageBitmap(imagelist.get(2));
				but4 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton4);
				but4.setImageBitmap(imagelist.get(3));
				but5 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton5);
				but5.setImageBitmap(imagelist.get(4));
				but6 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton6);
				but6.setImageBitmap(imagelist.get(5));
				but7 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton7);
				but7.setImageBitmap(imagelist.get(6));
				but8 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton8);
				but8.setImageBitmap(imagelist.get(7));
				but9 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton9);
				but9.setImageBitmap(imagelist.get(8));
				but10 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton10);
				but10.setImageBitmap(imagelist.get(9));
				but11 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton11);
				but11.setImageBitmap(imagelist.get(10));
				but12 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton12);
				but12.setImageBitmap(imagelist.get(11));
				but13 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton13);
				but13.setImageBitmap(imagelist.get(12));
				but14 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton14);
				but14.setImageBitmap(imagelist.get(13));

				buttt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(0));

						selectedimage = "/resources/images/users/g1.png";
					}
				});
				but2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(1));
						selectedimage = "/resources/images/users/g2.png";
					}
				});
				but3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(2));
						selectedimage = "/resources/images/users/g3.png";
					}
				});
				but4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(3));
						selectedimage = "/resources/images/users/g4.png";
					}
				});
				but5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(4));
						selectedimage = "/resources/images/users/g5.png";
					}
				});
				but6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(5));
						selectedimage = "/resources/images/users/g6.png";
					}
				});
				but7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(6));
						selectedimage = "/resources/images/users/g7.png";
					}
				});
				but8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(7));
						selectedimage = "/resources/images/users/g8.png";
					}
				});
				but9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(8));
						selectedimage = "/resources/images/users/g9.png";
					}
				});
				but10.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(9));
						selectedimage = "/resources/images/users/g10.png";
					}
				});
				but11.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(10));
						selectedimage = "/resources/images/users/g11.png";
					}
				});
				but12.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(11));
						selectedimage = "/resources/images/users/g12.png";
					}
				});
				but13.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(12));
						selectedimage = "/resources/images/users/g13.png";
					}
				});
				but14.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(13));
						selectedimage = "/resources/images/users/g14.png";
					}
				});
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}

			else if (count == 13 && imagelist.size() == 13) {
				pDialog.dismiss();
				LayoutInflater li = LayoutInflater.from(getActivity());
				final View promptsView = li.inflate(R.layout.avatar_selection,
						null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());

				alertDialogBuilder.setView(promptsView);
				buttt = (ImageButton) promptsView
						.findViewById(R.id.ImageButton1);
				buttt.setImageBitmap(imagelist.get(0));
				but2 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton2);
				but2.setImageBitmap(imagelist.get(1));
				but3 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton3);
				but3.setImageBitmap(imagelist.get(2));
				but4 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton4);
				but4.setImageBitmap(imagelist.get(3));
				but5 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton5);
				but5.setImageBitmap(imagelist.get(4));
				but6 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton6);
				but6.setImageBitmap(imagelist.get(5));
				but7 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton7);
				but7.setImageBitmap(imagelist.get(6));
				but8 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton8);
				but8.setImageBitmap(imagelist.get(7));
				but9 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton9);
				but9.setImageBitmap(imagelist.get(8));
				but10 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton10);
				but10.setImageBitmap(imagelist.get(9));
				but11 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton11);
				but11.setImageBitmap(imagelist.get(10));
				but12 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton12);
				but12.setImageBitmap(imagelist.get(11));
				but13 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton13);
				but13.setImageBitmap(imagelist.get(12));
				but14 = (ImageButton) promptsView
						.findViewById(R.id.ImageButton14);
				// but14.setImageBitmap(imagelist.get(13));
				but14.setVisibility(View.INVISIBLE);

				buttt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(0));
						selectedimage = "/resources/images/users/1.png";
					}
				});
				but2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(1));
						selectedimage = "/resources/images/users/2.png";
					}
				});
				but3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(2));
						selectedimage = "/resources/images/users/3.png";
					}
				});
				but4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(3));
						selectedimage = "/resources/images/users/4.png";
					}
				});
				but5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(4));
						selectedimage = "/resources/images/users/5.png";
					}
				});
				but6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(5));
						selectedimage = "/resources/images/users/6.png";
					}
				});
				but7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(6));
						selectedimage = "/resources/images/users/7.png";
					}
				});
				but8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(7));
						selectedimage = "/resources/images/users/8.png";
					}
				});
				but9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(8));
						selectedimage = "/resources/images/users/9.png";
					}
				});
				but10.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(9));
						selectedimage = "/resources/images/users/10.png";
					}
				});
				but11.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(10));
						selectedimage = "/resources/images/users/11.png";
					}
				});
				but12.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(11));
						selectedimage = "/resources/images/users/12.png";
					}
				});
				but13.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						image.setVisibility(View.VISIBLE);
						image.setImageBitmap(imagelist.get(12));
						selectedimage = "/resources/images/users/13.png";
					}
				});

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								})

						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		}

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

			captializefirstname = firstnameedit.getText().toString();
			final StringBuilder result = new StringBuilder(
					captializefirstname.length());
			String[] words = captializefirstname.split("\\s");
			for (int i = 0, l = words.length; i < l; ++i) {
				if (i > 0)
					result.append(" ");
				result.append(Character.toUpperCase(words[i].charAt(0)))
						.append(words[i].substring(1));

			}
			captitalizelastname = lastnameedit.getText().toString();
			final StringBuilder caplast = new StringBuilder(
					captitalizelastname.length());
			String[] wordssplit = captitalizelastname.split("\\s");
			for (int i = 0, l = wordssplit.length; i < l; ++i) {
				if (i > 0)
					caplast.append(" ");
				caplast.append(Character.toUpperCase(wordssplit[i].charAt(0)))
						.append(wordssplit[i].substring(1));

			}

			params1.add(new BasicNameValuePair("oldusername", Config.username));
			params1.add(new BasicNameValuePair("firstname", result.toString()));
			params1.add(new BasicNameValuePair("lastname", caplast.toString()));
			params1.add(new BasicNameValuePair("email", emailedit.getText()
					.toString()));

			params1.add(new BasicNameValuePair("username", usernameedit
					.getText().toString()));
			params1.add(new BasicNameValuePair("password", passwordedit
					.getText().toString()));
			params1.add(new BasicNameValuePair("interested_in", interestedin));
			params1.add(new BasicNameValuePair("gender", genderstring));
			params1.add(new BasicNameValuePair("avatar", selectedimage));
			Config.firstname = result.toString();
			Config.lastname = caplast.toString();
			Config.username = usernameedit.getText().toString();
			Config.email = emailedit.getText().toString();
			Config.password = passwordedit.getText().toString();
			Config.interested_in = interestedin;
			Config.gender = genderstring;
			Config.avatar = selectedimage;

			// Config.avatar="S"+Config.student_id+".jpg";

			System.out.println("selected image value" + selectedimage);
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin
					.makeHttpRequest(updateurl, "POST", params1);
			System.out.println("value for json::" + json);
			if (json != null) {
				try {
					if (json != null) {
						System.out.println("json value::" + json);

						JSONObject jUser = json.getJSONObject(TAG_SRESL);
						successL = jUser.getString(TAG_SUCCESS);
						System.out.println("successL value" + successL);
						// avatar = jUser.getString(TAG_AVATAR_URL );
						System.out.println("avatar url in second async"
								+ avatar);
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

			// new ImageData().execute();
			if (successL.equalsIgnoreCase("Yes")) {
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("Profile updated successfully");

				alertDialog.setIcon(R.drawable.tick);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

								Intent intentSignUP = new Intent(getActivity(),
										NewMainActivity.class);
								startActivity(intentSignUP);
							}
						});

				alertDialog.show();

			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

				alertDialog.setTitle("INFO!");

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
			// Intent intentSignUP=new
			// Intent(getActivity(),ProfileActivity.class);
			// startActivity(intentSignUP);
		}
	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						args[0]).getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {
			if (image != null) {
				System.out.println("bitmap" + bitmap);
				ProfileFragment.this.image.setImageBitmap(image);

			} else {

				// Toast.makeText(getActivity(),
				// "Image Does Not exist or Network Error",
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

}