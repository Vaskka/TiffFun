package com.vaskka.fun.tiff.tool;

import com.vaskka.fun.tiff.entity.Matrix;
import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;
import com.vaskka.fun.tiff.entity.PointSet;
import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.utils.ImageUtility;
import com.vaskka.fun.tiff.utils.UsualUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.vaskka.fun.tiff.utils.ImageUtility.energy2gray;
import static com.vaskka.fun.tiff.utils.UsualUtil.l;
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
     * @param count 迭代次数
     * @return BufferedImage
     * @throws TiffChangeRunningException 运行异常
     * @throws IOException 文件读写异常
     */
    public static BufferedImage getBoxBlurImage(String imagePath, String outputPath, int count) throws IOException {
        innerBoxBlurAndWriteInFile(imagePath, outputPath, count);

        return ImageIO.read(new File(outputPath));
    }

    /**
     * 得到输入输出文件路径和迭代次数 返回储存好的boxblur图像数据
     * @param sourceImagePath 源文件路径
     * @param outputImagePath 目标文件路径
     * @param count 迭代次数
     */
    private static void innerBoxBlurAndWriteInFile(String sourceImagePath, String outputImagePath, int count)  {

        var image = ImageUtility.readTIFFImageFromFile(sourceImagePath);

        for (int i = 0; i < count; i++) {
            image = fromSourceImageGetBoxBlurImage(image);
        }

        ImageUtility.writeTIFFImageToFile(image, outputImagePath);

    }


    /**
     * 从源图像得到box blur后图像
     * @param sourceImage 源图像
     * @return PixImage
     */
    private static PixImage fromSourceImageGetBoxBlurImage(PixImage sourceImage) {
        var inner = sourceImage.getPixels();
        var width = sourceImage.getWidth();
        var height = sourceImage.getHeight();
        var newInner = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                var targetR = 0;
                var targetG = 0;
                var targetB = 0;

                var count = 0;
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        if (ii < 0 || ii >= width || jj < 0 || jj >= height) {
                            continue;
                        }
                        targetR += inner[ii][jj].getRed();
                        targetG += inner[ii][jj].getGreen();
                        targetB += inner[ii][jj].getBlue();
                        count ++;
                    }
                }



                var r = (short)(targetR / count);
                var g = (short)(targetG / count);
                var b = (short)(targetB / count);
                newInner[i][j] = new Pixel( r, g, b);
            }
        }

        return new PixImage(newInner, sourceImage.getWidth(), sourceImage.getHeight());
    }


    public static BufferedImage getSobelImage(String imagePath, int count) throws IOException, TIFFMatrixException {
        String outputPath = UsualUtil.getFileDirectoryPath(imagePath) + separator + UsualUtil.changeFileToBoxBlurOutputFileName(UsualUtil.getFileName(imagePath));

        innerGetSobelImage(imagePath, outputPath, count);

        return ImageIO.read(new File(outputPath));
    }


    public static BufferedImage getSobelImage(String imagePath, String outputPath, int count) throws IOException, TIFFMatrixException {
        innerGetSobelImage(imagePath, outputPath, count);

        return ImageIO.read(new File(outputPath));
    }

    private static void innerGetSobelImage(String sourceImagePath, String outputImagePath, int count) throws TIFFMatrixException {
        var image = ImageUtility.readTIFFImageFromFile(sourceImagePath);

        for (int i = 0; i < count; i++) {
            image = fromSourceImageGetSobelImage(image);
        }

        ImageUtility.writeTIFFImageToFile(image, outputImagePath);
    }

    private static PixImage fromSourceImageGetSobelImage(PixImage sourceImage) throws TIFFMatrixException {

        var newInner = new Pixel[sourceImage.getWidth()][sourceImage.getHeight()];
        var inner = sourceImage.getPixels();

        var width = sourceImage.getWidth();
        var height = sourceImage.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                long[][] innerR = new long[3][3];
                long[][] innerG = new long[3][3];
                long[][] innerB = new long[3][3];

                for (int ii = i - 1, countI = 0; ii <= i + 1; ii++, countI++) {
                    for (int jj =j - 1, countJ = 0; jj <= j + 1; jj++, countJ++) {

                        int realI = ii;
                        int realJ = jj;

                        if (ii < 0) {
                            realI++;
                        }
                        if (ii >= width) {
                            realI--;
                        }
                        if (jj < 0) {
                            realJ++;
                        }
                        if (jj >= height) {
                            realJ--;
                        }

                        // 一般情况
                        innerR[countI][countJ] = inner[realI][realJ].getRed();
                        innerG[countI][countJ] = inner[realI][realJ].getGreen();
                        innerB[countI][countJ] = inner[realI][realJ].getBlue();


                        newInner[i][j] = getGrayPixel(innerR, innerG, innerB);
                    }
                }
            }
        }


        return new PixImage(newInner, width, height);


    }




    /**
     * RGB 3x3 矩阵得到灰度像素
     * @param innerR R分量矩阵
     * @param innerG G分量矩阵
     * @param innerB B分量矩阵
     * @return 目标像素
     * @throws TIFFMatrixException 矩阵运算异常
     */
    private static Pixel getGrayPixel(long[][] innerR, long[][] innerG, long[][] innerB) throws TIFFMatrixException {
        long rGx = (new Matrix(innerR)).getXGradient();
        long rGy = (new Matrix(innerR)).getYGradient();

        long gGx = (new Matrix(innerG)).getXGradient();
        long gGy = (new Matrix(innerG)).getYGradient();

        long bGx = (new Matrix(innerB)).getXGradient();
        long bGy = (new Matrix(innerB)).getYGradient();

        long energy = (long) (Math.pow(rGx, 2) +
                Math.pow(rGy, 2) +
                Math.pow(gGx, 2) +
                Math.pow(gGy, 2) +
                Math.pow(bGx, 2) +
                Math.pow(bGy, 2));
        short gray = energy2gray(energy);

        return new Pixel(gray, gray, gray);
    }


}
