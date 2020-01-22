package testpushya14.coml.example.android.findphone2.Activities;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import testpushya14.coml.example.android.findphone2.R;
import testpushya14.coml.example.android.findphone2.Services.jobSchedularService;

public class MainActivity extends AppCompatActivity {

    private static int res;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private static JobScheduler mJobScheduler;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private EditText code_txt;
    private String MY_CODE="myCode";
    private String DEFAULT_0="default";
    private String codeText;
    boolean isCoded = false;
    private String code;
    public LocationManager locationManager;
    public static Location location;

    private EditText oldCode_edt;
    private EditText newCode_edt;
    private SharedPreferences prefs;
    private String oldCodeString;
    private String newCodeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.N) {

            prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
            String codeSp = prefs.getString(MY_CODE, DEFAULT_0);

            Toast.makeText(this, codeSp, Toast.LENGTH_SHORT).show();


//            PersistableBundle intentExtras = new PersistableBundle(Integer.parseInt("android.provider.Telephony.SMS_RECEIVED"));
            Uri uriSms = Uri.parse("content://sms/inbox");
            ComponentName component = new ComponentName(getPackageName(),
                    jobSchedularService.class.getName());
            JobInfo builder = new JobInfo.Builder(1, component)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .addTriggerContentUri(new JobInfo.TriggerContentUri(
                            Telephony.Sms.CONTENT_URI,
                            JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS))
                    .setTriggerContentUpdateDelay(1000)
                    .setTriggerContentMaxDelay(300)
                    .build();

            mJobScheduler = (JobScheduler)
                    getSystemService(this.JOB_SCHEDULER_SERVICE);
            int res = mJobScheduler.schedule(builder);


//            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
//                        android.Manifest.permission.ACCESS_FINE_LOCATION
//                }, 10);
//            }
//
//
//
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//
//            location =
//
//                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            UsersLocation usersLocation = new UsersLocation();
//            usersLocation.populateCoordinatesFromLastKnownLocation();
//            String lattd = UsersLocation.lat;
//            String longt = UsersLocation.lon;
//
//            Toast.makeText(usersLocation, "lat = "+lattd+"longitude = : "+ longt, Toast.LENGTH_SHORT).show();


            if (res == JobScheduler.RESULT_SUCCESS) {
                Log.i("res", "job scheduled");
            } else {
                Log.i("res", "job scheduled failed");
            }
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    1);
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_SMS},
                        2);
            }
            SharedPreferences prefs;
            prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
            code = prefs.getString(MY_CODE, DEFAULT_0);
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
            isCoded = prefs.getBoolean("isCoded", isCoded);
            Log.i("iscoded", String.valueOf(isCoded));
            if (isCoded) {
                next();
                finish();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isCoded) {
            //  finish();
        }
    }


    public void saveMyCodeFirst(View view) {
        SharedPreferences prefs;
        code_txt = (EditText) findViewById(R.id.code);
        codeText = code_txt.getText().toString();
        Toast.makeText(this, "" + codeText, Toast.LENGTH_SHORT).show();
        prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
        isCoded = true;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isCoded", isCoded);
        editor.putString(MY_CODE, codeText);
        editor.apply();
        String codeSp = prefs.getString(MY_CODE, DEFAULT_0);
        code_txt.setText(codeSp);


        Log.i("codeSp", String.valueOf(codeSp));
        next();
    }


//    public void test(View view) {
//        SharedPreferences prefs;
//
//        prefs = this.getSharedPreferences("xyz", Context.MODE_PRIVATE);
//        String codeSp = prefs.getString(MY_CODE,DEFAULT_0);
//        code_txt = (EditText) findViewById(R.id.code);
//        code_txt.setText(codeSp);
//
//        Log.i("codeSp", String.valueOf(codeSp));
//    }

    public void next() {
        Intent intent = new Intent(MainActivity.this, change_activity.class);
        startActivity(intent);
    }

}