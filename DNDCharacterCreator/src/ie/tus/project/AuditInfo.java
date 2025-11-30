package ie.tus.project;

import java.time.LocalDateTime;

public class AuditInfo {
    private final LocalDateTime created;
    private final LocalDateTime lastEdited;

    // Constructor
    public AuditInfo(LocalDateTime created, LocalDateTime lastEdited) {
        this.created = created;
        this.lastEdited = lastEdited;
    }

    // Factory method to create new AuditInfo for a newly created character
    public static AuditInfo createNew() {
        LocalDateTime now = LocalDateTime.now();
        return new AuditInfo(now, now);
    }

    // Factory method to update lastEdited
    public AuditInfo update() {
        return new AuditInfo(this.created, LocalDateTime.now());
    }

    // Getters
    public LocalDateTime getCreated() { return created; }
    public LocalDateTime getLastEdited() { return lastEdited; }

    @Override
    public String toString() {
        return "Created: " + created + ", Last Edited: " + lastEdited;
    }
}
