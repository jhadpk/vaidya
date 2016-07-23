package com.inmobi.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.inmobi.app.bloodbank.BloodBank;
import com.inmobi.app.bloodbank.R;
import com.inmobi.app.medicalcenters.GoogleHospitalsActivity;
import com.inmobi.app.reports.ListReportActivity;
import com.inmobi.app.utils.CustomGrid;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;

import java.util.Map;

public class MainWindowActivity extends ActionBarActivity {

    GridView grid;
    String[] web = {
            "Blood Bank",
            "My Reports",
            "Medical Centers",
            "Statistics"

    } ;
    int[] imageId = {
            R.drawable.needblood,
            R.drawable.myreports,
            R.drawable.mediassist,
            R.drawable.shop
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InMobi.initialize(this, "f6096125ce64465eb751da358c21ce3d");
//        IMBanner banner = (IMBanner) findViewById(R.id.banner);
//        banner.loadBanner();
//        banner.setIMBannerListener(new IMBannerListener() {
//            @Override
//            public void onShowBannerScreen(IMBanner arg0) {
//            }
//
//            @Override
//            public void onLeaveApplication(IMBanner arg0) {
//            }
//
//            @Override
//            public void onDismissBannerScreen(IMBanner arg0) {
//            }
//
//            @Override
//            public void onBannerRequestFailed(IMBanner banner, IMErrorCode errorCode) {
//                Log.d("Unsuccessful", "Ad");
//                Context context = getApplicationContext();
//                CharSequence text = "Waiting for Ad!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//
//            }
//
//            @Override
//            public void onBannerRequestSucceeded(IMBanner arg0) {
//                Log.d("Successful", "Ad");
//            }
//
//            @Override
//            public void onBannerInteraction(IMBanner arg0, Map<String, String> arg1) {
//                Log.d("Successful", "Interaction");
//            }
//        });
//        banner.setRefreshInterval(1);
        CustomGrid adapter = new CustomGrid(MainWindowActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setBackgroundColor(Color.WHITE);
        grid.setVerticalSpacing(1);
        grid.setHorizontalSpacing(1);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(MainWindowActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

                if(web[position].equalsIgnoreCase("Blood Bank")){
                    Intent bloodbank = new Intent(getApplicationContext(), BloodBank.class);
                    startActivity(bloodbank);
                } else if(web[position].equalsIgnoreCase("My Reports")){
                    Intent myreports = new Intent(getApplicationContext(), ListReportActivity.class);
                    startActivity(myreports);

                }else if(web[position].equalsIgnoreCase("Medical Centers")){
                    Intent medical = new Intent(getApplicationContext(), GoogleHospitalsActivity.class);
                    startActivity(medical);
                }else if(web[position].equalsIgnoreCase("Statistics")){
                    Toast.makeText(MainWindowActivity.this, "This section will display Statistics in new future" , Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
