package com.example.afinal.room;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.example.afinal.R;

public class memberFragment extends Fragment {
    private ImageButton mimageButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_member, container, false);
        mimageButton = mview.findViewById(R.id.btn_add_member);


        mimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return mview;
    }

    private void openDialogAddMember(int gravity){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_member);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        EditText edtname = dialog.findViewById(R.id.txtname);
        EditText edtdate = dialog.findViewById(R.id.txtdate);
        RadioButton rman = dialog.findViewById(R.id.nam);
        RadioButton rgirl = dialog.findViewById(R.id.nu);
        EditText edtaddress = dialog.findViewById(R.id.txtaddress);
        EditText edtnumber = dialog.findViewById(R.id.txtnumber);
        EditText edtdatestart = dialog.findViewById(R.id.txtdatestart);
        Button btnaccept = dialog.findViewById(R.id.btn_accept);

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}