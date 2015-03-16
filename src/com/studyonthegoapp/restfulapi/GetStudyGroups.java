package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.fragment.SearchStudyGroupsFragment;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.AsyncTask;

public class GetStudyGroups extends AsyncTask<String, Void, Void>
{	
//	private ArrayList<HashMap<String, String>> messages;
//	
//	private MessagingFragment messagingActivity;
	
	private ArrayList<StudyGroup> studyGroups;
	private SearchStudyGroupsFragment searchStudyGroupFragment;
	
	public GetStudyGroups(SearchStudyGroupsFragment instance)
	{
		searchStudyGroupFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
//		String username = params[0];
//		String friendUsername = params[1];
//		String dateTime = params[2];
		
		// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httppost = new HttpGet("http://studyonthegoapp.com/rest/studygroups/show");
        JSONObject json = new JSONObject();
        
        // Will hold the whole all the data gathered from the URL
     	String result = null;
 
        try {
//            // JSON data:
//            json.put("username", username);
//            json.put("friendUsername", friendUsername);
//            json.put("dateTime", dateTime);
// 
//            JSONArray postjson = new JSONArray();
//            postjson.put(json);
//// 
//            // Post the data:
//            httppost.setHeader("json",json.toString());
//            httppost.getParams().setParameter("jsonpost",postjson);
 
            // Execute HTTP Post Request
            System.out.print(json);
            HttpResponse response = httpclient.execute(httppost);
 
            // for JSON:
            if(response != null)
            {
                InputStream is = response.getEntity().getContent();
 
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
 
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                result = sb.toString();
            }
 
 
        }catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        
        
        // Holds Key Value pairs from a JSON source
		JSONObject jsonObject;
		try {

			// Print out all the data read in
			// Log.v("JSONParser RESULT ", result);

			// Get the root JSONObject
//			jsonObject = new JSONObject(result);
			
//			if (result.equals("null"))
				
//			System.out.println(); // Consume line
//			System.out.println(result);
//			
//			if (result.equals("none"))
//			{
//				System.out.println("dude");
//				messages = null;
//				return null;
//			}
			
			JSONArray jArray = new JSONArray(result);
			
//			messages = new ArrayList<HashMap<String, String>>();
			
			studyGroups = new ArrayList<StudyGroup>();
			
			for(int i = 0; i < jArray.length(); i++)
			{
			   jsonObject = jArray.getJSONObject(i);
			   
			   studyGroups.add(new StudyGroup(jsonObject));
			   
//			   HashMap<String, String> messageRow = new HashMap<String, String>();
//			   
//			   messageRow.put("fromUsername", jsonObject.getString("FromUsername"));
//			   messageRow.put("toUsername", jsonObject.getString("ToUsername"));
//			   messageRow.put("originalMessage", jsonObject.getString("OriginalMessage"));
//			   messageRow.put("modifiedMessage", jsonObject.getString("ModifiedMessage"));
//			   messageRow.put("dateTime", jsonObject.getString("DateTime"));
//			   messageRow.put("status", jsonObject.getString("Status"));
//			   messageRow.put("fromMisses", jsonObject.getString("FromMisses"));
//			   messageRow.put("fromTimeElapsed", jsonObject.getString("FromTimeElapsed"));
//			   
//			   messageRow.put("toMisses", jsonObject.getString("ToMisses"));
//			   messageRow.put("toTimeElapsed", jsonObject.getString("ToTimeElapsed"));
//			   messageRow.put("fromPoints", jsonObject.getString("FromPoints"));
//			   messageRow.put("toPoints", jsonObject.getString("ToPoints"));
//			   messageRow.put("winner", jsonObject.getString("Winner"));
//			   
//			   messages.add(messageRow);
			}
			
			// Check if the MySQL query caused an error
//			boolean mysqlError = jsonObject.optBoolean("mysqlError");
//			
//			if (mysqlError)
//			{
//				fromUsername = message = dateTime = "MySQL error";
//				return null;
//			}

		} catch (JSONException e) {
//			messages = null;
			studyGroups = null;
//			e.printStackTrace();
		}

        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to Messaging activity
		searchStudyGroupFragment.receiveGetStudyGroupsResultFromMySQL(studyGroups);
	}

}
