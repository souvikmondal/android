package com.lib.sampleimagelist.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lib.loaderlib.LoaderCallback;
import com.lib.loaderlib.factory.model.RemoteResource;
import com.lib.sampleimagelist.widget.DownloadedDrawable;

import java.lang.ref.WeakReference;

public class ImageLoaderCallback implements LoaderCallback {

    private WeakReference<ImageView> imageViewWeakReference;
    public String url;

    public ImageLoaderCallback(ImageView imageView, String url) {
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.url = url;
    }

    @Override
    public void onLoaded(String url, RemoteResource remoteResource) {
        if (imageViewWeakReference != null) {
            ImageView imageView = imageViewWeakReference.get();
            ImageLoaderCallback imageLoaderCallback = getImageLoaderCallback(imageView);
            if ((this == imageLoaderCallback)) {
                Bitmap bitmap = (Bitmap) remoteResource.getResource();
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onCancelled(String url) {

    }

    @Override
    public void onFailed(String url, Exception ex) {

    }

    public static ImageLoaderCallback getImageLoaderCallback(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getImageLoaderCallback();
            }
        }
        return null;
    }

}