package com.example.mototaxi.dashboaddata;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.mototaxi.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class home extends Fragment implements OnMapReadyCallback , GoogleApiClient.OnConnectionFailedListener , RoutingListener {
    MapView mapView;
    GoogleMap map;
    private home context;
    LinearLayout linear_vehicle_list, linear_schedule, linear_payments, linear_main_types,
            linear_trip, linear_tripevent, linear_paymentevent, linear_scheduleevent, linear_vehicleevent, linear_tripdetails;
    TextView btn_Continue, btn_book, btn_Continue_vehicle, btn_Continue_schedule, btn_Continue_payment;
    EditText et_pickup, et_drop;
    View displayschedule;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private List<Polyline> polylines = null;
    protected LatLng start = null;
    protected LatLng end = null;
    private Circle geoFenceLimits;
    private static final float GEOFENCE_RADIUS = 1000.0f; // in meters

    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        mapView = v.findViewById(R.id.map_id);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        context = this;
        MapsInitializer.initialize(this.getContext());
        return v;
    }

    private void initView(View v) {
        linear_main_types = v.findViewById(R.id.linear_main_types);
        linear_payments = v.findViewById(R.id.linear_payments);
        linear_schedule = v.findViewById(R.id.linear_schedule);
        linear_tripevent = v.findViewById(R.id.linear_tripevent);
        linear_paymentevent = v.findViewById(R.id.linear_paymentevent);
        linear_scheduleevent = v.findViewById(R.id.linear_scheduleevent);
        linear_vehicleevent = v.findViewById(R.id.linear_vehicleevent);
        linear_vehicle_list = v.findViewById(R.id.linear_vehicle_list);
        linear_tripdetails = v.findViewById(R.id.linear_tripdetails);
        et_pickup = v.findViewById(R.id.et_pickup);
        et_drop = v.findViewById(R.id.et_drop);
        btn_Continue = v.findViewById(R.id.btn_Continue);
        btn_book = v.findViewById(R.id.btn_book);
        btn_Continue_vehicle = v.findViewById(R.id.btn_Continue_vehicle);
        btn_Continue_schedule = v.findViewById(R.id.btn_Continue_schedule);
        btn_Continue_payment = v.findViewById(R.id.btn_Continue_payment);
        displayschedule = v.findViewById(R.id.displayschedule);

        linear_trip = v.findViewById(R.id.linear_trip);
        linear_payments.setVisibility(View.GONE);
        linear_schedule.setVisibility(View.GONE);
        linear_vehicle_list.setVisibility(View.GONE);
        linear_trip.setVisibility(View.VISIBLE);
        linear_tripdetails.setVisibility(View.GONE);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                pickCurrentPlace();
                // this code will be executed after 2 seconds
            }
        }, 3000);


        displayschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SingleDateAndTimePickerDialog.Builder(getContext())
                        //.bottomSheet()
                        //.curved()
                        //.stepSizeMinutes(15)
                        //.displayHours(false)
                        //.displayMinutes(false)
                        //.todayText("aujourd'hui")
                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                            @Override
                            public void onDisplayed(SingleDateAndTimePicker picker) {
                                // Retrieve the SingleDateAndTimePicker
                            }
                        })
                        .title("Simple")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {

                            }
                        }).display();

            }
        });

        et_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectAddressMapsFragment nextFrag = new SelectAddressMapsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        et_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.GONE);
                linear_payments.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.VISIBLE);
            }
        });
        btn_Continue_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_payments.setVisibility(View.VISIBLE);
            }
        });
        btn_Continue_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_payments.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.VISIBLE);
            }
        });
        btn_Continue_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_trip.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_payments.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.VISIBLE);
            }
        });
        linear_paymentevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_payments.setVisibility(View.VISIBLE);
                linear_schedule.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
            }
        });
        linear_scheduleevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_payments.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.VISIBLE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
            }
        });
        linear_vehicleevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_payments.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.VISIBLE);
                linear_trip.setVisibility(View.GONE);
                linear_tripdetails.setVisibility(View.GONE);
            }
        });
        linear_tripevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear_payments.setVisibility(View.GONE);
                linear_schedule.setVisibility(View.GONE);
                linear_vehicle_list.setVisibility(View.GONE);
                linear_trip.setVisibility(View.VISIBLE);
                linear_tripdetails.setVisibility(View.GONE);
            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitateDriverFragment nextFrag = new InitateDriverFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        //  map.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
