package ie.tus.project;

 public class PCharacter   {

	private String name;
	private String species;
	private String pClass;
	private String background;
	
	public PCharacter(String name, String pClass, String species, String background){
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
	
	public String getName(PCharacter player) {
		return name;
	}
	
	public String getPClass(PCharacter player) {
		return pClass;
	}
	
	public String getSpecies(PCharacter player) {
		return species;
	}
	
	public String getBackground(PCharacter player) {
		return background;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Character Details\n"+"name: "+name+"\nSpecies: "+species+"\nBackground: "+background;
	}
	
	
	
	public void levelUp() {}

}

