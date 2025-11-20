package ie.tus.project;
//Dungeons & Dragons 2024 Classes 
public enum Classes {Barbarian,Bard,Cleric,Druid,Fighter,Monk,Paladin,Ranger,Rogue,Sorcerer,Warlock,Wizard;
	
	public Classes getPClass(int classNum) {
		for (Classes c : Classes.values()) {
	        System.out.println((c.ordinal() + 1) + ". " + c);
	    }
	return Classes.values()[classNum-1];
}

}




