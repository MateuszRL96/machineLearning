package com.example.machinelearning.controller;

import com.example.machinelearning.entity.Iris;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IrisController {

    @PostMapping("/clasify-iris")
    public String classifyIris(@RequestBody Iris irisInput){
        //sadsdasd
        var prediction = model.predict(irisInput.toExample());
        return prediction.getOutput().getLabel();
    }
}
