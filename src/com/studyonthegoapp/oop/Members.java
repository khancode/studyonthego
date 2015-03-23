package com.studyonthegoapp.oop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Members implements Parcelable {
	
	private ArrayList<User> members;
	
	public Members()
	{
		this.members = new ArrayList<User>();
	}
	
	public Members(JSONArray jArray)
	{
		members = new ArrayList<User>();
		
		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject profileObject = null;
			try {
				profileObject = jArray.getJSONObject(i);
			}
			catch (JSONException e) {
				Log.e("Memberse()", "This should not happen");
			}
			
			members.add(new User(profileObject));
		}
	}
	
	public Members(Parcel parcel)
	{
		this.members = parcel.createTypedArrayList(User.CREATOR);
	}
	
	public int length()	{ return this.members.size(); }
	
	public ArrayList<User> getUsers() { return this.members; }
	
	@Override
	public String toString()
	{
		String str = "";
		for (int i = 0; i < members.size(); i++)
			str += "\n" + i + ": " + members.get(i);
		
		return str;
	}
	
	public static final Parcelable.Creator<Members> CREATOR = new Creator<Members>() {  
		public Members createFromParcel(Parcel source) {  
		    return new Members(source);
		}

		@Override
		public Members[] newArray(int size) {
			return new Members[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeTypedList(members);
	}

}
