package com.example.mishka.new_menu;

import java.util.ArrayList;

/**
 * Created by Mishka on 3/28/17.
 */

public class Participants {

    //data
    int total;
    private ArrayList<EachBluetoothDevice> people;


    //constructor
    public Participants(){
        total = 0;
        people = new ArrayList<EachBluetoothDevice>();
    }

    //methods

    public void addParticipant(String name, String time){
        EachBluetoothDevice i = new EachBluetoothDevice(name, time);
        people.add(i);

    }

    public ArrayList<EachBluetoothDevice> getPeople(){
        return people;
    }

    public int getSize(){
        int size = people.size();
        return size;
    }


}
