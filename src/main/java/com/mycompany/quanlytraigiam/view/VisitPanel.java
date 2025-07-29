/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.view;
import com.mycompany.quanlytraigiam.controller.VisitController;
import com.mycompany.quanlytraigiam.entity.Visit;

import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.FlowLayout;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.List;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.JTextField;

import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */

public class VisitView extends JFrame {



    // Khai báo các thành phần UI

    private JTextField txtInmateId;

    private JTextField txtPrisonerName;

    private JTextField txtVisitorName;

    private JTextField txtRelationship;

    private JTextField txtVisitDate;

    private JTextField txtVisitTime;

    private JTextField txtNotes;

    private JButton btnAdd;

    private JButton btnEdit;

    private JButton btnDelete;

    private JButton btnClear;

    private JButton btnSearch;

    private JButton btnBack; // Nút quay lại MainView

    private JTable visitTable;

    private DefaultTableModel tableModel;



    private final String[] COLUMN_NAMES = new String[]{"Mã phạm nhân", "Tên phạm nhân", "Tên người thăm", "Mối quan hệ", "Ngày thăm", "Giờ thăm", "Ghi chú"};



    public VisitView() {

        initComponents(); // Khởi tạo các thành phần UI

        initTable(); // Khởi tạo bảng

        setTitle("Quản lý Thăm Nuôi");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng cửa sổ này khi nhấn X

        setSize(900, 600); // Kích thước mặc định

        setLocationRelativeTo(null); // Hiển thị ở giữa màn hình

    }



    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Khoảng cách giữa các nút

        JScrollPane scrollPane;



        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần



        // --- Input Panel ---

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.anchor = GridBagConstraints.WEST;



        // Mã phạm nhân

        gbc.gridx = 0; gbc.gridy = 0;

