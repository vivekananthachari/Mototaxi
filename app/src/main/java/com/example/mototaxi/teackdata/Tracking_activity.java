package com.example.mototaxi.teackdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.mototaxi.R;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.vechicledata.MyListDatavechile;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Tracking_activity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {
    RecyclerView vechilerecyclier;
    ArrayList<MyListDatavechile> myListDatanew = new ArrayList() ;
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 500.0f; // in meters
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
    private List<Polyline> polylines=null;
    protected LatLng start=null;
    protected LatLng end=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        MyListDatavechile myListDatavechile=new MyListDatavechile("Pulsar 150","Arivukani","TN2460549595");
        MyListDatavechile myListDatavechile1=new MyListDatavechile("Pulsar 100","Arivukani","TN2460549595");
        MyListDatavechile myListDatavechile2=new MyListDatavechile("Pulsar 100","Arivukani","TN2460549595");

        myListDatanew.add(myListDatavechile);
        myListDatanew.add(myListDatavechile1);
        myListDatanew.add(myListDatavechile2);
        MyListAdaptervechilehorizonaltrack adapter = new MyListAdaptervechilehorizonaltrack(myListDatanew, Tracking_activity.this);
        vechilerecyclier.setHasFixedSize(true);
        vechilerecyclier.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        // vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
        vechilerecyclier.setAdapter(adapter);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Tracking_activity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_time", "true");
        editor.commit();

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //

        // Set up the action toolbar





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



//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                getDeviceLocation();
//            }
//        });

        // Prompt the user for permission.
        getLocationPermission();

    }

    /**
     * Calls the findCurrentPlace method in Google Maps Platform Places API.
     * Response contains a list of placeLikelihood objects.
     * Takes the most likely places and extracts the place details for access in other methods.
     */
    private void getCurrentPlaceLikelihoods() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeFields).build();
        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(this,
                new OnCompleteListener<FindCurrentPlaceResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                        if (task.isSuccessful()) {
                            FindCurrentPlaceResponse response = task.getResult();
                            // Set the count, handling cases where less than 5 entries are returned.
                            int count;
                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                                count = response.getPlaceLikelihoods().size();
                            } else {
                                count = M_MAX_ENTRIES;
                            }

                            int i = 0;
                            mLikelyPlaceNames = new String[count];
                            mLikelyPlaceAddresses = new String[count];
                            mLikelyPlaceAttributions = new String[count];
                            mLikelyPlaceLatLngs = new LatLng[count];

                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                Place currPlace = placeLikelihood.getPlace();
                                mLikelyPlaceNames[i] = currPlace.getName();
                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
                                        null : TextUtils.join(" ", currPlace.getAttributions());
                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();

                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
                                        "" : mLikelyPlaceLatLngs[i].toString();

                                Log.i(TAG, String.format("Place " + currPlace.getName()
                                        + " has likelihood: " + placeLikelihood.getLikelihood()
                                        + " at " + currLatLng));

                                i++;
                                if (i > (count - 1)) {
                                    break;
                                }
                            }


                            // COMMENTED OUT UNTIL WE DEFINE THE METHOD
                            // Populate the ListView
                            // fillPlacesList();
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                            }
                        }
                    }
                });
    }

    /**
     * Get the current location of the device, and position the map's camera
     */

