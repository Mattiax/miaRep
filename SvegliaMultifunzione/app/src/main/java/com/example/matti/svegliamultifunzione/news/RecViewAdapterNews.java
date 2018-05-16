package com.example.matti.svegliamultifunzione.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.matti.svegliamultifunzione.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecViewAdapterNews extends RecyclerView.Adapter<RecViewAdapterNews.ViewHolder> {

    private JSONArray articles;
    private Context context;

    public RecViewAdapterNews(Context context, JSONArray articles) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.cardview, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            JSONObject art = articles.getJSONObject(i);
            viewHolder.title.setText(art.isNull("title")?"":art.getString("title"));
            viewHolder.description.setText(art.isNull("description")?"":art.getString("description"));
            viewHolder.author.setText(art.isNull("author")?"":art.getString("author"));
            viewHolder.source.setText(art.isNull("name")?"":art.getString("name"));
            if(!art.isNull("urlToImage")) {
                Log.d("URL",art.getString("urlToImage"));
                viewHolder.image.setMaxHeight(300);
                viewHolder.image.setImageUrl(art.getString("urlToImage"), MySingleton.getInstance(context).getImageLoader());
            }
        } catch (NullPointerException|JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return articles.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, description, author, source;
        public NetworkImageView image;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.news_title);
            description = v.findViewById(R.id.news_description);
            author = v.findViewById(R.id.news_author);
            source = v.findViewById(R.id.news_source);
            image = v.findViewById(R.id.news_image);
        }
    }
}
