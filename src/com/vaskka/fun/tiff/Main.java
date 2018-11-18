package com.vaskka.fun.tiff;


import com.vaskka.fun.tiff.ui.part.MainFrame;

import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

public class Main {

    private static void go() {
        invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            MainFrame f = new MainFrame("TIFF Magic!");
        });
    }


    public static void main(String[] args) {
	// write your code here
        go();
    }
}
