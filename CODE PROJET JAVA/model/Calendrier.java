package model;

import java.time.LocalDateTime;

public class Calendrier {
    private int id;
    private Activity activity;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String lieu;

    // Constructeur
    public Calendrier(int id, Activity activity, LocalDateTime debut, LocalDateTime fin, String lieu) {
        this.id = id;
        this.activity = activity;
        this.debut = debut;
        this.fin = fin;
        this.lieu = lieu;
    }

    public int getId() { return id; }
    public Activity getActivity() { return activity; }
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public String getLieu() { return lieu; }

    @Override
    public String toString() {
        return activity.toString() + " | " + debut + " - " + fin + " | Lieu: " + lieu;
    }
}