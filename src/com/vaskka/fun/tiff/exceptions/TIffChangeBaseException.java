package com.vaskka.fun.tiff.exceptions;

import com.vaskka.fun.tiff.entity.PixImage;

/**
 * @program: TiffFun
 * @description: TIffChangeBaseException 基本异常
 * @author: Vaskka
 * @create: 2018/11/7 6:05 PM
 **/

public class TIffChangeBaseException extends Exception {
    private PixImage image;

    public TIffChangeBaseException(PixImage image) {
        this.image = image;
    }

    public TIffChangeBaseException(String message, PixImage image) {
        super(message);
        this.image = image;
    }

    public TIffChangeBaseException(String message, Throwable cause, PixImage image) {
        super(message, cause);
        this.image = image;
    }

    public TIffChangeBaseException(Throwable cause, PixImage image) {
        super(cause);
        this.image = image;
    }

    public TIffChangeBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, PixImage image) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.image = image;
    }

    public PixImage getImage() {
        return image;
    }

    public void setImage(PixImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "TIffChangeBaseException{" +
                "image=" + image + "," +
                "message=" + getMessage() +
                '}';
    }
}
