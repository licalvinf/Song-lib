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
	@FXML ListView<Song> songView; //songView is the fx:id in .fxml file -- Andrew 02/12/2021
	@FXML TextArea songDetails; //Song Details for selections
	private ObservableList<Song> songListObj; //-- Andrew 02/12/2021
	private Song selectedSong;
	private Song[] sortedSongs;
	
	//Works on ints now
	private int[] mergeSortR(int[] unsortedSongs, int startIndex, int endIndex) {
		int n = endIndex - startIndex + 1;
		if( (n) == 0 || (n) == 1) {
			//System.out.println("Reached base");
			
			if(n == 1) {
				int[] baseArray = new int[n];
				//System.out.println("Returning array: ");
				baseArray[0] = unsortedSongs[startIndex];
				//printArray(baseArray);
				return baseArray; // as automatically sorted
			} else {
				return null;
			}
		}
		int endA = startIndex + n/2 - 1;
		int startB = startIndex + n/2;
		//Sort first half
		//System.out.println(String.format("(L) Recursing on start: %s end: %s", startIndex, endA ));
		int[] firstHalf = mergeSortR(unsortedSongs, startIndex, endA);
		//printArray(firstHalf);
		//Sort second half
		//System.out.println(String.format("(R) Recursing on start: %s end: %s", startB, endIndex ));
		int[] secondHalf = mergeSortR(unsortedSongs, startB, endIndex);
		//printArray(secondHalf);
		int[] merged = merge(firstHalf, secondHalf);
		
		//Copy over merged into original
		for (int i = startIndex; i < merged.length; i++) {
			unsortedSongs[i] = merged[i];
		}
		
		//printArray(merged);
		return merged;
		
	}
	
	//working on ints
	private int[] merge(int[] firstHalf, int[] secondHalf) {
		int n = firstHalf.length + secondHalf.length;
		int[] mergedSongs = new int[n];
		int pointer1 = 0;
		int pointer2 = 0;
		for (int i = 0; i < mergedSongs.length; i++) {
			if(pointer2 == secondHalf.length) {
				mergedSongs[i] = firstHalf[pointer1];
				//System.out.println("Case 1");
				pointer1++;
			} else if((pointer1 != firstHalf.length && firstHalf[pointer1] < secondHalf[pointer2])   ) {
				mergedSongs[i] = firstHalf[pointer1];
				//System.out.println("Case 2");
				pointer1++;
			}  else {
				mergedSongs[i] = secondHalf[pointer2];
				//System.out.println("Case 3");
				pointer2++;
			}
		}
		return mergedSongs;
	}
	
	//Working for ints
	private int binarySearchR(int[] array, int startIndex , int endIndex, int target) {
		if(array.length == 0 || startIndex > endIndex) {
			System.out.println("Target not found!");
			return -1;
		}
		int n = endIndex - startIndex + 1;
		int m = startIndex + n/2;
		if(array[m] == target) {
			return m;
		}
		else if(target < array[m]) {
			System.out.println(String.format("(target less than) Recursing on array from start: %s to end: %s", startIndex, m-1));
			return binarySearchR(array, startIndex, m- 1, target);
		}
		else {
			System.out.println(String.format("(target more than) Recursing on array from start: %s to end: %s", m+ 1, endIndex));
			return binarySearchR(array, m+1, endIndex, target);
		}
	}
	
	public void testMergeSort() {
		int[] testMergeSort = new int[] {1, 2, 7, 8, 3, 4, 5, 6, 22, 11, 2, 4, 5, 7, 3};
		int[] sortedArray = mergeSortR(testMergeSort, 0, testMergeSort.length-1);
		//int[] testMergeA = new int[] {1,2,7,8};
		//int[] testMergeB = new int[] {3,4,5,6};
		//int[] sortedArray = merge( testMergeA, testMergeB);
		printArray(sortedArray);
		System.out.println(binarySearchR(sortedArray, 0, sortedArray.length-1, 5));
	}
	
	public void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] );
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
			//TOADD: Add song, check for existing song, reorder
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
			if(!(nameedit.getText().isEmpty())) {
				this.selectedSong.name = nameedit.getText();
			}
			if(!(artistedit.getText().isEmpty())) {
				this.selectedSong.artist = artistedit.getText();
			}
			if(!(yearedit.getText().isEmpty())) {
				this.selectedSong.year = yearedit.getText();
			}
			if(!(albumedit.getText().isEmpty())) {
				this.selectedSong.album = albumedit.getText();
			}
			nameedit.setText("");
			artistedit.setText("");
			yearedit.setText("");
			albumedit.setText("");
			//TOADD: Check for existing song, reorder
		}
	}
	public void deleteSong(ActionEvent e) {
		//Button b = (Button)e.GetSource();
		
		

	}
}
