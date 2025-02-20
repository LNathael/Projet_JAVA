package dao;

import model.Participant;
import model.Activity;
import model.Registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    private Connection connection;

    public ParticipantDAO(Connection connection) {
        this.connection = connection;
    }

    public Participant getParticipantByName(String name) throws SQLException {
        String query = "SELECT * FROM participants WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("age"),
                        rs.getInt("user_id"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public Participant getParticipantByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM participants WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("age"),
                        rs.getInt("user_id"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public List<Participant> getPendingParticipants() throws SQLException {
        String query = "SELECT * FROM participants WHERE status = 'PENDING'";
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Participant participant = new Participant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("age"),
                    rs.getInt("user_id"),
                    rs.getString("status")
                );
                participants.add(participant);
            }
        }
        return participants;
    }

    public boolean acceptParticipant(int participantId) throws SQLException {
        String query = "UPDATE participants SET status = 'ACCEPTED' WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, participantId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean registerForActivity(int userId, String activityName) throws SQLException {
        // Vérifier si le user_id existe dans la table participants
        Participant participant = getParticipantByUserId(userId);
        if (participant == null) {
            throw new SQLException("User ID " + userId + " n'existe pas dans la table participants.");
        }

        // Vérifier si l'activité existe dans la table activities
        String activityQuery = "SELECT * FROM activities WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(activityQuery)) {
            stmt.setString(1, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Activity " + activityName + " n'existe pas dans la table activities.");
                }
            }
        }

        // Vérifier si l'utilisateur est déjà inscrit à l'activité
        String registrationQuery = "SELECT * FROM registrations WHERE user_id = ? AND activity_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(registrationQuery)) {
            stmt.setInt(1, userId);
            stmt.setString(2, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    throw new SQLException("User ID " + userId + " est déjà inscrit à l'activité " + activityName + ".");
                }
            }
        }

        String query = "INSERT INTO registrations (user_id, activity_name, status) VALUES (?, ?, 'PENDING')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, activityName);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
    }

    public List<Registration> getPendingRegistrations() throws SQLException {
        String query = "SELECT r.id, r.user_id, r.activity_name, r.status, p.nom AS participant_name " +
                       "FROM registrations r " +
                       "JOIN participants p ON r.user_id = p.user_id " +
                       "WHERE r.status = 'PENDING'";
        List<Registration> registrations = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Registration registration = new Registration(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("activity_name"),
                    rs.getString("status"),
                    rs.getString("participant_name")
                );
                registrations.add(registration);
            }
        }
        return registrations;
    }

    public boolean updateRegistrationStatus(int registrationId, String status) throws SQLException {
        String query = "UPDATE registrations SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, registrationId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public List<Activity> getActivitiesByUserId(int userId) throws SQLException {
        String query = "SELECT a.* FROM activities a JOIN registrations r ON a.nom = r.activity_name WHERE r.user_id = ?";
        List<Activity> activities = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
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
}