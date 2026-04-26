package ie.tus.project.services;

import ie.tus.project.PCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentCharacterService {

    public List<String> calculateStatSummaries(List<PCharacter> characters) throws Exception {
        List<Callable<String>> tasks = new ArrayList<>();

        for (PCharacter character : characters) {
            Callable<String> task = () -> buildStatSummary(character);
            tasks.add(task);
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            return executor.invokeAll(tasks)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            return "Error calculating character summary: " + e.getMessage();
                        }
                    })
                    .toList();
        }
    }

    private String buildStatSummary(PCharacter character) {
        int[] stats = character.getStats();

        int totalStats = Arrays.stream(stats).sum();

        int totalModifiers = Arrays.stream(stats)
                .map(PCharacter::abilityModifier)
                .sum();

        return character.getName()
                + " | Class: " + character.getPClass()
                + " | Level: " + character.getLevel()
                + " | Total Stats: " + totalStats
                + " | Total Modifiers: " + totalModifiers;
    }
}