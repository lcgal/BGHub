package com.example.bghub.Models.ApiResponse;

public class BooleanResponse {
    private Boolean Data;

    private String Error;

    public Boolean getData ()
    {
        return Data;
    }

    public void setData (Boolean Data)
    {
        this.Data = Data;
    }

    public String getError ()
    {
        return Error;
    }

    public void setError (String error)
    {
        this.Error = error;
    }
}
