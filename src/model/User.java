package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static final long serialVersionUID = 42L;
	public String username;
	public ArrayList<Album> allAlbums;
	public User user;
	
	public static final String storeDir = "data";
	public static final String storeFile = "dat.dat";
	
	public User(String username) {
		this.username = username;
		allAlbums = new ArrayList<Album>();
	}
	public User getUserName() {
		return user;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ArrayList<Album> getAlbums(){
		return allAlbums;
	}
	
	public String toString() {
		return this.username.toLowerCase();
	}
	
	public boolean checkUserInList(User other) {
		return this.username.equals(other.username);
	}
	
	//Similar methods to Github
	public void createAlbum(Album newAlbum) {
	allAlbums.add(newAlbum);
	}
	
	public void deleteAlbum(int index) {
		allAlbums.remove(index);
	}
	
	public void setAlbums(ArrayList<Album> allAlbums) {
		this.allAlbums = allAlbums;
	}
	public boolean checkAlbumInList(String album) {
		for (int i = 0; i < allAlbums.size(); i++) {
			if (album.equals(allAlbums.get(i).getName())) {
				return true;
				
				//Set new current user
				//Set logged in to true
			}
		}
		return false;
	}
	
	public static void write(User u) throws IOException {
		
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator + storeFile));
		o.writeObject(u);
		o.close();
	}
	
	public static User read() throws IOException, ClassNotFoundException{
		ObjectInputStream o = new ObjectInputStream(new FileInputStream(storeDir+File.separator + storeFile));
		User userList = (User)o.readObject();
		o.close();
		return userList;
	}
	//public ArrayList<Photo> getSortedPhotos(){};
	
	//Storing and Loading
}
