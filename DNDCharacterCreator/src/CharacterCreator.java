import java.nio.channels.SelectableChannel;
import java.util.Scanner;
import ie.tus.project.PCharacter;
import ie.tus.project.Background;
import ie.tus.project.Classes;
import ie.tus.project.Species;
Scanner sc = new Scanner(System.in);
//Main Method Compact Source File
void main(){
	//Variables
	int input, sentinel =1;
	
	
	IO.println("Welcome to the D&D 2024 Character Creator");
	System.out.println("Please select an option");
	
	//Main Loop
	do{
	System.out.println("1. Create Character");
	System.out.println("2. Quick Character");
	System.out.println("3. Display Characters Character");
		if (sc.hasNextInt()) {
	        input = sc.nextInt();
	    } else {
	        System.out.println("Invalid input, please enter a valid number");
	        sc.next(); // consume invalid token
	        continue; // restart loop
	    }
		
		switch(input) {
		case 1:
			IO.println("Entered Create Character\n");
			createCharacter();
			break;
		case 2:
			IO.println("Entered Default Character\n");
			createCharacter("Adventurer", Classes.Fighter, Species.HUMAN);
			break;
		default:
			IO.println("Invalid Option\n");
			break;
		}
	}while(sentinel == 1);


	
}

//Create Character
public void createCharacter() {
//	String name;
//	Classes pClass;
//	Species species;
//	Background background;
	//Enter Character Details
	var name=setName();
	var pClass = setClass();
	var species = setSpecies();
	var background = setBackground();
	var stats = rollStatsWithReroll();
	var  playerCharacter = new PCharacter(name, pClass, species, background, stats);
	displayCharacter(playerCharacter);
	rollStats();
	//Display Classes from Classes Enum
	
}

public void createCharacter(String name, Classes pClass, Species species) {
    var playerCharacter = new PCharacter(name, pClass, species);
    displayCharacter(playerCharacter);
}

public String setName() {
	IO.println("Enter Character Name");
	String name =IO.readln();
	IO.println(name);
	return name;
}

public Classes setClass() {
	int  pClass =0;
	Classes selectedClass;
	do{System.out.println("1. Select Class");
	for (Classes c : Classes.values()) {
        System.out.println((c.ordinal() + 1) + ". " + c);
    }
	 IO.println("Select Class");
	 pClass = sc.nextInt();
     IO.println(pClass);
    selectedClass =Classes.values()[pClass-1];
     System.out.println("Class is: "+selectedClass );
     // = selectedClass.toString();
}while(pClass <1 || pClass >12);
	return selectedClass;
	
}

public Species setSpecies() {
	int  species =0;
	Species selectedSpecies;
	do{System.out.println("Select Species");
	for (Species s : Species.values()) {
        System.out.println((s.ordinal() + 1) + ". " + s);
    }
	 species = sc.nextInt();
     IO.println(species);
     selectedSpecies =Species.values()[species-1];
     System.out.println(selectedSpecies );
}while(species <1 || species >10);
	return selectedSpecies;
}

public Background setBackground() {
	int  bg =0;
	Background selectedBackground;
	do{System.out.println("Select Background");
	for (Background b : Background.values()) {
        System.out.println((b.ordinal() + 1) + ". " + b);
    }
	 bg = sc.nextInt();
     IO.println(bg);
     selectedBackground =Background.values()[bg-1];
     System.out.println(selectedBackground );
}while(bg <1 || bg >16);
	return selectedBackground;
}

public void displayCharacter(PCharacter pCharacter) {
	IO.println(pCharacter.toString());
}

public int[] rollStats() {
	var stats = new int[6];
	for(int i =0; i<6; i++) {
		var rollTotal = new int[4];
		for(int j =0; j<4;j++) {
			rollTotal[j] = (int)(Math.random()*6)+1;
		}
		Arrays.sort(rollTotal);
		stats[i] =rollTotal[1]+rollTotal[2]+rollTotal[3];
	}
	return stats;
}

public int[] rollStatsWithReroll() {
	int[] stats;
	do {
		stats = rollStats();
		System.out.println("You rolled: "+ Arrays.toString(stats));
		System.out.println("Do you want to keep these stats? (Y/N)");
		String answer = IO.readln().trim().toUpperCase();
		if(answer.equals("Y")) {
			return stats;
		}
		System.out.println("Rerolling stats...\n");
		
	}while (true);
}
