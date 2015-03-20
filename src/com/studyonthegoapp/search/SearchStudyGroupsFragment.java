package com.studyonthegoapp.search;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.GetCurrentStudyGroups;

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
	
	private Profile profile;
	
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
		
		profile = null;
		
		return view;
	}
	
	/** Immediately called after being instantiated */
	public void setProfileFromAppCoreActivity(Profile profile)
	{
		this.profile = profile;
		
		getCurrentStudyGroupsWithUsersCourses();
	}
	
	private void getCurrentStudyGroupsWithUsersCourses()
	{
		Course[] courses = profile.getCourses();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < courses.length; i++) {
			Course c = courses[i];
			sb.append(c.getSubject()+c.getNumber());
			if (i != courses.length - 1)
				sb.append("_");
		}
		
		Log.d("getCurrentStudyGroups", sb.toString());
		String coursesFormatted = sb.toString();
		
		GetCurrentStudyGroups asyncTask = new GetCurrentStudyGroups(this);
		asyncTask.execute(coursesFormatted, null, profile.getUsername());
	}
	
	@Override
	public void onClick(View arg0) {
		
		getCurrentStudyGroupsWithSelectedCoursesAndBuilding();
	}
	
	private void getCurrentStudyGroupsWithSelectedCoursesAndBuilding()
	{
		// courseET should have user inputed course(s)separated by whitespace.
		String courses = courseET.getText().toString();
		String building = buildingET.getText().toString();
		
		if (courses.length() == 0)
			courses = null;
		else
		{
			String[] cArr = courses.split(" "); // extract multiple courses if inputed.
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < cArr.length; i++)
			{
				sb.append(cArr[i]);
				if (i != cArr.length -1)
					sb.append("_");
			}
			
			courses = sb.toString(); // courses is now formatted to be used as a parameter for RESTful API
		}
		
		if (building.length() == 0)
			building = null;
		
		GetCurrentStudyGroups asyncTask = new GetCurrentStudyGroups(this);
		asyncTask.execute(courses, building, null); // set user(3rd parameter) null because we're not finding any user associated group
	}
	
	public void receiveGetCurrentStudyGroupsResult(StudyGroup[] currentGroups)
	{	
		final String TAG = "receiveGetCurrentStudyGroupsResultFromMySQL";
		
		this.studyGroups = currentGroups;
		
		if (this.studyGroups == null)
		{
			Log.e(TAG, "studyGroups is null. This should not happen.");
			return;
		}
		
		// DEBUG PURPOSES: printing allGroups result
		for (int i = 0; i < this.studyGroups.length; i++)
		{
			StudyGroup group = this.studyGroups[i];
			
			Log.d(TAG, group.toString() + "\n");
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
				intent.putExtra("profile", profile);
				intent.putExtra("studyGroup", group);
				startActivity(intent);
			}
	    	
	    });
	    
	}
	
	// TODO What to do with this function? Will come back to it later.
//	public void addUserStudyGroupFromAppCoreActivity(StudyGroup group)
//	{
//		adapter.add(group);
//		adapter.notifyDataSetChanged();
//		
//		final String TAG = "addUserStudyGroupFromAppCoreActivity";
//		Log.d(TAG, "studyGroups.length: " + studyGroups.length);
//		Log.d(TAG, "adapter.values.length: " + adapter.values.length);
//	}
	
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
	    	holder.courseTV.setText(group.getSubject() + " " + group.getCourseNumber());
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
