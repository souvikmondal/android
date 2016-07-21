package com.lib.sampleimagelist.presenter;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lib.loaderlib.LoaderManager;
import com.lib.loaderlib.factory.model.ImageResource;
import com.lib.sampleimagelist.R;
import com.lib.sampleimagelist.utils.ImageLoaderCallback;
import com.lib.sampleimagelist.widget.DownloadedDrawable;

/**
 * Created by souvik on 7/19/2016.
 */
public class ImageSliderFragment extends Fragment {
    public static final String KEY_IMAGE_URL = "key_image_url";

    private ImageView mImageView;

    public ImageSliderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_image_slide, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.img_full);
        String imageUrl = getArguments().getString(KEY_IMAGE_URL);
        displayImage(imageUrl);
        return rootView;
    }

    protected void displayImage(String imageUrl) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        int width = size.x - (margin * 2);
        int height = size.y - (margin * 2);

        if (cancelPotentialDownload(imageUrl, mImageView)) {
            ImageLoaderCallback imageLoaderCallback = new ImageLoaderCallback(mImageView, imageUrl);
            DownloadedDrawable downloadedDrawableImage = new DownloadedDrawable(imageLoaderCallback);
            mImageView.setImageDrawable(downloadedDrawableImage);
            LoaderManager.getInstance().load(imageUrl,
                    new ImageResource(width, height), imageLoaderCallback, false);
        }

    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        ImageLoaderCallback imageLoaderCallback = ImageLoaderCallback.getImageLoaderCallback(imageView);

        if (imageLoaderCallback != null ) {
            if (!url.equals(imageLoaderCallback.url)) {
                LoaderManager.getInstance().cancel(url, imageLoaderCallback);
            } else {
                //already being downloaded
                return false;
            }
        }
        return true;
    }

}
