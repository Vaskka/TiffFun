package com.vaskka.fun.tiff.exceptions;

import com.vaskka.fun.tiff.entity.PixImage;

/**
 * @program: TiffFun
 * @description: TiffChangeRunningException 运行异常
 * @author: Vaskka
 * @create: 2018/11/7 6:06 PM
 **/

public class TiffChangeRunningException extends TIffChangeBaseException {
    public TiffChangeRunningException(PixImage image) {
        super(image);
    }

    public TiffChangeRunningException(String message, PixImage image) {
        super(message, image);
    }

    public TiffChangeRunningException(String message, Throwable cause, PixImage image) {
        super(message, cause, image);
    }

    public TiffChangeRunningException(Throwable cause, PixImage image) {
        super(cause, image);
    }

    public TiffChangeRunningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, PixImage image) {
        super(message, cause, enableSuppression, writableStackTrace, image);
    }
}