//        map.animateCamera(cameraUpdate);
        getLocationPermission();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pickCurrentPlaceclick(latLng.latitude,latLng.longitude);
            }
        });
    }

    private void pickCurrentPlaceclick(Double lat,Double longg) {
        if (map == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocationclick(lat,longg);
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocationclick(lat,longg);
            // Add a default marker, because the user hasn't selected a place.
            map.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    private void getDeviceLocationclick(Double lat,Double lon) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                geocoder = new Geocoder(getContext(), Locale.getDefault());

                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); //

                final LatLng mDefaultLocation1 = new LatLng(lat, lon);
                map.clear();
                map.addMarker(new MarkerOptions()
                        .title(city)
                        .position(mDefaultLocation1)
                        .snippet(state));


                final LatLng origin = new LatLng(lat, lon);
                final LatLng dest = new LatLng(10.828001748491019, 78.67306261951575);

//                            mMap.addMarker(new MarkerOptions()
//                                    .title("Murugan Store")
//                                    .position(dest)
//                                    .snippet("trichy"));

                drawGeofence();

                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);

                final LatLng mDefaultLocation122= new LatLng(10.828232, 78.683240);

                final LatLng mDefaultLocation12= new LatLng(10.832169, 78.681406);
                map.addMarker(new MarkerOptions()
                        .title("TN 57 4598494")
                        .icon(icon)
                        .position(mDefaultLocation12)
                        .snippet("trichy"));

                map.addMarker(new MarkerOptions()
                        .title("TN 28 5409665")
                        .icon(icon)

                        .position(mDefaultLocation122)
                        .snippet("trichy"));

                start = origin;
                end = dest;
                Findroutes(origin, dest);
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
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                locationResult.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = location;

                            Geocoder geocoder;
                            List<Address> addresses = null;
                            geocoder = new Geocoder(getContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

//                                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                                city = addresses.get(0).getLocality();
//                                state = addresses.get(0).getAdminArea();
//                                String country = addresses.get(0).getCountryName();
//                                String postalCode = addresses.get(0).getPostalCode();
//                                String knownName = addresses.get(0).getFeatureName(); //

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            // drawGeofence();

                            final LatLng origin = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            final LatLng dest = new LatLng(10.828001748491019, 78.67306261951575);

//                            mMap.addMarker(new MarkerOptions()
//                                    .title("Murugan Store")
//                                    .position(dest)
//                                    .snippet("trichy"));

                            drawGeofence();

                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);

                            final LatLng mDefaultLocation122= new LatLng(10.828232, 78.683240);

                            final LatLng mDefaultLocation12= new LatLng(10.832169, 78.681406);
                            map.addMarker(new MarkerOptions()
                                    .title("TN 57 4598494")
                                            .icon(icon)
                                    .position(mDefaultLocation12)
                                    .snippet("trichy"));

                            map.addMarker(new MarkerOptions()
                                    .title("TN 28 5409665")
                                    .icon(icon)

                                    .position(mDefaultLocation122)
                                    .snippet("trichy"));

                            start = origin;
                            end = dest;
                            Findroutes(origin, dest);
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
                            map.moveCamera(CameraUpdateFactory
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
        geoFenceLimits = map.addCircle( circleOptions );
    }

    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

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


    private void pickCurrentPlace() {
        if (map == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");
            getDeviceLocation();
            // Add a default marker, because the user hasn't selected a place.
            map.addMarker(new MarkerOptions()
                    .title(getString(R.string.app_name))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.app_name)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = getView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(getContext(), "Finding Route...", Toast.LENGTH_LONG).show();


    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(Color.RED);
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = map.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        map.addMarker(startMarker);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);
        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("TN 45 48954949");
        endMarker.icon(icon);
        map.addMarker(endMarker);

    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start, end);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);
    }
}