package com.studyonthegoapp.active;

import java.util.ArrayList;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.Buildings;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;
import com.studyonthegoapp.restfulapi.CreateStudyGroup;
import com.studyonthegoapp.restfulapi.GetBuildings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGroupActivity extends ActionBarActivity implements OnClickListener {

	private EditText groupNameET;
	private Spinner courseSpinner;
	private EditText descriptionET;
	private EditText buildingET;
	private AutoCompleteTextView buildingAT;
	private EditText locationET;
	private EditText startDateET;
	private EditText endDateET;
	private EditText startTimeET;
	private EditText endTimeET;
	private EditText membersLimitET;
	private Button createGroupButton;
	
	private ProgressDialog mDialog;
	
	private String groupName;
	private String admin;
	private Course course;
	private String description;
	private String building;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int membersLimit;
	
//	private Profile profile;
	private User user;
	
	private ArrayList<String> buildings= new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		
		groupNameET = (EditText) findViewById(id.groupNameEditText);
		courseSpinner = (Spinner) findViewById(id.courseSpinner);
		descriptionET = (EditText) findViewById(id.descriptionEditText);
		//buildingET = (EditText) findViewById(id.buildingEditText);
		buildingAT = (AutoCompleteTextView) findViewById(R.id.buildingAutoText);
		locationET = (EditText) findViewById(id.locationEditText);
		startDateET = (EditText) findViewById(id.startDateEditText);
		endDateET = (EditText) findViewById(id.endDateEditText);
		startTimeET = (EditText) findViewById(id.startTimeEditText);
		endTimeET = (EditText) findViewById(id.endTimeEditText);
		membersLimitET = (EditText) findViewById(id.membersLimitEditText);
		createGroupButton = (Button) findViewById(id.createGroupbutton);
		createGroupButton.setOnClickListener(this);
		
		mDialog = new ProgressDialog(this);
		
		Intent intent = getIntent();
//		profile = (Profile) intent.getExtras().getParcelable("profile");
//		admin = profile.getUsername();
		
		user = (User) intent.getExtras().getParcelable("user");
		admin = user.getUsername();
		
		Log.d("CreateGroupActivity", "user : " + user);
		
	    MySimpleArrayAdapter dAdapter = new MySimpleArrayAdapter(this, user.getProfile().getCourses());
	    courseSpinner.setAdapter(dAdapter);
	    courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				course = user.getProfile().getCourses()[position];
				
				Log.d("onItemSelected", "course: " + course);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
	    	
	    });
	    
	    
	    //getBuildings();
	    ArrayAdapter<String> buildingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Buildings.getBuildings());
        
	    buildingAT.setThreshold(3);
        buildingAT.setAdapter(buildingAdapter);
	    
	}
	
	/** This api call doesn't work */
	/*private void getBuildings()
	{
		GetBuildings asyncTask = new GetBuildings(this);
		asyncTask.execute();
	}
	
	public void receiveGetBuildingsResult(ArrayList<String> buildings_result, int responseCode)
	{
		buildings.addAll(buildings_result);
		System.out.println(buildings);
		//showAlertDialog(responseCode);
		
	}*/
	
	private void showAlertDialog(final int result)
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("Success!");
        builder1.setMessage("ResponseCode: "+ result);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            	//sendDataBackToParentFragment(group);
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}


	
	@Override
	public void onClick(View arg0) {
		groupName = groupNameET.getText().toString();
		description = descriptionET.getText().toString();
		building = buildingET.getText().toString();
		location = locationET.getText().toString();
		startDate = startDateET.getText().toString();
		endDate = endDateET.getText().toString();
		startTime = startTimeET.getText().toString();
		endTime = endTimeET.getText().toString();
		membersLimit = Integer.parseInt(membersLimitET.getText().toString());
		
		Log.d("OnClick", "groupName: " + groupName +
						 "\nadmin: " + admin +
						 "\ncourse: " + course.getId() +
						 "\ndescription: " + description +
						 "\nbuilding: " + building +
						 "\nlocation: " + location +
						 "\nstartDate: " + startDate +
						 "\nendDate: " + endDate +
						 "\nstartTime: " + startTime +
						 "\nendTime: " + endTime +
						 "\nmembersLimit: " + membersLimit);
		
		createStudyGroup();
	}
	
	private void createStudyGroup()
	{
		mDialog.setMessage("Creating study group...");
		mDialog.setCancelable(false);
		mDialog.show();
		
		CreateStudyGroup asyncTask = new CreateStudyGroup(this);
		asyncTask.execute(admin, groupName, Integer.toString(course.getId()), description, building,
						  location, startDate, endDate, startTime, endTime, Integer.toString(membersLimit));
	}
	
	public void receiveCreateStudyGroupResult(boolean groupNameExists, boolean insertStudyGroupError,
											  boolean insertMemberError, int groupId)
	{
		Log.d("receiveCreateStudyGroupResult", "groupNameExists: " + groupNameExists +
														"\ninsertStudyGroupError: " + insertStudyGroupError +
														"\ninsertMemberError: " + insertMemberError +
														"\ngroupId: " + groupId);
		
		if (groupNameExists)
			groupNameET.setError("Group name already exists! :o");
		else if (groupNameExists == false && (insertStudyGroupError || insertMemberError))
		{
			Toast toast = Toast.makeText(this, 
					"Error: Please try creating group again.", Toast.LENGTH_SHORT);  
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
		else if (groupNameExists == false && insertStudyGroupError == false && insertMemberError == false)
		{
			StudyGroup group = new StudyGroup(groupId, groupName, user, course.getId(), 
											  course.getSubject(), course.getNumber(), course.getSection(),
											  description, building, location, startDate, endDate,
											  startTime, endTime, membersLimit);
			showAlertDialog(group);
		}
		
		mDialog.cancel();
	}
	
	private void showAlertDialog(final StudyGroup group)
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("Success!");
        builder1.setMessage("Successfully created group! :D");
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	
            	sendDataBackToParentFragment(group);
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
	private void sendDataBackToParentFragment(StudyGroup group) {
		Intent data = new Intent();
    	data.putExtra("studyGroup", group);
    	// Activity finished ok, return the data
    	setResult(RESULT_OK, data);
    	finish();
	}
	
	/**
	 * Used for creating courseSpinner view
	 * @author khancode
	 */
	
	private class MySimpleArrayAdapter extends ArrayAdapter<Course>
	{
		Context context;
	    Course[] values;

	    public MySimpleArrayAdapter(Context context, Course[] values) {
	        super(context, android.R.layout.simple_spinner_item, values);
	        this.context = context;
	        this.values = values;
	    }
	    
	    @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {   
	    	return initView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {   
        	// This view starts when we click the spinner.
        	return initView(position, convertView, parent);
        }
        
        private View initView(int position, View convertView, ViewGroup parent)
        {
        	View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.spinner_course_item, parent, false);
            }

            Course course = values[position];
            String format = course.getSubject() + " " + course.getNumber();
            
            TextView courseTV = (TextView) row.findViewById(id.courseTextView);
            courseTV.setText(format);

            return row;
        }
	}
}
