package model;

public class Registration {
    private int id;
    private int userId;
    private String activityName;
    private String status;
    private String participantName;

    public Registration(int id, int userId, String activityName, String status, String participantName) {
        this.id = id;
        this.userId = userId;
        this.activityName = activityName;
        this.status = status;
        this.participantName = participantName;
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

    public String getParticipantName() {
        return participantName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}