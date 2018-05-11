package com.example.matti.svegliamultifunzione.news;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.matti.svegliamultifunzione.R;

import org.json.JSONArray;

public class NewsFrag extends Fragment implements GetLatestNews.AsyncResponse {

    private JSONArray articles;
    private ListView list;
    private RecViewAdapterNews ad;
    private RecyclerView rc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_list, container, false);
        //list= v.findViewById(R.id.list_of_news);
        articles=new JSONArray();
        rc=v.findViewById(R.id.news_recview);
        ad=new RecViewAdapterNews(getContext(),articles);
        rc.setAdapter(ad);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        //an = new ArrayAdapterNews(getContext(),R.layout.news_list,articles);

        //list.setAdapter(an);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        new GetLatestNews.DownloadNews(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void processFinish(final JSONArray articlesList) {
        articles=articlesList;
        //an = new ArrayAdapterNews(getContext(),R.layout.news_list,articles);
        ad=new RecViewAdapterNews(getContext(),articles);
        ad.notifyDataSetChanged();
        rc.setAdapter(ad);
    }
}
