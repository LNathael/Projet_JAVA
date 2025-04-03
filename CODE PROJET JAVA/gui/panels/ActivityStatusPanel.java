package gui.panels;

import dao.ActivityDAO;
import dao.CalendrierDAO;
import dao.ParticipantDAO;
import model.Activity;
import model.Calendrier;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ActivityStatusPanel extends JPanel {
    private Connection connection;

    public ActivityStatusPanel(Connection connection, int userId) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Liste des activités
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> activityList = new JList<>(listModel);
        activityList.setCellRenderer(new ActivityListRenderer()); // Code couleur
        JScrollPane scrollPane = new JScrollPane(activityList);

        // Bouton pour afficher les informations de l'activité
        JButton infoButton = new JButton("Voir les informations");

        // Charger les activités auxquelles l'utilisateur est inscrit
        loadUserActivities(listModel, userId);

        // Action sur le bouton "Voir les informations"
        infoButton.addActionListener(e -> {
            int selectedIndex = activityList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedActivityName = listModel.getElementAt(selectedIndex).replace("[VERT] ", "").replace("[ORANGE] ", "");
                try {
                    // Récupérer les informations détaillées de l'activité
                    ActivityDAO activityDAO = new ActivityDAO(connection);
                    Activity activity = activityDAO.getActivityByName(selectedActivityName);

                    if (activity != null) {
                        // Afficher les informations de l'activité
                        JOptionPane.showMessageDialog(this,
                                "Nom : " + activity.getNom() + "\n" +
                                "Description : " + activity.getDescription() + "\n" +
                                "Âge minimum : " + activity.getAgeMin() + "\n" +
                                "Âge maximum : " + activity.getAgeMax(),
                                "Informations de l'activité",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucune information trouvée pour cette activité.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des informations : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une activité.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ajout des composants au panneau
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(infoButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Charger les activités auxquelles l'utilisateur est inscrit
    private void loadUserActivities(DefaultListModel<String> listModel, int userId) {
        try {
            ParticipantDAO participantDAO = new ParticipantDAO(connection);
            List<Activity> activities = participantDAO.getActivitiesByUserId(userId); // Charger les activités de l'utilisateur
            listModel.clear();
            for (Activity activity : activities) {
                String status = activity.getStatus();
                if ("VALIDÉ".equalsIgnoreCase(status)) {
                    listModel.addElement("[VERT] " + activity.getNom());
                } else if ("EN ATTENTE".equalsIgnoreCase(status)) {
                    listModel.addElement("[ORANGE] " + activity.getNom());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Renderer pour appliquer le code couleur aux activités
    private static class ActivityListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String text = value.toString();
            if (text.startsWith("[VERT]")) {
                label.setForeground(Color.GREEN);
            } else if (text.startsWith("[ORANGE]")) {
                label.setForeground(Color.ORANGE);
            }
            label.setText(text.replace("[VERT] ", "").replace("[ORANGE] ", ""));
            return label;
        }
    }
}