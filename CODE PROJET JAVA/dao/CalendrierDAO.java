package dao;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Calendrier;

public class CalendrierDAO {
    private Connection connection;

    public CalendrierDAO(Connection connection) {
        this.connection = connection;
    }
    public List<Calendrier> getParDate(LocalDateTime date) throws SQLException {
        String query = """SELECT c.id AS calendrier_id, c.debut, c.fin, c.lieu, 
                   a.id AS activite_id, a.nom, a.age_min, a.age_max, a.description
            FROM calendrier c
            JOIN activites a ON c.activite_id = a.id
            WHERE DATE(c.debut) = ?""";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(date.toLocalDate()));
    
        ResultSet rs = stmt.executeQuery();
        List<Calendrier> calendriers = new ArrayList<>();
        while (rs.next()) {
            Activite activite = new Activite(
                rs.getInt("activite_id"),
                rs.getString("nom"),
                rs.getInt("age_min"),
                rs.getInt("age_max"),
                rs.getString("description")
            );
            calendriers.add(new Calendrier(
                rs.getInt("calendrier_id"),
                activite,
                rs.getTimestamp("debut").toLocalDateTime(),
                rs.getTimestamp("fin").toLocalDateTime(),
                rs.getString("lieu")
            ));
        }
        return calendriers;
    }
    
}
