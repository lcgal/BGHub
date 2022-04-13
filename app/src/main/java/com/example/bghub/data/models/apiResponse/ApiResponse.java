package com.example.bghub.data.models.apiResponse;

public class ApiResponse<T> {
    private Boolean Result;

    private T ReturnData;

    private String Error;

    public Boolean getResult ()
    {
        return Result;
    }

    public void setResult (Boolean Data)
    {
        this.Result = Data;
    }

    public String getError ()
    {
        return Error;
    }

    public void setError (String error)
    {
        this.Error = error;
    }

    public T getReturnData() { return ReturnData; }

    public void setReturnData(T returnData) { ReturnData = returnData; }
}
