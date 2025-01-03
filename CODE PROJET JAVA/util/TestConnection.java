import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connexion réussie !");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
