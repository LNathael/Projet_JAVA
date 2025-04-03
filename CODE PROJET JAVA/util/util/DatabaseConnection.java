package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        // URL correcte pour SQLite (sans user/password)
        String url = "jdbc:sqlite:/Users/yanis/Documents/GitHub/Projet_JAVA/CODE_PROJET_JAVA/util/javadb.sql";
        
        return DriverManager.getConnection(url);
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Connexion à la base de données réussie !");
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
