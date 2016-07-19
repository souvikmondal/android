package com.lib.loaderlib;

import com.lib.loaderlib.factory.model.RemoteResource;

/**
 * Created by souvik on 7/10/2016.
 */
public interface LoaderCallback {

    public void onLoaded(String url, RemoteResource remoteResource);
    public void onCancelled(String url);
    public void onFailed(String url, Exception ex);

}
