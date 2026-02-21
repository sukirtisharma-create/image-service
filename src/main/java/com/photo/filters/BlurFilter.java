package com.photo.filters;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Map;

public class BlurFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage input, Map<String, String> params) {
        float[] kernel = {
            1f/9f, 1f/9f, 1f/9f,
            1f/9f, 1f/9f, 1f/9f,
            1f/9f, 1f/9f, 1f/9f
        };

        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
        return op.filter(input, null);
    }
}