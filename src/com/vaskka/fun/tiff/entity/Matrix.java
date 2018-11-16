package com.vaskka.fun.tiff.entity;

import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TIffChangeBaseException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;

/**
 * @program: TiffFun
 * @description: Matrix 矩阵
 * @author: Vaskka
 * @create: 2018/11/14 5:57 PM
 **/

public class Matrix {

    /**
     * 矩阵宽度
     */
    private int width;

    /**
     * 矩阵高度
     */
    private int height;


    /**
     * 矩阵内部而为数组
     */
    private  long[][] innerArray;

    /**
     * 用而为数组构造矩阵
     * @param innerArray
     */
    public Matrix(long[][] innerArray) {
        this.innerArray = innerArray;
        this.width = innerArray[0].length;
        this.height = innerArray.length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long[][] getInnerArray() {
        return innerArray;
    }

    /**
     * 计算两个矩阵的卷积
     * @param m1 矩阵1
     * @param m2 矩阵2
     * @return 卷积值
     * @throws TIFFMatrixException 矩阵运算异常
     */
    public static long getConvolution(Matrix m1, Matrix m2) throws TIFFMatrixException {
        if (m1.width != m2.width || m1.height != m2.height) {
            throw new TIFFMatrixException("卷积计算行列不相等");
        }


        long result = 0;
        for (int i = 0; i < m1.width; i++) {
            for (int j = 0; j < m2.height; j++) {
                result += (m1.innerArray[i][j] * m2.innerArray[i][j]);
            }
        }

        return result;
    }

    /**
     * 得到x方向梯度
     * @return 梯度值
     * @throws TIFFMatrixException 矩阵运算异常
     */
    public long getXGradient() throws TIFFMatrixException {
        var X_GRADIENT_CONST_INNER = new long[3][3];
        X_GRADIENT_CONST_INNER[0][0] = 1;
        X_GRADIENT_CONST_INNER[0][1] = 0;
        X_GRADIENT_CONST_INNER[0][2] = -1;
        X_GRADIENT_CONST_INNER[1][0] = 2;
        X_GRADIENT_CONST_INNER[1][1] = 0;
        X_GRADIENT_CONST_INNER[1][2] = -2;
        X_GRADIENT_CONST_INNER[2][0] = 1;
        X_GRADIENT_CONST_INNER[2][1] = 0;
        X_GRADIENT_CONST_INNER[2][2] = -1;
        var X_GRADIENT_CONST = new Matrix(X_GRADIENT_CONST_INNER);

        return getConvolution(this, X_GRADIENT_CONST);

    }


    /**
     * 得到Y方向梯度
     * @return 梯度值
     * @throws TIFFMatrixException 矩阵运算异常
     */
    public long getYGradient() throws TIFFMatrixException {
        var Y_GRADIENT_CONST_INNER = new long[3][3];
        Y_GRADIENT_CONST_INNER[0][0] = 1;
        Y_GRADIENT_CONST_INNER[0][1] = 2;
        Y_GRADIENT_CONST_INNER[0][2] = 1;
        Y_GRADIENT_CONST_INNER[1][0] = 0;
        Y_GRADIENT_CONST_INNER[1][1] = 0;
        Y_GRADIENT_CONST_INNER[1][2] = 0;
        Y_GRADIENT_CONST_INNER[2][0] = -1;
        Y_GRADIENT_CONST_INNER[2][1] = -2;
        Y_GRADIENT_CONST_INNER[2][2] = -1;
        var Y_GRADIENT_CONST = new Matrix(Y_GRADIENT_CONST_INNER);

        return getConvolution(this, Y_GRADIENT_CONST);

    }


}
