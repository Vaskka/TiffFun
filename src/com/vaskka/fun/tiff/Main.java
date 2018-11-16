package com.vaskka.fun.tiff;

import com.vaskka.fun.tiff.entity.Matrix;
import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;
import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.tool.ImageTool;
import com.vaskka.fun.tiff.ui.part.MainFrame;
import com.vaskka.fun.tiff.utils.ImageUtility;
import com.vaskka.fun.tiff.utils.UsualUtil;

import javax.swing.*;
import java.io.IOException;

import static com.vaskka.fun.tiff.utils.UsualUtil.l;
import static javax.swing.SwingUtilities.invokeLater;

public class Main {

    private static void go() {
        invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            MainFrame f = new MainFrame("TIFF Magic!");
        });
    }


    public static void main(String[] args) throws TIFFMatrixException {
	// write your code here
        go();
    }
}
