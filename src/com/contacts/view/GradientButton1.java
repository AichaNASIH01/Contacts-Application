package com.contacts.view;

import javax.swing.*;
import java.awt.*;

public class GradientButton1 extends JButton {

    private Color startColor;
    private Color endColor;

    public GradientButton1(String text, Color startColor, Color endColor) {
        super(text);
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false); // Disable default button background
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        // Draw the gradient background
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        super.paintComponent(g);
    }
}
