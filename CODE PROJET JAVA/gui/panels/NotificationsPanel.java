package gui.panels;

import javax.swing.*;
import java.awt.BorderLayout;
import java.sql.Connection;
import dao.ParticipantDAO;
import model.Notification;
import model.Participant;
import service.NotificationService;

public class NotificationsPanel extends JPanel {
    private Connection connection;

    public NotificationsPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Formulaire
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Nom Participant :"));
        JTextField nameField = new JTextField(10);
        formPanel.add(nameField);

        formPanel.add(new JLabel("Message :"));
        JTextField messageField = new JTextField(20);
        formPanel.add(messageField);

        JButton sendButton = new JButton("Envoyer");
        formPanel.add(sendButton);

        // Action sur le bouton
        sendButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String message = messageField.getText();

                if (name.isEmpty() || message.isEmpty()) {
                    throw new IllegalArgumentException("Nom et message requis");
                }

                ParticipantDAO participantDAO = new ParticipantDAO(connection);
                Participant participant = participantDAO.getParticipantByName(name);

                if (participant == null) {
                    throw new IllegalArgumentException("Participant non trouvé");
                }

                Notification notification = new Notification(1, participant, message);

                NotificationService notificationService = new NotificationService();
                notificationService.planifierNotification(notification, 5000); // 5 secondes
                JOptionPane.showMessageDialog(this, "Notification planifiée !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        add(formPanel, BorderLayout.NORTH);
    }
}