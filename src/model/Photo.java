package model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.scene.image.Image;


public class Photo implements Serializable{
	
	private SerializableImage image;
	private String caption;
	private ArrayList<Tag>tags;
	private Calendar date;
	
	//Not sure if these need to be used
	public File photoLocation;
	public String photoFilePath;
	public Date currDate;
	public boolean isStockPhoto = false;
	
	
	public Photo(SerializableImage image, Calendar date) {
		this.caption = "";
		tags = new ArrayList<Tag>();
		this.date = date;
		this.date.set(Calendar.MILLISECOND, 0);

	}
	
	public Photo(Image image) {
		this.image = new SerializableImage(image);
		
	}
	public void setCaption( String caption) {
		this.caption = caption;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public Image getImage() {
		return image.getImage();
	}
	
	public ArrayList<Tag> getTags(){
		return tags;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	
	//New methods
	public void addNewTag(String name, String value) {
		tags.add(new Tag(name, value));
	}
	
	public void deleteTag(String name, String value) {
		for(int i = 0; i < tags.size(); i++) {
			Tag curr = tags.get(i);
			if(curr.name.toLowerCase().equals(name.toLowerCase()) && curr.value.toLowerCase().equals(value.toLowerCase())) {
				tags.remove(i);
			}
		}
	}
	
	public File getImg() {
		return this.photoLocation;
	}
	
	public void setImg(File photoLocation) {
		this.photoLocation = photoLocation;
	}

}