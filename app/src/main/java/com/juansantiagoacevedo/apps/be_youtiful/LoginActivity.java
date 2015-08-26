package com.juansantiagoacevedo.apps.be_youtiful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public Button loginButton;
    public List<String> fbPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void inicializarLayout() {

        fbPermissions = Arrays.asList(getResources().getStringArray(R.array.my_facebook_permissions));

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this,
                        fbPermissions, new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if(parseUser == null) {
                                    Toast.makeText(LoginActivity.this, "Error al iniciar Sesion", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        /**
         Spannable loginButtonLabel = new SpannableString(loginButton.getText());
         loginButtonLabel.setSpan(
         new ImageSpan(
         getApplicationContext(),
         R.drawable.com_facebook_button_icon,
         ImageSpan.ALIGN_BOTTOM),
         0,
         1,
         Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
         loginButton.setText(loginButtonLabel);
         */
    }
}
