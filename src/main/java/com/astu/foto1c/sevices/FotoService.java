package com.astu.foto1c.sevices;

import lombok.extern.java.Log;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.global.opencv_objdetect;
import org.bytedeco.opencv.global.opencv_videoio;
import org.bytedeco.opencv.opencv_core.*;
//import org.bytedeco.opencv.opencv_core.MatOfRect;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.CvCapture;
import org.opencv.core.MatOfRect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Base64;

import org.bytedeco.opencv.global.opencv_imgcodecs;

import javax.imageio.ImageIO;

@Service
public class FotoService {

    private static Logger logger = LoggerFactory.getLogger(FotoService.class.getName());

    public static FrameGrabber grabber = new MyOpenCVFrameGrabber(1);

    static {
        logger.info("start grabber");
        try {
            grabber.setImageWidth(320);
            grabber.setImageHeight(240);
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }

    public void setCamera(int i){

        try {
            grabber.stop();
            grabber = new MyOpenCVFrameGrabber(i, opencv_videoio.CAP_DSHOW);
            grabber.setImageWidth(1920);
            grabber.setImageHeight(1080);
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

    }

    public String getFrame(){

        String result = "no image";

        try {
            Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();

            OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();

            Mat loadedImage = converterToMat.convert(grabber.grabFrame());


            /*RectVector facesDetected = new RectVector();

            CascadeClassifier cascadeClassifier = new CascadeClassifier();
            int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
            String path = FotoService.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(6).split("!")[0].replaceAll("demo-0.0.1-SNAPSHOT.jar", "");
            logger.info(path);
            cascadeClassifier.load(path + "/haarcascades/haarcascade_frontalface_alt.xml");
            cascadeClassifier.detectMultiScale(
                    loadedImage,
                    facesDetected,
                    1.1,
                    3,
                    opencv_objdetect.CASCADE_SCALE_IMAGE,
                    new Size(minFaceSize, minFaceSize),
                    new Size()
            );
            int x = 0;
            int y = 0;
            int w = 0;
            int h = 0;
            for(Rect face : facesDetected.get()) {
                //opencv_imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar());
                x = face.x() + face.width()/2;
                y = face.y() + face.height()/2;
                w = (int)(face.width() * 1.7f);
                h = w * 9/7;
            }*/

            BufferedImage bufferedImage = java2dFrameConverter.getBufferedImage(converterToMat.convert(loadedImage));

            /*try {
                bufferedImage = bufferedImage.getSubimage(x-w/2, y-h/2, w, h);
                //bufferedImage = bufferedImage.getSubimage(x, y, w, h);
            } catch (RasterFormatException e){

            }*/

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"jpeg",baos);
            byte[] imageInByte = baos.toByteArray();
            result = Base64.getEncoder().encodeToString(imageInByte);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
