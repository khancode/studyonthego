package com.studyonthegoapp.oop;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class StudyGroup implements Parcelable{

	private int groupId;
	private String groupName;
	private String admin;
	private int courseId;
	private String subject;
	private int courseNumber;
	private String section;
	private String description;
	private String building;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int membersCount;
	private int membersLimit;
	
	private Members members;
	private RequestsToJoin requestsToJoin;
	
	public StudyGroup(JSONObject jsonObj)
	{
		try
		{
			this.groupId = jsonObj.getInt("GroupID");
			this.groupName = jsonObj.getString("GroupName");
			this.admin = jsonObj.getString("Admin");
			this.courseId = jsonObj.getInt("CourseID");
			this.subject = jsonObj.getString("Subject");
			this.courseNumber = jsonObj.getInt("CourseNumber");
			this.section = jsonObj.getString("Section");
			this.description = jsonObj.getString("Description");
			this.building = jsonObj.getString("Building");
			
			if (jsonObj.has("Location"))
				this.location = jsonObj.getString("Location");
			else
				this.location = null;
			
			this.startDate = jsonObj.getString("StartDate");
			this.endDate = jsonObj.getString("EndDate");
			this.startTime = jsonObj.getString("StartTime");
			this.endTime = jsonObj.getString("EndTime");
			
			this.membersCount = jsonObj.getInt("MembersCount");
			this.membersLimit = jsonObj.getInt("MembersLimit");
			
			if (jsonObj.has("members"))
				this.members = new Members(jsonObj.getJSONArray("members"));
			else
				this.members = null;
			
			if (jsonObj.has("requestsToJoin"))
				this.requestsToJoin = new RequestsToJoin(jsonObj.getJSONArray("requestsToJoin"));
			else
				this.requestsToJoin = null; // null means user is not admin
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* For creating a new group */	
	public StudyGroup(int groupId, String groupName, User adminUser, int courseId, String subject, 
					  int courseNumber, String section, String description, String building,
					  String location, String startDate, String endDate, String startTime,
					  String endTime, int membersLimit)
	{
		this.groupId = groupId;
		this.groupName = groupName;
		this.admin = adminUser.getUsername();
		this.courseId = courseId;
		this.subject = subject;
		this.courseNumber = courseNumber;
		this.section = section;
		this.description = description;
		this.building = building;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.membersCount = 1; // the group always includes the admin
		this.membersLimit = membersLimit;
		
		this.members = new Members();
		this.members.add(adminUser); // add the admin to members.
		this.requestsToJoin = new RequestsToJoin();
	}
	
	public StudyGroup(Parcel parcel)
	{
		this.groupId = parcel.readInt();
		this.groupName = parcel.readString();
		this.admin = parcel.readString();
		this.courseId = parcel.readInt();
		this.subject = parcel.readString();
		this.courseNumber = parcel.readInt();
		this.section = parcel.readString();
		this.description = parcel.readString();
		this.building = parcel.readString();
		this.location = parcel.readString();
		
		this.startDate = parcel.readString();
		this.endDate = parcel.readString();
		this.startTime = parcel.readString();
		this.endTime = parcel.readString();
		
		this.membersCount = parcel.readInt();
		this.membersLimit = parcel.readInt();
		
		this.members = parcel.readParcelable(getClass().getClassLoader());
		this.requestsToJoin = parcel.readParcelable(getClass().getClassLoader());
	}
	
	public boolean isAdmin(String username) { return username.equalsIgnoreCase(admin); }
	
	public int getGroupId() { return this.groupId; }
	public String getGroupName() { return this.groupName; }
	public String getAdmin() { return this.admin; }
	public int getCourseId() { return this.courseId; }
	public String getSubject() { return this.subject; }
	public int getCourseNumber() { return this.courseNumber; }
	public String getSection() { return this.section; }
	public String getDescription() { return this.description; }
	public String getBuilding() { return this.building; }
	public String getLocation() { return this.location; }
	public String getStartDate() { return this.startDate; }
	public String getEndDate() { return this.endDate; }
	public String getStartTime() { return this.startTime; }
	public String getEndTime() { return this.endTime; }
	public int getMembersCount() { return this.membersCount; }
	public int getMembersLimit() { return this.membersLimit; }
	
	public Members getMembers() { return this.members; }
	public ArrayList<User> getMemberUsers() { return this.members.getUsers(); }
	public RequestsToJoin getRequestsToJoin() { return this.requestsToJoin; }
	
	public void addMember(User user) 
	{
		this.members.add(user);
		this.membersCount++;
	}
	
	@Override
	public String toString()
	{
		return "GroupID: " + this.groupId + "\n" +
			   "GroupName: " + this.groupName + "\n" +
			   "Admin: " + this.admin + "\n" +
			   "CourseID: " + this.courseId + "\n" +
			   "Subject: " + this.subject + "\n" +
			   "CourseNumber: " + this.courseNumber + "\n" +
			   "Section: " + this.section + "\n" + 
			   "Description: " + this.description + "\n" +
			   "Building: " + this.building + "\n" +
			   "Location: " + this.location + "\n" +
			   "StartDate: " + this.startDate + "\n" +
			   "EndDate: " + this.endDate + "\n" +
			   "StartTime: " + this.startTime + "\n" +
			   "EndTime: " + this.endTime + "\n" +
			   "MembersCount: " + this.membersCount + "\n" +
			   "MembersLimit: " + this.membersLimit + "\n" + 
			   "Members: " + this.members + "\n" + 
			   "RequestsToJoin: " + this.requestsToJoin;
	}
	
	public static final Parcelable.Creator<StudyGroup> CREATOR = new Creator<StudyGroup>() {  
		public StudyGroup createFromParcel(Parcel source) {  
		    return new StudyGroup(source);
		}

		@Override
		public StudyGroup[] newArray(int size) {
			// TODO Auto-generated method stub
			return new StudyGroup[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {

		parcel.writeInt(groupId);
		parcel.writeString(groupName);
		parcel.writeString(admin);
		parcel.writeInt(courseId);
		parcel.writeString(subject);
		parcel.writeInt(courseNumber);
		parcel.writeString(section);
		parcel.writeString(description);
		parcel.writeString(building);
		parcel.writeString(location);
		parcel.writeString(startDate);
		parcel.writeString(endDate);
		parcel.writeString(startTime);
		parcel.writeString(endTime);
		parcel.writeInt(membersCount);
		parcel.writeInt(membersLimit);
		
		parcel.writeParcelable(members, flags);
		parcel.writeParcelable(requestsToJoin, flags);
	}
}
