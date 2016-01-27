package com.example.dodobal_1.recoderapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    public static int quality=16;
    public static String spinType="mp3";
    public static String callFlag="On";
    private MediaRecorder recorder;
    private Button start, stop,show;
    File path;

    private static final int REQUEST_CODE=0;
    private DevicePolicyManager mDPM;
    private ComponentName mAdminName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            mAdminName = new ComponentName(this, DeviceAdminDemo.class);

            if (!mDPM.isAdminActive(mAdminName)) {

                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Click on Activate button to secure your application.");
                startActivityForResult(intent, REQUEST_CODE);
            } else {

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        start=(Button)findViewById(R.id.startButton);
        start.setOnClickListener(startListener);
        stop=(Button)findViewById(R.id.stopButton);
        stop.setOnClickListener(stopListener);

        show=(Button)findViewById(R.id.showFiles);
        show.setOnClickListener(showListener);
        recorder=new MediaRecorder();


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("8bit");
        categories.add("16bit");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                final String spinVal = String.valueOf(spinner.getSelectedItem());
                if (spinVal=="8bit") {
                    quality = 8;
                 //   resetRecorder();
                }
                else if (spinVal=="16bit") {
                    quality = 16;
                   // resetRecorder();
                }

               // Toast.makeText(MainActivity.this, spinVal,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        // Spinner click listener

        // Spinner Drop down elements
        List<String> categories1 = new ArrayList<String>();
        categories1.add("mp3");
        categories1.add("wav");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                 spinType = String.valueOf(spinner1.getSelectedItem());


                //Toast.makeText(MainActivity.this, spinType,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener

        // Spinner Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("On");
        categories2.add("Off");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               TService.call_Flag = String.valueOf(spinner2.getSelectedItem());


               // Toast.makeText(MainActivity.this, callFlag,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode, data);

            //   Toast.makeText(this, "onActivityResult",Toast.LENGTH_LONG).show();
            if (REQUEST_CODE == requestCode) {

                Intent intent = new Intent(MainActivity.this, TService.class);
                startService(intent);
            }
            //  else{
            //      Intent intent=new Intent(MainActivity.this,TService.class);
            //      startService(intent);
            //  }

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        recorder.release();
    }

    private void resetRecorder(){

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        if (spinType=="mp3") {
            recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        }
        else
        {
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        }
        recorder.setAudioEncodingBitRate(quality);
        recorder.setOutputFile(path.getAbsolutePath());
        try{
            recorder.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private View.OnClickListener showListener=new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener startListener=new View.OnClickListener(){
        @Override
        public void onClick(View v){
            recorder=new MediaRecorder();
            String out=new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
            File sampleDir=new File(Environment.getExternalStorageDirectory(),"/TestData");
            if (!sampleDir.exists()){
                sampleDir.mkdirs();
            }

            String file_name="Record";
            try{
                if (spinType=="mp3")
                    path=File.createTempFile(file_name+out,".mp3",sampleDir);
                else
                    path=File.createTempFile(file_name+out,".wav",sampleDir);

            }
            catch(IOException e){
                e.printStackTrace();
            }


            //path=new File(Environment.getExternalStorageDirectory(),"myRecording.3gpp");
            resetRecorder();
            try {
                recorder.start();
                Toast.makeText(MainActivity.this, "Start Record", Toast.LENGTH_SHORT).show();
                start.setEnabled(false);
                stop.setEnabled(true);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener stopListener=new View.OnClickListener(){
        @Override
        public void onClick(View v){
            try {
                recorder.stop();
                resetRecorder();
                onDestroy();
                start.setEnabled(true);
                stop.setEnabled(false);
                Toast.makeText(MainActivity.this, "Save Audio",Toast.LENGTH_LONG).show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
