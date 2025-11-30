package ie.tus.project;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Scanner;
import ie.tus.project.Classes;
//Fiels are final and private automatically
 public record PCharacter(String name, Classes pClass, Species species, Background background, int[] stats,int  level, int hp, LocalDateTime createdTime,LocalDateTime lastEdited) implements Describable   {
	
//	private String name;
//	private Species species;
//	private Classes pClass;
//	private Background background;
	
	public PCharacter{
		/*this.pClass = pClass;
		this.species = species;
		this.background = background;
	//	this.stats = stats;*/
		if (name == null || pClass == null || species == null || background == null) {
	        throw new IllegalArgumentException("Name, class, species, and background cannot be null");
	    }
	    if (stats == null || stats.length != 6) {
	        throw new IllegalArgumentException("Stats must be an array of 6 integers");
	    }
	    for (int s : stats) {
	        if (s < 3 || s > 20) {
	            throw new IllegalArgumentException("Each stat must be between 3 and 20: " + Arrays.toString(stats));
	        }
	    }
	    if (createdTime == null) {
	    	createdTime = LocalDateTime.now();
	    }
	    if (lastEdited == null) {
	        lastEdited = createdTime;
	    }
	    stats = Arrays.copyOf(stats, stats.length);
	   // level =1;
	    int conMod = (stats[2]-10)/2;
	    hp = pClass.hitDie() + conMod;
	    LocalDateTime createdAt;
	    LocalDateTime lastEditedAt;

	}
	
    public PCharacter(String name, Classes pClass,Species species, Background background,int[] stats) {
    	
    	this(name,pClass,species,background,Arrays.copyOf(stats, stats.length),1,Math.max(1, pClass.hitDie() +(stats[2] - 10) / 2),LocalDateTime.now(),LocalDateTime.now());}
	
	// Overloaded constructor with default background
    public PCharacter(String name, Classes pClass, Species species) {
        this(name, pClass, species, Background.ACOLYTE, new int[]{10,10,10,10,10,10},0,0,LocalDateTime.now(),LocalDateTime.now());
    }
	//Object Ability Score Improvements
	//Abstract Level up
	
	
	//HP
/*	PCharacter(String name){
		this.name = name;
	}*/
    
    private static final DateTimeFormatter FORMATTER =DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    
    public static int abilityModifier(int abilityScore) {
    	return (abilityScore-10)/2 ;
    }
    
    @Override
    public int[] stats() {
        return Arrays.copyOf(stats, stats.length);
    }
    
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
	    String[] abilities = {"Str", "Dex", "Con","Int", "Wis", "Cha"};
	    StringBuilder sb = new StringBuilder();
	    sb.append("Character Details\n");
	    sb.append("-----------------\n");
	    sb.append("Name: ").append(name).append("\n");
	    sb.append("Class: ").append(pClass).append("\n");
	    sb.append("Species: ").append(species).append("\n");
	    sb.append("Background: ").append(background).append("\n");
	    sb.append("Level: ").append(level).append("\n");
	    sb.append("HP: ").append(hp).append("\n\n");
	    sb.append("Ability Scores\n");
	    
	    for (int i = 0; i < stats.length; i++) {
	        int mod = abilityModifier(stats[i]);
	        sb.append(String.format(
	            "%s: %d (%+d)%n",
	            abilities[i], stats[i], mod
	        ));
	    }
	    sb.append("Created:     ").append(createdTime).append("\n");
	    sb.append("Last Edited: ").append(lastEdited).append("\n");


	    return sb.toString();
	}
	
	public static int readInt(String input, int min, int max) {
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
	
	public int maxHP() {
		//+1 for every 2 points above 10;
		//int con = stats[2];
		int conModifier =0;
		conModifier = (stats[2]-10)/2;
		return pClass.hitDie() + conModifier;
	}
	

	
	public void levelUp() {}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return String.format(
	            "%s is a level %d %s %s with background %s. HP: %d. Stats: %s",
	            name, level, species, pClass, background, hp, Arrays.toString(stats)
	        );
	}

}

