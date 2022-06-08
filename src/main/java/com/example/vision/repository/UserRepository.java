package com.example.vision.repository;

import com.example.vision.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActive(String username, boolean b);

    Optional<User> findByUsername(String username);
}
