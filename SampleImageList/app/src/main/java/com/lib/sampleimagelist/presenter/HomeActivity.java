package com.lib.sampleimagelist.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.lib.loaderlib.LoaderCallback;
import com.lib.loaderlib.LoaderManager;
import com.lib.loaderlib.factory.model.RemoteResource;
import com.lib.loaderlib.factory.model.TextResource;
import com.lib.sampleimagelist.R;
import com.lib.sampleimagelist.model.PinModel;
import com.lib.sampleimagelist.utils.JSONParser;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private GridView mGridView;
    private HomeGridAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Bitmap NO_IMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.no_image);

        hookInControls();

    }

    private void hookInControls() {
        mGridView = (GridView) findViewById(R.id.home_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swip_refresh_layout);
        refreshContent();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        String endPoint = getString(R.string.service_endpoint);
        LoaderManager.getInstance().load(endPoint,
                new TextResource(), new DataLoaderCallback(), true);
    }

    class DataLoaderCallback implements LoaderCallback {

        @Override
        public void onLoaded(String url, RemoteResource remoteResource) {
            String jsonString = (String) remoteResource.getResource();
            PinModel[] pinModels = JSONParser.parse(jsonString, PinModel[].class);
            mAdapter = new HomeGridAdapter(pinModels);
            mGridView.setAdapter(mAdapter);
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onCancelled(String url) {
            mSwipeRefreshLayout.setRefreshing(false);

        }

        @Override
        public void onFailed(String url, Exception ex) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
