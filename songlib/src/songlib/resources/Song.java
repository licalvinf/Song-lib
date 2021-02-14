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
	@Override
	public String toString() {
		return this.name + " | By: " + this.artist;
	}
	public String getDetails() {
		return String.format("%s\n%s\n%s\n%s", this.name, this.artist, this.year, this.album); 
	}
	public int compareTo(Song other) {
		String concatThis = String.format("%s%s", this.name, this.artist);
		String concatOther = String.format("%s%s", other.name, other.artist);
		return concatThis.compareToIgnoreCase(concatOther);
	}
}
