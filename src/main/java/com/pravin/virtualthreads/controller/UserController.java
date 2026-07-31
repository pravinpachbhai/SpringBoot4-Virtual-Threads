package com.pravin.virtualthreads.controller;

import com.pravin.virtualthreads.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/notification")
public class UserController {

    private final NotificationService notificationService;

    public UserController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notify")
    public CompletableFuture<String> notifyUser() {
        return notificationService.sendEmail("Pravin Pachbhai");
    }
}