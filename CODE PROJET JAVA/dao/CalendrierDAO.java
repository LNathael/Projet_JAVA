package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.Calendrier;

public class CalendrierDAO {
    private Connection connection;

    // Constructeur pour initialiser la connexion
    public CalendrierDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour récupérer les calendriers par date
    public List<Calendrier> getParDate(LocalDateTime date) throws SQLException {
        String query = "SELECT c.id AS calendrier_id, c.debut, c.fin, c.lieu, c.description AS calendrier_description, " +
                       "a.id AS activity_id, a.nom, a.age_min, a.age_max, a.description AS activity_description " +
                       "FROM calendrier c " +
                       "JOIN activities a ON c.activite_id = a.id " +
                       "WHERE DATE(c.debut) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(date.toLocalDate()));

            try (ResultSet rs = stmt.executeQuery()) {
                List<Calendrier> calendriers = new ArrayList<>();
                while (rs.next()) {
                    // Création de l'objet Activity
                    Activity activity = new Activity(
                        rs.getInt("activity_id"),
                        rs.getString("nom"),
                        rs.getInt("age_min"),
                        rs.getInt("age_max"),
                        rs.getString("activity_description")
                    );

                    // Création de l'objet Calendrier
                    calendriers.add(new Calendrier(
                        rs.getInt("calendrier_id"),
                        activity,
                        rs.getTimestamp("debut").toLocalDateTime(),
                        rs.getTimestamp("fin").toLocalDateTime(),
                        rs.getString("lieu"),
                        rs.getString("calendrier_description") // Ajout de la description du calendrier
                    ));
                }
                return calendriers;
            }
        }
    }
    public List<Calendrier> getDatesByActivityName(String activityName) throws SQLException {
        String query = "SELECT c.id, c.debut, c.fin, c.lieu, c.description " +
                       "FROM calendrier c " +
                       "JOIN activities a ON c.activite_id = a.id " +
                       "WHERE a.nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Calendrier> dates = new ArrayList<>();
                while (rs.next()) {
                    dates.add(new Calendrier(
                        rs.getInt("id"),
                        null, // L'activité peut être null ici
                        rs.getTimestamp("debut").toLocalDateTime(),
                        rs.getTimestamp("fin").toLocalDateTime(),
                        rs.getString("lieu"),
                        rs.getString("description")
                    ));
                }
                return dates;
            }
        }
    }
}