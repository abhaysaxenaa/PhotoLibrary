package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.security.auth.callback.Callback;

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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.listUser;

public class searchController {
	
	@FXML
	public Button searchButton, addTagButton, removeTagButton, createNewAlbumButton, backButton;
	
	@FXML
	TextField  tagName, tagValue;
	
	@FXML
	ListView<String> tags;
	
	@FXML
	ListView<Photo> searchResults;
	
	@FXML
	DatePicker fromDate, toDate;
	
	public Album album;
	User user;
	listUser userlist = Photos.driver;
	
	public ArrayList<Photo> allPhotos = new ArrayList<Photo>();
	public ArrayList<Tag> allTags = new ArrayList<Tag>();
	public ArrayList<String> tagPairs = new ArrayList<String>();
	public ObservableList<String> obsShowTags;
	

	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	
	@FXML
	public void search(ActionEvent event) {
		allPhotos= photosInAlbum();
		LocalDate from, to;
		
		
	}
	@FXML
	public void addTag(ActionEvent event) throws IOException {
		if (tagName.getText().isEmpty() || tagValue.getText().isEmpty()) {
			alert("One of the Tag property is missing.");
			return;
		} else {
			//Basically the update method.
			Tag newTag = new Tag(tagName.getText().trim(), tagValue.getText().trim());
			tagName.clear();
			tagValue.clear();
			allTags.add(newTag);
			
			tagPairs.clear();
			for(Tag tempTag : allTags) {
				tagPairs.add("Tag Name - " + tempTag.name +    ", Tag Value - " + tempTag.value);
			}
			obsShowTags = FXCollections.observableArrayList(tagPairs);
			tags.setItems(obsShowTags);
		}
	}
	
	@FXML
	public void removeTag(ActionEvent event) {
		int idx = tags.getSelectionModel().getSelectedIndex();
		if (idx < 0) {
			alert("Cannot remove a non-existent tag!");
		} else {
			errorAlert("Remove Tag");
			allTags.remove(idx);
			tagPairs.clear();
			for(Tag tempTag : allTags) {
				tagPairs.add("Tag Name - " + tempTag.name +    ", Tag Value - " + tempTag.value);
			}
			obsShowTags = FXCollections.observableArrayList(tagPairs);
			tags.setItems(obsShowTags);
		}
	}
	
public void back(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
		Parent parent = (Parent) loader.load();
		userController controller = loader.<userController>getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.bootUp();
		appStage.setScene(scene);
		appStage.show();
	}
	
	
	@FXML
	public void createNewAlbum(ActionEvent event) {
		/*if(searchResults.getItems().isEmpty()) {
			errorAlert("create a new album");
		}
		else {
			Dialog<String> dialog = new Dialog<>();
			   dialog.setTitle("Create a New Album from search results");
			   dialog.setHeaderText("Name for album created from search results ");
			   dialog.setResizable(true);
			   
			   Label albumnameLabel = new Label("Album Name: ");
			   TextField albumName = new TextField();
			   
			   GridPane grid = new GridPane();
			   grid.add(albumnameLabel, 1, 1);
			   grid.add(albumName, 2, 1);
			   
			   dialog.getDialogPane().setContent(grid);
			   
			   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
			   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			   
			   dialog.setResultConverter(new Callback<ButtonType, String>() { 
				   @Override
				   public String call(ButtonType b) {
					   if (b == buttonTypeOk) {
						   if (albumName.getText().trim().isEmpty()) {
							  errorAlert("Invalid Album Name");

							   Optional<ButtonType> buttonClicked=alert.showAndWait();
							   if (buttonClicked.get()==ButtonType.OK) {
								   alert.close();
							   }
							   else {
								   alert.close();
							   }
							   return null;
						   }
						   else if(user.checkAlbumInList(albumName == true)){ 
							   errorAlert(" Album Name already exists");
						   }
						   return albumName.getText().trim();
					   }
					   return null;
				   }
				
			   });
			   
			   Optional<String> result = dialog.showAndWait();
			   
			   if (result.isPresent()) {
				   Album albumFromSearch = new Album(result.get());
				   user.getAlbums().add(albumFromSearch);
				  
				   for(Photo photo : allPhotos) {
					   albumFromSearch.addPhoto(photo);
				   }
				   listUser.write(userlist);
			   }
		}*/
	}
	
	public ArrayList<Photo> photosInAlbum(){
		ArrayList<Album> allAlbums = user.getAlbums();
		int i = 0;
		while(i < allAlbums.size()) {
			
			for(Photo photo : allAlbums.get(i).getPhotos()) {
				if(!allPhotos.contains(photo)) {
					allPhotos.add(photo);
					
				}
			}
			i++;
			
		}
		return allPhotos;
	}
	
	public Alert errorAlert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.ERROR);
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Operation: Create New Album" );
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase() );

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
	
	public Alert alert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.ERROR);
		confirmation.setTitle("Error Dialog");
		confirmation.setHeaderText("Tag Error" );
		confirmation.setContentText("Please enter a valid tag!");

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
}