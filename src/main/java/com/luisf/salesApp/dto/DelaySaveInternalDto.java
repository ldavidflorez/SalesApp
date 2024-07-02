package com.luisf.salesApp.dto;

import com.luisf.salesApp.model.Delay;

public class DelaySaveInternalDto {
    private Delay delay;

    private String message;

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DelaySaveInternalDto{" +
                "delay=" + delay +
                ", message='" + message + '\'' +
                '}';
    }
}
