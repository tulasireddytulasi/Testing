package com.example.doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;

public class OTPActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean firstTime;
    private CardView cardView;
    private String codeBySystem;
    private PinView pinView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        cardView = findViewById(R.id.card);
        pinView = findViewById(R.id.pinview);
        textView = findViewById(R.id.timer);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                String code = pinView.getText().toString();
                if (!code.isEmpty()){
                    VerifyCode(code);
                   // startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                }
            }
        });

        CountDown();

        Intent intent1 = getIntent();
        String phone_No = intent1.getStringExtra("phone_no");
        Log.d("otp", phone_No);
        sendVerificationCodeToUser(phone_No);
        }

    private void CountDown() {
        CountDownTimer countDownTimer= new CountDownTimer(30000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("time", String.valueOf(millisUntilFinished));
                textView.setText("OTP Auto resend in "+(millisUntilFinished/1000) + " sec");
            }
            @Override
            public void onFinish() {
                Log.d("time", "time over...");
            }
        };
        countDownTimer.start();
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                 String code = phoneAuthCredential.getSmsCode();
                 if (code != null){
                   pinView.setText(code);
                   VerifyCode(code);
                 }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toasty.error(OTPActivity.this, e.getMessage()+ " Please Enter Correct Phone Number..", Toast.LENGTH_LONG, true).show();
                }
            };

    private void VerifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toasty.success(OTPActivity.this, "Login Success...", Toast.LENGTH_SHORT, true).show();
                            sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            firstTime = false;
                            editor.putBoolean("firstTime",firstTime);
                            editor.apply();
                            startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toasty.error(OTPActivity.this, "Verification Failed...", Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                });
    }



// =============================================
}
