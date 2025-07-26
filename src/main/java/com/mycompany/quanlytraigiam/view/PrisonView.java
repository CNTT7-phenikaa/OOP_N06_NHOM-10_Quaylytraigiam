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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

    private String[] columnNames = new String[] {
        "STT", "Mã trại giam", "Tên trại giam", "Địa chỉ", "Sức chứa", "Số phạm nhân", "Quản lý trưởng", "Số điện thoại", "Email", "Ngày Thành Lập"
    };
    private JTable tablePrison;
    private JTextField FieldMaTraiGiam;
    private JTextField FieldTenTraiGiam;
    private JTextField FieldQuanLiTruong;
    private JTextField FieldEmail;
    
    private javax.swing.JButton btnUndo;
    public PrisonView() {
        tablePrison = new JTable();

        JScrollPane scrollPane = new JScrollPane(tablePrison);
        add(scrollPane); 
    }
    public void showListPrisons(List<Prison> list) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        list.stream().forEach(prison -> {
            model.addRow(new Object[]{
                prison.getId(),
                prison.getMaTraiGiam(),
                prison.getTenTraiGiam(),
                prison.getDiaChi(),
                prison.getSucChuaToiDa(),
                prison.getSoLuongPhamNhanHienTai(),
                prison.getQuanLiTruong(),
                prison.getSoDienThoai(),
                prison.getEmail()
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
            if (FieldID.getText() != null && !FieldID.getText().isEmpty()) {
                prison.setId(FieldID.getText());
            }
            prison.setMaTraiGiam(FieldMaTraiGiam.getText().trim());
            prison.setTenTraiGiam(FieldTenTraiGiam.getText().trim());
            prison.setDiaChi(TextAreaAddress.getText().trim());
            
            // Xử lý số nguyên cho sức chứa
            try {
                prison.setSucChuaToiDa(sucChuaToiDa.getText().trim());
            } catch (NumberFormatException e) {
                showMessage("Sức chứa phải là số nguyên");
                return null;
            }
            
            // Xử lý số nguyên cho số lượng phạm nhân
            try {
                prison.setSoLuongPhamNhanHienTai(soLuongPhamNhanHienTai.getText().trim());
            } catch (NumberFormatException e) {
                showMessage("Số lượng phạm nhân phải là số nguyên");
                return null;
            }
            
            // Xử lý ngày thành lập
            Date ngayThanhLap = BirthdayChooser.getDate();
            if (ngayThanhLap != null) {
                prison.setNgayThanhLap(ngayThanhLap);
            }
            
            prison.setQuanLiTruong(FieldQuanLiTruong.getText().trim());
            prison.setSoDienThoai(FieldPhone.getText().trim());
            prison.setEmail(FieldEmail.getText().trim());
            return prison;
        } catch (Exception e) {
            showMessage("Lỗi khi lấy thông tin trại giam: " + e.getMessage());
        }
        return null;
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
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        CheckBoxYear = new javax.swing.JCheckBox();
        CheckBoxIDFamily = new javax.swing.JCheckBox();
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
        btnResidentUndo = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        FieldSumFamily = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        FieldSum = new javax.swing.JTextField();
        CheckBoxSortID = new javax.swing.JCheckBox();
        CheckBoxSortName = new javax.swing.JCheckBox();
        CheckBoxSortIDFamily = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableResident = new javax.swing.JTable();
        FieldCMT = new javax.swing.JTextField();
        FieldName = new javax.swing.JTextField();
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

        SearchDialog.setResizable(false);
        SearchDialog.setSize(new java.awt.Dimension(511, 390));

        jPanel3.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel18.setText("Tìm kiếm");
        jPanel3.add(jLabel18);
        jLabel18.setBounds(210, 40, 110, 29);

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText("Chọn tiêu chí tìm kiếm:");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(40, 190, 290, 29);

        btnGroupSearch.add(CheckBoxName);
        CheckBoxName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxName.setText("Tên");
        CheckBoxName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxNameActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxName);
        CheckBoxName.setBounds(170, 230, 85, 20);
        CheckBoxName.setOpaque(false);

        jLabel20.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        jPanel3.add(jLabel20);
        jLabel20.setBounds(30, 130, 30, 30);

        btnGroupSearch.add(CheckBoxAddress);
        CheckBoxAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxAddress.setText("Địa chỉ");
        CheckBoxAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxAddressActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxAddress);
        CheckBoxAddress.setBounds(390, 230, 100, 20);
        CheckBoxAddress.setOpaque(false);

        btnGroupSearch.add(CheckBoxYear);
        CheckBoxYear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxYear.setText("Năm sinh");
        CheckBoxYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxYearActionPerformed(evt);
            }
        });
        jPanel3.add(CheckBoxYear);
        CheckBoxYear.setBounds(260, 230, 100, 20);
        CheckBoxYear.setOpaque(false);

        btnGroupSearch.add(CheckBoxIDFamily);
        CheckBoxIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxIDFamily.setText("Số hộ khẩu");
        CheckBoxIDFamily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxIDFamily);
        CheckBoxIDFamily.setBounds(20, 230, 120, 25);
        CheckBoxIDFamily.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Nhập nội dung tìm kiếm:");
        jPanel3.add(jLabel21);
        jLabel21.setBounds(40, 90, 290, 29);

        FieldSearch.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 102)));
        jPanel3.add(FieldSearch);
        FieldSearch.setBounds(60, 130, 400, 40);
        FieldSearch.setOpaque(false);

        btnCancelDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnCancelDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelDialog.setText("Hủy");
        btnCancelDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnCancelDialog);
        btnCancelDialog.setBounds(290, 270, 150, 50);
        btnCancelDialog.setBorder(new RoundedBorder(20));
        btnCancelDialog.setOpaque(false);

        btnSearchDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnSearchDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearchDialog.setText("Tìm kiếm");
        btnSearchDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnSearchDialog);
        btnSearchDialog.setBounds(70, 270, 140, 50);
        btnSearchDialog.setBorder(new RoundedBorder(20));
        btnSearchDialog.setOpaque(false);

        jLabel22.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/viewSearchView.png"));
        jLabel22.setText("=");
        jPanel3.add(jLabel22);
        jLabel22.setBounds(-10, 0, 520, 360);

        javax.swing.GroupLayout SearchDialogLayout = new javax.swing.GroupLayout(SearchDialog.getContentPane());
        SearchDialog.getContentPane().setLayout(SearchDialogLayout);
        SearchDialogLayout.setHorizontalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        );
        SearchDialogLayout.setVerticalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý dân cư trong khu vực");
        setName("Quản lý dân cư trong khu vực"); // NOI18N
        setSize(new java.awt.Dimension(1207, 665));

        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel16.setText("Chọn tiêu chí sắp xếp:");

        btnCancelSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnCancelSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/cancel.png"));
        btnCancelSearch.setText("Hủy tìm kiếm");
        btnCancelSearch.setToolTipText("");
        btnCancelSearch.setBorder(null);
        btnCancelSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSearchActionPerformed(evt);
            }
        });
        btnCancelSearch.setEnabled(false);

        btnSort.setBackground(new java.awt.Color(0, 0, 102));
        btnSort.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSort.setForeground(new java.awt.Color(255, 255, 255));
        ImageIcon imageIcon2 = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/sorting.png");
        Image image2 = imageIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        btnSort.setIcon(new ImageIcon(image2));
        btnSort.setText("Sắp xếp");
        btnSort.setToolTipText("");
        btnSort.setBorder(null);
        btnSort.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortActionPerformed(evt);
            }
        });

        btnResidentUndo.setBackground(new java.awt.Color(0, 0, 102));
        btnResidentUndo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnResidentUndo.setForeground(new java.awt.Color(255, 255, 255));
        btnResidentUndo.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/LogOut.png"));
        btnResidentUndo.setText("Quay lại");
        btnResidentUndo.setToolTipText("");
        btnResidentUndo.setBorder(null);
        btnResidentUndo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResidentUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResidentUndoActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(0, 0, 102));
        btnSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        btnSearch.setText("Tìm kiếm");
        btnSearch.setBorder(null);
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        ImageIcon imageIcon = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/logo.png");
        Image image = imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon=new ImageIcon(image);
        jLabel14.setIcon(imageIcon);
        jLabel14.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel12.setText("Tổng số hộ dân:");

        FieldSumFamily.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSumFamily.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldSumFamily.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSumFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumFamilyActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel17.setText("Tổng số trại giam:");

        FieldSum.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        FieldSum.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        FieldSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldSumActionPerformed(evt);
            }
        });

        CheckBoxSortID.setBackground(new java.awt.Color(0, 102, 204, 175));
        btnGroupSort.add(CheckBoxSortID);
        CheckBoxSortID.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortID.setText("Sắp xếp theo STT");
        CheckBoxSortID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortID.setOpaque(false);

        btnGroupSort.add(CheckBoxSortName);
        CheckBoxSortName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortName.setText("Sắp xếp theo tên");
        CheckBoxSortName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortName.setOpaque(false);

        btnGroupSort.add(CheckBoxSortIDFamily);
        CheckBoxSortIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        CheckBoxSortIDFamily.setText("Sắp xếp theo tên quản lý trưởng");
        CheckBoxSortIDFamily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxSortIDFamily.setOpaque(false);
        CheckBoxSortIDFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxSortIDFamilyActionPerformed(evt);
            }
        });

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
                        .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortID, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortName, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(CheckBoxSortIDFamily, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnResidentUndo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(CheckBoxSortID)
                .addGap(3, 3, 3)
                .addComponent(CheckBoxSortName)
                .addGap(6, 6, 6)
                .addComponent(CheckBoxSortIDFamily)
                .addGap(6, 6, 6)
                .addComponent(btnSort, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(FieldSum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FieldSumFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addComponent(btnResidentUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        FieldSumFamily.setOpaque(false);
        FieldSumFamily.setEditable(false);
        FieldSum.setOpaque(false);
        FieldSum.setEditable(false);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 230, 780);
        jPanel2.setOpaque(true);

        jScrollPane1.setBackground(new java.awt.Color(0, 51, 153, 125));

        tableResident.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableResident.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            }, columnNames
        ));
        tableResident.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tableResident.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableResident.setRowHeight(30);
        jScrollPane1.setViewportView(tableResident);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(240, 420, 1030, 260);

        FieldCMT.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldCMT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldCMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldCMTActionPerformed(evt);
            }
        });
        jPanel1.add(FieldCMT);
        FieldCMT.setBounds(970, 260, 260, 40);
        FieldCMT.setOpaque(false);

        FieldName.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldNameActionPerformed(evt);
            }
        });
        jPanel1.add(FieldName);
        FieldName.setBounds(390, 170, 250, 40);
        FieldName.setOpaque(false);

        ComboBoxRole.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        ComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "Chủ hộ", "Vợ", "Chồng", "Con", "Bố", "Mẹ", "Người thân khác" }));
        jPanel1.add(ComboBoxRole);
        ComboBoxRole.setBounds(970, 100, 260, 40);
        ComboBoxRole.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Quản lý trưởng:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(830, 100, 140, 40);

        FieldIDFamily.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldIDFamily.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldIDFamily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldIDFamilyActionPerformed(evt);
            }
        });
        jPanel1.add(FieldIDFamily);
        FieldIDFamily.setBounds(390, 100, 250, 40);
        FieldIDFamily.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Địa chỉ:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(900, 170, 70, 40);

        TextAreaAddress.setBackground(new java.awt.Color(255, 255, 255, 0));
        TextAreaAddress.setColumns(20);
        TextAreaAddress.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        TextAreaAddress.setRows(5);
        TextAreaAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
        jScrollPane2.setViewportView(TextAreaAddress);
        TextAreaAddress.setOpaque(false);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(970, 160, 260, 60);
        jScrollPane2.setOpaque(false);

        BirthdayChooser.setBackground(new java.awt.Color(0, 204, 255));
        BirthdayChooser.setForeground(new java.awt.Color(102, 255, 255));
        BirthdayChooser.setDateFormatString("dd/MM/yyyy");
        BirthdayChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jPanel1.add(BirthdayChooser);
        BirthdayChooser.setBounds(390, 310, 160, 40);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Tên trại giam:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(270, 170, 120, 40);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Ngày thành lập:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(260, 310, 130, 40);

        FieldPhone.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldPhone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
        FieldPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldPhoneActionPerformed(evt);
            }
        });
        jPanel1.add(FieldPhone);
        FieldPhone.setBounds(1030, 310, 200, 40);
        FieldPhone.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Số điện thoại:");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(910, 310, 120, 40);

        FieldID.setEditable(false);
        FieldID.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        FieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        FieldID.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 102)));
        FieldID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldIDActionPerformed(evt);
            }
        });
        jPanel1.add(FieldID);
        FieldID.setBounds(390, 40, 70, 40);
        FieldID.setOpaque(false);
        FieldID.setVisible(false);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("<html>Quản Lý Trại Giam<br> ");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(620, 10, 320, 80);

        btnAdd.setBackground(new java.awt.Color(0, 0, 102));
        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/add.png"));
        btnAdd.setText("Thêm");
        btnAdd.setBorder(null);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd);
        btnAdd.setBounds(330, 370, 170, 44);

        btnEdit.setBackground(new java.awt.Color(0, 0, 102));
        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Edit.png"));
        btnEdit.setText("Cập nhật");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit);
        btnEdit.setBounds(580, 370, 170, 44);

        btnDelete.setBackground(new java.awt.Color(0, 0, 102));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/delete.png"));
        btnDelete.setText("Xóa");
        btnDelete.setBorder(null);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);
        btnDelete.setBounds(830, 370, 170, 44);

        btnClear.setBackground(new java.awt.Color(0, 0, 102));
        btnClear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/trash.png"));
        btnClear.setText("Làm mới");
        btnClear.setToolTipText("");
        btnClear.setBorder(null);
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel1.add(btnClear);
        btnClear.setBounds(1070, 370, 170, 44);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Mã trại giam:");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(270, 100, 120, 40);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("ID:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(350, 50, 30, 21);

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setText("Email:");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(910, 270, 60, 21);

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setText("Sức chứa tối đa:");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(250, 240, 140, 21);

        sucChuaToiDa.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        sucChuaToiDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucChuaToiDaActionPerformed(evt);
            }
        });
        jPanel1.add(sucChuaToiDa);
        sucChuaToiDa.setBounds(390, 240, 100, 27);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Số lượng phạm nhân hiện tại:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(247, 280, 240, 21);

        soLuongPhamNhanHienTai.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jPanel1.add(soLuongPhamNhanHienTai);
        soLuongPhamNhanHienTai.setBounds(490, 280, 64, 27);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Lovepik_com-500330964-blue-blazed-background.jpg"));
        jLabel9.setText("Số lượng phạm nhân:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.add(jLabel9);
        jLabel9.setBounds(0, 0, 1640, 890);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1276, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void FieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNameActionPerformed

    private void FieldCMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldCMTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldCMTActionPerformed

    private void FieldIDFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDFamilyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDFamilyActionPerformed

    private void FieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldPhoneActionPerformed

    private void FieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDActionPerformed

    private void CheckBoxNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxNameActionPerformed

    private void CheckBoxYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxYearActionPerformed

    private void CheckBoxAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxAddressActionPerformed

    private void sucChuaToiDaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucChuaToiDaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sucChuaToiDaActionPerformed

    private void FieldSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumActionPerformed

    private void FieldSumFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumFamilyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumFamilyActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnResidentUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResidentUndoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnResidentUndoActionPerformed

    private void btnSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortActionPerformed

    private void btnCancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelSearchActionPerformed

    private void CheckBoxSortIDFamilyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxSortIDFamilyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxSortIDFamilyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrisonView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

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
    
    public Prison getPrisontInfo() {
        // validate prisons
        if (!validateID() || !validateMaTraiGiam() || !validateTenTraiGiam() ||
        !validateAddress() || !validatePhone() || !validateEmail() ||
        !validateSucChua() || !validateSoLuong() || !validateNgayThanhLap() ||
        !validateQuanLiTruong()) {
    return null;
}
        try {
            Prison prisons = new Prison();
            if (FieldID.getText() != null && !"".equals(FieldID.getText())) {
                prisons.setId(FieldID.getText().trim());
            }
            prisons.setMaTraiGiam(FieldMaTraiGiam.getText().trim());
            prisons.setTenTraiGiam(FieldTenTraiGiam.getText().trim());
            prisons.setDiaChi(TextAreaAddress.getText().trim());
            prisons.setSoDienThoai(FieldPhone.getText().trim());
            prisons.setEmail(FieldEmail.getText().trim());
            prisons.setSucChuaToiDa(sucChuaToiDa.getText().trim());
            prisons.setSoLuongPhamNhanHienTai(soLuongPhamNhanHienTai.getText().trim());
            prisons.setNgayThanhLap(BirthdayChooser.getDate());
            prisons.setQuanLiTruong(FieldQuanLiTruong.getText().trim());

        return prisons;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }

    public void cancelSearch() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void closeSearchDialog() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getSelectedSearchOption() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSearchKeyword() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void showSearchDialog() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getSelectedSortOption() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        public boolean isBorderOpaque() {
            return true;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
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
        Object [][] residents = new Object[size][9];
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
        //jLabel1.setLayout(null);
        tablePrison.getColumnModel().getColumn(0).setWidth(3);
        tablePrison.setModel(new DefaultTableModel(residents, columnNames));
        //tableResident.removeColumn(tableResident.getColumnModel().getColumn(6));
    }
    
    
    public void showPrisons(Prison prison) 
    {
        FieldID.setText(String.valueOf(prison.getId()));
        FieldMaTraiGiam.setText(prison.getMaTraiGiam());
        FieldTenTraiGiam.setText(prison.getTenTraiGiam());
        TextAreaAddress.setText(prison.getDiaChi());
        //FieldOpeningDate.setText("" + fDate.format(resident.getOpeningDate()));
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
        // lấy chỉ số của hàng được chọn 
        int row = tableResident.getSelectedRow();
        if (row >= 0) {
            int prisonID = Integer.parseInt(tableResident.getModel().getValueAt(row, 0).toString());
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

                // enable Edit and Delete buttons
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                // disable Add button
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
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }
    
    public int getChooseSelectSort(){
        if(CheckBoxSortID.isSelected()) return 1;
        else if(CheckBoxSortName.isSelected()) return 2;
        else if(CheckBoxSortIDFamily.isSelected()) return 3;
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
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(true);
    }
    
    public void cancelDialogSearchResidentInfo() {
        //FrameSearch = new ManagerView();
        SearchDialog.setVisible(false);
    }
    
    public int getChooseSelectSearch(){
        if(CheckBoxIDFamily.isSelected()) return 1;
        else if(CheckBoxName.isSelected()) return 2;
        else if(CheckBoxYear.isSelected()) return 3;
        else if(CheckBoxAddress.isSelected()) return 4;
        return 0;
    }
    
    public void cancelSearchResident()
    {
        String id=FieldID.getText();
        btnCancelSearch.setEnabled(false);
        btnSearch.setEnabled(true);
        btnClear.setEnabled(true);
        if (id == null || "".equals(id.trim()))
        {
            
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);
        }
        else
        {
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
    
    public String validateSearch(){
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
    
    public void addUndoListener(ActionListener listener){
        btnUndo.addActionListener(listener);
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
    
    public void addCancelSearchPrisonListener(ActionListener listener){
        btnCancelSearch.addActionListener(listener);
    }
    
    public void addCancelDialogListener(ActionListener listener){
        btnCancelDialog.addActionListener(listener);
    }   
    public void showTotalPrisons(int total) {
        JOptionPane.showMessageDialog(this, "Tong so trai giam: " + total);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BirthdayChooser;
    private javax.swing.JCheckBox CheckBoxAddress;
    private javax.swing.JCheckBox CheckBoxIDFamily;
    private javax.swing.JCheckBox CheckBoxName;
    private javax.swing.JCheckBox CheckBoxSortID;
    private javax.swing.JCheckBox CheckBoxSortIDFamily;
    private javax.swing.JCheckBox CheckBoxSortName;
    private javax.swing.JCheckBox CheckBoxYear;
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
