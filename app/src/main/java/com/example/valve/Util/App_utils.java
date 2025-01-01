package com.example.valve.Util;

import static java.io.File.separatorChar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class App_utils {


    public static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    public static String removePrefix(String fileName) {

        if (fileName != null && fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return fileName;
    }


}
