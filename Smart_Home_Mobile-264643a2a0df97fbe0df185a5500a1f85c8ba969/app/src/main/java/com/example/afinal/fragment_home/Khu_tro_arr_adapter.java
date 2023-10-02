package com.example.afinal.fragment_home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.afinal.R;

import java.util.ArrayList;

public class Khu_tro_arr_adapter extends ArrayAdapter<khu_tro>
{
    private static final int resource = R.layout.layout_khu_tro;
    Fragment context;
    int id_layout;
    ArrayList<khu_tro> khu_tro_list;
    /* Tao constructor cho fragmentHome */

    public Khu_tro_arr_adapter(Fragment context, int id_layout, ArrayList<khu_tro> khu_tro_list) {
        super(context.getContext(), id_layout, khu_tro_list);
        this.context = context;
        this.id_layout = id_layout;
        this.khu_tro_list = khu_tro_list;
    }
    /* Sap xep data */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Tao de
        LayoutInflater mInflater = context.getLayoutInflater();
        // Dat id layout len de
        convertView = mInflater.inflate(id_layout, null);
        // lay 1 phan tu trong mang
        khu_tro mKhuTro = khu_tro_list.get(position);
        // khai bao, tham chieu ID va hien thi len ImageView
        ImageView img_background = convertView.findViewById(R.id.imgView_background);
        img_background.setImageResource(mKhuTro.getImg_background());
        ImageView img_icon_map = convertView.findViewById(R.id.imgView_icon_map);
        img_icon_map.setImageResource(mKhuTro.getImg_icon_map());
        ImageView img_icon_connected = convertView.findViewById(R.id.imgView_icon_connected);
        img_icon_connected.setImageResource(mKhuTro.getImg_icon_connected());
        ImageView img_icon_smart_lock = convertView.findViewById(R.id.imgView_icon_smart_lock);
        img_icon_smart_lock.setImageResource(mKhuTro.getImg_icon_smart_lock());
        // Khai bao, tham chieu ID va hien thi text view
        TextView tv_ten_khu_tro = convertView.findViewById(R.id.tv_ten_khu_tro);
        tv_ten_khu_tro.setText(mKhuTro.getTv_ten_khu_tro());
        TextView tv_address = convertView.findViewById(R.id.tv_address);
        tv_address.setText(mKhuTro.getTv_address());
        TextView tv_connected = convertView.findViewById(R.id.tv_connected);
        tv_connected.setText(mKhuTro.getTv_connected());
        TextView tv_device = convertView.findViewById(R.id.tv_device);
        tv_device.setText(mKhuTro.getTv_device());
        return convertView;
    }
}
