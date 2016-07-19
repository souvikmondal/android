/**
 * A loader controller which is responsible for downloading a resource from specified url
 * and keeping them in a memory cache. The cached resources would be garbage collected
 * once they are no more used. The loader will not download a resource multiple time unless
 * it is garbage collected. The downloading would be happening in a background thread and
 * the number of threads spawned depends on the cpu of the device.
 */

package com.lib.loaderlib;

import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.lib.loaderlib.factory.model.ImageResource;
import com.lib.loaderlib.factory.model.RemoteResource;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by souvik on 7/10/2016.
 */
public final class LoaderManager {

    private static final Object LOCK = new Object();

    private static LoaderManager instance = new LoaderManager();

    public static LoaderManager getInstance() {
        return instance;
    }

    private LruCache<String, RemoteResource> mResourceCache;
    private Map<String, List<LoaderCallback>> mCallbackMap;

    private LoaderManager() {
        initResourceCache();
        mCallbackMap = new WeakHashMap<>();
    }

    /**
     * Load a @RemoteResource from the specified @url.
     * After successful download the caller would be
     * notified by the @LoaderCallback instance provided.
     * @param url Remote http/https location url
     * @param resource Expected resource from the url
     * @param callback Implementation of @LoaderCallback for receiving the callbacks
     * @param force If set to true the recent cached resource would be cleared and re-download triggered.
     */
    public void load(String url, RemoteResource resource,
                     LoaderCallback callback, boolean force) {
        synchronized (LOCK) {
            boolean started = addCallbackToMap(url, callback);
            RemoteResource cachedRes = getResourceFromCache(url);
            if ((cachedRes == null && !started) || force) {//force download
                DownloaderTask task = new DownloaderTask(resource);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
            } else if (cachedRes != null) {// finished
                callback.onLoaded(url, cachedRes);
            }
        }
    }

    /**
     * Un-subscribe from a downloading
     * @param url
     * @param callback
     */
    public void cancel(String url, LoaderCallback callback) {
        synchronized (LOCK) {
            List<LoaderCallback> callbackList = getCallbacks(url);
            if (callbackList != null && callbackList.size() > 0) {
                callbackList.remove(callback);
            }
        }
    }

    private boolean addCallbackToMap(String url, LoaderCallback callback) {
        boolean callbackExist = true;
        List<LoaderCallback> callbackList = mCallbackMap.get(url);
        if (callbackList == null) {
            callbackList = new ArrayList<>();
            mCallbackMap.put(url, callbackList);
            callbackExist = false;
        }
        callbackList.add(callback);
        return callbackExist;
    }

    private List<LoaderCallback> getCallbacks(String url) {
        return mCallbackMap.get(url);
    }

    private void initResourceCache() {
        mResourceCache = new LruCache<String, RemoteResource>(computeCacheSize()) {
            @Override
            protected int sizeOf(String key, RemoteResource remoteResource) {
                return remoteResource.size() / 1024;
            }
        };
    }

    private int computeCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        return cacheSize;
    }

    private void addResourceToCache(String url, RemoteResource remoteResource) {
        if (remoteResource != null) {
            mResourceCache.put(url, remoteResource);
        }
    }

    private RemoteResource getResourceFromCache(String url) {
        final RemoteResource remoteResource = mResourceCache.get(url);
        return remoteResource;
    }

    private RemoteResource download(String url, RemoteResource resource) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            HttpURLConnection.setFollowRedirects(true);
            URL urlObj = new URL(url);
            httpURLConnection = (HttpURLConnection) urlObj.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
//TODO we can create the resources using the content type
//                String contentType = httpURLConnection.getHeaderField("Content-Type");
                resource.prepare(httpURLConnection.getInputStream());
            } else {
                throw new IOException("Http bad response code : " + responseCode);
            }
        } finally {
            try {
                httpURLConnection.disconnect();
            } catch (Exception ex) {
                //ignore
            }
        }
        return resource;
    }

    final class DownloaderTask extends AsyncTask<String, Void, RemoteResource> {

        private String mUrl;
        private Exception mException;
        private RemoteResource remoteResource;

        public DownloaderTask(RemoteResource res) {
            remoteResource = res;
        }

        @Override
        protected RemoteResource doInBackground(String... params) {

            mUrl = params[0];
            try {
                remoteResource = download(mUrl, remoteResource);
            } catch (IOException e) {
                remoteResource = null;
                mException = e;
            }

            return remoteResource;
        }

        @Override
        protected void onPostExecute(RemoteResource remoteResource) {
            synchronized (LOCK) {
                List<LoaderCallback> callbackList = mCallbackMap.remove(mUrl);
                if (remoteResource == null || remoteResource.size() == -1) {
                    if (callbackList != null && callbackList.size() > 0) {
                        for (LoaderCallback callback:callbackList) {
                            callback.onFailed(mUrl, mException);
                        }
                    }
                } else {
                    addResourceToCache(mUrl, remoteResource);
                    if (callbackList != null && callbackList.size() > 0) {
                        for (LoaderCallback callback:callbackList) {
                            callback.onLoaded(mUrl, remoteResource);
                        }
                    }
                }
            }
        }
    }

}
