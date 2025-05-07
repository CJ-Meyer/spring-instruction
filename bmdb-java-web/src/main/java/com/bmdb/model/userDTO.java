package com.bmdb.model;

public class userDTO {
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public userDTO() {
		super();
	}
	public userDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	@Override
	public String toString() {
		return "userDTO [username=" + username + ", password=" + password + "]";
	}
	
}
