#include <WiFi.h>
#include <FirebaseESP32.h>
#include <WiFiManager.h>

// Firebase ESP32 credentials
#define FIREBASE_HOST "datn-efb05-default-rtdb.asia-southeast1.firebasedatabase.app"
#define FIREBASE_AUTH "cs1xOkrXUMAtoQmaUhnDgmSpjUnnPgDolcC8VSHm"
#define TRIGGER_PIN 0

unsigned int timeout = 120;

// FirebaseESP32 object
FirebaseData firebaseData;

void connectToWifi(void) {
  // Enter WiFi credentials
  const char* ssid = "NT3";
  const char* password = "Thanh@@026099566";

  // Connect to WiFi
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
    // is configuration portal requested?
    if ( digitalRead(TRIGGER_PIN) == LOW) {
      WiFiManager wm;    

      //reset settings - for testing
      wm.resetSettings();
    
      // set configportal timeout
      wm.setConfigPortalTimeout(timeout);

      if (!wm.startConfigPortal("KHOACUA")) {
        Serial.println("failed to connect and hit timeout");
        delay(3000);
        //reset and try again, or maybe put it to deep sleep
        ESP.restart();
        delay(5000);
      }
    }
  }

  Serial.println("\nConnected to WiFi");
}

void resetWifi(void)
{
  WiFi.mode(WIFI_STA); // explicitly set mode, esp defaults to STA+AP  
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("\n Starting");
  pinMode(TRIGGER_PIN, INPUT_PULLUP);
}

void setup() {
  Serial.begin(9600);
  resetWifi();
  // Connect to WiFi
  connectToWifi();
  // Initialize FirebaseESP32
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  // Send "Hello" to Firebase Realtime Database
  Firebase.setString(firebaseData, "/message", "Hello");

  delay(5000);  // Send data every 5 seconds
}
