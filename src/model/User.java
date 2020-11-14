package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private String username;
	private ArrayList<Album> albums;
	
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	public String toString() {
		return this.username;
	}
	
	public boolean equals(User other) {
		return this.username.equals(other.username);
	}
}
