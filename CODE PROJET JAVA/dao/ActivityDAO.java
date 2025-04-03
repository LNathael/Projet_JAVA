package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.Calendrier;

public class ActivityDAO {
    private Connection connection;

    public ActivityDAO(Connection connection) {
        this.connection = connection;
    }
    public Activity getActivityByName(String activityName) throws SQLException {
        String query = "SELECT id, nom, age_min, age_max, description FROM activities WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Activity(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("age_min"),
                        rs.getInt("age_max"),
                        rs.getString("description")
                    );
                }
            }
        }
        return null;
    }
    public List<Activity> searchActivitiesByName(String name) throws SQLException {
        String query = "SELECT * FROM activities WHERE nom LIKE ?";
        List<Activity> activities = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Activity activity = new Activity(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("age_min"),
                        rs.getInt("age_max"),
                        rs.getString("description")
                    );
                    activities.add(activity);
                }
            }
        }
        return activities;
    }

    public void addActivity(Activity activity) throws SQLException {
        String query = "INSERT INTO activities (nom, age_min, age_max, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, activity.getNom());
            stmt.setInt(2, activity.getAgeMin());
            stmt.setInt(3, activity.getAgeMax());
            stmt.setString(4, activity.getDescription());
            stmt.executeUpdate();
        }
    }

    public Iterable<Calendrier> getActivitesParTrancheAge(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActivitesParTrancheAge'");
    }
}