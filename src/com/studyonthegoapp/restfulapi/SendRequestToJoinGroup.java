package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.search.StudyGroupDetailsActivity;

import android.os.AsyncTask;

public class SendRequestToJoinGroup extends AsyncTask<String, Void, Void>
{	
	private boolean insertRequestToJoinError;
	private StudyGroupDetailsActivity studyGroupDetailsActivity;
	
	public SendRequestToJoinGroup(StudyGroupDetailsActivity instance)
	{
		studyGroupDetailsActivity = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String groupID = params[0];
		String username = params[1];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/request";
		String urlParameters = "groupID="+groupID+"&username="+username;
		
		StringBuffer response = new StringBuffer();
		
		try {			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add request header
			con.setRequestMethod("POST");
//			con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
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
			System.out.println(response.toString());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			JSONObject jsonObj = new JSONObject(response.toString());
		
			insertRequestToJoinError = jsonObj.getBoolean("insertRequestToJoinError");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to Messaging activity
		studyGroupDetailsActivity.receiveSendRequestToJoinGroupResult(insertRequestToJoinError);
	}


}
