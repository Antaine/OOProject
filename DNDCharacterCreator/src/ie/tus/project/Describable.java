package ie.tus.project;

public sealed interface Describable permits PCharacter {
	String description();
	
	 default String shortDescription() {
	        return "A describable object";
	    }

	    static void print(Describable d) {
	        System.out.println(d.description());
	    }

}
