package dao;

import model.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
    private Connection connection;

    public ActivityDAO(Connection connection) {
        this.connection = connection;
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
}