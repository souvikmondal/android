package com.lib.loaderlib.factory.model;

import android.graphics.Bitmap;


import com.lib.loaderlib.util.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by souvik on 7/10/2016.
 */
public class ImageResource implements RemoteResource<Bitmap> {

    private Bitmap mResource;
    private int mReqWidth, mReqHeight;

    public ImageResource(int width, int height) {
        mReqWidth = width;
        mReqHeight = height;
    }

    @Override
    public void prepare(InputStream inputStream) {
        if (mReqWidth > 0 && mReqHeight > 0) {
            this.mResource = BitmapUtils.decodeStreamToBitmap(inputStream, mReqWidth, mReqHeight);
        } else {
            this.mResource = BitmapUtils.decodeStreamToBitmap(inputStream);
        }

        try {
            inputStream.close();
        } catch (IOException ex) {
            //ignore
        }
    }

    @Override
    public Bitmap getResource() {
        return mResource;
    }

    @Override
    public int size() {
        if (mResource == null)
            return -1;
        return mResource.getByteCount() / 1024;
    }
}
