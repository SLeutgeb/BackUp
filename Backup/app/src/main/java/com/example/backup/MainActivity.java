package com.example.backup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences dataStorage;

    private Button buttonSourceSelect;
    private Button buttonDestinationSelect;
    private TextView textDestination;
    private TextView helloWorld;

    private Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStorage = getSharedPreferences("BackUpPrefs", Context.MODE_PRIVATE);

        buttonSourceSelect = (Button) findViewById(R.id.buttonSourceSelect);
        buttonDestinationSelect = (Button) findViewById(R.id.buttonDestinationSelect);
        textDestination = (TextView) findViewById(R.id.textDestination);
        helloWorld = (TextView) findViewById(R.id.HelloWorld);

        textDestination.setText(dataStorage.getString("destination", ""));

        count = 0;

        buttonSourceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                helloWorld.setText("Worked! " + count);
            }
        });

        buttonDestinationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(Intent.createChooser(i, "Choose Directory"), 9999);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 9999:
                Log.i("Test", "Result URI " + data.getData());

                SharedPreferences.Editor editor = dataStorage.edit();
                editor.putString("destination", data.getData().toString());
                editor.commit();
                textDestination.setText(dataStorage.getString("destination", ""));

                break;
        }
    }
}