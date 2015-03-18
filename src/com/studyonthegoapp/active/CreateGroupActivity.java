package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.CreateStudyGroup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	
	private String groupName;
	private String course;
	private String description;
	private String building;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int membersLimit;
	
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
		groupName = groupNameET.getText().toString();
		course = courseET.getText().toString();
		description = descriptionET.getText().toString();
		building = buildingET.getText().toString();
		location = locationET.getText().toString();
		startDate = startDateET.getText().toString();
		endDate = endDateET.getText().toString();
		startTime = startTimeET.getText().toString();
		endTime = endTimeET.getText().toString();
		membersLimit = Integer.parseInt(membersLimitET.getText().toString());
		
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
						  location, startDate, endDate, startTime, endTime, Integer.toString(membersLimit));
		
	}
	
	public void receiveCreateStudyGroupResultFromMySQL(boolean groupNameExists, boolean insertError, int groupId)
	{
		// TODO NEED TO IMPLEMENT
		Log.d("receiveCreateStudyGroupResultFromMySQL", "groupNameExists: " + groupNameExists +
														"\ninsertError: " + insertError +
														"\ngroupId: " + groupId);
		
		if (groupNameExists)
			groupNameET.setError("Group name already exists! :o");
		else if (groupNameExists == false && insertError)
		{
			Toast toast = Toast.makeText(this, 
					"Error: Please try creating group again.", Toast.LENGTH_SHORT);  
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
		else if (groupNameExists == false && insertError == false)
		{
			// TODO create Profile class such that it contains: courseId, subject, courseNumber, and section
			StudyGroup group = new StudyGroup(groupId, groupName, username, 1, "subject", 4261, "section",
											  description, building, location, startDate, endDate,
											  startTime, endTime, membersLimit);
			showAlertDialog(group);
		}
	}
	
	private void showAlertDialog(final StudyGroup group)
	{
		
		// TODO Auto-generated method stub
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("Success!");
        builder1.setMessage("Successfully created group! :D");
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            	sendBackToParentFragment(group);
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
	public void sendBackToParentFragment(StudyGroup group) {
		Intent data = new Intent();
    	data.putExtra("studyGroup", group);
    	// Activity finished ok, return the data
    	setResult(RESULT_OK, data);
    	finish();
	}
}
