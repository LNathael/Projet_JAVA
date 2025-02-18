package gui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private Connection connection;

    public RegisterFrame(Connection connection) {
        this.connection = connection;

        setTitle("Inscription");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordField = new JPasswordField();
        registerButton = new JButton("S'inscrire");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel());
        mainPanel.add(registerButton);

        add(mainPanel);

        registerButton.addActionListener(new RegisterActionListener());
    }

    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    RegisterFrame.this,
                    "Veuillez remplir tous les champs.",
                    "Erreur d'inscription",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                UserDAO userDAO = new UserDAO(connection);
                if (userDAO.isUsernameTaken(username)) {
                    JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Nom d'utilisateur déjà pris.",
                        "Erreur d'inscription",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean success = userDAO.registerUser(username, password, "USER");

                if (success) {
                    JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Inscription réussie !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    new LoginFrame(connection).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                        RegisterFrame.this,
                        "Une erreur s'est produite lors de l'inscription.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    RegisterFrame.this,
                    "Une erreur s'est produite lors de l'inscription.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}