package model;

public class Registration {
    private int id;
    private int userId;
    private String activityName;
    private String status;
    private String participantName; // Nouveau champ pour le nom du participant

    public Registration(int id, int userId, String activityName, String status, String participantName) {
        this.id = id;
        this.userId = userId;
        this.activityName = activityName;
        this.status = status;
        this.participantName = participantName; // Initialisation du nouveau champ
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipantName() { // Getter pour le nom du participant
        return participantName;
    }

    public void setParticipantName(String participantName) { // Setter pour le nom du participant
        this.participantName = participantName;
    }
}