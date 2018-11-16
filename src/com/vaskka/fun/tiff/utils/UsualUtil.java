package com.vaskka.fun.tiff.utils;


import java.io.File;

/**
 * @program: TiffFun
 * @description: UsualUtil 工具类
 * @author: Vaskka
 * @create: 2018/11/7 5:21 PM
 **/

public class UsualUtil {

    public static void l(Object o) {
        System.out.println(o);

    }


    /**
     * 得到文件所在文件夹
     * @param sourcePath 源文件路径
     * @return 文件夹路径
     */
    public static String getFileDirectoryPath(String sourcePath) {
        var file = new File(sourcePath);
        return file.getParent();
    }

    /**
     * 路径获取文件名
     * @param sourcePath 源文件路径
     * @return 文件路径
     */
    public static String getFileName(String sourcePath) {
        var file = new File(sourcePath);
        return file.getName();
    }

    /**
     * 为文件名结尾添加固定字符串
     * @param fileName 带修改的文件名
     * @return String 修改后的文件名
     */
    public static String changeFileToBoxBlurOutputFileName(String fileName) {
        String[] sList = fileName.split("\\.");
        String format = sList[sList.length - 1];

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sList.length - 1; i++) {
            result.append(sList[i]);
        }
        result.append(Const.BOX_BLUR_OUTPUT_IMAGE_NAME_END);
        result.append(".");
        result.append(format);

        return result.toString();
    }
}
