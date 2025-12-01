import java.nio.channels.SelectableChannel;
import java.util.Arrays;
import java.util.Scanner;
import ie.tus.project.PCharacter;
import ie.tus.project.Background;
import ie.tus.project.Classes;
import ie.tus.project.Species;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import ie.tus.project.AuditInfo;
Scanner sc = new Scanner(System.in);
private static List<PCharacter> characters = new ArrayList<>();

//Main Method Compact Source File
void main(){
	//Variables
	int input, sentinel =1;
	IO.println("Welcome to the D&D 2024 Character Creator");
	System.out.println("Please select an option");
	
	//Main Loop - Run until 0 is selected
	do{
		System.out.println("1. Create Character");
		System.out.println("2. Quick Character");
		System.out.println("3. Display Characters");
		System.out.println("4. Save Characters to File");
		System.out.println("5. Load Characters from File");
		System.out.println("6. Select Character");
		System.out.println("7. Delete Character");
		System.out.println("0. Exit Aplication");
		//Check For Input
		if (sc.hasNextInt()) {input = sc.nextInt();}
		//Restart Loop in Invalid Inout
		else {
	        System.out.println("Invalid input, please enter a valid number");
	        sc.next();
	        continue; // restart loop
	    }
		
		//Input Switch
		switch (input) {
		    case 1 -> createCharacter();
		    case 2 -> quickCharacter();
		    case 3 -> displayAllCharacters();
		    case 4 -> saveCharactersToFile();
		    case 5 -> {
		        characters = loadCharacters();
		        if (characters.isEmpty())
		            System.out.println("No Saved Characters on File");
		        else
		            characters.forEach(System.out::println);
		    }
		    case 6 -> selectCharacter();
		    case 7 -> deleteCharacter();
		    case 0 -> sentinel =0;
		    default -> System.out.println("Invalid option");
		}
	}while(sentinel == 1);
}

//Displays List of Saved Characters and Index
private int chooseCharacterIndex(String prompt) {
	//Check List isn't Empty
    if (characters.isEmpty()) {
        System.out.println("No characters available.");
        return -1;
    }
    
    //Outputs Character Names and the Index to access them
    System.out.println(prompt);
    for (int i = 0; i < characters.size(); i++) {
        System.out.printf("%d. %s%n", i + 1, characters.get(i).getName());
    }
    return readInt("Enter character number:", 1, characters.size()) - 1;
}

//Display All Characters stored in Characters Array
private void displayAllCharacters() {
	// Check Characters Exist & Return If not
	if(characters.isEmpty()) {
		System.out.println("No Characters Loaded");
		return;
	}
	//Output Characters
	System.out.println("Saved Characters:");
	int i =1;
	for(var c : characters) {
		System.out.println("\nCharacter #"+i++);
		System.out.println(c);
	}
}

private void selectCharacter() {
	//Generate Character Selection index
    int index = chooseCharacterIndex("Select a Character:");
    //Return if No Characters
    if (index == -1) return;
    //Select Character, Display Description and Confirm
    PCharacter selectedCharacter = characters.get(index);
    System.out.println("\n--- Character Description ---");
    System.out.println(selectedCharacter.description());
    System.out.println("Is this the correct character? (Y/N)");
    String confirm = IO.readln().trim().toUpperCase();
    if (!confirm.equals("Y")) {
        System.out.println("Selection cancelled.");
        return;
    }
    //Edit Character
    System.out.println("Would you like to edit this character? (Y/N)");
    String edit = IO.readln().trim().toUpperCase();
    if (edit.equals("Y")) {
        PCharacter editedCharacter = editCharacter(selectedCharacter);
        characters.set(index, editedCharacter);
        saveCharactersToFile();
        System.out.println("Character updated successfully.");
    }
}

