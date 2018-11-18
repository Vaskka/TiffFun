package com.vaskka.fun.tiff.ui.part;

import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.tool.ImageTool;
import com.vaskka.fun.tiff.ui.layout.VerticalFlowLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @program: TiffFun
 * @description: ShowFrame 图片展示Frame
 * @author: Vaskka
 * @create: 2018/11/16 9:08 PM
 **/

public class ShowFrame extends JFrame {

    /**
     * 主窗口的引用
     */
    private JFrame context;

    /**
     * 图片源BufferedImage
     */
     private BufferedImage sourceImage;

    /**
     * 图片源绝对路径
     */
    private String sourcePath;

    /**
     * 变换后图片BufferedImage
     */
    private BufferedImage resultImage;

    /**
     * 迭代次数，初始默认1
     */
    private int count = 1;

    /**
     * 更改迭代次数
     */
     private JButton changeCount;

    /**
     * 原图片展示窗体
     */
    private ImagePanel source;

    /**
     * 变换后图片展示窗体
     */
     private ImagePanel result;

    /**
     * 迭代次数输入框
     */
    private JTextField countTextField;

    /**
     * 变换种类
     */
     private ShowType type;

    /**
     * 变换种类枚举
     */
    public enum ShowType {
        BoxBlur,
        Sobel
     }

    /**
     * 初始化变换窗体
     * @param title 标题
     * @param context 上下文（主窗口引用）
     * @param sourcePath 原图片路径
     * @param type 种类
     * @throws IOException 文件读写异常 403
     * @throws TiffChangeRunningException 变换发生异常 500
     * @throws TIFFMatrixException 矩阵计算错误 500
     */
     public ShowFrame(String title, JFrame context, String sourcePath, ShowType type) throws IOException, TiffChangeRunningException, TIFFMatrixException {
        super(title);

        this.context = context;
        this.sourcePath = sourcePath;
        this.sourceImage = ImageIO.read(new FileInputStream(sourcePath));

        // 讨论种类
        switch (type) {
            case BoxBlur:
                resultImage = ImageTool.getBoxBlurImage(sourcePath, count);
                break;
            case Sobel:
                resultImage = ImageTool.getSobelImage(sourcePath, count);
                break;
        }

        this.type = type;

        initView();
        initAction();

        this.setSize(500, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        context.setVisible(false);

     }


    private void initView() {
         // 按钮样式
        changeCount = new JButton("Let's Change the Count!");
        changeCount.setPreferredSize(new Dimension(300, 50));

        // 输入框样式
        countTextField = new JTextField("", 20);
        countTextField.setPreferredSize(new Dimension(300, 50));

        Container container = this.getContentPane();

        source = new ImagePanel();
        result = new ImagePanel();

        source.setImage(sourceImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        result.setImage(resultImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));


        JLabel before = new JLabel("变换前:");
        JLabel after = new JLabel("变换后:");
        before.setFont(new Font("Dialog", Font.ITALIC, 15));
        after.setFont(new Font("Dialog", Font.ITALIC, 15));

        container.add(before);
        container.add(source);
        container.add(new JLabel(" "));
        container.add(after);
        container.add(result);

        // 占位
        for (int i = 0; i < 3; i++) {
            container.add(new JLabel(" "));
        }
        container.add(countTextField);
        container.add(changeCount);
        container.setLayout(new VerticalFlowLayout());

    }

    private void initAction() {
        changeCount.addActionListener(e->{
            // 晴空输入框
            String countString = countTextField.getText();
            countTextField.setText("");

            try {
                count = Integer.parseInt(countString);

                // 讨论不同种类变换
                switch (type) {
                    case BoxBlur:
                        resultImage = ImageTool.getBoxBlurImage(sourcePath, count);
                        break;
                    case Sobel:
                        resultImage = ImageTool.getSobelImage(sourcePath, count);
                        break;
                }

                result.setImage(resultImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                result.repaint();
            }
            catch (NumberFormatException except) {
                JOptionPane.showMessageDialog(ShowFrame.this, "出错啦", "输入的迭代次数不正确，请重试", JOptionPane.ERROR_MESSAGE);

            } catch (IOException | TiffChangeRunningException | TIFFMatrixException e1) {
                JOptionPane.showMessageDialog(ShowFrame.this, "出错啦", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

            }

        });
    }
}
