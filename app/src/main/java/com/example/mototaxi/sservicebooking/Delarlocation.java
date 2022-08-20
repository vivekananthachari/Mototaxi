package com.example.mototaxi.sservicebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.mototaxi.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Delarlocation extends AppCompatActivity implements OnMapReadyCallback, RoutingListener {
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 1000.0f; // in meters
    private GoogleMap mMap;
    // New variables for Current Place Picker
    private static final String TAG = "MapsActivity";
    private Circle geoFenceLimits;

    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    RecyclerView recyclerView;
    TextView workshop,filterdata;
    LinearLayout filter;
    String mycity1;
    String myaddress1;
    TextView myaddress,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delarlocation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Delarlocation.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_time", "true");
        editor.commit();

        mycity1=sharedPreferences.getString("city","");
        myaddress1=sharedPreferences.getString("address","");
        myaddress=findViewById(R.id.myaddress);

        city=findViewById(R.id.city);


        city.setText(mycity1);
        myaddress.setText(myaddress1);
        ArrayList<ListRowItem> listitem = new ArrayList<>();
        Context context = Delarlocation.this;
        MyBaseAdapter baseAdapter;

        ListRowItem lr = new ListRowItem();
        lr.setCarrier("General Checkup");
        listitem.add(lr);

        ListRowItem lr1= new ListRowItem();
        lr1.setCarrier("Scheduled Oil Service");
        listitem.add(lr1);
        baseAdapter = new MyBaseAdapter(context,listitem);

       ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(baseAdapter);

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //

        // Set up the action toolbar


       workshop=findViewById(R.id.workshop);
       filterdata=findViewById(R.id.filterdata);
        recyclerView=findViewById(R.id.recyclier);
        filter=findViewById(R.id.filter);

        recyclerView.setVisibility(View.VISIBLE);
        filter.setVisibility(View.GONE);




       workshop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               recyclerView.setVisibility(View.VISIBLE);
               filter.setVisibility(View.GONE);
           }
       });


        filterdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                filter.setVisibility(View.VISIBLE);

            }
        });



        ArrayList<Mydata> myListDatanew = new ArrayList() ;
        Mydata mydata=new Mydata("Murugan Store","2.0","4.0","2400", R.drawable.car1);
        Mydata mydata1=new Mydata("Anbu Store","2.0","4.0","2000", R.drawable.car1);
        Mydata mydata2=new Mydata("Ravi Store","2.0","4.0","1400", R.drawable.car1);
        myListDatanew.add(mydata);
        myListDatanew.add(mydata1);
        myListDatanew.add(mydata2);
        MyListAdapterdelear adapter = new MyListAdapterdelear(myListDatanew, Delarlocation.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        // Set up the views
        // Initialize the Places client
        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                pickCurrentPlace();
                // this code will be executed after 2 seconds
            }
        }, 3000);


    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //

        // Enable the zoom controls for the map
        mMap.getUiSettings().setZoomControlsEnabled(true);



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pickCurrentPlaceclick(latLng.latitude,latLng.longitude);
            }
        });

        // Prompt the user for permission.
        getLocationPermission();

    }

    /**
     * Calls the findCurrentPlace method in Google Maps Platform Places API.
     * Response contains a list of placeLikelihood objects.
     * Takes the most likely places and extracts the place details for access in other methods.
     */

    /**
     * Get the current location of the device, and position the map's camera
     */

    private void getDeviceLocationclick(Double lat,Double lon) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); //

                final LatLng mDefaultLocation1 = new LatLng(lat, lon);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .title(city)
                        .position(mDefaultLocation1)
                        .snippet(state));
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = location;

                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(Delarlocation.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                               String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                              String  city = addresses.get(0).getLocality();
                              String  state = addresses.get(0).getAdminArea();
                                String country = addresses.get(0).getCountryName();
                                String postalCode = addresses.get(0).getPostalCode();
                                String knownName = addresses.get(0).getFeatureName(); //


                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Delarlocation.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("city", city);
                                editor.commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            final LatLng mDefaultLocation1 = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(mDefaultLocation1)
                                    );

                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            drawGeofence();



                            final LatLng mDefaultLocation122= new LatLng(10.828232, 78.683240);

                            final LatLng mDefaultLocation12= new LatLng(10.832169, 78.681406);
                            mMap.addMarker(new MarkerOptions()
                                    .title("Murugan Store")
                                    .position(mDefaultLocation12)
                                    .snippet("trichy"));

                            mMap.addMarker(new MarkerOptions()
                                    .title("Anbu Store")
                                    .position(mDefaultLocation122)
                                    .snippet("trichy"));

                           LatLng start = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());

                            LatLng end = new LatLng(10.828232, 78.683240);

                            Routing routing = new Routing.Builder()
                                    .travelMode(Routing.TravelMode.WALKING)
                                    .withListener(Delarlocation.this)
                                    .waypoints(start, end)
                                    .build();
                            routing.execute();

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        }

                        //  getCurrentPlaceLikelihoods();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    private Geofence createGeofence(LatLng latLng, float radius ) {
        Log.d(TAG, "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration( GEO_DURATION )
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT )
                .build();
    }
    private GeofencingRequest createGeofenceRequest(Geofence geofence ) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
                .addGeofence( geofence )
                .build();
    }
    private void drawGeofence() {
        Log.d(TAG, "drawGeofence()");

        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
        LatLng latLng=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
        CircleOptions circleOptions = new CircleOptions()
                .center( latLng)
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor( Color.argb(100, 150,150,150) )
                .radius( GEOFENCE_RADIUS );
        geoFenceLimits = mMap.addCircle( circleOptions );
    }
    /**
     * Fetch a list of likely places, and show the current place on the map - provided the user
     * has granted location permission.
     */
    private void pickCurrentPlaceclick(Double lat,Double longg) {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocationclick(lat,longg);
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocationclick(lat,longg);
            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocation();
            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

    /**
     * When user taps an item in the Places list, add a marker to the map with the place details
     */
    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // position will give us the index of which place was selected in the array
            LatLng markerLatLng = mLikelyPlaceLatLngs[position];
            String markerSnippet = mLikelyPlaceAddresses[position];
            if (mLikelyPlaceAttributions[position] != null) {
                markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            mMap.addMarker(new MarkerOptions()
                    .title(mLikelyPlaceNames[position])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));
        }
    };

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Delarlocation.this, Maplocation.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Delarlocation.this.finish();
        super.onBackPressed();
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

    }

    /**
     * Display a list allowing the user to select a place from a list of likely places.
     */
