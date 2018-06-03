package com.honghiepit.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private BufferedImage image;
    public void setImage(BufferedImage image){
        this.image=image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(image!=null) {
            int widthImg=image.getWidth();
            int heightImg=image.getHeight();

            if(widthImg>heightImg){
                if(widthImg>650) {
                    g2d.drawImage(image, 0, 0, 650, (650*heightImg)/widthImg, null);
                }else {
                    g2d.drawImage(image, 0, 0, widthImg, heightImg, null);
                }
            }else {
                if(heightImg>600) {
                    g2d.drawImage(image, 0, 0, (600*widthImg)/heightImg, 600, null);
                }else {
                    g2d.drawImage(image, 0, 0, widthImg, heightImg, null);
                }
            }

        }
    }

}
