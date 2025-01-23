package gui;

import javax.swing.*;

import gui.panels.ActivitesPanel;
import gui.panels.CalendrierPanel;
import gui.panels.NotificationsPanel;

import java.awt.*;

import java.sql.Connection;

public class MainFrame extends JFrame {
    public MainFrame(Connection connection) {
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
        add(tabbedPane);
    }

    // Méthode principale pour tester directement MainFrame
    public static void main(String[] args) {
        try {
            // Établir la connexion à la base de données
            Connection connection = util.DatabaseConnection.getConnection();

            // Exécuter l'interface graphique sur le thread dédié
            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(connection);
                frame.setVisible(true); // Afficher la fenêtre
            });
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
