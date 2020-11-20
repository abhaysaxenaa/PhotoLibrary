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
	public User user;
	public ArrayList<String> allUsers = new ArrayList<String>();
	public Album album;
	public listUser userlist = Photos.driver;
	public String username;
	
	public ObservableList<Album> obsList =  FXCollections.observableArrayList(); 

	public void bootUp() {
			
		 
		//update();
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
		else if(user.checkAlbumInList(newAlbum)== false) {
			errorAlert(" Album Name already exists");
		}
		else {
			//obsList.add(newAlbum);
			System.out.println("new:" + newAlbum);
			
			allAlbums.add(album);
			//user.createAlbum(album);
			obsList.add(album);
			listView.setItems(obsList);
			listUser.write(userlist);
			for(int i = 0; i< allAlbums.size(); i++) {
				String name =allAlbums.get(i).getName();
			System.out.println("allAlbums: "+ name);}
			System.out.println("userlist: " + userlist);
		}
	}
	
	
	
	
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
			}
			else {
				Alert confirmation2 = ConfirmationAlert("Are you Sure");
				if (confirmation2.showAndWait().get() == ButtonType.YES) {
				selectedAlbum.rename(newName);
			}
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
			obsList.remove(album);
			user.deleteAlbum(index);
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
		Album selectedAlbum = listView.getSelectionModel().getSelectedItem();
		
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
		Album selectedAlbum = listView.getSelectionModel().getSelectedItem();

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
		user.setText(username + "'s Album List:");
		// tfName.setText(listview.getSelectionModel().getSelectedItem());
		user = userlist.getUser(username);
		
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
			confirmation.setHeaderText("Logout Confirmation");
			confirmation.setContentText("Are you sure you want to logout" );

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
