package com.example.afinal.fragment_home;

import android.annotation.SuppressLint;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.login.SignInActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class fragmentHome extends Fragment {
    private DatabaseReference firebase_home;
    private TextView user_name, date;
    private String get_phone, user_id, current_date;
    private ListView lv;
    private Button btn_add;
    public int i = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MainActivity home_main_activity = (MainActivity) getActivity();
        assert home_main_activity != null;
        View home_view = inflater.inflate(R.layout.fragment_home, container, false);
        user_name = home_view.findViewById(R.id.welcome_username);
        user_id = home_main_activity.getUserID();
        date = home_view.findViewById(R.id.tv_date);
        firebase_home = FirebaseDatabase.getInstance().getReference();
        current_date = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
        date.setText(current_date);
        btn_add = home_view.findViewById(R.id.btn_add_khu_tro);
        lv = home_view.findViewById(R.id.lv_khu_tro);
        ArrayList<them_khu_tro> khu_tro_list = new ArrayList<>();
        them_khu_tro_arr_adapter khu_tro_arr_adapter = new them_khu_tro_arr_adapter(fragmentHome.this, R.layout.layout_them_khu_tro, khu_tro_list);
//        lv.setAdapter(khu_tro_arr_adapter);
//        for (i = 0; i < ten_khu_tro.length; i++)
//        {
//            khu_tro_list.add(new khu_tro(R.drawable.background_home_1, R.drawable.icon_home_map, R.drawable.icon_smart_lock, R.drawable.icon_connected, ten_khu_tro[i], dia_chi[i], "Đã kết nối", ten_thiet_bi[i]));
//        }
        getUserDataByPath(user_id);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_khu_tro_layout);

                Window window = dialog.getWindow();
                if (window == null)
                {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);

                if (Gravity.CENTER == Gravity.CENTER)
                {
                    dialog.setCancelable(true);
                }
                else
                {
                    dialog.setCancelable(false);
                }

                EditText edtMaThietBi = dialog.findViewById(R.id.edit_text_ma_thiet_bi);
                EditText edtTenKhuTro = dialog.findViewById(R.id.edit_text_ten_khu_tro);
                EditText edtDiaChi = dialog.findViewById(R.id.edit_text_dia_chi);
                ImageButton imgBtnXacNhan = dialog.findViewById(R.id.img_btn_xac_nhan_tt);

                imgBtnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ma_thiet_bi, ten_khu_tro, dia_chi;

                        if (edtMaThietBi.length() == 0)
                        {
                            edtMaThietBi.setError("Nhập mã thiết bị");
                        }
                        else if (edtTenKhuTro.length() == 0)
                        {
                            edtTenKhuTro.setError("Nhập tên khu trọ");
                        }
                        else if (edtDiaChi.length() == 0)
                        {
                            edtDiaChi.setError("Nhập địa chỉ");
                        }
                        else
                        {
                            ma_thiet_bi = edtMaThietBi.getText().toString().trim();
                            ten_khu_tro = edtTenKhuTro.getText().toString().trim();
                            dia_chi = edtDiaChi.getText().toString().trim();

                            lv.setAdapter(khu_tro_arr_adapter);
                            khu_tro_list.add(new them_khu_tro(R.drawable.background_home_1, R.drawable.icon_home_map, R.drawable.icon_smart_lock, R.drawable.icon_connected, ten_khu_tro, dia_chi, "Đã kết nối", ma_thiet_bi));
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        return home_view;
    }
    private void getUserDataByPath(String user_id)
    {
        DatabaseReference firebase_home01 = FirebaseDatabase.getInstance().getReference("USER/PHONE");
        firebase_home01.child(user_id).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists())
            {
                DataSnapshot dataSnapshot = task.getResult();
                get_phone = String.valueOf(dataSnapshot.child("userPhone").getValue());
                firebase_home.child("USER/PHONE").child(get_phone).child("userName").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user_name.setText(Objects.requireNonNull(snapshot.getValue()).toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void openAddKhuTroDialog(int gravity)
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_khu_tro_layout);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity)
        {
            dialog.setCancelable(true);
        }
        else
        {
            dialog.setCancelable(false);
        }

        EditText edtMaThietBi = dialog.findViewById(R.id.edit_text_ma_thiet_bi);
        EditText edtTenKhuTro = dialog.findViewById(R.id.edit_text_ten_khu_tro);
        EditText edtDiaChi = dialog.findViewById(R.id.edit_text_dia_chi);
        ImageButton imgBtnXacNhan = dialog.findViewById(R.id.img_btn_xac_nhan_tt);

        imgBtnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma_thiet_bi, ten_khu_tro, dia_chi;

                ma_thiet_bi = edtMaThietBi.getText().toString();
                ten_khu_tro = edtTenKhuTro.getText().toString();
                dia_chi = edtDiaChi.getText().toString();

                ArrayList<them_khu_tro> khu_tro_list = new ArrayList<>();
                them_khu_tro_arr_adapter khu_tro_arr_adapter = new them_khu_tro_arr_adapter(fragmentHome.this, R.layout.layout_them_khu_tro, khu_tro_list);
                lv.setAdapter(khu_tro_arr_adapter);
                khu_tro_list.add(new them_khu_tro(R.drawable.background_home_1, R.drawable.icon_home_map, R.drawable.icon_smart_lock, R.drawable.icon_connected, ten_khu_tro, dia_chi, "Đã kết nối", ma_thiet_bi));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
