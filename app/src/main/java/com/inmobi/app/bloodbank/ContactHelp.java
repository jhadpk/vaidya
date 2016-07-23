package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inmobi.app.bloodbank.R;

import retrofit.http.GET;

/**
 * Created by deepak.jha on 13/08/15.
 */
public class ContactHelp extends Activity{


    private String namefrmobj;
    private String bloodgpfrmobj;
    private String phonefrmobj;
    private String latfrmobj;
    private String lonfrmobj;
    private String addressfrmobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactdonor);
        System.out.println("The name from object is "+  getIntent().getExtras().getString("name"));
        namefrmobj = getIntent().getExtras().getString("name");
        bloodgpfrmobj = getIntent().getExtras().getString("bloodgp");
        phonefrmobj = getIntent().getExtras().getString("phone");
        latfrmobj = getIntent().getExtras().getString("lat");
        lonfrmobj = getIntent().getExtras().getString("lon");
        addressfrmobj = getIntent().getExtras().getString("address");

        TextView name = (TextView) findViewById(R.id.name);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView bloodgp = (TextView) findViewById(R.id.bloodgp);
        TextView address = (TextView) findViewById(R.id.address);
        Button contact = (Button) findViewById(R.id.contact);
        LinearLayout uber = (LinearLayout) findViewById(R.id.uberlayout);

        name.setText(namefrmobj);
        phone.setText(phonefrmobj);
        bloodgp.setText(bloodgpfrmobj);
        address.setText(addressfrmobj);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phonefrmobj));
                    startActivity(callIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Cant connect to " + phonefrmobj, Toast.LENGTH_SHORT).show();
                }
            }
        });

        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri = "uber://?action=setPickup&&pickup[latitude]="+latfrmobj+"&pickup[longitude]="+lonfrmobj+"&pickup[nickname]=BloodDonor";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    // No Uber app! Open Mobile Website.
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.uber.com/sign-up?client_id=YOUR_CLIENT_ID"));
                    startActivity(browserIntent);
                }
            }
        });
    }
}
