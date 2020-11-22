package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import model.Superuser;
//import model.listUser;
import javafx.stage.WindowEvent;
import model.listUser;

public class Photos extends Application {
	
	public static  listUser driver;
	public listUser userlist;
	//public static listUser driver = new listUser();
	public Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(
					 getClass().getResource("/View/Login.fxml"));
			 
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			
			userlist = userlist.read();
			driver = userlist;
			
	        primaryStage.setResizable(false);
	        primaryStage.setTitle("Photo Album");
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//When window is closed
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				
				//Implement saving listUser instance.
				
				System.out.print("Closed");
			}
		});
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//load list from superuser
		
		launch(args);
	}

}