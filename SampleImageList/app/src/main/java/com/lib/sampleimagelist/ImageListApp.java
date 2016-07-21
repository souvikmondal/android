package com.lib.sampleimagelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.multidex.MultiDexApplication;

import com.lib.sampleimagelist.model.PinModel;

/**
 * Created by souvik on 7/19/2016.
 */
public class ImageListApp extends MultiDexApplication{

    private static ImageListApp instance;
    public static Bitmap NO_IMAGE;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
    }

    public static ImageListApp getAppInstance() {
        return instance;
    }

    private PinModel[] pinModels;

    public PinModel[] getPinModels() {
        return pinModels;
    }

    public void setPinModels(PinModel[] pinModels) {
        this.pinModels = pinModels;
    }
}
