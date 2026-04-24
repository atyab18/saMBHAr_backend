package com.mess.repository;

import com.mess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileNo(String mobileNo);
    boolean existsByMobileNo(String mobileNo);
}
