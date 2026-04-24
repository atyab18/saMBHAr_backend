package com.mess.service;

import com.mess.entity.Mess;
import com.mess.repository.MessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessService {

    private final MessRepository messRepo;

    public List<Mess> findAll() {
        return messRepo.findAll();
    }

    public Mess findById(Long id) {
        return messRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mess not found"));
    }
}
