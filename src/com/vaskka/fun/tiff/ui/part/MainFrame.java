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

    private JButton buttonBoxBlur;

    private JButton buttonSobel;


    public MainFrame(String title) {
        super(title);

        initView();
        initAction();

        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private void initView() {
        Container container = getContentPane();



        JLabel mainTitle = new JLabel("TIFF SHOW");
        mainTitle.setSize(300, 300);
        mainTitle.setFont(new Font("Dialog", Font.ITALIC, 35));
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        buttonBoxBlur = new JButton("Box Blur");
        buttonBoxBlur.setPreferredSize(new Dimension(500, 50));
        buttonSobel = new JButton("Sobel");
        buttonSobel .setPreferredSize(new Dimension(500, 50));


        for (int i = 0; i < 5; i++) {
            var space = new  JLabel(" ");
            container.add(space);
        }

        container.add(mainTitle);

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
                try {
                    ShowFrame showFrame = new ShowFrame("Box Blur", MainFrame.this, path, ShowFrame.ShowType.BoxBlur);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件读写错误", JOptionPane.ERROR_MESSAGE);

                } catch (TiffChangeRunningException | TIFFMatrixException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                }

            }
            else {
                JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonSobel.addActionListener(e -> {
            String path = chooseImageFile();

            if (path != null) {
                try {
                    ShowFrame showFrame = new ShowFrame("Sobel", MainFrame.this, path, ShowFrame.ShowType.Sobel);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件读写错误", JOptionPane.ERROR_MESSAGE);

                } catch (TiffChangeRunningException | TIFFMatrixException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "出错啦", e1.getMessage(), JOptionPane.ERROR_MESSAGE);

                }


            }
            else {
                JOptionPane.showMessageDialog(MainFrame.this, "出错啦", "文件不存在", JOptionPane.ERROR_MESSAGE);
            }
        });


    }

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
