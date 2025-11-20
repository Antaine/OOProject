package ie.tus.project;

sealed abstract class PCharacter permits Fighter {

	private String name;
	private Race race;
	private Classes pClass;
	private Background background;
	//Object Ability Score Improvements
	//Abstract Level up
	
	
	//HP
	PCharacter(String name){
		this.name = name;
	}
	private void setName() {
		
	}
	
	private void getName() {
		
	}
	
	private void setPClass() {
		
	}
	
	private void getPClass() {
		
	}
	
	public void levelUp() {}

}

