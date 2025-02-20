package model;

public class Participant {
    private int id;
    private String nom;
    private int age;
    private int userId;
    private int idUser;
    private String status;

    public Participant(int id, String nom, int age, int userId, int idUser, String status) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.userId = userId;
        this.idUser = idUser;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}