package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	public void startList() {
		songListObj = FXCollections.observableArrayList("Song1", "Song2");
		songView.setItems(songListObj);
	}
	
}
