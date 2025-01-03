import java.sql.Connection;
import java.time.LocalDateTime;

import dao.ActiviteDAO;
import dao.CalendrierDAO;
import model.Notification;
import model.Participant;
import util.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données
            Connection connection = DatabaseConnection.getConnection();

            // DAO pour les activités
            ActiviteDAO activiteDAO = new ActiviteDAO(connection);
            System.out.println("Activités adaptées pour 12 ans :");
            activiteDAO.getActivitesParTrancheAge(12).forEach(System.out::println);

            // DAO pour le calendrier
            CalendrierDAO calendrierDAO = new CalendrierDAO(connection);
            System.out.println("\nCalendrier pour aujourd'hui :");
            calendrierDAO.getParDate(LocalDateTime.now()).forEach(System.out::println);

            // Notification
            Participant participant = new Participant(1, "Jean Dupont", 12);
            Notification notification = new Notification(1, participant, "Votre activité commence dans 30 minutes !");
            NotificationService notificationService = new NotificationService();
            notificationService.planifierNotification(notification, 5000); // 5 secondes pour tester

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
