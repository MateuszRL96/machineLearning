package com.example.machinelearning.controller;

import com.example.machinelearning.entity.Iris;
import com.example.machinelearning.service.IrisClassivyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
public class IrisController {
    private final IrisClassivyService service;

    @Autowired
    public IrisController(IrisClassivyService service) {
        this.service = service;
    }

    @GetMapping("/classify")
    public String classifyIris(@RequestParam double sepalLength, @RequestParam double sepalWidth,
                               @RequestParam double petalLength, @RequestParam double petalWidth) {
        return service.classify(sepalLength, sepalWidth, petalLength, petalWidth);
    }
}
