package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import model.Superuser;
import model.listUser;

public class Photos extends Application {
	
	//public static listUser driver = new listUser();
	public Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					 getClass().getResource("/View/Login.fxml"));
			//AnchorPane root = (AnchorPane) loader.load();
			 
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			
	        primaryStage.setResizable(false);
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
