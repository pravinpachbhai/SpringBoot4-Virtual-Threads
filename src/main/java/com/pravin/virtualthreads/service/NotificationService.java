package com.pravin.virtualthreads.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NotificationService {

    //NOTE - @Async method runs on a virtual thread rather than a platform thread.
    @Async
    public CompletableFuture<String> sendEmail(String user) {
        log.info("Thread: " + Thread.currentThread());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Email sent to " + user);
    }
}