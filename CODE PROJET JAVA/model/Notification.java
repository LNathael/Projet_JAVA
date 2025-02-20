package model;

public class Notification {
    private int id;
    private Participant participant;
    private String message;
    private boolean etatEnvoye;

    public Notification(int id, Participant participant, String message) {
        this.id = id;
        this.participant = participant;
        this.message = message;
        this.etatEnvoye = false;
    }

    public int getId() { return id; }
    public Participant getParticipant() { return participant; }
    public String getMessage() { return message; }
    public boolean isEtatEnvoye() { return etatEnvoye; }

    public void envoyer() {
        System.out.println("Notification envoyée à " + participant.getNom() + ": " + message);
        this.etatEnvoye = true;
    }
}