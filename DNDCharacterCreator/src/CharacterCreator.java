import java.nio.channels.SelectableChannel;
import java.util.Scanner;
import ie.tus.project.PCharacter;
import ie.tus.project.Background;
import ie.tus.project.Classes;
import ie.tus.project.Species;
import java.util.function.Predicate;
Scanner sc = new Scanner(System.in);
private static List<PCharacter> characters = new ArrayList<>();
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
	System.out.println("3. Display Characters");
	System.out.println("4. Save Characters to File");
	System.out.println("5. Load Characters from File");
	System.out.println("6. Select Character");
	System.out.println("0. Exit Aplication");
		if (sc.hasNextInt()) {
	        input = sc.nextInt();
	    } else {
	        System.out.println("Invalid input, please enter a valid number");
	        sc.next(); // consume invalid token
	        continue; // restart loop
	    }
		
		switch (input) {
	    case 1 -> createCharacter();
	    case 2 -> createCharacter("Adventurer", Classes.Fighter, Species.HUMAN);
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
	    case 0 -> sentinel =0;
	    default -> System.out.println("Invalid option");
	}
	}while(sentinel == 1);


	
}

private void displayAllCharacters() {
	// TODO Auto-generated method stub
	if(characters.isEmpty()) {
		System.out.println("No Characters Loaded");
		return;
	}
	
	System.out.println("Saved Characters:");
	int i =1;
	for(var c : characters) {
		System.out.println("\nCharacter #"+i++);
		System.out.println(c);
	}
}

private void selectCharacter() {
	if (characters.isEmpty()) {
        System.out.println("No characters available to select.");
        return;
    }
	System.out.println("Select a Character:");
	 for (int i = 0; i < characters.size(); i++) 
	 {System.out.printf("%d. %s%n", i + 1, characters.get(i).getName());}
	 int input = readInt("Enter character number:", 1, characters.size());
	 PCharacter selectedCharacter = characters.get(input - 1);
	 System.out.println("\n--- Character Description ---");
	 System.out.println(selectedCharacter.description());
	 System.out.println("Is this the correct character? (Y/N)");
	 String confirm = IO.readln().trim().toUpperCase();
	 if (!confirm.equals("Y")) {System.out.println("Selection cancelled.");return;}
	 
	 System.out.println("Would you like to edit this character? (Y/N)");
	 String edit = IO.readln().trim().toUpperCase();
	 if (edit.equals("Y")) {
	       PCharacter editedCharacter = editCharacter(selectedCharacter);
	       characters.set(input-1, editedCharacter);
	       System.out.println("Character updated successfully");
	    } else {
	        System.out.println("Character selection complete.");
	    }
}

public void displayAssignedStats(int[] assignedStats) {
    String[] abilities = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    System.out.println("\nCharacter Ability Scores:");
    for (int i = 0; i < abilities.length; i++) {
    	int mod = PCharacter.abilityModifier(assignedStats[i]);
    	//Check if positive or negative
    	String modString = (mod >= 0 ? "+":"")+mod;
        System.out.printf("%-14s : %2d (%s)%n", abilities[i], assignedStats[i], modString);
    }
}

private PCharacter editCharacter(PCharacter originalCharacter) {
	Classes newClass =originalCharacter.pClass();
	Species newSpecies =originalCharacter.species();
	Background newBackground = originalCharacter.background();
	int newLevel = originalCharacter.level();
	
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
	     
	     switch (selected) {
         case 1 -> newClass = setClass();
         case 2 -> newSpecies = setSpecies();
         case 3 -> newBackground = setBackground();
         case 4 -> {newLevel = readInt("Enter new level (1–3):", 1, 3);}
         case 5 -> originalCharacter = editStats(originalCharacter);
         case 0 -> System.out.println("Finishing edit...");
     }

	}while(selected !=0);
	 
	 return new PCharacter(originalCharacter.name(),
			 newClass,newSpecies,newBackground,originalCharacter.stats(),newLevel,originalCharacter.hp());

}

