package com.example.androidhigh.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidhigh.Adapter.FeedAdapter;
import com.example.androidhigh.Common.HTTPDataHandler;
import com.example.androidhigh.Model.Item;
import com.example.androidhigh.Model.RSSObject;
import com.example.androidhigh.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
    RecyclerView recyclerView;

    private ArrayList<Item> arrayItem = new ArrayList<>();
    private FeedAdapter feedAdapter;
    public final String RSS_link = "https://vnexpress.net/rss/giao-duc.rss";
    private final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json?rss_url=";

    private ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        feedAdapter = new FeedAdapter(arrayItem);
        recyclerView.setAdapter(feedAdapter);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading... ");
        mDialog.setCancelable(false);

        new loadRSSAsync().execute(RSS_to_Json_API + RSS_link);

        return view;

    }

    private class loadRSSAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            // mDialog.isShowing();
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result;
            HTTPDataHandler http = new HTTPDataHandler();
            result = http.GetHTTPData(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            RSSObject rssObject = new Gson().fromJson(s, RSSObject.class);

            arrayItem.clear();
            arrayItem.addAll(rssObject.getItems());
            feedAdapter.notifyDataSetChanged();
            if (mDialog.isShowing())

                // mDialog.isShowing();
                mDialog.dismiss();
        }
    }


}
