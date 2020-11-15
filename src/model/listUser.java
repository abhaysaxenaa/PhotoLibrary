package model;

import java.util.ArrayList;

public class listUser {

	public ArrayList<User> allUsers;
	public User current;
	public boolean loggedIn;
	
	public listUser() {
		allUsers = new ArrayList<User>();
		allUsers.add(new User("admin"));
		this.current = null;
		this.loggedIn = false;
	}
	
	public boolean checkUserInList(User user) {
		for (int i = 0; i < allUsers.size(); i++) {
			if (user.getUsername().equals(allUsers.get(i))) {
				return true;
				//Set new current user
				//Set logged in to true
			}
		}
		return false;
	}
	
	public void addUser() {}
	public void deleteUser(int idx) {}
	public void deleteUser(User user) {}
	
	//Implement Save
	//Implement Load

}
