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
}
