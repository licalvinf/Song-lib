package songlib.view;

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
	
	Song selectedSong = null;
	
	public void transferSong(Song s) {
		selectedSong = s;
	}
	
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
		Button b = (Button)e.GetSource();
		
		
	}
}
