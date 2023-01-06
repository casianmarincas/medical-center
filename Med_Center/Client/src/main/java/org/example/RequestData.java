package org.example;

import med.networking.RequestType;

public class RequestData {

    public RequestType type;

    public Object object;

    public RequestData(RequestType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
