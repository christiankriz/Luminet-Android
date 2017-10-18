package com.luminet.mobile.luminetandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.luminet.mobile.luminetandroid.newsFeed.NewsFeed;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.activity.CompanyActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * Created by chris on 2017/10/05.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsFeed> newsFeedList;
    private Context ctx;
    NewsFeedHolder newsFeedHolder;
    NewsFeed newsFeed;
    byte[] decodedString;
    ImageView imageView;

    public NewsFeedAdapter(List<NewsFeed> newsFeedList, Context ctx) {
        this.newsFeedList = newsFeedList;
        this.ctx = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_feed_item, parent, false);
        return new NewsFeedAdapter.NewsFeedHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        newsFeed = newsFeedList.get(position);
        newsFeedHolder = (NewsFeedHolder) holder;
        newsFeedHolder.companyName.setText(newsFeed.getNewsFeedHeader());
        newsFeedHolder.message.setText(newsFeed.getNewsFeedBody());
        String imageUrl = newsFeed.getImageURL();
        if(newsFeed.getImageURL().length() >10 && imageUrl.contains(",")){
            Bitmap decodedByte = getBitmap(imageUrl);
            newsFeedHolder.imageView.setImageBitmap(decodedByte);
            newsFeedHolder.imageView.setTag(position);
        }else{
            newsFeedHolder.imageView.setTag(position);
            Bitmap bitmap = ((BitmapDrawable)newsFeedHolder.imageView.getDrawable()).getBitmap();
            decodedString = getBytesFromBitmap(bitmap);
        }
        newsFeedHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsFeed = newsFeedList.get(Integer.parseInt(view.getTag().toString()));
                imageView = (ImageView) view;
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                decodedString = getBytesFromBitmap(bitmap);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                bundle.putString("companyName", newsFeed.getNewsFeedHeader());
                bundle.putString("messageBody", newsFeed.getNewsFeedBody());
                bundle.putByteArray("image",decodedString);
                //Add the bundle to the intent
                Intent i = new Intent(ctx, CompanyActivity.class);
                i.putExtras(bundle);

                //Fire the second activity
                ctx.startActivity(i);
            }
        });
    }

    private Bitmap getBitmap(String stringBitmap){
        Bitmap bitmap = null;
        String base64Image = null;
        if(stringBitmap.contains(",")) {
            base64Image = stringBitmap.split(",")[1];
            decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bitmap;
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    @Override
    public int getItemCount() {
        return newsFeedList == null ? 0 : newsFeedList.size();
    }


    public class NewsFeedHolder extends RecyclerView.ViewHolder {
        protected TextView companyName;
        protected TextView message;
        protected ImageView imageView;


        public NewsFeedHolder(View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.company);
            //companyName.setEnabled(false);
            companyName.setTypeface(null, Typeface.BOLD_ITALIC);
            message = itemView.findViewById(R.id.message);
            //message.setEnabled(false);
            imageView = itemView.findViewById(R.id.company_logo);

        }
    }
}