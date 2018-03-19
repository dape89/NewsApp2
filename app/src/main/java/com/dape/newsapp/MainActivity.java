package com.dape.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dape.newsapp.adapter.NewsAdapter;
import com.dape.newsapp.loader.NewsLoader;
import com.dape.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>,
        SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 1;
    private static final String API_KEY = "test";
    private static final String QUERY_API_KEY = "api-key";
    private static final String QUERY_TOPIC = "?q=";
    private static final String QUERY_TAG = "tag";
    private static final String QUERY_SHOW_TAGS = "show-tags";
    private static final String QUERY_SHOW_TAGS_SELECTION = "contributor";
    private NewsAdapter newsAdapter;
    private ProgressBar pgb_news;
    private TextView tv_emptyData;
    private RecyclerView lsv_news;
    private NetworkInfo networkInfo;
    private LoaderManager loaderManager;
    private ArrayList<News> listNews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
    }
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        tv_emptyData.setVisibility(View.GONE);
        pgb_news.setVisibility(View.VISIBLE);
        lsv_news.setVisibility(View.GONE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sectionName = sharedPrefs.getString(
                getString(R.string.settings_sectionNews_key),
                getString(R.string.settings_sectionNews_default));
        Uri uri = Uri.parse(NEWS_REQUEST_URL).buildUpon().
                appendQueryParameter(QUERY_TOPIC,sectionName).
                appendQueryParameter(QUERY_TAG, sectionName + "/" + sectionName).
                appendQueryParameter(QUERY_SHOW_TAGS, QUERY_SHOW_TAGS_SELECTION).
                appendQueryParameter(QUERY_API_KEY,API_KEY).build();
        return new NewsLoader(this, uri.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        if (networkInfo != null && networkInfo.isConnected()) {
            if (news == null || news.size() == 0){
                pgb_news.setVisibility(View.GONE);
                lsv_news.setVisibility(View.GONE);
                tv_emptyData.setVisibility(View.VISIBLE);
                tv_emptyData.setText(getString(R.string.no_news));
            }else{
                tv_emptyData.setVisibility(View.GONE);
                pgb_news.setVisibility(View.GONE);
                lsv_news.setVisibility(View.VISIBLE);
                newsAdapter.addItems(news);
            }
        }else{
            pgb_news.setVisibility(View.GONE);
            lsv_news.setVisibility(View.GONE);
            tv_emptyData.setVisibility(View.VISIBLE);
            tv_emptyData.setText(getString(R.string.no_data));
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
            newsAdapter.clear();
    }
    public void init() {
        lsv_news = findViewById(R.id.lsv_news);
        tv_emptyData = findViewById(R.id.tv_emptyData);
        pgb_news = findViewById(R.id.pgb_news);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lsv_news.setLayoutManager(mLayoutManager);
        lsv_news.setItemAnimator(new DefaultItemAnimator());
        lsv_news.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        networkInfo = connMgr.getActiveNetworkInfo();
        lsv_news.setAdapter(newsAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_sectionNews_key))){
            newsAdapter.clear();
            pgb_news.setVisibility(View.VISIBLE);
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        }
    }
}
