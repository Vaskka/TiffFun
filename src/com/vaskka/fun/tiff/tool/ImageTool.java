package com.vaskka.fun.tiff.tool;

import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.utils.ImageUtility;
import com.vaskka.fun.tiff.utils.UsualUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.io.File.separator;

/**
 * @program: TiffFun
 * @description: ImageTool 图像处理工具
 * @author: Vaskka
 * @create: 2018/11/7 5:19 PM
 **/

public class ImageTool {

    /**
     * 转换图像到boxblur图像，保存在imagePath同一路径
     * @param imagePath 图片路径
     * @param count 迭代次数
     * @return BufferedImage
     * @throws IOException 文件读写错误
     * @throws TiffChangeRunningException 运行异常
     */
    public static BufferedImage getBoxBlurImage(String imagePath, int count) throws IOException, TiffChangeRunningException {
        String outputPath = UsualUtil.getFileDirectoryPath(imagePath) + separator + UsualUtil.changeFileToBoxBlurOutputFileName(UsualUtil.getFileName(imagePath));

        innerBoxBlurAndWriteInFile(imagePath, outputPath, count);

        return ImageIO.read(new File(outputPath));
    }


    /**
     * 转换图像到boxblur图像，保存在imagePath同一路径
     * @param imagePath 图片路径
     * @param outputPath 输出路径
     * @param count
     * @return BufferedImage
     * @throws TiffChangeRunningException 运行异常
     * @throws IOException 文件读写异常
     */
    public static BufferedImage getBoxBlurImage(String imagePath, String outputPath, int count) throws TiffChangeRunningException, IOException {
        innerBoxBlurAndWriteInFile(imagePath, outputPath, count);

        return ImageIO.read(new File(outputPath));
    }

    /**
     * 得到输入输出文件路径和迭代次数 返回储存好的boxblur图像数据
     * @param sourceImagePath 源文件路径
     * @param outputImagePath 目标文件路径
     * @param count 迭代次数
     */
    private static void innerBoxBlurAndWriteInFile(String sourceImagePath, String outputImagePath, int count) throws TiffChangeRunningException {

        var image = ImageUtility.readTIFFImageFromFile(sourceImagePath);

        for (int i = 0; i < count; i++) {
            fromSouceImageGetBoxBlurImage(image);
        }

        ImageUtility.writeTIFFImageToFile(image, outputImagePath);

    }