        inputPanel.add(new JLabel("Mã phạm nhân:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;

        txtInmateId = new JTextField(20);

        inputPanel.add(txtInmateId, gbc);



        // Tên phạm nhân

        gbc.gridx = 2; gbc.gridy = 0;

        inputPanel.add(new JLabel("Tên phạm nhân:"), gbc);

        gbc.gridx = 3; gbc.gridy = 0;

        txtPrisonerName = new JTextField(20);

        inputPanel.add(txtPrisonerName, gbc);



        // Tên người thăm

        gbc.gridx = 0; gbc.gridy = 1;

        inputPanel.add(new JLabel("Tên người thăm:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;

        txtVisitorName = new JTextField(20);

        inputPanel.add(txtVisitorName, gbc);



        // Mối quan hệ

        gbc.gridx = 2; gbc.gridy = 1;

        inputPanel.add(new JLabel("Mối quan hệ:"), gbc);

        gbc.gridx = 3; gbc.gridy = 1;

        txtRelationship = new JTextField(20);

        inputPanel.add(txtRelationship, gbc);



        // Ngày thăm

        gbc.gridx = 0; gbc.gridy = 2;

        inputPanel.add(new JLabel("Ngày thăm (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;

        txtVisitDate = new JTextField(20);

        inputPanel.add(txtVisitDate, gbc);



        // Giờ thăm

        gbc.gridx = 2; gbc.gridy = 2;

        inputPanel.add(new JLabel("Giờ thăm (HH:mm):"), gbc);

        gbc.gridx = 3; gbc.gridy = 2;

        txtVisitTime = new JTextField(20);

        inputPanel.add(txtVisitTime, gbc);



        // Ghi chú

        gbc.gridx = 0; gbc.gridy = 3;

        inputPanel.add(new JLabel("Ghi chú:"), gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 3; // Chiếm 3 cột

        txtNotes = new JTextField(20);

        inputPanel.add(txtNotes, gbc);



        // --- Button Panel ---

        btnAdd = new JButton("Thêm");

        btnEdit = new JButton("Sửa");

        btnDelete = new JButton("Xóa");

        btnClear = new JButton("Làm mới");

        btnSearch = new JButton("Tìm kiếm");

        btnBack = new JButton("Quay lại");



        buttonPanel.add(btnAdd);

        buttonPanel.add(btnEdit);

        buttonPanel.add(btnDelete);

        buttonPanel.add(btnClear);

        buttonPanel.add(btnSearch);

        buttonPanel.add(btnBack);



        // --- Table ---

        visitTable = new JTable();

        scrollPane = new JScrollPane(visitTable);

        scrollPane.setPreferredSize(new Dimension(850, 300)); // Kích thước bảng



        // --- Add panels to main frame ---

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);



        add(mainPanel); // Thêm panel chính vào frame

    }



    private void initTable() {

        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {

            @Override

            public boolean isCellEditable(int row, int column) {

                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng

            }

        };

        visitTable.setModel(tableModel);

    }



    //region === Các phương thức GET dữ liệu từ UI ===

    public Visit getVisitInfo() {

        String inmateId = txtInmateId.getText().trim();

        String prisonerName = txtPrisonerName.getText().trim();

        String visitorName = txtVisitorName.getText().trim();

        String relationship = txtRelationship.getText().trim();

        String visitDate = txtVisitDate.getText().trim();

        String visitTime = txtVisitTime.getText().trim();

        String notes = txtNotes.getText().trim();



        if (inmateId.isEmpty() || prisonerName.isEmpty() || visitorName.isEmpty() || visitDate.isEmpty()) {

            showMessage("Vui lòng điền đầy đủ các thông tin bắt buộc: Mã phạm nhân, Tên phạm nhân, Tên người thăm, Ngày thăm.");

            return null;

        }



        return new Visit(inmateId, prisonerName, visitorName, relationship, visitDate, visitTime, notes);

    }



    public int getSelectedVisitIndex() {

        return visitTable.getSelectedRow();

    }

    //endregion



    //region === Các phương thức HIỂN THỊ dữ liệu lên UI ===

    public void showListVisits(List<Visit> visits) {

        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        if (visits != null) {

            for (Visit visit : visits) {

                tableModel.addRow(new Object[]{

                    visit.getInmateID(),

                    visit.getPrisonerName(),

                    visit.getVisitorName(),

                    visit.getRelationship(),

                    visit.getVisitDate(),

                    visit.getVisitTime(),

                    visit.getNotes()

                });

            }

        }

    }



    public void fillVisitFromSelectedRow() {

        int row = visitTable.getSelectedRow();

        if (row >= 0) {

            txtInmateId.setText(tableModel.getValueAt(row, 0).toString());

            txtPrisonerName.setText(tableModel.getValueAt(row, 1).toString());

            txtVisitorName.setText(tableModel.getValueAt(row, 2).toString());

            txtRelationship.setText(tableModel.getValueAt(row, 3).toString());

            txtVisitDate.setText(tableModel.getValueAt(row, 4).toString());

            txtVisitTime.setText(tableModel.getValueAt(row, 5).toString());

            txtNotes.setText(tableModel.getValueAt(row, 6).toString());

        }

    }



    public void clearVisitInfo() {

        txtInmateId.setText("");

        txtPrisonerName.setText("");

        txtVisitorName.setText("");

        txtRelationship.setText("");

        txtVisitDate.setText("");

        txtVisitTime.setText("");

        txtNotes.setText("");

        visitTable.clearSelection(); // Bỏ chọn hàng trong bảng

    }



    public void showMessage(String message) {

        JOptionPane.showMessageDialog(this, message);

    }

    //endregion



    //region === Các phương thức thêm Listener cho các nút ===

    public void addAddVisitListener(ActionListener listener) {

        btnAdd.addActionListener(listener);

    }



    public void addEditVisitListener(ActionListener listener) {

        btnEdit.addActionListener(listener);

    }



    public void addDeleteVisitListener(ActionListener listener) {

        btnDelete.addActionListener(listener);

    }



    public void addClearVisitListener(ActionListener listener) {

        btnClear.addActionListener(listener);

    }



    public void addSearchVisitListener(ActionListener listener) {

        btnSearch.addActionListener(listener);

    }



    public void addBackListener(ActionListener listener) {

        btnBack.addActionListener(listener);

    }



    public void addListVisitSelectionListener(ListSelectionListener listener) {

        visitTable.getSelectionModel().addListSelectionListener(listener);

    }

    //endregion



    // Các phương thức getter cho JTextField nếu cần từ Controller để lấy keyword tìm kiếm

    public String getInmateIdInput() {

        return txtInmateId.getText().trim();

    }

    public String getPrisonerNameInput() {

        return txtPrisonerName.getText().trim();

    }

    public String getVisitorNameInput() {

        return txtVisitorName.getText().trim();

    }

    // ... thêm các getter khác nếu bạn muốn dùng chúng cho chức năng tìm kiếm phức tạp hơn

    public void addListSelectionListener(VisitController.ListVisitSelectionListener listVisitSelectionListener) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getSelectedInmateId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSelectedVisitDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSelectedVisitTime() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int showMessageConfirm(String bạn_có_chắc_chắn_muốn_xóa_lượt_thăm_này) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSearchKeyword() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }



}
