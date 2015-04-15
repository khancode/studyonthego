package com.studyonthegoapp.active;

import java.util.ArrayList;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.oop.User;
import com.studyonthegoapp.restfulapi.GetUserCurrentGroups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActiveGroupFragment extends Fragment implements OnClickListener {
	
	public static final int CREATE_GROUP_REQUEST_CODE = 1;
	public static final int ACTIVE_GROUP_REQUEST_CODE = 2;
	
	private Button actionButton;
	
	private FragmentManager manager;
	
	private User user;
	
	private ListView listView;
	private MySimpleArrayAdapter adapter;
	private ArrayList<StudyGroup> studyGroups;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_active_group, container, false);
		
		actionButton = (Button) view.findViewById(id.actionButton);
		actionButton.setOnClickListener(this);
		
		manager = getFragmentManager();
//		addNoGroupInnerFragment();
		
		listView = (ListView) view.findViewById(id.studyGroupsListView);
		
		// Set OnItemClickListener
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				StudyGroup group = (StudyGroup) adapter.getItemAtPosition(position);
				
				viewActiveStudyGroupDetails(group);
			}
	    	
	    });
		
		return view;
	}
	
	private void viewActiveStudyGroupDetails(StudyGroup group)
	{
		Intent intent = new Intent(getActivity(), ActiveGroupDetailsActivity.class);
		intent.putExtra("studyGroup", group);
		startActivityForResult(intent, ACTIVE_GROUP_REQUEST_CODE);
	}
	
	/** Immediately called after being instantiated (because of dummy data) */
	public void setUserFromAppCoreActivity(User user)
	{ 
		this.user = user;
		
		getUserCurrentGroups();
	}
	
	
	@Override
	public void onClick(View arg0) {
				
		Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
		intent.putExtra("user", user);
		startActivityForResult(intent, CREATE_GROUP_REQUEST_CODE);
			
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.d("onActivityResult", "it worked in fragment!");
//		Log.d("onActivityResult", "requestCode: " + requestCode +
//								  " resultCode: " + resultCode);
//		Log.d("shouldbe", "REQUEST_CODE: " + REQUEST_CODE +
//						  "RESULT_CODE: " + getActivity().RESULT_OK);
		
		if (requestCode == CREATE_GROUP_REQUEST_CODE)
		{			
		    if (resultCode == Activity.RESULT_OK) {
//		        if (data.hasExtra("myData1")) {
//		            Toast.makeText(getActivity(), data.getExtras().getString("myData1"),
//		                Toast.LENGTH_SHORT).show();
//		        }
		        
		        StudyGroup group = (StudyGroup) data.getExtras().getParcelable("studyGroup");
		        
		        this.studyGroups.add(group);
		        adapter.notifyDataSetChanged();
		        
//		        replaceInnerFragmentWithAdminGroup(group);
		    }
		}
		else if (requestCode == ACTIVE_GROUP_REQUEST_CODE)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				boolean groupUpdated = data.getExtras().getBoolean("groupUpdated");
				if (groupUpdated)
				{
					StudyGroup group = (StudyGroup) data.getExtras().getParcelable("studyGroup");
					
					for (int i = 0; i < studyGroups.size(); i++)
					{
						if (studyGroups.get(i).getGroupId() == group.getGroupId())
						{
							studyGroups.set(i, group);
							break;
						}
					}
				
					adapter.notifyDataSetChanged();
				}
				
//				Log.d("onActivityResult", "groupUpdated: " + groupUpdated);
			}
		}
	}
	
//	private void addNoGroupInnerFragment()
//	{
//		NoGroupInnerFragment f1 = new NoGroupInnerFragment();
//		FragmentTransaction transaction = manager.beginTransaction();
//		transaction.add(R.id.frameContainer, f1, "noGroup");
//		transaction.commit();
//	}
//	
//	private void replaceInnerFragmentWithAdminGroup(StudyGroup group)
//	{
//		FragmentTransaction transaction = manager.beginTransaction();
//		
//		NoGroupInnerFragment noGroup = (NoGroupInnerFragment) manager.findFragmentByTag("noGroup");
//		transaction.remove(noGroup);
//		
//		AdminGroupInnerFragment adminGroup = new AdminGroupInnerFragment();
//		// Use bundle so AdminGroupInnerFragment can access StudyGroup object.
//		Bundle args = new Bundle();
//		args.putParcelable("studyGroup", group);
//		adminGroup.setArguments(args);
//		
//		transaction.add(id.frameContainer, adminGroup, "adminGroup");
//		
//		transaction.commit();
//	}
	
	private void getUserCurrentGroups()
	{
		GetUserCurrentGroups asyncTask = new GetUserCurrentGroups(this);
		asyncTask.execute(user.getUsername());
	}
	
	public void receiveUserCurrentGroupsResult(StudyGroup[] groups)
	{
		final String TAG = "receiveUserActiveGroupResult";
		
		if (groups == null) {
			Log.d(TAG, "studyGroup is null. Thus, no user active group found.");
			return;
		}
		
//		if (group.isAdmin(user.getUsername()))
//		{
////			replaceInnerFragmentWithAdminGroup(group);
//		}
		
		this.studyGroups = new ArrayList<StudyGroup>(); //new StudyGroup[]{group, group, group};
		
		for (int i = 0; i < groups.length; i++){
			this.studyGroups.add(groups[i]);
		}
		
//		this.studyGroups.add(group);
		
		// Add to list view
	    adapter = new MySimpleArrayAdapter(getActivity(), this.studyGroups);
	    listView.setAdapter(adapter);
	    
	    Log.d(TAG, "received data!");
	    
//	    // Set OnItemClickListener
//	    listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
//				StudyGroup group = (StudyGroup) adapter.getItemAtPosition(position);
//				
//				viewStudyGroupDetails(group);
//			}
//	    	
//	    });
	}
	
