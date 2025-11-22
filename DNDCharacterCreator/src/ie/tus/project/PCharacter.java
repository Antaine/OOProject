package ie.tus.project;

 public class PCharacter   {

	private String name;
	private Species species;
	private Classes pClass;
	private Background background;
	
	public PCharacter(String name, Classes pClass, Species species, Background background){
		this.name = name;
		this.pClass = pClass;
		this.species = species;
		this.background = background;
		
	}
	//Object Ability Score Improvements
	//Abstract Level up
	
	
	//HP
/*	PCharacter(String name){
		this.name = name;
	}*/
	private void setName() {
		
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
	
	
	
	public void levelUp() {}

}

