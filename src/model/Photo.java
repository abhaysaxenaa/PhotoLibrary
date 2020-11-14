package model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javafx.scene.image.Image;


public class Photo implements Serializable{
	
	private SerializableImage image;
	private String caption;
	private ArrayList<Tag>tags;
	private Calendar date;
	
	
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
	
	
	

}
