package com.tvoyagryvnia.bean.response;

public class ResultBeanWithData extends ResultBean {
    private Object data;

    public ResultBeanWithData(Boolean success, Object data) {
        super(success);
        this.data = data;
    }

    public ResultBeanWithData(Boolean success, String errorMessage, Object data) {
        super(success, errorMessage);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
