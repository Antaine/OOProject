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
	do{System.out.println("1. Create Character");
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
		default:
			IO.println("Invalid Option\n");
			break;
		}
	}while(sentinel == 1);


	
}

//Create Character
public void createCharacter() {
	String name;
	Classes pClass;
	Species species;
	Background background;
	//Enter Character Details
	//IO.println("Enter Character Name");
	//String name =IO.readln();
//	IO.println(name);
	name=this.setName();
	pClass = this.setClass();
	species = this.setSpecies();
	background = this.setBackground();
	PCharacter playerCharacter = new PCharacter(name, pClass, species, background);
	this.displayCharacter(playerCharacter);
	//Display Classes from Classes Enum
	
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