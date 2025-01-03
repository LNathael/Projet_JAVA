package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Activite;

public class ActiviteDAO {
    private Connection connection;

    public ActiviteDAO(Connection connection) {
        this.connection = connection;
    }

    // Récupérer les activités adaptées à un âge donné
    public List<Activite> getActivitesParTrancheAge(int age) throws SQLException {
        String query = "SELECT * FROM activites WHERE age_min <= ? AND age_max >= ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, age);
        stmt.setInt(2, age);

        ResultSet rs = stmt.executeQuery();
        List<Activite> activites = new ArrayList<>();
        while (rs.next()) {
            activites.add(new Activite(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getInt("age_min"),
                rs.getInt("age_max"),
                rs.getString("description")
            ));
        }
        return activites;
    }
}
