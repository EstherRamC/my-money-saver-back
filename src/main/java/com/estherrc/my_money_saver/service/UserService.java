package com.estherrc.my_money_saver.service;

import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.model.UserProfilePicture;
import com.estherrc.my_money_saver.repository.UserRepository;
import com.estherrc.my_money_saver.repository.UserProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUid(String uid) {
        return userRepository.findByUid(uid);
    }

    public void updateUserProfile(String uid, String username) {
        Optional<User> userOptional = userRepository.findByUid(uid);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(username);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with uid: " + uid);
        }
    }
}
