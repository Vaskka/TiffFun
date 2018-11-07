package com.vaskka.fun.tiff.utils;

import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtility {

    //从文件读取TIFF图像数据到内存，图像数据存储在PixImage对象中
    public static PixImage readTIFFImageFromFile(String vInputFilePath) {
        File file = new File(vInputFilePath);
        PixImage pixImage = new PixImage();

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            Pixel[][] pixels = new Pixel[width][height];

            for (int x = 0; x < width; ++x)
                for (int y = 0; y < height; ++y) {
                    int rgb = bufferedImage.getRGB(x, y);
                    short r = (short) ((rgb & 0xff0000) >> 16);
                    short g = (short) ((rgb & 0xff00) >> 8);
                    short b = (short) (rgb & 0xff);
                    pixels[x][y] = new Pixel();
                    pixels[x][y].setRed(r);
                    pixels[x][y].setGreen(g);
                    pixels[x][y].setBlue(b);
                }

            pixImage.setWidth(width);
            pixImage.setHeight(height);
            pixImage.setPixels(pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pixImage;
    }

    //将图像数据从内存写出，保存为TIFF图像文件
    public static void writeTIFFImageToFile(PixImage vPixImage, String vOutputFilePath) {
        BufferedImage bufferedImage = new BufferedImage(vPixImage.getWidth(), vPixImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        File file = new File(vOutputFilePath);

        Pixel[][] pixels = vPixImage.getPixels();
        for (int x = 0; x < vPixImage.getWidth(); ++x)
            for (int y = 0; y < vPixImage.getHeight(); ++y) {
                short r = pixels[x][y].getRed();
                short g = pixels[x][y].getGreen();
                short b = pixels[x][y].getBlue();
                int rgb = new Color(r,g,b).getRGB();
                bufferedImage.setRGB(x, y, rgb);
            }

        try {
            ImageIO.write(bufferedImage, "tiff", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将能量值转换为灰色像素值
    public static short energy2gray(long vEnergy) {
        short intensity = (short) (30.0 * Math.log(1.0 + (double) vEnergy) - 256.0);

        // Make sure the returned intensity is in the range 0...255, regardless of
        // the input value.
        if (intensity < 0) {
            intensity = 0;
        } else if (intensity > 255) {
            intensity = 255;
        }
        return intensity;
    }

}
