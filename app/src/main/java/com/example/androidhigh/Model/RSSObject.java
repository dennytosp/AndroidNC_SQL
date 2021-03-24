package com.example.androidhigh.Model;

import java.util.ArrayList;

public class RSSObject {
    private String status;
    private Feed feed;
    private ArrayList<Item> items;

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return feed;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
