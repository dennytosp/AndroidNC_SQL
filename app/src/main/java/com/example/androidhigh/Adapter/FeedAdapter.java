package com.example.androidhigh.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidhigh.Model.Item;
import com.example.androidhigh.R;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private ArrayList<Item> arrayItem;

    public FeedAdapter(ArrayList<Item> arrayItem) {
        this.arrayItem = arrayItem;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.txtTitle.setText(arrayItem.get(position).getTitle());
        holder.txtPubDate.setText(arrayItem.get(position).getPubDate());
        Glide.with(holder.itemView).load(arrayItem.get(position).getThumbnail()).into(holder.image);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.txtContent.setText(Html.fromHtml(arrayItem.get(position).getContent(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            holder.txtContent.setText(Html.fromHtml(arrayItem.get(position).getContent()));
//        }
    }

    @Override
    public int getItemCount() {
        return arrayItem.size();
    }


    class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtPubDate, txtContent;
        ImageView image;

        FeedViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPubDate = itemView.findViewById(R.id.txtPubDate);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(arrayItem.get(getAdapterPosition()).getLink()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}