package controller;

import java.io.IOException;
import java.security.AccessController;
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
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.listUser;

public class userController {
	
	@FXML 
	public ListView<Album> listView;
	
	@FXML 
	public Button addAblum, deleteAlbum, renameAlbum, openAlbum, searchPhotos, logout;
	
	@FXML 
	public TextField albumName;
	
	
	public ArrayList<Album> allAlbums = new ArrayList<Album>();
	public User user ;
	public ArrayList<String> allUsers = new ArrayList<String>();
	public Album album;
	public listUser userlist = Photos.driver;
	public String username;
	//public ArrayList<String> albumNames = new ArrayList<String>(); //USED FOR PRINTING OBSERVABLE LIST
	
	public ObservableList<Album> obsList =  FXCollections.observableArrayList(); 

	public void bootUp() {
			
		 
		update();
		
		/*allAlbums.clear();
		for (int i = 0; i < allAlbums.size(); i++) {
			albumNames.add(userlist.getCurrentUser().getAlbums().get(i).getName());
		}
		obsList = FXCollections.observableArrayList(albumNames);*/
		
		obsList = FXCollections.observableArrayList(allAlbums);
		if(!allAlbums.isEmpty()) {
			listView.getSelectionModel().select(0);
			
		}
//			
			
	}
	
	
	
	@FXML
	public void addAlbum(ActionEvent event) throws IOException, ClassNotFoundException{
		String newAlbum = albumName.getText().trim();
		Album album = new Album(newAlbum);
		
		if(newAlbum.isEmpty()) {
			errorAlert("Empty Album Name");
		}
		
		else if(user.checkAlbumInList(newAlbum) == true) {
			errorAlert("Album name already exists");
		}
		
		else {
			userlist.getCurrentUser().createAlbum(album);
			System.out.println(userlist);
			update();
			//user.createAlbum(album);
			//obsList.add(album);
			//listView.setItems(obsList);
			//System.out.println(userlist);
			//listUser.write(userlist);
			//User.write(userlist.getCurrentUser());
			albumName.clear();
			for(int i = 0; i< allAlbums.size(); i++) {
				String name =allAlbums.get(i).getName();
			System.out.println("allAlbums: "+ name);}
			System.out.println("userlist: " + userlist);
		//	listUser.write(userlist);
			System.out.println(user.getUsername());
		}
		listUser.write(userlist);
	}
	
	
	
	//CHANGE: Error doesn't pop up.
	@FXML
	public void renameAlbum(ActionEvent event) throws IOException{
		String newName = albumName.getText().trim();
		Album selectedAlbum = listView.getSelectionModel().getSelectedItem();
		
		
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			if(newName == null) {
				errorAlert("Empty Album Name");
			}
			else if(newName.equals(selectedAlbum.getName())) {
				errorAlert("Name already exists");
			} else if (userlist.getCurrentUser().checkAlbumInList(newName)){
				errorAlert("Another album with that name exists. Please try another name.");
			}
			else {
				 
				selectedAlbum.rename(newName);
				User.write(userlist.getCurrentUser());
				update();
				listView.refresh();
				albumName.clear();
				try {
					listUser.write(userlist);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	
	
	@FXML 
	public void deleteAlbum(ActionEvent event) throws IOException{
		int index = listView.getSelectionModel().getSelectedIndex();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			
			user.deleteAlbum(index);
			update();
			User.write(userlist.getCurrentUser());
			listView.getItems().remove(album);
			try {
				listUser.write(userlist);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@FXML
	public void openAlbum(ActionEvent event) throws IOException{
		//Album selectedAlbum = listView.getSelectionModel().getSelectedItem();
		albumController.album = listView.getSelectionModel().getSelectedItem();
//		albumController.user = user;
//		albumController.allAlbums = allAlbums;
		System.out.println(user.getCurrentAlbum());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumDetails.fxml"));
		Parent parent = (Parent) loader.load();
		albumController controller = loader.getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.start();
		appStage.setScene(scene);
		appStage.show();
	}
	
	@FXML
	public void searchPhotos(ActionEvent event) throws IOException{
		

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Search.fxml"));
		Parent parent = (Parent) loader.load();
		searchController controller = loader.<searchController>getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.start();
		appStage.setScene(scene);
		appStage.show();
	}
	
	
	@FXML
	public void logout(ActionEvent event) throws IOException{
		listUser.write(userlist);
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
			Parent manager = (Parent) loader.load();
			Scene adminScene = new Scene(manager);
			Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
			appStage.setScene(adminScene);
			appStage.show();
		}
	}
	
	
	public void update() {
		user = userlist.getCurrentUser();
		
		allAlbums.clear();
		for (int i = 0; i < user.getAlbums().size(); i++) {
			allAlbums.add(user.getAlbums().get(i));
		}
		obsList = FXCollections.observableArrayList(allAlbums);
		listView.setItems(obsList);
		listView.refresh();
	}
	
		public Alert ConfirmationAlert(String function) {
			//MODIFIED: Added a more specific confirmation dialog.
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Confirmation Dialog");
			confirmation.setHeaderText(" Confirmation");
			confirmation.setContentText(function);

			confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

			return confirmation;
		}
	
		public Alert errorAlert(String function) {
			//MODIFIED: Added a more specific confirmation dialog.
			Alert confirmation = new Alert(AlertType.ERROR);
			confirmation.setTitle("Confirmation Dialog");
			confirmation.setHeaderText("Operation: "+ function + "ing a song.");
			confirmation.setContentText("Are you sure you want to " + function.toLowerCase() + " this song?");

			confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

			return confirmation;
		}

}