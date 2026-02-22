package com.photo.filters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class RotateFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage input, Map<String, String> params) {

        int angle = Integer.parseInt(params.getOrDefault("angle", "90"));
        double radians = Math.toRadians(angle);

        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage rotated =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = rotated.createGraphics();
        g2d.rotate(radians, width / 2.0, height / 2.0);
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();

        return rotated;
    }
}