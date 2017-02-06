package com.video.kamcord.kamcordvideosfeed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay B on 12/25/2016.
 */

public class CustomGridLayout extends BaseAdapter{

    private Context mContext;
    private List<VideoInfo> video_data;
    ImageLoader imageLoader;

    public CustomGridLayout(Context context, List<VideoInfo> data) {
        mContext = context;
        this.video_data = data;
        imageLoader = new ImageLoader(mContext);
    }

    @Override
    public int getCount() {
        return video_data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
        //return  video_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.videogrid_layout, parent,false);
        }
        else{
            v = convertView;
        }
        ImageView image = (ImageView)v.findViewById(R.id.thumbImage);

        if(isNetworkAvailable()) {
            imageLoader.display(video_data.get(position).getShotThumbnail(),image,R.drawable.color_background);
        }

        return v;
    }

    /**********************************************************************************************/

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**********************************************************************************************/

}
