package com.fengchi.TimeTravel.Utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by stormbaron on 17-4-19.
 */

public class PermissionUtil {

    public static String[] requestPermissionAr = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE};


    public static void requestPermission(Activity object, int code) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            checkPermission(object);
        }
    }

    private static void checkPermission(Activity object) {

        int ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(object, Manifest.permission.ACCESS_COARSE_LOCATION);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(object, Manifest.permission.READ_EXTERNAL_STORAGE);
        int CAMERA = ContextCompat.checkSelfPermission(object, Manifest.permission.CAMERA);
        int CALL_PHONE = ContextCompat.checkSelfPermission(object, Manifest.permission.CALL_PHONE);

        int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

        if (ACCESS_COARSE_LOCATION != PERMISSION_GRANTED || READ_EXTERNAL_STORAGE != PERMISSION_GRANTED ||
                CAMERA != PERMISSION_GRANTED || CALL_PHONE != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(object,
                    requestPermissionAr,
                    1);

        }

    }

    public static void getPermission(Context context){
        /*// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }*/
    }

}
