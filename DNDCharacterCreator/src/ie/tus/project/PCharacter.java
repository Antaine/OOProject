package ie.tus.project;

import java.util.Scanner;
import java.util.Scanner;
//Fiels are final and private automatically
 public record PCharacter(String name, Classes pClass, Species species, Background background)   {
	
//	private String name;
//	private Species species;
//	private Classes pClass;
//	private Background background;
	
	public PCharacter(String name, Classes pClass, Species species, Background background){
		this.name = name;
		this.pClass = pClass;
		this.species = species;
		this.background = background;
		
	}
	
	// Overloaded constructor with default background
    public PCharacter(String name, Classes pClass, Species species) {
        this(name, pClass, species, Background.ACOLYTE); // choose default
    }
	//Object Ability Score Improvements
	//Abstract Level up
	
	
	//HP
/*	PCharacter(String name){
		this.name = name;
	}*/
	private String setName() {
		IO.println("Enter Character Name");
		return IO.readln();
	}
	
	private Classes setClass() {
		var classNumber = readInt("Select Class:",1,Classes.values().length);
		var selected = Classes.values()[classNumber-1];
		IO.println("Class Selected "+selected);
		return selected;
	}
	
	private Species setSpecies() {
		var speciesNumber = readInt("Select Species:",1,Species.values().length);
		var selected = Species.values()[speciesNumber-1];
		IO.println("Species Selected "+selected);
		return selected;
	}
	
	private Background setBackground() {
		var backgroundNumber = readInt("Select Background:",1,Background.values().length);
		var selected = Background.values()[backgroundNumber-1];
		IO.println("Background Selected "+selected);
		return selected;
	}
	
	public String getName() {
		return name;
	}
	
	public Classes getPClass() {
		return pClass;
	}
	
	public Species getSpecies() {
		return species;
	}
	
	public Background getBackground() {
		return background;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Character Details\n"+"name: "+name+"\nSpecies: "+species+"\nBackground: "+background;
	}
	
	private int readInt(String input, int min, int max) {
		int val;
		 Scanner sc = new Scanner(System.in);
		do {
	        System.out.println(input);
	        if (sc.hasNextInt()) {
	            val = sc.nextInt();
	            if (val >= min && val <= max) return val;
	            else System.out.println("Enter a number between " + min + " and " + max);
	        } else {
	            System.out.println("Invalid input. Enter a number.");
	            sc.next();
	        }
	    } while(true);
	}
	
	
	public void levelUp() {}

}

