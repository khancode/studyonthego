package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.CreateStudyGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateGroupActivity extends ActionBarActivity implements OnClickListener{

	private EditText groupNameET;
	private EditText courseET;
	private EditText descriptionET;
	private EditText buildingET;
	private EditText locationET;
	private EditText startDateET;
	private EditText endDateET;
	private EditText startTimeET;
	private EditText endTimeET;
	private EditText membersLimitET;
	private Button createGroupButton;
	
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		
		groupNameET = (EditText) findViewById(id.groupNameEditText);
		courseET = (EditText) findViewById(id.courseEditText);
		descriptionET = (EditText) findViewById(id.descriptionEditText);
		buildingET = (EditText) findViewById(id.buildingEditText);
		locationET = (EditText) findViewById(id.locationEditText);
		startDateET = (EditText) findViewById(id.startDateEditText);
		endDateET = (EditText) findViewById(id.endDateEditText);
		startTimeET = (EditText) findViewById(id.startTimeEditText);
		endTimeET = (EditText) findViewById(id.endTimeEditText);
		membersLimitET = (EditText) findViewById(id.membersLimitEditText);
		createGroupButton = (Button) findViewById(id.createGroupbutton);
		createGroupButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String groupName = groupNameET.getText().toString();
		String course = courseET.getText().toString();
		String description = descriptionET.getText().toString();
		String building = buildingET.getText().toString();
		String location = locationET.getText().toString();
		String startDate = startDateET.getText().toString();
		String endDate = endDateET.getText().toString();
		String startTime = startTimeET.getText().toString();
		String endTime = endTimeET.getText().toString();
		String membersLimit = membersLimitET.getText().toString();
		
		Log.d("OnClick", "username: " + username +
						 "\ngroupName: " + groupName +
						 "\ncourse: " + course +
						 "\ndescription: " + description +
						 "\nbuilding: " + building +
						 "\nlocation: " + location +
						 "\nstartDate: " + startDate +
						 "\nendDate: " + endDate +
						 "\nstartTime: " + startTime +
						 "\nendTime: " + endTime +
						 "\nmembersLimit: " + membersLimit);
		
		CreateStudyGroup asyncTask = new CreateStudyGroup(this);
		asyncTask.execute(username, groupName, course, description, building,
						  location, startDate, endDate, startTime, endTime, membersLimit);
		
	}
	
	public void receiveCreateStudyGroupResultFromMySQL(boolean groupNameExists, boolean insertError)
	{
		// TODO NEED TO IMPLEMENT
		Log.d("receiveCreateStudyGroupResultFromMySQL", "groupNameExists: " + groupNameExists +
														"\ninsertError: " + insertError);
	}
}
