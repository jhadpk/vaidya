package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by deepak.jha on 24/08/15.
 */
public class Thanks extends Activity {

    private Double latfrmobj;
    private Double lonfrmobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanksforaccepting);
        if(latfrmobj != null && lonfrmobj !=null) {
            latfrmobj = Double.valueOf(getIntent().getExtras().getString("lat"));
            lonfrmobj = Double.valueOf(getIntent().getExtras().getString("lon"));
        }
        else{
            latfrmobj=12.915591;
            lonfrmobj=77.665216;
        }

        TextView share = (TextView) findViewById(R.id.share);

        LinearLayout uber = (LinearLayout) findViewById(R.id.uber);

        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri = "uber://?action=setPickup&&pickup[latitude]=" + latfrmobj + "&pickup[longitude]=" + lonfrmobj + "&pickup[nickname]=BloodDonor";
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Thanks for donating blood through Vaidya.");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "you can download the app from play Store and join the noble cause");
                startActivity(Intent.createChooser(i,"Share via"));
            }
        });
    }
}
