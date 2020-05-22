package com.stratio.qa.models;

import java.util.List;

public class BaseResponseList<T> extends BaseResponse {

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
