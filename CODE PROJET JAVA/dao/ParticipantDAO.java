package dao;

import model.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    private Connection connection;

    public ParticipantDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addParticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO participants (nom, age, user_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, participant.getNom());
        stmt.setInt(2, participant.getAge());
        stmt.setInt(3, participant.getUserId());
        return stmt.executeUpdate() > 0;
    }

    public List<Participant> getAllParticipants() throws SQLException {
        String query = "SELECT * FROM participants";
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        List<Participant> participants = new ArrayList<>();
        while (rs.next()) {
            participants.add(new Participant(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("age"),
                rs.getInt("user_id")
            ));
        }
        return participants;
    }

    public Participant getParticipantByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM participants WHERE user_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Participant(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("age"),
                rs.getInt("user_id")
            );
        }
        return null;
    }
}
