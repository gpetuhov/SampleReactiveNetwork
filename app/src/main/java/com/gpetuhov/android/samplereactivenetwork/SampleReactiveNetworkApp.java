package com.gpetuhov.android.samplereactivenetwork;

import android.app.Application;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SampleReactiveNetworkApp extends Application {

    public static final String LOG_TAG = SampleReactiveNetworkApp.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        // Observe network connectivity (not Internet).
        // It is recommended to use application context.
        ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        connectivity -> {
                            // This will run once on the moment of subscribe
                            // and then every time network connection state changes.
                            Log.d(LOG_TAG, "Network state is " + connectivity.getState().name());
                        }
                );

        // Observe Internet connection
        // This method will connect to google.com EVERY 2 SECONDS!
        // To reduce traffic we may save this subscription
        // and unsubscribe from it as soon as we check Internet state and
        // complete all necessary operations.
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isConnectedToInternet -> {
                            // This will run once on the moment of subscribe
                            // and then every time Internet connection state changes.
                            Log.d(LOG_TAG, "Internet connection is " + isConnectedToInternet);
                        }
                );
    }
}