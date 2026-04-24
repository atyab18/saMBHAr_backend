package com.mess.controller;

import com.mess.dto.MenuRequest;
import com.mess.entity.Menu;
import com.mess.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/mess/{messId}")
    public ResponseEntity<List<Menu>> getByMess(@PathVariable Long messId) {
        return ResponseEntity.ok(menuService.getByMess(messId));
    }

    @PostMapping
    public ResponseEntity<Menu> add(@RequestBody MenuRequest req) {
        return ResponseEntity.ok(menuService.add(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> update(@PathVariable Long id, @RequestBody MenuRequest req) {
        return ResponseEntity.ok(menuService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
