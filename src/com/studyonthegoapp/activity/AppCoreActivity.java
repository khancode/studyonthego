package com.studyonthegoapp.activity;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.codebase.R.layout;
import com.studyonthegoapp.codebase.R.menu;
import com.studyonthegoapp.fragment.ActiveGroupFragment;
import com.studyonthegoapp.fragment.SearchStudyGroupsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AppCoreActivity extends ActionBarActivity {

	private String username;
	private String password;
	
	private static ViewPager viewPager;
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_core);
		
		
		// Get username that was passed into intent from LoginFragment
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");

	    // setup action bar for tabs
	    actionBar = getSupportActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(true);
			    
	    // Create the Fragments
	    ActiveGroupFragment activeGroupFragment = new ActiveGroupFragment();
	    SearchStudyGroupsFragment searchStudyGroupsFragment = new SearchStudyGroupsFragment();
			    
	    // ViewPager
	    viewPager = (ViewPager) findViewById(R.id.pager);
	    viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), activeGroupFragment, searchStudyGroupsFragment));
	    viewPager.setOffscreenPageLimit(2); 
	    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
			}
	    	
	    });

	    actionBar.addTab(actionBar.newTab().setText("Active").setTabListener(new TabListener<ActiveGroupFragment>(activeGroupFragment)));
	    actionBar.addTab(actionBar.newTab().setText("Search").setTabListener(new TabListener<SearchStudyGroupsFragment>(searchStudyGroupsFragment)));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_core, menu);
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
	
	public static class TabsPagerAdapter extends FragmentPagerAdapter
	{
		ActiveGroupFragment active_frag;
		SearchStudyGroupsFragment search_frag;

		public TabsPagerAdapter(FragmentManager fm, ActiveGroupFragment active_frag, SearchStudyGroupsFragment search_frag) { //, FriendsFragment friends_frag, MessagingFragment messaging_frag) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.active_frag = active_frag;
			this.search_frag = search_frag;
		}

		@Override
		public Fragment getItem(int index) {
			// TODO Auto-generated method stub
			switch (index)
			{
				case 0:
					return active_frag;
				case 1:
					return search_frag;
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		
	}
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener
	{
	    private Fragment mFragment;
//	    private final ActionBarActivity mActivity;
//	    private final String mTag;
//	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Fragment fragment) {
//	        mActivity = activity;
//	        mTag = tag;
//	        mClass = clz;
	    	
	    	mFragment = fragment;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        viewPager.setCurrentItem(tab.getPosition());
	        
	        Log.v("onTabSelected", "called!");
	        
//	        if (mFragment instanceof FriendsFragment)
//	        	((FriendsFragment) mFragment).getFriendRequestsAndFriends();
//	        else if (mFragment instanceof MessagingFragment)
//	        	((MessagingFragment) mFragment).getFriends();
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}
}