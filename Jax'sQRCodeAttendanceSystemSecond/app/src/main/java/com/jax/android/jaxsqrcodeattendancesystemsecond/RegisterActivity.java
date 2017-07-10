package com.jax.android.jaxsqrcodeattendancesystemsecond;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText name;
    private EditText password;
    private EditText comfirmPassowrd;
    private EditText studentID;
    private EditText androidDeviceID;
    private Button registerButton;
    private String address;
    private boolean validated = false;
    User user = new User();
    String TAG = "JAXX'SSS";
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button)findViewById(R.id.buttonRegister);
        userName = (EditText)findViewById(R.id.etUsername);
        name = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        studentID = (EditText)findViewById(R.id.etStudentID);
        androidDeviceID = (EditText)findViewById(R.id.etDeviceAddress);
        comfirmPassowrd = (EditText)findViewById(R.id.etComfirmPassword);
       // FirebaseApp.initializeApp(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        androidDeviceID.setText(getMacAddr());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // User user = new User(userName.getText().toString(), name.getText().toString(),
                 //
                //       password.getText().toString(), Integer.parseInt(studentID.getText().toString()));

                //mDatabaseReference.child("user").push().setValue(user);


                AsyncSignUpTask task = new AsyncSignUpTask();
                task.execute();

            }

        });

    }
    // get MAC address
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    private class AsyncSignUpTask extends AsyncTask<String, Void, Void>{


        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG,"doInBackground");
            if (validated){
                // add data into firebase
                mDatabaseReference.child("user").push().setValue(user);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG,"onPostExecute");
            //navigate back to login page
            if (validated) {
                Toast toast = Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_LONG);
                toast.show();
                userName.setText("");
                name.setText("");
                password.setText("");
                studentID.setText("");
                androidDeviceID.setText("");
                comfirmPassowrd.setText("");

            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Error Occured!", Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPostExecute");
            if (userName.getText().length() != 0 && userName.getText().toString() != "") {
                user.setUserName(userName.getText().toString());
                if (name.getText().length() != 0 && name.getText().toString() != "") {
                    user.setName(name.getText().toString());
                    if (studentID.getText().length() != 0 && studentID.getText().toString() != "") {
                        user.setStudentID(Integer.parseInt(studentID.getText().toString()));

                        if (password.getText().length() != 0 && password.getText().toString() != "") {
                            // user.setPassword(password.getText().toString());
                            if (comfirmPassowrd.getText().length() != 0 && comfirmPassowrd.getText().toString() != "") {
                                if (password.getText().toString().equals(comfirmPassowrd.getText().toString())) {
                                    user.setPassword(password.getText().toString());
                                    user.setAndroidDeviceID(getMacAddr());
                                    validated = true;
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG);
                                    toast.show();
                                    validated = false;
                                }

                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Enter comfirm password", Toast.LENGTH_LONG);
                                toast.show();
                                validated = false;
                            }
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG);
                            toast.show();
                            validated = false;
                        }
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Enter your student ID", Toast.LENGTH_LONG);
                        toast.show();
                        validated = false;
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG);
                    toast.show();
                    validated = false;
                }
            }else{

                Toast toast = Toast.makeText(getApplicationContext(), "Enter your user user name", Toast.LENGTH_LONG);
                toast.show();
                validated = false;
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }
    }

}