//    private void getDeviceLocationclick(Double lat,Double lon) {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (mLocationPermissionGranted) {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                Geocoder geocoder;
//                List<Address> addresses;
//                geocoder = new Geocoder(this, Locale.getDefault());
//
////                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
////
////                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
////                String city = addresses.get(0).getLocality();
////                String state = addresses.get(0).getAdminArea();
////                String country = addresses.get(0).getCountryName();
////                String postalCode = addresses.get(0).getPostalCode();
////                String knownName = addresses.get(0).getFeatureName(); //
////
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Tracking_activity.this);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("city", "");
//                editor.commit();
//                final LatLng mDefaultLocation1 = new LatLng(lat, lon);
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions()
//                        .title("")
//                        .position(mDefaultLocation1)
//                        .snippet(""));
//
//                final LatLng origin1= new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//                final LatLng dest= new LatLng(10.790483, 78.704674);
//
//                mMap.addMarker(new MarkerOptions()
//                        .title("Murugan Store")
//                        .position(dest)
//                        .snippet("trichy"));
//
//
//
//                mMap.addPolyline((new PolylineOptions()).add(origin1,dest).
//                        // below line is use to specify the width of poly line.
//                                width(5)
//                        // below line is use to add color to our poly line.
//                        .color(Color.RED)
//                        // below line is to make our poly line geodesic.
//                        .geodesic(true));
//                // on below line we will be starting the drawing of polyline.
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin1, 13));
//
//            }
//        } catch (Exception e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }

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
                            geocoder = new Geocoder(Tracking_activity.this, Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

//                                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                                city = addresses.get(0).getLocality();
//                                state = addresses.get(0).getAdminArea();
//                                String country = addresses.get(0).getCountryName();
//                                String postalCode = addresses.get(0).getPostalCode();
//                                String knownName = addresses.get(0).getFeatureName(); //

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Tracking_activity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("city", "");
                                editor.commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            final LatLng mDefaultLocation1 = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(mDefaultLocation1));

                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            // drawGeofence();

                            final LatLng origin= new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            final LatLng dest= new LatLng(10.828001748491019, 78.67306261951575);

//                            mMap.addMarker(new MarkerOptions()
//                                    .title("Murugan Store")
//                                    .position(dest)
//                                    .snippet("trichy"));

                           start=origin;
                           end=dest;
                            Findroutes(origin,dest);
//                            mMap.addPolyline((new PolylineOptions()).add(origin,dest).
//                                    // below line is use to specify the width of poly line.
//                                            width(10)
//                                    // below line is use to add color to our poly line.
//                                    .color(Color.RED)
//
//                                    // below line is to make our poly line geodesic.
//                                    .geodesic(true));
//                            // on below line we will be starting the drawing of polyline.
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 13));

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
//    private Geofence createGeofence(LatLng latLng, float radius ) {
//        Log.d(TAG, "createGeofence");
//        return new Geofence.Builder()
//                .setRequestId(GEOFENCE_REQ_ID)
//                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
//                .setExpirationDuration( GEO_DURATION )
//                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
//                        | Geofence.GEOFENCE_TRANSITION_EXIT )
//                .build();
//    }
//    private GeofencingRequest createGeofenceRequest(Geofence geofence ) {
//        Log.d(TAG, "createGeofenceRequest");
//        return new GeofencingRequest.Builder()
//                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
//                .addGeofence( geofence )
//                .build();
//    }
//    private void drawGeofence() {
//        Log.d(TAG, "drawGeofence()");
//
//        if ( geoFenceLimits != null )
//            geoFenceLimits.remove();
//        LatLng latLng=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
//        CircleOptions circleOptions = new CircleOptions()
//                .center( latLng)
//                .strokeColor(Color.argb(50, 70,70,70))
//                .fillColor( Color.argb(100, 150,150,150) )
//                .radius( GEOFENCE_RADIUS );
//        geoFenceLimits = mMap.addCircle( circleOptions );
//    }
    /**
     * Fetch a list of likely places, and show the current place on the map - provided the user
     * has granted location permission.
     */
//    private void pickCurrentPlaceclick(Double lat,Double longg) {
//        if (mMap == null) {
//            return;
//        }
//
//        if (mLocationPermissionGranted) {
//            getDeviceLocationclick(lat,longg);
//        } else {
//            // The user has not granted permission.
//            Log.i(TAG, "The user did not grant location permission.");
//            getDeviceLocationclick(lat,longg);
//            // Add a default marker, because the user hasn't selected a place.
//            mMap.addMarker(new MarkerOptions()
//                    .title(getString(R.string.app_name))
//                    .position(mDefaultLocation)
//                    .snippet(getString(R.string.app_name)));
//
//            // Prompt the user for permission.
//            getLocationPermission();
//        }
//    }


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

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Tracking_activity.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Tracking_activity.this.finish();
        super.onBackPressed();
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(Tracking_activity.this,"Finding Route...",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(Color.RED);
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);

            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Murugan Store");
        mMap.addMarker(endMarker);

    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);

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




    /**
     * A class to parse the Google Places in JSON format
     */


    /**
     * A method to download json data from url
     */

    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(Tracking_activity.this,"Unable to get location",Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyB6pFHUDqYAOYugr7pyj8XaNqxJz3079J8")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }



}