package ie.tus.project.services;

import ie.tus.project.Classes;
import ie.tus.project.PCharacter;
import ie.tus.project.Species;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CharacterAnalyticsService {

    // Sorting - Comparator.comparing() and sorted()
    public List<PCharacter> sortByName(List<PCharacter> characters) {
        return characters.stream()
                .sorted(Comparator.comparing(PCharacter::getName))
                .toList();
    }

    public List<PCharacter> sortByLevel(List<PCharacter> characters) {
        return characters.stream()
                .sorted(Comparator.comparing(PCharacter::getLevel).reversed())
                .toList();
    }

    public List<PCharacter> sortByClass(List<PCharacter> characters) {
        return characters.stream()
                .sorted(Comparator.comparing(c -> c.getPClass().name()))
                .toList();
    }

    public List<PCharacter> sortBySpecies(List<PCharacter> characters) {
        return characters.stream()
                .sorted(Comparator.comparing(c -> c.getSpecies().name()))
                .toList();
    }

    public List<PCharacter> sortByLastEdited(List<PCharacter> characters) {
        return characters.stream()
                .sorted(Comparator.comparing(c -> c.getAuditInfo().lastEdited()))
                .toList();
    }

    // Lambdas and Predicate
    public List<PCharacter> filterByClass(List<PCharacter> characters, Classes pClass) {
        Predicate<PCharacter> sameClass = c -> c.getPClass() == pClass;

        return characters.stream()
                .filter(sameClass)
                .toList();
    }

    public List<PCharacter> filterBySpecies(List<PCharacter> characters, Species species) {
        Predicate<PCharacter> sameSpecies = c -> c.getSpecies() == species;

        return characters.stream()
                .filter(sameSpecies)
                .toList();
    }

    public List<PCharacter> filterByMinimumLevel(List<PCharacter> characters, int level) {
        Predicate<PCharacter> highEnoughLevel = c -> c.getLevel() >= level;

        return characters.stream()
                .filter(highEnoughLevel)
                .toList();
    }

    // Terminal operations
    public long countCharacters(List<PCharacter> characters) {
        return characters.stream()
                .count();
    }

    public Optional<PCharacter> highestLevelCharacter(List<PCharacter> characters) {
        return characters.stream()
                .max(Comparator.comparing(PCharacter::getLevel));
    }

    public Optional<PCharacter> lowestLevelCharacter(List<PCharacter> characters) {
        return characters.stream()
                .min(Comparator.comparing(PCharacter::getLevel));
    }

    public Optional<PCharacter> findFirstByClass(List<PCharacter> characters, Classes pClass) {
        return characters.stream()
                .filter(c -> c.getPClass() == pClass)
                .findFirst();
    }

    public Optional<PCharacter> findAnyHighLevelCharacter(List<PCharacter> characters) {
        return characters.stream()
                .filter(c -> c.getLevel() >= 10)
                .findAny();
    }

    public boolean allCharactersHaveValidStats(List<PCharacter> characters) {
        return characters.stream()
                .allMatch(this::hasValidStats);
    }

    public boolean anyCharacterIsLevelTenOrHigher(List<PCharacter> characters) {
        return characters.stream()
                .anyMatch(c -> c.getLevel() >= 10);
    }

    public boolean noCharactersHaveInvalidStats(List<PCharacter> characters) {
        return characters.stream()
                .noneMatch(c -> !hasValidStats(c));
    }

    public void printCharacters(List<PCharacter> characters) {
        Consumer<PCharacter> printer = System.out::println;

        characters.stream()
                .sorted(Comparator.comparing(PCharacter::getName))
                .forEach(printer);
    }

    // Intermediate operations: map(), distinct(), limit()
    public List<String> characterNames(List<PCharacter> characters) {
        Function<PCharacter, String> nameFunction = PCharacter::getName;

        return characters.stream()
                .map(nameFunction)
                .collect(Collectors.toList());
    }

    public List<Classes> distinctClasses(List<PCharacter> characters) {
        return characters.stream()
                .map(PCharacter::getPClass)
                .distinct()
                .toList();
    }

    public List<PCharacter> firstThreeCharacters(List<PCharacter> characters) {
        return characters.stream()
                .limit(3)
                .toList();
    }

    // Collectors
    public Map<Classes, List<PCharacter>> groupByClass(List<PCharacter> characters) {
        return characters.stream()
                .collect(Collectors.groupingBy(PCharacter::getPClass));
    }

    public Map<Species, List<PCharacter>> groupBySpecies(List<PCharacter> characters) {
        return characters.stream()
                .collect(Collectors.groupingBy(PCharacter::getSpecies));
    }

    public Map<Boolean, List<PCharacter>> partitionByLevelFiveOrHigher(List<PCharacter> characters) {
        return characters.stream()
                .collect(Collectors.partitioningBy(c -> c.getLevel() >= 5));
    }

    public Map<String, PCharacter> mapNameToCharacter(List<PCharacter> characters) {
        return characters.stream()
                .collect(Collectors.toMap(
                        PCharacter::getName,
                        Function.identity(),
                        (first, duplicate) -> first
                ));
    }

    public Map<String, Integer> mapNameToLevel(List<PCharacter> characters) {
        return characters.stream()
                .collect(Collectors.toMap(
                        PCharacter::getName,
                        PCharacter::getLevel,
                        (first, duplicate) -> first
                ));
    }

    // Supplier example
    public PCharacter createDefaultAnalyticsCharacter() {
        Supplier<PCharacter> defaultCharacter =
                () -> new PCharacter("Analytics Test Character", Classes.Fighter, Species.HUMAN);

        return defaultCharacter.get();
    }

    private boolean hasValidStats(PCharacter character) {
        int[] stats = character.getStats();

        return stats.length == 6 &&
                java.util.Arrays.stream(stats)
                        .allMatch(stat -> stat >= 3 && stat <= 20);
    }
}