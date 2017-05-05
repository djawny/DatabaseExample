package com.offcasoftware.databaseexample.application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.offcasoftware.databaseexample.databse.Database;
import com.offcasoftware.databaseexample.databse.DatabaseImpl;

import android.app.Application;

public class AndroidApplication extends Application {

    private static Database mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabase = OpenHelperManager.getHelper(this, DatabaseImpl.class);
    }

    public static Database getDatabase() {
        return mDatabase;
    }
}
