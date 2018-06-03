package com.honghiepit.gui;

import com.honghiepit.Constants;

import javax.swing.*;

public class MyFrame extends JFrame {
    private MyPanel myPanel;

    public MyFrame() {
        setTitle(Constants.NAME);
        setSize(Constants.WIDTH_SIZE_FRAME, Constants.HEIGHT_SIZE_FRAME);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        myPanel = new MyPanel();
        add(myPanel);
    }

}
