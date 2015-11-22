package com.example.gusfc_000.freeex;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gusfc_000 on 21/11/2015.
 */
public class GPSTracker extends Service implements LocationListener {

    public Context myContext;

    //estados
    public boolean isGPSEnable = false;
    public boolean isNetworkEnable;
    public boolean cancelGetLocation = false;


    public LocationManager locationManager;
    public Location locationGPS;

    public double latitude;
    public double longitude;




    public GPSTracker(Context context) {
        this.myContext = context;
        getLocation();

    }

    /**
     * Metodo que obtiene los proveedores de GPS.
     * Si ya esta habilitado prosigue a obetener las coordenadas mediante
     * latitud y longitud
     * @return
     */
    public Location getLocation() {
        try {

            locationManager = (LocationManager) myContext.getSystemService(LOCATION_SERVICE);

            isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnable && !isNetworkEnable) {
                //ningun proveedor de red esta habilitada
            } else {
                this.cancelGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnable) {
                    //proveedor, minimo timepo, minimo distancial
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10, 1, this);
                    Log.d("Provider", "Network");
                    if (locationManager != null) {
                        locationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (locationGPS != null) {
                            latitude = locationGPS.getLatitude();
                            longitude = locationGPS.getLongitude();
                        }
                    }
                }
                //habilitando GPS servicio
                if (isGPSEnable) {
                    if (locationGPS == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, this);
                        Log.d("Provider", "GPS Enabled");
                        if (locationManager != null) {
                            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (locationGPS != null) {
                                latitude = locationGPS.getLatitude();
                                longitude = locationGPS.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return locationGPS;

    }

    //function to get latitude
    public double getLatitude() {
        if (locationGPS != null) {
            latitude = locationGPS.getLatitude();
        }
        return latitude;
    }


    //function to get longitude
    public double getLongitude() {
        if (locationGPS != null) {
            longitude = locationGPS.getLongitude();
        }
        return longitude;
    }

    /**
     * Metodo que muestra en ventana un dialogo de alerta.
     * para que el jugador vaya a opciones y habilite el proveedor de GPS.
     *
     */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                myContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        this.locationGPS = location;
    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status   {@link LocationProvider#OUT_OF_SERVICE} if the
     *                 provider is out of service, and this is not expected to change in the
     *                 near future; {@link LocationProvider#TEMPORARILY_UNAVAILABLE} if
     *                 the provider is temporarily unavailable but is expected to be available
     *                 shortly; and {@link LocationProvider#AVAILABLE} if the
     *                 provider is currently available.
     * @param extras   an optional Bundle which will contain provider specific
     *                 status variables.
     *                 <p/>
     *                 <p> A number of common key/value pairs for the extras Bundle are listed
     *                 below. Providers that use any of the keys on this list must
     *                 provide the corresponding value as described below.
     *                 <p/>
     *                 <ul>
     *                 <li> satellites - the number of satellites used to derive the fix
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


