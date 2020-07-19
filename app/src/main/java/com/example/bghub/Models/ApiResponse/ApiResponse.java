package com.example.bghub.Models.ApiResponse;

public class ApiResponse<T> {
    private Boolean Result;

    private T ResultData;

    private String Error;

    public Boolean getData ()
    {
        return Result;
    }

    public void setData (Boolean Data)
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

    public T getResultData() { return ResultData; }

    public void setResultData(T resultData) { ResultData = resultData; }
}
