package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class photoDisplay {
	
	@FXML
	public ListView<String> listView;
	
	@FXML
	public ImageView photoDisplay;
	
	@FXML
	public Button logoutButton, backButton, saveCaptionButton, addTagButton, removeTagButton;
	
	@FXML
	public TextField caption, tagName, tagValue;
	
	@FXML
	public Text photoDate;

	public static listUser userlist = Photos.driver;
	public static ArrayList<Tag> allTags = new ArrayList<>();
	public static ArrayList<String> tagPair = new ArrayList<>();
	public ObservableList<String> obsList;
	public static ArrayList<Photo> allPhotos = new ArrayList<Photo>();
	public static Photo photo; 
	public int currIndex;
	public int previousIndex;
	public int nextIndex = 0;
	
	public void start(Stage app_stage) {
		System.out.println(userlist.getCurrentUser().getCurrentAlbum());
		System.out.println(userlist.getCurrentUser().getCurrentAlbum().getPhoto());
		app_stage.setTitle(userlist.getCurrentUser().getCurrentAlbum().getPhoto().getName() + " ");
		
		//update();
		Photo photo = userlist.getCurrentUser().getCurrentAlbum().getPhoto();
//		File file = photo.getImg();
//		Image image = new Image(file.toURI().toString());
		photoDisplay.setImage(photo.getImage());

		if(!allTags.isEmpty()) {
			listView.getSelectionModel().select(0); //select first user
		}
	}
	
	
	@FXML 
	public void addTag(ActionEvent event) {
		String tagname = tagName.getText().trim();
		String value = tagValue.getText().trim();
		Tag tag = (tagname.isEmpty() || value.isEmpty()) ? null : new Tag(tagname, value);
		if (tag == null) {
			alert("Not a valid tag.");
			return;
		}
		userlist.getCurrentUser().getCurrentAlbum().getPhoto().addNewTag(tag.name, tag.value);
		
		displayTags();
		//NEEDS IMPLEMENTATION
		//update
		//listUser.save(userlist);
	}
	
	public void displayTags() {

		if (photo != null) {
			Image photoImg = new Image(photo.getImg().toURI().toString());
			photoDisplay.setImage(photoImg);
			tagPair.clear();
			
			ArrayList<Tag> temp = userlist.getCurrentUser().getCurrentAlbum().getPhoto().getTags();
			for (int i = 0; i < temp.size(); i++) {
				Tag tempTag = temp.get(i);
				tagPair.add("Name - " + tempTag.name + ", Value - " + tempTag.value);
			}
			
			obsList = FXCollections.observableArrayList(tagPair);
			listView.setItems(obsList);
			listView.refresh();
			//System.out.println(allTags.toString());
		}
		
	}
	
	public void displayDate() {
		photoDate.setText("Date: " + userlist.getCurrentUser().getCurrentAlbum().getPhoto().getDate());
	}
	
	//Change
	@FXML 
	public void deleteTag(ActionEvent event) {
		int idx = listView.getSelectionModel().getSelectedIndex();
		
		ArrayList<Tag> allTags = userlist.getCurrentUser().getCurrentAlbum().getPhoto().getTags();
		userlist.getCurrentUser().getCurrentAlbum().getPhoto().deleteTag(allTags.get(idx).name, allTags.get(idx).value);
		
		//NEEDS IMPLEMENTATION
		//update();
		//listUser.save(userlist);
	}
	
	
	//NEEDS IMPLEMENTATION
	@FXML
	public void nextPhoto(ActionEvent event) {
		
		ArrayList<Photo> allPhotos = userlist.getCurrentUser().getCurrentAlbum().getPhotos();
		int previousIndex = allPhotos.size()-1;
		if(currIndex + 1 > previousIndex) {
			return;
		}else {
			currIndex++;
			File file;
			Photo photo = allPhotos.get(currIndex);
			if(photo != null) {
				file = photo.getImg();
				Image image = new Image(file.toURI().toString());
				photoDisplay.setImage(image);
			}
		}
		
		
	}
	
	@FXML
	public void previousPhoto(ActionEvent event) {
		ArrayList<Photo> allPhotos = userlist.getCurrentUser().getCurrentAlbum().getPhotos();
		if(currIndex-1 < nextIndex) {
			return;
		}
		else {
			currIndex--;
			File file;
			Photo photo = allPhotos.get(currIndex);
			if(photo != null) {
				file = photo.getImg();
				Image image = new Image(file.toURI().toString());
				photoDisplay.setImage(image);
				
			}
		}
	}
	
	
	public void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AlbumDetails.fxml"));
		Parent sceneManager = (Parent) fxmlLoader.load();
		albumController photoViewController = fxmlLoader.getController();
		Scene adminScene = new Scene(sceneManager);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		photoViewController.start();
		appStage.setScene(adminScene);
		appStage.show();
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Alert confirmation = ConfirmationAlert("Logout");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
			Parent manager = (Parent) loader.load();
			Scene adminScene = new Scene(manager);
			Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
			appStage.setScene(adminScene);
			appStage.show();
			System.out.println("Logged Out Successfully.");
		}
	}
	
	public Alert ConfirmationAlert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Operation: "+ function);
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase());

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
	
	public void alert(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Tag Error");
		alert.setContentText(error);
		alert.showAndWait();
		return;
	}
	
	@FXML
	public void editCaption(ActionEvent event) throws IOException{
		String newCaption = caption.getText().trim();
		Alert confirm = ConfirmationAlert("Change Photo Caption.");
		if (confirm.showAndWait().get() == ButtonType.YES) {
			photo.setCaption(newCaption);
			//NEEDS IMPLEMENTATION
			//photo.save(photo);
		}
		
//		String caption = captionPhoto.getText().trim();
//		if(caption == null) {
//			errorAlert("please enter a valid caption");
//		}
//		else if (caption == photo.getCaption()) {
//			errorAlert("please enter a different caption to re-caption");
//	}
//		else { 
//			photo.setCaption(caption);
//			listUser.write(userlist);
//		}
		
	}
}