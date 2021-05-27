package com.astu.foto1c;

import lombok.Data;

@Data
public class Image {
    private String base64;

    public Image(String base64) {
        this.base64 = base64;
    }
}
