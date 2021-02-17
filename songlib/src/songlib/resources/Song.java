/*Names:
	Andrew Cheng (Netid: ac1792)
	Calvin Li (Netid: cfl53)
Section: 01*/
package songlib.resources;

public class Song {
	public String name;
	public String artist;
	public String year;
	public String album;
	
	public Song(String n, String art, String y, String alb) {
		name = n;
		artist = art;
		year = y;
		album = alb;		
	}
	public String getCSV() {
		return String.format("%s|%s|%s|%s", this.name, this.artist,this.year,this.album);
	}
	@Override
	public String toString() {
		return this.name + " | By: " + this.artist;
	}
	public String getDetails() {
		return String.format("%s\n%s\n%s\n%s", this.name, this.artist, this.year, this.album); 
	}
	public int compareTo(Song other) {
		if(this.name.compareToIgnoreCase( other.name) == 0 ) {
			int checkArtist = this.artist.compareToIgnoreCase(other.artist);
			if(checkArtist == 0) {
				//Exactly same song by criteria
				return 0;
			} else if(checkArtist < 0) {
				//Same name but this artist comes BEFORE
				return -1;
			} else {
				//Same name but this artist comes AFTER
				return 1;
			}
			
		} else if(this.name.compareToIgnoreCase(other.name) < 0 ) {
			//This name comes BEFORE
			return -1;
		} else {
			//This name comes AFTER
			return 1;
		}
	}
}
