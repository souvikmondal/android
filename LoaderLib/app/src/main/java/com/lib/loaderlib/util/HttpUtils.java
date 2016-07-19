package com.lib.loaderlib.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by souvik on 7/9/2016.
 */
public final class HttpUtils {

    public static String readStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine = null;
        while ((inputLine = in.readLine()) != null)
            builder.append(inputLine);
        try {
            in.close();
        } catch (IOException e) {
        }

        return builder.toString();
    }

}
