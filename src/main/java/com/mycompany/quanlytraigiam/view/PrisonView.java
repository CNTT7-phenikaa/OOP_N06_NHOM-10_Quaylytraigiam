/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quanlytraigiam.view;

import com.mycompany.quanlytraigiam.controller.PrisonController;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.raven.chart.Chart;
import com.raven.chart.ModelChart;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PC
 */
public class PrisonView extends javax.swing.JFrame {

    private String[] columnNames = { "ID", "Mã trại giam", "Tên trại giam", "Địa chỉ", "Sức chứa tối đa",
            "Số lượng phạm nhân hiện tại", "Quản lý trưởng", "Số điện thoại", "Email", "Ngày thành lập" };
    private JTable tablePrison;
    private JTextField FieldMaTraiGiam;
    private JTextField FieldTenTraiGiam;
    private JTextField FieldQuanLiTruong;
    private JTextField FieldEmail;

    private javax.swing.JButton btnUndo;
    private javax.swing.JButton btnBack;
    private javax.swing.JTextField FieldSearchMain;

    public PrisonView() {
        initComponents(); //
    }

    public void showListPrisons(List<Prison> list) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày tháng

        list.stream().forEach(prison -> {
            // Chuyển đổi ngayThanhLap thành String định dạng dd/MM/yyyy
            String formattedDate = (prison.getNgayThanhLap() != null)
                    ? dateFormat.format(prison.getNgayThanhLap())
                    : "N/A";
            model.addRow(new Object[] {
                    prison.getId(),
                    prison.getMaTraiGiam(),
                    prison.getTenTraiGiam(),
                    prison.getDiaChi(),
                    prison.getSucChuaToiDa(),
                    prison.getSoLuongPhamNhanHienTai(),
                    prison.getQuanLiTruong(),
                    prison.getSoDienThoai(),
                    prison.getEmail(),
                    formattedDate // Thêm cột Ngày thành lập
            });
        });
        tablePrison.setModel(model);
        customizeTableAppearance();
    }

    private void customizeTableAppearance() {

        tablePrison.setRowHeight(30);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tablePrison.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tablePrison.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        tablePrison.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablePrison.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablePrison.getColumnModel().getColumn(2).setPreferredWidth(150);
    }

    public Prison getPrisonInfo() {
        if (!validatePrisonFields()) {
            return null;
        }

        try {
            Prison prison = new Prison();

            // Kiểm tra và gán ID (nếu có)
            if (FieldID.getText() != null && !FieldID.getText().trim().isEmpty()) {
                prison.setId(FieldID.getText().trim());
            }

            // Gán các trường văn bản
            prison.setMaTraiGiam(FieldMaTraiGiam.getText().trim());
            prison.setTenTraiGiam(FieldTenTraiGiam.getText().trim());
            prison.setDiaChi(TextAreaAddress.getText().trim());
            prison.setQuanLiTruong(FieldQuanLiTruong.getText().trim());
            prison.setSoDienThoai(FieldPhone.getText().trim());
            prison.setEmail(FieldEmail.getText().trim());

            // Xử lý số nguyên cho sức chứa
            if (!sucChuaToiDa.getText().trim().isEmpty()) {
                prison.setSucChuaToiDa(Integer.parseInt(sucChuaToiDa.getText().trim()));
            }

            // Xử lý số nguyên cho số lượng phạm nhân
            if (!soLuongPhamNhanHienTai.getText().trim().isEmpty()) {
                prison.setSoLuongPhamNhanHienTai(Integer.parseInt(soLuongPhamNhanHienTai.getText().trim()));
            }

            // Xử lý ngày thành lập
            Date ngayThanhLap = BirthdayChooser.getDate();
            if (ngayThanhLap != null) {
                prison.setNgayThanhLap(ngayThanhLap);
            } else {
                showMessage("Ngày thành lập không được để trống");
                return null;
            }

            // Kiểm tra định dạng email cơ bản
            if (!FieldEmail.getText().trim().isEmpty() &&
                    (!FieldEmail.getText().trim().contains("@") || !FieldEmail.getText().trim().contains("."))) {
                showMessage("Email không hợp lệ, phải chứa @ và dấu chấm");
                return null;
            }

            return prison;
        } catch (NumberFormatException e) {
            showMessage("Sức chứa hoặc số lượng phạm nhân phải là số nguyên hợp lệ");
            return null;
        } catch (Exception e) {
            showMessage("Lỗi khi lấy thông tin trại giam: " + e.getMessage());
            return null;
        }
    }

    private boolean validatePrisonFields() {
        if (FieldMaTraiGiam.getText().trim().isEmpty()) {
            showMessage("Mã trại giam không được trống");
            return false;
        }
        if (FieldTenTraiGiam.getText().trim().isEmpty()) {
            showMessage("Tên trại giam không được trống");
            return false;
        }
        if (TextAreaAddress.getText().trim().isEmpty()) {
            showMessage("Địa chỉ không được trống");
            return false;
        }
        if (sucChuaToiDa.getText().trim().isEmpty()) {
            showMessage("Sức chứa không được trống");
            return false;
        }
        if (soLuongPhamNhanHienTai.getText().trim().isEmpty()) {
            showMessage("Số lượng phạm nhân không được trống");
            return false;
        }
        if (FieldQuanLiTruong.getText().trim().isEmpty()) {
            showMessage("Quản lý trưởng không được trống");
            return false;
        }
        if (FieldEmail.getText().trim().isEmpty()) {
            showMessage("Email không được trống");
            return false;
        }
        if (FieldPhone.getText().trim().isEmpty()) {
            showMessage("Số điện thoại không được trống");
            return false;
        }
        return true;
    }

    public void showPrison(Prison prison) {
        FieldID.setText(prison.getId());
        FieldMaTraiGiam.setText(prison.getMaTraiGiam());
        FieldTenTraiGiam.setText(prison.getTenTraiGiam());
        TextAreaAddress.setText(prison.getDiaChi());
        sucChuaToiDa.setText(String.valueOf(prison.getSucChuaToiDa()));
        soLuongPhamNhanHienTai.setText(String.valueOf(prison.getSoLuongPhamNhanHienTai()));

        if (prison.getNgayThanhLap() != null) {
            BirthdayChooser.setDate(prison.getNgayThanhLap());
        }

        FieldQuanLiTruong.setText(prison.getQuanLiTruong());
        FieldPhone.setText(prison.getSoDienThoai());
        FieldEmail.setText(prison.getEmail());

        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnAdd.setEnabled(false);
    }

    public void clearPrisonInfo() {
        FieldID.setText("");
        FieldMaTraiGiam.setText("");
        FieldTenTraiGiam.setText("");
        TextAreaAddress.setText("");
        sucChuaToiDa.setText("");
        soLuongPhamNhanHienTai.setText("");
        BirthdayChooser.setDate(null);
        FieldQuanLiTruong.setText("");
        FieldPhone.setText("");
        FieldEmail.setText("");

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnAdd.setEnabled(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupSex = new javax.swing.ButtonGroup();
        btnGroupSort = new javax.swing.ButtonGroup();
        btnGroupSearch = new javax.swing.ButtonGroup();
        SearchDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        CheckBoxName = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        CheckBoxAddress = new javax.swing.JCheckBox();
        CheckBoxWarden = new javax.swing.JCheckBox();
        CheckBoxPrisonId = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        FieldSearch = new javax.swing.JTextField();
        btnCancelDialog = new javax.swing.JButton();
        btnSearchDialog = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnCancelSearch = new javax.swing.JButton();
        btnSort = new javax.swing.JButton();
        btnUndo = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        FieldSumFamily = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        FieldSum = new javax.swing.JTextField();
        CheckBoxSortName = new javax.swing.JCheckBox();
        CheckBoxSortCapacity = new javax.swing.JCheckBox();
        CheckBoxSortWarden = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePrison = new javax.swing.JTable();
        FieldMaTraiGiam = new javax.swing.JTextField();
        FieldTenTraiGiam = new javax.swing.JTextField();
        ComboBoxRole = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        FieldIDFamily = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaAddress = new javax.swing.JTextArea();
        BirthdayChooser = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        FieldPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        FieldID = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        sucChuaToiDa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        soLuongPhamNhanHienTai = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        FieldQuanLiTruong = new javax.swing.JTextField();
        FieldEmail = new javax.swing.JTextField();

        SearchDialog.setResizable(false);
        SearchDialog.setSize(new java.awt.Dimension(511, 390));

        jPanel3.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 3, 24));
        jLabel18.setText("Tìm kiếm");
        jPanel3.add(jLabel18);
        jLabel18.setBounds(210, 40, 110, 29);

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel19.setText("Chọn tiêu chí tìm kiếm:");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(40, 190, 290, 29);

        jLabel20.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/search.png")));
        jPanel3.add(jLabel20);
        jLabel20.setBounds(30, 130, 30, 30);

        btnGroupSearch.add(CheckBoxName);
        CheckBoxName.setFont(new java.awt.Font("Times New Roman", 0, 18));
        CheckBoxName.setText("Tên trại giam");
        CheckBoxName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxName.addActionListener((java.awt.event.ActionEvent evt) -> {
        });
        jPanel3.add(CheckBoxName);
        CheckBoxName.setBounds(140, 230, 140, 20);
        CheckBoxName.setOpaque(false);

        btnGroupSearch.add(CheckBoxAddress);
        CheckBoxAddress.setFont(new java.awt.Font("Times New Roman", 0, 18));
        CheckBoxAddress.setText("Địa chỉ");
        CheckBoxAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxAddress.addActionListener((java.awt.event.ActionEvent evt) -> {
        });
        jPanel3.add(CheckBoxAddress);
        CheckBoxAddress.setBounds(390, 230, 100, 20);
        CheckBoxAddress.setOpaque(false);

        btnGroupSearch.add(CheckBoxWarden);
        CheckBoxWarden.setFont(new java.awt.Font("Times New Roman", 0, 18));
        CheckBoxWarden.setText("Tên quản lý");
        CheckBoxWarden.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxWarden.addActionListener((java.awt.event.ActionEvent evt) -> {
        });
        jPanel3.add(CheckBoxWarden);
        CheckBoxWarden.setBounds(270, 230, 200, 20);
        CheckBoxWarden.setOpaque(false);

        btnGroupSearch.add(CheckBoxPrisonId);
        CheckBoxPrisonId.setFont(new java.awt.Font("Times New Roman", 0, 18));
        CheckBoxPrisonId.setText("Mã trại giam");
        CheckBoxPrisonId.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxPrisonId);
        CheckBoxPrisonId.setBounds(20, 230, 120, 25);
        CheckBoxPrisonId.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel21.setText("Nhập nội dung tìm kiếm:");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(40, 90, 290, 29);

        FieldSearch.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 102)));
        jPanel3.add(FieldSearch);
        FieldSearch.setBounds(60, 130, 400, 40);
        FieldSearch.setOpaque(false);

        btnCancelDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnCancelDialog.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnCancelDialog.setText("Hủy");
        btnCancelDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnCancelDialog);
        btnCancelDialog.setBounds(290, 270, 150, 50);
        btnCancelDialog.setBorder(new RoundedBorder(20));
        btnCancelDialog.setOpaque(false);

        btnSearchDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnSearchDialog.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnSearchDialog.setText("Tìm kiếm");
        btnSearchDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnSearchDialog);
        btnSearchDialog.setBounds(70, 270, 140, 50);
        btnSearchDialog.setBorder(new RoundedBorder(20));
        btnSearchDialog.setOpaque(false);

        jLabel22.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/viewSearchView.png")));
        jLabel22.setText("=");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(-10, 0, 520, 360);

        javax.swing.GroupLayout SearchDialogLayout = new javax.swing.GroupLayout(SearchDialog.getContentPane());
        SearchDialog.getContentPane().setLayout(SearchDialogLayout);
        SearchDialogLayout.setHorizontalGroup(
                SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE));
        SearchDialogLayout.setVerticalGroup(
                SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý dân cư trong khu vực");
        setName("Quản lý dân cư trong khu vực");
        setSize(new java.awt.Dimension(1276, 681));

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jLabel16.setText("Chọn tiêu chí sắp xếp:");

        btnCancelSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnCancelSearch.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnCancelSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelSearch.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/cancel.png")));
        btnCancelSearch.setText("Hủy tìm kiếm");
        btnCancelSearch.setBorder(null);
        btnCancelSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelSearch.addActionListener((java.awt.event.ActionEvent evt) -> btnCancelSearch.setEnabled(false));
        btnCancelSearch.setEnabled(false);

        btnSort.setBackground(new java.awt.Color(0, 0, 102));
        btnSort.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnSort.setForeground(new java.awt.Color(255, 255, 255));
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/mycompany/quanlytraigiam/view/sorting.png"));
        Image scaledImg = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        btnSort.setIcon(new ImageIcon(scaledImg));
        btnSort.setText("Sắp xếp");
        btnSort.setBorder(null);
        btnSort.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnUndo.setBackground(new java.awt.Color(0, 0, 102));
        btnUndo.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnUndo.setForeground(new java.awt.Color(255, 255, 255));
        btnUndo.setText("Hoàn tác");
        btnUndo.setBorder(null);
        btnUndo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUndo.setBounds(1070, 430, 170, 44);

        btnBack.setBackground(new java.awt.Color(0, 0, 102));
        btnBack.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/Undo.png")));
        btnBack.setText("Quay lại");
        btnBack.setBorder(null);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(btnBack);
        btnBack.setBounds(0, 620, 230, 60);
        btnBack.setOpaque(true);

        btnSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnSearch.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/search.png")));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setBorder(null);
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/com/mycompany/quanlytraigiam/view/logo.png"));
        Image image = originalIcon.getImage(); // lấy ảnh gốc
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // resize
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // tạo icon mới từ ảnh đã resize
        jLabel14.setIcon(scaledIcon); // gán vào jLabel
        jLabel14.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jLabel12.setText("Tổng số hộ dân:");

        FieldSumFamily.setFont(new java.awt.Font("Times New Roman", 1, 16));
        FieldSumFamily.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumFamily
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumFamily.setEditable(false);

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jLabel17.setText("Tổng số trại giam:");

        FieldSum.setFont(new java.awt.Font("Times New Roman", 1, 16));
        FieldSum.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSum.setEditable(false);

        CheckBoxSortName.setBackground(new java.awt.Color(0, 102, 204, 175));
        btnGroupSort.add(CheckBoxSortName);
        CheckBoxSortName.setFont(new java.awt.Font("Times New Roman", 0, 16));
        CheckBoxSortName.setText("Sắp xếp theo tên");
        CheckBoxSortName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortName.setOpaque(false);

        btnGroupSort.add(CheckBoxSortCapacity);
        CheckBoxSortCapacity.setFont(new java.awt.Font("Times New Roman", 0, 16));
        CheckBoxSortCapacity.setText("Sắp xếp theo sức chứa");
        CheckBoxSortCapacity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortCapacity.setOpaque(false);

        btnGroupSort.add(CheckBoxSortWarden);
        CheckBoxSortWarden.setFont(new java.awt.Font("Times New Roman", 0, 16));
        CheckBoxSortWarden.setText("Sắp xếp theo tên quản lý trưởng");
        CheckBoxSortWarden.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortWarden.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 176,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(CheckBoxSortName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(CheckBoxSortCapacity,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(CheckBoxSortWarden,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 187,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 236,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 230,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 230,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(CheckBoxSortName)
                                .addGap(3, 3, 3)
                                .addComponent(CheckBoxSortCapacity)
                                .addGap(6, 6, 6)
                                .addComponent(CheckBoxSortWarden)
                                .addGap(6, 6, 6)
                                .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel17)
                                        .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));

        FieldSumFamily.setOpaque(false);
        FieldSum.setOpaque(false);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 230, 681);

        tablePrison.setFont(new java.awt.Font("Times New Roman", 0, 18));
        tablePrison.setModel(new DefaultTableModel(
                new Object[][] {},
                columnNames));
        tablePrison.setRowHeight(30);
        jScrollPane1.setViewportView(tablePrison);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(240, 420, 1030, 260);

        FieldMaTraiGiam.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldMaTraiGiam
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldMaTraiGiam);
        FieldMaTraiGiam.setBounds(390, 100, 250, 40);
        FieldMaTraiGiam.setOpaque(false);

        FieldTenTraiGiam.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldTenTraiGiam
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldTenTraiGiam);
        FieldTenTraiGiam.setBounds(390, 170, 250, 40);
        FieldTenTraiGiam.setOpaque(false);

        FieldQuanLiTruong.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldQuanLiTruong
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldQuanLiTruong);
        FieldQuanLiTruong.setBounds(970, 100, 260, 40);
        FieldQuanLiTruong.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel1.setText("Quản lý trưởng:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(830, 100, 140, 40);

        FieldIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldIDFamily
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldIDFamily);
        FieldIDFamily.setBounds(390, 100, 250, 40);
        FieldIDFamily.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel2.setText("Địa chỉ:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(900, 170, 70, 40);

        TextAreaAddress.setBackground(new java.awt.Color(255, 255, 255, 0));
        TextAreaAddress.setColumns(20);
        TextAreaAddress.setFont(new java.awt.Font("Times New Roman", 0, 20));
        TextAreaAddress.setRows(5);
        TextAreaAddress
                .setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(TextAreaAddress);
        TextAreaAddress.setOpaque(false);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(970, 160, 260, 60);

        BirthdayChooser.setBackground(new java.awt.Color(0, 204, 255));
        BirthdayChooser.setDateFormatString("dd/MM/yyyy");
        BirthdayChooser.setFont(new java.awt.Font("Times New Roman", 0, 20));
        jPanel1.add(BirthdayChooser);
        BirthdayChooser.setBounds(390, 310, 160, 40);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel5.setText("Tên trại giam:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(270, 170, 120, 40);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel7.setText("Ngày thành lập:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(260, 310, 130, 40);

        FieldPhone.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldPhone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldPhone);
        FieldPhone.setBounds(1030, 310, 200, 40);
        FieldPhone.setOpaque(false);

        FieldEmail.setFont(new java.awt.Font("Times New Roman", 0, 20));
        FieldEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldEmail);
        FieldEmail.setBounds(970, 270, 260, 40);
        FieldEmail.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel8.setText("Số điện thoại:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(910, 310, 120, 40);

        FieldID.setEditable(false);
        FieldID.setFont(new java.awt.Font("Times New Roman", 1, 20));
        FieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FieldID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 102)));
        jPanel1.add(FieldID);
        FieldID.setBounds(390, 40, 70, 40);
        FieldID.setOpaque(false);
        FieldID.setVisible(false);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("<html>Quản Lý Trại Giam<br>");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(620, 10, 320, 80);

        btnAdd.setBackground(new java.awt.Color(0, 0, 102));
        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/quanlytraigiam/view/add.png")));
        btnAdd.setText("Thêm");
        btnAdd.setBorder(null);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnEdit.setBackground(new java.awt.Color(0, 0, 102));
        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/Edit.png")));
        btnEdit.setText("Cập nhật");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDelete.setBackground(new java.awt.Color(0, 0, 102));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/delete.png")));
        btnDelete.setText("Xóa");
        btnDelete.setBorder(null);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnClear.setBackground(new java.awt.Color(0, 0, 102));
        btnClear.setFont(new java.awt.Font("Times New Roman", 0, 18));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/com/mycompany/quanlytraigiam/view/trash.png")));
        btnClear.setText("Làm mới");
        btnClear.setBorder(null);
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel11.setText("Mã trại giam:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(270, 100, 120, 40);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel6.setText("ID:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(350, 50, 30, 21);

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel23.setText("Email:");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(910, 270, 60, 21);

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel24.setText("Sức chứa tối đa:");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(250, 240, 140, 21);

        sucChuaToiDa.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jPanel1.add(sucChuaToiDa);
        sucChuaToiDa.setBounds(390, 240, 100, 27);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jLabel4.setText("Số lượng phạm nhân hiện tại:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(247, 280, 240, 21);

        soLuongPhamNhanHienTai.setFont(new java.awt.Font("Times New Roman", 1, 18));
        jPanel1.add(soLuongPhamNhanHienTai);
        soLuongPhamNhanHienTai.setBounds(490, 280, 64, 27);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "/com/mycompany/quanlytraigiam/view/Lovepik_com-500330964-blue-blazed-background.jpg")));
        jLabel9.setText("Số lượng phạm nhân:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 0, 1640, 890);

        jPanel1.add(btnAdd);
        btnAdd.setBounds(330, 370, 170, 44);

        jPanel1.add(btnEdit);
        btnEdit.setBounds(580, 370, 170, 44);

        jPanel1.add(btnDelete);
        btnDelete.setBounds(830, 370, 170, 44);

        jPanel1.add(btnClear);
        btnClear.setBounds(1070, 370, 170, 44);

        jPanel1.add(btnUndo);
        btnUndo.setBounds(1070, 430, 170, 44);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1276, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE));

        pack();
    }

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnClearActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnEditActionPerformed

    private void FieldNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldNameActionPerformed

    private void FieldCMTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldCMTActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldCMTActionPerformed

    private void FieldIDFamilyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldIDFamilyActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldIDFamilyActionPerformed

    private void FieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldPhoneActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldPhoneActionPerformed

    private void FieldIDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldIDActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldIDActionPerformed

    private void CheckBoxNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CheckBoxNameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_CheckBoxNameActionPerformed

    private void CheckBoxWardenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CheckBoxWardenActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_CheckBoxWardenActionPerformed

    private void CheckBoxAddressActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CheckBoxAddressActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_CheckBoxAddressActionPerformed

    private void sucChuaToiDaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_sucChuaToiDaActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_sucChuaToiDaActionPerformed

    private void FieldSumActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldSumActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldSumActionPerformed

    private void FieldSumFamilyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_FieldSumFamilyActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_FieldSumFamilyActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSearchActionPerformed

    private void btnResidentUndoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnResidentUndoActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnResidentUndoActionPerformed

    private void btnSortActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSortActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnSortActionPerformed

    private void btnCancelSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelSearchActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnCancelSearchActionPerformed

    private void CheckBoxSortIDFamilyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CheckBoxSortIDFamilyActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_CheckBoxSortIDFamilyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrisonView().setVisible(true);
            }
        });
    }

    public void showPopupMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // public Prison getPrisontInfo() {
    // // validate prisons
    // if (!validateID() || !validateMaTraiGiam() || !validateTenTraiGiam() ||
    // !validateAddress() || !validatePhone() || !validateEmail() ||
    // !validateSucChua() || !validateSoLuong() || !validateNgayThanhLap() ||
    // !validateQuanLiTruong()) {
    // return null;
    // }
    // try {
    // Prison prisons = new Prison();
    // if (FieldID.getText() != null && !"".equals(FieldID.getText())) {
    // prisons.setId(FieldID.getText().trim());
    // }
    // prisons.setMaTraiGiam(FieldMaTraiGiam.getText().trim());
    // prisons.setTenTraiGiam(FieldTenTraiGiam.getText().trim());
    // prisons.setDiaChi(TextAreaAddress.getText().trim());
    // prisons.setSoDienThoai(FieldPhone.getText().trim());
    // prisons.setEmail(FieldEmail.getText().trim());
    // if (!sucChuaToiDa.getText().trim().isEmpty()) {
    // try {
    // prisons.setSucChuaToiDa(Integer.parseInt(sucChuaToiDa.getText().trim()));
    // } catch (NumberFormatException e) {
    // showMessage("Sức chứa phải là số nguyên hợp lệ");
    // return;
    // }
    // }
    // if (!soLuongPhamNhanHienTai.getText().trim().isEmpty()) {
    // try {
    // prisons.setSoLuongPhamNhanHienTai(Integer.parseInt(soLuongPhamNhanHienTai.getText().trim()));
    // } catch (NumberFormatException e) {
    // showMessage("Số lượng phạm nhân hiện tại phải là số nguyên hợp lệ");
    // return;
    // }
    // }
    // prisons.setNgayThanhLap(BirthdayChooser.getDate());
    // prisons.setQuanLiTruong(FieldQuanLiTruong.getText().trim());

    // return prisons;
    // } catch (Exception e) {
    // showMessage(e.getMessage());
    // }
    // return null;
    // }

    public void cancelSearch() {
        // Đặt lại trạng thái tìm kiếm, ví dụ: xóa từ khóa trong FieldSearch
        FieldSearch.setText("");
        // Ẩn hoặc đặt lại trạng thái các checkbox nếu cần
        CheckBoxPrisonId.setSelected(false);
        CheckBoxName.setSelected(false);
        CheckBoxAddress.setSelected(false);
        CheckBoxWarden.setSelected(false);
    }

    public void closeSearchDialog() {
        if (SearchDialog != null && SearchDialog.isDisplayable()) {
            SearchDialog.dispose(); // Đóng dialog an toàn
        }
    }

    public int getSelectedSearchOption() {
        if (CheckBoxPrisonId.isSelected())
            return 1; // Mã trại giam
        if (CheckBoxName.isSelected())
            return 2; // Tên trại giam
        if (CheckBoxAddress.isSelected())
            return 3; // Địa chỉ
        if (CheckBoxWarden.isSelected())
            return 4; // Năm sinh (hoặc quản lý trưởng)
        return 0;
    }

    public String getSearchKeyword() {
        return FieldSearch.getText().trim(); // Lấy nội dung từ trường nhập liệu
    }

    public void showSearchDialog() {
        SearchDialog.setVisible(true);
    }

    public int getSelectedSortOption() {
        if (CheckBoxSortName.isSelected()) {
            return 1; // Sắp xếp theo STT
        } else if (CheckBoxSortCapacity.isSelected()) {
            return 2; // Sắp xếp theo tên
        } else if (CheckBoxSortWarden.isSelected()) {
            return 3; // Sắp xếp theo tên quản lý trưởng
        }
        return 0; // Mặc định nếu không có checkbox nào được chọn
    }

    public class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    private boolean validateID() {
        String id = FieldID.getText().trim();
        if (id.isEmpty() || !id.matches("\\d+")) {
            showMessage("ID không hợp lệ");
            FieldID.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateMaTraiGiam() {
        String ma = FieldMaTraiGiam.getText().trim();
        if (ma.isEmpty()) {
            showMessage("Mã trại giam không được để trống");
            FieldMaTraiGiam.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateTenTraiGiam() {
        String name = FieldTenTraiGiam.getText().trim();
        if (name.isEmpty()) {
            showMessage("Tên trại giam không được để trống");
            FieldTenTraiGiam.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        String address = TextAreaAddress.getText();
        if (address == null || "".equals(address.trim())) {
            TextAreaAddress.requestFocus();
            showMessage("Địa chỉ không được trống.");
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        String phone = FieldPhone.getText().trim();
        if (phone.isEmpty()) {
            showMessage("Số điện thoại không được để trống");
            FieldPhone.requestFocus();
            return false;
        }
        if (!phone.matches("\\d{9,11}")) {
            showMessage("Số điện thoại không hợp lệ (phải là 9-11 chữ số)");
            FieldPhone.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = FieldEmail.getText().trim();
        if (email.isEmpty()) {
            showMessage("Email không được để trống");
            FieldEmail.requestFocus();
            return false;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showMessage("Email không hợp lệ");
            FieldEmail.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateSucChua() {
        String capacity = sucChuaToiDa.getText().trim();
        if (capacity.isEmpty()) {
            showMessage("Sức chứa tối đa không được để trống");
            sucChuaToiDa.requestFocus();
            return false;
        }
        try {
            int val = Integer.parseInt(capacity);
            if (val <= 0) {
                showMessage("Sức chứa phải là số nguyên dương");
                sucChuaToiDa.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showMessage("Sức chứa không hợp lệ (phải là số)");
            sucChuaToiDa.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateSoLuong() {
        String count = soLuongPhamNhanHienTai.getText().trim();
        if (count.isEmpty()) {
            showMessage("Số lượng phạm nhân hiện tại không được để trống");
            soLuongPhamNhanHienTai.requestFocus();
            return false;
        }
        try {
            int val = Integer.parseInt(count);
            if (val < 0) {
                showMessage("Số lượng không thể âm");
                soLuongPhamNhanHienTai.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showMessage("Số lượng không hợp lệ (phải là số)");
            soLuongPhamNhanHienTai.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateNgayThanhLap() {
        try {
            Date today = new Date();
            Date selectedDate = BirthdayChooser.getDate();

            if (selectedDate == null) {
                showMessage("Vui lòng chọn ngày thành lập");
                BirthdayChooser.requestFocus();
                return false;
            }

            if (selectedDate.after(today)) {
                showMessage("Ngày thành lập không được lớn hơn ngày hiện tại");
                BirthdayChooser.requestFocus();
                return false;
            }
        } catch (Exception e) {
            showMessage("Lỗi định dạng ngày thành lập");
            BirthdayChooser.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateQuanLiTruong() {
        String manager = FieldQuanLiTruong.getText().trim();
        if (manager.isEmpty()) {
            showMessage("Quản lý trưởng không được để trống");
            FieldQuanLiTruong.requestFocus();
            return false;
        }
        return true;
    }

    public void showListPrisonsTable(List<Prison> list) {
        if (list == null) {
            showMessage("Danh sách trại giam trống");
            return;
        }
        int size = list.size();
        // với bảng tableResident có 9 cột,
        // khởi tạo mảng 2 chiều residents, trong đó:
        // số hàng: là kích thước của list resident
        // số cột: là 9
        Object[][] residents = new Object[size][9];
        for (int i = 0; i < size; i++) {
            Prison p = list.get(i);
            residents[i][0] = p.getId();
            residents[i][1] = p.getMaTraiGiam();
            residents[i][2] = p.getTenTraiGiam();
            residents[i][3] = p.getDiaChi();
            residents[i][4] = p.getSucChuaToiDa();
            residents[i][5] = p.getSoLuongPhamNhanHienTai();
            residents[i][6] = p.getQuanLiTruong();
            residents[i][7] = p.getSoDienThoai();
            residents[i][8] = p.getEmail();
        }
        // jLabel1.setLayout(null);
        tablePrison.getColumnModel().getColumn(0).setWidth(3);
        tablePrison.setModel(new DefaultTableModel(residents, columnNames));
        // tableResident.removeColumn(tableResident.getColumnModel().getColumn(6));
    }

    public void showPrisons(Prison prison) {
        FieldID.setText(String.valueOf(prison.getId()));
        FieldMaTraiGiam.setText(prison.getMaTraiGiam());
        FieldTenTraiGiam.setText(prison.getTenTraiGiam());
        TextAreaAddress.setText(prison.getDiaChi());
        // FieldOpeningDate.setText("" + fDate.format(resident.getOpeningDate()));
        sucChuaToiDa.setText(String.valueOf(prison.getSucChuaToiDa()));
        soLuongPhamNhanHienTai.setText(String.valueOf(prison.getSoLuongPhamNhanHienTai()));
        FieldQuanLiTruong.setText(prison.getQuanLiTruong());
        FieldPhone.setText(prison.getSoDienThoai());
        FieldEmail.setText(prison.getEmail());
        BirthdayChooser.setDate(prison.getNgayThanhLap());
        // enable Edit and Delete buttons
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        // disable Add button
        btnAdd.setEnabled(false);
        btnClear.setEnabled(true);
    }

    public void fillPrisonFromSelectedRow(List<Prison> list) throws ParseException {
        int row = tablePrison.getSelectedRow(); // Sửa từ tableResident thành tablePrison
        if (row >= 0) {
            int prisonID = Integer.parseInt(tablePrison.getModel().getValueAt(row, 0).toString());
            Prison selectedPrison = findPrisonByID(list, prisonID);

            if (selectedPrison != null) {
                FieldID.setText(String.valueOf(selectedPrison.getId()));
                FieldMaTraiGiam.setText(selectedPrison.getMaTraiGiam());
                FieldTenTraiGiam.setText(selectedPrison.getTenTraiGiam());
                TextAreaAddress.setText(selectedPrison.getDiaChi());
                sucChuaToiDa.setText(String.valueOf(selectedPrison.getSucChuaToiDa()));
                soLuongPhamNhanHienTai.setText(String.valueOf(selectedPrison.getSoLuongPhamNhanHienTai()));
                FieldQuanLiTruong.setText(selectedPrison.getQuanLiTruong());
                FieldPhone.setText(selectedPrison.getSoDienThoai());
                FieldEmail.setText(selectedPrison.getEmail());
                BirthdayChooser.setDate(selectedPrison.getNgayThanhLap());

                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                btnAdd.setEnabled(false);
                btnClear.setEnabled(true);
            }
        }
    }

    public void fillPrisonFromSelectedRow() throws ParseException {
        // lấy chỉ số của hàng được chọn
        int row = tablePrison.getSelectedRow();
        if (row >= 0) {
            FieldID.setText(tablePrison.getModel().getValueAt(row, 0).toString()); // STT hoặc ID
            FieldMaTraiGiam.setText(tablePrison.getModel().getValueAt(row, 1).toString());
            FieldTenTraiGiam.setText(tablePrison.getModel().getValueAt(row, 2).toString());
            TextAreaAddress.setText(tablePrison.getModel().getValueAt(row, 3).toString());
            sucChuaToiDa.setText(tablePrison.getModel().getValueAt(row, 4).toString());
            soLuongPhamNhanHienTai.setText(tablePrison.getModel().getValueAt(row, 5).toString());
            FieldQuanLiTruong.setText(tablePrison.getModel().getValueAt(row, 6).toString());
            FieldPhone.setText(tablePrison.getModel().getValueAt(row, 7).toString());
            FieldEmail.setText(tablePrison.getModel().getValueAt(row, 8).toString());

            // enable Edit and Delete buttons
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            // disable Add button
            btnAdd.setEnabled(false);
            btnClear.setEnabled(true);
        }
    }

    private Prison findPrisonByID(List<Prison> prisonList, int prisonID) {
        for (Prison prison : prisonList) {
            if (Integer.parseInt(prison.getId()) == prisonID) {
                return prison;
            }
        }
        return null; // Trả về null nếu không tìm thấy đối tượng
    }

    public void resetPrisonForm() {
        FieldID.setText("");
        FieldMaTraiGiam.setText("");
        FieldTenTraiGiam.setText("");
        TextAreaAddress.setText("");
        sucChuaToiDa.setText("");
        soLuongPhamNhanHienTai.setText("");
        FieldQuanLiTruong.setText("");
        FieldPhone.setText("");
        FieldEmail.setText("");
        BirthdayChooser.setDate(null);

        // disable Edit and Delete buttons
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        // enable Add button
        btnAdd.setEnabled(true);
    }

    public void showCountListPrisons(List<Prison> list) {
        int size = list.size();
        FieldSum.setText(String.valueOf(size));
    }

    public void SearchPrisonInfo() {
        // FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }

    public int getChooseSelectSort() {
        if (CheckBoxSortName.isSelected())
            return 1;
        else if (CheckBoxSortCapacity.isSelected())
            return 2;
        else if (CheckBoxSortWarden.isSelected())
            return 3;
        return 0;
    }

    public void showStatisticQuanLi(List<Prison> list) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Prison prison : list) {
            String quanli = prison.getQuanLiTruong();
            countMap.put(quanli, countMap.getOrDefault(quanli, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println("Quản lý: " + entry.getKey() + ", Số trại: " + entry.getValue());
        }
    }

    public void searchResidentInfo() {
        // FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }

    public void cancelDialogSearchResidentInfo() {
        // FrameSearch = new ManagerView();
        SearchDialog.setVisible(false);
    }

    public int getChooseSelectSearch() {
        if (CheckBoxPrisonId.isSelected())
            return 1;
        else if (CheckBoxName.isSelected())
            return 2;
        else if (CheckBoxWarden.isSelected())
            return 3;
        else if (CheckBoxAddress.isSelected())
            return 4;
        return 0;
    }

    public void cancelSearchResident() {
        String id = FieldID.getText();
        btnCancelSearch.setEnabled(false);
        btnSearch.setEnabled(true);
        btnClear.setEnabled(true);
        if (id == null || "".equals(id.trim())) {

            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        } else {
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }

    public String validateSearch() {
        String search = FieldSearch.getText();
        if (search == null || "".equals(search.trim())) {
            FieldSearch.requestFocus();
            showMessage("Nội dung tìm kiếm không hợp lệ!");
            return "";
        }
        btnCancelSearch.setEnabled(true);
        SearchDialog.setVisible(false);
        btnAdd.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnClear.setEnabled(false);
        btnSearch.setEnabled(false);
        return search;
    }

    public void addUndoListener(ActionListener listener) {
        if (btnUndo != null) {
            btnUndo.addActionListener(listener);
        } else {
            System.err.println("Error: btnUndo is null in addUndoListener!");
        }
    }

    public void addAddPrisonListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addListPrisonSelectionListener(ListSelectionListener listener) {
        tablePrison.getSelectionModel().addListSelectionListener(listener);
    }

    public void addEditPrisonListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        btnClear.addActionListener(listener);
    }

    public void addDeletePrisonListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addSortPrisonListener(ActionListener listener) {
        btnSort.addActionListener(listener);
    }

    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }

    public void addSearchDialogListener(ActionListener listener) {
        btnSearchDialog.addActionListener(listener);
    }

    public void addCancelSearchPrisonListener(ActionListener listener) {
        btnCancelSearch.addActionListener(listener);
    }

    public void addCancelDialogListener(ActionListener listener) {
        btnCancelDialog.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }

    public void showTotalPrisons(int total) {
        JOptionPane.showMessageDialog(this, "Tong so trai giam: " + total);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BirthdayChooser;
    private javax.swing.JCheckBox CheckBoxAddress;
    private javax.swing.JCheckBox CheckBoxPrisonId;
    private javax.swing.JCheckBox CheckBoxName;
    private javax.swing.JCheckBox CheckBoxSortName;
    private javax.swing.JCheckBox CheckBoxSortWarden;
    private javax.swing.JCheckBox CheckBoxSortCapacity;
    private javax.swing.JCheckBox CheckBoxWarden;
    private javax.swing.JComboBox<String> ComboBoxRole;
    private javax.swing.JTextField FieldCMT;
    private javax.swing.JTextField FieldID;
    private javax.swing.JTextField FieldIDFamily;
    private javax.swing.JTextField FieldName;
    private javax.swing.JTextField FieldPhone;
    private javax.swing.JTextField FieldSearch;
    private javax.swing.JTextField FieldSum;
    private javax.swing.JTextField FieldSumFamily;
    private javax.swing.JDialog SearchDialog;
    private javax.swing.JTextArea TextAreaAddress;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelDialog;
    private javax.swing.JButton btnCancelSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGroupSearch;
    private javax.swing.ButtonGroup btnGroupSex;
    private javax.swing.ButtonGroup btnGroupSort;
    private javax.swing.JButton btnResidentUndo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchDialog;
    private javax.swing.JButton btnSort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField soLuongPhamNhanHienTai;
    private javax.swing.JTextField sucChuaToiDa;
    private javax.swing.JTable tableResident;
    // End of variables declaration//GEN-END:variables
}
