package com.github.jhchee.moneylionfeatureswitches.viewModel;

public class SwitchResponse {

    boolean canAccess;

    public SwitchResponse(boolean canAccess) {
        this.canAccess = canAccess;
    }

    public boolean isCanAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }
}
