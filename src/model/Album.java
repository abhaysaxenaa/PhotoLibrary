
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

public class Album implements Serializable{
	
	private static final long serialVersionUID = 42L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	private String name;
	private ArrayList<Photo> allPhotos;
	public int currPhotoIdx;
//	private ArrayList<Album> allAlbums;
	/*
	 * constructor
	 * @param name
	 * 
	 */
	public Album(String name) {
		this.name = name;
		allPhotos = new ArrayList<Photo>();
	}
	
	/*
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * @param photo
	 */
	public void setPhoto(Photo photo) {
		this.currPhotoIdx = allPhotos.indexOf(photo);
	}
	
	/*
	 * @param name
	 */
	
	public void rename(String name) {
		this.name = name;
	}
	
	/*
	 * @return the name of the album
	 * 
	 */
	public String getName() {
		return this.name;
	}
	
	/*
	 * @return current photo
	 * 
	 */
	
	public Photo getPhoto() {
		return allPhotos.get(currPhotoIdx);
	}
	/*
	 * @return the count of all photos
	 */
	
	public int getPhotoCount() {
		return this.allPhotos.size();
	}
	
	/*
	 * 
	 * @param other 
	 * @return boolean if album names equal to other
	 */
	
	public boolean equals(Album other) {
		return name.equals(other.name);
	}
	/*
	 * 
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		allPhotos.add(photo);
	}
	/*
	 * param index
	 * 
	 */
	
	public void remove(int index) {
		allPhotos.remove(index);
		
	}
	/*
	 * @return name of the album
	 * 
	 */
	
	public String toString() {
		if(name == null) {
			return "There are no albums";
			}
		return name;
	}
	/*
	 * @return all photos
	 */
	
	public ArrayList<Photo> getPhotos(){
		return allPhotos;
	}
	
	

	
}