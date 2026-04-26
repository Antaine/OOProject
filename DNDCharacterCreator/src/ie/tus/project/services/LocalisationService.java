package ie.tus.project.services;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalisationService {

    private Locale currentLocale = Locale.ENGLISH;
    private ResourceBundle messages = ResourceBundle.getBundle("resources.messages", currentLocale);

    public void setEnglish() {
        currentLocale = Locale.ENGLISH;
        messages = ResourceBundle.getBundle("resources.messages", currentLocale);
    }

    public void setIrish() {
        currentLocale = Locale.forLanguageTag("ga");
        messages = ResourceBundle.getBundle("resources.messages", currentLocale);
    }

    public void setItalian() {
        currentLocale = Locale.ITALIAN;
        messages = ResourceBundle.getBundle("resources.messages", currentLocale);
    }
    
    public void setSpanish() {
        currentLocale = Locale.forLanguageTag("es");
        messages = ResourceBundle.getBundle("resources.messages", currentLocale);
    }

    public String text(String key) {
        return messages.getString(key);
    }

    public Locale currentLocale() {
        return currentLocale;
    }

    public String formatNumber(Number number) {
        return NumberFormat.getNumberInstance(currentLocale).format(number);
    }

    public String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd MMMM yyyy HH:mm", currentLocale);

        return dateTime.format(formatter);
    }
}