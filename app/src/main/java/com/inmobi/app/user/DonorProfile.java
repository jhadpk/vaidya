package com.inmobi.app.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inmobi.app.bloodbank.BloodBank;
import com.inmobi.app.bloodbank.R;

public class DonorProfile extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        findViewById(R.id.seachbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bloodbank = new Intent(getApplicationContext(), BloodBank.class);
                startActivity(bloodbank);
            }
        });

        findViewById(R.id.modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(view);
            }
        });
    }

    protected String showInputDialog(final View view) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(DonorProfile.this);
        View promptView = layoutInflater.inflate(R.layout.profile_modify, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DonorProfile.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(DonorProfile.this, "We are working :  ::" +
                                "You can change your status anytime", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return editText.getText().toString();
    }
}
