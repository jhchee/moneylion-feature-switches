package com.github.jhchee.moneylionfeatureswitches.viewModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SwitchRequest {

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not valid")
    String email;

    @NotEmpty(message = "Feature name cannot be empty")
    String featureName;

    boolean enable;

    public SwitchRequest(String email, String featureName, boolean enable) {
        this.email = email;
        this.featureName = featureName;
        this.enable = enable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
