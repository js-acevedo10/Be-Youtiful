package com.juansantiagoacevedo.apps.be_youtiful;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.appevents.AppEventsLogger;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public final static int LOGIN_REQUEST = 1;
    public ParseUser currentUser;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    //********************************************
    //METODOS POR HERENCIA Y EXTENSION EN OVERRIDE
    //********************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarToolbar();
        inicializarNavDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {

        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_REQUEST);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1) {
            finish();
        }
    }

    //***************************************
    //METODOS PROPIOS
    //***************************************

    /**
     * Inicializa y configura el ActionBar
     */
    private void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void inicializarNavDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(toolbar != null)  {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }
}
