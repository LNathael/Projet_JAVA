package gui;

import gui.panels.UserManagementPanel;

import javax.swing.*;
import java.sql.Connection;

public class MainAdminFrame extends JFrame {
    public MainAdminFrame(Connection connection) {
        setTitle("Administration - Gestion des utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet de gestion des utilisateurs
        tabbedPane.addTab("Gérer les utilisateurs", new UserManagementPanel(connection));

        // Placeholder pour d'autres outils administratifs
        tabbedPane.addTab("Autres outils d'administration", new JPanel());

        add(tabbedPane);
    }
}
