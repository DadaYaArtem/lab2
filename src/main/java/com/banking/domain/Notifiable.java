package com.banking.domain;

public interface Notifiable {
    void sendNotification(String message);

    void sendEmailNotification(String subject, String body);

    void sendSMSNotification(String message);
}
