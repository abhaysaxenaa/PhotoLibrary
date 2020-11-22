
//Abhay Saxena (ans192) & GVS Karthik (vg311)

package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
import model.User;
import model.listUser;

/*
 * @author Venkata Sai Karthik Gandrath
 * @author Abhay Saxena 
 * 
 * 
 */

public class adminController {
	@FXML private Button  createButton, deleteButton, listUserbutton, logoutButton;
	@FXML private TextField userName;
	@FXML private ListView<String> listView;
	
	public  ArrayList<String> allUsers = new ArrayList();
	
	public listUser userlist = Photos.driver;
	
	
	public ObservableList<String> obsList; 
	
	/*
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void bootup() throws ClassNotFoundException, IOException {
			listView.setVisible(false);	
			update();
			if(!allUsers.isEmpty()) {
				listView.getSelectionModel().select(0);
				
			}
	}


	/*
	 * @param event 
	 * @throws IOException
	 */
	
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
	
	
	/*
	 * @param event 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	public void deleteUser(ActionEvent event) throws IOException, ClassNotFoundException{
		int index = listView.getSelectionModel().getSelectedIndex();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			userlist.deleteUser(index);
			update();
			listView.refresh();
			listUser.write(userlist);
			
		}
	}
	
	
	
	/*
	 * @param event 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	public void addUser(ActionEvent event) throws IOException, ClassNotFoundException{
		
		String username = userName.getText().toLowerCase();
		User newUser = new User(username);
		if (username.isEmpty()  || userlist.checkUserInList(username) == true || username.equals("admin")) {
			errorAlert("Please enter a valid UserName");
			return;
		} else {
			userlist.addUser(newUser);
			update();
			listView.setItems(obsList);
			
			
			try {
				listUser.write(userlist);
			}catch (Exception e) {
				e.printStackTrace();
			}
			userName.clear();
		}
		
	}
	
	
	/*
	 * @param event 
	 * @throws IOException
	 * 
	 */
	@FXML
	public void listUser(ActionEvent event) throws IOException{
		listView.setVisible(true);
		listView.refresh();
	}
	
	
	/*
	 * @param function
	 */
	public Alert ConfirmationAlert(String function) {
			//MODIFIED: Added a more specific confirmation dialog.
			Alert confirmation = new Alert(AlertType.CONFIRMATION);
			confirmation.setTitle("Confirmation Dialog");
			confirmation.setHeaderText("Operation: "+ function );
			confirmation.setContentText(function);

			confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

			return confirmation;
	}
	
	/*
	 * 
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
		
		
	public void update() {
		allUsers.clear();
		for (int i = 0; i < userlist.getList().size(); i++) {
			allUsers.add(userlist.getList().get(i).getUsername());
		}
		listView.refresh();
		obsList = FXCollections.observableArrayList(allUsers);
		listView.setItems(obsList);
		listView.refresh();

	
	}
	
	
}