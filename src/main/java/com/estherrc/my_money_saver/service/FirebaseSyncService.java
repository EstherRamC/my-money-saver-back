package com.estherrc.my_money_saver.service;

import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirebaseSyncService {


    @Autowired
    private UserRepository userRepository;

    public void syncUser(String idToken) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();
        String name = decodedToken.getName();

        if (name == null || name.isEmpty()) {
            name = email.split("@")[0];
        }

        if(userRepository.findByUid(uid) != null) {
            User user = new User();
            user.setUid(uid);
            user.setEmail(email);
            user.setUsername(name);

            userRepository.save(user);
        }

    }
}
