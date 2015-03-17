package com.studyonthegoapp.fragment;

import com.studyonthegoapp.activity.AppCoreActivity;
import com.studyonthegoapp.activity.StudyGroupDetailsActivity;
import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.GetStudyGroups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchStudyGroupsFragment extends Fragment implements OnClickListener {
	
	private EditText courseET;
	private EditText buildingET;
	private Button searchButton;
	
	private ListView listView;
	private MySimpleArrayAdapter adapter;
	private StudyGroup[] studyGroups;
	
	private String username;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_search_study_groups, container, false);
		
		courseET = (EditText) view.findViewById(id.courseEditText);
		buildingET = (EditText) view.findViewById(id.buildingEditText);
		searchButton = (Button) view.findViewById(id.searchButton);
		searchButton.setOnClickListener(this);
		
		listView = (ListView) view.findViewById(id.studyGroupsListView);
		adapter = null;	
		studyGroups = null;
		
		username = ((AppCoreActivity) view.getContext()).getUsername();
		
		GetStudyGroups asyncTask = new GetStudyGroups(this);
		asyncTask.execute(null, null);
		
		return view;
	}
	
	@Override
	public void onClick(View arg0) {
		String course = courseET.getText().toString();
		String building = buildingET.getText().toString();
		
		if (course.length() == 0)
			course = null;
		
		if (building.length() == 0)
			building = null;
		
		GetStudyGroups asyncTask = new GetStudyGroups(this);
		asyncTask.execute(course, building);
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
	    adapter = new MySimpleArrayAdapter(getActivity(), this.studyGroups);
	    listView.setAdapter(adapter);
	    
	    // Set OnItemClickListener
	    final Context context = getActivity();	    
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				StudyGroup group = (StudyGroup) adapter.getItemAtPosition(position);
				
				Intent intent = new Intent(context, StudyGroupDetailsActivity.class);
				intent.putExtra("username", username);
				intent.putExtra("studyGroup", group);
				startActivity(intent);
			}
	    	
	    });
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
