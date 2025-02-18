package dao;

import model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantDAO {
    private Connection connection;

    public ParticipantDAO(Connection connection) {
        this.connection = connection;
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
                        rs.getInt("user_id")
                    );
                }
            }
        }
        return null;
    }
}