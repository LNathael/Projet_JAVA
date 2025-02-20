package model;

public class Participant {
    private int id;
    private String nom;
    private int age;
    private int userId;
    private String status;

    public Participant(int id, String nom, int age, int userId, String status) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.userId = userId;
        this.status = status;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}