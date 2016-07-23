package com.inmobi.app.bloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inmobi.app.MainWindowActivity;


public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText phone = (EditText) findViewById(R.id.phone);
        //final EditText Password = (EditText) findViewById(R.id.password);
        phone.setRawInputType(InputType.TYPE_CLASS_PHONE);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = phone.getText().toString();
                //String password = Password.getText().toString();
                try{
                    if(username.length() > 0)
                    {
                        DBData dbUser = new DBData(Login.this);
                        dbUser.open();

                        if(dbUser.Login(username)) {
                            Intent mainwindow = new Intent(getApplicationContext(), MainWindowActivity.class);
                            Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            startActivity(mainwindow);
                        }else{
                            Toast.makeText(Login.this,"Invalid Username/Password", Toast.LENGTH_LONG).show();
                        }
                        dbUser.close();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

        });

        TextView linktoregister = (TextView) findViewById(R.id.link_to_register);

        linktoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}