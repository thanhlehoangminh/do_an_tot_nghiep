package com.example.afinal.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.afinal.R;
import com.example.afinal.option.CheckInternet;
import com.example.afinal.option.ProgressDialogNotify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class forgotPassWord extends AppCompatActivity {

    private TextView btnSignIn;
    private ImageButton btnBack, btnSubmit;
    private EditText txtAddress;
    private FirebaseAuth mAuth;
    private ProgressDialogNotify progress;
    private CheckInternet checkInternet;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private CountryCodePicker countryCodePicker;


    static final private String PHONE_PATTERN = "^[0-9]{10}$";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        checkInternet = new CheckInternet();
        progress = ProgressDialogNotify.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(forgotPassWord.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumber();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgotPassWord.this, SignInActivity.class));
            }
        });

    }

    private void Init() {
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        countryCodePicker = (CountryCodePicker) findViewById(R.id.countryCode);
    }

    public void verifyPhoneNumber() {
        txtAddress.setError(null);
        String getUserEnteredPhone = txtAddress.getText().toString().trim();

        boolean cancel = true;

        if (getUserEnteredPhone.matches("")) {
            txtAddress.setError(getString(R.string.error_field_address_empty));
            cancel = false;
        } else if (!getUserEnteredPhone.matches(PHONE_PATTERN)) {
            txtAddress.setError(getString(R.string.error_field_phone_required));
            cancel = false;
        }

        String _phoneNo = "+" + countryCodePicker.getSelectedCountryCode() + getUserEnteredPhone;

        if (cancel) {
            Intent intent = new Intent(forgotPassWord.this, verifyOTP.class);
            intent.putExtra("FG_USERPHONE", getUserEnteredPhone);
            intent.putExtra("FG_PHONENO", _phoneNo);
            startActivity(intent);
        }

    }


}
