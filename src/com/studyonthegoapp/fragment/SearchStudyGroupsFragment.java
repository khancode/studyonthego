package com.studyonthegoapp.fragment;

import java.util.List;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.GetStudyGroups;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchStudyGroupsFragment extends Fragment {
	
	private ListView listView;
	
	private StudyGroup[] studyGroups;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_search_study_groups, container, false);
		
		listView = (ListView) view.findViewById(id.studyGroupsListView);
		studyGroups = null;
		
		GetStudyGroups asyncTask = new GetStudyGroups(this);
		asyncTask.execute();
		
		return view;
	}
	
	public void receiveGetStudyGroupsResultFromMySQL(StudyGroup[] studyGroups)
	{	
		this.studyGroups = studyGroups;
		
		for (int i = 0; i < this.studyGroups.length; i++)
		{
			StudyGroup group = this.studyGroups[i];
			
			Log.d("receiveGetStudyGroupsResultFromMySQL()", group.toString() + "\n");
		}
		
//		final ListView listview = (ListView) findViewById(R.id.listview);
//	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//	        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//	        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//	        "Android", "iPhone", "WindowsMobile" };
//
//	    final ArrayList<String> list = new ArrayList<String>();
//	    for (int i = 0; i < values.length; ++i) {
//	      list.add(values[i]);
//	    }
	    final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
	        android.R.layout.simple_list_item_1, this.studyGroups);
	    listView.setAdapter(adapter);
	}
	
	private class StableArrayAdapter extends ArrayAdapter<StudyGroup> {

//	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	    
	    StudyGroup[] groups;

	    public StableArrayAdapter(Context context, int textViewResourceId, StudyGroup[] objects)
	    {
		  super(context, textViewResourceId, objects);
		  
		  this.groups = objects;
		  
//		  for (int i = 0; i < objects.size(); ++i) {
//		    mIdMap.put(objects.get(i), i);
//		  }
	    }

	    @Override
	    public long getItemId(int position) {
//	      String item = getItem(position);
	      return position;//mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }

}
