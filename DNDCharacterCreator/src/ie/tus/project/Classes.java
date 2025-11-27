package ie.tus.project;
//Dungeons & Dragons 2024 Classes 
public enum Classes {Barbarian(12),Bard(8),Cleric(8),Druid(8),Fighter(10),Monk(8),Paladin(10),Ranger(10),Rogue(8),Sorcerer(6),Warlock(8),Wizard(6);
	
/*	public Classes getPClass(int classNum) {
		for (Classes c : Classes.values()) {
	        System.out.println((c.ordinal() + 1) + ". " + c);
	    }
	return Classes.values()[classNum-1];
}*/

private final int hitDie;	
Classes(int hitDie){this.hitDie = hitDie;
}
public int hitDie() {
    return hitDie;
}
}




