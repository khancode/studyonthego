package com.studyonthegoapp.active;

import com.studyonthegoapp.activity.AppCoreActivity;
import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ActiveGroupFragment extends Fragment implements OnClickListener {
	
	private Button actionButton;
	
	private FragmentManager manager;
	
	private String username;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_active_group, container, false);
		
		actionButton = (Button) view.findViewById(id.actionButton);
		actionButton.setOnClickListener(this);
		
		manager = getFragmentManager();
		addFragment();
		
		username = ((AppCoreActivity) view.getContext()).getUsername();
		
		return view;
	}
	
	@Override
	public void onClick(View arg0) {
				
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
		intent.putExtra("username", username);
		startActivity(intent);
			
	}	
	
	private void addFragment()
	{
		NoGroupInnerFragment f1 = new NoGroupInnerFragment();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.frameContainer, f1, "noGroup");
		transaction.commit();
	}
	
	private void removeFragment()
	{
		NoGroupInnerFragment f1 = (NoGroupInnerFragment) manager.findFragmentByTag("noGroup");
		FragmentTransaction transaction = manager.beginTransaction();
		if (f1 != null)
		{
			transaction.remove(f1);
			transaction.commit();
		}
	}

	int counter = 0;
	
	private void replaceFragment()
	{
		FragmentTransaction transaction = manager.beginTransaction();
		
		if (counter++ % 2 == 0)
			transaction.replace(R.id.frameContainer, new NoGroupInnerFragment(), "No");
		else
			transaction.replace(R.id.frameContainer, new AdminGroupInnerFragment(), "Yes");
		
		transaction.commit();
	}
	

}
