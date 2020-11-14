package app;

import java.io.IOException;


import controller.loginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import model.Superuser;

public class Photos extends Application {
	
	//public static Superuser driver = new Superuser(); //NEED TO IMPLEMENT THIS FIRST
	public Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			FXMLLoader loader = new FXMLLoader();
			 loader.setLocation(
					 getClass().getResource("/View/Login.fxml"));
			 Parent root = (Parent)loader.load();

	        loginController cont =
	       		 loader.getController();
	        cont.start(primaryStage); 
	        primaryStage.setTitle("Photo Album");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		launch(args);
	}

}
