package com.honghiepit.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SobelFilter {
    private BufferedImage img;
    private BufferedImage sobelImg;
    private String pathFile;

    private double gX[][] = new double[][]{
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private double gY[][] = new double[][]{
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    public BufferedImage getImg() {
        return img;
    }

    public BufferedImage getSobelImg() {
        return sobelImg;
    }

    public SobelFilter(String pathFile) {
        this.pathFile = pathFile;
        readImage();
    }

    public void readImage() {
        img = null;
        try {
            img = ImageIO.read(new File(pathFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void blur() {
        //sobelImg = new BufferedImage(img.getWidth(), img.getHeight(), TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth() - 2; i++) {
            for (int j = 0; j < img.getHeight() - 2; j++) {

//                double gx1 = img.getRGB(i, j) * gX[0][0];
//                double gx2 = img.getRGB(i + 1, j) * gX[1][0];
//                double gx3 = img.getRGB(i + 2, j) * gX[2][0];
//                double gx4 = img.getRGB(i, j + 1) * gX[0][1];
//                double gx5 = img.getRGB(i + 1, j + 1) * gX[1][1];
//                double gx6 = img.getRGB(i + 2, j + 1) * gX[2][1];
//                double gx7 = img.getRGB(i, j + 2) * gX[0][2];
//                double gx8 = img.getRGB(i + 1, j + 2) * gX[1][2];
//                double gx9 = img.getRGB(i + 2, j + 2) * gX[2][2];
//
//                double valueX = gx1 + gx2 + gx3 + gx4 + gx5 + gx6 + gx7 + gx8 + gx9;
//
//                double gy1 = img.getRGB(i, j) * gY[0][0];
//                double gy2 = img.getRGB(i + 1, j) * gY[1][0];
//                double gy3 = img.getRGB(i + 2, j) * gY[2][0];
//                double gy4 = img.getRGB(i, j + 1) * gY[0][1];
//                double gy5 = img.getRGB(i + 1, j + 1) * gY[1][1];
//                double gy6 = img.getRGB(i + 2, j + 1) * gY[2][1];
//                double gy7 = img.getRGB(i, j + 2) * gY[0][2];
//                double gy8 = img.getRGB(i + 1, j + 2) * gY[1][2];
//                double gy9 = img.getRGB(i + 2, j + 2) * gY[2][2];
//
//                double valueY = gy1 + gy2 + gy3 + gy4 + gy5 + gy6 + gy7 + gy8 + gy9;
//
//                double value = Math.sqrt(valueX * valueX + valueY * valueY);
//
//                img.setRGB(i,j, (int) value);

                double redX = 0;
                double greenX = 0;
                double blueX = 0;
                double alphaX = 0;

                double redY = 0;
                double greenY = 0;
                double blueY = 0;
                double alphaY = 0;

                for (int k = 0; k < gX.length; k++) {
                    for (int l = 0; l < gX[k].length; l++) {
                        Color c = new Color(img.getRGB(i + k, j + l));
                        double _gX=gX[k][l];
                        double _gY=gY[k][l];

                        redX += c.getRed() * _gX;
                        greenX += c.getGreen() * _gX;
                        blueX += c.getBlue() * _gX;
                        alphaX += c.getAlpha() * _gX;

                        redY += c.getRed() * _gY;
                        greenY += c.getRed() * _gY;
                        blueY += c.getRed() * _gY;
                        alphaY += c.getRed() * _gY;
                    }
                }
                double valueX=(redX+greenX+blueX+alphaX)/4;
                double valueY=(redY+greenY+blueY+alphaY)/4;

                System.out.println(redX);
                System.out.println(greenX);
                System.out.println(blueX);

                double value=Math.sqrt(valueX*valueX+valueY*valueY);
                int rgb= (int) value;
                System.out.println("trước khi dịch bit tại "+i+" "+j+" "+rgb);
                rgb = (rgb << 24 | rgb << 16 | rgb << 8 | rgb);
                System.out.println("sau khi dịch bit tại "+i+" "+j+" "+rgb);
                img.setRGB(i, j, rgb);
            }
        }
    }


    public void writeImage() {
        File output = new File("imgChanged.bmp");
        try {
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
