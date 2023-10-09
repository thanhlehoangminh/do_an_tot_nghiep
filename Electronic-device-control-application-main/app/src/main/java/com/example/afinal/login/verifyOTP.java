package com.example.afinal.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.afinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyOTP extends AppCompatActivity {

    private ImageButton btnBack, btnSubmit;
    private PinView txtOTP;
    private TextView btnSignIn;
    private String codeBySystem;
    private FirebaseAuth mAuth;

    private String userPhone, phoneNo;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.enter_otp_code);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        mAuth = FirebaseAuth.getInstance();
        userPhone = getIntent().getStringExtra("FG_USERPHONE");
        phoneNo = getIntent().getStringExtra("FG_PHONENO");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(verifyOTP.this, SignInActivity.class));
                finish();
            }
        });

        sendVerificationCodeToUser(phoneNo);
    }

    public void Init() {
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
        txtOTP = (PinView) findViewById(R.id.pin_view);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
    }

    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
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
                    if (code != null) {
                        txtOTP.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(verifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            sendUserToResetPassword();

//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                Toast.makeText(verifyOTP.this, "Verifycation Not Completed! Try again",Toast.LENGTH_LONG).show();
//                            }
//                            else
//                            {
//                                Toast.makeText(verifyOTP.this, "Phone is registered",Toast.LENGTH_LONG).show();
//                            }
                        }
                    }
                });
    }

    private void sendUserToResetPassword() {
        Intent intent = new Intent(verifyOTP.this, resetPassword.class);
        intent.putExtra("RS_USERPHONE", userPhone);
        startActivity(intent);
    }

    public void clickToResetPassword(View view) {
        String code = txtOTP.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}
