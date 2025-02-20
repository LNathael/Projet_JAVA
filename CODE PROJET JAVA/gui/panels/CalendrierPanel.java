package gui.panels;

import com.toedter.calendar.JCalendar;
import dao.CalendrierDAO;
import model.Calendrier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CalendrierPanel extends JPanel {
    private Connection connection;
    private JCalendar calendar;
    private DefaultListModel<String> listModel;
    private JList<String> calendarList;

    public CalendrierPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        // Composant de calendrier visuel
        calendar = new JCalendar();
        calendar.setTodayButtonVisible(true);
        calendar.setNullDateButtonVisible(true);

        // Liste des résultats
        listModel = new DefaultListModel<>();
        calendarList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(calendarList);

        // Bouton de recherche
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchActivities();
            }
        });

        // Bouton d'impression
        JButton printButton = new JButton("Imprimer");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printCalendar();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(printButton);

        add(calendar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchActivities() {
        try {
            Date selectedDate = calendar.getDate();
            LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            CalendrierDAO calendrierDAO = new CalendrierDAO(connection);
            listModel.clear();
            List<Calendrier> calendriers = calendrierDAO.getParDate(date.atStartOfDay());
            for (Calendrier calendrier : calendriers) {
                listModel.addElement(calendrier.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void printCalendar() {
        try {
            calendarList.printAll(calendarList.getGraphics());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'impression : " + ex.getMessage());
        }
    }
}