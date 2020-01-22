package testpushya14.coml.example.android.findphone2.Activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import testpushya14.coml.example.android.findphone2.R;

public class change_activity extends AppCompatActivity {

    private EditText oldCode_edt;
    private EditText newCode_edt;
    private SharedPreferences prefs;
    private String MY_CODE="myCode";
    private String oldCodeString;
    private String newCodeString;
    private String DEFAULT_0="default";
    private String code;
    private boolean isCoded;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        oldCode_edt = (EditText) findViewById(R.id.old_code);
        newCode_edt = (EditText) findViewById(R.id.new_code);
        prefs = this.getSharedPreferences("xyz",Context.MODE_PRIVATE);
        code =  prefs.getString(MY_CODE,DEFAULT_0);
        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();

        prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
        String codeSp = prefs.getString(MY_CODE,DEFAULT_0);
        newCodeString = newCode_edt.getText().toString();

        oldCodeString = oldCode_edt.getText().toString();
        Toast.makeText(this, codeSp, Toast.LENGTH_SHORT).show();

//
//        if (ActivityCompat.checkSelfPermission
//                (change_activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (change_activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.

//        }
//        UsersLocation usersLocation = new UsersLocation();
//        usersLocation.populateCoordinatesFromLastKnownLocation();
//        String lattd = UsersLocation.lat;
//        String longt = UsersLocation.lon;
//        Toast.makeText(usersLocation, "lat = "+lattd+"longitude = : "+ longt, Toast.LENGTH_SHORT).show();



    }


    public void changeCode(View view) {
        prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
        String codeSp = prefs.getString(MY_CODE,DEFAULT_0);
        oldCodeString = oldCode_edt.getText().toString();
        newCodeString = newCode_edt.getText().toString();
        if(codeSp.equals(oldCodeString)){
            Toast.makeText(this, "code changed", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor= prefs.edit();
            editor.clear();
            editor.putString(MY_CODE,newCodeString);
            isCoded = true;
            editor.putBoolean("isCoded",isCoded);
            editor.apply();
        }else{
            Toast.makeText(this, "enter correct old code", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCode(View view) {
        prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
        String codeSp = prefs.getString(MY_CODE,DEFAULT_0);
        newCodeString = newCode_edt.getText().toString();

        oldCodeString = oldCode_edt.getText().toString();
        Toast.makeText(this, codeSp, Toast.LENGTH_SHORT).show();
        if(codeSp.equals(oldCodeString)) {
            Toast.makeText(this, "correct code", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
        }else{
            Toast.makeText(this, "enter correct old code", Toast.LENGTH_SHORT).show();
        }
    }


    public void stop(View view) {
    }
}
