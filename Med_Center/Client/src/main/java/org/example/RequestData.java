package org.example;

import med.networking.RequestType;

public abstract class RequestData {

    public RequestType type;
    public abstract Object getObject();

    public RequestData(RequestType type) {
        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
