package gui.panels;

import dao.ParticipantDAO;
import dao.UserDAO;
import gui.MainAdminFrame;
import gui.MainFrame;
import gui.RegisterFrame;
import model.Participant;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginPanel extends JPanel {
    public LoginPanel(Connection connection) {
        setLayout(new BorderLayout());

        // Formulaire de connexion
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Se connecter");
        JButton registerButton = new JButton("S'inscrire");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(loginButton);
        formPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);

        // Action du bouton Connexion
        loginButton.addActionListener(e -> {
    try {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        UserDAO userDAO = new UserDAO(connection);
        String role = userDAO.authenticateUser(username, password);

        if (role != null) {
            if (role.equals("ADMIN")) {
                new MainAdminFrame(connection).setVisible(true);
            } else {
                // Charger le participant correspondant
                ParticipantDAO participantDAO = new ParticipantDAO(connection);
                Participant participant = participantDAO.getParticipantByUserId(getUserIdByUsername(username));
                                System.out.println("Participant chargé : " + participant);
                
                                new MainFrame(connection).setVisible(true);
                            }
                            SwingUtilities.getWindowAncestor(this).dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erreur lors de la connexion.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
                
                
                        // Action du bouton Inscription
                        registerButton.addActionListener(e -> {
                            SwingUtilities.getWindowAncestor(this).dispose();
                            new RegisterFrame(connection).setVisible(true);
                        });
                    }
                
                    private int getUserIdByUsername(String username) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'getUserIdByUsername'");
                    }
}
