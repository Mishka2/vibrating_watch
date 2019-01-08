package com.example.mishka.new_menu;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.view.ViewGroup.LayoutParams;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int maxDevices = 2;
    ArrayList<Integer> devices = new ArrayList<Integer>();
    ArrayList<CharSequence> users = new ArrayList<CharSequence>();
    int num = 1;
    String user = null;
    String address = null;
    Participants allBluetooth = new Participants();
    Menu menu;
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream outputStream;
    String MY_MAC_ADDR;
    //the medical devices:
    //private static final String emma = "20:16:09:12:87:68";
//    String MY_MAC_ADDR = "20:16:09:05:15:06";
    private Connection first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button alarmButton = (Button) findViewById(R.id.alarm);
        Button setRealTime = (Button) findViewById(R.id.button);
        Button medicineTaken = (Button) findViewById(R.id.taken_button);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        for(int i = 0; i < maxDevices; i++){
            devices.add(i,i+1);
        }

        setNamesNull();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addNewPatient) {
            // Handle the camera action
        } else if (id == R.id.nav_bluetooth1) {

        } else if (id == R.id.nav_bluetooth2) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void popUpMenu(MenuItem item) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText name = (EditText) alertLayout.findViewById(R.id.et_name);
        final EditText macAddress = (EditText) alertLayout.findViewById(R.id.et_address);



        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Login");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 user = name.getText().toString();
                 address = macAddress.getText().toString();
                Display(user, address);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void setNamesNull(){
        for(int n = 0; n < maxDevices; n++) {
            allBluetooth.addParticipant(null, "5:43");
        }

    }

    public void Display(String name, String macAddress){

        if(num == 1) {
            allBluetooth.getPeople().get(0).setNamePerson(name);
            allBluetooth.getPeople().get(0).setMacAddress(macAddress);
            num++;
        } else if (num == 2){
            allBluetooth.getPeople().get(1).setNamePerson(name);
            allBluetooth.getPeople().get(1).setMacAddress(macAddress);
            num++;
        } else {
            Toast.makeText(getBaseContext(), "Not enough room to add a new device",
                    Toast.LENGTH_SHORT).show();
        }
        arrayUser(name);
    }

    public ArrayList<CharSequence> arrayUser(String user){
        CharSequence userChar = (CharSequence) user;
        users.add(userChar);
        return users;
    }

    public void nameOne(MenuItem item){
        String nameOnePerson = allBluetooth.getPeople().get(0).getNamePerson();
        item.setTitle(users.get(0));
        if(nameOnePerson != null) {
            MY_MAC_ADDR = allBluetooth.getPeople().get(0).getMacAddress();
            Toast.makeText(getBaseContext(), " " + allBluetooth.getPeople().get(0).getNamePerson() +
                    "Address: " + MY_MAC_ADDR,
                    Toast.LENGTH_SHORT).show();
            setUser(1);

        } else {
            Toast.makeText(getBaseContext(), "No Person in this Account",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void nameTwo(MenuItem item){
        String nameTwoPerson = allBluetooth.getPeople().get(1).getNamePerson();
        item.setTitle(users.get(1));
        if(nameTwoPerson != null) {
            MY_MAC_ADDR = allBluetooth.getPeople().get(1).getMacAddress();
            Toast.makeText(getBaseContext(), " " + allBluetooth.getPeople().get(1).getNamePerson() +
                            "Address: " + MY_MAC_ADDR,
                    Toast.LENGTH_SHORT).show();
            setUser(2);

        } else {
            Toast.makeText(getBaseContext(), "No Person in this Account",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setUser(int user){
//        MY_MAC_ADDR = allBluetooth.getPeople().get(user).getMacAddress();
        Toast.makeText(getBaseContext(), "address: " + MY_MAC_ADDR,
                Toast.LENGTH_SHORT).show();
//        Connection first = new Connection(allBluetooth.getPeople().get(0).getMacAddress());
        first = new Connection(MY_MAC_ADDR);
        first.connectBlue();
    }

    public void setAlarm(View v){
        Toast.makeText(getBaseContext(), "address: " + MY_MAC_ADDR,
                Toast.LENGTH_SHORT).show();
        TimePicker alarmTime = (TimePicker) findViewById(R.id.timePicker);

        int minute = alarmTime.getCurrentMinute();
        int hour = alarmTime.getCurrentHour();

        String timeAlarm = hour + ":" + minute;

        Toast.makeText(getBaseContext(), "Time alarm: " + timeAlarm + " MacAdd: " + MY_MAC_ADDR,
                Toast.LENGTH_SHORT).show();


        try{
            write("setalarm " + timeAlarm);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setTime(View v){

        Calendar rightNow = Calendar.getInstance();
        int hourCurrent = rightNow.get(Calendar.HOUR);
        int minCurrent = rightNow.get(Calendar.MINUTE);

        String timeTime = hourCurrent + ":" + minCurrent;

        try{
            String timeHour = String.valueOf(hourCurrent);
            write("settime " + timeTime);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) throws IOException {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        first.writeToBluetooth(s);
    }


}
