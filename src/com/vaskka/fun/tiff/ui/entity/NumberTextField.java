package com.vaskka.fun.tiff.ui.entity;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 * @program: TiffFun
 * @description: NumberTextField 正数输入文档模型
 * @author: Vaskka
 * @create: 2018/11/16 9:18 PM
 **/

public class NumberTextField extends PlainDocument {

    public NumberTextField() {
        super();
    }


    /**
     * 限制数字输入格式
     * @param offset 偏移量
     * @param str 元字符串
     * @param attr AttributeSet
     * @throws javax.swing.text.BadLocationException
     */
    @Override
    public void insertString (int offset, String str, AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) {
            return;
        }


        char[] s = str.toCharArray();
        int length = 0; // 过滤非数字

        for (int i = 0; i < s.length; i++) {
            if ((s[i] >= '0') && (s[i] <= '9')) {
                s[length++] = s[i];
            }
        }


        // 插入内容
        super.insertString(offset, new String(s, 0, length), attr);

    }
}
