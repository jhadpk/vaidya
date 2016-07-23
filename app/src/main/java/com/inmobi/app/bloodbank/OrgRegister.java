package com.inmobi.app.bloodbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inmobi.app.bloodbank.R;
import com.inmobi.app.model.OrganizationData;
import com.inmobi.app.utils.MorphWebServiceUtility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.api.json.JSONConfiguration;


public class OrgRegister extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_register);

        final EditText name = (EditText) findViewById(R.id.reg_fullname);
        final EditText phone = (EditText) findViewById(R.id.reg_phone);
        final Spinner city = (Spinner) findViewById(R.id.orgnization_city);
        final EditText address = (EditText) findViewById(R.id.edit_address);
        final EditText email = (EditText) findViewById(R.id.reg_email);
        final EditText Password = (EditText) findViewById(R.id.reg_password);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrganizationData organizationData = new OrganizationData(name.getText().toString(),phone.getText().toString(),city.getSelectedItem().toString(),
                        address.getText().toString(),email.getText().toString(),Password.getText().toString());
                //MorphWebServiceUtility.saveOrgDetails(organizationData);
             //   MorphWebServiceUtility morphWebServiceUtility = new MorphWebServiceUtility();
               // morphWebServiceUtility.test(organizationData);

                if ((!name.getText().toString().equals("")) && (!phone.getText().toString().equals(""))
                        && (!email.getText().toString().equals("")) && (!Password.getText().toString().equals(""))) {
                    if (!email.getText().toString().contains("@") || !email.getText().toString().contains(".")) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid email id", Toast.LENGTH_SHORT).show();
                    } else if (phone.getText().toString().length() != 10) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        // Make a webservice call to store data

                    //    new MyAsyncTask().execute("Welcome");

                        Toast.makeText(getApplicationContext(),
                                "Successfully Registered!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }

                //Ask webservice to store the data in DB.
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.d("Krishna", "Doing in backgroud");
           postData("Welcome");

            return null;
        }

        protected void onPostExecute(Double result){

            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }
        protected void onProgressUpdate(Integer... progress){

        }

        public void postData(String valueIWantToSend) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.2.5:8081/MorphWebService/morph/validate");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                Log.d("Krishna",""+ response.getStatusLine());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }

//        public void postDateJersey(){
//            try {
//                OrganizationData organizationData
//                        = new OrganizationData("Krishna", "9986882536", "Bangalore",
//                        "Bellandur", "kk@gmail", "jaimaa");
//                ClientConfig clientConfig = new DefaultClientConfig();
//
//                clientConfig.getFeatures().put(
//                        JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//
//                Client client = Client.create(clientConfig);
//
//                WebResource webResource = client
//                        .resource("http://localhost:9090/JerseyJSONExample/rest/jsonServices/send");
//
//                ClientResponse response = webResource.accept("application/json")
//                        .type("application/json").post(ClientResponse.class, organizationData);
//
//                if (response.getStatus() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + response.getStatus());
//                }
//
//                String output = response.getEntity(String.class);
//
//                System.out.println("Server response .... \n");
//                System.out.println(output);
//
//            } catch (Exception e) {
//
//                e.printStackTrace();
//
//            }
//        }

    }
}
