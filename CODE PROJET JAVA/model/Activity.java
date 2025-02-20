package model;

public class Activity {
    private int id;
    private String nom;
    private int ageMin;
    private int ageMax;
    private String description;

    public Activity(int id, String nom, int ageMin, int ageMax, String description) {
        this.id = id;
        this.nom = nom;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.description = description;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getAgeMin() { return ageMin; }
    public void setAgeMin(int ageMin) { this.ageMin = ageMin; }

    public int getAgeMax() { return ageMax; }
    public void setAgeMax(int ageMax) { this.ageMax = ageMax; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}