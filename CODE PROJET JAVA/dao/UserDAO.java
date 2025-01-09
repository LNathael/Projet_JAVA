package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public String authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("role");
        }
        return null;
    }

    public boolean registerUser(String username, String password, String role) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, role);

        try {
            statement.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // Username already exists
        }
    }
    // Récupérer l'ID d'un utilisateur par son nom d'utilisateur
    public int getUserIdByUsername(String username) throws SQLException {
    String query = "SELECT id FROM users WHERE username = ?";
    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, username); // Injection du nom d'utilisateur dans la requête
    ResultSet rs = stmt.executeQuery();

    if (rs.next()) {
        return rs.getInt("id"); // Retourne l'ID si l'utilisateur est trouvé
    }
    throw new SQLException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
}

    public List<String> getAllUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT username FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            users.add(resultSet.getString("username"));
        }
        return users;
    }
}
