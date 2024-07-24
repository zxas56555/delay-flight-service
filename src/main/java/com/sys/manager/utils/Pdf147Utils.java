package com.sys.manager.utils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.google.zxing.NotFoundException;

public class Pdf147Utils {

    public static void main(String[] args) {
        try {
            File imageFile = new File("C:\\Users\\12097\\Desktop\\222.jpg");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            String decodedText = result.getText();
            System.out.println("Decoded PDF417 Code: " + decodedText);
//"M1QIMAOYU             EPXCQJ8 TNAINCMU 6359 185S046A0029 147>3180WO4185BMU              29781225291837400MU MU 604026565975     20K"
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}