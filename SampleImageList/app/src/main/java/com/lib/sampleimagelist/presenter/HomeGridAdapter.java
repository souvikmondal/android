package com.lib.sampleimagelist.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.loaderlib.LoaderCallback;
import com.lib.loaderlib.LoaderManager;
import com.lib.loaderlib.factory.model.ImageResource;
import com.lib.loaderlib.factory.model.RemoteResource;
import com.lib.sampleimagelist.R;
import com.lib.sampleimagelist.model.PinModel;

import java.lang.ref.WeakReference;

public class HomeGridAdapter extends BaseAdapter {

    private PinModel[] mAllPins;

    public HomeGridAdapter(PinModel[] pins) {
        mAllPins = pins;

    }

    @Override
    public int getCount() {
        return mAllPins.length;
    }

    @Override
    public Object getItem(int position) {
        return mAllPins[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_grid_view, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.grid_image);
            holder.imageViewProfile = (ImageView) view.findViewById(R.id.icon_profile);
            holder.profileNameView = (TextView) view.findViewById(R.id.profile_name);
            holder.likesCountView = (TextView) view.findViewById(R.id.likes_count);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        PinModel pinModel = mAllPins[position];
        holder.likesCountView.setText(String.valueOf(pinModel.getLikes()));
        holder.profileNameView.setText(pinModel.getUser().getName());

        String imageUrl = pinModel.getUrls().get("small");
        if(cancelPotentialDownload(imageUrl, holder.imageView)) {
            int width = parent.getResources().getDimensionPixelSize(R.dimen.grid_image_width);
            int height = parent.getResources().getDimensionPixelSize(R.dimen.grid_image_height);
            ImageLoaderCallback imageLoaderCallback = new ImageLoaderCallback(holder.imageView, imageUrl);
            DownloadedDrawable downloadedDrawableImage = new DownloadedDrawable(imageLoaderCallback);
            holder.imageView.setImageDrawable(downloadedDrawableImage);
            LoaderManager.getInstance().load(imageUrl, new ImageResource(), imageLoaderCallback, false);
        }

        String profileImage = pinModel.getUser().getProfile_image().get("small");
        if (cancelPotentialDownload(profileImage, holder.imageViewProfile)) {
            int width = parent.getResources().getDimensionPixelSize(R.dimen.profile_icon_width);
            int height = parent.getResources().getDimensionPixelSize(R.dimen.profile_icon_height);
            ImageLoaderCallback imageLoaderCallbackProfile = new ImageLoaderCallback(holder.imageViewProfile, profileImage);
            DownloadedDrawable downloadedDrawableImageProfile = new DownloadedDrawable(imageLoaderCallbackProfile);
            holder.imageViewProfile.setImageDrawable(downloadedDrawableImageProfile);
            LoaderManager.getInstance().load(profileImage, new ImageResource(), imageLoaderCallbackProfile, false);
        }

        return view;
    }

    public static class ViewHolder {
        public TextView profileNameView;
        public TextView likesCountView;
        public ImageView imageView;
        public ImageView imageViewProfile;

    }

    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        ImageLoaderCallback imageLoaderCallback = getImageLoaderCallback(imageView);

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

    private static ImageLoaderCallback getImageLoaderCallback(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getImageLoaderCallback();
            }
        }
        return null;
    }

    static class ImageLoaderCallback implements LoaderCallback {

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
    }

    static class DownloadedDrawable extends BitmapDrawable {
        private WeakReference<ImageLoaderCallback> imageLoaderCallbackWeakReference;


        public DownloadedDrawable(ImageLoaderCallback callback) {
            super(HomeActivity.NO_IMAGE);
            imageLoaderCallbackWeakReference =
                    new WeakReference<ImageLoaderCallback>(callback);
        }

        public ImageLoaderCallback getImageLoaderCallback() {
            return imageLoaderCallbackWeakReference.get();
        }
    }
}