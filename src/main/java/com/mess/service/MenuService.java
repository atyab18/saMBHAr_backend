package com.mess.service;

import com.mess.dto.MenuRequest;
import com.mess.entity.Menu;
import com.mess.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepo;

    public List<Menu> getByMess(Long messId) {
        return menuRepo.findByMessId(messId);
    }

    public Menu add(MenuRequest req) {
        Menu menu = Menu.builder()
                .messId(req.getMessId())
                .comboName(req.getComboName())
                .items(req.getItems())
                .price(req.getPrice())
                .build();
        return menuRepo.save(menu);
    }

    public Menu update(Long id, MenuRequest req) {
        Menu menu = menuRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        if (req.getComboName() != null) menu.setComboName(req.getComboName());
        if (req.getItems() != null) menu.setItems(req.getItems());
        if (req.getPrice() != null) menu.setPrice(req.getPrice());
        return menuRepo.save(menu);
    }

    public void delete(Long id) {
        menuRepo.deleteById(id);
    }
}
