package com.example.dodobal_1.recoderapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_record);

        ListView lv;
        final ArrayList<String> FilesInFolder = GetFiles("/sdcard/TestData");
        lv = (ListView)findViewById(R.id.list_file);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Clicking on items
              //  Toast.makeText(ShowActivity.this,"bbbb",Toast.LENGTH_LONG).show();

                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource("/sdcard/TestData/" + FilesInFolder.get(position));
                }

                catch (IOException e) {
                   // Toast.makeText(ShowActivity.this,"cccc",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                 //   Toast.makeText(ShowActivity.this,"dddd",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();


            }
        });

    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }

    public void backMain(View v){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
