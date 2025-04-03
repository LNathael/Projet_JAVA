package gui.panels;

import com.toedter.calendar.JCalendar;
import dao.CalendrierDAO;
import model.Calendrier;

import javax.swing.*;
import java.awt.*;
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


        // Liste des résultats
        listModel = new DefaultListModel<>();
        calendarList = new JList<>(listModel);
        calendarList.setCellRenderer(new CalendarListRenderer()); // Code couleur
        JScrollPane scrollPane = new JScrollPane(calendarList);

        // Bouton de recherche
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> searchActivities());

        // Bouton d'impression
        JButton printButton = new JButton("Imprimer");
        printButton.addActionListener(e -> printCalendar());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(printButton);

        JPanel calendarPanel = new JPanel();
        calendarPanel.add(calendar);// revoir erreur 
        add(calendarPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajouter un listener pour afficher les détails d'une activité
        calendarList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedValue = calendarList.getSelectedValue();
                if (selectedValue != null) {
                    JOptionPane.showMessageDialog(this, selectedValue, "Détails de l'activité", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void searchActivities() {
        try {
            Date selectedDate = calendar.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            CalendrierDAO calendrierDAO = new CalendrierDAO(this.connection);
            listModel.clear();
            List<Calendrier> calendriers = calendrierDAO.getParDate(date.atStartOfDay());

            if (calendriers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune activité trouvée pour la date sélectionnée.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Calendrier calendrier : calendriers) {
                String status = calendrier.getActivity() != null ? calendrier.getActivity().getStatus() : "INCONNU";
                String colorCoded = (status.equalsIgnoreCase("VALIDÉ") ? "[VERT] " : "[ORANGE] ") + calendrier.toString();
                listModel.addElement(colorCoded);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche des activités : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printCalendar() {
        try {
            calendarList.printAll(calendarList.getGraphics());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'impression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Renderer pour appliquer le code couleur
    private static class CalendarListRenderer extends DefaultListCellRenderer {
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