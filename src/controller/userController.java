package controller;

import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;

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
	private Button addAblum, deleteAlbum, renameAlbum, openAlbum, searchPhotos, logout;
	
	@FXML 
	private TextField albumName;
	
	
	private ArrayList<Album> allAlbums = new ArrayList<Album>();
	private User user;
	private ArrayList<User> allUsers;
	private Album album;
	private listUser userlist;
	
	private ObservableList<Album> obsList =  FXCollections.observableArrayList(); 

	public void start(User user) {
			this.user = user;
			listView.setItems(FXCollections.observableArrayList(user.getAlbums()));
			listView.getSelectionModel().select(0);
			
	}
	
	
	
	@FXML
	public void addAlbum(ActionEvent event) throws IOException, ClassNotFoundException{
		String newAlbum = albumName.getText().trim();
		Album album = new Album(newAlbum);
		
		if(newAlbum.isEmpty()) {
			errorAlert("Empty Album Name");
		}
		else if(user.checkAlbumInList(album)) {
			errorAlert(" Album Name already exists");
		}
		else {
			obsList.add(album);
			allAlbums.add(album);
			listUser.write(userlist);
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
		Album album = listView.getSelectionModel().getSelectedItem();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			obsList.remove(album);
			user.deleteAlbum(album);
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
		controller.start(user,selectedAlbum);
		appStage.setScene(scene);
		appStage.show();
	}
	
	@FXML
	public void searchAlbum(ActionEvent event) throws IOException{
		Album selectedAlbum = listView.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Search.fxml"));
		Parent parent = (Parent) loader.load();
		searchController controller = loader.<searchController>getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.start(selectedAlbum);
		appStage.setScene(scene);
		appStage.show();
	}
	
	
	@FXML
	public void logout(ActionEvent event) throws IOException{
		
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
	
	
	
		public Alert ConfirmationAlert(String function) {
			//MODIFIED: Added a more specific confirmation dialog.
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Confirmation Dialog");
			confirmation.setHeaderText("Operation: "+ function + "ing a song.");
			confirmation.setContentText("Are you sure you want to " + function.toLowerCase() + " this song?");

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
