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

import android.os.AsyncTask;

import com.studyonthegoapp.active.RequestToJoinActivity;

public class RespondToRequest extends AsyncTask<String, Void, Void>
{	
	
	private boolean deleteRequestToJoinError;
	private boolean insertMemberError;
	private boolean updateStudyGroupError;
	
	private RequestToJoinActivity requestToJoinActivity;
	private String acceptRequest;
	
	public RespondToRequest(RequestToJoinActivity instance)
	{
		requestToJoinActivity = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String groupID = params[0];
		String username = params[1];
		acceptRequest = params[2];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/request/respond";
		String urlParameters = "groupID="+groupID+"&username="+username+"&acceptRequest="+acceptRequest;
		
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
		
			deleteRequestToJoinError = jsonObj.getBoolean("deleteRequestToJoinError");
			
			if (acceptRequest.equals("yes"))
			{
				insertMemberError = jsonObj.getBoolean("insertMemberError");
				updateStudyGroupError = jsonObj.getBoolean("updateStudyGroupError");
			}
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
		requestToJoinActivity.receiveRespondToRequestResult(acceptRequest, deleteRequestToJoinError,
															insertMemberError, updateStudyGroupError);
	}

}
