package com.estherrc.my_money_saver.repository;

import com.estherrc.my_money_saver.model.User;
import com.estherrc.my_money_saver.model.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {
    Optional<UserProfilePicture> findByUser(User user);
}