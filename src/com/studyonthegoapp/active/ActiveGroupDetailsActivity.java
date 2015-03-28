package com.studyonthegoapp.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.codebase.R.layout;
import com.studyonthegoapp.codebase.R.menu;
import com.studyonthegoapp.oop.Members;
import com.studyonthegoapp.oop.RequestsToJoin;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ActiveGroupDetailsActivity extends ActionBarActivity {

	private TextView groupNameTV;
	private TextView courseTV;
	private TextView descriptionTV;
	private TextView buildingTV;
	private TextView locationTV;
	private EditText startDateET;
	private EditText endDateET;
	private EditText startTimeET;
	private EditText endTimeET;
//	private EditText membersCountET;
//	private EditText membersLimitET;
	
	private StudyGroup group;
	
	private ExpandableListView requestsExpandableListView;
	private ExpandableListAdapter listAdapter;
	private List<String> listDataHeader;
	private HashMap<String, List<User>> listDataChild;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_group_details);
		
		Intent intent = getIntent();
		group = intent.getParcelableExtra("studyGroup");
		
		final String TAG = "AdminGroupInnerFragment";
		
		RequestsToJoin requests = group.getRequestsToJoin();
		if (requests.length() == 0)
			Log.d(TAG, "No requests found");
		else
			Log.d(TAG, "requests found: " + requests);
		
		Members members = group.getMembers();
		if (members.length() == 1)
			Log.d(TAG, "The user is the only member of this group");
		else
			Log.d(TAG, "members: " + group.getMembers());
				
		
		groupNameTV = (TextView) findViewById(id.groupNameTextView);
		courseTV = (TextView) findViewById(id.courseTextView);
		descriptionTV = (TextView) findViewById(id.descriptionTextView);
		buildingTV = (TextView) findViewById(id.buildingTextView);
		locationTV = (TextView) findViewById(id.locationTextView);
		startDateET = (EditText) findViewById(id.startDateEditText);
		endDateET = (EditText) findViewById(id.endDateEditText);
		startTimeET = (EditText) findViewById(id.startTimeEditText);
		endTimeET = (EditText) findViewById(id.endTimeEditText);
//		membersCountET = (EditText) findViewById(id.membersCountEditText);
//		membersLimitET = (EditText) findViewById(id.membersLimitEditText);
		
		groupNameTV.setText(group.getGroupName());
		courseTV.setText(group.getSubject() + " " + group.getCourseNumber());
		descriptionTV.setText(group.getDescription());
		buildingTV.setText(group.getBuilding());
		locationTV.setText(group.getLocation());
		startDateET.setText(group.getStartDate());
		endDateET.setText(group.getEndDate());
		startTimeET.setText(group.getStartTime());
		endTimeET.setText(group.getEndTime());
//		membersCountET.setText(Integer.toString(group.getMembersCount()));
//		membersLimitET.setText(Integer.toString(group.getMembersLimit()));
		
		// get the listview
		requestsExpandableListView = (ExpandableListView) findViewById(id.requestsExpandableListView);
		
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        requestsExpandableListView.setAdapter(listAdapter);
        
        listAdapter.notifyDataSetChanged();
        
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099CC")));
	}
	
	private void prepareListData()
	{
		listDataHeader = new ArrayList<String>();
		final String header = "Requests To Join";
		listDataHeader.add(header);
		
		final String membersHeader = "Members";
		listDataHeader.add(membersHeader);
		
		listDataChild = new HashMap<String, List<User>>();
		listDataChild.put(header, group.getRequestsToJoin().getRequests());
		
		listDataChild.put(membersHeader, group.getMemberUsers());
	}
	
	private class ExpandableListAdapter extends BaseExpandableListAdapter {
		
		private Context _context;
	    private List<String> _listDataHeader; // header titles
	    // child data in format of header title, child title
	    private HashMap<String, List<User>> _listDataChild;
	 
	    public ExpandableListAdapter(Context context, List<String> listDataHeader,
	            HashMap<String, List<User>> listChildData) {
	        this._context = context;
	        this._listDataHeader = listDataHeader;
	        this._listDataChild = listChildData;
	    }

	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	 
	        final User user = (User) getChild(groupPosition, childPosition);
	        
	        Log.d("dat feel", "user: " + user);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_item_requests_to_join, null);
	        }
	 
	        TextView usernameTextView = (TextView) convertView
	                .findViewById(R.id.usernameTextView);
	        
//	        TextView firstLastNameTextView = (TextView) convertView
//	                .findViewById(R.id.firstLastNameToJoinTextView);

	        usernameTextView.setText(user.getUsername());
//	        firstLastNameTextView.setText(user.getProfile().getFirstLastName());
	        
	        return convertView;
	    }

		@Override
	    public int getChildrenCount(int groupPosition) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .size();
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
	            convertView = infalInflater.inflate(R.layout.list_group_requests_to_join, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.requestsToJoinTextView);
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
}
