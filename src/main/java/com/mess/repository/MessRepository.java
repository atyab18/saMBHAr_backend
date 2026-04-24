package com.mess.repository;

import com.mess.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessRepository extends JpaRepository<Mess, Long> {
    Optional<Mess> findByOwnerId(Long ownerId);
}
