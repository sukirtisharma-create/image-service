package com.photo.filters;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface ImageFilter {
    BufferedImage apply(BufferedImage input, Map<String, String> params);
}