//	private void removeFragment()
//	{
//		NoGroupInnerFragment f1 = (NoGroupInnerFragment) manager.findFragmentByTag("noGroup");
//		FragmentTransaction transaction = manager.beginTransaction();
//		if (f1 != null)
//		{
//			transaction.remove(f1);
//			transaction.commit();
//		}
//	}
//
//	int counter = 0;
//	
//	private void replaceFragment()
//	{
//		FragmentTransaction transaction = manager.beginTransaction();
//		
//		if (counter++ % 2 == 0)
//			transaction.replace(R.id.frameContainer, new NoGroupInnerFragment(), "No");
//		else
//			transaction.replace(R.id.frameContainer, new AdminGroupInnerFragment(), "Yes");
//		
//		transaction.commit();
//	}
	
	private class MySimpleArrayAdapter extends ArrayAdapter<StudyGroup> {

	    Context context;
	    ArrayList<StudyGroup> values;

	    public MySimpleArrayAdapter(Context context, ArrayList<StudyGroup> values) {
	        super(context, R.layout.row_active_study_group, values);
	        this.context = context;
	        this.values = values;
	    }
	    
	    class ViewHolder {
	    	TextView groupNameTV;
	    	TextView courseTV;
	    	TextView buildingTV;
	    	
	    	TextView requestsTV;
	    	TextView membersTV;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	View rowView = convertView;
	    	// reuse views
	    	if (rowView == null) {
	    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		rowView = inflater.inflate(R.layout.row_active_study_group, parent, false);
	    		// configure view holder
	    		ViewHolder viewHolder = new ViewHolder();
	    		viewHolder.groupNameTV = (TextView) rowView.findViewById(R.id.groupNameTextView);
	    		viewHolder.courseTV = (TextView) rowView.findViewById(R.id.courseTextView);
	    		viewHolder.buildingTV = (TextView) rowView.findViewById(R.id.buildingTextView);
	    		
	    		viewHolder.requestsTV = (TextView) rowView.findViewById(R.id.requestsTextView);
	    		viewHolder.membersTV = (TextView) rowView.findViewById(R.id.membersTextView);
	    		rowView.setTag(viewHolder);
	    	}
	    	
	    	// fill data
	    	ViewHolder holder = (ViewHolder) rowView.getTag();
	    	StudyGroup group = values.get(position);
	    	holder.groupNameTV.setText(group.getGroupName());
	    	holder.courseTV.setText(group.getSubject() + " " + group.getCourseNumber());
	    	holder.buildingTV.setText(group.getBuilding());
	    	
	    	holder.requestsTV.setText(Integer.toString(group.getRequestsToJoin().length()));
	    	holder.membersTV.setText(group.getMembersCount() + "/" + group.getMembersLimit());
	    	
//		  TextView groupNameTV = (TextView) rowView.findViewById(R.id.groupNameTextView);
//		  groupNameTV.setText(values[position].getGroupName());
//		  TextView courseTV = (TextView) rowView.findViewById(R.id.courseTextView);
//		  courseTV.setText(Integer.toString(values[position].getCourseId()));
//		  TextView textView = (TextView) rowView.findViewById(R.id.buildingTextView);
//		  textView.setText(values[position].getBuilding());
		  // Change the icon for Windows and iPhone
//		  String s = values[position];
//		  if (s.startsWith("Windows7") || s.startsWith("iPhone")
//		  || s.startsWith("Solaris")) {
//		    imageView.setImageResource(R.drawable.no);
//		  } else {
//		    imageView.setImageResource(R.drawable.ok);
//		  }
		
		  return rowView;
	    }
	    
	}

}
