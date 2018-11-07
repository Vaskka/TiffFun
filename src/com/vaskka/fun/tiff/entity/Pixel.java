package com.vaskka.fun.tiff.entity;

public class Pixel {
    //像素的RGB三个分量，每个分量用short类型保存
    private short red;
    private short green;
    private short blue;

    public short getRed() {
        return red;
    }

    public void setRed(short red) {
        this.red = red;
    }

    public short getGreen() {
        return green;
    }

    public void setGreen(short green) {
        this.green = green;
    }

    public short getBlue() {
        return blue;
    }

    public void setBlue(short blue) {
        this.blue = blue;
    }

    public Pixel(short red, short green, short blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Pixel() {}

    @Override
    public String toString() {
        return "com.vaskka.fun.tiff.entity.Pixel{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    /**
     * 将this 更改为目标像素值
     * @param targetPixel 目标像素
     */
    public void changeToThisPixel(Pixel targetPixel) {
        this.blue  = targetPixel.getBlue();
        this.green = targetPixel.getGreen();
        this.red   = targetPixel.getRed();
    }
}
