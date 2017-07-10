package com.jax.android.jaxsqrcodeattendancesystemlecturer;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText comfirmPassowrd;
    private EditText lecturerID;
    private Button registerButton;
    private boolean validated = false;
    Lecturer lecturer = new Lecturer();
    String TAG = "JAXX'SSS";
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = (Button)findViewById(R.id.buttonRegister);
        userName = (EditText)findViewById(R.id.etUsername);
        lecturerID = (EditText) findViewById(R.id.etLecturerID);
        password = (EditText)findViewById(R.id.etPassword);
        comfirmPassowrd = (EditText)findViewById(R.id.etComfirmPassword);
        //FirebaseApp.initializeApp(this);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncSignUpTask task = new AsyncSignUpTask();
                task.execute();

            }

        });
    }

    private class AsyncSignUpTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG,"doInBackground");
            if (validated){
                // add data into firebase
                mDatabaseReference.child("lecturer").push().setValue(lecturer);
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

                password.setText("");

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
                lecturer.setLecturerName(userName.getText().toString());
                if (lecturerID.getText().length() != 0 && lecturerID.getText().toString() !=""){
                    lecturer.setLecturerID(Integer.parseInt(lecturerID.getText().toString()));
                    if(password.getText().toString() !="" && password.getText().length() != 0){
                        if(comfirmPassowrd.getText().length() !=0 && comfirmPassowrd.getText().toString()!= ""){
                            if(password.getText().toString().equals(comfirmPassowrd.getText().toString())){
                                lecturer.setPassword(password.getText().toString());
                                validated = true;
                            }else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG);
                                toast.show();
                                validated = false;
                            }
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Enter comfirm password", Toast.LENGTH_LONG);
                            toast.show();
                            validated = false;

                        }
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG);
                        toast.show();
                        validated = false;
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter your lecturer ID", Toast.LENGTH_LONG);
                    toast.show();
                    validated = false;
                }

            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG);
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