//Selects and Deletes Character
private void deleteCharacter() {
    int index = chooseCharacterIndex("Select a Character to Delete:");
    //Check Characters Exist
    if (index == -1) return;
    //Confirm Deletion
    PCharacter selected = characters.get(index);
    System.out.println("Are you sure you want to delete " + selected.getName() + "? (Y/N)");
    String confirm = IO.readln().trim().toUpperCase();
    if (!confirm.equals("Y")) {
        System.out.println("Deletion cancelled.");
        return;
    }
    //Remove from Array and Overwrite File
    characters.remove(index);
    saveCharactersToFile();
    System.out.println("Character deleted successfully.");
}

//Display Stats Array with name & Ability Modifier
public void displayAssignedStats(int[] assignedStats) {
    String[] abilities = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    System.out.println("\nCharacter Ability Scores:");
    //Ability Score Loop
    for (int i = 0; i < abilities.length; i++) {
    	int mod = PCharacter.abilityModifier(assignedStats[i]);
    	//Check if positive or negative
    	String modString = (mod >= 0 ? "+":"")+mod;
        System.out.printf("%-14s : %2d (%s)%n", abilities[i], assignedStats[i], modString);
    }
}

//Edit Character
private PCharacter editCharacter(PCharacter originalCharacter) {
	Classes newClass =originalCharacter.pClass();
	Species newSpecies =originalCharacter.species();
	Background newBackground = originalCharacter.background();
	int newLevel = originalCharacter.getLevel();
    int[] newStats = Arrays.copyOf(originalCharacter.getStats(), originalCharacter.getStats().length);
	
    //Edit Menu 0-5
	int selected;
	 do{
		 System.out.println("\n--- Edit Character ---");
	     System.out.println("1. Change Class");
	     System.out.println("2. Change Species");
	     System.out.println("3. Change Background");
	     System.out.println("4. Change Level");
	     System.out.println("5. Edit Ability Scores");
	     System.out.println("0. Finish Editing");
	     selected = readInt("Select an option:", 0, 5);
	     //Call Methods
	     switch (selected) {
         case 1 -> newClass = setClass();
         case 2 -> newSpecies = setSpecies();
         case 3 -> newBackground = setBackground();
         case 4 -> {newLevel = readInt("Enter new level (1–3):", 1, 3);}
         case 5 -> originalCharacter = editStats(originalCharacter);
         case 0 -> System.out.println("Finishing edit...");
     }

	}while(selected !=0);
	 
	 //Return Edited Character
	 return new PCharacter(originalCharacter.name(),
			 newClass,newSpecies,newBackground,originalCharacter.getStats(),newLevel,originalCharacter.getAuditInfo() );

}

