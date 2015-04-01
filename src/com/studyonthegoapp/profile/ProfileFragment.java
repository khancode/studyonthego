package com.studyonthegoapp.profile;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.User;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements OnClickListener {
	
	
	private TextView userNameET;
	private Spinner courseSpinner;
	private EditText firstNameET;
	private EditText lastNameET;
	private EditText skillsET;
	private Spinner yearSpinner;
	private EditText majorET;
	private EditText specET;
	private Button saveProfileButton;
	private Button cancelProfileSaveButton;

	private String userName;
	private String firstName;
	private String lastName;
	private Course course;
	private String skills;
	private String major;
	private String spec;

	private Profile profile;
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container,
				false);
		userNameET = (TextView) view.findViewById(R.id.Username);
		firstNameET = (EditText) view.findViewById(R.id.FirstNameEditText);
		lastNameET = (EditText) view.findViewById(R.id.LastNameEditText);
		skillsET = (EditText) view.findViewById(R.id.SkillsEditText);
		majorET = (EditText) view.findViewById(R.id.MajorEditText);
		specET = (EditText) view.findViewById(R.id.SpecializationEditText);
		courseSpinner = (Spinner) view.findViewById(R.id.courseSpinner);
		yearSpinner = (Spinner) view.findViewById(R.id.DegreeSpinner);
		saveProfileButton = (Button) view.findViewById(R.id.SaveButton);
		saveProfileButton.setOnClickListener(this);
		setValues();

		return view;
	}

	public void setProfileFromAppCoreActivity(Profile profile) {
		this.profile = profile;
	}

	private void setValues() {

		// fill values in the form
		
		userNameET.setText(profile.getUsername());
		firstNameET.setText(profile.getFirstName());
		lastNameET.setText(profile.getLastName());
		
		
		MySimpleArrayAdapter dAdapter = new MySimpleArrayAdapter(getActivity(),
				profile.getCourses());
		courseSpinner.setAdapter(dAdapter);
		courseSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						course = profile.getCourses()[position];

						Log.d("onItemSelected", "course: " + course);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private class MySimpleArrayAdapter extends ArrayAdapter<Course> {
		Context context;
		Course[] values;

		public MySimpleArrayAdapter(Context context, Course[] values) {
			super(context, android.R.layout.simple_spinner_item, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return initView(position, convertView, parent);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// This view starts when we click the spinner.
			return initView(position, convertView, parent);
		}

		private View initView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.spinner_course_item, parent,
						false);
			}

			Course course = values[position];
			String format = course.getSubject() + " " + course.getNumber();

			TextView courseTV = (TextView) row.findViewById(id.courseTextView);
			courseTV.setText(format);

			return row;
		}
	}
	
}