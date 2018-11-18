package com.vaskka.fun.tiff.ui.part;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static com.vaskka.fun.tiff.utils.UsualUtil.l;

/**
 * @program: TiffFun
 * @description: ImagePanel 展示图片的Panel
 * @author: Vaskka
 * @create: 2018/11/16 9:47 PM
 **/

public class ImagePanel extends JPanel {
    /**
     * 内部展示BufferedImage
     */
    private Image image;

    /**
     * 图片宽度
     */
    private int imgWidth;

    /**
     * 图片高度
     */
    private int imgHeight;

    public int getImgWidth() {
        return imgWidth;
    }

    /**
     * 设置展示宽度
     * @param imgWidth
     */
     void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    /**
     * 设置展示高度
     * @param imgHeight
     */
     void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

     ImagePanel() {
    }

    /**
     * 设置内部展示图片
     * @param img
     */
     void setImage(Image img) {

        image = img;
        setImgWidth(image.getWidth(this));
        setImgHeight(image.getHeight(this));

        int rw = imgWidth;
        int rh = imgHeight;
//        if (imgWidth > 150) {
//            rw = 150;
//        }
//        if (imgHeight > 150) {
//            rh = 150;
//        }

        setPreferredSize(new Dimension(rw, rh));
    }

    /**
     * 绘制图片
     * @param g1 画笔
     */
    @Override
    public void paintComponent(Graphics g1) {
        int x = 0;
        int y = 0;
        Graphics g = (Graphics) g1;
        if (null == image) {
            return;
        }

        g.drawImage(image, x, y, image.getWidth(this), image.getHeight(this),
                null);

        g = null;

    }
}
