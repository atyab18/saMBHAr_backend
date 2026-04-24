package com.mess.service;

import com.mess.dto.*;
import com.mess.entity.Mess;
import com.mess.entity.User;
import com.mess.repository.MessRepository;
import com.mess.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final MessRepository messRepo;

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByMobileNo(req.getMobileNo())) {
            throw new RuntimeException("Mobile number already registered");
        }
        String role = req.getRole() == null ? "USER" : req.getRole().toUpperCase();
        User user = User.builder()
                .name(req.getName())
                .mobileNo(req.getMobileNo())
                .password(req.getPassword())
                .address(req.getAddress())
                .role(role)
                .build();
        user = userRepo.save(user);

        Long messId = null;
        if ("ADMIN".equals(role)) {
            String messName = req.getMessName() != null && !req.getMessName().isBlank()
                    ? req.getMessName() : (req.getName() + "'s Mess");
            String messAddress = req.getMessAddress() != null && !req.getMessAddress().isBlank()
                    ? req.getMessAddress() : req.getAddress();
            Mess mess = Mess.builder()
                    .name(messName)
                    .address(messAddress)
                    .ownerId(user.getId())
                    .build();
            mess = messRepo.save(mess);
            messId = mess.getId();
        }

        return AuthResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .mobileNo(user.getMobileNo())
                .address(user.getAddress())
                .role(user.getRole())
                .messId(messId)
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByMobileNo(req.getMobileNo())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        Long messId = null;
        if ("ADMIN".equals(user.getRole())) {
            messId = messRepo.findByOwnerId(user.getId()).map(Mess::getId).orElse(null);
        }
        return AuthResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .mobileNo(user.getMobileNo())
                .address(user.getAddress())
                .role(user.getRole())
                .messId(messId)
                .build();
    }

    public AuthResponse updateProfile(Long userId, ProfileUpdateRequest req) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (req.getName() != null) user.setName(req.getName());
        if (req.getMobileNo() != null && !req.getMobileNo().equals(user.getMobileNo())) {
            if (userRepo.existsByMobileNo(req.getMobileNo())) {
                throw new RuntimeException("Mobile number already in use");
            }
            user.setMobileNo(req.getMobileNo());
        }
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(req.getPassword());
        }
        if (req.getAddress() != null) user.setAddress(req.getAddress());
        user = userRepo.save(user);
        Long messId = null;
        if ("ADMIN".equals(user.getRole())) {
            messId = messRepo.findByOwnerId(user.getId()).map(Mess::getId).orElse(null);
        }
        return AuthResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .mobileNo(user.getMobileNo())
                .address(user.getAddress())
                .role(user.getRole())
                .messId(messId)
                .build();
    }
}
