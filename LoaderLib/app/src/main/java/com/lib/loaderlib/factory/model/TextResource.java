package com.lib.loaderlib.factory.model;

import com.lib.loaderlib.util.HttpUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by souvik on 7/10/2016.
 */
public class TextResource implements RemoteResource<String> {

    private String mResource;

    @Override
    public void prepare(InputStream inputStream) throws IOException {
        try {
            mResource = HttpUtils.readStream(inputStream);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                //ignore
            }
        }
    }

    @Override
    public String getResource() {
        return mResource;
    }

    @Override
    public int size() {
        if (mResource == null)
            return -1;
        return mResource.getBytes().length / 1024;
    }
}
