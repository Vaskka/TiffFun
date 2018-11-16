package com.vaskka.fun.tiff.ui.part;

import com.vaskka.fun.tiff.exceptions.TIFFMatrixException;
import com.vaskka.fun.tiff.exceptions.TiffChangeRunningException;
import com.vaskka.fun.tiff.ui.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @program: TiffFun
 * @description: MainFrame 主界面
 * @author: Vaskka
 * @create: 2018/11/16 8:22 PM
 **/

public class MainFrame extends JFrame {

    /**
     * 主界面 展示box blur按钮
     */
    private JButton buttonBoxBlur;

    /**
     * 主界面 展示Sobel按钮
     */
    private JButton buttonSobel;


    /**
     * 构造窗体
     * @param title 标题
     */
    public MainFrame(String title) {
        super(title);

        initView();
        initAction();

        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }


    /**
     * 初始化视图
     */
    private void initView() {
        Container container = getContentPane();

        // 标题样式
        JLabel mainTitle = new JLabel("TIFF SHOW");
        mainTitle.setSize(300, 300);
        mainTitle.setFont(new Font("Dialog", Font.ITALIC, 35));
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // 按钮样式
        buttonBoxBlur = new JButton("Box Blur");
        buttonBoxBlur.setPreferredSize(new Dimension(500, 50));
        buttonSobel = new JButton("Sobel");
        buttonSobel .setPreferredSize(new Dimension(500, 50));

        // 上方留白
        for (int i = 0; i < 5; i++) {
            var space = new  JLabel(" ");
            container.add(space);
        }

        container.add(mainTitle);

        // 中部留白
        for (int i = 0; i < 6; i++) {
            var space = new  JLabel(" ");
            container.add(space);
        }

        container.add(buttonBoxBlur);
        container.add(new JLabel(" "));
        container.add(new JLabel(" "));
        container.add(buttonSobel);
        container.setLayout(new VerticalFlowLayout());

    }

    private void initAction() {
        buttonBoxBlur.addActionListener(e -> {
            String path = chooseImageFile();

            if (path != null) {
                // 路径有效
                try {
                    ShowFrame showFrame = new ShowFrame("Box Blur", MainFrame.this, path, ShowFrame.ShowType.BoxBlur);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件读写错误", JOptionPane.ERROR_MESSAGE);

                } catch (TiffChangeRunningException | TIFFMatrixException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                }

            }
            else {
                // 路径无效
                JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonSobel.addActionListener(e -> {
            String path = chooseImageFile();

            if (path != null) {
                // 路径有效
                try {
                    ShowFrame showFrame = new ShowFrame("Sobel", MainFrame.this, path, ShowFrame.ShowType.Sobel);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件读写错误", JOptionPane.ERROR_MESSAGE);

                } catch (TiffChangeRunningException | TIFFMatrixException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                }


            }
            else {
                // 路径无效
                JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);
            }
        });


    }

    /**
     * 文件打开窗体
     * @return 选择文件的绝对路径
     */
    private String chooseImageFile() {

        var choose = new JFileChooser();
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int value = choose.showOpenDialog(MainFrame.this);

        if (value == JFileChooser.APPROVE_OPTION) {
            // 文件正常打开
            File file = choose.getSelectedFile();

            return file.getAbsolutePath();

        }
        else {
            JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件打开失败，请重试", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


}
