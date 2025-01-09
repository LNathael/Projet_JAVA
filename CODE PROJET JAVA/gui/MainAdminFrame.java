package gui;

import javax.swing.*;
import java.sql.Connection;

public class MainAdminFrame extends JFrame {
    public MainAdminFrame(Connection connection) {
        setTitle("Administration - Gestion des utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Gérer les utilisateurs", new UserManagementPanel(connection));
        tabbedPane.addTab("Autres outils d'administration", new JPanel()); // Placeholders

        add(tabbedPane);
    }
}
