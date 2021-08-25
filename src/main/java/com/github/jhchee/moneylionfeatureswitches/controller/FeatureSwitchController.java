package com.github.jhchee.moneylionfeatureswitches.controller;

import com.github.jhchee.moneylionfeatureswitches.model.Feature;
import com.github.jhchee.moneylionfeatureswitches.model.User;
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
        User user = userRepository.findUserByEmail(email);
        Feature feature = featureRepository.findFeatureByName(featureName);

        SwitchResponse response = new SwitchResponse(false);

        if (user != null && feature != null) {
            // the switch is on if it exists in the user_feature table
            boolean canAccess = user.getFeatures().contains(feature);
            response.setCanAccess(canAccess);
        }

        return response;
    }

    @PostMapping
    public void updateSwitchStatus(@RequestBody SwitchRequest switchRequest) {
        // TODO: update switch status with email, feature name, should enable
    }
}
