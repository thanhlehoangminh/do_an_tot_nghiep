package com.example.afinal.fragment_home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
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
    private final String ten_khu_tro[] = {"Khu trọ 1", "Khu trọ 2", "Khu trọ 3", "Khu trọ 4", "Khu trọ 5", "Khu trọ 6"};
    private final String ten_thiet_bi[] = {"DV01", "DV02", "DV02", "DV03", "DV04", "DV05", "DV06"};
    private final String dia_chi[] = {"1, Võ Văn Ngân", "2, Võ Văn Ngân", "3, Võ Văn Ngân", "4, Võ Văn Ngân", "5, Võ Văn Ngân", "6, Võ Văn Ngân"};
    public int i = 0;

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
        lv = home_view.findViewById(R.id.lv_khu_tro);
        ArrayList<khu_tro> khu_tro_list = new ArrayList<>();
        Khu_tro_arr_adapter khu_tro_arr_adapter = new Khu_tro_arr_adapter(fragmentHome.this, R.layout.layout_khu_tro, khu_tro_list);
        lv.setAdapter(khu_tro_arr_adapter);
        for (i = 0; i < ten_khu_tro.length; i++)
        {
            khu_tro_list.add(new khu_tro(R.drawable.background_home_1, R.drawable.icon_home_map, R.drawable.icon_smart_lock, R.drawable.icon_connected, ten_khu_tro[i], dia_chi[i], "Connected", ten_thiet_bi[i]));
        }
        getUserDataByPath(user_id);

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


}
