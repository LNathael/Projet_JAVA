package gui.panels;

import dao.ParticipantDAO;
import model.Registration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RegistrationManagementPanel extends JPanel {
    private Connection connection;

    public RegistrationManagementPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Tableau des inscriptions en attente
        String[] columnNames = {"ID", "Nom Participant", "Activité", "Statut"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Boutons pour accepter ou refuser une inscription
        JButton acceptButton = new JButton("Accepter");
        JButton rejectButton = new JButton("Refuser");

        // Charger les inscriptions en attente
        loadPendingRegistrations(tableModel);

        // Action sur le bouton Accepter
        acceptButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int registrationId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    ParticipantDAO participantDAO = new ParticipantDAO(connection);
                    boolean success = participantDAO.updateRegistrationStatus(registrationId, "ACCEPTED", "SomeAdditionalInfo");
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Inscription acceptée !");
                        loadPendingRegistrations(tableModel);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de l'acceptation de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'acceptation de l'inscription : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une inscription.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action sur le bouton Refuser
        rejectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int registrationId = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    ParticipantDAO participantDAO = new ParticipantDAO(connection);
                    boolean success = participantDAO.updateRegistrationStatus(registrationId, "REJECTED", "SomeAdditionalInfo");
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Inscription refusée !");
                        loadPendingRegistrations(tableModel);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors du refus de l'inscription.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors du refus de l'inscription : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une inscription.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadPendingRegistrations(DefaultTableModel tableModel) {
        try {
            ParticipantDAO participantDAO = new ParticipantDAO(connection);
            List<Registration> registrations = participantDAO.getPendingRegistrations();
            for (Registration registration : registrations) {
                tableModel.addRow(new Object[]{
                    registration.getId(),
                    registration.getParticipantName(), // Nom du participant
                    registration.getActivityName(),    // Nom de l'activité
                    registration.getStatus()           // Statut de l'inscription
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des inscriptions : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}