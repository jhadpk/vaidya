package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by deepak.jha on 06/08/15.
 */
public class Register extends Activity {

    String dbname, dbphone, dbblood, dbemail, dbweight, dbblooddonortype, dbpregnant, dbgender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText name = (EditText) findViewById(R.id.reg_fullname);
        final EditText phone = (EditText) findViewById(R.id.reg_phone);
        final Spinner blood = (Spinner) findViewById(R.id.blood);
        final EditText email = (EditText) findViewById(R.id.reg_email);
        final RadioButton radiomale = (RadioButton) findViewById(R.id.radioMale);
        final CheckBox blooddonor = (CheckBox) findViewById(R.id.blooddonor);
        final CheckBox plasma = (CheckBox) findViewById(R.id.plasma);
        final CheckBox bonemarrow = (CheckBox) findViewById(R.id.bonemarrow);
        final EditText weight = (EditText) findViewById(R.id.weight);
        final TextView content = (TextView) findViewById(R.id.content);


        //final EditText Password = (EditText) findViewById(R.id.reg_password);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        phone.setRawInputType(InputType.TYPE_CLASS_PHONE);
        weight.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        checkpregnant();
        final RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        int selectedgender = radioSexGroup.getCheckedRadioButtonId();
        final RadioButton radioSexButton = (RadioButton) findViewById(selectedgender);
        ;
        final CheckBox pregnant = (CheckBox) findViewById((R.id.pregnant));
        final String gender = radioSexButton.getText().toString();
        String blooddonortype = "";
        System.out.println("Blood is " + blooddonor.isChecked());
        System.out.println("Plasma is " + plasma.isChecked());
        System.out.println("Bone marrow is " + bonemarrow.isChecked());


        if (blooddonor.isChecked() && plasma.isChecked() && bonemarrow.isChecked()) {
            blooddonortype = "Blood, Plasma and Bonemarrow";
        } else if (blooddonor.isChecked() && plasma.isChecked()) {
            blooddonortype = "Blood and Plasma";
        } else if (blooddonor.isChecked() && bonemarrow.isChecked()) {
            blooddonortype = "Blood and Bonemarrow";
        } else if (plasma.isChecked() && bonemarrow.isChecked()) {
            blooddonortype = "Plasma and Bonemarrow";
        } else if (blooddonor.isChecked() && !plasma.isChecked() && !bonemarrow.isChecked()) {
            blooddonortype = "Blood";
        } else if (!blooddonor.isChecked() && plasma.isChecked() && !bonemarrow.isChecked()) {
            blooddonortype = "Plasma";
        } else if (!blooddonor.isChecked() && !plasma.isChecked() && bonemarrow.isChecked()) {
            blooddonortype = "Bonemarrow";
        }

        //final String finalBlooddonortype = blooddonortype;
        final String finalBlooddonortype = blooddonortype;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((!name.getText().toString().equals("")) && (!phone.getText().toString().equals(""))
                        && (!email.getText().toString().equals(""))) {
                    if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid email id", Toast.LENGTH_SHORT).show();
                    } else if (phone.getText().toString().length() != 10) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        DBData db = new DBData(Register.this);
                        Toast.makeText(getApplicationContext(),
                                "Registering you...", Toast.LENGTH_SHORT).show();
                        System.out.println("Weight is " + weight.getText().toString());
                        System.out.println("Donor type is " + finalBlooddonortype);
                        System.out.println("Pregnant is " + pregnant.isChecked());
                        System.out.println("Gender next is " + gender);


                        try {
                            dbname = URLEncoder.encode(name.getText().toString(), "UTF-8");
                            dbphone = URLEncoder.encode(phone.getText().toString(), "UTF-8");
                            dbblood = URLEncoder.encode(blood.getSelectedItem().toString(), "UTF-8");
                            dbemail = URLEncoder.encode(email.getText().toString(), "UTF-8");
                            dbweight = URLEncoder.encode(weight.getText().toString(), "UTF-8");
                            dbblooddonortype = URLEncoder.encode(finalBlooddonortype, "UTF-8");
                            dbgender = URLEncoder.encode(gender, "UTF-8");
                            dbpregnant = URLEncoder.encode(String.valueOf(pregnant.isChecked()), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }


                        // Create http cliient object to send request to server

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        HttpClient Client = new DefaultHttpClient();
                        String URL = "http://localhost:8888/register?name="
                                + dbname + "&phone=" + dbphone + "&blood=" + dbblood + "&email=" + dbemail + "&weight=" + dbweight +
                                "&donortype=" + dbblooddonortype + "&gender=" + dbgender + "&pregnant=" + dbpregnant;

                        System.out.println("The register url is " + URL);
                        try {
                            String SetServerString = "";
                            HttpGet httpget = new HttpGet(URL);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            System.out.println("Response handler is : " + responseHandler);
                            System.out.println("Before executing register");
                            System.out.println("HTTPGET is "+ httpget);
                            System.out.println("HTTPGET is "+ httpget.toString());
                            Client.execute(httpget);
                            //Client.execute(httpget, responseHandler);
                            System.out.println("After executing register");

                            content.setText(SetServerString);
                        } catch (Exception ex) {
                            //content.setText("Fail!");
                            System.out.println("Exception is "+ex);
                            System.out.println("Into catch for http request");
                        }

                        db.insertdata(name.getText().toString(), phone.getText().toString(),
                                blood.getSelectedItem().toString(), email.getText().toString(), weight.getText().toString(),
                                finalBlooddonortype, pregnant.isChecked(), gender);
                        if (DBData.insertsuccessful == 1) {
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Registered!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }

    public void checkpregnant() {
        System.out.println("Into check pregnant function");
        final RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);


        final CheckBox pregnant = (CheckBox) findViewById((R.id.pregnant));

        radioSexGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedgender = radioSexGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton =(RadioButton) findViewById(selectedgender);

                System.out.println("Radiosexbutton getText is " + radioSexButton.getText());

                if (radioSexButton.getText() == "Female") {
                    System.out.println("Female is selected");
                    pregnant.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}


