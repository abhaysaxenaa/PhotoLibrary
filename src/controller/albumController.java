package controller;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.*;

public class albumController {
	
	@FXML
	public ListView<Photo> listView;
	
	@FXML
	TextField addPhoto, captionPhoto, copyPhoto, movePhoto;
	
	@FXML 
	ImageView photoDisplay;
	
	@FXML
	Button addButton, removebutton, captionButton, moveButton, copyButton, addTagButton, deletetagButton, backButton, logoutButton;
	
	public ObservableList<Photo> obsList;
	public ArrayList<Photo> allPhotos;
	public User user;
	public listUser userlist;
	public static Album album;
	private Photo photo;
	
	
	public void start(User user, Album album) {
//		this.user = user;
//		this.album = album;
//		listView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
//			@Override
//			public ListCell<Photo> call(ListView<Photo> p) {
//				return new PhotoCell();
//			}
//		});
		
	}
	
	public void fileChooser() {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        chooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	}
	
	
	
	public void removePhoto(ActionEvent event) throws ClassNotFoundException, IOException {
		Photo photo = listView.getSelectionModel().getSelectedItem();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			album.remove(photo);
			obsList.remove(photo);
			album.remove(photo);
		// implement 	reload();
		listUser.write(userlist);
		}
	}
	
	@FXML
	public void captionPhoto(ActionEvent event) throws ClassNotFoundException, IOException {
		String caption = captionPhoto.getText().trim();
		if(caption == null) {
			errorAlert("please enter a valid caption");
		}
		else if (caption == photo.getCaption()) {
			errorAlert("please enter a different caption to re-caption");
		}
		else { 
			photo.setCaption(caption);
			listUser.write(userlist);
		}
	}
	
	
	public void back(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumDetails.fxml"));
		Parent parent = (Parent) loader.load();
		albumController controller = loader.<albumController>getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.start(user, album);
		appStage.setScene(scene);
		appStage.show();
	}

	public void logout(ActionEvent event) throws IOException {
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
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase() + " this Photo?");

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}
	
	public void errorAlert(String error) {
		   Alert alert =  new Alert(AlertType.ERROR);
		   alert.setTitle("Error");
		   alert.setHeaderText("Error");
		   String content = error;
		   alert.setContentText(content);
		   alert.showAndWait();
	}
	

}
