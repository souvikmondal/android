package com.lib.sampleimagelist.widget;

import android.graphics.drawable.BitmapDrawable;

import com.lib.sampleimagelist.ImageListApp;
import com.lib.sampleimagelist.utils.ImageLoaderCallback;

import java.lang.ref.WeakReference;

public class DownloadedDrawable extends BitmapDrawable {
    private WeakReference<ImageLoaderCallback> imageLoaderCallbackWeakReference;


    public DownloadedDrawable(ImageLoaderCallback callback) {
        super(ImageListApp.NO_IMAGE);
        imageLoaderCallbackWeakReference =
                new WeakReference<ImageLoaderCallback>(callback);
    }

    public ImageLoaderCallback getImageLoaderCallback() {
        return imageLoaderCallbackWeakReference.get();
    }




}