import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendrierDAO {
    private Connection connection;

    public CalendrierDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Calendrier> getParDate(LocalDateTime date) throws SQLException {
        String query = "SELECT * FROM calendrier WHERE DATE(debut) = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDate(1, Date.valueOf(date.toLocalDate()));

        ResultSet rs = stmt.executeQuery();
        List<Calendrier> calendriers = new ArrayList<>();
        while (rs.next()) {
            calendriers.add(new Calendrier(
                rs.getInt("id"),
                null, // Remplir l'objet Activite à partir d'une jointure si nécessaire
                rs.getTimestamp("debut").toLocalDateTime(),
                rs.getTimestamp("fin").toLocalDateTime(),
                rs.getString("lieu")
            ));
        }
        return calendriers;
    }
}
