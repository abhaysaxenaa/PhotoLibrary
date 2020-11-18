package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Album;

public class searchController {
	
	@FXML
	public Button searchButton, addTagButton, deleteTagButton, copyButton, backButton;
	
	@FXML
	TextField date, tagName;
	
	@FXML
	ListView listView;
	
	public Album album;

	public void start(Album album) {
		// TODO Auto-generated method stub
		
	}

	
}
