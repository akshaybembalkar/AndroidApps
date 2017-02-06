package com.video.kamcord.kamcordvideosfeed;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Akshay B on 12/26/2016.
 * ImageLoader class downloads image from given URL, caches it and displays it in the imageView
 *
 * Availability : http://stackoverflow.com/questions/11623994/example-using-androids-lrucache
 */

public class ImageLoader implements ComponentCallbacks2 {
    private ImageLruCache cache;

    public ImageLoader(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = 1024 * maxKb / 8; // 1/8th of total ram
        cache = new ImageLruCache(limitKb);
    }

    public void display(String url, ImageView imageview, int defaultResource) {
        //imageview.setImageResource(defaultResource);
        Bitmap image = cache.get(url);
        if (image != null) {
            imageview.setImageBitmap(image);
        }
        else {
            new SetImageTask(imageview).execute(url);
        }
    }

    private class ImageLruCache extends LruCache<String, Bitmap> {

        public ImageLruCache(int maxSize) {
            super(maxSize);
        }
/*
        @Override
        protected int sizeOf(ImagePoolKey key, Bitmap value) {
            int kbOfBitmap = value.getByteCount() / 1024;
            return kbOfBitmap;
        }*/
    }

    private class SetImageTask extends AsyncTask<String, Void, Integer> {
        private ImageView imageview;
        private Bitmap bmp;

        public SetImageTask(ImageView imageview) {
            this.imageview = imageview;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String url = params[0];
            try {
                bmp = getBitmapFromURL(url,imageview);
                if (bmp != null) {
                    cache.put(url, bmp);
                }
                else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                imageview.setImageBitmap(bmp);
            }
            super.onPostExecute(result);
        }

        private Bitmap getBitmapFromURL(String src,ImageView destination) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection
                        = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

/*
            URL website;
            int req_width = imageview.getMaxWidth();
            int req_height = imageview.getMaxHeight();
            try {
                website = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) website.openConnection();
                InputStream is = connection.getInputStream();

                if(req_width == 0)
                {
                    return BitmapFactory.decodeStream(is);
                }


                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options);
                is.close();

                //System.out.println("Image url: "+src);
                connection = (HttpURLConnection) website.openConnection();
                is = connection.getInputStream();
                options.inSampleSize = calculateInSampleSize(options, req_width, req_height);
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeStream(is, null , options);

            } catch (Exception  e) {
                return null;
            }*/
            //return bitmap;
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @SuppressLint("NewApi")
    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }
}