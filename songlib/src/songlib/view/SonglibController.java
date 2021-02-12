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
	@FXML TextField nameadd;
	@FXML TextField nameedit;
	@FXML TextField artistadd;
	@FXML TextField artistedit;
	@FXML TextField yearadd;
	@FXML TextField yearedit;
	@FXML TextField albumadd;
	@FXML TextField albumedit;
	@FXML ListView<String> songView; //songView is the fx:id in .fxml file -- Andrew 02/12/2021
	@FXML TextArea songDetails; //Song Details for selections
	private ObservableList<String> songListObj; //-- Andrew 02/12/2021
	
	public void addSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == addb) {
			String name = null; String artist=null; String year=null; String album=null;
			if(nameadd.getText().isEmpty() || artistadd.getText().isEmpty() ) {
				//Throw Error Message
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
			Song addedSong = new Song(name, artist, year, album);			
		}
		
	}
	
	//-- Andrew 02/12/2021
	public void startList(Stage primaryStage) {
		songListObj = FXCollections.observableArrayList("Song1\nArtist1\nAlbum1\n2000", "Song2\nArtist2\nAlbum2\n2002");
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
		String selectedSong = songView.getSelectionModel().getSelectedItem();
		String[] songElements = selectedSong.split("\n");
		String name = songElements[0]; String artist = songElements[1];
		String album = songElements[2]; String year = songElements[3];
		String outputDetails = String.format("Name:\t\t%s\nArtist:\t\t%s\nAlbum:\t\t%s\nYear:\t\t%s", name, artist, album, year);
		//System.out.println(outputDetails);
		songDetails.setText(outputDetails);
	}
}
