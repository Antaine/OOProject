package ie.tus.project;

public sealed interface Describable permits PCharacter, CharacterBase {

    String description();

    default String shortDescription() {
        return clean(description());
    }

    static void print(Describable d) {
        System.out.println(d.description());
    }

    private String clean(String text) {
        return text == null ? "No description available" : text.trim();
    }
}