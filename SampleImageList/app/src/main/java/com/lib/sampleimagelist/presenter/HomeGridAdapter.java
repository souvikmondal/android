package com.lib.sampleimagelist.presenter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.loaderlib.LoaderManager;
import com.lib.loaderlib.factory.model.ImageResource;
import com.lib.sampleimagelist.R;
import com.lib.sampleimagelist.model.PinModel;
import com.lib.sampleimagelist.utils.ImageLoaderCallback;
import com.lib.sampleimagelist.widget.DownloadedDrawable;

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
            OnGridItemClickListener gridItemClickListener = new OnGridItemClickListener();
            gridItemClickListener.gridItemPosition = position;
            holder.gridItemClickListener = gridItemClickListener;
            view.setOnClickListener(gridItemClickListener);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
            holder.gridItemClickListener.gridItemPosition = position;
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
            LoaderManager.getInstance().load(imageUrl,
                    new ImageResource(width, height), imageLoaderCallback, false);
        }

        String profileImage = pinModel.getUser().getProfile_image().get("small");
        if (cancelPotentialDownload(profileImage, holder.imageViewProfile)) {
            int width = parent.getResources().getDimensionPixelSize(R.dimen.profile_icon_width);
            int height = parent.getResources().getDimensionPixelSize(R.dimen.profile_icon_height);
            ImageLoaderCallback imageLoaderCallbackProfile = new ImageLoaderCallback(holder.imageViewProfile, profileImage);
            DownloadedDrawable downloadedDrawableImageProfile = new DownloadedDrawable(imageLoaderCallbackProfile);
            holder.imageViewProfile.setImageDrawable(downloadedDrawableImageProfile);
            LoaderManager.getInstance().load(profileImage,
                    new ImageResource(width, height), imageLoaderCallbackProfile, false);
        }

        return view;
    }

    class OnGridItemClickListener implements View.OnClickListener {

        private int gridItemPosition;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ImageSliderActivity.class);
            intent.putExtra(ImageSliderActivity.KEY_IMAGE_POSITION, gridItemPosition);
            v.getContext().startActivity(intent);
        }

    }

    public static class ViewHolder {
        public TextView profileNameView;
        public TextView likesCountView;
        public ImageView imageView;
        public ImageView imageViewProfile;
        public OnGridItemClickListener gridItemClickListener;
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