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
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.add(new JLabel("Nom de l'activité :"));
        JTextField nameField = new JTextField(15);
        formPanel.add(nameField);

        JButton searchButton = new JButton("Rechercher");
        formPanel.add(searchButton);

        // Menu déroulant pour afficher toutes les activités
        JComboBox<String> activityDropdown = new JComboBox<>();
        formPanel.add(new JLabel("Ou sélectionnez une activité :"));
        formPanel.add(activityDropdown);

        // Zone pour afficher les détails de l'activité sélectionnée
        JTextArea activityDetails = new JTextArea(5, 30);
        activityDetails.setEditable(false);
        activityDetails.setLineWrap(true);
        activityDetails.setWrapStyleWord(true);
        JScrollPane detailsScrollPane = new JScrollPane(activityDetails);

        // Bouton pour s'inscrire à une activité
        JButton registerButton = new JButton("S'inscrire");

        // Charger toutes les activités dans le menu déroulant
        loadAllActivities(activityDropdown);

        // Action sur le menu déroulant
        activityDropdown.addActionListener(e -> {
            String selectedActivityName = (String) activityDropdown.getSelectedItem();
            if (selectedActivityName != null) {
                try {
                    ActivityDAO activityDAO = new ActivityDAO(connection);
                    List<Activity> activities = activityDAO.searchActivitiesByName(selectedActivityName);
                    if (!activities.isEmpty()) {
                        Activity activity = activities.get(0); // On suppose que le nom est unique
                        activityDetails.setText(
                            "Nom : " + activity.getNom() + "\n" +
                            "Description : " + activity.getDescription() + "\n" +
                            "Âge minimum : " + activity.getAgeMin() + "\n" +
                            "Âge maximum : " + activity.getAgeMax()
                        );
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors du chargement des détails de l'activité : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action sur le bouton de recherche
        searchButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                ActivityDAO activityDAO = new ActivityDAO(connection);
                List<Activity> activities = activityDAO.searchActivitiesByName(name);
                activityDropdown.removeAllItems();
                for (Activity activity : activities) {
                    activityDropdown.addItem(activity.getNom());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la recherche des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action sur le bouton pour s'inscrire
        registerButton.addActionListener(e -> {
            String selectedActivityName = (String) activityDropdown.getSelectedItem();
            if (selectedActivityName != null) {
                try {
                    // TODO: Remplacer par l'ID de l'utilisateur connecté
                    int userId = 1; // Exemple d'ID utilisateur
                    ParticipantDAO participantDAO = new ParticipantDAO(connection);

                    // Vérifier si l'utilisateur est déjà inscrit
                    if (participantDAO.isUserAlreadyRegistered(userId, selectedActivityName)) {
                        JOptionPane.showMessageDialog(this, "Vous êtes déjà inscrit à l'activité : " + selectedActivityName, "Information", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    boolean success = participantDAO.registerForActivity(userId, selectedActivityName);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Inscription réussie à l'activité : " + selectedActivityName);
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

        // Ajout des composants au panneau
        add(formPanel, BorderLayout.NORTH);
        add(detailsScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Charger toutes les activités dans le menu déroulant
    private void loadAllActivities(JComboBox<String> activityDropdown) {
        try {
            ActivityDAO activityDAO = new ActivityDAO(connection);
            List<Activity> activities = activityDAO.searchActivitiesByName(""); // Charger toutes les activités
            activityDropdown.removeAllItems();
            for (Activity activity : activities) {
                activityDropdown.addItem(activity.getNom());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}