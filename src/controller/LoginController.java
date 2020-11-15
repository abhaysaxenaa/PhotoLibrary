package controller;

import java.io.IOException;

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
import model.listUser;
import model.User;

public class LoginController {
	
	@FXML private Button loginButton;
	@FXML private TextField userName;
	
	//Call a driver to the Photos.java class in 'src'.
	
	@FXML
	public void login(ActionEvent event) {
		
		String username = userName.getText();
		FXMLLoader loader;
		Parent manager;
		//public static listUser checker = Photos.checker; USE IN USER CHECK.
		
		try {
			
		
			if(username.equals("admin")) {
				loader = new FXMLLoader(getClass().getResource("/View/Admin.fxml"));
				manager = (Parent) loader.load();
				adminController admincontroller = loader.getController();
				Scene adminScene = new Scene(manager);
				Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
				admincontroller.start();
				appStage.setScene(adminScene);
				appStage.show();
			}
			//Implement check for user.
			else if(username != "admin" && username != null){
				loader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
				manager = (Parent) loader.load();
				userController usercontroller = loader.getController();
				Scene userScene = new Scene(manager);
				Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
				usercontroller.start(appStage);
				appStage.setScene(userScene);
				appStage.show();
			}
			else {
				errorAlert("Please enter a valid input");
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
			
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
