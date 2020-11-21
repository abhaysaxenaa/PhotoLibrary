package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

public class albumController {
	
	@FXML
	public ListView<Photo> listView;
	
	@FXML
	TextField addPhoto, captionPhoto, copyPhoto, movePhoto;
	
	@FXML 
	ImageView photoView;
	
	@FXML
	Button addButton, removebutton,  moveButton, copyButton,  backButton, logoutButton, photoDisplay;
	
	@FXML
	public Text photoDate;
	
	
	
	public static ArrayList<Album> allAlbums;
	public static ArrayList<Photo> allPhotos = new ArrayList<Photo>();
	public ObservableList<Photo> obsList;
	
	public static User user;
	public listUser userlist = Photos.driver;
	public static Album album;
	private Photo photo;
	public int currIndex;
	public int previousIndex;
	public int nextIndex = 0;
	
	public void start() {
		update();
		if (!allPhotos.isEmpty()) {
			listView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				thumbnail();
	//		update();
			});
//			ArrayList<String> allAlbums = new ArrayList<String>();
//			copylist.setItems(FXCollections.observableArrayList(allAlbums));
//			movelist.setItems(FXCollections.observableArrayList(allAlbums));
		}
		
//		this.user = user;
//		this.album = album;
//		listView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
//			@Override
//			public ListCell<Photo> call(ListView<Photo> p) {
//				return new PhotoCell();
//			}
//		});
		
	}
	
	@FXML
	public void photoDisplay (ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/photoDisplay.fxml"));
		Parent parent = (Parent) loader.load();
		photoDisplay controller = loader.getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
		controller.start(appStage);
		appStage.setScene(scene);
		appStage.show();
	}
	
	public void displayDate() {
		photoDate.setText("Date: " + userlist.getCurrentUser().getCurrentAlbum().getPhoto().getDate());
	}
	
	public void thumbnail() {
		Photo photo = listView.getSelectionModel().getSelectedItem();
		File file;
		if(photo != null) {
			file = photo.getImg();
			if(userlist.getCurrentUser().getUsername().equals("stock") && photo.isStockPhoto) {
				String str = file.getAbsolutePath();
				int stockPhoto = str.indexOf("stockphotos");
				String newfilepath = str.substring(stockPhoto, str.length());
				File img = new File(newfilepath);
				Image image = new Image(img.toURI().toString());
				photoView.setImage(image);
		}
			else {
				Image image = new Image(file.toURI().toString());
				photoView.setImage(image);
			}
		}
	}
	
	@FXML
	public void addPhoto(ActionEvent event) throws IOException {
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        chooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = chooser.showOpenDialog(null);
       
        if(file == null) {
        	return;
        }
        if(file != null) {
        	Image img = new Image(file.toURI().toString());
        	//SerializableImage SImg = new SerializableImage(img);
        	//SImg.setImage(img);
        	Calendar date = Calendar.getInstance();
        	
        	Photo newPhoto = new Photo(img);
        	//album.addPhoto(newPhoto);
        	userlist.getCurrentUser().getCurrentAlbum().addPhoto(newPhoto);
        	listUser.write(userlist);
        	photoView.setImage(img);
        	update();
        }
	}

	//Change
	@FXML 
	public void movePhoto(ActionEvent event) throws IOException{
		String movePath = movePhoto.getText().trim();
		boolean albumCheck = false;
		int idx = 0;;
		//Check if move album exists
		for (int i = 0; i < allAlbums.size(); i++) {
			Album temp = allAlbums.get(i);
			if (temp.getName().equals(movePath)) {
				idx = i;
				albumCheck = !albumCheck;
			}
		}
		
		if (albumCheck) {
			Album newAlbum = allAlbums.get(idx);
			Photo photo = listView.getSelectionModel().getSelectedItem();
			newAlbum.addPhoto(photo);
			album.remove(listView.getSelectionModel().getSelectedIndex());
			
			//newAlbum.write(newAlbum);
			//album.write(album);
			update();
		} else {
			return;
		}
		errorAlert("Invalid Album");
			
	}
	
	
	@FXML
	public void copyPhoto(ActionEvent event) throws IOException{
		String copyLocation = copyPhoto.getText().trim();
		boolean albumCheck = false;
		int idx = 0;;
		//Check if move album exists
		for (int i = 0; i < allAlbums.size(); i++) {
			Album temp = allAlbums.get(i);
			if (temp.getName().equals(copyLocation)) {
				idx = i;
				albumCheck = !albumCheck;
			}
		}
		
		if (albumCheck) {
			Album newAlbum = allAlbums.get(idx);
			Photo photo = listView.getSelectionModel().getSelectedItem();
			newAlbum.addPhoto(photo);
			
		//	newAlbum.save(newAlbum);
		} else {
			return;
		}
		errorAlert("Invalid Album");
	}
	
	/*@FXML
	public void nextPhoto(ActionEvent event) {
		
		
		int previousIndex = allPhotos.size()-1;
		if(currIndex + 1 > previousIndex) {
			return;
		}else {
			currIndex++;
			File file;
			Photo photo = allPhotos.get(currIndex);
			if(photo != null) {
				file = photo.getImg();
				Image image = new Image(file.toURI().toString());
				photoView.setImage(image);
			}
		}
		
		
	}
	@FXML
	public void previousPhoto(ActionEvent event) {
		if(currIndex-1 < nextIndex) {
			return;
		}
		else {
			currIndex--;
			File file;
			Photo photo = allPhotos.get(currIndex);
			if(photo != null) {
				file = photo.getImg();
				Image image = new Image(file.toURI().toString());
				photoView.setImage(image);
				
			}
		}
	}*/
	
	public void update() {
		/*if (allPhotos.size() > 0) {
			currIndex = 0;
			previousIndex = allPhotos.size()-1;
			File file;
			if (allPhotos.get(0) != null) {
				Photo photo = allPhotos.get(0);
				if(photo != null) {
					file = photo.getImg();
					Image image = new Image(file.toURI().toString());
					photoView.setImage(image);
					obsList = FXCollections.observableArrayList(allPhotos);
					listView.setItems(obsList);
					listView.refresh();
				}
			}
		}*/
		
		allPhotos.clear();
		for (int i = 0; i < album.getPhotos().size(); i++) {
			allPhotos.add(album.getPhotos().get(i));
		}

		obsList = FXCollections.observableArrayList(allPhotos);
		listView.setItems(obsList);
		listView.refresh();
	}
	
	//Not sure about album.remove(), should take in an index, was taking Photo photo = listView.getSelectionModel().getSelectedItem();
	@FXML
	public void removePhoto(ActionEvent event) throws ClassNotFoundException, IOException {
		int photoIdx = listView.getSelectionModel().getSelectedIndex();
		Alert confirmation = ConfirmationAlert("Are you Sure");
		if (confirmation.showAndWait().get() == ButtonType.YES) {
			album.remove(photoIdx);
			update();
			int lastuserindex = album.getPhotos().size();
				if (album.getPhotos().size() == 1) {
					listView.getSelectionModel().select(0);
				} else if (photoIdx == lastuserindex) {
					listView.getSelectionModel().select(lastuserindex-1);
				} else { 
					listView.getSelectionModel().select(photoIdx);
				}
				if (allPhotos.size() == 0) {
					photoView.setImage(null);
				} else {
					listView.getSelectionModel().select(0);
				}
		// implement 	reload();
		listUser.write(userlist);
		}
	}
	
