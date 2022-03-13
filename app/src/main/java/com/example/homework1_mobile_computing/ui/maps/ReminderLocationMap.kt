package com.example.homework1_mobile_computing.ui.maps

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.Graph
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.ui.main.reminderEntries.ReminderEntriesViewModel
import com.example.homework1_mobile_computing.ui.reminder.createNotificationChannel
import com.example.homework1_mobile_computing.ui.reminder.locationNotification
import com.example.homework1_mobile_computing.util.rememberMapViewWithLifecycle
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat.getSystemService
import com.example.homework1_mobile_computing.util.ShowAlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Math.abs
import java.util.*


@Composable
fun ReminderLocationMap(
    mapNavController: NavController,
    buttonPressed: String
) {
    val mapView = rememberMapViewWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    val viewModel: ReminderEntriesViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()
    val remindersList = viewState.reminders

    val isDialogOpen = remember { mutableStateOf(true) }
    val emailVal = remember { mutableStateOf("") }


    //


    /*private lateinit var mapG: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(Graph.appContext)
    geofencingClient = LocationServices.getGeofencingClient(Graph.appContext)*/


/*
    /*const */val LOCATION_REQUEST_CODE = 123
    /*const */val GEOFENCE_LOCATION_REQUEST = 12345         // USED
    /*const */val CAMERA_ZOOM_LEVEL = 13f
    /*const */val GEOFENCE_RADIUS = 500                     // used
    /*const */val GEOFENCE_ID = "REMINDER_GEOFENCE_ID"      // used
    /*const */val GEOFENCE_EXPIRATION = 10 * 24 * 60 * 60 * 1000 // 10 days     // used
    /*const */val GEOFENCE_DWELL_DELAY = 10 * 1000 // 10 seconds                // used
    //const val TAG = MapsActivity::class.java.simpleName


    if (!isLocationPermissionGranted()) {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        /*ActivityCompat.requestPermissions(
            Graph.activity,
            permissions.toTypedArray(),
            LOCATION_REQUEST_CODE
        )*/
    }else {

*/
        //

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(bottom = 36.dp)
        ) {
            AndroidView({ mapView }) { mapView ->
                coroutineScope.launch {
                    val map = mapView.awaitMap()

                    //
                    /*if(ContextCompat.checkSelfPermission(
                            context,Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ))
                    map.isMyLocationEnabled = true*/
                    //
                    map.uiSettings.isZoomControlsEnabled = true


                    val location = LatLng(65.06, 25.47)

                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(location, 13f)
                    )

                    val markerOptions = MarkerOptions()
                        .title("Welcome to Oulu")
                        .position(location)
                    map.addMarker(markerOptions)

                    setMapLongClick(
                        map = map,
                        clickMapNavController = mapNavController,
                        listReminders = remindersList,
                        buttonPressed
                    )



                }

            }
            /*if (check){
                ShowAlertDialog()
            }*/
        /*}*/
    }
}



private fun setMapLongClick (
    map: GoogleMap,
    clickMapNavController: NavController,
    listReminders: List<Reminder>,
    buttonPressed: String
) {

    var alertdialog : androidx.appcompat.app.AlertDialog
    var check = false

    //
    /*private lateinit var geofencingClient: GeofencingClient
    geofencingClient = LocationServices.getGeofencingClient(Graph.appContext)*/
    /*const */
    val GEOFENCE_RADIUS = 500
    //

    map.setOnMapLongClickListener { latlng ->
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.2f, Lng: %2$.2f",
            latlng.latitude,
            latlng.longitude
        )

        if (buttonPressed=="location") {
            map.addMarker(
                MarkerOptions().position(latlng).title("Current Location").snippet(snippet)
            ).apply {
                clickMapNavController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("location_data", latlng)
            }

            map.addCircle(
                CircleOptions()
                    .center(latlng)
                    .strokeColor(
                        android.graphics.Color.argb(
                            50,
                            70,
                            70,
                            70
                        )
                    ) // color argd from color graphics
                    .fillColor(
                        android.graphics.Color.argb(
                            70,
                            150,
                            150,
                            150
                        )
                    )   // color argd from color graphics
                    .radius(GEOFENCE_RADIUS.toDouble())

            )

            locationTriggeredNotification(listReminders,latlng)

        } else if (buttonPressed=="new"){
            map.addMarker(
                MarkerOptions().position(latlng).title("Reminder location").snippet(snippet)
            ).apply {
                clickMapNavController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("location_data", latlng)
            }
        } else if (buttonPressed=="edit"){
            map.addMarker(
                MarkerOptions().position(latlng).title("New Reminder location").snippet(snippet)
            ).apply {
                clickMapNavController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("location_data", latlng)
            }
        }
    }


}


private fun locationTriggeredNotification(
    listOfReminders: List<Reminder>,
    currentLocation: LatLng
){
    val locationReminders = mutableListOf<Reminder>()

    for (reminder in listOfReminders) {
        if (abs(reminder.longitude - currentLocation.longitude) <= 0.1 &&
            abs(reminder.latitude - currentLocation.latitude) <= 0.1
        ) {
            createNotificationChannel(Graph.appContext)
            locationReminders.add(reminder)
        }

    }
    if(locationReminders.isNotEmpty()){
        locationNotification(locationReminders)
    }

}




/*
@Composable
fun isLocationPermissionGranted(): Boolean{

    val context = LocalContext.current

    return ContextCompat.checkSelfPermission(
        context,Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

//


private fun createGeofence(location:LatLng, /*reminderId: String,*/ geofencingClient: GeofencingClient){
    val GEOFENCE_ID = "REMINDER_GEOFENCE_ID"
    val GEOFENCE_RADIUS = 500
    val GEOFENCE_EXPIRATION = 10 * 24 * 60 * 60 * 1000 // 10 days
    val GEOFENCE_DWELL_DELAY = 10 * 1000 // 10 seconds
    val GEOFENCE_LOCATION_REQUEST = 12345



    val geofence = Geofence.Builder()
        .setRequestId(GEOFENCE_ID)
        .setCircularRegion(location.latitude, location.longitude, GEOFENCE_RADIUS.toFloat())
        .setExpirationDuration(GEOFENCE_EXPIRATION.toLong())
        .setTransitionTypes(
            Geofence.GEOFENCE_TRANSITION_ENTER or
            Geofence.GEOFENCE_TRANSITION_DWELL
        ).setLoiteringDelay(GEOFENCE_DWELL_DELAY)
        .build()

    val geofenceRequest = GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofence(geofence)
        .build()

    val intent = Intent(Graph.appContext, GeofenceReceiver::class.java)
        //.putExtra("reminder ID", reminderId)
        .putExtra("message", "Geofence detected!")

    val pendingIntent = PendingIntent.getBroadcast(
        Graph.appContext,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    if (ActivityCompat.checkSelfPermission(
            Graph.appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        println("nooooo")
        return
    }
    geofencingClient.addGeofences(geofenceRequest, pendingIntent)



    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        if (ContextCompat.checkSelfPermission(
                Graph.appContext,Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                Graph.activity,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                GEOFENCE_LOCATION_REQUEST
            )
        } else {
            geofencingClient.addGeofences(geofenceRequest, pendingIntent)
        }
    } else{
        geofencingClient.addGeofences(geofenceRequest,pendingIntent)
    }*/


}


// ovveride fun onRequestPermissionResult



*/
