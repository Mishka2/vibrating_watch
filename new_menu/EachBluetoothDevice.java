package com.example.mishka.new_menu;

import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Mishka on 3/28/17.
 */

public class EachBluetoothDevice {
    //data

    //private static final String MY_MAC_ADDR = "20:16:09:05:11:70"
    private String MY_MAC_ADDR;
    private String namePerson = null;
    private String alarm = null;

    //data
    String address;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ;
    private OutputStream outputStream;

    //constructors
    public EachBluetoothDevice(String name, String time){
        namePerson = null;
        alarm = time;
    }

    public EachBluetoothDevice(String name){
        namePerson = name;
        alarm = "0:0";
    }

    //methods
    public String getMacAddress(){
        return MY_MAC_ADDR;
    }

    public String getNamePerson(){
        return namePerson;
    }

    public void setNamePerson(String name) {
        namePerson = name;
    }

    public void setMacAddress(String mac){
        MY_MAC_ADDR = mac;
    }

    public String getTime() {
        return alarm;
    }


}
