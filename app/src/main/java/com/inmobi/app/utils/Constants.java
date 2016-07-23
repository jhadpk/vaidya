package com.inmobi.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inmobi.app.bloodbank.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    private static HashMap<String, String> authParameters = new HashMap<String, String>();

    public static final String AUTHORIZE_URL = "https://login.uber.com/oauth/authorize";
    public static final String BASE_URL = "https://login.uber.com/";
    public static final String SCOPES = "profile history_lite history";
    public static final String BASE_UBER_URL_V1 = "https://api.uber.com/v1/";
    public static final String BASE_UBER_URL_V1_1 = "https://api.uber.com/v1.1/";
    public static final double START_LATITUDE = 37.781955;
    public static final double START_LONGITUDE = -122.402367;
    public static final double END_LATITUDE = 37.744352;
    public static final double END_LONGITUDE = -122.416743;
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String getUberClientId(Activity activity) {
        return getManifestData(activity, "com.inmobi.app.utils.UBER_CLIENT_ID");
    }

    public static String getUberClientSecret(Activity activity) {
        return getManifestData(activity, "com.inmobi.app.utils.UBER_CLIENT_SECRET");
    }

    public static String getUberRedirectUrl(Activity activity) {
        return getManifestData(activity, "com.inmobi.app.utils.UBER_REDIRECT_URL");
    }

    public static String getManifestData(Activity activity, String name) {
        String data = authParameters.get(name);
        if (data != null) {
            return data;
        }
        try {
            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            data = bundle.getString(name);
            authParameters.put(name, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static class DemoActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

        public static void start(Context context, String accessToken, String tokenType) {
            Intent intent = new Intent(context, DemoActivity.class);
            intent.putExtra("access_token", accessToken);
            intent.putExtra("token_type", tokenType);
            context.startActivity(intent);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list);

            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setOnItemClickListener(this);
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getOptionsList()));
        }

        private List<String> getOptionsList() {
            List<String> options = new ArrayList<String>();
//            options.add(getString(R.string.demo_list_header_text, getIntent().getStringExtra("access_token")));
//            options.add(getString(R.string.products));
//            options.add(getString(R.string.time_estimates));
//            options.add(getString(R.string.price_estimates));
//            options.add(getString(R.string.history_v1));
//            options.add(getString(R.string.history_v1_1));
//            options.add(getString(R.string.me));
            return options;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EndpointActivity.start(this, position,
                    getIntent().getStringExtra("access_token"),
                    getIntent().getStringExtra("token_type"));
        }

    }
}