package com.photo.filters;

import java.awt.image.BufferedImage;
import java.util.Map;

public class ResizeFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage input, Map<String, String> params) {
        int width = Integer.parseInt(params.getOrDefault("width", "300"));
        int height = Integer.parseInt(params.getOrDefault("height", "300"));

        BufferedImage resized =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        resized.getGraphics().drawImage(input, 0, 0, width, height, null);
        return resized;
    }
}