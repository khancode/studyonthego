package com.studyonthegoapp.oop;

import java.util.ArrayList;

public class Buildings {

	private static ArrayList<String> buildings;
	
	public Buildings()
	{
		buildings = new ArrayList<String>();
	}
	
	public Buildings(ArrayList<String> buildings_inp)
	{
		buildings = new ArrayList<String>();
		buildings.addAll(buildings_inp);
	}
	
	public int length()	{ return buildings.size(); }
	
	public static ArrayList<String> getBuildings() { return buildings; }
	
}
