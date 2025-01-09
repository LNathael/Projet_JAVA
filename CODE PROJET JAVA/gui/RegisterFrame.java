package gui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

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

            try {
                UserDAO userDAO = new UserDAO(connection);
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
                        "Le nom d'utilisateur existe déjà.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception ex) {
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
