package app;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import model.Superuser;

public class Photos extends Application {
	
	//public static Superuser driver = new Superuser(); //NEED TO IMPLEMENT THIS FIRST
	public Stage mainStage;

	@Override
	public void start(Stage mainStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/View/Login.fxml"));
			//FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane) fxmlLoader.load();

			
			Scene scene = new Scene(root);
			mainStage.setResizable(false);
			mainStage.setTitle("Photo Library");
			mainStage.setScene(scene);
			mainStage.show();
			LoginController loginController = fxmlLoader.getController();
			loginController.start(mainStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Implement SuperUser");
		//driver = Superuser.load();
		launch(args);
	}

}
