package gui.panels;

import dao.ParticipantDAO;
import model.Participant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ParticipantManagementPanel extends JPanel {
    private Connection connection;

    public ParticipantManagementPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        ParticipantDAO participantDAO = new ParticipantDAO(connection);
        try {
            List<Participant> participants = participantDAO.getPendingParticipants();
            String[] columnNames = {"ID", "Nom", "Âge", "User ID", "Status"};
            Object[][] data = new Object[participants.size()][5];
            for (int i = 0; i < participants.size(); i++) {
                Participant participant = participants.get(i);
                data[i][0] = participant.getId();
                data[i][1] = participant.getNom();
                data[i][2] = participant.getAge();
                data[i][3] = participant.getUserId();
                data[i][4] = participant.getStatus();
            }
            JTable table = new JTable(new DefaultTableModel(data, columnNames));
            add(new JScrollPane(table), BorderLayout.CENTER);

            JButton acceptButton = new JButton("Accepter la demande");
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int participantId = (int) table.getValueAt(selectedRow, 0);
                        try {
                            boolean success = participantDAO.acceptParticipant(participantId);
                            if (success) {
                                JOptionPane.showMessageDialog(ParticipantManagementPanel.this, "Demande acceptée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                                // Rafraîchir la table
                                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                            } else {
                                JOptionPane.showMessageDialog(ParticipantManagementPanel.this, "Erreur lors de l'acceptation de la demande.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(ParticipantManagementPanel.this, "Erreur lors de l'acceptation de la demande : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ParticipantManagementPanel.this, "Veuillez sélectionner une demande à accepter.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            add(acceptButton, BorderLayout.SOUTH);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des participants : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}