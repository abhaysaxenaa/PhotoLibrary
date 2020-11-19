package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class listUser implements Serializable{
	
	private  static final long serialVersionUID = 42L;
	
	public  ArrayList<User> allUsers;
	//public User current;
	//public boolean loggedIn;
	
	public final String admin = "admin";
	
	public  final String storeDir = "data";
	public  final String storeFile = "dat.dat";
	
	public listUser() {  
		allUsers = new ArrayList<User>();
		allUsers.add(new User("admin"));
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
	
	public  void addUser(User user) {
		allUsers.add(user);
	}
	
	public void deleteUser(User user) {
		allUsers.remove(user);
	}
	
	public  ArrayList<User> getList(){
		return allUsers;
	}
	
	public User getUser(String user) {
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
	
	public  listUser read() throws IOException, ClassNotFoundException{
		ObjectInputStream o = new ObjectInputStream(new FileInputStream(storeDir+File.separator + storeFile));
		listUser userList = (listUser)o.readObject();
		o.close();
		return userList;
		}
	
	
	public void write(listUser userList) throws IOException{

		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator + storeFile));
		o.writeObject(userList);
		o.close();
	}
	
	
	
	//Implement Save
	//Implement Load

}
