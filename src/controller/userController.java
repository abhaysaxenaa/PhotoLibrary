package controller;

import java.io.IOException;
import java.util.ArrayList;

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
import model.User;

public class userController {
	
	@FXML 
	public ListView<Album> listView;
	
	@FXML 
	private Button addAblum, deleteAlbum, renameAlbum, openAlbum, searchPhotos, logout;
	
	@FXML 
	private TextField albumName;
	
	private ArrayList<Album> albums = new ArrayList<Album>();
	private User user;
	private ArrayList<User> users;
	

	public void start(Stage mainStage) {
				
	}
	
	@FXML
	public void addAlbum(ActionEvent event) throws IOException{
		String newAlbum = albumName.getText().trim();
		
	}
	
	
	public void renameAlbum(ActionEvent event) throws IOException{
		String newName = albumName.getText().trim();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			if(newName != null) {
				if(newName.equals())
			}
		}
	}
	@FXML 
	public void deleteAlbum(ActionEvent event) throws IOException{
		Album album = listView.getSelectionModel().getSelectedItem();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			user.getAlbums().remove(album);
			listView.getItems().remove(album);
//implement save.
		}
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
	
	

}
