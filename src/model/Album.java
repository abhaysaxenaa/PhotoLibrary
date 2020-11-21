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
	public Photo currPhoto;
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

	public Photo getPhoto() {
		return currPhoto;
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
	public ArrayList<Photo> getPhotos(){
		return allPhotos;
	}
	
	public static void save(Album album) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(album);
		oos.close();
	}
	
	public static listUser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		listUser userList = (listUser) ois.readObject();
		ois.close();
		return userList;
	}
	
}