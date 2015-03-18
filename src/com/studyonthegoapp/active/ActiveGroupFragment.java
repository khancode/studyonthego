package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.StudyGroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ActiveGroupFragment extends Fragment implements OnClickListener {
	
	public static final int REQUEST_CODE = 1;
	
	private Button actionButton;
	
	private FragmentManager manager;
	
	private Profile profile;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_active_group, container, false);
		
		actionButton = (Button) view.findViewById(id.actionButton);
		actionButton.setOnClickListener(this);
		
		manager = getFragmentManager();
		addNoGroupInnerFragment();
		
		return view;
	}
	
	/** Immediately called after being instantiated */
	public void setProfileFromAppCoreActivity(Profile profile)
	{ 
		this.profile = profile; 
	}
	
	@Override
	public void onClick(View arg0) {
				
		Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
		intent.putExtra("profile", profile);
		startActivityForResult(intent, REQUEST_CODE);
			
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.d("onActivityResult", "it worked in fragment!");
//		Log.d("onActivityResult", "requestCode: " + requestCode +
//								  " resultCode: " + resultCode);
//		Log.d("shouldbe", "REQUEST_CODE: " + REQUEST_CODE +
//						  "RESULT_CODE: " + getActivity().RESULT_OK);
		
		if (requestCode == REQUEST_CODE)
		{			
		    if (resultCode == Activity.RESULT_OK) {
//		        if (data.hasExtra("myData1")) {
//		            Toast.makeText(getActivity(), data.getExtras().getString("myData1"),
//		                Toast.LENGTH_SHORT).show();
//		        }
		        
		        StudyGroup group = (StudyGroup) data.getExtras().getParcelable("studyGroup");
//		        Log.d("onActivityResult", "group: " + group.toString());
		        
		        replaceInnerFragmentWithAdminGroup(group);
		    }
		}
	}
	
	private void addNoGroupInnerFragment()
	{
		NoGroupInnerFragment f1 = new NoGroupInnerFragment();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.frameContainer, f1, "noGroup");
		transaction.commit();
	}
	
	private void replaceInnerFragmentWithAdminGroup(StudyGroup group)
	{
		FragmentTransaction transaction = manager.beginTransaction();
		
		NoGroupInnerFragment noGroup = (NoGroupInnerFragment) manager.findFragmentByTag("noGroup");
		transaction.remove(noGroup);
		
		AdminGroupInnerFragment adminGroup = new AdminGroupInnerFragment();
		// Use bundle so AdminGroupInnerFragment can access StudyGroup object.
		Bundle args = new Bundle();
		args.putParcelable("studyGroup", group);
		adminGroup.setArguments(args);
		
		transaction.add(id.frameContainer, adminGroup, "adminGroup");
		
		transaction.commit();
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
	

}
