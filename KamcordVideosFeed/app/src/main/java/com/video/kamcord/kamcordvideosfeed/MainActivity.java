package com.video.kamcord.kamcordvideosfeed;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

/*
    MainActivity class initializes video grid as app starts.
 */

public class MainActivity extends AppCompatActivity {

    int current_page = 1;
    private List<VideoInfo> videoList; //Add 20 records on each http call.

    private GridView videoGrid;
    private TextView netState;
    private CustomGridLayout customGridLayout;
    private ProgressBar progressBar;

    private String imageSize;
    private String FEED_ID;      //Get feedId for next page
    private String NEXT_PAGE;    // Get next page

    private boolean userScrolled = false; //Check if user scrolled till bottom of Grid
    private boolean loadingMore = true;   //Check if no http request is called
    private boolean stopLoadingData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Specify ActionBar properties.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.kamcordlogo);

        //Initialize views
        netState = (TextView) findViewById(R.id.netStateText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        videoGrid = (GridView) findViewById(R.id.videoGrid);

        videoList = new Vector<>();
        imageSize = "small";
        customGridLayout = new CustomGridLayout(this,videoList);

        initializeActivity();
        //new getDataFromServer().execute("https://api.kamcord.com/v1/feed/set/featuredShots?count=20");
        videoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),VideoPlay.class);
                i.putExtra("heartCount",String.valueOf(videoList.get(position).getHeartCount()));
                i.putExtra("video_url",videoList.get(position).getVideoUrl());
                startActivity(i);
            }
        });

        videoGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                synchronized (this) {
                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        userScrolled = true;
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;

                synchronized (this){
                    if (userScrolled && (lastInScreen >= totalItemCount) && !(loadingMore)) {
//                        System.out.println("Scrolling....");

                        if (!stopLoadingData && (videoGrid.getAdapter().getCount()==videoList.size())
                                && isNetworkAvailable()) {
                            // FETCH THE NEXT BATCH OF FEEDS
                            new getMoreDataFromServer().execute("https://api.kamcord.com/v1/feed/"
                                    + FEED_ID + "?count=20&page=" + NEXT_PAGE);
                        }
                        userScrolled = false;
                    }
                }
            }
        });
    }
/************************************************************************************************/
    /*
    Initialize grid columns and call HTTP GET on Activity start
     */
    public void initializeActivity(){
        if(isNetworkAvailable()) {
            new getDataFromServer().execute("https://api.kamcord.com/v1/feed/set/featuredShots?count=20");
            //new getMoreDataFromServer().execute("https://api.kamcord.com/v1/feed/"+FEED_ID+"?count=20&page="+NEXT_PAGE);
        }
        else{
            netState.setVisibility(View.VISIBLE);
        }
        if(isTablet(this)){
            videoGrid.setNumColumns(5);
            imageSize = "medium";
        }
        else{
            videoGrid.setNumColumns(3);
            imageSize = "medium";
        }
    }

    /**********************************************************************************************/
    /*
        Get first 20 video feeds from server. Run getDataFromServer asynchronously without affecting
        app UI.
     */
    private class getDataFromServer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            loadingMore = true;

            try {
                java.net.URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                //Specify Http Headers
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("accept-language", "en");
                connection.setRequestProperty("device-token","abc123");
                connection.setRequestProperty("client-name", "android");
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here you'll get JSON response

                }
                JSONObject mainResponseObject = new JSONObject(buffer.toString());

                JSONArray groupsArray = mainResponseObject.getJSONArray("groups");
                for(int i=0;i<groupsArray.length();i++){

                    JSONObject feedIdObject = groupsArray.getJSONObject(i);
                    FEED_ID = feedIdObject.getString("feedId");
                    NEXT_PAGE = feedIdObject.getString("nextPage");
                    NEXT_PAGE = NEXT_PAGE.substring(0,NEXT_PAGE.indexOf(','));
                    //System.out.println(NEXT_PAGE);

                    JSONArray cardsArray = new JSONArray(groupsArray.getJSONObject(i).getString("cards"));

                    for(int j=0;j<cardsArray.length();j++){

                        JSONObject cardDataObj = new JSONObject(cardsArray.getJSONObject(j).getString("shotCardData"));
                        JSONObject thumbnailObj = cardDataObj.getJSONObject("shotThumbnail");
                        JSONObject playObj = cardDataObj.getJSONObject("play");

                        int heartCount = Integer.parseInt(cardDataObj.getString("heartCount"));
                        String thumbnailUrl = thumbnailObj.getString(imageSize);
                        String videoUrl = playObj.getString("mp4");
                        //System.out.println(thumbnailUrl);
                        if(thumbnailUrl!=null && videoUrl!=null)
                            videoList.add(new VideoInfo(heartCount,thumbnailUrl,videoUrl));
                    }
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //executeAfter();

            videoGrid.setAdapter(customGridLayout);
            videoGrid.setSelection(0);
            progressBar.setVisibility(View.GONE);
            //System.out.println("Video count: "+videoList.size());
            loadingMore = false;
        }
    }

    /**********************************************************************************************/

    /*
     Fetch next 20 feed once user reached bottom of current video Grid
      */
    private class getMoreDataFromServer extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //videoList.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            loadingMore = true;
            current_page += 1;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                java.net.URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                //Specify Http Headers
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("accept-language", "en");
                connection.setRequestProperty("device-token","abc123");
                connection.setRequestProperty("client-name", "android");
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");               //here you'll get JSON response
                    //Log.d("Response: ", "> " + line);
                }

                //Parse the JSON response.
                JSONObject mainObject = new JSONObject(buffer.toString());

                FEED_ID = mainObject.getString("feedId");
                NEXT_PAGE = mainObject.getString("nextPage");
                NEXT_PAGE = NEXT_PAGE.substring(0,NEXT_PAGE.indexOf(','));

                JSONArray jsonArray = mainObject.getJSONArray("cards");
                for(int j=0;j<jsonArray.length();j++) {

                    JSONObject subObj = new JSONObject(jsonArray.getJSONObject(j).getString("shotCardData"));
                    JSONObject thumbnailObj = subObj.getJSONObject("shotThumbnail");
                    JSONObject playObj = subObj.getJSONObject("play");

                    int heartCount = Integer.parseInt(subObj.getString("heartCount"));
                    String thumbnailUrl = thumbnailObj.getString(imageSize);
                    String videoUrl = playObj.getString("mp4");

                    int page = Integer.parseInt(NEXT_PAGE.substring(0,NEXT_PAGE.indexOf('.')));
                    //System.out.println("page: "+page);
                    if(videoList.size()<=page && (thumbnailUrl!=null && videoUrl!=null))
                        videoList.add(new VideoInfo(heartCount,thumbnailUrl,videoUrl));

                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            int currentPosition = videoGrid.getFirstVisiblePosition();
            customGridLayout.notifyDataSetChanged();
            videoGrid.setSelection(currentPosition + 1);
//            System.out.println("list size: "+videoList.size());
            progressBar.setVisibility(View.GONE);
            loadingMore = false;
            stopLoadingData = false;
        }
    }
    /**********************************************************************************************/

    /*
        Check if current device is a Mobile or Tablet
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**********************************************************************************************/

    /*
        Return true if internet connection is available
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
