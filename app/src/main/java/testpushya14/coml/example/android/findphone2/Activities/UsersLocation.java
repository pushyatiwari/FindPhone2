package testpushya14.coml.example.android.findphone2.Activities;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UsersLocation extends AppCompatActivity {

    private static final NumberFormat nf = new DecimalFormat("##.########");
    public  LocationManager locationManager;
    public static String lat;
    public static String lon;


   public void populateCoordinatesFromLastKnownLocation() {


        if (MainActivity.location != null) {

            lat = nf.format(MainActivity.location.getLatitude());

            lon = nf.format(MainActivity.location.getLongitude());

//            Toast.makeText(this, "loc found  lat = "+lat+"longitude = : "+ lon, Toast.LENGTH_SHORT).show();


        }
    else {
            Toast.makeText(this, "loc not found", Toast.LENGTH_SHORT).show();

        }

    }
}


