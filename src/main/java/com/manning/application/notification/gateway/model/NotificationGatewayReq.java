package com.manning.application.notification.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public final class NotificationGatewayReq {
    @NotEmpty
    private String customerId;
    @NotEmpty
    private String notificationContent;
    private String emailAddress;
    private String emailSubject;
    private String phoneNumber;
    @Pattern(regexp = "^(EMAIL|SMS)$")
    private String notificationMode;
}
