package com.example.afinal.fragment_home;

public class khu_tro {
    private int img_background, img_icon_map, img_icon_smart_lock, img_icon_connected;
    private String tv_ten_khu_tro, tv_address, tv_connected, tv_device;

    public int getImg_background() {
        return img_background;
    }

    public void setImg_background(int img_background) {
        this.img_background = img_background;
    }

    public int getImg_icon_map() {
        return img_icon_map;
    }

    public void setImg_icon_map(int img_icon_map) {
        this.img_icon_map = img_icon_map;
    }

    public int getImg_icon_smart_lock() {
        return img_icon_smart_lock;
    }

    public void setImg_icon_smart_lock(int img_icon_smart_lock) {
        this.img_icon_smart_lock = img_icon_smart_lock;
    }

    public int getImg_icon_connected() {
        return img_icon_connected;
    }

    public void setImg_icon_connected(int img_icon_connected) {
        this.img_icon_connected = img_icon_connected;
    }

    public String getTv_ten_khu_tro() {
        return tv_ten_khu_tro;
    }

    public void setTv_ten_khu_tro(String tv_ten_khu_tro) {
        this.tv_ten_khu_tro = tv_ten_khu_tro;
    }

    public String getTv_address() {
        return tv_address;
    }

    public void setTv_address(String tv_address) {
        this.tv_address = tv_address;
    }

    public String getTv_connected() {
        return tv_connected;
    }

    public void setTv_connected(String tv_connected) {
        this.tv_connected = tv_connected;
    }

    public String getTv_device() {
        return tv_device;
    }

    public void setTv_device(String tv_device) {
        this.tv_device = tv_device;
    }

    /* Constructor */

    public khu_tro(int img_background, int img_icon_map, int img_icon_smart_lock, int img_icon_connected, String tv_ten_khu_tro, String tv_address, String tv_connected, String tv_device) {
        this.img_background = img_background;
        this.img_icon_map = img_icon_map;
        this.img_icon_smart_lock = img_icon_smart_lock;
        this.img_icon_connected = img_icon_connected;
        this.tv_ten_khu_tro = tv_ten_khu_tro;
        this.tv_address = tv_address;
        this.tv_connected = tv_connected;
        this.tv_device = tv_device;
    }
}
