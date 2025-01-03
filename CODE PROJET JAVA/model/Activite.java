package model;
public class Activite {
    private int id;
    private String nom;
    private int ageMin;
    private int ageMax;
    private String description;

    // Constructeur
    public Activite(int id, String nom, int ageMin, int ageMax, String description) {
        this.id = id;
        this.nom = nom;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.description = description;
    }

    // Getters et setters
    public int getAgeMin() { return ageMin; }
    public int getAgeMax() { return ageMax; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }

    public int getId() { return id; }

    // Vérifie si une activité est adaptée à un âge donné
    public boolean estAgeAdapte(int age) {
        return age >= ageMin && age <= ageMax;
    }

    @Override
    public String toString() {
        return nom + " (Âge: " + ageMin + "-" + ageMax + ")";
    }
}
