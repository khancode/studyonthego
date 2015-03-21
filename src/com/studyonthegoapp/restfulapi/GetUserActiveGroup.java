package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.active.ActiveGroupFragment;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.AsyncTask;
import android.util.Log;

public class GetUserActiveGroup extends AsyncTask<String, Void, Void>
{	
	private StudyGroup studyGroup;
	
	private ActiveGroupFragment activeGroupFragment;
	
	public GetUserActiveGroup(ActiveGroupFragment instance)
	{
		activeGroupFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String username = params[0];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/show/user/active?username="+username;
		
		StringBuffer response = new StringBuffer();
		
		try {
			URL obj = new URL(url);
		
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
//			con.setRequestProperty("User-Agent", "USER_AGENT");
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
//			System.out.println(response.toString());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{		
			JSONObject jObject = new JSONObject(response.toString());
				
			studyGroup = new StudyGroup(jObject);
		}
		catch (JSONException e) {
			if (response.toString().equals("null"))
				Log.d("GetUserActiveGroup", "JSONObject is null");
			else
				e.printStackTrace();
			
			studyGroup = null;
		}
        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to ActiveGroupFragment		
		activeGroupFragment.receiveUserActiveGroupResult(studyGroup);
	}

}

