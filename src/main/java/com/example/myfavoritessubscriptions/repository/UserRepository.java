package com.example.myfavoritessubscriptions.repository;

import com.example.myfavoritessubscriptions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
