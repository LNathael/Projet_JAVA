package gui;

import javax.swing.*;

import gui.panels.ActivitesPanel;
import gui.panels.CalendrierPanel;
import gui.panels.NotificationsPanel;
import util.DatabaseConnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class MainFrame extends JFrame {
    private String username;
    private Connection connection;

    public MainFrame(Connection connection, String username) {
        this.connection = connection;
        this.username = username;

        // Titre de la fenêtre
        setTitle("Gestion des Activités et Notifications");

        // Taille de la fenêtre
        setSize(800, 600);

        // Action à réaliser lors de la fermeture de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création des onglets principaux
        JTabbedPane tabbedPane = new JTabbedPane();

        // Ajouter les onglets à la fenêtre
        tabbedPane.addTab("Activités", new ActivitesPanel(connection));
        tabbedPane.addTab("Calendrier", new CalendrierPanel(connection));
        tabbedPane.addTab("Notifications", new NotificationsPanel());

        // Ajout des onglets au contenu principal de la fenêtre
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

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        try {
            // Établir la connexion à la base de données
            Connection connection = util.DatabaseConnection.getConnection();

            // Vérifier si l'utilisateur est connecté
            if (!isUserLoggedIn()) {
                // Afficher l'écran de connexion
                SwingUtilities.invokeLater(() -> {
                    new LoginFrame(connection).setVisible(true);
                });
            } else {
                // Exécuter l'interface graphique sur le thread dédié
                SwingUtilities.invokeLater(() -> {
                    MainFrame frame = new MainFrame(connection, "TestUser");
                    frame.setVisible(true); // Afficher la fenêtre
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour vérifier si l'utilisateur est connecté
    private static boolean isUserLoggedIn() {
        // Implémentez votre logique pour vérifier si l'utilisateur est connecté
        // Par exemple, vérifier un token de session ou une variable de session
        return false; // Retourne false pour l'exemple
    }
}