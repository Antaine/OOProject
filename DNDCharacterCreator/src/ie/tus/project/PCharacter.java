package ie.tus.project;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Scanner;
import ie.tus.project.Classes;
//Fiels are final and private automatically
//Player Character Class
public final class PCharacter extends CharacterBase implements Describable {

	//Variables
    private final Classes pClass;
    private final Species species;
    private final Background background;
    private final AuditInfo auditInfo;  
    
	//Constructor
	public PCharacter(String name, Classes pClass, Species species, Background background,int[] stats,int level,AuditInfo auditInfo) {
		
		
		//Error Handling
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
	    super(name, level, Arrays.copyOf(stats, stats.length));
	    this.pClass =pClass;
	    this.species = species;
	    this.background = background;
	    this.auditInfo=auditInfo;
	    calculateHP();

	}
	
    //Constructor
    public PCharacter(String name, Classes pClass, Species species, Background background, int[] stats) {
        this(name, pClass, species, background,stats,1, AuditInfo.createNew()); 
    }

    // Constructor with default background and default stats
    public PCharacter(String name, Classes pClass, Species species) {
        this(name, pClass, species, Background.ACOLYTE, new int[]{10, 10, 10, 10, 10, 10});
    }
	
	//Getters & Setters
    public Classes pClass() { return pClass; }
    public Species species() { return species; }
    public Background background() { return background; }
    public AuditInfo audit() { return auditInfo; }
    public int[] getStats() { return Arrays.copyOf(stats, stats.length); }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public AuditInfo getAuditInfo() { return auditInfo; }
    public String name() {return name;}
    public AuditInfo auditInfo() {return auditInfo;}
	public String getName() {return name;}
	public Classes getPClass() {return pClass;}
	public Species getSpecies() {return species;}
	public Background getBackground() {return background;}
    
	//Set Character Name
	private String setName() {
		IO.println("Enter Character Name");
		return IO.readln();
	}
	//Set Character Class
	private Classes setClass() {
		var classNumber = readInt("Select Class:",1,Classes.values().length);
		var selected = Classes.values()[classNumber-1];
		IO.println("Class Selected "+selected);
		return selected;
	}
	//Set Character Species
	private Species setSpecies() {
		var speciesNumber = readInt("Select Species:",1,Species.values().length);
		var selected = Species.values()[speciesNumber-1];
		IO.println("Species Selected "+selected);
		return selected;
	}
	//Background
	private Background setBackground() {
		var backgroundNumber = readInt("Select Background:",1,Background.values().length);
		var selected = Background.values()[backgroundNumber-1];
		IO.println("Background Selected "+selected);
		return selected;
	}
	//Calculate Hit Points
    @Override
    protected void calculateHP() {
        int conMod = (stats[2] - 10) / 2;
        this.hp = Math.max(1,pClass.hitDie() + conMod + (level - 1));
    }
    
	//Calculate Ability Score Modifier
    public static int abilityModifier(int abilityScore) {return (abilityScore-10)/2 ;}
    //Format Dates
    private static final DateTimeFormatter FORMATTER =DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
	
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
	    
	    //Stat Array
	    for (int i = 0; i < stats.length; i++) {
	    	//Calculate Stat Modifier
	        int mod = abilityModifier(stats[i]);
	        sb.append(String.format("%s: %d (%+d)%n",abilities[i], stats[i], mod));
	    }
	    //Audit Info - Time Created & Time Last Edited
	    sb.append("Created:     ").append(auditInfo.created()).append("\n");
	    sb.append("Last Edited: ").append(auditInfo.lastEdited()).append("\n");
	    return sb.toString();
	}
	
	//Read Int for Menu Logic
	public static int readInt(String input, int min, int max) {
		int val;
		Scanner sc = new Scanner(System.in);
		do {
	        System.out.println(input);
	        if (sc.hasNextInt()) {
	            val = sc.nextInt();
	            if (val >= min && val <= max) return val;
	            else System.out.println("Enter a number between " + min + " and " + max);
	        } 
	        else{
	            System.out.println("Invalid input. Enter a number.");
	            sc.next();
	        }
	    } while(true);
	}
	
	//Calculate MaxHP with Hit Die + Con Modifier
	public int maxHP() {
		//+1 for every 2 points above 10;
		int conModifier = (stats[2]-10)/2;
		return pClass.hitDie() + conModifier;
	}
	
	public void levelUp() {}

	//Character Description
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return String.format(
				"%s is a level %d %s %s with background %s. HP: %d. Stats: %s",
	            getName(), getLevel(), species, pClass, background, getHp(), Arrays.toString(stats));
	}
}