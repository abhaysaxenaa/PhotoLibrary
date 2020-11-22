
//Abhay Saxena (ans192) & GVS Karthik (vg311)

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

/*
 * @author Venkata Sai Karthik Gandrath
 * @author Abhay Saxena 
 * 
 * 
 */

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
	
	public ObservableList<Album> obsList =  FXCollections.observableArrayList(); 

	public void bootUp() {
			
		 
		initialize();
		
		
		
		obsList = FXCollections.observableArrayList(allAlbums);
		if(!allAlbums.isEmpty()) {
			listView.getSelectionModel().select(0);
			
		}
		
			
	}
	
	
	/*
	 * @param event 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
			
			initialize();
			
			albumName.clear();
			for(int i = 0; i< allAlbums.size(); i++) {
				String name =allAlbums.get(i).getName();
			
		}
		listUser.write(userlist);
		}
	}
	
	
	
	/*
	 * @param event 
	 * @throws IOException
	 */
	@FXML
	public void renameAlbum(ActionEvent event) throws IOException{
		String newName = albumName.getText().trim();
		Album selectedAlbum = listView.getSelectionModel().getSelectedItem();
		
		
		Alert confirmation = ConfirmationAlert("Are you sure you want to rename this album?");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			
			
			if(newName == null || newName.isEmpty() || newName.isBlank()) {
				errorAlert("Empty Album Name");
			}
			else if(newName.equals(selectedAlbum.getName()) || userlist.getCurrentUser().checkAlbumInList(newName)) {
				errorAlert("Invalid Album name.");
			}
			else {
				 
				selectedAlbum.rename(newName);
				User.write(userlist.getCurrentUser());
				initialize();
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
	
	
	
	/*
	 * @param event 
	 * @throws IOException
	 */
	@FXML 
	public void deleteAlbum(ActionEvent event) throws IOException{
		int index = listView.getSelectionModel().getSelectedIndex();
		Alert confirmation = ConfirmationAlert("Are you sure you want to delete this album?");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			
			user.deleteAlbum(index);
			initialize();
			User.write(userlist.getCurrentUser());
			listView.getItems().remove(album);
			try {
				listUser.write(userlist);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * @param event 
	 * @throws IOException
	 */
	@FXML
	public void openAlbum(ActionEvent event) throws IOException{
		if (allAlbums.size() > 0) {
			albumController.album = listView.getSelectionModel().getSelectedItem();
			albumController.user = user;
			albumController.allAlbums = allAlbums;
			
			userlist.getCurrentUser().setAlbum(listView.getSelectionModel().getSelectedItem());
			
		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AlbumDetails.fxml"));
			Parent parent = (Parent) loader.load();
			albumController controller = loader.getController();
			Scene scene = new Scene(parent);
			Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			controller.start();
			appStage.setScene(scene);
			appStage.show();
		} else {
			errorAlert("Can't open null.");
		}
	}
	
	/*
	 * @param event 
	 * @throws IOException
	 */
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
	
	/*
	 * @param event 
	 * @throws IOException
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException{
		listUser.write(userlist);
		Alert confirmation = ConfirmationAlert("Logout?");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
			Parent manager = (Parent) loader.load();
			Scene adminScene = new Scene(manager);
			Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
			appStage.setScene(adminScene);
			appStage.show();
		}
	}
	
	
	public void initialize() {
		user = userlist.getCurrentUser();
		
		allAlbums.clear();
		for (int i = 0; i < user.getAlbums().size(); i++) {
			allAlbums.add(user.getAlbums().get(i));
		}
		obsList = FXCollections.observableArrayList(allAlbums);
		listView.setItems(obsList);
		listView.refresh();
	}
	
	/*
	 * @param function
	 */
	public Alert ConfirmationAlert(String function) {
			
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Confirmation Dialog");
			confirmation.setHeaderText(" Confirmation");
			confirmation.setContentText(function);

			confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

			return confirmation;
		}
	
	/*
	 * @param error
	 */
	public void errorAlert(String error) {
			   Alert alert =  new Alert(AlertType.ERROR);
			   alert.setTitle("Error");
			   alert.setHeaderText("Error");
			   String content = error;
			   alert.setContentText(content);
			   alert.showAndWait();
		}

}