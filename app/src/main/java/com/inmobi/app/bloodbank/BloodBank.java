package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//import com.github.nkzawa.emitter.Emitter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inmobi.app.bloodbank.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

/**
 * Created by deepak.jha on 12/08/15.
 */
public class BloodBank extends Activity {

    protected LocationManager locationManager;
    GoogleMap googlemap;
    Button search;
    Spinner bloodgroup;
    public JSONArray bloodbank;
    protected Context context;
    String bloodgp, phone, name, lat, lon, place;
    Double latitude;
    Double longitude;
    LatLng ll;
    Geocoder geocoder;
    List<Address> addresses;
    public int temp = 0 ;
    //private BloodDonor[] derDonor = new BloodDonor[1000];
    private BloodDonor derDonor = new BloodDonor();

    public static class BloodDonor {
        String name;
        String bloodgp;
        String phone;
        String lat;
        String lon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getApplicationContext());

        final BitmapDescriptor yourlocation = BitmapDescriptorFactory.fromResource(R.drawable.yourlocation32);
        final BitmapDescriptor pending = BitmapDescriptorFactory.fromResource(R.drawable.pending24px);
        final BitmapDescriptor accept = BitmapDescriptorFactory.fromResource(R.drawable.accept32);
        final BitmapDescriptor reject = BitmapDescriptorFactory.fromResource(R.drawable.reject);
        final BitmapDescriptor[] iconList = new BitmapDescriptor[3];
        iconList[0] = pending;
        iconList[1] = accept;
        iconList[2] = reject;
        final EditText units = (EditText) findViewById(R.id.noofunits);

        setContentView(R.layout.gmap);
        googlemap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        geocoder = new Geocoder(this, Locale.getDefault());
        googlemap.setMyLocationEnabled(true);
        googlemap.getUiSettings().setZoomGesturesEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.d("msg", "GPS:" + isGPSEnabled);

