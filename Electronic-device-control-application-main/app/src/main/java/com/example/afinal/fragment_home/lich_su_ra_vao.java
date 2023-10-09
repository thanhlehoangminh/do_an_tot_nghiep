package com.example.afinal.fragment_home;

public class lich_su_ra_vao {
    private int img_background, img_icon_out_in;
    private String tv_ten_khach_thue, ten_khu_tro, gio_ra_vao, ngay_ra_vao;

    public int getImg_background() {
        return img_background;
    }

    public void setImg_background(int img_background) {
        this.img_background = img_background;
    }

    public int getImg_icon_out_in() {
        return img_icon_out_in;
    }

    public void setImg_icon_out_in(int img_icon_out_in) {
        this.img_icon_out_in = img_icon_out_in;
    }

    public String getTv_ten_khach_thue() {
        return tv_ten_khach_thue;
    }

    public void setTv_ten_khach_thue(String tv_ten_khach_thue) {
        this.tv_ten_khach_thue = tv_ten_khach_thue;
    }

    public String getTen_khu_tro() {
        return ten_khu_tro;
    }

    public void setTen_khu_tro(String ten_khu_tro) {
        this.ten_khu_tro = ten_khu_tro;
    }

    public String getGio_ra_vao() {
        return gio_ra_vao;
    }

    public void setGio_ra_vao(String gio_ra_vao) {
        this.gio_ra_vao = gio_ra_vao;
    }

    public String getNgay_ra_vao() {
        return ngay_ra_vao;
    }

    public void setNgay_ra_vao(String ngay_ra_vao) {
        this.ngay_ra_vao = ngay_ra_vao;
    }

    /* Constructor */

    public lich_su_ra_vao(int img_background, int img_icon_out_in, String tv_ten_khach_thue, String ten_khu_tro, String gio_ra_vao, String ngay_ra_vao) {
        this.img_background = img_background;
        this.img_icon_out_in = img_icon_out_in;
        this.tv_ten_khach_thue = tv_ten_khach_thue;
        this.ten_khu_tro = ten_khu_tro;
        this.gio_ra_vao = gio_ra_vao;
        this.ngay_ra_vao = ngay_ra_vao;
    }
}