private PCharacter editStats(PCharacter pc) {
    int[] stats = pc.stats(); // defensive copy already
    int points = 3;

    String[] abilities = {
        "Strength", "Dexterity", "Constitution",
        "Intelligence", "Wisdom", "Charisma"
    };

    while (points > 0) {
        System.out.println("\nCurrent Stats (Points left: " + points + ")");
        for (int i = 0; i < stats.length; i++) {
            int mod = (stats[i] - 10) / 2;
            System.out.printf(
                "%d. %-13s : %2d (%+d)%n",
                i + 1, abilities[i], stats[i], mod
            );
        }

        int choice = readInt(
            "Select a stat to increase (1–6) or 0 to finish:",
            0, 6
        );

        if (choice == 0) break;

        int index = choice - 1;

        if (stats[index] >= 20) {
            System.out.println("That stat is already at 20.");
            continue;
        }

        while (true) {
            System.out.println("\nAbility Scores (Adjustment points: " + points + ")");
            for (int i = 0; i < stats.length; i++) {
                int mod = (stats[i] - 10) / 2;
                System.out.printf(
                    "%d. %-13s : %2d (%+d)%n",
                    i + 1, abilities[i], stats[i], mod
                );
            }

            System.out.println(
                "\nSelect stat to modify:\n" +"1–6 = increase stat by 1\n" +"-1 to -6 = decrease stat by 1\n" +"0 = finish editing"
            );

            int input;
            try {
                input = Integer.parseInt(IO.readln().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (input == 0) break;

            int index1 = Math.abs(input) - 1;
            int delta = input > 0 ? 1 : -1;

            if (index1 < 0 || index1 >= 6) {
                System.out.println("Invalid stat selection.");
                continue;
            }

            int newValue = stats[index1] + delta;

            if (newValue < 3 || newValue > 20) {
                System.out.println("Stat must remain between 3 and 20.");
                continue;
            }

            if (delta > 0 && points < 1) {
                System.out.println("No points remaining.");
                continue;
            }

            stats[index1] = newValue;
            points -= delta;

            System.out.println(
                abilities[index1] + " updated to " +
                stats[index1] + " (Points left: " + points + ")"
            );
        }}
    int conMod = (stats[2] - 10) / 2;
    int newHp = Math.max(
        1,
        pc.pClass().hitDie()
        + (pc.level() - 1) * (pc.pClass().hitDie() / 2 + 1)
        + conMod * pc.level()
    );


    return new PCharacter(
        pc.name(),
        pc.pClass(),
        pc.species(),
        pc.background(),
        stats,
        pc.level(),
        newHp
    );
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
	int[] abilityScores = assignStatsToAbilities(stats);
	abilityScoreImprovements(abilityScores);
	displayAssignedStats(abilityScores);
	var  playerCharacter = new PCharacter(name, pClass, species, background, abilityScores);
	displayCharacter(playerCharacter);
	characters.add(playerCharacter);
	System.out.println("Character saved!\n");
	//Display Classes from Classes Enum
	
}

private void abilityScoreImprovements(int[] stats) {
	// TODO Auto-generated method stub
	String[] abilities = {"Strength", "Dexterity", "Constitution","Intelligence", "Wisdom", "Charisma"};
	int improvementPoints =3;
    System.out.println("\nAbility Score Improvements");
    System.out.println("You can spend up to 3 points.(Max of 20)");
    
  while(improvementPoints >0) {
    	while (improvementPoints > 0) {
            displayAssignedStats(stats);
            System.out.println("Points remaining: " + improvementPoints);

            int choice = -1;
            do {
                System.out.print("Choose an ability (1–6): ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt() - 1;
                } else {
                    sc.next();
                }
            } while (choice < 0 || choice >= 6);

            if (stats[choice] >= 20) {
                System.out.println("That ability is already at max (20).");
                continue;
            }

            int maxSpend = Math.min(improvementPoints, 20 - stats[choice]);

            int spend;
            do {
                System.out.print("Spend points (1–" + maxSpend + "): ");
                while (!sc.hasNextInt()) sc.next();
                spend = sc.nextInt();
            } while (spend < 1 || spend > maxSpend);

            stats[choice] += spend;
            improvementPoints -= spend;

            System.out.println(
                abilities[choice] + " increased to " + stats[choice]
            );
        }

        System.out.println("Ability Score Improvements complete.\n");
    }
    

}

public void createCharacter(String name, Classes pClass, Species species) {
    var playerCharacter = new PCharacter(name, pClass, species);
    displayCharacter(playerCharacter);
    characters.add(playerCharacter);
	System.out.println("Quick Character saved!\n");
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
	Predicate<int[]> rollAbove70 = stats -> Arrays.stream(stats).sum() >=70;
	int[] stats;
	do {
		stats = rollStats();
		int totalRoll = Arrays.stream(stats).sum();
		System.out.println("You rolled: "+ Arrays.toString(stats));
		System.out.println("Sum of ability scores: "+ totalRoll);
		if(rollAbove70.test(stats)) {
			System.out.println("Do you want to keep these stats? (Y/N)");
			String answer = IO.readln().trim().toUpperCase();
			if(answer.equals("Y")) {return stats;}
			System.out.println("Rerolling stats...\n");
		}
		else {System.out.println("Total below <70. Rerolling...\n");}
		
	}while (true);
}

public String toJson(PCharacter pc) {
	StringBuilder sb = new StringBuilder();
	sb.append("{\n");
	sb.append("  \"name\": \"").append(pc.name()).append("\",\n");
    sb.append("  \"class\": \"").append(pc.pClass()).append("\",\n");
    sb.append("  \"species\": \"").append(pc.species()).append("\",\n");
    sb.append("  \"background\": \"").append(pc.background()).append("\",\n");
    sb.append("  \"level\": \"").append(pc.level()).append("\",\n");
    sb.append("  \"hp\": \"").append(pc.hp()).append("\",\n");
    sb.append("  \"stats\": [");
    for(int i =0; i<pc.stats().length;i++) {
    	sb.append(pc.stats()[i]);
    	if(i<pc.stats().length -1) sb.append(",");
    
    }
	sb.append("]\n");
	sb.append("}");
	return sb.toString();
}

public void saveCharactersToFile() {
	try {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		for(int i = 0; i<characters.size(); i++) {
			sb.append(toJson(characters.get(i)));
			if(i <characters.size() -1) sb.append(",\n");
		}
		sb.append("\n");
		java.nio.file.Files.writeString(
				java.nio.file.Path.of("characters.json"),
				sb.toString()
		);
		
		System.out.println("Characters saved to characters.json");
	} catch(Exception e) {
		System.out.println("Error saving characters: "+e.getMessage());
	}
}

/*public static void checkFileCharacters() {
	File characterFile = new File("characters.json");
	
	if(!characterFile.exists()) {
		System.out.println("No saved characters file found.");
		return;
	}
	
	List<PCharacter> loaded = load
}*/

public List<PCharacter> loadCharacters(){
	var characters = new ArrayList<PCharacter>();
	try {
		var characterFile = new File("characters.json");
		if(!characterFile.exists()) {
			System.out.println("No saved characters found.");
			return characters;
		}
		var rawCharacters = Files.readString(characterFile.toPath()).trim();
		//Empty
		if(rawCharacters.equals("[]")) return characters;
		
		rawCharacters = rawCharacters.substring(1,rawCharacters.length()-1);
		var entries = rawCharacters.split("\\},\\s*\\{");
		
		for(int i =0; i< entries.length; i++) {
			var jsonCharacter = entries[i];
			if (!jsonCharacter.startsWith("{")) jsonCharacter = "{" + jsonCharacter;
            if (!jsonCharacter.endsWith("}")) jsonCharacter = jsonCharacter + "}";
            String name = extractValue(jsonCharacter, "name");
            Classes pClass = Classes.valueOf(extractValue(jsonCharacter, "class"));
            Species species = Species.valueOf(extractValue(jsonCharacter, "species"));
            Background background = Background.valueOf(extractValue(jsonCharacter, "background"));
            int level = Integer.parseInt(extractValue(jsonCharacter, "level"));
            int hp = Integer.parseInt(extractValue(jsonCharacter, "hp"));
            String statsString = jsonCharacter.substring(jsonCharacter.indexOf('[')+1, jsonCharacter.indexOf(']'));
            int stats[] = Arrays.stream(statsString.split(","))
            		.map(String::trim)
            		.mapToInt(Integer::parseInt)
            		.toArray();
            characters.add(new PCharacter(name, pClass, species, background, stats, level, hp));
		}
		
		
	}catch(Exception e) {
		System.out.println("Error loading characters: "+e.getMessage());
	}
	
	return characters;

}

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

public int[] assignStatsToAbilities(int[] rolledStats) {
    String[] abilities = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    int[] assignedStats = new int[6];

    // Convert to a mutable list
    List<Integer> availableStats = new ArrayList<>();
    for (int stat : rolledStats) availableStats.add(stat);

    Scanner sc = new Scanner(System.in);

    for (int i = 0; i < abilities.length; i++) {
        System.out.println("\nAvailable stats: " + availableStats);
        int choice = -1;

        do {
            System.out.print("Assign a stat to " + abilities[i] + ": ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (availableStats.contains(choice)) {
                    break; // valid choice
                } else {
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

