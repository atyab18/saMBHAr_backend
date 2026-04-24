package com.mess.controller;

import com.mess.entity.Mess;
import com.mess.service.MessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mess")
@RequiredArgsConstructor
public class MessController {

    private final MessService messService;

    @GetMapping
    public ResponseEntity<List<Mess>> all() {
        return ResponseEntity.ok(messService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mess> byId(@PathVariable Long id) {
        return ResponseEntity.ok(messService.findById(id));
    }
}
