package gui;

import javax.swing.*;
import java.sql.Connection;

public class MainFrame extends JFrame {
    public MainFrame(Connection connection) {
        setTitle("Gestion des Activités et Notifications");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Onglets principaux
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Activités", new ActivitesPanel(connection));
        tabbedPane.addTab("Calendrier", new CalendrierPanel(connection));
        tabbedPane.addTab("Notifications", new NotificationsPanel());

        add(tabbedPane);
    }
}
