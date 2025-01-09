package model;

public class Participant {
    private int id;
    private String nom;
    private int age;
    private int userId; // Ajout de l'identifiant utilisateur

    public Participant(int id, String nom, int age, int userId) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.userId = userId;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getNom() { return nom; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", age=" + age +
                ", userId=" + userId +
                '}';
    }
}
