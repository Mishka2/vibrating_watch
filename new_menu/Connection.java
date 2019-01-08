package com.example.mishka.new_menu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Mishka on 4/7/17.
 */

public class Connection {

    String MY_MAC_ADDR;
    int hour;
    int min;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream outputStream;
    boolean bloop = false;


    public Connection(String mac){
        MY_MAC_ADDR = mac;
    }


    public void connectBlue(){
        try{
            init();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        bloop = true;

        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();
                BluetoothDevice device = null;

                if(bondedDevices.size() > 0) {
                    for (BluetoothDevice mdevice : bondedDevices) {

                        if (MY_MAC_ADDR.equals(mdevice.getAddress())) {
                            device = mdevice;
                            break;
                        }
                    }
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    socket.connect();
                    outputStream = socket.getOutputStream();
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }

    public void writeToBluetooth(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            outputStream.write(msgBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean bloop(){
        return bloop;
    }




}
