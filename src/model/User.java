
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

import app.Photos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
/*
 * @author Venkata Sai Karthik Gandrath
 * @author Abhay Saxena 
 * 
 * 
 */


public class User implements Serializable {

	private static final long serialVersionUID = 42L;
	public String username;
	public ArrayList<Album> allAlbums;
	public User user;
	public Album currAlbum;
	listUser userlist = Photos.driver;
	
	public static final String storeDir = "data";
	public static final String storeFile = "dat.dat";
	/*
	 * constructor 
	 * @param username 
	 */
	public User(String username) {
		this.username = username;
		allAlbums = new ArrayList<Album>();
	}
	
	
	/*
	 * @param username 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	/*
	 * @param al
	 */
	public void setAlbum(Album al) {
		this.currAlbum = al;
	}
	
	/*
	 * @param username 
	 */
	
	public void renameAlbum(String  username) {
		this.username = username;
	}
	
	/*
	 * @param allAlbums
	 */
	
	public void setAlbums(ArrayList<Album> allAlbums) {
		this.allAlbums = allAlbums;
	}
	
	/*
	 * @return allAlbums
	 */
	public ArrayList<Album> getAlbums(){
		return allAlbums;
	}
	
	/*
	 * @return current album
	 */
	public Album getCurrentAlbum() {
		return currAlbum;
	}
	
	/*
	 * @return user
	 */
	public User getUserName() {
		return user;
		
	}
	
	/*
	 * @return username 
	 */
	public String getUsername() {
		return username;
	}
	
	/*
	 * @return username
	 */
	public String toString() {
		return this.username.toLowerCase();
	}
	
	/*
	 * @boolean if user equals to another user
	 */
	public boolean checkUserInList(User other) {
		return this.username.equals(other.username);
	}
	
	/*
	 * @param newAlbum
	 */
	public void createAlbum(Album newAlbum) {
		
		if (allAlbums.contains(newAlbum)) {
			errorAlert(" Album Name already exists");
		}
		else {
			
			allAlbums.add(newAlbum);

		}
	}
	
	/*
	 * @param index
	 */
	public void deleteAlbum(int index) {
		if (allAlbums.size() > 0) {
			allAlbums.remove(index);
		}
		else{errorAlert("empty album list");}
				
	}

	/*
	 * @param album
	 */
	public boolean checkAlbumInList(String album) {
		
		for (int i = 0; i < allAlbums.size(); i++) {
			if (album.equals(allAlbums.get(i).getName())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * @return a list of user
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	
	public static User read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User user = (User) ois.readObject();
			ois.close();
		return user;
		
	}
	
	/*
	 * @param userlist
	 * @throws IOException
	 */
	public static void write(User user) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(user);
		oos.close();
	}

	/*
	 * @param function
	 */
	public Alert errorAlert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.ERROR);
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Operation: "+ function + "ing a song.");
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase() + " this song?");

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
}