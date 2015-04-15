package com.studyonthegoapp.gcm;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.studyonthegoapp.activity.AppCoreActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class ConfigGCM
{
	// Google project id
    private final String SENDER_ID = "776363977422";
    private GoogleCloudMessaging gcm;
    private String regID;
    
    AppCoreActivity appCoreActivity;
	
    public ConfigGCM(AppCoreActivity instance)
    {
    	appCoreActivity = instance;
		gcm = GoogleCloudMessaging.getInstance(appCoreActivity);
    }
    
	public void setUpGCM()
	{
	    if (isRegistered() == false)
	    {
	    	Log.v("isRegistered()", "false");
	    	
	    	new RegisterWithGCM().execute();
	    	
	    	// onPostExecute it will run InsertGCMRegIDAsyncTask
	    }
	    else
	    {
	    	Log.v("isRegistered()", "true");
	    	
	    	// Store in mysql db every time to handle the case where an account is used on multiple devices
//		    InsertGCMRegIDAsyncTask asyncTask = new InsertGCMRegIDAsyncTask(this);
//	    	asyncTask.execute(username, regID);
	    }
	}
	
	private boolean isRegistered()
	{
		SharedPreferences shp = appCoreActivity.getBaseContext().getSharedPreferences("gcm", Activity.MODE_PRIVATE);
		
		regID = shp.getString("RegID", null);
		Log.v("isRegistered()", "fromSharedPref regID is: " + regID);
		
		return shp.contains("RegID");
	}

	private class RegisterWithGCM extends AsyncTask<String, Void, Void>
	{		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				regID = gcm.register(SENDER_ID);
	//			gcm.unregister();
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v)
		{
			// Return the results to Messaging activity
			Log.v("isRegistered()", "regID is: " + regID);
	    	
	    	SharedPreferences shp = appCoreActivity.getBaseContext().getSharedPreferences("gcm", Activity.MODE_PRIVATE);
	    	SharedPreferences.Editor editor=shp.edit();
	    	editor.putString("RegID", regID).commit();
//	    	
//	    	// Store in mysql db every time to handle the case where an account is used on multiple devices
//	    	InsertGCMRegIDAsyncTask asyncTask = new InsertGCMRegIDAsyncTask(homePageActivity);
//	    	asyncTask.execute(username, regID);
		}
	}
}