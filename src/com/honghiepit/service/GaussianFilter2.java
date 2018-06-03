package com.honghiepit.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GaussianFilter2 {
    private BufferedImage img;
    private String pathFile;

    public BufferedImage getImg() {
        return img;
    }

    public GaussianFilter2(String pathFile) {
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

    public void blur(double sigma, int kernelsize) {
        double[][] kernel = createKernel(sigma, kernelsize);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {

                int kernelhalf = (kernelsize - 1) / 2;

                double red = 0;
                double green = 0;
                double blue = 0;
                double alpha = 0;

                for (int k = 0; k < kernel.length; k++) {
                    for (int l = 0; l < kernel[k].length; l++) {
                        if (i - kernelhalf + k < 0 || i - kernelhalf + k > img.getWidth() - 1
                                || j - kernelhalf + l < 0 || j - kernelhalf + l > img.getHeight() - 1) {

                            continue;
                        }
                        Color c =
                                new Color
                                        (img.getRGB(i - kernelhalf + k,
                                                j - kernelhalf + l));


                        double _kernel = kernel[k][l];


                        red += c.getRed() * _kernel;
                        green += c.getGreen() * _kernel;
                        blue += c.getBlue() * _kernel;
                        alpha += c.getAlpha() * _kernel;
                    }
                }

                double value = (red + green + blue + alpha) / 4;
                //img.setRGB(i, j, (int) value);
                img.setRGB(i, j, new Color((int) red, (int) green, (int) blue, (int) alpha).getRGB());
                //System.out.println(new Color((int) red, (int) green, (int) blue).getRGB());

            }

        }
    }

    public static double[][] createKernel(double sigma, int kernelsize) {

        double[][] kernel = new double[kernelsize][kernelsize];
        float sum = 0;
        for (int i = 0; i < kernel.length; i++) {
            double x = i - (kernelsize - 1) / 2;

            for (int j = 0; j < kernel[i].length; j++) {
                double y = j - (kernelsize - 1) / 2;
                kernel[i][j] = 1 / (2 * Math.PI * sigma) * Math.exp(-(x * x + y * y) / (2 * sigma * sigma));

                sum += kernel[i][j];
            }
        }


        for (int i = 0; i < kernelsize; i++) {
            for (int j = 0; j < kernelsize; j++) {
                kernel[i][j] /= sum;
                System.out.print(kernel[i][j] + "\t");
            }
            System.out.println("\n");
        }
        return kernel;
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