    /**
     * 从源图像得到box blur后图像
     * @param sourceImage 源图像
     * @return PixImage
     */
    private static PixImage fromSouceImageGetBoxBlurImage(PixImage sourceImage) throws TiffChangeRunningException {

        var pixels = sourceImage.getPixels();

        for (int i = 0; i < sourceImage.getWidth(); i++) {
            for (int j = 0; j < sourceImage.getHeight(); j++) {
                // 判断边缘或角落
                if(i == 0 || i == sourceImage.getWidth() - 1 || j == 0 || j == sourceImage.getHeight() - 1) {
                    if ((i == 0 && j == 0)
                            || (i == 0 && j == sourceImage.getHeight())
                            || (j == 0 && i == sourceImage.getWidth())
                            || (j == sourceImage.getHeight() && i == sourceImage.getWidth())) {
                        // 角落处理
                        var iLength = new int[4];
                        var jLength = new int[4];

                        if (i == 0 && j == 0) {
                            // 左上角
                            int c = 0;
                            for (int k = 0; k < 2; k++) {
                                for (int l = 0; l < 2; l++) {
                                    iLength[c] = k;
                                    jLength[c] = l;
                                    c++;
                                }
                            }
                        }
                        else {
                            int c = 0;
                            if (i == 0 && j == sourceImage.getHeight()) {
                                // 左下角
                                for (int k = 0; k < 2; k++) {
                                    for (int l = sourceImage.getHeight() - 1; l > sourceImage.getHeight() - 3; l--) {
                                        iLength[c] = k;
                                        jLength[c] = l;
                                        c++;
                                    }
                                }
                            }
                            else {
                                if (i == sourceImage.getHeight() && j == 0) {
                                    // 右上角
                                    for (int k = 0; k < 2; k++) {
                                        for (int l = sourceImage.getWidth() - 1; l > sourceImage.getWidth() - 3; l--) {
                                            iLength[c] = l;
                                            jLength[c] = k;
                                            c++;
                                        }
                                    }
                                }
                                else {
                                    // 右下角
                                    for (int l = sourceImage.getWidth() - 1; l > sourceImage.getWidth() - 3; l--) {
                                        for (int k = sourceImage.getHeight() - 1; k >sourceImage.getHeight() - 3; k--) {
                                            iLength[c] = l;
                                            jLength[c] = k;
                                            c++;
                                        }

                                    }

                                }
                            }
                        }

                        // 写入像素值
                        changePixelOnPixels(pixels, i, j, getBoxBlurPixel(pixels, iLength, jLength));

                    }
                    else {
                        // 边缘处理
                        int c = 0;
                        var iLength = new int[6];
                        var jLength = new int[6];

                        if (i == 0) {
                            // 左侧

                            for (int k = 0; k < 2; k++) {
                                for (int m = j - 1; m < j + 2; m++) {
                                    iLength[c] = k;
                                    jLength[c] = m;
                                    c++;
                                }
                            }

                        }
                        else {
                            if (j == 0) {
                                // 上侧

                                for (int k = 0; k < 2; k++) {
                                    for (int m = i - 1; m < i + 2; m++) {
                                        iLength[c] = m;
                                        jLength[c] = k;
                                        c++;
                                    }
                                }
                            }
                            else {
                                if (j == sourceImage.getHeight()) {
                                    // 下侧
                                    for (int k = j - 1; k < sourceImage.getHeight(); k++) {
                                        for (int m = i - 1; m < i + 2; m++) {
                                            iLength[c] = m;
                                            jLength[c] = k;
                                            c++;
                                        }
                                    }

                                }
                                else {
                                    // 右侧
                                    for (int k = i - 1; k < sourceImage.getWidth(); k++) {
                                        for (int m = j - 1; m < j + 2; m++) {
                                            iLength[c] = k;
                                            jLength[c] = m;
                                            c++;
                                        }
                                    }
                                }
                            }

                        }
                        // 写入像素值
                        changePixelOnPixels(pixels, i, j, getBoxBlurPixel(pixels, iLength, jLength));

                    }

                }
                else {
                    // 不是边缘
                    int c = 0;
                    var iLength = new int[9];
                    var jLength = new int[9];

                    for (int k = i - 1; k < i + 2; k++) {
                        for (int m = j - 1; m < j + 2; m++) {
                            iLength[c] = k;
                            jLength[c] = m;
                        }
                    }

                    // 写入像素值
                    changePixelOnPixels(pixels, i, j, getBoxBlurPixel(pixels, iLength, jLength));
                }
            }
        }

        return new PixImage(pixels, sourceImage.getWidth(), sourceImage.getHeight());
    }


    /**
     * 得到box blur 模糊的像素
     * @param pixels 像素而为数组
     * @param iLength i坐标数组
     * @param jLength j坐标数组
     * @return 模糊过的像素值
     */
    private static Pixel getBoxBlurPixel(Pixel[][] pixels, int[] iLength, int[] jLength) throws TiffChangeRunningException {
        int r = 0;
        int b = 0;
        int g = 0;

        // 处理长度不平衡异常
        if (iLength.length != jLength.length) {
            throw new TiffChangeRunningException( "取平均值时ij长度不平衡", null);
        }


        // 计算平均
        for (int i = 0; i < iLength.length; i++) {
            r += pixels[iLength[i]][jLength[i]].getRed();
            g += pixels[iLength[i]][jLength[i]].getGreen();
            b += pixels[iLength[i]][jLength[i]].getBlue();

        }

        return new Pixel((short) (r / jLength.length), (short) (g / jLength.length), (short) (b / jLength.length));
    }

    /**
     * 更改特定位置的像素值
     * @param pixels 源图片像素数组
     * @param i y
     * @param j x
     * @param pixel 带改变的像素值
     */
    private static void changePixelOnPixels(Pixel[][] pixels, int i, int j, Pixel pixel) {
        pixels[i][j].changeToThisPixel(pixel);
    }

}
