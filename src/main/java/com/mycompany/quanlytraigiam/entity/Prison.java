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
    private int sucChuaToiDa;
    private int soLuongPhamNhanHienTai;
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
        this.sucChuaToiDa = Integer.parseInt(sucChuaToiDa);
        this.soLuongPhamNhanHienTai = Integer.parseInt(soLuongPhamNhanHienTai);
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
        if (soDienThoai == null || !soDienThoai.matches("^[0-9]{10,15}$")) {
        throw new IllegalArgumentException("Số điện thoại không hợp lệ");
        }
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.isEmpty() && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        this.email = email;
    }

    public int getSucChuaToiDa() {
        return sucChuaToiDa;
    }

    public void setSucChuaToiDa(String sucChuaToiDa) {
            try {
            this.sucChuaToiDa = Integer.parseInt(sucChuaToiDa);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Sức chứa tối đa phải là số nguyên", e);
        }
    }

    public int getSoLuongPhamNhanHienTai() {
        return soLuongPhamNhanHienTai;
    }

    public void setSoLuongPhamNhanHienTai(String soLuongPhamNhanHienTai) {
        try {
            this.soLuongPhamNhanHienTai = Integer.parseInt(soLuongPhamNhanHienTai);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số lượng phạm nhân hiện tại phải là số nguyên", e);
        }
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
    @Override
    public String toString() {
        return "Prison{" +
                "id='" + id + '\'' +
                ", tenTraiGiam='" + tenTraiGiam + '\'' +
                ", sucChuaToiDa=" + sucChuaToiDa +
                ", soLuongPhamNhanHienTai=" + soLuongPhamNhanHienTai +
                '}';
    }
}

