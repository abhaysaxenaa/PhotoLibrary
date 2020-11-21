package controller;

import java.io.IOException;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.listUser;
//import model.listUser;
//import model.User;

public class LoginController {
	
	
	@FXML public Button loginButton;
	@FXML public TextField userName;
	public Stage primaryStage;

	@FXML
	public void loginUser(ActionEvent event) throws ClassNotFoundException, IOException {

		String username = userName.getText().toLowerCase().trim();
		listUser userlist = Photos.driver;
		FXMLLoader loader;
		Parent manager;
		String str ="";
	
		System.out.println(userlist + " hey");
		
		try{
			if(username.equals("admin")) {

			loader = new FXMLLoader(getClass().getResource("/View/Admin.fxml"));
			manager = (Parent) loader.load();
			adminController admincontroller = loader.getController();
			Scene adminScene = new Scene(manager);
			Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
			admincontroller.bootup();
			appStage.setScene(adminScene);
			appStage.show();
		}
	//Implement check for user.
			else if(userlist.checkUserInList(username) == true) {
				if(username != "admin" && !(username.equals(str)) ){
			loader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
			manager = (Parent) loader.load();
			User curr = new User(username);
			userlist.setCurrent(curr);
			userController usercontroller = loader.getController();
			usercontroller.user = curr;
			Scene userScene = new Scene(manager);
			Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
			usercontroller.bootUp();
			appStage.setScene(userScene);
			appStage.show();
				}
			}
			
		else {
			errorAlert("Please enter a valid input");
			userName.clear();
		}
	}catch (IOException e) {
		e.printStackTrace();}

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