package com.honghiepit.gui;

import com.honghiepit.Constants;
import com.honghiepit.service.GaussianFilter2;
import com.honghiepit.service.SobelFilter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyPanel extends JPanel {
    private ImagePanel pnOriginImage, pnBlurImage;

    private JPanel pnFuntionGaussian, pnFuntionSobel;

    private JFileChooser chooseImage;

    private JButton btnChooseImage, btnBlurImage, btnReset;

    private JRadioButton rdGaussian, rdSobel;

    private JTextField txtKernelSize, txtSigma;

    private String pathFile;


    public MyPanel() {
        setSize(Constants.WIDTH_SIZE_FRAME, Constants.HEIGHT_SIZE_FRAME);
        setLocation(0, 0);
        setLayout(null);

        initViews();
    }

    private void initViews() {
        pnOriginImage = new ImagePanel();
        pnOriginImage.setSize(Constants.WIDTH_SIZE_IMAGE_PANEL, Constants.HEIGHT_SIZE_IMAGE_PANEL);
        pnOriginImage.setLocation(Constants.LOCATION_PANEL_ORIGIN_IMAGE_X, Constants.LOCATION_PANEL_ORIGIN_IMAGE_Y);
        pnOriginImage.setBackground(Color.WHITE);
        add(pnOriginImage);

        pnBlurImage = new ImagePanel();
        pnBlurImage.setSize(Constants.WIDTH_SIZE_IMAGE_PANEL, Constants.HEIGHT_SIZE_IMAGE_PANEL);
        pnBlurImage.setLocation(Constants.LOCATION_PANEL_BLUR_IMAGE_X, Constants.LOCATION_PANEL_BLUR_IMAGE_Y);
        pnBlurImage.setBackground(Color.WHITE);
        add(pnBlurImage);

        btnChooseImage = new JButton("Chọn ảnh");
        btnChooseImage.setBounds(10, 650, 100, 30);
        add(btnChooseImage);
        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage = new JFileChooser();
                FileNameExtensionFilter loc = new FileNameExtensionFilter("Images", "png", "jpg", "bmp");
                chooseImage.setFileFilter(loc);

                int i = chooseImage.showOpenDialog(MyPanel.this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = chooseImage.getSelectedFile();
                    pathFile = file.getAbsolutePath();
                    try {
                        BufferedImage image = ImageIO.read(new File(pathFile));
                        pnOriginImage.setImage(image);
                        pnOriginImage.repaint();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        btnBlurImage = new JButton("Làm mờ");
        btnBlurImage.setBounds(400, 650, 100, 30);
        add(btnBlurImage);
        btnBlurImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pathFile == null) {
                    return;
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(rdGaussian.isSelected()==true){
                            gaussianProcess();
                        }else {
                            SobelProcessing();
                        }

                    }
                });
                thread.start();
            }
        });


        btnReset = new JButton("Reset");
        btnReset.setBounds(150, 650, 100, 30);
        add(btnReset);
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathFile = null;
                pnOriginImage.setImage(null);
                pnBlurImage.setImage(null);
                pnOriginImage.repaint();
                pnBlurImage.repaint();
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        rdGaussian = new JRadioButton("Gaussian");
        rdGaussian.setBounds(500, 640, 100, 30);
        rdGaussian.setSelected(true);
        add(rdGaussian);

        rdSobel = new JRadioButton("Sobel");
        rdSobel.setBounds(500, 660, 100, 30);
        add(rdSobel);

        buttonGroup.add(rdGaussian);
        buttonGroup.add(rdSobel);

        rdGaussian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnFuntionGaussian.setVisible(true);
                pnFuntionSobel.setVisible(false);
            }
        });
        rdSobel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnFuntionGaussian.setVisible(false);
                pnFuntionSobel.setVisible(true);
            }
        });



        pnFuntionGaussian = new JPanel();
        pnFuntionGaussian.setLayout(null);
        pnFuntionGaussian.setBounds(700, 620, 500, 200);
        pnFuntionGaussian.setBackground(Color.WHITE);


        JLabel lblKernelSize = new JLabel("Kích thước ma trận kernel");
        lblKernelSize.setBounds(10, 10, 200, 30);
        pnFuntionGaussian.add(lblKernelSize);

        JLabel lblSigma = new JLabel("Phương sai chuẩn của phân tán");
        lblSigma.setBounds(10, 40, 200, 30);
        pnFuntionGaussian.add(lblSigma);

        txtKernelSize = new JTextField();
        txtKernelSize.setBounds(300, 10, 200, 30);
        pnFuntionGaussian.add(txtKernelSize);

        txtSigma = new JTextField();
        txtSigma.setBounds(300, 40, 200, 30);
        pnFuntionGaussian.add(txtSigma);

        add(pnFuntionGaussian);


        pnFuntionSobel = new JPanel();
        pnFuntionSobel.setLayout(null);
        pnFuntionSobel.setBounds(700, 620, 500, 200);
        pnFuntionSobel.setBackground(Color.WHITE);
        add(pnFuntionSobel);
        pnFuntionSobel.setVisible(false);
    }

    private void SobelProcessing() {
        SobelFilter sobelFilter=new SobelFilter(pathFile);
        sobelFilter.blur();
        BufferedImage blurImage=sobelFilter.getImg();
        pnBlurImage.setImage(blurImage);
        pnBlurImage.repaint();
        System.out.println("done");
    }

    private void gaussianProcess() {
        GaussianFilter2 gaussianFilter = new GaussianFilter2(pathFile);
        try {
            int kernelSize = Integer.parseInt(txtKernelSize.getText());
            int sigma = Integer.parseInt(txtSigma.getText());

            gaussianFilter.blur(sigma, kernelSize);
            //gaussianFilter.writeImage();
            BufferedImage blurImage = gaussianFilter.getImg();
            pnBlurImage.setImage(blurImage);
            pnBlurImage.repaint();
            System.out.println("done");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không hợp lệ");
            return;
        }
    }
}
