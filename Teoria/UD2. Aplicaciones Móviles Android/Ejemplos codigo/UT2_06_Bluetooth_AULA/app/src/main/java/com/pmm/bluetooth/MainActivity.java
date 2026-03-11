package com.pmm.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter BTAdapter;
    private static final int CODIGO_BT= 100;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BTAdapter= BluetoothAdapter.getDefaultAdapter();

        if(BTAdapter == null) {
            Toast.makeText(this, "El dispositivo carece de adaptador Bluetooh", Toast.LENGTH_SHORT).show();
        }
        else {
            if(!BTAdapter.isEnabled()) {
                Intent enableBtIntent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBtIntent);
            }

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, CODIGO_BT);
            }

        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CODIGO_BT) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Usuario aceptó
                boolean ok= BTAdapter.startDiscovery();
                Log.d("TEST", "startDiscovery: " + ok);
            }
        }

    }

}