        if (isGPSEnabled) {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.d("msg", "first lat long : " + latitude + " " + longitude);
                LatLng latLng = new LatLng(latitude, longitude);
                googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googlemap.animateCamera(CameraUpdateFactory.zoomTo(14));
                String address = "";
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    address = addresses.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                googlemap.addMarker(new MarkerOptions().position(latLng)
                        .title("Your location").snippet(address).icon(yourlocation));
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Log.d("msg", "changed lat long : " + latitude + " " + longitude);
                        LatLng latLng = new LatLng(latitude, longitude);
                        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googlemap.animateCamera(CameraUpdateFactory.zoomTo(14));
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(BloodBank.this, Locale.getDefault());
                        String addresschange = "";

                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            addresschange = addresses.get(0).getAddressLine(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        googlemap.addMarker(new MarkerOptions().position(latLng)
                                .title("Your location").snippet(addresschange).icon(yourlocation));
                    }
                });
            }

        } else {
            System.out.println("GPS is disabled");
        }
        googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        search = (Button) findViewById(R.id.bloodNeed);
        bloodgroup = (Spinner) findViewById(R.id.bloodsearch);


        String strblood = "[{\"bloodgp\":\"O+\",\"name\":\"Rajesh\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"O+\",\"name\":\"NameB\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"O+\",\"name\":\"Krishna\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"O+\",\"name\":\"Kevin\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"O+\",\"name\":\"James\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"O+\",\"name\":\"Ricky\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"},{\"bloodgp\":\"A+\",\"name\":\"Senthil\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"A+\",\"name\":\"Sanjay\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"A+\",\"name\":\"Krishna\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"A+\",\"name\":\"Naman\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"A+\",\"name\":\"Neelam\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"A+\",\"name\":\"Behera\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"},{\"bloodgp\":\"B+\",\"name\":\"Sudhanshu\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"B+\",\"name\":\"Andrew\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"B+\",\"name\":\"Devil\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"B+\",\"name\":\"Naveen\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"B+\",\"name\":\"Mark\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"B+\",\"name\":\"Sujay\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"},{\"bloodgp\":\"AB+\",\"name\":\"Sujoy\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"AB+\",\"name\":\"Krishh\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"AB+\",\"name\":\"Soumya\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"AB+\",\"name\":\"Raj\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"AB+\",\"name\":\"Rahul\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"AB+\",\"name\":\"Akshay\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"},{\"bloodgp\":\"AB-\",\"name\":\"Dhirshya\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"AB-\",\"name\":\"Kevin\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"AB-\",\"name\":\"Khanna\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"AB-\",\"name\":\"Ramesh\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"AB-\",\"name\":\"Suresh\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"AB-\",\"name\":\"Jatt\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"},{\"bloodgp\":\"O-\",\"name\":\"Jimmy\",\"phone\":\"9986882536\",\"lat\":\"12.926054\",\"lon\":\"77.680721\",\"place\":\"Near RMZ Ecospace\"},{\"bloodgp\":\"O-\",\"name\":\"Tommy\",\"phone\":\"9986882536\",\"lat\":\"12.956562\",\"lon\":\"77.700599\",\"place\":\"Near Marathahalli\"},{\"bloodgp\":\"O-\",\"name\":\"Khurana\",\"phone\":\"9986882536\",\"lat\":\"12.934550\",\"lon\":\"77.677070\",\"place\":\"Near Bellandur Lake\"},{\"bloodgp\":\"O-\",\"name\":\"Javed\",\"phone\":\"9159884920\",\"lat\":\"12.955256\",\"lon\":\"77.681537\",\"place\":\"Near HAL\"},{\"bloodgp\":\"O-\",\"name\":\"Mukhtar\",\"phone\":\"9883659157\",\"lat\":\"12.939388\",\"lon\":\"77.703726\",\"place\":\"Near Coconut plantation\"},{\"bloodgp\":\"O-\",\"name\":\"Sreekanth\",\"phone\":\"9159884920\",\"lat\":\"12.912827\",\"lon\":\"77.652995\",\"place\":\"Near NIFT\"}]\";";
        try {
            bloodbank = new JSONArray(strblood);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification(view,bloodgroup.getSelectedItem().toString());
                JSONObject person = new JSONObject();
                for (int i = 0; i < bloodbank.length(); i++) {
                    BloodDonor donor = new BloodDonor();
                    try {
                        person = bloodbank.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        donor.bloodgp = person.getString("bloodgp");
                        Log.d("The blood group is " + bloodgp, "Message");
                        System.out.println("The blood group is " + bloodgp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("The bloodgroup is " + bloodgroup, "Message1");
                    System.out.println("The bloodgroup is " + bloodgroup.getSelectedItem().toString());
                    if (donor.bloodgp.equals(bloodgroup.getSelectedItem().toString())) {
                        try {
                            donor.phone = person.getString("phone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            donor.name = person.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            donor.lat = person.getString("lat");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            donor.lon = person.getString("lon");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        grepuser(donor);
                        ll = new LatLng(Double.valueOf(donor.lat), Double.valueOf(donor.lon));
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(BloodBank.this, Locale.getDefault());
                        String addressdonor = "";

                        try {
                            addresses = geocoder.getFromLocation(Double.valueOf(donor.lat), Double.valueOf(donor.lon), 1);
                            addressdonor = addresses.get(0).getAddressLine(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        googlemap.moveCamera(CameraUpdateFactory.newLatLng(ll));
                        googlemap.animateCamera(CameraUpdateFactory.zoomTo(14));
                        googlemap.addMarker(new MarkerOptions().position(ll).title(name).snippet(addressdonor).icon(iconList[i % 3]));

                    }
                }
                googlemap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                    @Override
                    public boolean onMarkerClick(Marker arg0) {
                        Intent nextpage = new Intent(getApplicationContext(), ContactHelp.class);
                        nextpage.putExtra("name", derDonor.name);

                        nextpage.putExtra("bloodgp", derDonor.bloodgp);
                        nextpage.putExtra("phone", derDonor.phone);
                        nextpage.putExtra("lat", derDonor.lat);
                        nextpage.putExtra("lon", derDonor.lon);
                        nextpage.putExtra("address", arg0.getSnippet());
                        /*for(int i = 0; i<derDonor.length;i++) {
                            nextpage.putExtra("name", derDonor[i].name);

                            nextpage.putExtra("bloodgp", derDonor[i].bloodgp);
                            nextpage.putExtra("phone", derDonor[i].phone);
                            nextpage.putExtra("lat", derDonor[i].lat);
                            nextpage.putExtra("lon", derDonor[i].lon);
                            nextpage.putExtra("address", arg0.getSnippet());
                        }*/
                        startActivity(nextpage);
                        Toast.makeText(BloodBank.this, arg0.getSnippet(), Toast.LENGTH_SHORT).show();
                        return true;
                    }

                });

            }
        });


    }

    public void grepuser(BloodDonor donor) {
        derDonor.bloodgp = donor.bloodgp;
        derDonor.name = donor.name;
        derDonor.phone = donor.phone;
        derDonor.lat = donor.lat;
        derDonor.lon = donor.lon;
        /*derDonor[temp].bloodgp = donor.bloodgp;
        derDonor[temp].name = donor.name;
        derDonor[temp].phone = donor.phone;
        derDonor[temp].lat = donor.lat;
        derDonor[temp].lon = donor.lon;
temp++;*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void createNotification(View view, String bloodtosearch) {

        Intent intent = new Intent(this, BloodBank.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.bloodbank)
                        .setContentTitle(bloodtosearch + " Blood Required")
                        .setContentText("Please help Morph to save life")
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission

                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Help"))
                        .addAction(R.drawable.yes2,
                                "Accept", pIntent)
                        .addAction(R.drawable.no1,
                                "Can not make it", pIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }


}