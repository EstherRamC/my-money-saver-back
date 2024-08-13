package com.estherrc.my_money_saver.controller;

import com.estherrc.my_money_saver.service.FirebaseSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirebaseSyncController {

    @Autowired
    private FirebaseSyncService firebaseSyncService;

    @PostMapping("/syncUser")
    public ResponseEntity<?> syncUser(@RequestHeader("Authorization") String authorization) {
        try {
            String token = authorization.replace("Bearer ", "");
            firebaseSyncService.syncUser(token);
            return ResponseEntity.ok("User synchronized");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error synchronizing user: " + e.getMessage());
        }
    }
}