//    private void fillPlacesList() {
//        // Set up an ArrayAdapter to convert likely places into TextViews to populate the ListView
//        ArrayAdapter<String> placesAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_2, mLikelyPlaceNames);
//        lstPlaces.setAdapter(placesAdapter);
//        lstPlaces.setOnItemClickListener(listClickedHandler);
//    }
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mMap.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

             MarkerOptions   markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                mMap.addMarker(markerOptions);

                // Locate the first location
                if(i==0)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }
    public  class Mydata {
        private  String Shop_name;
        private  String Distance;
        private  String Rating;
        private  String Ruppe;
        private int imgid;

        public String getShop_name() {
            return Shop_name;
        }

        public void setShop_name(String shop_name) {
            Shop_name = shop_name;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String distance) {
            Distance = distance;
        }

        public String getRating() {
            return Rating;
        }

        public void setRating(String rating) {
            Rating = rating;
        }

        public String getRuppe() {
            return Ruppe;
        }

        public void setRuppe(String ruppe) {
            Ruppe = ruppe;
        }

        public int getImgid() {
            return imgid;
        }

        public void setImgid(int imgid) {
            this.imgid = imgid;
        }

        public Mydata(String shop_name,String Distance,String Rating,String ruppy,int imgid) {
            this.Shop_name = shop_name;
            this.Distance = Distance;
            this.Rating = Rating;
            this.imgid = imgid;
            this.Ruppe=ruppy;
        }

    }

}