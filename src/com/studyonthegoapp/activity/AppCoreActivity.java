package com.studyonthegoapp.activity;

import java.util.ArrayList;

import com.studyonthegoapp.active.ActiveGroupFragment;
import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.oop.Buildings;
import com.studyonthegoapp.oop.Course;
import com.studyonthegoapp.oop.Profile;
import com.studyonthegoapp.oop.User;
import com.studyonthegoapp.profile.ProfileFragment;
import com.studyonthegoapp.restfulapi.GetBuildings;
import com.studyonthegoapp.search.SearchStudyGroupsFragment;
import com.studyonthegoapp.slidingtabs.SlidingTabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AppCoreActivity extends ActionBarActivity {

	/**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;
    
    private ActiveGroupFragment activeGroupFragment;
	private SearchStudyGroupsFragment searchStudyGroupsFragment;
	private ProfileFragment profileFragment;
	
	private Profile profile;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_core);
		
		// Get username that was passed into intent from MainActivity
		Intent intent = getIntent();
		String username = intent.getStringExtra("username");
		
		// Create the Fragments
	    activeGroupFragment = new ActiveGroupFragment();
	    searchStudyGroupsFragment = new SearchStudyGroupsFragment();
	    profileFragment = new ProfileFragment();
		
		// BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager(), activeGroupFragment, searchStudyGroupsFragment, profileFragment));
        mViewPager.setOffscreenPageLimit(3);
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
        
        
        // TODO Get courses from either T-Square API or MySQL
	    // dummy courses data for now
	    Course[] courses = { new Course(1, "CS", 3451, "A"),
	    					 new Course(4, "CS", 3600, "A"),
	    					 new Course(5, "CS", 3251, "B"),
	    					 new Course(3, "CS", 2200, "B")};
	    
	    this.profile = new Profile(username, "Omar", "Khan", courses, "Computer Science",
	    						   "Senior", "Java, C, Python, JavaScript, PHP, Android");
	    
	    this.user = new User(username, profile);
	    
	    activeGroupFragment.setUserFromAppCoreActivity(user);
	    searchStudyGroupsFragment.setUserFromAppCoreActivity(user);
	    profileFragment.setProfileFromAppCoreActivity(profile);
	    
	    // to get all Georgia Tech Buildings
	    getBuildings();
	}
	
	private void getBuildings()
	{
		GetBuildings asyncTask = new GetBuildings(this);
		asyncTask.execute();
	}
	
	public void receiveGetBuildingsResult(ArrayList<String> buildings_result, int responseCode)
	{
		//buildings.addAll(buildings_result);
		Buildings build = new Buildings(buildings_result); 
		System.out.println(Buildings.getBuildings());
		//showAlertDialog(responseCode);
		
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
	
	/**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends FragmentPagerAdapter {

    	ActiveGroupFragment active_frag;
		SearchStudyGroupsFragment search_frag;
		ProfileFragment profile_frag;

		public SamplePagerAdapter(FragmentManager fm, ActiveGroupFragment active_frag, SearchStudyGroupsFragment search_frag,
								ProfileFragment profile_frag)
		{
			super(fm);
			// TODO Auto-generated constructor stub
			this.active_frag = active_frag;
			this.search_frag = search_frag;
			this.profile_frag = profile_frag;
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
				case 2:
					return profile_frag;
				default:
					return null;
			}
		}

		/**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }
        // END_INCLUDE (pageradapter_getpagetitle)
		
    }
}
