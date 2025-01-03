public class Participant {
    private int id;
    private String nom;
    private int age;

    public Participant(int id, String nom, int age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public String getNom() { return nom; }
    public int getAge() { return age; }
}
