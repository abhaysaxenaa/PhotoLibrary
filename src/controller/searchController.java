
//Abhay Saxena (ans192) & GVS Karthik (vg311)

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


/*
 * @author Venkata Sai Karthik Gandrath
 * @author Abhay Saxena 
 * 
 * 
 */

public class searchController {
	
	@FXML
	public Button searchButton, createNewAlbumButton, backButton;
	
	@FXML
	TextField  tagName, tagValue;
	
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
	public ObservableList<Photo> obsList;
	

	public void start() {

		
	}
	
	/*
	 * @param event
	 */
	@FXML
	public void search(ActionEvent event) {
//		get tag from UI
		String name = tagName.getText().trim();
		String value = tagValue.getText().trim();
		Tag temp = new Tag(name,value);
		ArrayList<Photo> selectedPhotos = new ArrayList<>();
		ArrayList<Album> albums = userlist.getCurrentUser().getAlbums();
		for(Album a: albums) {
			for(Photo p: a.getPhotos()) {
				for(Tag t: p.getTags()) {
					if(t.getName().equals(name) && t.getValue().equals(value)) {
						selectedPhotos.add(p);
						break;
					}
				}
			}
		}
		
		allPhotos = selectedPhotos;
		obsList = FXCollections.observableArrayList(selectedPhotos);
		searchResults.setItems(obsList);
		searchResults.refresh();
		
	}	
		/*
		 * @param event 
		 * @throws IOException
		 */
	@FXML
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
	
	/*
	 * @param event 
	 * @throws IOException
	 */
	@FXML
	public void createNewAlbum(ActionEvent event) throws IOException {
		if(searchResults.getItems().isEmpty()) {
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
			   dialog.setResultConverter(buttonType -> {
				   user = userlist.getCurrentUser();
				   if (buttonType == buttonTypeOk) {
					   if (albumName.getText().trim().isEmpty()) {
							 Alert alert =  errorAlert("Invalid Album Name");

							   Optional<ButtonType> buttonClicked=alert.showAndWait();
							   if (buttonClicked.get()==ButtonType.OK) {
								   alert.close();
							   }
							   else {
								   alert.close();
							   }
							   return null;
						   }
						   else if(user.checkAlbumInList(albumName.getText().trim()) == true){ 
							   errorAlert(" Album Name already exists");
						   }
						   return albumName.getText().trim();
				   }
				   return null;
			   });
			
			   
			   Optional<String> result = dialog.showAndWait();
			   
			   if (result.isPresent()) {
				   Album albumFromSearch = new Album(result.get());
				   
				  
				   for(Photo photo : allPhotos) {
					   albumFromSearch.addPhoto(photo);
				   }
				   user.getAlbums().add(albumFromSearch);
				   userlist.write(userlist);
			   }
		}
	}
	
	
	/*
	 * @param function
	 */
	public Alert errorAlert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.ERROR);
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Operation: Create New Album" );
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase() );

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
	
	/*
	 * @param funtion
	 */
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