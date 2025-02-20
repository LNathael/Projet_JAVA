package service;

import model.Notification;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService {
    public void planifierNotification(Notification notification, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notification.envoyer();
            }
        }, delay);
    }

    public void notifierResponsable(Notification notification, String message) {
        notification.notifierResponsable(message);
    }
}