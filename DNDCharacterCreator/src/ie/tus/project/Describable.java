package ie.tus.project;

public sealed interface Describable permits PCharacter, CharacterBase {
	String description();
	
	//Default Method
	 default String shortDescription() {
	        return "A describable object";
	    }
	//Describable Method
	 static void print(Describable d) {
		 System.out.println(d.description());
	 }
}
