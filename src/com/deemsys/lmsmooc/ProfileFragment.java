package com.deemsys.lmsmooc;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
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
	private static final String TAG_SUCCESS1 = "success";
	public ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static String selecturl2 = Config.ServerUrl + Config.studentSignup1;
	private static final String TAG_SRESL = "serviceresponse";

	String selectedimage = "";

	String urlServer = Config.ServerUrl + Config.uploadpictwo;

	String captializefirstname, captitalizelastname;
	EditText firstnameedit, lastnameedit, usernameedit, emailedit, avataredit;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	int genderspinpos, interestedinspin;
	String firstname, lastname, username, email, interestedin, avatar, logins,
			genderstring;
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
		usernameedit.setFocusable(false);
		usernameedit.setClickable(true);
		genderselecthid = (TextView) rootView.findViewById(R.id.proftextgender);
		genderselecthid.setVisibility(View.INVISIBLE);
		firstnameedit.setText(Config.firstname);
		lastnameedit.setText(Config.lastname);
		usernameedit.setText(Config.username);
		emailedit.setText(Config.email);

		radioGroup1 = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
		radioGroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup2);

		subj = (RadioButton) rootView.findViewById(R.id.radiosubject);
		cours = (RadioButton) rootView.findViewById(R.id.radiocourse);

		maley = (RadioButton) rootView.findViewById(R.id.radiomale);
		femaley = (RadioButton) rootView.findViewById(R.id.radiofemale);

		genderstring = Config.gender;

		interestedin = Config.interested_in;

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

				String str = s.toString();

				if (str.length() > 0 && str.startsWith(" ")) {

					lastnameedit.setText("");

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
		((EditText) rootView.findViewById(R.id.firstname))
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@SuppressWarnings("deprecation")
					public void onFocusChange(View v, boolean hasFocus) {

						if (!hasFocus) {
							firstname = firstnameedit.getText().toString();

							if (firstname.length() == 0) {

								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog.setMessage("Enter firstname");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {

											}
										});

								alertDialog.show();
							} else if (firstname.length() >= 2
									&& firstname.length() <= 84) {
								if (isValidName(firstname)) {

								} else {
									AlertDialog alertDialog = new AlertDialog.Builder(
											getActivity()).create();

									alertDialog.setTitle("Sorry User");

									alertDialog
											.setMessage("Please enter valid firstname");

									alertDialog.setIcon(R.drawable.delete);

									alertDialog
											.setButton(
													"OK",
													new DialogInterface.OnClickListener() {

														public void onClick(
																final DialogInterface dialog,
																final int which) {
															firstnameedit
																	.setText("");
														}
													});

									alertDialog.show();
								}

							} else {
								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog
										.setMessage("Please enter valid firstname");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												firstnameedit.setText("");
											}
										});

								alertDialog.show();
							}
						}

					}

					private boolean isValidName(String firstname) {
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(firstname);
						return matcher.matches();
					}

				});
		((EditText) rootView.findViewById(R.id.lastname))
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@SuppressWarnings("deprecation")
					public void onFocusChange(View v, boolean hasFocus) {

						if (!hasFocus) {
							lastname = lastnameedit.getText().toString();

							if (lastname.length() == 0) {

								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog.setMessage("Enter lastname");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												lastnameedit.setText("");
											}
										});

								alertDialog.show();
							} else if (lastname.length() >= 2
									&& lastname.length() <= 84) {
								if (isValidName(lastname)) {

								} else {
									AlertDialog alertDialog = new AlertDialog.Builder(
											getActivity()).create();

									alertDialog.setTitle("Sorry User");

									alertDialog
											.setMessage("Please enter valid lastname");

									alertDialog.setIcon(R.drawable.delete);

									alertDialog
											.setButton(
													"OK",
													new DialogInterface.OnClickListener() {

														public void onClick(
																final DialogInterface dialog,
																final int which) {
															lastnameedit
																	.setText("");
														}
													});

									alertDialog.show();
								}

							} else {
								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog
										.setMessage("Please enter valid lastname");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												lastnameedit.setText("");
											}
										});

								alertDialog.show();
							}
						}

					}

					private boolean isValidName(String firstname) {
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(firstname);
						return matcher.matches();
					}

				});
		((EditText) rootView.findViewById(R.id.email))
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@SuppressWarnings("deprecation")
					public void onFocusChange(View v, boolean hasFocus) {

						if (!hasFocus) {
							email = emailedit.getText().toString();

							if (email.length() == 0) {

								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog.setMessage("Enter email");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												emailedit.setText("");
											}
										});

								alertDialog.show();
							} else if (email.length() >= 10
									&& email.length() <= 84) {
								if (isValidEmail(email)) {
									if (!Config.email.equalsIgnoreCase(email)) {
										new SelectEmail().execute();
									}

								} else {
									AlertDialog alertDialog = new AlertDialog.Builder(
											getActivity()).create();

									alertDialog.setTitle("Sorry User");

									alertDialog
											.setMessage("Enter a valid email");

									alertDialog.setIcon(R.drawable.delete);

									alertDialog
											.setButton(
													"OK",
													new DialogInterface.OnClickListener() {

														public void onClick(
																final DialogInterface dialog,
																final int which) {
															emailedit
																	.setText("");
														}
													});

									alertDialog.show();
								}

							} else {
								AlertDialog alertDialog = new AlertDialog.Builder(
										getActivity()).create();

								alertDialog.setTitle("Sorry User");

								alertDialog
										.setMessage("Enter a valid email");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {
												emailedit.setText("");
											}
										});

								alertDialog.show();
							}
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

				});
		savechanges.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				int a;

				isInternetPresent = cd.isConnectingToInternet();
				firstname = firstnameedit.getText().toString();
				lastname = lastnameedit.getText().toString();
				username = usernameedit.getText().toString();

				email = emailedit.getText().toString();

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
						&& interestedin != "null" && genderstring != "null") {
					a = 1;

					{

						if (firstname.length() >= 2 && firstname.length() <= 84
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

														if (!subj.isChecked()
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
																		.setTitle("Sorry User");

																// Setting
																// Dialog
																// Message
																alertDialog
																		.setMessage("Please select your gender");

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

															alertDialog
																	.setTitle("Sorry User");

															alertDialog
																	.setMessage("Please select your interested topic.");

															alertDialog
																	.setIcon(R.drawable.delete);

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

												} else {

													a = 0;
													AlertDialog alertDialog = new AlertDialog.Builder(
															ProfileFragment.this
																	.getActivity())
															.create();

													// Setting Dialog Title
													alertDialog
															.setTitle("Sorry User");

													// Setting Dialog Message
													alertDialog
															.setMessage("Enter a valid email id");

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
											alertDialog.setTitle("Sorry User");

											// Setting Dialog Message
											alertDialog
													.setMessage("Enter a valid username");

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
									alertDialog.setTitle("Sorry User");

									// Setting Dialog Message
									alertDialog
											.setMessage("Enter a valid lastname");

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
							alertDialog.setTitle("Sorry User");

							// Setting Dialog Message
							alertDialog
									.setMessage("Enter a valid firstname");

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

				if (isInternetPresent) {
					if (a == 1) {

						new UpdateProf().execute();

					}
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							getActivity()).create();

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

			private boolean isValidOther(String other) {
				// TODO Auto-generated method stub

				String EMAIL_PATTERN = "[a-zA-Z0-9]+[a-zA-Z0-9@_.,-/\n ]*$";

				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher matcher = pattern.matcher(other);
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

		if (!sourceFile.isFile()) {

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
				// String serverResponseMessage = conn.getResponseMessage();

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

			params1.add(new BasicNameValuePair("interested_in", interestedin));
			params1.add(new BasicNameValuePair("gender", genderstring));
			params1.add(new BasicNameValuePair("avatar", selectedimage));
			Config.firstname = result.toString();
			Config.lastname = caplast.toString();
			Config.username = usernameedit.getText().toString();
			Config.email = emailedit.getText().toString();

			Config.interested_in = interestedin;
			Config.gender = genderstring;
			Config.avatar = selectedimage;

			// Config.avatar="S"+Config.student_id+".jpg";

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

			// new ImageData().execute();
			if (successL.equalsIgnoreCase("Yes")) {
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

				alertDialog.setTitle("Success");

				alertDialog.setMessage("Profile update successfull");

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

				alertDialog.setTitle("Sorry User");

				alertDialog.setMessage("Profile not update successfull");

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

	// private class LoadImage extends AsyncTask<String, String, Bitmap> {
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	//
	// }
	//
	// protected Bitmap doInBackground(String... args) {
	// try {
	// bitmap = BitmapFactory.decodeStream((InputStream) new URL(
	// args[0]).getContent());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return bitmap;
	// }
	//
	// protected void onPostExecute(Bitmap image) {
	// if (image != null) {
	//
	// ProfileFragment.this.image.setImageBitmap(image);
	//
	// } else {
	//
	// // Toast.makeText(getActivity(),
	// // "Image Does Not exist or Network Error",
	// // Toast.LENGTH_SHORT).show();
	// }
	// }
	// }

	class SelectEmail extends AsyncTask<String, String, String> {
		ProgressDialog userDialog;

		@Override
		protected void onPreExecute() {
			userDialog = new ProgressDialog(getActivity());

			userDialog.setMessage("Please wait...");

			userDialog.setIndeterminate(false);
			userDialog.setCancelable(false);
			userDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			email = emailedit.getText().toString();
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("email", email));

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
			userDialog.dismiss();
			if (successL.equalsIgnoreCase("No")) {
				// emailDialog.dismiss();

			}

			else {
				// user name or email id alreadsdy exist
				// emailDialog.dismiss();
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

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

								emailedit.setText("");

							}
						});

				alertDialog.show();

			}

			// pDialog.dismiss();
			if (JsonParser.jss.equals("empty")) {

				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
						.create();

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

}