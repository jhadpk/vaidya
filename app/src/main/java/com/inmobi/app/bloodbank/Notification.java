package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by deepak.jha on 23/08/15.
 */
public class Notification extends Activity implements LocationListener {

    private LocationManager locationManager;
    private String provider;
    private Donor donorobj = new Donor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        addListenerOnChk();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            System.out.println("Location not available");
        }

    }

    public void addListenerOnChk() {
        final CheckBox flu = (CheckBox) findViewById(R.id.flu);
        final CheckBox pregnant = (CheckBox) findViewById(R.id.pregnantcheck);
        final CheckBox diabetes = (CheckBox) findViewById(R.id.diabetes);
        final CheckBox cancer = (CheckBox) findViewById(R.id.cancer);
        final CheckBox surgery = (CheckBox) findViewById(R.id.surgery);
        final CheckBox malaria = (CheckBox) findViewById(R.id.malaria);
        final CheckBox antibiotics = (CheckBox) findViewById(R.id.antibiotics);
        final CheckBox military = (CheckBox) findViewById(R.id.military);
        final Button reject = (Button) findViewById(R.id.reject);
        final Button accept = (Button) findViewById(R.id.accept);


        flu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        pregnant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        surgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        antibiotics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        malaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });
        military.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if ((flu.isChecked())&&(pregnant.isChecked())&&(diabetes.isChecked())&&(cancer.isChecked())
                        &&(surgery.isChecked())&&(malaria.isChecked())&&(antibiotics.isChecked())
                        &&(military.isChecked())) {
                    accept.setVisibility(View.VISIBLE);
                }

            }
        });

accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent thanks = new Intent(getApplicationContext(),Thanks.class);
        thanks.putExtra("lat", String.valueOf(donorobj.lat));
        thanks.putExtra("lon",String.valueOf(donorobj.lon));
        startActivity(thanks);
    }
});
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1,this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }



    @Override
    public void onLocationChanged(Location location) {
        donorobj.lat = location.getLatitude();
        donorobj.lon = location.getLongitude();
        Toast.makeText(Notification.this,"Your lat is "+donorobj.lat + " lon is "+donorobj.lon , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    public class Donor {
        String phone;
        Double lat;
        Double lon;
    }
}