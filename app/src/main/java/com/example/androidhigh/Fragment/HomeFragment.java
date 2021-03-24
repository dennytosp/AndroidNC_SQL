package com.example.androidhigh.Fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidhigh.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


public class HomeFragment extends Fragment {
    TextView btnShareDialog;
    ImageView imgShare;

    Bitmap image;
    SharePhoto photo;
    ShareDialog shareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imgShare = view.findViewById(R.id.imgShare);
        btnShareDialog = view.findViewById(R.id.btnShareDialog);

        shareDialog = new ShareDialog(this);
        btnShareDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get Drawable from imageView
                Drawable drawable = imgShare.getDrawable();
                Bitmap bmp = null;
                if (drawable instanceof BitmapDrawable) {
                    bmp = ((BitmapDrawable) imgShare.getDrawable()).getBitmap();
                    Log.d("t", "co bitmap");
                } else Log.d("t", "bitmap null");

                photo = new SharePhoto.Builder()
                        .setCaption("Photo Test")
                        .setBitmap(bmp)
                        .build();
                // only share image
                SharePhotoContent content1 = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                // multishare
//                ShareContent shareContent = new ShareMediaContent.Builder()
//                        .addMedium(photo)
//                        .build();

                shareDialog.show(getActivity(), content1);
            }
        });
        //Share link with ShareButton
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.youtube.com/watch?v=2qQ3pK9GwmE"))
                .build();


        ShareButton shareButton = view.findViewById(R.id.btnShare);
        shareButton.setShareContent(content);

        return view;

    }
}
