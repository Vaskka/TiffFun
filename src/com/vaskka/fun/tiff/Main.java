package com.vaskka.fun.tiff;

import com.vaskka.fun.tiff.entity.Matrix;
import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;
import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.tool.ImageTool;
import com.vaskka.fun.tiff.utils.ImageUtility;
import com.vaskka.fun.tiff.utils.UsualUtil;

import java.io.IOException;

import static com.vaskka.fun.tiff.utils.UsualUtil.l;

public class Main {

    public static void main(String[] args) throws TIFFMatrixException {
	// write your code here


        // l(UsualUtil.getFileDirectoryPath("/Users/vaskka/Desktop/a.tiff"));
        var origin = "/Users/vaskka/Desktop/";
        var output = "_output.tiff";
        var source = ".tiff";
        try {
            for (int  i = 0; i < 7; i++) {
                ImageTool.getSobelImage(origin + String.valueOf(i) + source, origin + String.valueOf(i) + output, 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TIFFMatrixException e) {
            e.printStackTrace();
        }
//        long[][] i = new long[3][3];
//
//        i[0][0] = 2;
//        i[0][1] = 3;
//        i[0][2] = 4;
//        i[1][0] = 5;
//        i[1][1] = 6;
//        i[1][2] = 7;
//        i[2][0] = 8;
//        i[2][1] = 9;
//        i[2][2] = 20;
//
//
//
//        Matrix m = new Matrix(i);
//
//        l(m.getXGradient());
//        l(m.getYGradient());
    }
}
