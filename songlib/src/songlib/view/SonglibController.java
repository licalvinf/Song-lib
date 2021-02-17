package songlib.view;






import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
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
	@FXML MenuItem openCSV;
	@FXML MenuItem saveCSV;
	private ObservableList<Song> songListObj; //-- Andrew 02/12/2021
	private Song selectedSong;
	
	//Inputs: (Song array to be sorted, 0, length of Song array - 1)
	//Outputs: SORTED Song array
	private Song[] mergeSortR(Song[] unsortedSongs, int startIndex, int endIndex) {
		int n = endIndex - startIndex + 1;
		if( (n) == 0 || (n) == 1) {
			if(n == 1) {
				Song[] baseArray = new Song[n];
				baseArray[0] = unsortedSongs[startIndex];
				return baseArray; // as automatically sorted
			} else {return null;}
		}
		int endA = startIndex + n/2 - 1;
		int startB = startIndex + n/2;
		Song[] firstHalf = mergeSortR(unsortedSongs, startIndex, endA);
		Song[] secondHalf = mergeSortR(unsortedSongs, startB, endIndex);
		Song[] merged = merge(firstHalf, secondHalf);
		for (int i = startIndex; i < merged.length; i++) {unsortedSongs[i] = merged[i];}
		return merged;
	}
	
	//Inputs: (SORTED first half of Song array, SORTED second half of a Song array)
	//Outputs: SORTED merged array
	private Song[] merge(Song[] firstHalf, Song[] secondHalf) {
		int n = firstHalf.length + secondHalf.length;
		Song[] mergedSongs = new Song[n];
		int pointer1 = 0; int pointer2 = 0;
		for (int i = 0; i < mergedSongs.length; i++) {
			if(pointer2 == secondHalf.length) {
				mergedSongs[i] = firstHalf[pointer1];
				pointer1++;
			} else if((pointer1 != firstHalf.length && firstHalf[pointer1].compareTo(secondHalf[pointer2]) < 0 )   ) {
				mergedSongs[i] = firstHalf[pointer1];
				pointer1++;
			}  else {
				mergedSongs[i] = secondHalf[pointer2];
				pointer2++;
			}
		}
		return mergedSongs;
	}
	
	//Inputs: (Song array, 0, Song array length - 1, Song to search)
	//Outputs: Index: -1 if not found, otherwise positive index where target is found
	private int binarySearchR(Song[] array, int startIndex , int endIndex, Song target) {
		if(array.length == 0 || startIndex > endIndex) {return -1;}
		int m = startIndex + (endIndex - startIndex + 1)/2;
		if(array[m].compareTo(target)  == 0) {return m;}
		else if(target.compareTo(array[m]) < 0) {return binarySearchR(array, startIndex, m- 1, target);}
		else {return binarySearchR(array, m+1, endIndex, target);}
	}
	
	private void sortSongList() {
		Song[] convertedArr = O2A(this.songListObj);
		Song[] sortedArr = mergeSortR(convertedArr, 0, convertedArr.length - 1);
		this.songListObj = A2O(sortedArr);
		try {
			songView.setItems(songListObj);
			} catch (Exception e) {
				System.out.println(e);
			}
		printSongList(this.songListObj);
	}
	
	private int searchSongList(Song target) {
		return binarySearchR(O2A(this.songListObj), 0, this.songListObj.size() - 1, target);
	}
	
	
	private Song[] O2A(ObservableList<Song> input) {
		Song[] newArr = new Song[input.size()];
		for (int i = 0; i<input.size();i++) {
			newArr[i] = input.get(i);
		}
		return newArr;
	}
	
	private ObservableList<Song> A2O(Song[] input){
		ObservableList<Song> returnObj = FXCollections.observableArrayList(new ArrayList<Song>());
		for (int i = 0; i<input.length;i++) {
			returnObj.add(input[i]);
		}
		return returnObj;
	}
	
	private void printSongList(ObservableList<Song> input) {
		for(int i = 0; i<input.size(); i++) {
			System.out.print(input.get(i) );
			System.out.print(" \n");
		}
		System.out.println();
		System.out.println();
	}
	
	public void printArray(Song[] songList) {
		for (int i = 0; i < songList.length; i++) {
			System.out.print(songList[i] );
			System.out.print(" ");
		}
		System.out.println();
		System.out.println();
	}
	
	public void addSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == addb) {
			String name = null; String artist=null; String year=null; String album=null;
			if(nameadd.getText().isBlank() || artistadd.getText().isBlank() ) {
				errorAlert("Add Alert","The song's name and artist must be filled out. The addition was cancelled.");
				nameadd.setText("");
				artistadd.setText("");
				yearadd.setText("");
				albumadd.setText("");
				return;
			}
			else {
				name = nameadd.getText().trim();
				artist = artistadd.getText().trim();
			}
			if(yearadd.getText().isBlank()) {
				year = "<No Info>";
			}
			else {
				year = yearadd.getText().trim();
			}
			if(albumadd.getText().isBlank()) {
				album = "<No Info>";
			}
			else {
				album = albumadd.getText().trim();
			}
			
			nameadd.setText("");
			artistadd.setText("");
			yearadd.setText("");
			albumadd.setText("");
			Song addedSong = new Song(name, artist, year, album);
			//if (binarySearchR>0), display error message.
			//else:
			if(searchSongList(addedSong)>=0) {
				errorAlert("Add Alert","A song with this name and artist already exists. The song could not be added.");
				return;
			}
			else
			{
			songListObj.add(addedSong);
			sortSongList();
			songView.getSelectionModel().select(searchSongList(addedSong));
			handleSelection();
			}		
		}
	}
	
	//-- Andrew 02/12/2021
	public void startList(Stage primaryStage) {
		songListObj = FXCollections.observableArrayList( new Song("Song1", "Artist1", "2000", "Album1"), new Song("SongB", "ArtistA", "2002", "Album2"), new Song("SongA", "ArtistB", "2002", "Album3"));
		//System.out.println(songListObj);
		try {
		songView.setItems(songListObj);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//Default selects first song 
		//Show default
		handleSelection(primaryStage);
		//Show subsequent selections
		songView.getSelectionModel().selectedIndexProperty()
		.addListener((obj, before, after) -> handleSelection(primaryStage));; //Set selection listener
	}
	
	//Handles songView selection
	private void handleSelection(Stage primaryStage) {
		//Route songView selection to songDetails for display
		if (songView.getSelectionModel().isEmpty()) {
			songDetails.setText("");
			return;
		}
		this.selectedSong = songView.getSelectionModel().getSelectedItem();
		String[] songElements = selectedSong.getDetails().split("\n");
		String name = songElements[0]; String artist = songElements[1];
		String album = songElements[3]; String year = songElements[2];
		String outputDetails = String.format("Name:\t\t%s\nArtist:\t\t%s\nAlbum:\t\t%s\nYear:\t\t\t%s", name, artist, album, year);
		//System.out.println(outputDetails);
		songDetails.setText(outputDetails);

	}
	private void handleSelection() {
		//Route songView selection to songDetails for display
		if (songView.getSelectionModel().isEmpty()) {
			songDetails.setText("");
			return;
		}
		this.selectedSong = songView.getSelectionModel().getSelectedItem();
		String[] songElements = selectedSong.getDetails().split("\n");
		String name = songElements[0]; String artist = songElements[1];
		String album = songElements[3]; String year = songElements[2];
		String outputDetails = String.format("Name:\t\t%s\nArtist:\t\t%s\nAlbum:\t\t%s\nYear:\t\t\t%s", name, artist, album, year);
		//System.out.println(outputDetails);
		songDetails.setText(outputDetails);

	}
	public void errorAlert(String header, String message) {
		Alert noSelectAlert = new Alert(AlertType.ERROR);
		noSelectAlert.setTitle("Error");
		noSelectAlert.setHeaderText(header);
		noSelectAlert.setContentText(message);
		noSelectAlert.show();
	}
	public void editSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == editb) {
			if(songView.getSelectionModel().isEmpty()) {
				errorAlert("Illegal Edit", "No song was selected.");
				nameedit.setText("");
				artistedit.setText("");
				yearedit.setText("");
				albumedit.setText("");
				return;
			}
			Song tempSong = new Song(this.selectedSong.name,this.selectedSong.artist,this.selectedSong.year,this.selectedSong.album);
			if(!(nameedit.getText().isBlank())) {
				tempSong.name = nameedit.getText().trim();
			}
			if(!(artistedit.getText().isBlank())) {
				tempSong.artist = artistedit.getText().trim();
			}
			if(!(yearedit.getText().isBlank())) {
				tempSong.year = yearedit.getText().trim();
			}
			if(!(albumedit.getText().isBlank())) {
				tempSong.album = albumedit.getText().trim();
			}
			nameedit.setText("");
			artistedit.setText("");
			yearedit.setText("");
			albumedit.setText("");
			if(searchSongList(tempSong)>=0) {
				errorAlert("Edit Alert","A song with the same name and artist already exists. The edit was cancelled.");	
				return;
			}
			else
			{
				songListObj.remove(songView.getSelectionModel().getSelectedIndex());
				songListObj.add(tempSong);
				sortSongList();
				songView.getSelectionModel().select(searchSongList(tempSong));
				handleSelection();
			}
			
		}
	}
	public void deleteSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == deleteb){
			if(songView.getItems().isEmpty()) {
				return;
			}
			int currIndex = songView.getSelectionModel().getSelectedIndex();
			songListObj.remove(currIndex);
			songView.getSelectionModel().select(currIndex);
			handleSelection();
		}
	}
	public void readCSV(ActionEvent e) {
		MenuItem m = (MenuItem)e.getSource();
		if(m == openCSV) {
			File file = new File("C:/data/songlibCSV.txt");
			if(!file.exists()) {
				return;
			}
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
				String line = bufferedReader.readLine();
				String tempSong=null; String tempArtist=null; String tempYear=null; String tempAlbum=null;
				String[] lineParts = line.split("\\|");
				tempSong = lineParts[0];
				tempArtist = lineParts[1];
				tempYear = lineParts[2];
				tempAlbum = lineParts[3];
				line = bufferedReader.readLine();	
				songListObj.setAll(new Song(tempSong, tempArtist, tempYear, tempAlbum));
				songView.getSelectionModel().select(0);
				handleSelection();
				while (line!=null) {
					lineParts = line.split("\\|");
					tempSong = lineParts[0];
					tempArtist = lineParts[1];
					tempYear = lineParts[2];
					tempAlbum = lineParts[3];
					songListObj.add(new Song(tempSong, tempArtist, tempYear, tempAlbum));	
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void writeCSV(ActionEvent e) {
		MenuItem m = (MenuItem)e.getSource();
		if(m == saveCSV) {
			File file = new File("C:/data/songlibCSV.txt");
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			try (BufferedWriter bufferedWriter  = new BufferedWriter(new FileWriter(file, false));) {
				for (Song s : songListObj) {
					bufferedWriter.write(s.getCSV() +"\n");
				}
				bufferedWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
		
		

	
}
