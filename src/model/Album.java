package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
	
	private static final long serialVersionUID = 42L;
	
	private String name;
	private ArrayList<Photo> allPhotos;
//	private ArrayList<Album> allAlbums;
	
	public Album(String name) {
		this.name = name;
		allPhotos = new ArrayList<Photo>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Photo> getPhoto(){
		return this.allPhotos;
	}
	
	public int getPhotoCount() {
		return this.allPhotos.size();
	}
	
	public boolean equals(Album other) {
		return name.equals(other.name);
	}
	public void rename(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo photo) {
		allPhotos.add(photo);
	}
	public void remove(Photo photo) {
		allPhotos.remove(photo);
		
	}
	public ArrayList<Photo> getPhotos(){
		return allPhotos;
	}
	
}
