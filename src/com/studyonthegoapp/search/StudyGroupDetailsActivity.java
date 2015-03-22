package com.studyonthegoapp.search;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
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
	private TextView locationTV;
	private TextView startDateTV;
	private TextView endDateTV;
	private TextView startTimeTV;
	private TextView endTimeTV;
	private TextView membersCountTV;
	private TextView membersLimitTV;
	private Button sendRequestButton;
	
//	private Profile profile;
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
		locationTV = (TextView) findViewById(id.locationTextView);
		startDateTV = (TextView) findViewById(id.startDateTextView);
		endDateTV = (TextView) findViewById(id.endDateTextView);
		startTimeTV = (TextView) findViewById(id.startTimeTextView);
		endTimeTV = (TextView) findViewById(id.endTimeTextView);
		membersCountTV = (TextView) findViewById(id.membersCountTextView);
		membersLimitTV = (TextView) findViewById(id.membersLimitTextView);
		sendRequestButton = (Button) findViewById(id.sendRequestButton);
		sendRequestButton.setOnClickListener(this);
		
		Intent intent = getIntent();
//		profile = (Profile) intent.getExtras().getParcelable("profile");
		user = (User) intent.getExtras().getParcelable("user");
		group = (StudyGroup) intent.getExtras().getParcelable("studyGroup");
		
		Log.d("OnCreate()", "user: " + user + "\n\tgroup: " + group);
		
		groupNameTV.setText(group.getGroupName());
		courseTV.setText(group.getSubject() + " " + group.getCourseNumber() + " " + group.getSection());
		adminTV.setText(group.getAdmin());
		descriptionTV.setText(group.getDescription());
		buildingTV.setText(group.getBuilding());
		locationTV.setText(group.getLocation());
		startDateTV.setText(group.getStartDate());
		endDateTV.setText(group.getEndDate());
		startTimeTV.setText(group.getStartTime());
		endTimeTV.setText(group.getEndTime());
		membersCountTV.setText(Integer.toString(group.getMembersCount()));
		membersLimitTV.setText(Integer.toString(group.getMembersLimit()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.study_group_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		Log.d("onClick()", "NEED TO IMPLEMENT");
	}
}
