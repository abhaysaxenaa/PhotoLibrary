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
	public User present;
	public int currUserIdx = -1;
	//public boolean loggedIn;
	
	//public final String admin = "admin";
	
	public  final static String storeDir = "data";
	public  final static String storeFile = "dat.dat";
	
	public listUser() {  
		allUsers = new ArrayList<User>();
		//allUsers.add(new User("admin"));
		//allUsers.add(new User("admin"));
		//this.current = null;
		//this.loggedIn = false;
	}
	
	public boolean checkUserInList(String user) {
		for (int i = 0; i < allUsers.size(); i++) {
			if (user.equals(allUsers.get(i).getUsername())) {
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
	
	public void deleteUser(int idx) {
		allUsers.remove(idx);
	}
	
	public  ArrayList<User> getList(){
		return allUsers;
	}
	
	public User getUser(User user) {
		for(int i = 0; i < allUsers.size(); i++) {
			if(allUsers.get(i).equals(user)) {
				return allUsers.get(i);
			}
		}
		return null;
	}
	
	public User getCurrentUser() {
		return allUsers.get(currUserIdx);
	}
	
	public void setCurrent(User present) {
		for(int i = 0; i < allUsers.size(); i++) {
			System.out.println(allUsers.get(i));
			if(allUsers.get(i).getUsername().equals(present.getUsername())) {
				
				this.currUserIdx = i;
			}
		}
		System.out.println(this.currUserIdx+" "+allUsers.get(this.currUserIdx));
		this.present = present;
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
	
	public static listUser read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		listUser userList = (listUser) ois.readObject();
			ois.close();
		return userList;
		
	}
	
	
	public static void write(listUser userlist) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(userlist);
		oos.close();
	}
	
	
	
	//Implement Save
	//Implement Load

}