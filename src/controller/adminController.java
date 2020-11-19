package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
import model.User;
import model.listUser;

public class adminController {
	@FXML private Button  createButton, deleteButton, listUserbutton, logoutButton;
	@FXML private TextField userName;
	@FXML private static ListView<User> listView;
	
	public static ArrayList<User> allUsers = new ArrayList();
	public static final String storeDir = "data";
	public static final String storeFile = "dat.dat";
	private listUser userlist;
	
	
	public ObservableList<User> obsList; 
	
	public void bootup() {
//			listView.setItems(obsList);
			listView.refresh();
			listView.getSelectionModel().select(0);
			listView.setVisible(false);	
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
	
	
	
	@FXML
	public void deleteUser(ActionEvent event) throws IOException, ClassNotFoundException{
		User user = listView.getSelectionModel().getSelectedItem();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			obsList.remove(user);
		//	listView.getItems().remove(user);
			listView.refresh();
			listUser.write(userlist);
			
		}
	}
	
	
	
	
	@FXML
	public void addUser(ActionEvent event) throws IOException, ClassNotFoundException{
		
		String username = userName.getText().toLowerCase();
		User newUser = new User(username);
		if (username == null  || userlist.checkUserInList(username) == true || username == "admin") {
			errorAlert("Pleae enter a valid UserName");
			return;
		} else {
			obsList.add(newUser);
			allUsers.add(newUser);
			
			try {
				listUser.write(userlist);
			}catch (Exception e) {
				e.printStackTrace();
			}
			userName.clear();
		}
		
	}
	
	
	
	@FXML
	public void listUser(ActionEvent event) throws IOException{
		listView.setVisible(true);
		listView.refresh();
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
	
	public void errorAlert(String error) {
		   Alert alert =  new Alert(AlertType.ERROR);
		   alert.setTitle("Error");
		   alert.setHeaderText("Error");
		   String content = error;
		   alert.setContentText(content);
		   alert.showAndWait();
	}
		
		
		
	public static listUser read() throws IOException, ClassNotFoundException{
		ObjectInputStream o = new ObjectInputStream(new FileInputStream(storeDir+File.separator + storeFile));
		listUser userList = (listUser)o.readObject();
		o.close();
		return userList;
		}
	
	
	public static void write(listUser userList) throws IOException, ClassNotFoundException{

		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(storeDir+File.separator + storeFile));
		o.writeObject(userList);
		o.close();
	}
	
	
}
