package gui.panels;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class UserManagementPanel extends JPanel {
    private Connection connection;

    public UserManagementPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        UserDAO userDAO = new UserDAO(connection);
        try {
            List<User> users = userDAO.getAllUsers();
            String[] columnNames = {"ID", "Nom d'utilisateur", "Rôle"};
            Object[][] data = new Object[users.size()][3];
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                data[i][0] = user.getUserId();
                data[i][1] = user.getUsername();
                data[i][2] = user.getRole();
            }
            JTable table = new JTable(data, columnNames);
            add(new JScrollPane(table), BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des utilisateurs : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}