package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class listUser {
	private  static final long serialVersionUID = 9221355046218690511L;
	public static ArrayList<User> allUsers;
	public User current;
	public boolean loggedIn;
	private static   String photofile = "data/dat.dat";
	File data = new File(photofile);
	
	public listUser() {  
		allUsers = new ArrayList<User>();
		//allUsers.add(new User("admin"));
		//this.current = null;
		//this.loggedIn = false;
	}
	
	public boolean checkUserInList(String user) {
		for (int i = 0; i < allUsers.size(); i++) {
			if (user.equals(allUsers.get(i))) {
				return true;
				
				//Set new current user
				//Set logged in to true
			}
		}
		return false;
	}
	
	public static void addUser(User user) {
		allUsers.add(user);
	}
	
	public void deleteUser(User user) {
		allUsers.remove(user);
	}
	
	public static ArrayList<User> getList(){
		return allUsers;
	}
	
	public static User getUser(String user) {
		for(int i = 0; i < allUsers.size(); i++) {
			if(allUsers.get(i).equals(user)) {
				return allUsers.get(i);
			}
		}
		return null;
	}
	
	
	public String toString() {
		if(allUsers == null) {
			return "There are no users";
			}
		String result = "";
		for(int i = 0; i < allUsers.size(); i++) {
			result = result + allUsers.get(i);
		}
		return result;
		
	}
	
	public static listUser read() throws IOException, ClassNotFoundException{
		FileInputStream fileInputStream = new FileInputStream(photofile);
		ObjectInputStream o = new ObjectInputStream(fileInputStream);
		listUser userlist = (listUser) o.readObject();
		o.close();
		return userlist;
		}
	
	
	public static void save(listUser userList) throws IOException, ClassNotFoundException{
		FileOutputStream fileOutputStream = new FileOutputStream(photofile);
		ObjectOutputStream o  = new ObjectOutputStream(fileOutputStream);
		o.writeObject(getList());
		o.close();
	}
	
	
	
	//Implement Save
	//Implement Load

}
