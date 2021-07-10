package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register parse model
        ParseObject.registerSubclass(Post.class);

        // Init code
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("KrcEeOoo1uvYl017pQTiNiQCgUmdcM85CUsQT13u")
                .clientKey("G1Rz9LB1nScd1eVgG3IbWiTTugMPczacoY0YJLcT")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
