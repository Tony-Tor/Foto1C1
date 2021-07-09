package com.astu.foto1c.controllers;

import com.astu.foto1c.Image;
import com.astu.foto1c.sevices.FotoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping(value = "/v1/public/photo", produces = MediaType.APPLICATION_JSON_VALUE)
public class FotoController {

    private final FotoService service;

    public FotoController(FotoService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String getHello(){
        return LocalDateTime.now().toString();
    }

    @GetMapping("/frame")
    public String getFrame(){
         return service.getFrame();
    }

    @GetMapping(value = "/frame2", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFrame2(){
        return Base64.getDecoder().decode(service.getFrame());
    }

    @GetMapping("/cameraId/{id}")
    public void setCamera(@PathVariable("id") Integer id){
        service.setCamera(id);
    }

}
