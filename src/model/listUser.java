
//Abhay Saxena (ans192) & GVS Karthik (vg311)

package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * @author Venkata Sai Karthik Gandrath
 * @author Abhay Saxena 
 * 
 * 
 */
public class listUser implements Serializable{
	
	private  static final long serialVersionUID = 42L;
	
	public  ArrayList<User> allUsers;
	public User present;
	public int currUserIdx = -1;
	//public boolean loggedIn;
	
	//public final String admin = "admin";
	
	public  final static String storeDir = "data";
	public  final static String storeFile = "dat.dat";
	
	/*
	 * constructor
	 */
	
	public listUser() {  
		allUsers = new ArrayList<User>();
		//allUsers.add(new User("admin"));
		//allUsers.add(new User("admin"));
		//this.current = null;
		//this.loggedIn = false;
	}
	/*
	 * @param user
	 * @return boolean by checking users in the list
	 * 
	 */
	
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
	/*
	 * @param user
	 * 
	 */
	public  void addUser(User user) {
		allUsers.add(user);
	}
	
	/*
	 * @param idx
	 */
	public void deleteUser(int idx) {
		allUsers.remove(idx);
	}
	
	
	/*
	 * @return a list of users
	 */
	public  ArrayList<User> getList(){
		return allUsers;
	}
	
	/*
	 * @param user
	 * @return user  
	 */
	public User getUser(User user) {
		for(int i = 0; i < allUsers.size(); i++) {
			if(allUsers.get(i).equals(user)) {
				return allUsers.get(i);
			}
		}
		return null;
	}
	
	/*
	 * @return current user
	 */
	public User getCurrentUser() {
		return allUsers.get(currUserIdx);
	}
	
	
	/*
	 * @param present 
	 */
	public void setCurrent(User present) {
		for(int i = 0; i < allUsers.size(); i++) {
			
			if(allUsers.get(i).getUsername().equals(present.getUsername())) {
				
				this.currUserIdx = i;
			}
		}
		
		this.present = present;
	}
	
	/*
	 * @return string of user
	 */
	
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
	
	/*
	 * @return a list of user
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static listUser read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		listUser userList = (listUser) ois.readObject();
			ois.close();
		return userList;
		
	}
	
	/*
	 * @param userlist
	 * @throws IOException
	 */
	public static void write(listUser userlist) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(userlist);
		oos.close();
	}
	
	
	
	

}