package com.github.jhchee.moneylionfeatureswitches.controller;

import com.github.jhchee.moneylionfeatureswitches.model.Feature;
import com.github.jhchee.moneylionfeatureswitches.model.User;
import com.github.jhchee.moneylionfeatureswitches.repository.IFeatureRepo;
import com.github.jhchee.moneylionfeatureswitches.repository.IUserRepo;
import com.github.jhchee.moneylionfeatureswitches.viewModel.SwitchRequest;
import com.github.jhchee.moneylionfeatureswitches.viewModel.SwitchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        String email = switchRequest.getEmail();
        String featureName = switchRequest.getFeatureName();
        boolean isEnable = switchRequest.isEnable();

        User user = userRepository.findUserByEmail(email);
        Feature feature = featureRepository.findFeatureByName(featureName);

        // the switch is on when there's an entry in the user_feature table
        boolean isSwitchOn = user != null && feature != null && user.getFeatures().contains(feature);

        // perform update only if the request to enable is opposite to the current switch status
        boolean shouldUpdate = isEnable ^ isSwitchOn;

        // return not modified message when it won't be updated
        if (!shouldUpdate) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Resource is not updated.");
        }

        // update the status
        if (isEnable) {
            // create user if it hasn't exist
            if (user == null) {
                user = new User(email);
            }

            // create feature if it hasn't exist
            if (feature == null) {
                feature = new Feature(featureName);
            }

            // switch on by adding to the table
            user.getFeatures().add(feature);
        } else {
            user.getFeatures().remove(feature);
        }

        userRepository.save(user);
    }
}
