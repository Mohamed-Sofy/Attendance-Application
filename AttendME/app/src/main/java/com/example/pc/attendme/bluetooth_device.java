package com.example.pc.attendme;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class bluetooth_device extends Activity {

    private static final String TAG = "bluetooth_device";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    int position;
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;
    TextView v, v2;
    Button b1;


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");




            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);


                get_attend(position, device.getAddress().toString(), device.getName());

                //////////////////////////////////////////////////// الخصائص

                ////////////////////////////////////////////////////////////////

                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());


                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }


        }
    };

    String tablename;
    String name;
    String r_att;
    String row;
    Database_menu1 data1 = new Database_menu1(this);

    public void get_attend(int pos, String ip, String  id) {


        // السماح بتاع الفايل
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            readcontacts();
        }

        if (pos == 0) {
            tablename = "menu1";

            boolean result = data1.check_ip_exists(ip, tablename);
            if (result == true) {
                Toast.makeText(this, "not attend", Toast.LENGTH_SHORT).show();
            }
            else if (result == false) {
                Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();


                boolean re = data1.attend_student(id, ip, tablename);


                if (re == true) {

                    // الكتابه ف file
                    name = data1.re_name(id, tablename);

                    row = name + "    " + id +  "\n";
                    save_to_file(row);
                    //////////////////////////////

                    Toast.makeText(this, "اتحضر", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "الطالب مش موجود ف القائمه", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (pos ==1) {
            tablename = "menu2";
            boolean result = data1.check_ip_exists(ip, tablename);
            if (result == true) {
                Toast.makeText(this, "not attend", Toast.LENGTH_SHORT).show();
            } else if (result == false) {
                Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();

                boolean re = data1.attend_student(id, ip, tablename);
                if (re == true) {

                    name = data1.re_name(id, tablename);
                    row = name + "    " + id +  "\n";
                    save_to_file(row);

                    Toast.makeText(this, "اتحضر", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "الطالب مش موجود ف القائمه", Toast.LENGTH_SHORT).show();
                }

            }


        } else if (pos == 2) {
            tablename = "menu3";

            boolean result = data1.check_ip_exists(ip, tablename);
            if (result == true) {
                Toast.makeText(this, "not attend", Toast.LENGTH_SHORT).show();
            } else if (result == false) {
                Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();
                boolean re = data1.attend_student(id, ip, tablename);
                if (re == true) {
                    name = data1.re_name(id, tablename);
                    row = name + "    " + id +  "\n";

                    save_to_file(row);

                    Toast.makeText(this, "اتحضر", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "الطالب مش موجود ف القائمه", Toast.LENGTH_SHORT).show();
                }

            }

        } else if (pos == 3) {
            tablename = "menu4";

            boolean result = data1.check_ip_exists(ip, tablename);
            if (result == true) {
                Toast.makeText(this, "not attend", Toast.LENGTH_SHORT).show();
            } else if (result == false) {
                Toast.makeText(this, "not exists", Toast.LENGTH_SHORT).show();
                boolean re = data1.attend_student(id, ip, tablename);
                if (re == true) {
                    name = data1.re_name(id, tablename);
                    row = name + "    " + id +  "\n";

                    save_to_file(row);

                    Toast.makeText(this, "اتحضر", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "الطالب مش موجود ف القائمه", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }



    public void save_to_file(String info) {


        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "//Attendfile");
            if (!dir.exists()) {
                dir.mkdir();
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }

            File file1 = new File(dir, "AttendStudents.txt");


            try {
                boolean append =true;
                FileOutputStream ss = new FileOutputStream(file1,append );
              // FileOutputStream ss = openFileOutput(file1,MODE_APPEND);

                ss.write(info.getBytes());

                ss.close();
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "SD card not found", Toast.LENGTH_LONG).show();
        }

    }

    private void readcontacts() {
        Toast.makeText(this,"permission",Toast.LENGTH_SHORT).show();

    }



// بيرمشن
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readcontacts();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                        new AlertDialog.Builder(this);
                        setTitle("action permission");


                    } else {
                        new AlertDialog.Builder(this);
                        setTitle("action permission");
                    }
                }
                break;
        }

    }





    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        //mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device);
        Button btnONOFF = (Button) findViewById(R.id.btnONOFF);
        btnEnableDisable_Discoverable = (Button) findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();


        Intent star = getIntent();
         position=star.getExtras().getInt("pos");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

////////////////////////////////////////////////////// فتح وقفل البلوثوث

        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: enabling/disabling bluetooth.");
                enableDisableBT();
            }
        });

    }



    public void enableDisableBT(){
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if(mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

    }

    ///////////////////////////////////////////////////////////////


    //////////////////////////////////////   تجعل البلوتوث مرئى
    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2,intentFilter);

    }

/////////////////////////////////////////// تبحث عن الاجهزه المفتوحه
    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){

            //check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

boolean r;
    public void back(View view) {

        if(position==0)
        {
            String tablename ="menu1";
          r=  data1.update_to_zreo(tablename);


        }
        else if(position==1)
        {
            String tablename ="menu2";
           r= data1.update_to_zreo(tablename);

        }
        else if(position==2)
        {
            String tablename ="menu3";
           r= data1.update_to_zreo(tablename);

        }
        else if(position==3)
        {
            String tablename ="menu4";
           r= data1.update_to_zreo(tablename);

        }
        if(r=true)
            Toast.makeText(this,"تم تسجيل الحضور",Toast.LENGTH_LONG).show();
        else if(r=false)
            Toast.makeText(this,"فشل",Toast.LENGTH_LONG).show();


        Intent intent=new Intent(bluetooth_device.this,navigation.class);
        startActivity(intent);
    }
}