//	@FXML
//	public void captionPhoto(ActionEvent event) throws ClassNotFoundException, IOException {
//		String caption = captionPhoto.getText().trim();
//		if(caption == null) {
//			errorAlert("please enter a valid caption");
//		}
//		else if (caption == photo.getCaption()) {
//			errorAlert("please enter a different caption to re-caption");
//		}
//		else { 
//			photo.setCaption(caption);
//			listUser.write(userlist);
//		}
//	}
	
//	@FXML
//	public void search(ActionEvent event) throws IOException{
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Search.fxml"));
//		Parent parent = (Parent) loader.load();
//		searchController controller = loader.<searchController>getController();
//		Scene scene = new Scene(parent);
//		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//		controller.start();
//		appStage.setScene(scene);
//		appStage.show();	
//	}
	
	@FXML
	public void back(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
		Parent parent = (Parent) loader.load();
		userController controller = loader.<userController>getController();
		Scene scene = new Scene(parent);
		Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		controller.bootUp();
		appStage.setScene(scene);
		appStage.show();
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
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
	
	public Alert ConfirmationAlert(String function) {
		//MODIFIED: Added a more specific confirmation dialog.
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Operation: "+ function + "ing a song.");
		confirmation.setContentText("Are you sure you want to " + function.toLowerCase() + " this Photo?");

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
	

}