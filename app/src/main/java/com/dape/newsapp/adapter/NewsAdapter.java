package com.dape.newsapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dape.newsapp.R;
import com.dape.newsapp.model.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Danale on 02/03/2018.
 */


@SuppressWarnings("DefaultFileTemplate")
public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.MyViewHolder> {
    private ArrayList<News> listNews;
    public NewsAdapter(Activity activity, ArrayList<News> listNews) {
        this.listNews = listNews;
    }
    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {
        final News currentNews = listNews.get(position);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(currentNews.getPublicationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strSdf = sdf.format(date);
        holder.tv_type.setText(currentNews.getPillarName());
        holder.tv_sectionName.setText(currentNews.getSectionName());
        holder.tv_publicationDate.setText(strSdf);
        holder.tv_webTitle.setText(currentNews.getWebTitle());
        holder.tv_webUrl.setText(currentNews.getWebUrl());
        holder.tv_author.setText(currentNews.getName());
    }
    @Override
    public int getItemCount() {
        return listNews.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_type;
        private TextView tv_sectionName;
        private TextView tv_publicationDate;
        private TextView tv_webTitle;
        private TextView tv_webUrl;
        private TextView tv_author;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_pillarName);
            tv_sectionName = itemView.findViewById(R.id.tv_sectionName);
            tv_publicationDate = itemView.findViewById(R.id.tv_publicationDate);
            tv_webTitle = itemView.findViewById(R.id.tv_webTitle);
            tv_webUrl = itemView.findViewById(R.id.tv_webUrl);
            tv_author = itemView.findViewById(R.id.tv_author);
        }
    }
    public void addItems(List<News> news) {
        listNews.clear();
        listNews.addAll(news);
        notifyItemRangeInserted(0, news.size());
    }
    public void clear(){
        listNews.clear();
    }
}

