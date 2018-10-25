package io.borlandfcsd.cinemasystem.entity.dto;

import java.io.Serializable;

public class AjaxResponse implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
