package com.banking.service;

import com.banking.domain.Notifiable;
import com.banking.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<NotificationRecord> notificationHistory;

    public NotificationService() {
        this.notificationHistory = new ArrayList<>();
    }

    public void sendTransactionNotification(Notifiable recipient, String transactionDetails) {
        String message = "Transaction completed: " + transactionDetails;
        recipient.sendNotification(message);
        logNotification("TRANSACTION", recipient.toString(), message);
    }

    public void sendAccountAlert(Customer customer, String alertMessage) {
        customer.sendEmailNotification("Account Alert", alertMessage);
        customer.sendSMSNotification(alertMessage);
        logNotification("ALERT", customer.getCustomerId(), alertMessage);
    }

    public void sendLowBalanceWarning(Customer customer, double balance) {
        String message = String.format("Warning: Your account balance is low: $%.2f", balance);
        customer.sendSMSNotification(message);
        logNotification("LOW_BALANCE", customer.getCustomerId(), message);
    }

    public void sendPaymentReminder(Customer customer, double amount, String dueDate) {
        String subject = "Payment Reminder";
        String body = String.format("Payment of $%.2f is due on %s", amount, dueDate);
        customer.sendEmailNotification(subject, body);
        logNotification("PAYMENT_REMINDER", customer.getCustomerId(), body);
    }

    public void sendSecurityAlert(Customer customer, String securityEvent) {
        String message = "Security Alert: " + securityEvent;
        customer.sendEmailNotification("Security Alert", message);
        customer.sendSMSNotification(message);
        logNotification("SECURITY", customer.getCustomerId(), message);
    }

    private void logNotification(String type, String recipientId, String message) {
        NotificationRecord record = new NotificationRecord(type, recipientId, message);
        notificationHistory.add(record);
    }

    public List<NotificationRecord> getNotificationHistory() {
        return new ArrayList<>(notificationHistory);
    }

    public static class NotificationRecord {
        private String type;
        private String recipientId;
        private String message;
        private LocalDateTime sentAt;

        public NotificationRecord(String type, String recipientId, String message) {
            this.type = type;
            this.recipientId = recipientId;
            this.message = message;
            this.sentAt = LocalDateTime.now();
        }

        public String getType() {
            return type;
        }

        public String getRecipientId() {
            return recipientId;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getSentAt() {
            return sentAt;
        }
    }
}
