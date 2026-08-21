package com.example.web.controller;

import com.example.common.domain.Result;
import com.example.web.service.EnumService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enum")
@AllArgsConstructor
public class EnumController {
    private final EnumService enumService;

    @GetMapping("/{name}")
    public Result<List<Map<String, String>>> getByName(@PathVariable String name) {
        return Result.success(enumService.get(name));
    }
}
