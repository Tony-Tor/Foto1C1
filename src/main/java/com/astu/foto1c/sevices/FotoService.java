package com.astu.foto1c.sevices;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.UMat;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Base64;

import org.bytedeco.opencv.global.opencv_imgcodecs;

import javax.imageio.ImageIO;

@Service
public class FotoService {

    public static FrameGrabber grabber = new OpenCVFrameGrabber(0);
    public static int CAMERA = 0;

    public void setCamera(int i){
        CAMERA = i;

    }

    public String getFrame(){

        String result = "no image";

        try {

            grabber.stop();
            grabber = new OpenCVFrameGrabber(CAMERA);
            grabber.start();

            Frame frame = grabber.grab();
            Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
            BufferedImage bufferedImage = java2dFrameConverter.getBufferedImage(grabber.grabFrame());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png",baos);
            byte[] imageInByte = baos.toByteArray();
            result = Base64.getEncoder().encodeToString(imageInByte);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
