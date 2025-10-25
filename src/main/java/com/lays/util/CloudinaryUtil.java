package com.lays.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "do7dnw8rl");
            config.put("api_key", "998982779943915");
            config.put("api_secret", "LCv3zFhfjYqdCyeZRSxTk0q3ALU");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }
}
