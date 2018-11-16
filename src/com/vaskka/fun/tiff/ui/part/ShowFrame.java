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

    private JFrame context;

   private BufferedImage sourceImage;

   private String sourcePath;


   private BufferedImage resultImage;

   private int count = 1;

   private JButton changeCount;

   private ImagePanel source;

   private ImagePanel result;

    private JTextField countTextField;

   private ShowType type;

   public enum ShowType {
       BoxBlur,
       Sobel
   }

    public ShowFrame(String title, JFrame context, String sourcePath, ShowType type) throws IOException, TiffChangeRunningException, TIFFMatrixException {
        super(title);

        this.context = context;
        this.sourcePath = sourcePath;
        this.sourceImage = ImageIO.read(new FileInputStream(sourcePath));

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
        changeCount = new JButton("Let's Change the Count!");
        changeCount.setPreferredSize(new Dimension(300, 50));

        countTextField = new JTextField("", 20);
        countTextField.setPreferredSize(new Dimension(300, 50));

        Container container = this.getContentPane();

        source = new ImagePanel();
        result = new ImagePanel();

        source.setImage(sourceImage);
        result.setImage(resultImage);


        JLabel before = new JLabel("变换前:");
        JLabel after = new JLabel("变换后:");
        before.setFont(new Font("Dialog", Font.ITALIC, 15));
        after.setFont(new Font("Dialog", Font.ITALIC, 15));

        container.add(before);
        container.add(source);
        container.add(new JLabel(" "));
        container.add(after);
        container.add(result);

        for (int i = 0; i < 3; i++) {
            container.add(new JLabel(" "));
        }
        container.add(countTextField);
        container.add(changeCount);
        container.setLayout(new VerticalFlowLayout());

    }

    private void initAction() {
        changeCount.addActionListener(e->{
            String countString = countTextField.getText();
            countTextField.setText("");

            try {
                count = Integer.parseInt(countString);

                switch (type) {
                    case BoxBlur:
                        resultImage = ImageTool.getBoxBlurImage(sourcePath, count);
                        break;
                    case Sobel:
                        resultImage = ImageTool.getSobelImage(sourcePath, count);
                        break;
                }

                result.setImage(resultImage);
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
