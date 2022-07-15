package pkg;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

////////////////////////////////////////////////////////////////////////////////////////
//Main.java		@authors wuddy
//
//Main
//args0 = Names.txt
////////////////////////////////////////////////////////////////////////////////////////
public class Main {
	static Scanner scan = new Scanner(System.in);
	private static ArrayList<Player> tblPlayers = new ArrayList<Player>(); //Why arraylist: sort, no need to gage size, etc.

	public static void main(String[] args) throws IOException {
		try {
			readFile(args[0]);
		} catch(IOException e) {
			e.printStackTrace();
		}
		int min = 0;
		int max = 0;
		int order;
		int nbName;
		int choice = -1;

		do {
			choice = mainMenu();
			switch(choice) {
			case 0:
				// Exit
				System.out.println("Exit program.");
				break;

			case 1:
				// Generate names

				System.out.println("[NAME GENERATOR]");
				System.out.println("How many minimum characters for the name?");
				min = scan.nextInt();

				System.out.println("");
				System.out.println("How many maximum characters for the name?");
				System.out.println("");

				while(min > max) { 
					System.err.println("You must have a minimum number greater than maximum number.");
					System.err.println();
					System.out.println("How many maximum characters for the name?");
					max = scan.nextInt();
				}

				while(min == max) { 
					System.err.println("You must have a minimum number greater than maximum number.");
					System.err.println();
					System.out.println("How many maximum characters for the name?");
					max = scan.nextInt();
				}

				System.out.println();
				System.out.println("How many names you want to generate?");
				nbName = scan.nextInt();

				order = 4;

				System.out.println();
				System.out.println();

				System.out.println("Names will now be generated...");
				Markov<?> markov = new Markov(min, max, order, nbName);
				markov.setfileToSelectFrom();
				markov.setProbabilities();
				ArrayList<String> list = markov.createNames();
				for(String s: list) {
					Player tempPlayer = new Player();
					String strFullName = s;
					int position = strFullName.indexOf(" ");
					String strFirstName = strFullName.substring(0,position+1);
					strFirstName.trim();

					String strLastName = strFullName.substring(position+1, strFullName.length());

					tempPlayer.setLastName(strLastName);
					tempPlayer.setFirstName(strFirstName);

					getTblPlayers().add(tempPlayer);
					System.out.println(tempPlayer.getFirstName() + tempPlayer.getLastName());
				}
				break;

			case 2: 
				displayNamesList();
				break;
			case 3:
				Player temp = new Player();
				temp.addPlayer();
				getTblPlayers().add(temp);
				displayNamesList();
				temp = null;
				break;
			case 4:
				initialize();
				try {
					readFile(args[0]);
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				break;

			default:
				System.err.println("Error.");
			}

		} while(choice != 0);
		scan.close();
		System.exit(0);
	}

	private static void readFile(String filePathA) throws FileNotFoundException {
		File thisFile = new File(filePathA);
		try (FileInputStream fis = new FileInputStream(thisFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				Scanner scan = new Scanner(bis)) {
			scan.useDelimiter("[^A-Za-z]+");
			// We now can search the line.
			while(scan.hasNext()) { // As long as theres a new long, get each parameter and construct the object.
				String lastName = scan.next();
				String firstName = scan.next() ;
				
				Player thisPlayer = new Player(lastName, firstName);
				getTblPlayers().add(thisPlayer);
			};
			scan.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("File was not found!");
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int mainMenu() throws IOException {
		System.out.println("\n[MENU]");
		System.out.println("1. Generate names.");
		System.out.println("2. Display names list.");
		System.out.println("3. Add a player manually.");
		System.out.println("4. Initialize.");
		System.out.println("0. Exit.");
		System.out.println("Make your choice and hit ENTER");
		return getInt();
	}
	
	public static void displayNamesList() {
		System.out.println("tblPlayers -->: ");
		
		for(Player str: getTblPlayers()) {
			System.out.println(str.getLastName() + " " + str.getFirstName()) ;
		}
	}
	
	public static void initialize() {
		getTblPlayers().remove(getTblPlayers());
	}
	
	public static int getInt() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return Integer.parseInt(br.readLine());
	}
	
	public static ArrayList<Player> getTblPlayers() {
		return tblPlayers;
	}
	
	public static void setTblPlayers(ArrayList<Player> tblPlayers) {
		Main.tblPlayers = tblPlayers;
	}
	
}
