package gui;

import dao.UserDAO;
import dao.ParticipantDAO;
import model.Participant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, forgotPasswordButton;
    private JCheckBox showPasswordCheckBox;
    private Connection connection;

    public LoginFrame(Connection connection) {
        this.connection = connection;

        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordField = new JPasswordField();
        showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        loginButton = new JButton("Se connecter");
        registerButton = new JButton("S'inscrire");
        forgotPasswordButton = new JButton("Mot de passe oublié");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(showPasswordCheckBox);
        mainPanel.add(new JLabel()); // Espace vide
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);
        mainPanel.add(forgotPasswordButton);

        add(mainPanel);

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        loginButton.addActionListener(new LoginActionListener());
        registerButton.addActionListener(e -> {
            new RegisterFrame(connection).setVisible(true);
            dispose();
        });
        forgotPasswordButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Entrez votre nom d'utilisateur :", "Mot de passe oublié", JOptionPane.QUESTION_MESSAGE);
            if (username != null && !username.isEmpty()) {
                try {
                    UserDAO userDAO = new UserDAO(connection);
                    String newPassword = userDAO.resetPassword(username);
                    if (newPassword != null) {
                        JOptionPane.showMessageDialog(this, "Votre nouveau mot de passe est : " + newPassword, "Mot de passe réinitialisé", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Nom d'utilisateur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de la réinitialisation du mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    LoginFrame.this,
                    "Veuillez remplir tous les champs.",
                    "Erreur de connexion",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                UserDAO userDAO = new UserDAO(connection);
                String role = userDAO.authenticateUser(username, password);

                if (role != null) {
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        new MainAdminFrame(connection, username).setVisible(true);
                    } else {
                        int userId = userDAO.getUserIdByUsername(username);
                        ParticipantDAO participantDAO = new ParticipantDAO(connection);
                        Participant participant = participantDAO.getParticipantByUserId(userId);

                        if (participant != null) {
                            System.out.println("Utilisateur connecté : " + participant.getNom());
                        }
                        new MainFrame(connection, username, role).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Nom d'utilisateur ou mot de passe incorrect.",
                        "Erreur de connexion",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    LoginFrame.this,
                    "Une erreur s'est produite lors de la connexion.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}