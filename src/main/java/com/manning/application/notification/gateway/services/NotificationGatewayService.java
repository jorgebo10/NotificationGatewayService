package com.manning.application.notification.gateway.services;

import com.manning.application.notification.gateway.model.NotificationGatewayReq;
import com.manning.application.notification.gateway.model.NotificationGatewayRes;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGatewayService {
    private final JavaMailSender sender;
    @Value("${twilio.username}")
    private String twilioUsername;
    @Value("${twilio.password}")
    private String twilioPassword;
    @Value("${twilio.fromPhoneNumber}")
    private String twilioFromPhoneNumber;

    public NotificationGatewayRes send(NotificationGatewayReq notificationGatewayReq) {
        if (notificationGatewayReq.getNotificationMode().equals("EMAIL")) {
            if (sendEmailTool(notificationGatewayReq.getNotificationContent(),
                    notificationGatewayReq.getEmailAddress(),
                    notificationGatewayReq.getEmailSubject())) {
                NotificationGatewayRes notificationGatewayRes = new NotificationGatewayRes();
                notificationGatewayRes.setStatus("SUCCESS");
                notificationGatewayRes.setStatusDescription("Mail sent");
                return notificationGatewayRes;
            } else {
                NotificationGatewayRes notificationGatewayRes = new NotificationGatewayRes();
                notificationGatewayRes.setStatus("ERROR");
                notificationGatewayRes.setStatusDescription("Mail no sent");
                return notificationGatewayRes;
            }
        } else {
            Twilio.init(twilioUsername, twilioPassword);
            Message message = Message.creator(new PhoneNumber(notificationGatewayReq.getPhoneNumber()),
                            new PhoneNumber("15208660139"), notificationGatewayReq.getNotificationContent())
                    .create();
            if (message.getSid() != null) {
                NotificationGatewayRes notificationGatewayRes = new NotificationGatewayRes();
                notificationGatewayRes.setStatus("SUCCESS");
                notificationGatewayRes.setStatusDescription("Mail sent");
                return notificationGatewayRes;
            } else {
                NotificationGatewayRes notificationGatewayRes = new NotificationGatewayRes();
                notificationGatewayRes.setStatus("ERROR");
                notificationGatewayRes.setStatusDescription("Method unknown");
                return notificationGatewayRes;
            }
        }
    }

    private boolean sendEmailTool(String textMessage, String email, String subject) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}