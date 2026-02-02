package com.rev.model;

public class Profile {
  private int userId;
  private String name;
  private String bio;
  private String profilePicturePath;
  private String location;
  private String website;
  
  public Profile() {}
  
public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getProfilePicturePath() {
	return profilePicturePath;
}

public void setProfilePicturePath(String profilePicturePath) {
	this.profilePicturePath = profilePicturePath;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public String getWebsite() {
	return website;
}

public void setWebsite(String website) {
	this.website = website;
}
public String getBio() {
	return bio;
}

public void setBio(String bio) {
	this.bio = bio;
}

public Profile(int userId, String name, String profilePicturePath, String location, String website) {
	super();
	this.userId = userId;
	this.name = name;
	this.profilePicturePath = profilePicturePath;
	this.location = location;
	this.website = website;
}
  
}
