#include <Wire.h>
#include "Wire.h"
#include <SoftwareSerial.h>
#define DS1307_ADDRESS 0x68

const int DS1307 = 0x68; // Address of DS1307 see data sheets
SoftwareSerial blueTooth(12,13); // RX | TX

const int buzzer =  5;
int buzzState = LOW; 
 
const int buttonPin = 2;
int buttonState = 0;  

byte second = 0;
byte minute = 0;
byte hour = 0;

int alarmHour;
int alarmMin;

String message;

void setup() {
  Wire.begin();
  Serial.begin(9600);
  delay(2000); // This delay allows the MCU to read the current date and time.

  pinMode(buzzer, OUTPUT);
  pinMode(buttonPin, INPUT);
}

byte decToBcd(byte val) {
  return ((val/10*16) + (val%10));
}
byte bcdToDec(byte val) {
  return ((val/16*10) + (val%16));
}

void actuallyBuzz(){
   digitalWrite(buzzer, HIGH);
   delay(100);
   digitalWrite(buzzer, LOW);
   delay(200);
}

void readTime() {
  Wire.beginTransmission(DS1307);
  Wire.write(byte(0));
  Wire.endTransmission();
  Wire.requestFrom(DS1307, 7);
  second = bcdToDec(Wire.read());
  minute = bcdToDec(Wire.read());
  hour = bcdToDec(Wire.read());
}

void alarm(){
  if((alarmHour == hour) && (alarmMin == minute)){
      actuallyBuzz();
  }
}

void vibrate() {
  buttonState = digitalRead(buttonPin);

  if (buttonState == HIGH) {
    alarmHour = 100;
    for (int i = 0; i< hour; i++){
       actuallyBuzz();
    }
  }
  digitalWrite(buzzer, buzzState = LOW);
}
 
void connectBlue() {
  if(Serial.available()){
  message = Serial.readStringUntil(' ');
  
    if(message == "setalarm"){
     alarmHour = Serial.parseInt();
     alarmMin = Serial.parseInt();
     actuallyBuzz();

     for (int i = 0; i< alarmMin; i++){
        actuallyBuzz();
      }
    }
    if(message == "settime"){
      hour = Serial.parseInt();
      minute = Serial.parseInt();
      actuallyBuzz();
      for (int i = 0; i< hour; i++){
        actuallyBuzz();
      }
    }
  }
}


void loop() {
  readTime();
  alarm();
  connectBlue();
  vibrate();
  delay(100);
}
