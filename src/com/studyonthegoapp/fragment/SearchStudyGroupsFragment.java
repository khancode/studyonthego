package com.studyonthegoapp.fragment;

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
import android.widget.TextView;

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
		
		// DEBUG PURPOSES: printing result
		for (int i = 0; i < this.studyGroups.length; i++)
		{
			StudyGroup group = this.studyGroups[i];
			
			Log.d("receiveGetStudyGroupsResultFromMySQL()", group.toString() + "\n");
		}

		// Add to list view
	    final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), this.studyGroups);
	    listView.setAdapter(adapter);
	}
	
	private class MySimpleArrayAdapter extends ArrayAdapter<StudyGroup> {

	    Context context;
	    StudyGroup[] values;

	    public MySimpleArrayAdapter(Context context, StudyGroup[] values) {
	        super(context, R.layout.row_study_group, values);
	        this.context = context;
	        this.values = values;
	    }
	    
	    class ViewHolder {
	    	TextView groupNameTV;
	    	TextView courseTV;
	    	TextView buildingTV;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	View rowView = convertView;
	    	// reuse views
	    	if (rowView == null) {
	    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		rowView = inflater.inflate(R.layout.row_study_group, parent, false);
	    		// configure view holder
	    		ViewHolder viewHolder = new ViewHolder();
	    		viewHolder.groupNameTV = (TextView) rowView.findViewById(R.id.groupNameTextView);
	    		viewHolder.courseTV = (TextView) rowView.findViewById(R.id.courseTextView);
	    		viewHolder.buildingTV = (TextView) rowView.findViewById(R.id.buildingTextView);
	    		rowView.setTag(viewHolder);
	    	}
	    	
	    	// fill data
	    	ViewHolder holder = (ViewHolder) rowView.getTag();
	    	StudyGroup group = values[position];
	    	holder.groupNameTV.setText(group.getGroupName());
	    	holder.courseTV.setText(Integer.toString(group.getCourseId()));
	    	holder.buildingTV.setText(group.getBuilding());
	    	
//		  TextView groupNameTV = (TextView) rowView.findViewById(R.id.groupNameTextView);
//		  groupNameTV.setText(values[position].getGroupName());
//		  TextView courseTV = (TextView) rowView.findViewById(R.id.courseTextView);
//		  courseTV.setText(Integer.toString(values[position].getCourseId()));
//		  TextView textView = (TextView) rowView.findViewById(R.id.buildingTextView);
//		  textView.setText(values[position].getBuilding());
		  // Change the icon for Windows and iPhone
//		  String s = values[position];
//		  if (s.startsWith("Windows7") || s.startsWith("iPhone")
//		  || s.startsWith("Solaris")) {
//		    imageView.setImageResource(R.drawable.no);
//		  } else {
//		    imageView.setImageResource(R.drawable.ok);
//		  }
		
		  return rowView;
	    }
	}

}
