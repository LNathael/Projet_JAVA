import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/camp_activites"; // Port 3306 par défaut pour Laragon
        String user = "root"; // Par défaut dans Laragon
        String password = " "; // Mot de passe vide par défaut dans Laragon
        return DriverManager.getConnection(url, user, password);
    }
}
