package controller;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController {

	@FXML 
	private Button loginButton;
	@FXML
	private TextField usernameField;
	ArrayList<User> users;
	private final String path = "data/data.dat";
	Boolean validUser = false;

	/**
	 * Main stage is the current stage
	 * 
	 * @param stage
	 *            takes the mouse event
	 */
	public void start(Stage mainStage) {
		// TODO Auto-generated method stub
		
	}

}
