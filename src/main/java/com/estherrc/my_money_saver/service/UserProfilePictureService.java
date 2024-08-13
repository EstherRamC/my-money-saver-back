package com.estherrc.my_money_saver.service;

import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.model.UserProfilePicture;
import com.estherrc.my_money_saver.repository.UserProfilePictureRepository;
import com.estherrc.my_money_saver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserProfilePictureService {

    @Autowired
    private UserProfilePictureRepository userProfilePictureRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<UserProfilePicture> getProfilePicture(User user) {
        return userProfilePictureRepository.findByUser(user);
    }

    public void saveProfilePicture(User user, MultipartFile file) throws IOException {
        UserProfilePicture userProfilePicture = new UserProfilePicture();
        userProfilePicture.setUser(user);
        userProfilePicture.setProfilePicture(file.getBytes());
        userProfilePictureRepository.save(userProfilePicture);
    }

    public void updateUserProfile(String uid, String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUid(uid).orElseThrow(() -> new RuntimeException("User not found"));

        // Update username if provided
        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }

        // Update profile picture if provided
        if (file != null && !file.isEmpty()) {
            UserProfilePicture profilePicture = userProfilePictureRepository.findByUser(user)
                    .orElse(new UserProfilePicture());
            profilePicture.setUser(user);
            profilePicture.setProfilePicture(file.getBytes());
            userProfilePictureRepository.save(profilePicture);
        }

        userRepository.save(user);
    }

}
