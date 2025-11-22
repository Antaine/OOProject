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
		input = sc.nextInt();
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
	String name,pClass,species,background;
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

public String setClass() {
	int  pClass =0;
	String finalClass;
	do{System.out.println("1. Select Class");
	for (Classes c : Classes.values()) {
        System.out.println((c.ordinal() + 1) + ". " + c);
    }
	 IO.println("Select Class");
	 pClass = sc.nextInt();
     IO.println(pClass);
     Classes selectedClass =Classes.values()[pClass-1];
     System.out.println("Class is: "+selectedClass );
     finalClass = selectedClass.toString();
}while(pClass <1 || pClass >12);
	return finalClass;
	
}

public String setSpecies() {
	int  species =0;
	String finalSpecies;
	do{System.out.println("Select Species");
	for (Species s : Species.values()) {
        System.out.println((s.ordinal() + 1) + ". " + s);
    }
	 species = sc.nextInt();
     IO.println(species);
     Species selectedSpecies =Species.values()[species-1];
     System.out.println(selectedSpecies );
     finalSpecies = selectedSpecies.toString();
}while(species <1 || species >10);
	return finalSpecies;
}

public String setBackground() {
	int  bg =0;
	String finalBackground;
	do{System.out.println("Select Background");
	for (Background b : Background.values()) {
        System.out.println((b.ordinal() + 1) + ". " + b);
    }
	 bg = sc.nextInt();
     IO.println(bg);
     Background selectedBackground =Background.values()[bg-1];
     System.out.println(selectedBackground );
     finalBackground = selectedBackground.toString();
}while(bg <1 || bg >16);
	return finalBackground;
}

public void displayCharacter(PCharacter pCharacter) {
	IO.println(pCharacter.toString());
}