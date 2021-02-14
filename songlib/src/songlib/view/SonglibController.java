package songlib.view;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private Song[] sortedSongs;
	
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
	
	public void testMergeSort() {
		Song[] testSongList = new Song[] {new Song("AB", "BC", "2000", "Album1"), 
				new Song("BB", "CC", "2001", "Album2"), 
				new Song("AB", "CD", "2002", "Album1")};
		Song[] sortedSongList = mergeSortR(testSongList, 0, testSongList.length - 1);
		printArray(sortedSongList);
		
		Song target = new Song("BB", "CC", "2001", "Album3");
		System.out.println(binarySearchR(sortedSongList, 0, sortedSongList.length-1, target));
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
			//if (binarySearchR>0), display error message.
			//else:
			songListObj.add(addedSong);
			//call mergesort function		
		}
	}
	
	//-- Andrew 02/12/2021
	public void startList(Stage primaryStage) {
		songListObj = FXCollections.observableArrayList( new Song("Song1", "Artist1", "2000", "Album1"), new Song("Song2", "Artist2", "2002", "Album2"));
		//System.out.println(songListObj);
		try {
		songView.setItems(songListObj);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//Default selects first song
		songView.getSelectionModel().select(0); 
		//Show default
		handleSelection(primaryStage);
		//Show subsequent selections
		songView.getSelectionModel().selectedIndexProperty()
		.addListener((obj, before, after) -> handleSelection(primaryStage));; //Set selection listener
	}
	
	//Handles songView selection
	private void handleSelection(Stage primaryStage) {
		//Route songView selection to songDetails for display
		this.selectedSong = songView.getSelectionModel().getSelectedItem();
		String[] songElements = selectedSong.getDetails().split("\n");
		String name = songElements[0]; String artist = songElements[1];
		String album = songElements[3]; String year = songElements[2];
		String outputDetails = String.format("Name:\t\t%s\nArtist:\t\t%s\nAlbum:\t\t%s\nYear:\t\t\t%s", name, artist, album, year);
		//System.out.println(outputDetails);
		songDetails.setText(outputDetails);

	}
	public void editSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == editb) {
			Song tempSong = this.selectedSong;
			if(!(nameedit.getText().isEmpty())) {
				tempSong.name = nameedit.getText();
			}
			if(!(artistedit.getText().isEmpty())) {
				tempSong.artist = artistedit.getText();
			}
			if(!(yearedit.getText().isEmpty())) {
				tempSong.year = yearedit.getText();
			}
			if(!(albumedit.getText().isEmpty())) {
				tempSong.album = albumedit.getText();
			}
			nameedit.setText("");
			artistedit.setText("");
			yearedit.setText("");
			albumedit.setText("");
			//if (binarySearchR>0), display error message.
			//else:
			this.selectedSong = tempSong;
			//call mergesort function
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
	public void readCSV(ActionEvent e) {
		MenuItem m = (MenuItem)e.getSource();
		if(m == openCSV) {
			File file = new File("C:/data/songlibCSV");
			if(!file.exists())
				return;
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
				String line = bufferedReader.readLine();
				String tempSong=null; String tempArtist=null; String tempYear=null; String tempAlbum=null;
				while (line!=null) {
					String[] lineParts = line.split("|");
					tempSong = lineParts[0];
					tempArtist = lineParts[1];
					tempYear = lineParts[2];
					tempAlbum = lineParts[3];
					songListObj.add(new Song(tempSong, tempArtist, tempYear, tempAlbum));
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
			try (BufferedWriter bufferedWriter  = new BufferedWriter(new FileWriter(file));) {
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
