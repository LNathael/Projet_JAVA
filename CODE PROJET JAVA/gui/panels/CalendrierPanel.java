package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import dao.CalendrierDAO;

public class CalendrierPanel extends JPanel {
    public CalendrierPanel(Connection connection) {
        setLayout(new BorderLayout());

        // Formulaire
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Date (AAAA-MM-JJ) :"));
        JTextField dateField = new JTextField(10);
        formPanel.add(dateField);
        JButton searchButton = new JButton("Rechercher");
        formPanel.add(searchButton);

        // Liste des résultats
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> calendarList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(calendarList);

        // Action sur le bouton
        searchButton.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(dateField.getText());
                CalendrierDAO calendrierDAO = new CalendrierDAO(connection);
                listModel.clear();
                calendrierDAO.getParDate(date.atStartOfDay()).forEach(
                    calendrier -> listModel.addElement(calendrier.toString())
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
