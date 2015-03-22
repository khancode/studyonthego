package com.studyonthegoapp.oop;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	
	private String username;
	private Profile profile;

	/* Constructor for inserting dummy data */
	public User(String username, Profile profile)
	{
		this.username = username;
		this.profile = profile;
	}
	
	public User(JSONObject jObject)
	{
		try {
			this.username = jObject.getString("Username");
			this.profile = new Profile(jObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User(Parcel parcel)
	{
		this.username = parcel.readString();
		this.profile = parcel.readParcelable(getClass().getClassLoader());
	}
	
	public String getUsername() { return this.username; }
	public Profile getProfile() { return this.profile; }
	
	@Override
	public String toString()
	{
		return "username: " + username +
			   "\nprofile: " + profile;
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {  
		public User createFromParcel(Parcel source) {  
		    return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		// TODO Auto-generated method stub
		parcel.writeString(username);
		parcel.writeParcelable(profile, flags);
	}
}
