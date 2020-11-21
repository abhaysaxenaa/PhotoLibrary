package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
	
	private static final long serialVersionUID = 42L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	private String name;
	private ArrayList<Photo> allPhotos;
	public int currPhotoIdx;
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
	public void setPhoto(Photo photo) {
		this.currPhotoIdx = allPhotos.indexOf(photo);
	}
	public Photo getPhoto() {
		return allPhotos.get(currPhotoIdx);
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
	public void remove(int index) {
		allPhotos.remove(index);
		
	}
	public String toString() {
		if(name == null) {
			return "There are no albums";
			}
		
		
		return name;
	}
	public ArrayList<Photo> getPhotos(){
		return allPhotos;
	}
	
	

	
}