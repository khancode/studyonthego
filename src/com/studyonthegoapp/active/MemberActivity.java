package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.layout;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

public class MemberActivity extends ActionBarActivity {

	private TextView usernameTV;
	private TextView firstNameTV;
	private TextView lastNameTV;
	private TextView coursesTV;
	private TextView majorTV;
	private TextView yearTV;
	private TextView skillsTV;
	
	private StudyGroup group;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member);
		Intent intent = getIntent();
		group = intent.getParcelableExtra("studyGroup");
		user = intent.getParcelableExtra("user");
		
		final String TAG = "MemberActivity";
		Log.d(TAG, "group: " + group);
		Log.d(TAG, "user: " + user);
		
		usernameTV = (TextView) findViewById(R.id.usernameTextView);
		firstNameTV = (TextView) findViewById(R.id.firstNameTextView);
		lastNameTV = (TextView) findViewById(R.id.lastNameTextView);
		coursesTV = (TextView) findViewById(R.id.coursesTextView);
		majorTV = (TextView) findViewById(R.id.majorTextView);
		yearTV = (TextView) findViewById(R.id.yearSpinnerTextView);
		skillsTV = (TextView) findViewById(R.id.skillsTextView);
		
		Profile profile = user.getProfile();
		
		usernameTV.setText(user.getUsername());
		firstNameTV.setText(profile.getFirstName());
		lastNameTV.setText(profile.getLastName());
		String coursesStr = "";
		Course[] courses = profile.getCourses();
		for (int i = 0; i < courses.length; i++)
		{
			coursesStr += courses[i].getSubject() + " " + courses[i].getNumber();
			
			if (i + 1 != courses.length)
				coursesStr += ", ";
		}
		coursesTV.setText(coursesStr);
//		coursesTV.setText(profile.getCourses().toString());
		majorTV.setText(profile.getMajor());
		yearTV.setText(profile.getYear());
		skillsTV.setText(profile.getSkills());
		
	}

}