//Edit Stats Array of Character
private PCharacter editStats(PCharacter pc) {
	//Defensive Copy to stop modification of Character stats by accident
    int[] stats = Arrays.copyOf(pc.getStats(), pc.getStats().length); // defensive copy already
    //Points to Spend
    int points = 3;
    String[] abilities = {"Strength", "Dexterity", "Constitution","Intelligence", "Wisdom", "Charisma"};
    
    //Spend Points
    while (points > 0) {
        System.out.println("\nCurrent Stats (Points left: " + points + ")");
        //Display Stats
        for (int i = 0; i < stats.length; i++) {
            int mod = (stats[i] - 10) / 2;
            System.out.printf("%d. %-13s : %2d (%+d)%n",i + 1, abilities[i], stats[i], mod);
        }
        //Select Stat
        int choice = readInt("Select a stat to increase (1–6) or 0 to finish:",0, 6);
        if (choice == 0) break;
        //Check Stat is not going over 20
        int index = choice - 1;
        if (stats[index] >= 20) {
            System.out.println("That stat is already at 20.");
            continue;
        }
        //Check Stat is not going under 3
        if (stats[index] <= 3) {
            System.out.println("That stat is already at 3.");
            continue;
        }
        //While Stats betwen 3 & 20
        while (true) {
        	System.out.println("\nAbility Scores (Adjustment points: " + points + ")");
            for(int i = 0; i < stats.length; i++) {
            	//Caclulate Stat Modifier
            	int mod = (stats[i] - 10) / 2;
                System.out.printf("%d. %-13s : %2d (%+d)%n",i + 1, abilities[i], stats[i], mod);
            }
            //Select Stats 1-6,
            System.out.println( "\nSelect stat to modify:\n" +"1–6 = increase stat by 1\n" +
            "-1 to -6 = decrease stat by 1\n" +"0 = finish editing");
            int input;
            //Check Input is in range
            try {
                input = Integer.parseInt(IO.readln().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            
            //Exit if Input equals 0
            if (input == 0) break;
            int index1 = Math.abs(input) - 1;
            //Check if Value is poisitive or negative 
            int delta = input > 0 ? 1 : -1;
            if (index1 < 0 || index1 >= 6) {
                System.out.println("Invalid stat selection.");
                continue;
            }
            //Add + or - stat based on delta
            int newValue = stats[index1] + delta;
            if (newValue < 3 || newValue > 20) {
                System.out.println("Stat must remain between 3 and 20.");
                continue;
            }
            //Check Remaining Points
            if (delta > 0 && points < 1) {
                System.out.println("No points remaining.");
                continue;
            }
            //Update Stat and Points
            stats[index1] = newValue;
            points -= delta;
            System.out.println(abilities[index1] + " updated to " +stats[index1] + " (Points left: " + points + ")");
        }}
    //Recalculate HP
    int conMod = (stats[2] - 10) / 2;
    int newHp = Math.max(1,pc.pClass().hitDie()+ (pc.getLevel() - 1) * (pc.pClass().hitDie() / 2 + 1)+ conMod * pc.getLevel());

    //Return Edited Character
    return new PCharacter(pc.name(),pc.pClass(),pc.species(),pc.background(),stats,newHp,AuditInfo.createNew());
}

//Create Character
public void createCharacter() {
	var name = setName();
	var pClass = setClass();
	var species = setSpecies();
	var background = setBackground();
	//Rolled Stats
	var rolledStats = rollStatsWithReroll();
	//Add Ability Score Improvement to Character
	int[] abilityScores = assignStatsToAbilities(rolledStats);
	abilityScoreImprovements(abilityScores);
	//Display New Scores
	displayAssignedStats(abilityScores);
	//Add Character
	PCharacter playerCharacter = new PCharacter(name,pClass,species,background,abilityScores,1,                     AuditInfo.createNew());
	displayCharacter(playerCharacter);
	characters.add(playerCharacter);
	System.out.println("Character saved!\n");
}

//Apply Ability Score Improvements to Stats
private void abilityScoreImprovements(int[] stats) {
	// TODO Auto-generated method stub
	String[] abilities = {"Strength", "Dexterity", "Constitution","Intelligence", "Wisdom", "Charisma"};
	int improvementPoints =3;
    System.out.println("\nAbility Score Improvements");
    System.out.println("You can spend up to 3 points.(Max of 20)");
    int totalAbilityScore = sum(stats);
    int maxSpend =3;
    while(improvementPoints >0) {
    	while (improvementPoints > 0) {
            displayAssignedStats(stats);
            System.out.println("Updated total ability score: " + totalAbilityScore);
            System.out.println("Points remaining: " + improvementPoints);
            int choice = -1;
            //Select Stat
            do {
                System.out.print("Choose an ability (1–6): ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt() - 1;
                } else {
                    sc.next();
                }
            } while (choice < 0 || choice >= 6);

            //Check Stat is in range
            if (stats[choice] >= 20) {
                System.out.println("That ability is already at max (20).");
                continue;
            }
            if (stats[choice] <= 3) {
                System.out.println("That ability is already at min (3).");
                continue;
            }
           //Set Max Spend for stat to stay below 20
            maxSpend = Math.min(improvementPoints, 20 - stats[choice]);
            int spend;
            //Select How many points to spend
            do {
                System.out.print("Spend points (1–" + maxSpend + "): ");
                while (!sc.hasNextInt()) sc.next();
                spend = sc.nextInt();
            } while (spend < 1 || spend > maxSpend);

            //Apply Points
            stats[choice] += spend;
            improvementPoints -= spend;
            //Display Stat & Points
            System.out.println(abilities[choice] + " increased to " + stats[choice]);
            totalAbilityScore = sum(stats);  // varargs usage
            //Display Total Points
            System.out.println("Updated total ability score: " + totalAbilityScore);
        }
        System.out.println("Ability Score Improvements complete.\n");
        totalAbilityScore = sum(stats);  // varargs usage
        System.out.println("Updated total ability score: " + totalAbilityScore);
    }
}

public void createCharacter(String name, Classes pClass, Species species) {

    name = setName();
    pClass = setClass();
    species = setSpecies();
    Background background = setBackground();

    // Roll and assign stats
    int[] stats = rollStatsWithReroll();
    int[] abilityScores = assignStatsToAbilities(stats);

    abilityScoreImprovements(abilityScores);
    displayAssignedStats(abilityScores);

    // ✅ AuditInfo created internally
    PCharacter playerCharacter =
            new PCharacter(name, pClass, species, background, abilityScores);

    displayCharacter(playerCharacter);
    characters.add(playerCharacter);

    System.out.println("Character saved!\n");
}

//Getters & Setters
//Set Character Name
public String setName() {
	IO.println("Enter Character Name");
	String name =IO.readln();
	IO.println(name);
	return name;
}
//Set Character class from Classes Enum
public Classes setClass() {
	int  pClass =0;
	Classes selectedClass;
	//Loop Through Classes
	do{System.out.println("1. Select Class");
	for (Classes c : Classes.values()) {System.out.println((c.ordinal() + 1) + ". " + c);}
	//Select Class
	IO.println("Select Class");
	pClass = sc.nextInt();
    IO.println(pClass);
    selectedClass =Classes.values()[pClass-1];
    System.out.println("Class is: "+selectedClass );
}while(pClass <1 || pClass >12);
	return selectedClass;
}

//Select Species from Species Enum
public Species setSpecies() {
	int  species =0;
	Species selectedSpecies;
	//Loop Through Species
	do{System.out.println("Select Species");
	for (Species s : Species.values()) {System.out.println((s.ordinal() + 1) + ". " + s);}
	//Select Species
	species = sc.nextInt();
    IO.println(species);
    selectedSpecies =Species.values()[species-1];
    System.out.println(selectedSpecies );
}while(species <1 || species >10);
	return selectedSpecies;
}
//Set Background from Enum Background
public Background setBackground() {
	int  bg =0;
	Background selectedBackground;
	//Loop Through Backgrounds
	do{System.out.println("Select Background");
	for (Background b : Background.values()) { System.out.println((b.ordinal() + 1) + ". " + b);}
	bg = sc.nextInt();
    IO.println(bg);
    selectedBackground =Background.values()[bg-1];
    System.out.println(selectedBackground );
}while(bg <1 || bg >16);
	return selectedBackground;
}

//Display To String for Character
public void displayCharacter(PCharacter pCharacter) {
	IO.println(pCharacter.toString());
}

//Simulate Rolling 4 six sided Dice and take the best 3  using Math.random
public int[] rollStats() {
	var stats = new int[6];
	//Do For Each Stat
	for(int i =0; i<6; i++) {
		var rollTotal = new int[4];
		for(int j =0; j<4;j++) {rollTotal[j] = (int)(Math.random()*6)+1;}
		Arrays.sort(rollTotal);
		stats[i] =rollTotal[1]+rollTotal[2]+rollTotal[3];
	}
	return stats;
}

//Check if Total of All Stats is below 70, if so Reroll so the Character is not week using Lambda Predicate
public int[] rollStatsWithReroll() {
	Predicate<int[]> rollAbove70 = stats -> Arrays.stream(stats).sum() >=70;
	int[] stats;
	do {
		//Roll & Calculate Total Rolled
		stats = rollStats();
		int totalRoll = Arrays.stream(stats).sum();
		System.out.println("You rolled: "+ Arrays.toString(stats));
		System.out.println("Sum of ability scores: "+ totalRoll);
		//Check if 70 or above
		if(rollAbove70.test(stats)) {
			System.out.println("Do you want to keep these stats? (Y/N)");
			String answer = IO.readln().trim().toUpperCase();
			//Confirm if User wants Stats
			if(answer.equals("Y")) {return stats;}
			System.out.println("Rerolling stats...\n");
		}
		//Reroll
		else {System.out.println("Total below <70. Rerolling...\n");}
		
	}while (true);
}

//Stingbuilder userd to convert character to Json String
public String toJson(PCharacter pc) {
	StringBuilder sb = new StringBuilder();
	//Add Character values to Stirng builder in Json Format
	sb.append("{\n");
	sb.append("  \"name\": \"").append(pc.name()).append("\",\n");
	sb.append("  \"class\": \"").append(pc.pClass().name()).append("\",\n");
	sb.append("  \"species\": \"").append(pc.species().name()).append("\",\n");
	sb.append("  \"background\": \"").append(pc.background().name()).append("\",\n");
    sb.append("  \"level\": \"").append(pc.getLevel()).append("\",\n");
    sb.append("  \"hp\": \"").append(pc.getHp()).append("\",\n");
    sb.append("  \"stats\": [");
    //Add Stats in loop
    int[] stats = pc.getStats();
    for (int i = 0; i < stats.length; i++) {
        sb.append(stats[i]);
        if (i < stats.length - 1) sb.append(", ");
    }
    sb.append("],\n");
    //End of Stats
    //TimeStamp
    sb.append("  \"auditInfo\": {\n");
    sb.append("    \"createdTime\": \"").append(pc.getAuditInfo().getCreated()).append("\",\n");
    sb.append("    \"lastEdited\": \"").append(pc.getAuditInfo().getLastEdited()).append("\"\n");
    sb.append("  }\n");
    sb.append("}\n");
    return sb.toString();
}

//Save All Characters  from array to File characters.json in JSON format
public void saveCharactersToFile() {
	try {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		//Loop Character Array
		for(int i = 0; i<characters.size(); i++) {
			sb.append(toJson(characters.get(i)));
			if(i <characters.size() -1) sb.append(",\n");
		}
		sb.append("\n");	
		//Write to File
		java.nio.file.Files.writeString(java.nio.file.Path.of("characters.json"),sb.toString());
		System.out.println("Characters saved to characters.json");
	//Error Handling
	} catch(Exception e) {
		System.out.println("Error saving characters: "+e.getMessage());
	}
}

//Varargs used to caclulated sums of inputed values(Used for Total Ability Scores)
public static int sum(int... values) {
    return Arrays.stream(values).sum();
}

public List<PCharacter> loadCharacters(){
	//Create Empty List
	var characters = new ArrayList<PCharacter>();
	
	try {
		//Look for File
		var characterFile = new File("characters.json");
		//File Does not Exist Check
		if(!characterFile.exists()) {
			System.out.println("No saved characters found.");
			return characters;
		}
		//Get Character JSON values
		var rawCharacters = Files.readString(characterFile.toPath()).trim();
		//If Characters Empty
		if(rawCharacters.equals("[]")) return characters;
		
		//If not Empty
		//Remove surronding brackets
		rawCharacters = rawCharacters.substring(1,rawCharacters.length()-1).trim();
		//Split rawJson Characters into individual Objects based on Regex for \\}\\s*,\\s*\\{
		var entries = rawCharacters.split("\\}\\s*,\\s*\\{");
		
		//Loop Through Json Characters
		for(int i =0; i< entries.length; i++) {
			//Get Character String Value
			var jsonCharacter = entries[i];
			
			//Makes sure the Json Character starts and ends with braces i
			if (!jsonCharacter.startsWith("{")) jsonCharacter = "{" + jsonCharacter;
            if (!jsonCharacter.endsWith("}")) jsonCharacter = jsonCharacter + "}";
            String name = extractValue(jsonCharacter, "name");
            
            //Get Values using extractValue that converts the JSON objects to normal Values
            Classes pClass = Classes.valueOf(extractValue(jsonCharacter, "class"));
            Species species = Species.valueOf(extractValue(jsonCharacter, "species"));
            Background background = Background.valueOf(extractValue(jsonCharacter, "background"));
            int level = Integer.parseInt(extractValue(jsonCharacter, "level"));
            int hp = Integer.parseInt(extractValue(jsonCharacter, "hp"));
            LocalDateTime createdTime = LocalDateTime.parse(extractValue(jsonCharacter, "createdTime"));
            LocalDateTime lastEdited = LocalDateTime.parse(extractValue(jsonCharacter, "lastEdited"));
            AuditInfo auditInfo = new AuditInfo(createdTime, lastEdited);
            
            //Get Stats and removes [], split on ',', remove Whitespacem convert String to Int and convertes to Array
            String statsString = jsonCharacter.substring(jsonCharacter.indexOf('[')+1, jsonCharacter.indexOf(']'));
            int stats[] = Arrays.stream(statsString.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            
            //Create character Objected and add to array
            characters.add(new PCharacter(name, pClass, species, background, stats, level, auditInfo));
		}		
	}catch(Exception e) {
		System.out.println("Error loading characters: "+e.getMessage());
	}
	return characters;
}

//Helper Method to 
private String extractValue(String json, String key) {
	  // Look for:  "key": 
    String target = "\"" + key + "\"";
    int keyIndex = json.indexOf(target);
    if (keyIndex == -1) return null;
    // Move to after the colon
    int colonIndex = json.indexOf(":", keyIndex);
    if (colonIndex == -1) return null;
    int start = colonIndex + 1;
    // Skip spaces
    while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
        start++;
    }
    // If value is quoted → String
    if (json.charAt(start) == '"') {
        start++;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
    // Else → number or bool
    int end = start;
    while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') {
        end++;
    }
    return json.substring(start, end).trim();
}

//Assign Stat roll to Ability Score
public int[] assignStatsToAbilities(int[] rolledStats) {
    String[] abilities = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    int[] assignedStats = new int[6];
    //Converts stats to a mutable list so they are removed when assinged
    List<Integer> availableStats = new ArrayList<>();
    for (int stat : rolledStats) availableStats.add(stat);
    Scanner sc = new Scanner(System.in);
    //Loop through the Abilities List
    for (int i = 0; i < abilities.length; i++) {
        System.out.println("\nAvailable stats: " + availableStats);
        int choice = -1;
        //Select the stat value you want to assign
        do {
            System.out.print("Assign a stat to " + abilities[i] + ": ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                //Check for Valid Choice
                if (availableStats.contains(choice)) {
                    break; // valid choice
                } 
                //Invalid Stat
                else {
                    System.out.println("Invalid choice. Pick a value from the available stats.");
                }
            } else {
                System.out.println("Invalid input. Enter a number.");
                sc.next(); // consume invalid token
            }
        } while (true);
        assignedStats[i] = choice;
        availableStats.remove((Integer) choice); // remove chosen stat
    }
    return assignedStats;
}

/*Helper Method Selection logic
 * Loops until input is between min & max*/
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

//Create Quick Template Character
public void quickCharacter() {
	//Overloaded Constructor
    PCharacter playerCharacter = new PCharacter( "Adventurer",Classes.Fighter,Species.HUMAN);
    displayCharacter(playerCharacter);
    //Add Quick character
    characters.add(playerCharacter);
    System.out.println("Quick Character saved!\n");
}
