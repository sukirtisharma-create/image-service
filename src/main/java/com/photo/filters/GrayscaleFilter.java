package com.photo.filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class GrayscaleFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage input, Map<String, String> params) {
        BufferedImage result =
                new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                Color c = new Color(input.getRGB(x, y));
                int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                result.setRGB(x, y, new Color(gray, gray, gray).getRGB());
            }
        }
        return result;
    }
}