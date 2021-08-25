package com.github.jhchee.moneylionfeatureswitches.controller;

import com.github.jhchee.moneylionfeatureswitches.repository.IFeatureRepo;
import com.github.jhchee.moneylionfeatureswitches.repository.IUserRepo;
import com.github.jhchee.moneylionfeatureswitches.viewModel.SwitchRequest;
import com.github.jhchee.moneylionfeatureswitches.viewModel.SwitchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feature")
public class FeatureSwitchController {
    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private IFeatureRepo featureRepository;

    @GetMapping
    public SwitchResponse getSwitchStatus(@RequestParam String email, @RequestParam String featureName) {
        // TODO: get switch status from user table with email, feature name
    }

    @PostMapping
    public void updateSwitchStatus(@RequestBody SwitchRequest switchRequest) {
        // TODO: update switch status with email, feature name, should enable
    }
}
