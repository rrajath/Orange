package com.rrajath.orange.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rrajath on 4/3/15.
 */
public class TextUtils {

    public static String getUrlDomain(String url) throws MalformedURLException {
        String urlDomain = new URL(url).getHost();
        if (urlDomain.startsWith("www.")) {
            urlDomain = urlDomain.substring(4);
        }
        return urlDomain;
    }
}
