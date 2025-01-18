package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import dao.ActiviteDAO;

public class ActivitesPanel extends JPanel {
    public ActivitesPanel(Connection connection) {
        setLayout(new BorderLayout());

        // Formulaire
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Âge :"));
        JTextField ageField = new JTextField(10);
        formPanel.add(ageField);
        JButton searchButton = new JButton("Rechercher");
        formPanel.add(searchButton);

        // Liste des résultats
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> activityList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(activityList);

        // Action sur le bouton
        searchButton.addActionListener(e -> {
            try {
                int age = Integer.parseInt(ageField.getText());
                ActiviteDAO activiteDAO = new ActiviteDAO(connection);
                listModel.clear();
                activiteDAO.getActivitesParTrancheAge(age).forEach(
                    activite -> listModel.addElement(activite.toString())
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
