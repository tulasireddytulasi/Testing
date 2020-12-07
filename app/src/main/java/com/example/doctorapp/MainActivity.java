package com.example.doctorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean firstTime;
    Button button;
    EditText editText;
    String phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);
        if(firstTime) {

        editText  =findViewById(R.id.phone_no);
        button = findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_no = editText.getText().toString();
                if (phone_no.length() >= 10){
                    String number = "+91"+phone_no;
                    Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                    intent.putExtra("phone_no", number);
                    startActivity(intent);
                    //Toasty.info(MainActivity.this, number, Toast.LENGTH_SHORT, true).show();
                }else {
                    Toasty.warning(MainActivity.this, "Please Enter your Phone No ...", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        }else {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}