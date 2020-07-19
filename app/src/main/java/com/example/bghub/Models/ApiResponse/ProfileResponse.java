package com.example.bghub.Models.ApiResponse;

import com.example.bghub.Models.Session.Profile;

public class ProfileResponse {

    private boolean Result;

    private Profile ReturnData;

    private String Error;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        this.Result = result;
    }

    public Profile getReturnData() {
        return ReturnData;
    }

    public void setReturnData(Profile profile) {
        this.ReturnData = profile;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        this.Error = error;
    }
}
