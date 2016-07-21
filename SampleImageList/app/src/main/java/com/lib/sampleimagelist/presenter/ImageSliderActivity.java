package com.lib.sampleimagelist.presenter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lib.sampleimagelist.ImageListApp;
import com.lib.sampleimagelist.R;
import com.lib.sampleimagelist.model.PinModel;

/**
 * Created by souvik on 7/19/2016.
 */
public class ImageSliderActivity extends FragmentActivity {

    public static final String KEY_IMAGE_POSITION = "key_image_position";

    private ViewPager mViewPager;
    private PinModel[] mPinModels;
    private ImageSliderPagerAdapter mImageSliderPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide);
        hookInControls();
    }

    private void hookInControls() {
        mPinModels = ImageListApp.getAppInstance().getPinModels();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mImageSliderPagerAdapter = new ImageSliderPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mImageSliderPagerAdapter);
        int position = getIntent().getIntExtra(KEY_IMAGE_POSITION, 0);
        mViewPager.setCurrentItem(position);
    }

    private class ImageSliderPagerAdapter extends FragmentStatePagerAdapter {

        public ImageSliderPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            ImageSliderFragment fragment = new ImageSliderFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ImageSliderFragment.KEY_IMAGE_URL, mPinModels[position].getUrls().get("full"));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return mPinModels.length;
        }
    }
}
