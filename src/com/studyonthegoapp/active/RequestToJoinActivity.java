package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;
import com.studyonthegoapp.restfulapi.RespondToRequest;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RequestToJoinActivity extends ActionBarActivity implements OnClickListener {

	private TextView usernameTV;
	private TextView firstNameTV;
	private TextView lastNameTV;
	private TextView coursesTV;
	private TextView majorTV;
	private TextView yearTV;
	private TextView skillsTV;
	private Button respondToRequestButton;
	
	private ProgressDialog mDialog;
	
	private StudyGroup group;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_to_join);
		
		Intent intent = getIntent();
		group = intent.getParcelableExtra("studyGroup");
		user = intent.getParcelableExtra("user");
		
		final String TAG = "RequestToJoinActivity";
		Log.d(TAG, "group: " + group);
		Log.d(TAG, "user: " + user);
		
		usernameTV = (TextView) findViewById(R.id.usernameTextView);
		firstNameTV = (TextView) findViewById(R.id.firstNameTextView);
		lastNameTV = (TextView) findViewById(R.id.lastNameTextView);
		coursesTV = (TextView) findViewById(R.id.coursesTextView);
		majorTV = (TextView) findViewById(R.id.majorTextView);
		yearTV = (TextView) findViewById(R.id.yearSpinnerTextView);
		skillsTV = (TextView) findViewById(R.id.skillsTextView);
		respondToRequestButton = (Button) findViewById(R.id.respondToRequestButton);
		
		mDialog = new ProgressDialog(this);
		
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
		
		majorTV.setText(profile.getMajor());
		yearTV.setText(profile.getYear());
		skillsTV.setText(profile.getSkills());
		respondToRequestButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.d("OnClick", "NEED TO IMPLEMENT");
		
		showAlertDialogForRespondToRequestButton();
	}
	
	private void showAlertDialogForRespondToRequestButton()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Respond to Request");
        builder.setMessage("Accept " + user.getUsername() + "'s request?");
        builder.setCancelable(true);
        builder.setPositiveButton("Accept",
                new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	
		            	sendRequestToJoinGroup("yes");
		                dialog.cancel();
		            }
        });
        builder.setNegativeButton("Decline",
        		new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						sendRequestToJoinGroup("no");
					}
		});

        AlertDialog alert = builder.create();
        alert.show();
	}
	
	private void sendRequestToJoinGroup(String acceptRequest)
	{
		if (acceptRequest.equals("yes"))
			mDialog.setMessage("Accepting Request...");
		else
			mDialog.setMessage("Declining Request...");
		mDialog.setCancelable(false);
		mDialog.show();
		
		RespondToRequest asyncTask = new RespondToRequest(this);
		asyncTask.execute(Integer.toString(group.getGroupId()), user.getUsername(), acceptRequest);
	}
	
	public void receiveRespondToRequestResult(String acceptRequest, boolean deleteRequestToJoinError,
											  boolean insertMemberError, boolean updateStudyGroupError)
	{
		final String TAG = "receiveRespondToRequestResult";
		
		if (acceptRequest.equals("yes"))
		{
			if (!deleteRequestToJoinError && !insertMemberError && !updateStudyGroupError)
			{
				Log.d(TAG, "Success: accepted request");
				
				showAlertDialogForAPIResult(false, acceptRequest);
			}
			else
			{
				Log.d(TAG, "Error: couldn't accept request" +
						   "\ndeleteRequestToJoinError: " + deleteRequestToJoinError + 
						   "\ninsertMemberError: " + insertMemberError +
						   "\nupdateStudyGroupError: " + updateStudyGroupError);
				
				showAlertDialogForAPIResult(true, acceptRequest);
			}
			
		}
		else if (acceptRequest.equals("no"))
		{
			if (!deleteRequestToJoinError)
			{
				Log.d(TAG, "Success: declined request");
				
				showAlertDialogForAPIResult(false, acceptRequest);
			}
			else
			{
				Log.d(TAG, "Error: couldn't delete request from MySQL table" +
						   "\ndeleteRequestToJoinError: " + deleteRequestToJoinError);
			
				showAlertDialogForAPIResult(true, acceptRequest);
			}
		}
		else
			Log.d(TAG, "ERROR: this should not happen! acceptRequest has to be either 'yes' or 'no'");
		
		
		mDialog.cancel();
	}
	
	private void showAlertDialogForAPIResult(final boolean error, final String acceptRequest)
	{
		String title;
		String message;
		if (error)
		{
			title = "Error";
			message = "Couldn't respond to request. Please try again.";
		}
		else
		{
			title = "Success";
			if (acceptRequest.equals("yes"))
				message = "Accepted " + user.getUsername() + "'s request.";
			else
				message = "Declined " + user.getUsername() + "'s request.";
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	
		            	if (error == false)
		            	{
		            		Intent data = new Intent();
		                	data.putExtra("user", user);
		                	data.putExtra("acceptRequest", acceptRequest);
		                	// Activity finished ok, return the data
		                	setResult(RESULT_OK, data);
		                	finish();
		            	}
		            	
		                dialog.cancel();
		            }
        });

        AlertDialog alert = builder.create();
        alert.show();
	}
}
