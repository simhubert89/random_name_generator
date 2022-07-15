package pkg;

import java.util.Scanner;

////////////////////////////////////////////////////////////////////////////////////////
//Player.java		@authors wuddy
//
////////////////////////////////////////////////////////////////////////////////////////
public class Player {
	private long idPlayer;
	private static long PlayerCounter = 1; 	// count how many instances
	
	private String lastName = "";
	private String firstName = "";
	
	protected Scanner sc;
	
	// Overloaded constructor
	Player(String firstName, String lastName) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		setIdPlayer((long)PlayerCounter++);
	}
	
	Player() { this.setIdPlayer(PlayerCounter++);}
	
	public void addPlayer() {
		sc = new Scanner(System.in);
		System.out.println("You want to add a custom player...");
		System.out.println("Enter his first name: ");
		firstName = sc.next();
		setFirstName(firstName);
		
		System.out.println("Enter his last name: ");
		lastName = sc.next();
		setLastName(lastName);
	}
	
	@Override
	public String toString() {
		return getIdPlayer() + " " + getLastName() + " " + getFirstName();
	}
	
	public void setIdPlayer(long idPlayer) {this.idPlayer = idPlayer;}
	public long getIdPlayer() { return idPlayer; }
	
	public void setLastName(String lastName) { this.lastName = lastName;}
	public String getLastName() { return lastName;}
	
	
	public void setFirstName(String firstName ) { this.firstName = firstName; }
	public String getFirstName() { return firstName; }
	
	public static void setPlayerCounter(long PlayerCounter) {
		Player.PlayerCounter = PlayerCounter;
	}
	
	public static long getPlayerCounter() {
		return PlayerCounter;
	}
 	
	// THANKS FOR WATCHING <3

}
