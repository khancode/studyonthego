package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AdminGroupInnerFragment extends Fragment {
	
	private TextView groupNameTV;
	private EditText courseET;
	private EditText descriptionET;
	private EditText buildingET;
	private EditText locationET;
	private EditText startDateET;
	private EditText endDateET;
	private EditText startTimeET;
	private EditText endTimeET;
	private EditText membersCountET;
	private EditText membersLimitET;
	
	private StudyGroup group;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.inner_fragment_admin_group, container, false);
		
		Bundle bundle = getArguments();
		group = bundle.getParcelable("studyGroup");
		
		groupNameTV = (TextView) view.findViewById(id.groupNameTextView);
		courseET = (EditText) view.findViewById(id.courseEditText);
		descriptionET = (EditText) view.findViewById(id.descriptionEditText);
		buildingET = (EditText) view.findViewById(id.buildingEditText);
		locationET = (EditText) view.findViewById(id.locationEditText);
		startDateET = (EditText) view.findViewById(id.startDateEditText);
		endDateET = (EditText) view.findViewById(id.endDateEditText);
		startTimeET = (EditText) view.findViewById(id.startTimeEditText);
		endTimeET = (EditText) view.findViewById(id.endTimeEditText);
		membersCountET = (EditText) view.findViewById(id.membersCountEditText);
		membersLimitET = (EditText) view.findViewById(id.membersLimitEditText);
		
		groupNameTV.setText(group.getGroupName());
		courseET.setText(group.getSubject() + " " + group.getCourseNumber());
		descriptionET.setText(group.getDescription());
		buildingET.setText(group.getBuilding());
		locationET.setText(group.getLocation());
		startDateET.setText(group.getStartDate());
		endDateET.setText(group.getEndDate());
		startTimeET.setText(group.getStartTime());
		endTimeET.setText(group.getEndTime());
		membersCountET.setText(Integer.toString(group.getMembersCount()));
		membersLimitET.setText(Integer.toString(group.getMembersLimit()));
		
		return view;
	}

}