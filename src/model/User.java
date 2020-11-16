package model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String username;
	private ArrayList<Album> allAlbums;
	
	public User(String username) {
		this.username = username;
		allAlbums = new ArrayList<Album>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Album> getAlbums(){
		return allAlbums;
	}
	
	public String toString() {
		return this.username.toLowerCase();
	}
	
	public boolean equals(User other) {
		return this.username.equals(other.username);
	}
	
	//Similar methods to Github
	public void createAlbum(Album newAlbum) {
	allAlbums.add(newAlbum);
	}
	
	public void deleteAlbum(int idx) {
		allAlbums.remove(idx);
	}
	
	public boolean checkAlbumInList(Album album) {
		for (int i = 0; i < allAlbums.size(); i++) {
			if (album.equals(allAlbums.get(i))) {
				return true;
				
				//Set new current user
				//Set logged in to true
			}
		}
		return false;
	}
	//public ArrayList<Photo> getSortedPhotos(){};
	
	//Storing and Loading
}
