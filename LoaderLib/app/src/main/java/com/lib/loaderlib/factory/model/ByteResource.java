package com.lib.loaderlib.factory.model;

import com.lib.loaderlib.util.HttpUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by souvik on 7/12/2016.
 */
public class ByteResource implements RemoteResource<byte[]> {

    private byte[] mByteData;

    @Override
    public void prepare(InputStream inputStream) throws IOException {
        mByteData = HttpUtils.readStream(inputStream).getBytes();
    }

    @Override
    public byte[] getResource() {
        return mByteData;
    }

    @Override
    public int size() {
        return mByteData.length;
    }
}
