package com.studyonthegoapp.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.studyonthegoapp.active.MemberActivity;
import com.studyonthegoapp.active.RequestToJoinActivity;
import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements OnClickListener {
	
	
	private TextView userNameET;
	private EditText firstNameET;
	private EditText lastNameET;
	private EditText skillsET;
	private Spinner yearSpinner;
	private EditText majorET;
	private Button saveProfileButton;
	private Button cancelProfileSaveButton;
	private Button editProfileButton;
	
	private TableRow saveCancelRow;
	private TableRow editRow;

	private String userName;
	private String firstName;
	private String lastName;
	private Course course;
	private String skills;
	private String major;
	private String spec;

	private Profile profile;
	
	private ExpandableListView coursesExpandableListView;
	private ExpandableListAdapter listAdapter;
	private List<String> listDataHeader;
	private HashMap<String, Course[]> listDataChild;
	
	private String[] years_list = {"Freshman","Sophomore","Junior","Senior","Master","PhD"};
	private String year;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container,
				false);
		userNameET = (TextView) view.findViewById(R.id.Username);
		firstNameET = (EditText) view.findViewById(R.id.FirstNameEditText);
		lastNameET = (EditText) view.findViewById(R.id.LastNameEditText);
		skillsET = (EditText) view.findViewById(R.id.SkillsEditText);
		majorET = (EditText) view.findViewById(R.id.MajorEditText);
		
		yearSpinner = (Spinner) view.findViewById(R.id.DegreeSpinner);
		saveProfileButton = (Button) view.findViewById(R.id.SaveButton);
		saveProfileButton.setOnClickListener(this);
		cancelProfileSaveButton = (Button)view.findViewById(R.id.CancelButton);
		cancelProfileSaveButton.setOnClickListener(this);
		editProfileButton = (Button) view.findViewById(R.id.EditProfile);
		editProfileButton.setOnClickListener(this);
		saveCancelRow = (TableRow) view.findViewById(R.id.tableRow8);
		
		saveCancelRow.setVisibility(saveCancelRow.GONE);
		
		editRow = (TableRow) view.findViewById(R.id.tableRow6);
		
		//saveProfileButton.setVisibility(visibility);
		coursesExpandableListView = (ExpandableListView) view.findViewById(id.coursesExpandableListView);
		
		firstNameET.setFocusable(false);
		lastNameET.setFocusable(false);
		skillsET.setFocusable(false);
		majorET.setFocusable(false);
		
		
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
		majorET.setText(profile.getMajor());
		skillsET.setText(profile.getSkills());
		
        // preparing list data for courses
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, Course[]>();
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this.getActivity(), listDataHeader, listDataChild);
        // setting list adapter
        coursesExpandableListView.setAdapter(listAdapter);
        
        listAdapter.notifyDataSetChanged();
        
        //preparing spinner data for years
        
        MySimpleArrayAdapter dAdapter = new MySimpleArrayAdapter(this.getActivity(), years_list);
	    yearSpinner.setAdapter(dAdapter);
	    yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				year = years_list[position];
				
				Log.d("onItemSelected", "year: " + year);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
	    	
	    });
	    yearSpinner.setSelection(dAdapter.getPosition(profile.getYear()));
	}
	
	public void setFocus()
	{
		firstNameET.setFocusable(true);
		lastNameET.setFocusable(true);
		skillsET.setFocusable(true);
		majorET.setFocusable(true);
		//firstNameET.
		saveCancelRow.setVisibility(saveCancelRow.VISIBLE);
		editRow.setVisibility(editRow.GONE);
	}
	
	public void removeFocus()
	{
		setValues();
		firstNameET.setFocusable(false);
		lastNameET.setFocusable(false);
		skillsET.setFocusable(false);
		majorET.setFocusable(false);
		saveCancelRow.setVisibility(saveCancelRow.GONE);
		editRow.setVisibility(editRow.VISIBLE);		
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

	    case R.id.EditProfile:
	        // do your code
	    	setFocus();
	        break;

	    case R.id.SaveButton:
	        // do your code
	        break;

	    case R.id.CancelButton:
	        // do your code
	    	removeFocus();
	        break;

	    default:
	        break;
	    }
	}


		
	
	private void prepareListData()
	{
		listDataHeader.clear();
		final String requestsHeader = "Courses( " + profile.getCourses().length + ")";
		listDataHeader.add(requestsHeader);
		
		listDataChild.clear();
		//ArrayList<Course> list = new ArrayList<Course>(Arrays.asList(profile.getCourses()));
		listDataChild.put(requestsHeader, profile.getCourses());
		
		//listDataChild.put(membersHeader, group.getMemberUsers());
	}
	
	private class ExpandableListAdapter extends BaseExpandableListAdapter {
		
		private Context _context;
	    private List<String> _listDataHeader; // header titles
	    // child data in format of header title, child title
	    private HashMap<String, Course[]> _listDataChild;
	 
	    public ExpandableListAdapter(Context context, List<String> listDataHeader,
	            HashMap<String, Course[]> listChildData) {
	        this._context = context;
	        this._listDataHeader = listDataHeader;
	        this._listDataChild = listChildData;
	    }

	    @Override
	    public Course getChild(int groupPosition, int childPosititon) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))[childPosititon];
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	 
	        final Course course = (Course) getChild(groupPosition, childPosition);
	        
	       if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_course_item,null);
			}

			Course courseToBeDisplayed = getChild(groupPosition,childPosition);
			String format = courseToBeDisplayed.getSubject() + " " + course.getNumber();

			TextView courseTV = (TextView) convertView.findViewById(id.coursesTextView);
			courseTV.setText(format);

			return convertView;	        
	    }

		@Override
	    public int getChildrenCount(int groupPosition) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .length;
	    }
	 
	    @Override
	    public Object getGroup(int groupPosition) {
	        return this._listDataHeader.get(groupPosition);
	    }
	 
	    @Override
	    public int getGroupCount() {
	        return this._listDataHeader.size();
	    }
	 
	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_courses, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.numberOfCoursesTextView);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

	private class MySimpleArrayAdapter extends ArrayAdapter<String>
	{
		Context context;
	    String[] values;

	    public MySimpleArrayAdapter(Context context, String[] values) {
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
                row = inflater.inflate(R.layout.spinner_year_item, parent, false);
            }

            String year = values[position];
            //String year = profile.getYear();
            //String format = course.getSubject() + " " + course.getNumber();
            
            TextView courseTV = (TextView) row.findViewById(id.yearSpinnerTextView);
            courseTV.setText(year);

            return row;
        }
	}
	
}