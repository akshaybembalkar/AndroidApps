package com.ab.searchgooglemapsapi;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.searchgooglemapsapi.database.Location;
import com.ab.searchgooglemapsapi.database.LocationDatabase;
import com.ab.searchgooglemapsapi.database.SingletonInstance;
import com.ab.searchgooglemapsapi.retrofit_models.AddressModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;
import java.util.List;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LocationDatabase locationDatabase;

    private double latitude;
    private double longitude;
    private String address;
    private String locationId;
    private static List<AddressModel> addressList;
    private Location savedLocation;
    private Menu menu;
    private Context mContext;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps_avtivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        latitude = b.getDouble("Lat");
        longitude = b.getDouble("Lng");
        address = b.getString("address");
        locationId = b.getString("locationId");

        addressList = getIntent().getParcelableArrayListExtra("addressList");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mContext = this;
        progressBar = findViewById(R.id.loadingData);
//        locationDatabase = Room.databaseBuilder(getApplicationContext(),
//                LocationDatabase.class, "location-db").build();
        locationDatabase = SingletonInstance.getDbInstance(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        new RetrieveLocation(GoogleMapsActivity.this, locationId).execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (addressList == null) {
            LatLng location = new LatLng(latitude, longitude);
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(location);
            googleMap.moveCamera(center);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(4);
            googleMap.animateCamera(zoom);
            createMarker(googleMap, location, address);
        } else {
            for (int i=0; i<addressList.size(); i++) {
                double lat = addressList.get(i).getGeometry().getLocation().getLatitude();
                double lng = addressList.get(i).getGeometry().getLocation().getLongitude();
                String locationName = addressList.get(i).getFormattedAddress();
                LatLng location = new LatLng(lat, lng);
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(location);
                googleMap.moveCamera(center);
                createMarker(googleMap, location, locationName);
            }
            CameraUpdate zoom=CameraUpdateFactory.zoomTo((float)2.5);
            googleMap.animateCamera(zoom);
        }
    }

    /**Add marker on Google map*/
    public void createMarker(GoogleMap googleMap, LatLng location, String locationName) {
        String lat = "Lat: "+Double.toString(location.latitude) + "\n"
                +"Lng: "+Double.toString(location.longitude);

        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(locationName)
                .snippet(lat));

        /**Add customized info window to display maker details*/
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);
                info.setPadding(10, 10, 10, 10);
                info.setBackgroundColor(Color.GRAY);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.BLACK);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                return info;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        /** Change marker color back to RED on info window close*/
        googleMap.setOnInfoWindowCloseListener(new GoogleMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
        });
    }

    public void onSuccessfulInsert() {
        MenuItem item = menu.findItem(R.id.action_save);
        item.setVisible(false);

        getMenuInflater().inflate(R.menu.action_bar_menu_delete, menu);
    }

    public void onSuccessfulDelete() {
        MenuItem item = menu.findItem(R.id.action_delete);
        item.setVisible(false);

        getMenuInflater().inflate(R.menu.action_bar_menu_save, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if (savedLocation == null) {
            if ( addressList == null) {
                getMenuInflater().inflate(R.menu.action_bar_menu_save, menu);
            }
        } else if (addressList == null) {
            getMenuInflater().inflate(R.menu.action_bar_menu_delete, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Location location = new Location();
                location.setLocation_id(locationId);
                location.setFormattedAddress(address);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                new InsertLocation(GoogleMapsActivity.this, location).execute();
                return true;
            case R.id.action_delete:
                new DeleteLocation(GoogleMapsActivity.this, locationId).execute();
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Delete location in database
     */
    private static class DeleteLocation extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<GoogleMapsActivity> activityReference;
        private String locationId;

        DeleteLocation(GoogleMapsActivity context, String locationId) {
            activityReference = new WeakReference<>(context);
            this.locationId = locationId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityReference.get().progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().locationDatabase.locationDao().delete(locationId);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            activityReference.get().progressBar.setVisibility(View.GONE);
            if (result) {
                Toast.makeText(activityReference.get().mContext, "Deleted successfully", Toast.LENGTH_LONG).show();
                activityReference.get().onSuccessfulDelete();
            } else {
                Toast.makeText(activityReference.get().mContext, "Failed to delete", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Save location in database
     */
    private static class InsertLocation extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<GoogleMapsActivity> activityReference;
        private Location location;

        InsertLocation(GoogleMapsActivity context, Location location) {
            activityReference = new WeakReference<>(context);
            this.location = location;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activityReference.get().progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... parms) {
            activityReference.get().locationDatabase.locationDao().insert(location);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                activityReference.get().progressBar.setVisibility(View.GONE);
                Toast.makeText(activityReference.get().mContext, "Inserted successfully", Toast.LENGTH_LONG).show();
                activityReference.get().onSuccessfulInsert();
            } else {
                Toast.makeText(activityReference.get().mContext, "Failed to insert", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Query selected location from database
     */
    private static class RetrieveLocation extends AsyncTask<Void,Void,Location> {

        private WeakReference<GoogleMapsActivity> activityReference;
        private String locationId;

        // only retain a weak reference to the activity
        RetrieveLocation(GoogleMapsActivity context, String locationId) {
            activityReference = new WeakReference<>(context);
            this.locationId = locationId;
        }

        @Override
        protected Location doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().locationDatabase.locationDao().getByLocationId(locationId);
            else
                return null;
        }

        @Override
        protected void onPostExecute(Location location) {
            // System.out.println(location);
            activityReference.get().savedLocation = location;
        }
    }
}
