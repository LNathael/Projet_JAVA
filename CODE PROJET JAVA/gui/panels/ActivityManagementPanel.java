package gui.panels;

import dao.ActivityDAO;
import model.Activity;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ActivityManagementPanel extends JPanel {
    private Connection connection;

    public ActivityManagementPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Formulaire pour ajouter une nouvelle activité
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("Nom de l'activité :"));
        JTextField nameField = new JTextField(10);
        formPanel.add(nameField);
        formPanel.add(new JLabel("Âge minimum :"));
        JTextField ageMinField = new JTextField(10);
        formPanel.add(ageMinField);
        formPanel.add(new JLabel("Âge maximum :"));
        JTextField ageMaxField = new JTextField(10);
        formPanel.add(ageMaxField);
        formPanel.add(new JLabel("Description :"));
        JTextField descriptionField = new JTextField(10);
        formPanel.add(descriptionField);
        JButton addButton = new JButton("Ajouter");
        formPanel.add(addButton);

        // Action sur le bouton Ajouter
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            int ageMin = Integer.parseInt(ageMinField.getText());
            int ageMax = Integer.parseInt(ageMaxField.getText());
            String description = descriptionField.getText();

            Activity activity = new Activity(0, name, ageMin, ageMax, description);
            try {
                ActivityDAO activityDAO = new ActivityDAO(connection);
                activityDAO.addActivity(activity);
                JOptionPane.showMessageDialog(this, "Activité ajoutée avec succès !");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'activité : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formPanel, BorderLayout.NORTH);
    }
}