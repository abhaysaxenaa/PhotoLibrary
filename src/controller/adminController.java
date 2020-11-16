package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
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
	
	public static ArrayList<User> userList = new ArrayList();
	private static final  String path = "data/dat.dat";
	File data = new File(path);
	listUser userlist;
	
	
	public void start() {
		if(userList != null) {
			listView.setItems(FXCollections.observableArrayList(userList));
			listView.refresh();
			listView.getSelectionModel().select(0);
			listView.setVisible(false);
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
	
	
	
	@FXML
	public void deleteUser(ActionEvent event) throws IOException{
		User user = listView.getSelectionModel().getSelectedItem();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			listView.getItems().remove(user);
			listView.refresh();
			
		}
	}
	
	
	
	
	@FXML
	public void addUser(ActionEvent event) throws IOException{
		
		String username = userName.getText().toLowerCase();
		User newUser = new User(username);
		if (username == null  || userlist.checkUserInList(username) == true || username == "admin") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Error");
			alert.setContentText("Please enter a valid user name.");
			alert.showAndWait();
			return;
		} else {
			
			listUser.addUser(newUser);
			try {
				listUser.save(userlist);
			}catch (Exception e) {
				e.printStackTrace();
			}
			userName.clear();
		}
		//save
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
	
		
		
		
	public static void save(User app) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		ObjectOutputStream o  = new ObjectOutputStream(fileOutputStream);
		o.writeObject(new ArrayList<>(Arrays.asList(listView.getItems().toArray())));
		o.close();
	}
	
	
}
