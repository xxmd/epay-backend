package com.example.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enum")
@AllArgsConstructor
public class EnumController {
    private final EnumRegistry enumRegistry;

    @GetMapping
    public Map<String, List<Map<String, String>>> getAll() {
        return enumRegistry.getAll();
    }

    @GetMapping("/{name}")
    public List<Map<String, String>> getByName(@PathVariable String name) {
        return enumRegistry.get(name);
    }
}
