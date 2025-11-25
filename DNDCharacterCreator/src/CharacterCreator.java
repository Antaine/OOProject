import java.nio.channels.SelectableChannel;
import java.util.Scanner;
import ie.tus.project.PCharacter;
import ie.tus.project.Background;
import ie.tus.project.Classes;
import ie.tus.project.Species;
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
	System.out.println("3. Display Characters Character");
	System.out.println("4. Save Characters to File");
	System.out.println("5. Load Characters from File");
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
		case 3:
			displayAllCharacters();
			break;
		case 4:
			saveCharactersToFile();
			break;
		case 5:
			characters = loadCharacters();
			if(characters.isEmpty()) {System.out.println("No Saved Characters on File");}
			else {characters.forEach(System.out::println);}
			break;
		default:
			IO.println("Invalid Option\n");
			break;
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
	characters.add(playerCharacter);
	System.out.println("Character saved!\n");
	//Display Classes from Classes Enum
	
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

public String toJson(PCharacter pc) {
	StringBuilder sb = new StringBuilder();
	sb.append("{\n");
	sb.append("  \"name\": \"").append(pc.name()).append("\",\n");
    sb.append("  \"class\": \"").append(pc.pClass()).append("\",\n");
    sb.append("  \"species\": \"").append(pc.species()).append("\",\n");
    sb.append("  \"background\": \"").append(pc.background()).append("\",\n");
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
		var entries = rawCharacters.split("},\\{");
		
		for(int i =0; i< entries.length; i++) {
			var jsonCharacter = entries[i];
			if (!jsonCharacter.startsWith("{")) jsonCharacter = "{" + jsonCharacter;
            if (!jsonCharacter.endsWith("}")) jsonCharacter = jsonCharacter + "}";
            String name = extractValue(jsonCharacter, "name");
            Classes pClass = Classes.valueOf(extractValue(jsonCharacter, "class"));
            Species species = Species.valueOf(extractValue(jsonCharacter, "species"));
            Background background = Background.valueOf(extractValue(jsonCharacter, "background"));
            String statsString = jsonCharacter.substring(jsonCharacter.indexOf('[')+1, jsonCharacter.indexOf(']'));
            int stats[] = Arrays.stream(statsString.split(","))
            		.map(String::trim)
            		.mapToInt(Integer::parseInt)
            		.toArray();
            characters.add(new PCharacter(name, pClass, species, background, stats));
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


