package com.deemsys.lmsmooc;






import java.io.InputStream;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.view.View.OnClickListener;


public class ProfileFragment extends Fragment {
	int i,count;
	String selectiongender;
	ImageButton buttt;
	Button savechanges;
	ImageButton but1,but2,but3,but4,but5,but6,but7,but8,but9,but10,but11,but12,but13,but14;
	ArrayList<Bitmap> imagelist= new ArrayList<Bitmap>();
	 Bitmap bitmap;
	 ImageView image;
	 Spinner gender,interstedin;
	 String[] interested = {
		      
		      "-Select your interested topics-",
		      "Subject",
		      "Courses"
		  };
	 String[] gendersel = {
		      
		      "-Select Gender-",
		      "Male",
		      "Female"
		  };
	public ProfileFragment()
	{
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.profilefrag, container, false);
        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.proffrag);
        layout.setOnTouchListener(new OnTouchListener()
	        {
	            @Override
	            public boolean onTouch(View view, MotionEvent ev)
	            {
	                hideKeyboard(view);
	                return false;
	            }

				
	        });
        savechanges = (Button)rootView.findViewById(R.id.savechagnes);
        image = (ImageView)rootView.findViewById(R.id.imageView1);
        interstedin = (Spinner)rootView.findViewById(R.id.spinner1);
        gender = (Spinner)rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, interested);
        interstedin.setAdapter(adapter);
        interstedin.setOnItemSelectedListener(
                      new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                  int arg2, long arg3) {
                            int position = interstedin.getSelectedItemPosition();
                    }
                          @Override
                          public void onNothingSelected(AdapterView<?> arg0) {
                              // TODO Auto-generated method stub
                          }
                      }
                  );
        savechanges.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	 
            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gendersel);
        gender.setAdapter(adapter1);
        gender.setOnItemSelectedListener(
                      new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                  int arg2, long arg3) {
                        	
                            int position = gender.getSelectedItemPosition();
                            System.out.println("positin value"+position);
                            imagelist.clear();
                            if(position==1)
                            {
                              count=13;
                            for(i=1;i<=13;i++)
                            {
                            new DownloadImageTask().execute(Config.AvatarUrl+i+".png");
                            }
                            }
                            else if(position==2)
                            {
                              count=14;
                            for(i=1;i<=14;i++)
                            {
                            new DownloadImageTask().execute(Config.AvatarUrl+"g"+i+".png");
                            }
                            }
                            
                          }
                          @Override
                          public void onNothingSelected(AdapterView<?> arg0) {
                              
                          }
                      }
                  );
        return rootView;
    }
	protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 }
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	  
	  

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	    	imagelist.add(result);
	    	System.out.println("number of images::"+imagelist.size());
	    	System.out.println(imagelist);
	     //   image.setImageBitmap(result);
	    	System.out.println("count value::"+count);
	        if(count==14&&imagelist.size()==14)
	        {
	        LayoutInflater li = LayoutInflater.from(getActivity());
			final View promptsView = li.inflate(R.layout.avatar_selection, null);
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

		
			alertDialogBuilder.setView(promptsView);
			buttt= (ImageButton) promptsView
					.findViewById(R.id.ImageButton1);
			buttt.setImageBitmap(imagelist.get(0));
			but2= (ImageButton) promptsView
					.findViewById(R.id.ImageButton2);
			but2.setImageBitmap(imagelist.get(1));
			but3= (ImageButton) promptsView
					.findViewById(R.id.ImageButton3);
			but3.setImageBitmap(imagelist.get(2));
			but4= (ImageButton) promptsView
					.findViewById(R.id.ImageButton4);
			but4.setImageBitmap(imagelist.get(3));
			but5= (ImageButton) promptsView
					.findViewById(R.id.ImageButton5);
			but5.setImageBitmap(imagelist.get(4));
			but6= (ImageButton) promptsView
					.findViewById(R.id.ImageButton6);
			but6.setImageBitmap(imagelist.get(5));
			but7= (ImageButton) promptsView
					.findViewById(R.id.ImageButton7);
			but7.setImageBitmap(imagelist.get(6));
			but8= (ImageButton) promptsView
					.findViewById(R.id.ImageButton8);
			but8.setImageBitmap(imagelist.get(7));
			but9= (ImageButton) promptsView
					.findViewById(R.id.ImageButton9);
			but9.setImageBitmap(imagelist.get(8));
			but10= (ImageButton) promptsView
					.findViewById(R.id.ImageButton10);
			but10.setImageBitmap(imagelist.get(9));
			but11= (ImageButton) promptsView
					.findViewById(R.id.ImageButton11);
			but11.setImageBitmap(imagelist.get(10));
			but12= (ImageButton) promptsView
					.findViewById(R.id.ImageButton12);
			but12.setImageBitmap(imagelist.get(11));
			but13= (ImageButton) promptsView
					.findViewById(R.id.ImageButton13);
			but13.setImageBitmap(imagelist.get(12));
			but14= (ImageButton) promptsView
					.findViewById(R.id.ImageButton14);
			but14.setImageBitmap(imagelist.get(13));
			
			buttt.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(0));
	            }
	        });
			but2.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(1));
	            }
	        });
			but3.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(2));
	            }
	        });
			but4.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(3));
	            }
	        });
			but5.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(4));
	            }
	        });
			but6.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(5));
	            }
	        });
			but7.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(6));
	            }
	        });
			but8.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(7));
	            }
	        });
			but9.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(8));
	            }
	        });
			but10.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(9));
	            }
	        });
			but11.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(10));
	            }
	        });
			but12.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(11));
	            }
	        });
			but13.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(12));
	            }
	        });
			
			
			alertDialogBuilder
			.setCancelable(false).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	    }
	    
	    else if (count==13 &&imagelist.size()==13)
	    {
	    	LayoutInflater li = LayoutInflater.from(getActivity());
			final View promptsView = li.inflate(R.layout.avatar_selection, null);
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getActivity());

		
			alertDialogBuilder.setView(promptsView);
			buttt= (ImageButton) promptsView
					.findViewById(R.id.ImageButton1);
			buttt.setImageBitmap(imagelist.get(0));
			but2= (ImageButton) promptsView
					.findViewById(R.id.ImageButton2);
			but2.setImageBitmap(imagelist.get(1));
			but3= (ImageButton) promptsView
					.findViewById(R.id.ImageButton3);
			but3.setImageBitmap(imagelist.get(2));
			but4= (ImageButton) promptsView
					.findViewById(R.id.ImageButton4);
			but4.setImageBitmap(imagelist.get(3));
			but5= (ImageButton) promptsView
					.findViewById(R.id.ImageButton5);
			but5.setImageBitmap(imagelist.get(4));
			but6= (ImageButton) promptsView
					.findViewById(R.id.ImageButton6);
			but6.setImageBitmap(imagelist.get(5));
			but7= (ImageButton) promptsView
					.findViewById(R.id.ImageButton7);
			but7.setImageBitmap(imagelist.get(6));
			but8= (ImageButton) promptsView
					.findViewById(R.id.ImageButton8);
			but8.setImageBitmap(imagelist.get(7));
			but9= (ImageButton) promptsView
					.findViewById(R.id.ImageButton9);
			but9.setImageBitmap(imagelist.get(8));
			but10= (ImageButton) promptsView
					.findViewById(R.id.ImageButton10);
			but10.setImageBitmap(imagelist.get(9));
			but11= (ImageButton) promptsView
					.findViewById(R.id.ImageButton11);
			but11.setImageBitmap(imagelist.get(10));
			but12= (ImageButton) promptsView
					.findViewById(R.id.ImageButton12);
			but12.setImageBitmap(imagelist.get(11));
			but13= (ImageButton) promptsView
					.findViewById(R.id.ImageButton13);
			but13.setImageBitmap(imagelist.get(12));
			but14= (ImageButton) promptsView
					.findViewById(R.id.ImageButton14);
		//	but14.setImageBitmap(imagelist.get(13));
			but14.setVisibility(View.INVISIBLE);
			buttt.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(0));
	            }
	        });
			but2.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(1));
	            }
	        });
			but3.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(2));
	            }
	        });
			but4.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(3));
	            }
	        });
			but5.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(4));
	            }
	        });
			but6.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(5));
	            }
	        });
			but7.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(6));
	            }
	        });
			but8.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(7));
	            }
	        });
			but9.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(8));
	            }
	        });
			but10.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(9));
	            }
	        });
			but11.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(10));
	            }
	        });
			but12.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(11));
	            }
	        });
			but13.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(12));
	            }
	        });
			but14.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	  image.setImageBitmap(imagelist.get(13));
	            }
	        });
			
			alertDialogBuilder
			.setCancelable(false).setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
			    
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
	    }
	    }
	 
}
}