/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 *
 * @author Admin
 */
public class VisitPanel extends JPanel {
    private JTable visitTable;
    private JButton btnAdd, btnEdit, btnDelete;
    private JTextField txtSearch;
    public VisitPanel() {
        initComponents();
    }
    private void initComponents() {
        setLayout(new BorderLayout());
        // Title
        JLabel title = new JLabel("Quản lý thăm nuôi", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);
        // Table
        visitTable = new JTable(
            new Object[][] {
                {"Nguyễn Văn A", "20/07/2025", "Cha mẹ"},
                {"Trần Thị B", "21/07/2025", "Anh trai"}
            },
            new Object[] {"Tên phạm nhân", "Ngày thăm", "Người thăm"}
        );
        JScrollPane scrollPane = new JScrollPane(visitTable);
        add(scrollPane, BorderLayout.CENTER);
        // Bottom panel
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtSearch = new JTextField(20);
        panelBottom.add(txtSearch);
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xoá");
        panelBottom.add(btnAdd);
        panelBottom.add(btnEdit);
        panelBottom.add(btnDelete);
        add(panelBottom, BorderLayout.SOUTH);
    }
    // Getter & Listener methods
    public JTable getVisitTable() { return visitTable; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTextField getTxtSearch() { return txtSearch; }
    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }
    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }
}
