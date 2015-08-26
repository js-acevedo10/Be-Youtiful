package com.juansantiagoacevedo.apps.be_youtiful;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by JuanSantiagoAcev on 8/25/15.
 */
public class MyApp extends Application{

    private static final String APPLICATION_ID = "ZbMMj2lWc5bVu87YXEmoqa6dxJVosF1tDQIwK3o1";
    private static final String CLIENT_KEY = "F78GsDpXRvW1p3bO1iXT0lrFQpJJTpcCXtdFk7c9";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseFacebookUtils.initialize(this);
    }
}
