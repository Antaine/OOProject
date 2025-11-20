import java.util.Scanner;

import ie.tus.project.Classes;
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
	//Enter Character Details
	//IO.println("Enter Character Name");
	//String name =IO.readln();
	int pClass =0;
//	IO.println(name);
	this.setName();
	this.setClass();
	//Display Classes from Classes Enum
	
}

public void setName() {
	IO.println("Enter Character Name");
	String name =IO.readln();
	IO.println(name);
}

public void setClass() {
	int  pClass =0;
	do{System.out.println("1. Create Character");
	for (Classes c : Classes.values()) {
        System.out.println((c.ordinal() + 1) + ". " + c);
    }
	 IO.println("Select Class");
	 pClass = sc.nextInt();
     IO.println(pClass);
     Classes selectedClass =Classes.values()[pClass-1];
     System.out.println(selectedClass );
}while(pClass <1 || pClass >12);
}
