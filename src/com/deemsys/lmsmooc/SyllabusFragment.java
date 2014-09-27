package com.deemsys.lmsmooc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.app.ProgressDialog;
import android.content.Intent;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class SyllabusFragment extends Fragment {
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Child>> listDataChild;
	String category_name;
	public ProgressDialog cDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	String  sectionid,lectureid,sectionname,lecturename,lecturetype;
	ListView list2;
	List<Child> ch_list;
	List<Child> empty_ch_list;
	 ArrayList<ArrayList<HashMap<String, String>>> Syllabus=new ArrayList<ArrayList<HashMap<String, String>>>();

	public static ArrayList<String> vehiclelist= new ArrayList<String>();
	public static List<Category> category= new ArrayList<Category>();
    JSONArray user = null;
    List<List<Child>> lists = new ArrayList<List<Child>>();
    List<List<Child>> empty_lists = new ArrayList<List<Child>>();
  
	   
		
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.syllabusfragment, container, false);
	        setHasOptionsMenu(true);
	        expListView = (ExpandableListView)v.findViewById(R.id.lvExp);
	        new CategoryDetails().execute(); 
	        expListView.setOnGroupClickListener(new OnGroupClickListener() {
	        	 
	            @Override
	            public boolean onGroupClick(ExpandableListView parent, View v,
	                    int groupPosition, long id) {
	                // Toast.makeText(getApplicationContext(),
	                // "Group Clicked " + listDataHeader.get(groupPosition),
	                // Toast.LENGTH_SHORT).show();
	                return false;
	            }
	        });
	 
	      
	        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
	 
	            @Override
	            public void onGroupExpand(int groupPosition) {
//	                Toast.makeText(getActivity(),
//	                        listDataHeader.get(groupPosition) + " Expanded",
//	                        Toast.LENGTH_SHORT).show();
	            }
	        });
	 
	        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
	 
	            @Override
	            public void onGroupCollapse(int groupPosition) {
//	                Toast.makeText(getActivity(),
//	                        listDataHeader.get(groupPosition) + " Collapsed",
//	                        Toast.LENGTH_SHORT).show();
	 
	            }
	        });
	 
	       
	        expListView.setOnChildClickListener(new OnChildClickListener() {
	 
	            @Override
	            public boolean onChildClick(ExpandableListView parent, View v,
	                    int groupPosition, int childPosition, long id) {
	           if(listDataChild.get(listDataHeader.get(groupPosition)).get(
                       childPosition).getlecttype().equalsIgnoreCase("Text")){
	            	 Intent i=new Intent(getActivity(),CourseText.class);
	  	    	   i.putExtra("lect_type", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlecttype());
	  	    	
	  	    	   i.putExtra("lect_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlectureid());
	  	    	   i.putExtra("section_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecid());
	  	    	   i.putExtra("section_name",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecname());
	  	    	   i.putExtra("lecture_name", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getName());
	  	    	   startActivity(i);
	                System.out.println("child data"+listDataChild.get(listDataHeader.get(groupPosition)).get(
	                                        childPosition).getlecttype());
	           } else  if(listDataChild.get(listDataHeader.get(groupPosition)).get(
                       childPosition).getlecttype().equalsIgnoreCase("Video"))
	           {
	        	   Intent i=new Intent(getActivity(),CourseVideo.class);
	  	    	   i.putExtra("lect_type", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlecttype());
	  	    	
	  	    	   i.putExtra("lect_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlectureid());
	  	    	   i.putExtra("section_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecid());
	  	    	   i.putExtra("section_name",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecname());
	  	    	   i.putExtra("lecture_name", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getName());
	  	    	   startActivity(i);
	                System.out.println("child data"+listDataChild.get(listDataHeader.get(groupPosition)).get(
	                                        childPosition).getlecttype());
	        	   
	           }
	           else  if(listDataChild.get(listDataHeader.get(groupPosition)).get(
                       childPosition).getlecttype().equalsIgnoreCase("Audio"))
	           {
	        	   Intent i=new Intent(getActivity(),CourseAudio.class);
	  	    	   i.putExtra("lect_type", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlecttype());
	  	    	
	  	    	   i.putExtra("lect_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getlectureid());
	  	    	   i.putExtra("section_id",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecid());
	  	    	   i.putExtra("section_name",   listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getsecname());
	  	    	   i.putExtra("lecture_name", listDataChild.get(listDataHeader.get(groupPosition)).get(
                           childPosition).getName());
	  	    	   startActivity(i);
	                System.out.println("child data"+listDataChild.get(listDataHeader.get(groupPosition)).get(
	                                        childPosition).getlecttype());
	        	   
	           }
	                return false;
	            }
	        });
			 return  v;
}
		
		class CategoryDetails extends AsyncTask<String,String,String>{
			@Override
		    protected void onPreExecute() {
				  cDialog = new ProgressDialog(getActivity());
		          cDialog.setMessage("Please wait...");
		          cDialog.setIndeterminate(false);
		          cDialog.setCancelable(false);
		        //  cDialog.show();
			}
				
				@Override
				protected void onPostExecute(String file_url) {
			   
					 super.onPostExecute(file_url);
					 
				        listAdapter = new ExpandablelistAdapter(getActivity(), listDataHeader, listDataChild);
				       
				        expListView.setAdapter(listAdapter);

				
			} 

				@Override
				protected String doInBackground(String... args) {
					// TODO Auto-generated method stub
					
					 listDataHeader = new ArrayList<String>();
				        listDataChild = new HashMap<String, List<Child>>();
					 CategoryArrayAdapter.data.clear();
				      category.clear();
					
					vehiclelist.clear();
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		             
		             params1.add(new BasicNameValuePair("course_id", CourseDetails.course_id));
		           System.out.println("course imnbd value"+CourseDetails.course_id);
		         jArray = jsonParser.makeHttpRequest(Config.ServerUrl+Config.courseurl, "POST", params1);
				
				    Log.i("tagconvertstr", "["+jArray+"]");
				    
				    try
				    {
				    	if(jArray != null){
				    	
				    	
				    	 JSONArray c = jArray.getJSONArray("JsonOutput");
					    	Log.i("jarray values", "["+c+"]");
					
					    	for(int i=0; i<c.length(); i++)
					    	{
					    		
					    		
					    
					    	     JSONObject obj=c.getJSONObject(i);
					    	     ch_list= new ArrayList<Child>();
					    	    
					    	        String value = obj.getString("ParentCategory"); 
					    	        listDataHeader.add(value);
					    	       
					    	        System.out.println("value of listdataheader"+listDataHeader);
					    	        Log.d("Item name: ", value);
					    	        String id = obj.getString("ChildCategory"); 
					    	        Log.d("child name: ", id);
					    	      
					    	        JSONArray jsonarray = new JSONArray(id);
					    	        for(int j=0; j<jsonarray.length(); j++)
							    	{
					    	        JSONObject data =jsonarray.getJSONObject(j);
					    	        sectionid=data.getString("id_section");
					    	        lectureid=data.getString("id_lecture");
					    	        sectionname=data.getString("name_section");
					    	        lecturename=data.getString("name_lecture");
					    	        lecturetype=data.getString("type_lecture");
					    	        System.out.println("data value"+data);
					    	       
					    	        Child ch = new Child();
					                ch.setName(lecturename);
					                ch.setlectureid(lectureid);
					                ch.setsecid(sectionid);
					                ch.setlecttype(lecturetype);
					                ch.setsecname(sectionname);
					                ch_list.add(ch);
					    	       
							    	}
					    	       System.out.println("list values"+ch_list);
					    	       System.out.println("list size"+ch_list.size());
					    	        lists.add(ch_list);
					    	        listDataChild.put(listDataHeader.get(i), lists.get(i));   
					    	        
					    	}
					    
					    	
	    	
				    	}
				    	
				    	}catch (JSONException e) 
				    	{
				    		for(int j=0; j<100; j++)
					    	{
				    			  Child ch = new Child();
				    			  ch.setName("");
				    			  empty_ch_list= new ArrayList<Child>();
				    		empty_ch_list.add(ch);
				    		  listDataChild.put("",empty_ch_list );  
					    	}
				        e.printStackTrace();
				    }
				    cDialog.dismiss();
					return null;
				}
				
				
				
			}
		
			
}