package model;

import java.time.LocalDateTime;

public class Calendrier {
    private int id;
    private Activite activite;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String lieu;

    // Constructeur
    public Calendrier(int id, Activite activite, LocalDateTime debut, LocalDateTime fin, String lieu) {
        this.id = id;
        this.activite = activite;
        this.debut = debut;
        this.fin = fin;
        this.lieu = lieu;
    }

    public int getId() { return id; }
    public Activite getActivite() { return activite; }
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public String getLieu() { return lieu; }

    @Override
    public String toString() {
        return activite.toString() + " | " + debut + " - " + fin + " | Lieu: " + lieu;
    }
}
