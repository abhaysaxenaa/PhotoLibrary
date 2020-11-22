package model;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.scene.image.Image;


public class Photo implements Serializable{
	
	private static final long serialVersionUID = -8485172240653089216L;
	private SerializableImage image;
	private String caption;
	private ArrayList<Tag> tags = new ArrayList<>();
	private Calendar date;
	
	//Not sure if these need to be used
	public File photoLocation;
	public String photoFilePath;
	public Date currDate;
	public boolean isStockPhoto = false;
	public String photoname;
	
	public Photo(SerializableImage image, Calendar date) {
		this.caption = "";
		this.tags = new ArrayList<Tag>();
		this.date = date;
		this.date.set(Calendar.MILLISECOND, 0);

	}
	
	public Photo(Image image) {
		this.caption = "";
		this.image = new SerializableImage(image);
		this.tags = new ArrayList<Tag>();
		
	}
	
	//Setter methods
	public void setName(String name) {
		this.photoname = name;
	}
	public void setCaption( String caption) {
		this.caption = caption;
	}
	public void setImg(File photoLocation) {
		this.photoLocation = photoLocation;
	}
	
	
	//Getter methods
	public String getName() {
		return photoname;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public Image getImage() {
		return image.getImage();
	}
	
	public ArrayList<Tag> getTags(){
		if(this.tags == null) this.tags = new ArrayList<>();
		return this.tags;
	}
	
	public Calendar getDate() {
		return date;
	}
	
	
	//New methods
	public void addNewTag(String name, String value) {
		if(this.tags == null) this.tags = new ArrayList<>();
		this.tags.add(new Tag(name, value));
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
	
	@Override
	public String toString() {
		if(photoname == null) {
			return "There are no photos";
			}
		
		
		return photoname;
	}
	
}