package com.manning.application.notification.gateway.controllers;

import com.manning.application.notification.gateway.model.NotificationGatewayReq;
import com.manning.application.notification.gateway.model.NotificationGatewayRes;
import com.manning.application.notification.gateway.services.NotificationGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notification/send")
@RequiredArgsConstructor
public class NotificationGatewayController {
    private final NotificationGatewayService notificationGatewayService;

    @PostMapping
    public NotificationGatewayRes create(@RequestBody @Valid NotificationGatewayReq notificationGatewayReq) {
        return notificationGatewayService.send(notificationGatewayReq);
    }
}
