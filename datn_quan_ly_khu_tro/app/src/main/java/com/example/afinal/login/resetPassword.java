package com.example.afinal.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.afinal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class resetPassword extends AppCompatActivity {

    private EditText txtNewPassword, txtConfirmNewPassword;
    private Button openEyeNewPass, openEyeConfirmNewPass;
    private boolean checkEye;
    private boolean checkEyeConfirm;
    private ImageButton btnSubmit;
    private TextView btnSignIn;

    private DatabaseReference mData;

    private String getUserPhoneChange;

    private boolean checkEdt = true;

    static final private String PASSWORD_PATTERN = "^[a-zA-z0-9]{6,20}$";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.reset_password);

        Init();

        mData = FirebaseDatabase.getInstance().getReference();

        getUserPhoneChange = getIntent().getStringExtra("RS_USERPHONE");

        txtConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getNewPassword = txtNewPassword.getText().toString().trim();
                String getConfirmNewPassword = txtConfirmNewPassword.getText().toString().trim();
                if (!getConfirmNewPassword.equals(getNewPassword)) {
                    txtConfirmNewPassword.setError("Password Not Match");
                    checkEdt = false;
                } else {
                    checkEdt = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        closePassword();
        openEyeNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEye) {
                    closePassword();
                    checkEye = false;
                } else {
                    openPassword();
                    checkEye = true;
                }
            }
        });

        closePasswordConfirm();
        openEyeConfirmNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEyeConfirm) {
                    closePasswordConfirm();
                    checkEyeConfirm = false;
                } else {
                    openPasswordConfirm();
                    checkEyeConfirm = true;
                }
            }
        });

//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetPassword();
//            }
//        });

    }

    private void Init() {
        txtNewPassword = (EditText) findViewById(R.id.txtNewPassword);
        txtConfirmNewPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        openEyeNewPass = (Button) findViewById(R.id.openEyeNewPass);
        openEyeConfirmNewPass = (Button) findViewById(R.id.openEyeConfirmNewPass);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnSubmit = (ImageButton) findViewById(R.id.btnSubmit);
    }

    public void resetPassword(View view) {
        txtNewPassword.setError(null);
        txtConfirmNewPassword.setError(null);

        String _getNewPassword = txtNewPassword.getText().toString().trim();
        String _getConfrimNewPassword = txtConfirmNewPassword.getText().toString().trim();
        boolean cancel = true;

        if (_getNewPassword.matches("")) {
            txtNewPassword.setError(getString(R.string.error_field_password_empty));
            cancel = false;
        } else if (!_getNewPassword.matches(PASSWORD_PATTERN)) {
            txtNewPassword.setError(getString(R.string.error_field_password_required));
            cancel = false;
        }

        if (_getConfrimNewPassword.matches("")) {
            txtConfirmNewPassword.setError(getString(R.string.error_field_password_empty));
            cancel = false;
        } else if (!_getConfrimNewPassword.matches(PASSWORD_PATTERN)) {
            txtConfirmNewPassword.setError(getString(R.string.error_field_password_required));
            cancel = false;
        }

        if (cancel == false || checkEdt == false) {
            return;
        }

        if (cancel == true && checkEdt == true) {
            mData.child("USER").child("PHONE").child(getUserPhoneChange).child("userPassword").setValue(_getConfrimNewPassword);
            startActivity(new Intent(resetPassword.this, SignInActivity.class));
        }

    }

    private void closePassword() {
        openEyeNewPass.setBackgroundResource(R.drawable.eye_open);
        txtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtNewPassword.setSelection(txtNewPassword.getText().length());
    }

    private void openPassword() {
        openEyeNewPass.setBackgroundResource(R.drawable.eye_close);
        txtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        txtNewPassword.setSelection(txtNewPassword.getText().length());
    }

    private void closePasswordConfirm() {
        openEyeConfirmNewPass.setBackgroundResource(R.drawable.eye_open);
        txtConfirmNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtConfirmNewPassword.setSelection(txtConfirmNewPassword.getText().length());
    }


    private void openPasswordConfirm() {
        openEyeConfirmNewPass.setBackgroundResource(R.drawable.eye_close);
        txtConfirmNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        txtConfirmNewPassword.setSelection(txtConfirmNewPassword.getText().length());
    }
}
