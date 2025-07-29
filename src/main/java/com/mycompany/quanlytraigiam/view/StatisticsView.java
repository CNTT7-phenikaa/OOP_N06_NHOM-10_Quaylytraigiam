/*
 * Lớp StatisticsView đã được cập nhật để thêm nút Làm mới.
 */
package com.mycompany.quanlytraigiam.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class StatisticsView extends JFrame {
    private JLabel lblTotalPrisonersValue, lblMaleCountValue, lblFemaleCountValue, lblOtherGenderCountValue, lblTotalVisitsValue;
    private JTextArea txtRelationshipStats;
    private JButton btnBack;
    private JButton btnRefresh; // NÚT MỚI

    public StatisticsView() {
        setTitle("Báo cáo Thống kê");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450); // Tăng chiều cao một chút
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        statsPanel.add(createLabel("Tổng số phạm nhân:", labelFont));
        lblTotalPrisonersValue = createValueLabel(valueFont);
        statsPanel.add(lblTotalPrisonersValue);

        statsPanel.add(createLabel("Số phạm nhân Nam:", labelFont));
        lblMaleCountValue = createValueLabel(valueFont);
        statsPanel.add(lblMaleCountValue);

        statsPanel.add(createLabel("Số phạm nhân Nữ:", labelFont));
        lblFemaleCountValue = createValueLabel(valueFont);
        statsPanel.add(lblFemaleCountValue);
        
        statsPanel.add(createLabel("Số phạm nhân (Khác):", labelFont));
        lblOtherGenderCountValue = createValueLabel(valueFont);
        statsPanel.add(lblOtherGenderCountValue);

        statsPanel.add(createLabel("Tổng số lượt thăm nuôi:", labelFont));
        lblTotalVisitsValue = createValueLabel(valueFont);
        statsPanel.add(lblTotalVisitsValue);

        JPanel relationshipPanel = new JPanel(new BorderLayout(0, 5));
        relationshipPanel.add(createLabel("Thống kê theo Mối quan hệ:", labelFont), BorderLayout.NORTH);

        txtRelationshipStats = new JTextArea(5, 20);
        txtRelationshipStats.setFont(valueFont);
        txtRelationshipStats.setEditable(false);
        relationshipPanel.add(new JScrollPane(txtRelationshipStats), BorderLayout.CENTER);

        // Sửa lại panel nút bấm để có 2 nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnRefresh = new JButton("Làm mới");
        btnBack = new JButton("Quay lại");
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);

        mainPanel.add(statsPanel, BorderLayout.NORTH);
        mainPanel.add(relationshipPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
    
    private JLabel createValueLabel(Font font) {
        JLabel label = new JLabel("0");
        label.setFont(font);
        return label;
    }

    public void setTotalPrisoners(int count) { lblTotalPrisonersValue.setText(String.valueOf(count)); }
    public void setGenderStats(int male, int female, int other) {
        lblMaleCountValue.setText(String.valueOf(male));
        lblFemaleCountValue.setText(String.valueOf(female));
        lblOtherGenderCountValue.setText(String.valueOf(other));
    }
    public void setTotalVisits(int count) { lblTotalVisitsValue.setText(String.valueOf(count)); }
    public void setRelationshipStats(String stats) { txtRelationshipStats.setText(stats); }

    public void addBackListener(ActionListener listener) { btnBack.addActionListener(listener); }
    // --- PHƯƠNG THỨC LISTENER MỚI ---
    public void addRefreshListener(ActionListener listener) { btnRefresh.addActionListener(listener); }
}