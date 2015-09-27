package com.tvoyagryvnia.bean.response;


public class ResultBean {

    private Boolean isSuccess;
    private String errorMessage;

    public ResultBean(Boolean success, String errorMessage) {
        isSuccess = success;
        this.errorMessage = errorMessage;
    }

    public ResultBean(Boolean success) {
        isSuccess = success;
        errorMessage = "";
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
