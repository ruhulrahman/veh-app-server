package com.ibas.brta.vehims.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibas.brta.vehims.service.NotificationService;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    // Job 1: Runs every minute
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleEveryMinuteNotification() {
        System.out.println("Cron Job 1 triggered - Sending notifications...");
        notificationService.sendNotification();
    }

    // Job 2: Runs every day at 8 AM
    @Scheduled(cron = "0 0 8 * * ?") // At 8:00 AM every day
    public void scheduleMorningNotification() {
        System.out.println("Cron Job 2 triggered - Sending notifications...");
        notificationService.sendNotification();
    }

    // Job 3: Runs every Monday at 9 AM
    @Scheduled(cron = "0 0 9 ? * MON")
    public void scheduleWeeklyNotification() {
        System.out.println("Cron Job 3 triggered - Sending notifications...");
        notificationService.sendNotification();
    }
}
