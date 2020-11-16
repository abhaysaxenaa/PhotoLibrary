package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
	
	private String name;
	private ArrayList<Photo> photos;
	private ArrayList<Album> allAlbums;
	
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Photo> getPhoto(){
		return this.photos;
	}
	
	public int getPhotoCount() {
		return this.photos.size();
	}
	
	public boolean equals(Album other) {
		return name.equals(other.name);
	}
	public void rename(String name) {
		this.name = name;
	}
	
}
