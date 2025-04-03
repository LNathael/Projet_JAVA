package model;

import java.time.LocalDateTime;

public class Calendrier {
    private int id;
    private Activity activity;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String lieu;
    private String description; // Nouvelle colonne

    // Constructeur
    public Calendrier(int id, Activity activity, LocalDateTime debut, LocalDateTime fin, String lieu, String description) {
        this.id = id;
        this.activity = activity;
        this.debut = debut;
        this.fin = fin;
        this.lieu = lieu;
        this.description = description;
    }

    public int getId() { return id; }
    public Activity getActivity() { return activity; }
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public String getLieu() { return lieu; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return activity.toString() + " | " + debut + " - " + fin + " | Lieu: " + lieu + " | Description: " + description;
    }
}