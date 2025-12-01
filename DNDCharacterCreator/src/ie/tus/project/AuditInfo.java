package ie.tus.project;
import java.time.LocalDateTime;
public record AuditInfo(LocalDateTime created, LocalDateTime lastEdited) {

    // Factory method to create a new AuditInfo for a new character
    public static AuditInfo createNew() {
        LocalDateTime now = LocalDateTime.now();
        return new AuditInfo(now, now);
    }

    // Method to update lastEdited timestamp when character is edited
    public AuditInfo update() {
        return new AuditInfo(this.created, LocalDateTime.now());
    }
}