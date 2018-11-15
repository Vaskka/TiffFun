package com.vaskka.fun.tiff.entity;

/**
 * @program: TiffFun
 * @description: PointSet 描述一组坐标
 * @author: Vaskka
 * @create: 2018/11/14 6:28 PM
 **/

public class PointSet {

    private int x;
    private int y;

    public PointSet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
