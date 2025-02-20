package gui.panels;

import dao.ActivityDAO;
import dao.ParticipantDAO;
import model.Activity;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ActivitySearchPanel extends JPanel {
    private Connection connection;

    public ActivitySearchPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Formulaire de recherche
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Nom de l'activité :"));
        JTextField nameField = new JTextField(10);
        formPanel.add(nameField);
        JButton searchButton = new JButton("Rechercher");
        formPanel.add(searchButton);

        // Liste des résultats
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> activityList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(activityList);

        // Action sur le bouton de recherche
        searchButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                ActivityDAO activityDAO = new ActivityDAO(connection);
                List<Activity> activities = activityDAO.searchActivitiesByName(name);
                listModel.clear();
                for (Activity activity : activities) {
                    listModel.addElement(activity.getNom());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la recherche des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton pour s'inscrire à une activité
        JButton registerButton = new JButton("S'inscrire");
        registerButton.addActionListener(e -> {
            int selectedIndex = activityList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedActivity = listModel.getElementAt(selectedIndex);
                try {
                    // TODO: Remplacer par l'ID de l'utilisateur connecté
                    int userId = 1; // Exemple d'ID utilisateur
                    ParticipantDAO participantDAO = new ParticipantDAO(connection);
                    boolean success = participantDAO.registerForActivity(userId, selectedActivity);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Inscription réussie à l'activité : " + selectedActivity);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription à l'activité.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription à l'activité : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une activité.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Bouton pour afficher les activités inscrites
        JButton showActivitiesButton = new JButton("Mes Activités");
        showActivitiesButton.addActionListener(e -> {
            try {
                // TODO: Remplacer par l'ID de l'utilisateur connecté
                int userId = 1; // Exemple d'ID utilisateur
                ParticipantDAO participantDAO = new ParticipantDAO(connection);
                List<Activity> activities = participantDAO.getActivitiesByUserId(userId);
                listModel.clear();
                for (Activity activity : activities) {
                    listModel.addElement(activity.getNom());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(registerButton, BorderLayout.SOUTH);
        add(showActivitiesButton, BorderLayout.EAST);
    }
}