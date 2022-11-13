package com.my.document.generator.controller;

import com.my.document.generator.service.KreamService;
import com.my.document.generator.service.NikeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bot")
public class BotController implements InitializingBean {

    @Autowired
    private KreamService kreamService;

    @GetMapping("/kreamAmt/{model}")
    public String getKreamAtm(
            @PathVariable("model") String model
    ) {
        return kreamService.getKreamAtm(model);
    }

    @Autowired
    private NikeService nikeService;

    @PostMapping("/nike/new")
    public List<String> getNikeNewRelease(
            @RequestBody Map<String,List<String>> inputData
    ) {
        return nikeService.getNikeNewRelease(inputData.get("filters"));
    }

    @Override
    public void afterPropertiesSet() {
        //캐싱처리
        nikeService.getNikeNewRelease(new ArrayList<>());
    }

}
