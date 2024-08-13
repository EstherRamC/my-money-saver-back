package com.estherrc.my_money_saver.controller;

import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.model.UserProfilePicture;
import com.estherrc.my_money_saver.service.UserProfilePictureService;
import com.estherrc.my_money_saver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.util.Base64;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfilePictureService userProfilePictureService;

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                           @RequestParam(value = "username", required = false) String username,
                                           @RequestParam("uid") String uid) {
        try {
            userProfilePictureService.updateUserProfile(uid, username, file);
            return ResponseEntity.ok("Profile updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating profile: " + e.getMessage());
        }
    }

    @GetMapping("/profile/image/{uid}")
    public ResponseEntity<String> getUserProfileImage(@PathVariable String uid) {
        Optional<User> user = userService.findByUid(uid);
        if (user.isPresent()) {
            Optional<UserProfilePicture> profilePicture = userProfilePictureService.getProfilePicture(user.get());
            if (profilePicture.isPresent()) {
                String base64Image = Base64.getEncoder().encodeToString(profilePicture.get().getProfilePicture());
                return ResponseEntity.ok().body(base64Image);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorization) {
        try {
            String token = authorization.replace("Bearer ", "");
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            String uid = decodedToken.getUid();

            Optional<User> user = userService.findByUid(uid);

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching user: " + e.getMessage());
        }
    }

}