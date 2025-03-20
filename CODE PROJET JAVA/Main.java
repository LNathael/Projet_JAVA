import java.sql.Connection;
import java.time.LocalDateTime;

import dao.ActivityDAO;
import dao.CalendrierDAO;
import model.Notification;
import model.Participant;
import service.NotificationService;
import util.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try {
            // Connexion à la base de données
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connexion à la base de données réussie.");

            // DAO pour les activités
            ActivityDAO activityDAO = new ActivityDAO(connection);
            System.out.println("Activités adaptées pour 12 ans :");
            activityDAO.getActivitesParTrancheAge(12).forEach(System.out::println);

            // DAO pour le calendrier
            CalendrierDAO calendrierDAO = new CalendrierDAO(connection);
            System.out.println("\nCalendrier pour aujourd'hui :");
            calendrierDAO.getParDate(LocalDateTime.now()).forEach(System.out::println);

            // Gestion des notifications
            Participant participant = new Participant(1, "Jean Dupont", 12, 0, 0, null);
            Notification notification = new Notification(1, participant, "Votre activité commence dans 30 minutes !");
            NotificationService notificationService = new NotificationService();
            notificationService.planifierNotification(notification, 5000); // 5 secondes pour tester
            System.out.println("Notification planifiée avec succès.");

        } catch (Exception e) {
            // Gestion des erreurs
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Fin de l'exécution du programme.");
        }
    }
}