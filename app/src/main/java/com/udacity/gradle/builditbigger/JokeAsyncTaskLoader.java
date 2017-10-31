package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cmonzon.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * @author cmonzon
 */

public class JokeAsyncTaskLoader extends AsyncTaskLoader<String> {

    private static final String TAG = JokeAsyncTaskLoader.class.getSimpleName();

    private String joke = null;

    @Nullable
    private ProgressBar progressBar;

    public JokeAsyncTaskLoader(Context context, @Nullable ProgressBar progressBar) {
        super(context);
        this.progressBar = progressBar;
    }

    @Override
    protected void onStartLoading() {
        if (joke != null) {
            deliverResult(joke);
        } else {
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            forceLoad();
        }
        super.onStartLoading();
    }

    @Override
    public String loadInBackground() {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                });
        MyApi myApiService = builder.build();
        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "load in background", e);
            return e.getLocalizedMessage();
        }
    }

    @Override
    public void deliverResult(String data) {
        joke = data;
        super.deliverResult(data);
    }
}
