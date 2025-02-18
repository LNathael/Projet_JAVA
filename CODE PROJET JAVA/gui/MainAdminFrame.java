package gui;

import gui.panels.UserManagementPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class MainAdminFrame extends JFrame {
    private String username;
    private Connection connection;

    public MainAdminFrame(Connection connection, String username) {
        this.connection = connection;
        this.username = username;

        setTitle("Administration - Gestion des utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet de gestion des utilisateurs
        tabbedPane.addTab("Gérer les utilisateurs", new UserManagementPanel(connection));

        // Placeholder pour d'autres outils administratifs
        tabbedPane.addTab("Autres outils d'administration", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Barre d'outils avec le nom de l'utilisateur et le bouton de déconnexion
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Connecté en tant que : " + username);
        JButton logoutButton = new JButton("Déconnexion");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(connection).setVisible(true);
                dispose();
            }
        });
        topPanel.add(userLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
    }
}