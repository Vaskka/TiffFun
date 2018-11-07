package com.vaskka.fun.tiff;

import com.vaskka.fun.tiff.entity.PixImage;
import com.vaskka.fun.tiff.entity.Pixel;
import com.vaskka.fun.tiff.utils.ImageUtility;
import com.vaskka.fun.tiff.utils.UsualUtil;

import static com.vaskka.fun.tiff.utils.UsualUtil.l;

public class Main {

    public static void main(String[] args) {
	// write your code here


        // l(UsualUtil.getFileDirectoryPath("/Users/vaskka/Desktop/a.tiff"));

        var image = ImageUtility.readTIFFImageFromFile("/Users/vaskka/Desktop/woman.tiff");

        var pixels = image.getPixels();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                l(pixels[x][y].toString());
            }
        }

    }
}
