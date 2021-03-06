package com.juansantiagoacevedo.apps.be_youtiful;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.konifar.fab_transformation.FabTransformation;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public int nItemDrawerSelected;
    public final static int LOGIN_REQUEST = 1;
    public ParseUser currentUser;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    public String email, name;
    public Profile fbProfile;
    public TextView drawer_name, drawer_email;
    CircleImageView mProfileImage;
    public Fragment fragmentoActual;
    public NavigationView mNavigationView;
    public FacebookFragment facebookFragment;
    public InstagramFragment instagramFragment;
    public HomeFragment homeFragment;
    public ReservasFragment reservasFragment;
    FloatingActionButton fabFacebook, fabInstagram, fabReservas;

    //********************************************
    //METODOS POR HERENCIA Y EXTENSION EN OVERRIDE
    //********************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarToolbar();
        inicializarNavDrawer();
        inicializarLayout();
        checkUser();
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
    public void onBackPressed() {
        if(fragmentoActual.equals(facebookFragment)) {
            if(facebookFragment.canDoBack()) {
                facebookFragment.doBack();
            } else {
                FabTransformation.with(fabFacebook).transformFrom(findViewById(R.id.content_fragment_facebook));
                homeFragment = new HomeFragment();
                fragmentoActual = homeFragment;
            }
        } else if(fragmentoActual.equals(instagramFragment)) {
            if(instagramFragment.canDoBack()) {
                instagramFragment.doBack();
            } else {
                FabTransformation.with(fabInstagram).transformFrom(findViewById(R.id.content_fragment_instagram));
                homeFragment = new HomeFragment();
                fragmentoActual = homeFragment;
            }
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULTADO", resultCode+"");
        if(resultCode == 0) {
            finish();
        } else if(resultCode == 2) {
            getDetailsFromFacebook();
        } else if(resultCode == 1) {
            getDetailsFromParse();
        }
    }

    //***************************************
    //METODOS PROPIOS
    //***************************************

    /**
     * Inicializa y configura el ActionBar
     */
    private void checkUser() {
        currentUser = ParseUser.getCurrentUser();
        if(currentUser != null) {
            getDetailsFromParse();
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_REQUEST);
        }
    }

    private void inicializarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.mNavigationView);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        nItemDrawerSelected = 0;
                        //TODO: menuItem.setChecked(mostrarFragmento(0));
                        break;
                    case R.id.navigation_item_2:
                        reservasFragment = new ReservasFragment();
                        fragmentoActual = reservasFragment;
                        mostrarFragmento("reservas");
                        break;
                    case R.id.navigation_item_3:
                        nItemDrawerSelected = 0;
                        logoutCurrentUser();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void logoutCurrentUser() {
        ParseUser.logOut();
        currentUser = null;
        checkUser();
    }

    public void inicializarLayout() {
        drawer_name = (TextView) findViewById(R.id.drawer_name);
        drawer_email = (TextView) findViewById(R.id.drawer_mail);
        mProfileImage = (CircleImageView) findViewById(R.id.drawer_circle_image);

        homeFragment = new HomeFragment();
        fragmentoActual = homeFragment;
        mostrarFragmento("home");

        fabFacebook = (FloatingActionButton) findViewById(R.id.FAB_facebook);
        fabFacebook.setImageDrawable(new IconDrawable(this, Iconify.IconValue.zmdi_facebook).colorRes(android.R.color.white));
        fabFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentoActual != null && fragmentoActual.equals(instagramFragment)) {
                    instagramFragment.activarLoad();
                    FabTransformation.with(fabInstagram)
                            .transformFrom(findViewById(R.id.content_fragment_instagram));
                }
                facebookFragment = new FacebookFragment();
                fragmentoActual = facebookFragment;
                FabTransformation.with(fabFacebook)
                        .transformTo(findViewById(R.id.content_fragment_facebook));
                mostrarFragmento("facebook");
            }
        });

        fabInstagram = (FloatingActionButton) findViewById(R.id.FAB_instagram);
        fabInstagram.setImageDrawable(new IconDrawable(this, Iconify.IconValue.zmdi_instagram).colorRes(android.R.color.white));
        fabInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragmentoActual != null && fragmentoActual.equals(facebookFragment)) {
                    facebookFragment.activarLoad();
                    FabTransformation.with(fabFacebook)
                            .transformFrom(findViewById(R.id.content_fragment_facebook));
                }
                instagramFragment = new InstagramFragment();
                fragmentoActual = instagramFragment;
                FabTransformation.with(fabInstagram)
                        .transformTo(findViewById(R.id.content_fragment_instagram));
                mostrarFragmento("instagram");
            }
        });

        fabReservas = (FloatingActionButton) findViewById(R.id.FAB_reservas);
        fabReservas.setImageDrawable(new IconDrawable(this, Iconify.IconValue.zmdi_plus).colorRes(android.R.color.white));
    }

    public boolean mostrarFragmento(String name) {

        if(name.equals("facebook")) {
            if(fragmentoActual != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment_facebook, fragmentoActual)
                        .commit();
                return true;
            }
        } else if(name.equals("instagram")) {
            if(fragmentoActual != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment_instagram, fragmentoActual)
                        .commit();
                return true;
            }
        } else if(name.equals("home")) {
            if(fragmentoActual != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_fragment_home, fragmentoActual)
                        .commit();
                return true;
            }
        } else if(name.equals("reservas")) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_fragment_home, fragmentoActual)
                    .commit();
            return true;
        }


        return false;
    }

    /**public void onClicReservar(View view) {
        mostrarFragmento(1);
    }

    public void onClicFacebook(View view) {
        mostrarFragmento(2);
    }

    public void onClicInstagram(View view) {
        mostrarFragmento(3);
    }**/

    public void getDetailsFromParse() {
        fbProfile = Profile.getCurrentProfile();
        currentUser = ParseUser.getCurrentUser();
        try {
            ParseFile parseFile = currentUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            mProfileImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        name = currentUser.getUsername();
        drawer_name.setText(name);
        email = currentUser.getEmail();
        if(email != null && !email.equalsIgnoreCase("")) {
            drawer_email.setText(email);
        }
    }

    public void getDetailsFromFacebook() {
        fbProfile = Profile.getCurrentProfile();
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        try {
                            email = graphResponse.getJSONObject().getString("email");
                            if(email != null)
                            {
                                drawer_email.setText(email);
                            }
                            else {
                                drawer_email.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            name = graphResponse.getJSONObject().getString("name");
                        } catch (JSONException a) {
                            a.printStackTrace();
                        }
                        drawer_name.setText(name);
                    }
                }
        ).executeAsync();

        ProfilePhotoAsync profilePhotoAsync = new ProfilePhotoAsync(fbProfile);
        profilePhotoAsync.execute();
    }

    public void saveNewUser() {

        currentUser = ParseUser.getCurrentUser();
        currentUser.setUsername(name);
        if(email != null) {
            currentUser.setEmail(email);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        String thumbName = currentUser.getUsername().replaceAll("\\s+", "")+ "_thumb.jpg";
        ParseFile parseFile = new ParseFile(thumbName, stream.toByteArray());

        try {
            parseFile.save();
            currentUser.put("profileThumb", parseFile);
            currentUser.save();
        } catch (ParseException e) {
            Log.e("ERRORRR", e.getMessage());
        }
        /**parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                }
                currentUser.put("profileThumb", parseFile);
                currentUser.saveInBackground();
            }
        });**/
    }

    public class ProfilePhotoAsync extends AsyncTask<String, String, String> {

        Profile profile;
        public Bitmap bitmap;

        ProfilePhotoAsync(Profile profile) {
            this.profile = profile;
        }

        @Override
        protected String doInBackground(String... strings) {
            bitmap = downloadImageBitmap(profile.getProfilePictureUri(200, 200).toString());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProfileImage.setImageBitmap(bitmap);
            saveNewUser();
        }
    }

    public static Bitmap downloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aUrl = new URL(url);
            URLConnection conn = aUrl.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }

    //***************************************************************
    //INTERFAZ FACEBOOKFRAGMENT
    //***************************************************************
}