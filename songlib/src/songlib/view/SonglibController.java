package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.resources.Song;


public class SonglibController {
	@FXML Button addb;
	@FXML Button editb;
	@FXML Button deleteb;
	@FXML TextField nameadd;
	@FXML TextField nameedit;
	@FXML TextField artistadd;
	@FXML TextField artistedit;
	@FXML TextField yearadd;
	@FXML TextField yearedit;
	@FXML TextField albumadd;
	@FXML TextField albumedit;
	@FXML ListView<Song> songView; //songView is the fx:id in .fxml file -- Andrew 02/12/2021
	@FXML TextArea songDetails; //Song Details for selections
	private ObservableList<Song> songListObj; //-- Andrew 02/12/2021

	// deleted song selection stuff meant for a two controller implementation -- Calvin 02/12/2021
	
	public void addSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == addb) {
			String name = null; String artist=null; String year=null; String album=null;
			if(nameadd.getText().isEmpty() || artistadd.getText().isEmpty() ) {
				//TOADD: Throw Error Message
			}
			else {
				name = nameadd.getText();
				artist = artistadd.getText();
			}
			if(yearadd.getText().isEmpty()) {
				year = "<No Info>";
			}
			else {
				year = yearadd.getText();
			}
			if(albumadd.getText().isEmpty()) {
				album = "<No Info>";
			}
			else {
				album = yearadd.getText();
			}
			
			nameadd.setText("");
			artistadd.setText("");
			yearadd.setText("");
			albumadd.setText("");
			Song addedSong = new Song(name, artist, year, album);
			//TOADD: Add song, check for existing song, reorder
		}

		
	}
	
	//-- Andrew 02/12/2021
	public void startList(Stage primaryStage) {
		songListObj = FXCollections.observableArrayList(new Song("a","b","c","d"));
		//System.out.println(songListObj);
		try {
		songView.setItems(songListObj);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		songView.getSelectionModel().select(0); //Default selects first song
		songView.getSelectionModel().selectedIndexProperty()
		.addListener((obj, before, after) -> handleSelection(primaryStage));; //Set selection listener
	}
	
	//Handles songView selection
	private void handleSelection(Stage primaryStage) {
		//Route songView selection to songDetails for display
		Song selectedSong = songView.getSelectionModel().getSelectedItem();
		String outputDetails = selectedSong.getSongDetails();
		System.out.println(outputDetails);
		songDetails.setText(outputDetails);

	}
	public void editSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == editb) {
			//TOADD: Get Song object from ListController
			Song selectedSong = new Song("a","b","c","d");
			if(!(nameedit.getText().isEmpty())) {
				selectedSong.name = nameedit.getText();
			}
			if(!(artistedit.getText().isEmpty())) {
				selectedSong.artist = artistedit.getText();
			}
			if(!(yearedit.getText().isEmpty())) {
				selectedSong.year = yearedit.getText();
			}
			if(!(albumedit.getText().isEmpty())) {
				selectedSong.album = albumedit.getText();
			}
			nameedit.setText("");
			artistedit.setText("");
			yearedit.setText("");
			albumedit.setText("");
			//TOADD: Check for existing song, reorder
		}
	}
	public void deleteSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == deleteb){
			int currIndex = songView.getSelectionModel().getSelectedIndex();
			songListObj.remove(currIndex);
			songView.getSelectionModel().select(currIndex);
		}
		
		

	}
}
