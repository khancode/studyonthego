package com.studyonthegoapp.oop;

import org.json.JSONException;
import org.json.JSONObject;

public class StudyGroup {

	private int groupId;
	private String groupName;
	private String admin;
	private int courseId;
	private String description;
	private String building;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private int membersCount;
	private int membersLimit;
	
	public StudyGroup(JSONObject jsonObj)
	{
		try
		{
			this.groupId = jsonObj.getInt("GroupID");
			this.groupName = jsonObj.getString("GroupName");
			this.admin = jsonObj.getString("Admin");
			this.courseId = jsonObj.getInt("CourseID");
			this.description = jsonObj.getString("Description");
			this.building = jsonObj.getString("Building");
			this.location = jsonObj.getString("Location");
			
			this.startDate = jsonObj.getString("StartDate");
			this.endDate = jsonObj.getString("EndDate");
			this.startTime = jsonObj.getString("StartTime");
			this.endTime = jsonObj.getString("EndTime");
			
			this.membersCount = jsonObj.getInt("MembersCount");
			this.membersLimit = jsonObj.getInt("MembersLimit");
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int getGroupId() { return this.groupId; }
	public String getGroupName() { return this.groupName; }
	public String getAdmin() { return this.admin; }
	
	@Override
	public String toString()
	{
		return "GroupID: " + this.groupId + "\n" +
			   "GroupName: " + this.groupName + "\n" +
			   "Admin: " + this.admin + "\n" +
			   "CourseID: " + this.courseId + "\n" +
			   "Description: " + this.description + "\n" +
			   "Building: " + this.building + "\n" +
			   "Location: " + this.location + "\n" +
			   "StartDate: " + this.startDate + "\n" +
			   "EndDate: " + this.endDate + "\n" +
			   "StartTime: " + this.startTime + "\n" +
			   "EndTime: " + this.endTime + "\n" +
			   "MembersCount: " + this.membersCount + "\n" +
			   "MembersLimit: " + this.membersLimit;
	}
}
