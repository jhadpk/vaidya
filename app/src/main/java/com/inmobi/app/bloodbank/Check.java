package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inmobi.app.bloodbank.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by deepak.jha on 16/08/15.
 */
public class Check extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check);

        TextView linktoindividual = (TextView) findViewById(R.id.link_to_individual);

        TextView linktoorg = (TextView) findViewById(R.id.link_to_organisation);

        linktoindividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });

        linktoorg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OrgRegister.class);
                startActivity(i);
            }
        });

    }
}