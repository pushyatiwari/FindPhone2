package testpushya14.coml.example.android.findphone2.Services;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import testpushya14.coml.example.android.findphone2.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class jobSchedularService extends JobService {
    private static final String CHANNEL_ID = "0";
    ArrayList<String> smsBody = new ArrayList<String>();
    private String address;
    private Cursor c;

    private String body = null;
    String finalmsg = null;

    String phone;
    MediaPlayer m;
    private String MY_CODE="myCode";
    private String DEFAULT_0="default";
    ArrayList<String> main = new ArrayList<String>();
    String TAG = "jobschdlar";
    private int type2;
    private SharedPreferences prefs;
    private String codeSp;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try{
        getAllSms(getApplicationContext());
         if(finalmsg!=null&&type2!=2){

             Log.i("codefinal", finalmsg+"    "+type2);
             m = MediaPlayer.create(jobSchedularService.this, R.raw.findmyphone);
             AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
             audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

             m.start();




                 {
                     SmsManager smsManager = SmsManager.getDefault();
                  //   smsManager.sendTextMessage(address, null, "send by : " + address + "" + "lat = " + lattd + "longitude = : " + longt, null, null);
                     createNotificationChannel();
                 }


         }}catch (Exception e){
            e.printStackTrace();
        }
        jobFinished(jobParameters, true);
        refresh();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAllSms(Context context) {
        prefs = this.getSharedPreferences("xyz",Context.MODE_PRIVATE);
        codeSp = prefs.getString(MY_CODE,DEFAULT_0);
        int count = 0;
        ContentResolver cr = context.getContentResolver();
        c = cr.query(Telephony.Sms.CONTENT_URI, new String[]{"address", "body", "date", "read","type"}
                , null, null, null);

        int j = 0;
        Date currentTime = Calendar.getInstance().getTime();
        String systime=String.valueOf(currentTime);
        Log.i("time", String.valueOf(currentTime));


        if (c != null) {

            if (c.moveToFirst()) {

                int i;
                 address = c.getString(c.getColumnIndex("address"));
                body = c.getString(c.getColumnIndex("body"));
                String date = c.getString(c.getColumnIndex("date"));
                String status = c.getString(c.getColumnIndex("read"));


                Long timestamp = Long.parseLong(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp);
                Date finaldate = calendar.getTime();
                String smstime = finaldate.toString();
                Log.i("smstym", smstime);
                //date of message
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(Long.parseLong(date));
                date = DateFormat.format("dd-MMM-yyyy", cal).toString();
                //current date
                Date curdate = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(curdate);
                int y = 0;
                String type = c.getString(c.getColumnIndex("type"));
                type2 = Integer.parseInt(type);
                getCode();
                Log.i("type1", type);
                if (date.equals(formattedDate)&&(type2==1)||(type2==2)) {
//                    Pattern p = Pattern.compile("pushya81(.*?)");
//                    Matcher m = p.matcher(body);
//
//                    while (m.find()) {
//                        y = 1;
//                        main.add(m.group(0));
//                        Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
//                        smsBody.add(body.toString());
//                        smsBody.add("djd");
//                        Log.i("myarray", "getAllSms: "+smsBody.get(0));
//                        Log.i("smsBody", body+"  j=  "+j);
//                        i=j;
//                        finalmsg = body;
//                        count++;
//
//                    }
                   int codeLength = codeSp.length();
                    Log.i("MySPCode", String.valueOf(codeLength));
                    Log.i("MySPCode2",String.valueOf(body.length()));
                    Log.i("MySPCode2",body.substring(0,codeLength-1));
                if(body.contains(codeSp)&&body.substring(0,codeLength).equals(codeSp)){
                    smsBody.add(body);
                    finalmsg = body;
                    Log.i("MySPCode", finalmsg);
                }


                }







                Log.i("count", String.valueOf(count));


            } else {
                Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
            }
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.N)

    private void refresh() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.N) {
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

            JobScheduler mJobScheduler = (JobScheduler)
                    getSystemService(JOB_SCHEDULER_SERVICE);
            int res = mJobScheduler.schedule(builder);


            Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT).show();

            if (res == JobScheduler.RESULT_SUCCESS) {
                Log.i("res", "job scheduled" );
            } else {
                Log.i("res", "job scheduled failed");
            }

        }
    }
    public String getCode(){
         String MY_CODE="myCode";
       codeSp = prefs.getString(MY_CODE,"default");
       return codeSp;
       }
    private void createNotificationChannel() {
        Intent snoozeIntent = new Intent(this, jobSchedularService.class);
        snoozeIntent.putExtra("myNtf", 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("find phone")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                  ;


            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);
            notificationManager2.notify(1, mBuilder.build());

        }
    }



}
