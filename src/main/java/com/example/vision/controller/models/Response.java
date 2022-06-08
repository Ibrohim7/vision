package com.example.vision.controller.models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Response {
    private Object data;
    private Status status;

    public Response(Object data, Status status) {
        this.data = data;
        this.status = new Status(status.getCode(), status.getMessage());
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Status getMessage() {
        return status;
    }

    public void setMessage(Status message) {
        this.status = new Status(message.getCode(), message.getMessage());
    }
}
