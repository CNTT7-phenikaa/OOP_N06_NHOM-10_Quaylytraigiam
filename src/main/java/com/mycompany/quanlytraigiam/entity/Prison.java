/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */

@XmlRootElement(name = "Prison")
@XmlAccessorType(XmlAccessType.FIELD)
public class Prison {
    private String id;
    private String maTraiGiam;
    private String tenTraiGiam;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String sucChuaToiDa;
    private String soLuongPhamNhanHienTai;
    private Date ngayThanhLap;
    private String quanLiTruong;
    
    public Prison() {}
    public Prison(String id, String maTraiGiam, String tenTraiGiam, String diaChi, String soDienThoai, String email, String sucChuaToiDa, String soLuongPhamNhanHienTai, Date ngayThanhLap, String quanLiTruong) {
        this.id = id;
        this.maTraiGiam = maTraiGiam;
        this.tenTraiGiam = tenTraiGiam;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.sucChuaToiDa = sucChuaToiDa;
        this.soLuongPhamNhanHienTai = soLuongPhamNhanHienTai;
        this.ngayThanhLap = ngayThanhLap;
        this.quanLiTruong = quanLiTruong;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaTraiGiam() {
        return maTraiGiam;
    }

    public void setMaTraiGiam(String maTraiGiam) {
        this.maTraiGiam = maTraiGiam;
    }

    public String getTenTraiGiam() {
        return tenTraiGiam;
    }

    public void setTenTraiGiam(String tenTraiGiam) {
        this.tenTraiGiam = tenTraiGiam;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSucChuaToiDa() {
        return sucChuaToiDa;
    }

    public void setSucChuaToiDa(String sucChuaToiDa) {
        this.sucChuaToiDa = sucChuaToiDa;
    }

    public String getSoLuongPhamNhanHienTai() {
        return soLuongPhamNhanHienTai;
    }

    public void setSoLuongPhamNhanHienTai(String soLuongPhamNhanHienTai) {
        this.soLuongPhamNhanHienTai = soLuongPhamNhanHienTai;
    }

    public Date getNgayThanhLap() {
        return ngayThanhLap;
    }

    public void setNgayThanhLap(Date ngayThanhLap) {
        this.ngayThanhLap = ngayThanhLap;
    }

    public String getQuanLiTruong() {
        return quanLiTruong;
    }

    public void setQuanLiTruong(String quanLiTruong) {
        this.quanLiTruong = quanLiTruong;
    }
}
