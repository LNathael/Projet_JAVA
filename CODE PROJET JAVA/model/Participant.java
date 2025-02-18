package model;

public class Participant {
    private int id;
    private String nom;
    private int age;
    private int userId;

    public Participant(int id, String nom, int age, int userId) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.userId = userId;
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
}