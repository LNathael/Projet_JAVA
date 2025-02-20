package service;
import java.util.Timer;
import java.util.TimerTask;

import model.Notification;

public class NotificationService {
    private Timer timer = new Timer();

    public void planifierNotification(Notification notification, long delayMillis) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notification.envoyer();
            }
        }, delayMillis);
    }

    public void notifierResponsables(String message) {
        // Logique pour notifier les responsables
        System.out.println("Notification aux responsables: " + message);
    }
}