package com.ibas.brta.vehims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.repository.SUserRepository;
import com.ibas.brta.vehims.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for sending notifications to users.
 */
@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SUserRepository sUserRepository;

    public void sendNotification() {
        // Get all active users from the database
        // List<SUser> users = userRepository.findByIsActiveTrueOrderByNameEnAsc();
        List<User> users = userRepository.findAll();

        // Check if there are any active users
        if (users.isEmpty()) {
            log.info("No active users found.");
            return;
        }

        // Log the number of active users
        log.info("Found {} active users.", users.size());

        // Send the notification to each user
        sendNotificationToUsers(users);

    }

    private void sendNotificationToUsers(List<User> users) {

        log.info("Inside sendNotificationToUsers function");
        // Process each user to send the notification
        for (User user : users) {
            log.info("Inside foreach loop");
            // Process the user's data to build the notification content
            String notificationContent = "Your vehicle has been updated. Please check the status.";

            // Send the notification to the user
            // notificationTemplateService.sendNotification(user.getMobile(),
            // notificationContent);

            // Logic to send a notification (e.g., email, SMS)
            System.out.println("Sending notification to users...");
            // You can integrate with an email service like JavaMailSender here
        }
    }
}
