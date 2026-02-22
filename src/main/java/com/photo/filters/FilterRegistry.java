package com.photo.filters;

import java.util.HashMap;
import java.util.Map;

public class FilterRegistry {

    private static final Map<String, ImageFilter> filters = new HashMap<>();

    static {
        filters.put("grayscale", new GrayscaleFilter());
        filters.put("resize", new ResizeFilter());
        filters.put("blur", new BlurFilter());
        filters.put("rotate", new RotateFilter());
    }

    public static ImageFilter get(String name) {
        return filters.get(name);
    }
}