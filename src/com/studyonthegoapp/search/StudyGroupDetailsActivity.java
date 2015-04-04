package com.studyonthegoapp.search;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;
import com.studyonthegoapp.restfulapi.GetStudyGroupInfo;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StudyGroupDetailsActivity extends ActionBarActivity implements OnClickListener {

	private TextView groupNameTV;
	private TextView courseTV;
	private TextView adminTV;
	private TextView descriptionTV;
	private TextView buildingTV;
	private TextView startDateTV;
	private TextView endDateTV;
	private TextView startTimeTV;
	private TextView endTimeTV;
	private TextView noOfAttendeesTV;
	private Button sendRequestButton;
	
	private ProgressDialog mDialog;
	
	private User user;
	private StudyGroup group;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_group_details);
		
		groupNameTV = (TextView) findViewById(id.groupNameTextView);
		courseTV = (TextView) findViewById(id.courseTextView);
		adminTV = (TextView) findViewById(id.adminTextView);
		descriptionTV = (TextView) findViewById(id.descriptionTextView);
		buildingTV = (TextView) findViewById(id.buildingTextView);
		startDateTV = (TextView) findViewById(id.startDateTextView);
		endDateTV = (TextView) findViewById(id.endDateTextView);
		startTimeTV = (TextView) findViewById(id.startTimeTextView);
		endTimeTV = (TextView) findViewById(id.endTimeTextView);
		noOfAttendeesTV = (TextView) findViewById(id.noOfAttendeesTextView);
		sendRequestButton = (Button) findViewById(id.sendRequestButton);
		sendRequestButton.setOnClickListener(this);
		
		Intent intent = getIntent();
//		profile = (Profile) intent.getExtras().getParcelable("profile");
		user = (User) intent.getExtras().getParcelable("user");
		group = (StudyGroup) intent.getExtras().getParcelable("studyGroup");
		
		Log.d("OnCreate()", "user: " + user + "\n\tgroup: " + group);
		
		mDialog = new ProgressDialog(this);
		
		getStudyGroupInfo();		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		Log.d("onClick()", "NEED TO IMPLEMENT");
	}
	
	private void getStudyGroupInfo()
	{
		mDialog.setMessage("Loading...");
		mDialog.setCancelable(false);
		mDialog.show();
		
		GetStudyGroupInfo asyncTask = new GetStudyGroupInfo(this);
		asyncTask.execute(Integer.toString(group.getGroupId()));
	}
	
	public void receiveStudyGroupInfoResult(boolean groupExists, StudyGroup studyGroup)
	{
		if (groupExists)
		{
			// Updated group details
			this.group = studyGroup;
			setTextViews();
		}
		else
		{
			// TODO NEED TO IMPLEMENT Group doesn't exist anymore
			Log.d("receiveStudyGroupInfoResult()", "NEED TO IMPLEMENT: Group doesn't exist anymore");
		}
		
		mDialog.cancel();
	}
	
	private void setTextViews()
	{
		groupNameTV.setText(group.getGroupName());
		courseTV.setText(group.getSubject() + " " + group.getCourseNumber() + " " + group.getSection());
		adminTV.setText(group.getAdmin());
		descriptionTV.setText(group.getDescription());
		buildingTV.setText(group.getBuilding());
		startDateTV.setText(group.getStartDate());
		endDateTV.setText(group.getEndDate());
		startTimeTV.setText(group.getStartTime());
		endTimeTV.setText(group.getEndTime());
		noOfAttendeesTV.setText(Integer.toString(group.getMembersCount()) + "/" + Integer.toString(group.getMembersLimit()));
	}
}
