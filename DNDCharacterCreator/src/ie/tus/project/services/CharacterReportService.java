package ie.tus.project.services;

import ie.tus.project.Classes;
import ie.tus.project.PCharacter;
import ie.tus.project.Species;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CharacterReportService {

	private final CharacterAnalyticsService analytics = new CharacterAnalyticsService();

	public Path exportAnalyticsReport(List<PCharacter> characters) throws IOException {
		Path reportsFolder = Path.of("reports");
		Files.createDirectories(reportsFolder);

		Path reportFile = reportsFolder.resolve("character-analytics-report.txt");

		String report = buildReport(characters);

		Files.writeString(reportFile, report);

		return reportFile;
	}

	public String readReport(Path reportFile) throws IOException {
		return Files.readString(reportFile);
	}

	private String buildReport(List<PCharacter> characters) {
		StringBuilder sb = new StringBuilder();

		sb.append("D&D Character Analytics Report\n");
		sb.append("==============================\n");
		sb.append("Generated: ").append(LocalDateTime.now()).append("\n\n");

		sb.append("Total characters: ").append(analytics.countCharacters(characters)).append("\n\n");

		sb.append("Character summaries:\n");
		analytics.sortByName(characters).forEach(c -> sb.append("- ").append(summary(c)).append("\n"));

		sb.append("\nGrouped by class:\n");
		Map<Classes, List<PCharacter>> byClass = analytics.groupByClass(characters);
		byClass.forEach((pClass, list) -> sb.append("- ").append(pClass).append(": ").append(list.size())
				.append(" character(s)\n"));

		sb.append("\nGrouped by species:\n");
		Map<Species, List<PCharacter>> bySpecies = analytics.groupBySpecies(characters);
		bySpecies.forEach((species, list) -> sb.append("- ").append(species).append(": ").append(list.size())
				.append(" character(s)\n"));

		sb.append("\nLevel partition:\n");
		analytics.partitionByLevelFiveOrHigher(characters)
				.forEach((levelFiveOrHigher, list) -> sb.append("- ")
						.append(levelFiveOrHigher ? "Level 5 or higher" : "Below level 5").append(": ")
						.append(list.size()).append(" character(s)\n"));

		sb.append("\nValidation:\n");
		sb.append("- All characters have valid stats: ").append(analytics.allCharactersHaveValidStats(characters))
				.append("\n");
		sb.append("- Any character level 10 or higher: ").append(analytics.anyCharacterIsLevelTenOrHigher(characters))
				.append("\n");
		sb.append("- No characters have invalid stats: ").append(analytics.noCharactersHaveInvalidStats(characters))
				.append("\n");

		return sb.toString();
	}

	private String summary(PCharacter c) {
		return c.getName() + " | Class: " + c.getPClass() + " | Species: " + c.getSpecies() + " | Level: "
				+ c.getLevel() + " | HP: " + c.getHp();
	}
}