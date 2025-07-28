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
    private Integer sucChuaToiDa;
    private Integer soLuongPhamNhanHienTai;
    private Date ngayThanhLap;
    private String quanLiTruong;

    public Prison() {
    }

    public Prison(String id, String maTraiGiam, String tenTraiGiam, String diaChi, String soDienThoai, String email,
            Integer sucChuaToiDa, Integer soLuongPhamNhanHienTai, Date ngayThanhLap, String quanLiTruong) {
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
        setSoDienThoai(soDienThoai); // Sử dụng setter để kiểm tra định dạng
        setEmail(email); // Sử dụng setter để kiểm tra định dạng
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
        if (maTraiGiam == null || maTraiGiam.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã trại giam không được trống");
        }
        this.maTraiGiam = maTraiGiam.trim();
    }

    public String getTenTraiGiam() {
        return tenTraiGiam;
    }

    public void setTenTraiGiam(String tenTraiGiam) {
        if (tenTraiGiam == null || tenTraiGiam.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên trại giam không được trống");
        }
        this.tenTraiGiam = tenTraiGiam.trim();
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được trống");
        }
        this.diaChi = diaChi.trim();
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        if (soDienThoai == null || !soDienThoai.matches("^[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ, phải là 10-15 chữ số");
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

    public Integer getSucChuaToiDa() {
        return sucChuaToiDa;
    }

    public void setSucChuaToiDa(Integer sucChuaToiDa) {
        if (sucChuaToiDa < 0) {
            throw new IllegalArgumentException("Sức chứa tối đa phải là số nguyên không âm");
        }
        this.sucChuaToiDa = sucChuaToiDa;
    }

    public Integer getSoLuongPhamNhanHienTai() {
        return soLuongPhamNhanHienTai;
    }

    public void setSoLuongPhamNhanHienTai(Integer soLuongPhamNhanHienTai) {
        if (soLuongPhamNhanHienTai < 0) {
            throw new IllegalArgumentException("Số lượng phạm nhân hiện tại phải là số nguyên không âm");
        }
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
        if (quanLiTruong == null || quanLiTruong.trim().isEmpty()) {
            throw new IllegalArgumentException("Quản lý trưởng không được trống");
        }
        this.quanLiTruong = quanLiTruong.trim();
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String ngayThanhLapStr = (ngayThanhLap != null) ? dateFormat.format(ngayThanhLap) : "N/A";
        return "Prison{" +
                "id='" + id + '\'' +
                ", tenTraiGiam='" + tenTraiGiam + '\'' +
                ", sucChuaToiDa=" + sucChuaToiDa +
                ", soLuongPhamNhanHienTai=" + soLuongPhamNhanHienTai +
                ", ngayThanhLap=" + ngayThanhLapStr +
                ", quanLiTruong='" + quanLiTruong + '\'' +
                '}';
    }
}