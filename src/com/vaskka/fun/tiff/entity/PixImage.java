package com.vaskka.fun.tiff.entity;

public class PixImage {
    private Pixel[][] pixels;  //图像的像素存储在二维数组中，例如pixels[x][y]表示图像中坐标(x,y)位置的像素
    private int width;         //图像宽度
    private int height;        //图像高度

    public Pixel[][] getPixels() {
        return pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public PixImage(Pixel[][] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public PixImage() {}

    public void setHeight(int height) {
        this.height = height;
    }


}
