package ie.tus.project;
//Dungeons & Dragons 2024 Classes & Their Hit Dies Value(D6, D8 , D10, D12)
public enum Classes {Barbarian(12),Bard(8),Cleric(8),Druid(8),Fighter(10),Monk(8),Paladin(10),Ranger(10),Rogue(8),Sorcerer(6),Warlock(8),Wizard(6);

//Type of Dice used for Hit Points
private final int hitDie;	

Classes(int hitDie){this.hitDie = hitDie;}
public int hitDie() {return hitDie;}

}




