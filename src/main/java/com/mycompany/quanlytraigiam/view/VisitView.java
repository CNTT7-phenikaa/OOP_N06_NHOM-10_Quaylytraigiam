/*
 * Lớp VisitView đã được cải tiến để sử dụng JDateChooser và JSpinner.
 */
package com.mycompany.quanlytraigiam.view;

import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.entity.Visit;
import com.toedter.calendar.JDateChooser; // MỚI: Import JDateChooser
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner; // MỚI: Import JSpinner
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel; // MỚI: Import SpinnerDateModel
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class VisitView extends JFrame {

    // --- SỬA ĐỔI: Khai báo các thành phần mới ---
    private JComboBox<Prisoner> cbxPrisoners;
    private JTextField txtPrisonerName, txtVisitorName, txtRelationship, txtNotes;
    private JDateChooser dateChooser; // THAY THẾ: txtVisitDate
    private JSpinner timeSpinner;     // THAY THẾ: txtVisitTime
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnSearch, btnBack;
    private JTable visitTable;
    private DefaultTableModel tableModel;

    private final String[] COLUMN_NAMES = {"Visit ID", "Mã phạm nhân", "Tên phạm nhân", "Tên người thăm", "Mối quan hệ", "Ngày thăm", "Giờ thăm", "Ghi chú"};
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public VisitView() {
        initComponents();
        initTable();
        setTitle("Quản lý Thăm Nuôi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // (Phần chọn phạm nhân giữ nguyên như lần trước)
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Chọn phạm nhân:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        cbxPrisoners = new JComboBox<>();
        cbxPrisoners.setRenderer(new PrisonerComboBoxRenderer());
        inputPanel.add(cbxPrisoners, gbc);
        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên phạm nhân:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0;
        txtPrisonerName = new JTextField(20);
        txtPrisonerName.setEditable(false);
        inputPanel.add(txtPrisonerName, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Tên người thăm:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtVisitorName = new JTextField(20);
        inputPanel.add(txtVisitorName, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(new JLabel("Mối quan hệ:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        txtRelationship = new JTextField(20);
        inputPanel.add(txtRelationship, gbc);

        // --- SỬA ĐỔI: Thay thế ô nhập ngày/giờ ---
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Ngày thăm:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        inputPanel.add(dateChooser, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        inputPanel.add(new JLabel("Giờ thăm:"), gbc);
        gbc.gridx = 3; gbc.gridy = 2;
        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date()); // Gán giá trị mặc định là giờ hiện tại
        inputPanel.add(timeSpinner, gbc);

        // (Các thành phần còn lại giữ nguyên)
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 3;
        txtNotes = new JTextField(20);
        inputPanel.add(txtNotes, gbc);
        
        btnAdd = new JButton("Thêm"); btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa"); btnClear = new JButton("Làm mới");
        btnSearch = new JButton("Tìm kiếm"); btnBack = new JButton("Quay lại");
        buttonPanel.add(btnAdd); buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete); buttonPanel.add(btnClear);
        buttonPanel.add(btnSearch); buttonPanel.add(btnBack);

        visitTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(visitTable);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private void initTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        visitTable.setModel(tableModel);
    }

    public void setPrisonerComboBox(List<Prisoner> prisoners) {
        cbxPrisoners.removeAllItems();
        cbxPrisoners.addItem(null);
        for (Prisoner p : prisoners) {
            cbxPrisoners.addItem(p);
        }
    }
    
    public void updateSelectedPrisonerInfo() {
        Prisoner selected = (Prisoner) cbxPrisoners.getSelectedItem();
        txtPrisonerName.setText(selected != null ? selected.getName() : "");
    }

    // --- SỬA ĐỔI: Lấy thông tin từ JDateChooser và JSpinner ---
    public Visit getVisitInfo() {
        Prisoner selectedPrisoner = (Prisoner) cbxPrisoners.getSelectedItem();
        if (selectedPrisoner == null) {
            showMessage("Vui lòng chọn một phạm nhân.");
            return null;
        }
        
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            showMessage("Vui lòng chọn ngày thăm.");
            return null;
        }

        String visitorName = txtVisitorName.getText().trim();
        if (visitorName.isEmpty()) {
            showMessage("Vui lòng điền Tên người thăm.");
            return null;
        }

        // Chuyển đổi từ java.util.Date sang java.time
        LocalDate visitDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date selectedTime = (Date) timeSpinner.getValue();
        LocalTime visitTime = selectedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        
        int visitId = 0;
        int selectedRow = visitTable.getSelectedRow();
        if (selectedRow != -1) {
            visitId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        }

        return new Visit(visitId, String.valueOf(selectedPrisoner.getId()), selectedPrisoner.getName(),
                         visitorName, txtRelationship.getText().trim(), visitDate, visitTime, txtNotes.getText().trim());
    }

    // --- SỬA ĐỔI: Điền thông tin vào JDateChooser và JSpinner ---
    public void fillVisitFromSelectedRow() {
        int row = visitTable.getSelectedRow();
        if (row >= 0) {
            String inmateIdToFind = getValueAt(row, 1).toString();
            for (int i = 0; i < cbxPrisoners.getItemCount(); i++) {
                Prisoner p = cbxPrisoners.getItemAt(i);
                if (p != null && String.valueOf(p.getId()).equals(inmateIdToFind)) {
                    cbxPrisoners.setSelectedItem(p);
                    break;
                }
            }
            
            txtVisitorName.setText(getValueAt(row, 3) != null ? getValueAt(row, 3).toString() : "");
            txtRelationship.setText(getValueAt(row, 4) != null ? getValueAt(row, 4).toString() : "");
            txtNotes.setText(getValueAt(row, 7) != null ? getValueAt(row, 7).toString() : "");

            // Chuyển đổi và gán giá trị cho dateChooser và timeSpinner
            try {
                LocalDate date = LocalDate.parse(getValueAt(row, 5).toString(), dateFormatter);
                dateChooser.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                
                LocalTime time = LocalTime.parse(getValueAt(row, 6).toString(), timeFormatter);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
                calendar.set(Calendar.MINUTE, time.getMinute());
                timeSpinner.setValue(calendar.getTime());
            } catch (Exception e) {
                dateChooser.setDate(null);
            }
        }
    }
    
    // --- SỬA ĐỔI: Xóa giá trị trong JDateChooser và JSpinner ---
    public void clearVisitInfo() {
        cbxPrisoners.setSelectedIndex(0);
        txtPrisonerName.setText("");
        txtVisitorName.setText("");
        txtRelationship.setText("");
        dateChooser.setDate(null); // Xóa ngày
        timeSpinner.setValue(new Date()); // Reset về giờ hiện tại
        txtNotes.setText("");
        visitTable.clearSelection();
    }
    
    // Các phương thức khác giữ nguyên
    public void showListVisits(List<Visit> visits) {
        tableModel.setRowCount(0);
        if (visits != null) {
            for (Visit visit : visits) {
                String visitDateStr = (visit.getVisitDate() != null) ? visit.getVisitDate().format(dateFormatter) : "";
                String visitTimeStr = (visit.getVisitTime() != null) ? visit.getVisitTime().format(timeFormatter) : "";
                tableModel.addRow(new Object[]{
                    visit.getVisitId(), visit.getInmateId(), visit.getPrisonerName(),
                    visit.getVisitorName(), visit.getRelationship(), visitDateStr,
                    visitTimeStr, visit.getNotes()
                });
            }
        }
    }
    
    public int getSelectedRow() { return visitTable.getSelectedRow(); }
    public Object getValueAt(int row, int col) { return (row >= 0 && col >= 0) ? visitTable.getValueAt(row, col) : null; }
    public void showMessage(String message) { JOptionPane.showMessageDialog(this, message); }
    public int showMessageConfirm(String message) { return JOptionPane.showConfirmDialog(this, message, "Xác nhận", JOptionPane.YES_NO_OPTION); }
    public String getSearchKeyword() { return JOptionPane.showInputDialog(this, "Nhập từ khóa tìm kiếm:"); }
    
    public void addPrisonerSelectionListener(ItemListener listener) { cbxPrisoners.addItemListener(listener); }
    public void addAddVisitListener(ActionListener listener) { btnAdd.addActionListener(listener); }
    public void addEditVisitListener(ActionListener listener) { btnEdit.addActionListener(listener); }
    public void addDeleteVisitListener(ActionListener listener) { btnDelete.addActionListener(listener); }
    public void addClearVisitListener(ActionListener listener) { btnClear.addActionListener(listener); }
    public void addSearchVisitListener(ActionListener listener) { btnSearch.addActionListener(listener); }
    public void addBackListener(ActionListener listener) { btnBack.addActionListener(listener); }
    public void addListVisitSelectionListener(ListSelectionListener listener) { visitTable.getSelectionModel().addListSelectionListener(listener); }

    private static class PrisonerComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Prisoner) {
                Prisoner prisoner = (Prisoner) value;
                setText("ID: " + prisoner.getId() + " - " + prisoner.getName());
            } else {
                setText(" -- Chọn một phạm nhân -- ");
            }
            return this;
        }
    }
}