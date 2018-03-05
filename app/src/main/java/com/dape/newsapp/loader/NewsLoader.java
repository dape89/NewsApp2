package com.dape.newsapp.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.dape.newsapp.model.News;
import com.dape.newsapp.utils.NewsQueryUtils;
import java.util.List;

/**
 * Created by Danale on 01/03/2018.
 */

@SuppressWarnings("ALL")
public class NewsLoader extends AsyncTaskLoader<List<News>>{
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String strUrl;
    public NewsLoader(Context context, String url) {
        super(context);
        strUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        if(strUrl == null) {
            return null;
        }
        List<News> news = NewsQueryUtils.takeNewsData(strUrl);
        return news;
    }
}
