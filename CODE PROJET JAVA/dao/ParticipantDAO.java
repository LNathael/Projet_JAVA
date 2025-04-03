package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.Notification;
import model.Participant;
import model.Registration;
import service.NotificationService;

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
                        rs.getInt("id_user"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public List<Activity> getActivitiesByUserId(int userId) throws SQLException {
        String query = "SELECT a.id, a.nom, a.age_min, a.age_max, a.description, r.status " +
                       "FROM activities a " +
                       "JOIN registrations r ON a.nom = r.activity_name " +
                       "WHERE r.user_id = ?";
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
                    activity.setStatus(rs.getString("status")); // Ajout du statut
                    activities.add(activity);
                }
            }
        }
        return activities;
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
                    rs.getInt("id_user"),
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

    public List<Registration> getRegistrationsByUserId(int userId) throws SQLException {
        String query = "SELECT r.id, r.user_id, r.activity_name, r.status, p.nom AS participant_name " +
                       "FROM registrations r " +
                       "JOIN participants p ON r.user_id = p.user_id " +
                       "WHERE r.user_id = ?";
        List<Registration> registrations = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration(
                        rs.getInt("id"),                  // ID de l'inscription
                        rs.getInt("user_id"),             // ID de l'utilisateur
                        rs.getString("activity_name"),    // Nom de l'activité
                        rs.getString("status"),           // Statut de l'inscription
                        rs.getString("participant_name")  // Nom du participant
                    );
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des inscriptions pour l'utilisateur " + userId + " : " + e.getMessage());
            throw e;
        }
        return registrations;
    }

    public boolean registerForActivity(int userId, String activityName) throws SQLException {
        // Vérifier si l'utilisateur existe dans la table `user`
        String userQuery = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(userQuery)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Erreur : L'utilisateur avec l'ID " + userId + " n'existe pas.");
                }
            }
        }
    
        // Vérifier si l'activité existe dans la table `activities`
        String activityQuery = "SELECT * FROM activities WHERE nom = ?";
        try (PreparedStatement stmt = connection.prepareStatement(activityQuery)) {
            stmt.setString(1, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Erreur : L'activité " + activityName + " n'existe pas.");
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
                    throw new SQLException("Erreur : L'utilisateur est déjà inscrit à l'activité " + activityName + ".");
                }
            }
        }
    
        // Inscrire l'utilisateur à l'activité
        String insertQuery = "INSERT INTO registrations (user_id, activity_name, status) VALUES (?, ?, 'PENDING')";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
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
                registrations.add(new Registration(
                    rs.getInt("id"),                  // ID de l'inscription
                    rs.getInt("user_id"),             // ID de l'utilisateur
                    rs.getString("activity_name"),    // Nom de l'activité
                    rs.getString("status"),           // Statut de l'inscription
                    rs.getString("participant_name")  // Nom du participant
                ));
            }
        }
        return registrations;
    }
    public boolean updateRegistrationStatus(int userId, String activityName, String newStatus) throws SQLException {
        String updateQuery = "UPDATE registrations SET status = ? WHERE user_id = ? AND activity_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);
            stmt.setString(3, activityName);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }


    public void ajouterParticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO participants (nom, age, user_id, id_user, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, participant.getNom());
            stmt.setInt(2, participant.getAge());
            stmt.setInt(3, participant.getUserId());
            stmt.setInt(4, participant.getIdUser());
            stmt.setString(5, participant.getStatus());
            stmt.executeUpdate();

            Notification notification = new Notification(1, participant, "Un nouvel enfant a été ajouté.");
            NotificationService notificationService = new NotificationService();
            notificationService.notifierResponsable(notification, "Un nouvel enfant a été ajouté.");
        }
    }

    public void notifierActiviteComplete(int activiteId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM registrations WHERE activity_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, activiteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") >= 10) { // Supposons que 10 est le nombre maximum de participants
                    Notification notification = new Notification(1, null, "L'activité est complète.");
                    NotificationService notificationService = new NotificationService();
                    notificationService.notifierResponsable(notification, "L'activité est complète.");
                }
            }
        }
    }
    public boolean isUserAlreadyRegistered(int userId, String activityName) throws SQLException {
        String query = "SELECT COUNT(*) FROM registrations WHERE user_id = ? AND activity_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, activityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}