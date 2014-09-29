package com.deemsys.lmsmooc;






import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;


public class StudentProfileFragment extends Fragment {
	static ImageView img;
	 public ProgressDialog pDialog;
	Bitmap bitmap;
	public StudentProfileFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.student_profile, container, false);
         img=(ImageView)rootView.findViewById(R.id.avatarimage);
        // new LoadImage().execute(ProfileActivity.avatar_whole_url);
        return rootView;
    }
//	private class LoadImage extends AsyncTask<String, String, Bitmap> {
//	    @Override
//	        protected void onPreExecute() {
//	            super.onPreExecute();
//
//	    }
//	       protected Bitmap doInBackground(String... args) {
//	         try {
//	               bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
//	        } catch (Exception e) {
//	              e.printStackTrace();
//	        }
//	      return bitmap;
//	       }
//	       protected void onPostExecute(Bitmap image) {
//	         if(image != null){
//	        	 System.out.println("bitmap"+bitmap);
//	           StudentProfileFragment.img.setImageBitmap(image);
//	          
//	         }else{
//	          
//	         //  Toast.makeText(getActivity(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
//	         }
//	       }
//	   }
}
