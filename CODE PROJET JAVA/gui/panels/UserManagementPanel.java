package gui.panels;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.sql.Connection;

public class UserManagementPanel extends JPanel {
    public UserManagementPanel(Connection connection) {
        setLayout(new BorderLayout());

        UserDAO userDAO = new UserDAO(connection);

        try {
            List<String> users = userDAO.getAllUsers();
            JList<String> userList = new JList<>(users.toArray(new String[0]));

            add(new JScrollPane(userList), BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
