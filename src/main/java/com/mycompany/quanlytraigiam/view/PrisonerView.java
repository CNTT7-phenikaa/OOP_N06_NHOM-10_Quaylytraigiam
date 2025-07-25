/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quanlytraigiam.view;
import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.raven.chart.Chart;
import com.raven.chart.ModelChart;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
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
public class PrisonerView extends javax.swing.JFrame {

    /**
     * Creates new form PrisonerView
     */
    private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    private String filename=null;
    private byte[] prisoner_image=null;
    private byte[] image=null;
    private String [] columnNames = new String [] {
        "STT", "Họ tên", "Ngày sinh", "Giới tính", "Quê quán", "Tội danh", "Ngày nhập trại" , "Kết án",
        "Số năm lĩnh án" , "Trại giam", "Ảnh"};
    private String [] columnNames2 = new String [] {
        "<none>","Số lượng"};
    private Object data = new Object [][] {};
    Chart chart=new Chart();
    public PrisonerView() {
        initComponents();
        btnAdd.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSearch.setEnabled(true);
        lblImage.setIcon(new ImageIcon("default-image.png"));
        tablePrisoner.setDefaultRenderer(Object.class, new MyRenderer());
        tableStatistic.setDefaultRenderer(Object.class, new MyRenderer2());
        //jLabel14.setLablFor(new CircleLabel());      
        OpeningDateChooser.setBackground(new Color(0, 204, 255));
        chart1.addLegend("Số lượng", new Color(0, 178, 238));
        
        // Ẩn spinner và label ngay từ đầu nếu cần
        spnSentenceYears.setVisible(true); // hoặc false, tùy chọn mặc định
        lblYears.setVisible(true);

        // Xử lý thay đổi lựa chọn án
        cboSentenceType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = cboSentenceType.getSelectedItem().toString();
                boolean isLimited = selected.equals("Tù có thời hạn");
                spnSentenceYears.setVisible(isLimited);
                lblYears.setVisible(isLimited);
            }
        });
    }
  
    //Xử lý phần ảnh 
    private static Image getCircleImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage circleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = circleImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
        graphics.setClip(circle);
        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(Color.WHITE);
        graphics.setStroke(new BasicStroke(2));
        graphics.draw(circle);
        return circleImage;
    }
    
    private ImageIcon ImageIconSize(JLabel label, String filename)
    {
        Image image = new ImageIcon(filename).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon=new ImageIcon(image);
        //jLabel14.setIcon(new ImageIcon(getCircleImage(imageIcon.getImage())));
        return imageIcon;
    }

    private String abbreviation(String name) {
        return name;
    }
    //Xử lý bảng hiển thị thông tin
    public class MyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            TableColumnModel columnModel=table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);   // STT
            columnModel.getColumn(1).setPreferredWidth(150);  // Họ tên
            columnModel.getColumn(2).setPreferredWidth(100);  // Ngày sinh
            columnModel.getColumn(3).setPreferredWidth(70);   // Giới tính
            columnModel.getColumn(4).setPreferredWidth(120);  // Quê quán
            columnModel.getColumn(5).setPreferredWidth(160);  // Tội danh
            columnModel.getColumn(6).setPreferredWidth(100);  // Ngày nhập trại
            columnModel.getColumn(7).setPreferredWidth(100);  // Kết án
            columnModel.getColumn(8).setPreferredWidth(40);   // Số năm lĩnh án
            columnModel.getColumn(9).setPreferredWidth(140);  // Trại giam
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(0, 0, 139));
            header.setForeground(Color.WHITE);
            header.setFont(new java.awt.Font("Times New Roman", 0, 18));
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(new Color(191, 239, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
            } else {
                c.setBackground(new Color(193, 255, 193));
            }

            return c;
        }
    }
    //Xử lý bảng thống kê
    public class MyRenderer2 extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            TableColumnModel columnModel=table.getColumnModel();
            //columnModel.getColumn(0).setPreferredWidth(5);
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(0, 0, 139));
            header.setForeground(Color.WHITE);
            header.setFont(new java.awt.Font("Times New Roman", 0, 18));
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(new Color(191, 239, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
            } else {
                c.setBackground(new Color(193, 255, 193));
            }

            return c;
        }
    }
    //Xử lý viết hoa chữ cái đầu trong chuỗi
    public static String capitalizeWords(String str) {
        str = str.toLowerCase();
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                if (word.equals("tt") || word.equals("tp") || word.equals("tx")) {
                    sb.append(word.toUpperCase());
                } else {
                    sb.append(Character.toUpperCase(word.charAt(0)));
                    sb.append(word.substring(1));
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SearchDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        CheckBoxName = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        CheckBoxAddress = new javax.swing.JCheckBox();
        CheckBoxYear = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        FieldSearch = new javax.swing.JTextField();
        btnCancelDialog1 = new javax.swing.JButton();
        btnSearchDialog = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        btnGroupSearch = new javax.swing.ButtonGroup();
        StatisticView = new javax.swing.JFrame();
        panelChart = new javax.swing.JPanel();
        ScrollPaneStatistic = new javax.swing.JScrollPane();
        tableStatistic = new javax.swing.JTable();
        lblTable = new javax.swing.JLabel();
        chart1 = new com.raven.chart.Chart();
        lblChart = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnStatisticAge = new javax.swing.JButton();
        btnStatisticUnder = new javax.swing.JButton();
        btnStatisticType = new javax.swing.JButton();
        timingTargetAdapter1 = new org.jdesktop.animation.timing.TimingTargetAdapter();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnCancelSearch = new javax.swing.JButton();
        btnManagerUndo = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        btnSortByOpeningDate = new javax.swing.JButton();
        btnSortByName = new javax.swing.JButton();
        btnStatistic = new javax.swing.JButton();
        btnSortByYear = new javax.swing.JButton();
        btnSortByID = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cboCrime = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        btnImage = new javax.swing.JButton();
        BirthdayChooser = new com.toedter.calendar.JDateChooser();
        OpeningDateChooser = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextAreaAddress = new javax.swing.JTextArea();
        FieldSum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        FieldName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePrisoner = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FieldID = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboGender = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cboSentenceType = new javax.swing.JComboBox<>();
        spnSentenceYears = new javax.swing.JSpinner();
        lblYears = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cboPrison = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        SearchDialog.setResizable(false);
        SearchDialog.setSize(new java.awt.Dimension(450, 370));

        jPanel3.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel10.setText("Tìm kiếm");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(170, 40, 110, 29);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Chọn tiêu chí tìm kiếm:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(40, 190, 290, 29);

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
        CheckBoxName.setBounds(20, 230, 85, 20);
        CheckBoxName.setOpaque(false);

        jLabel11.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/search.png"));
        jPanel3.add(jLabel11);
        jLabel11.setBounds(30, 130, 30, 30);

        btnGroupSearch.add(CheckBoxAddress);
        CheckBoxAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxAddress.setText("Quê quán");
        CheckBoxAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxAddress);
        CheckBoxAddress.setBounds(160, 230, 100, 20);
        CheckBoxAddress.setOpaque(false);

        btnGroupSearch.add(CheckBoxYear);
        CheckBoxYear.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        CheckBoxYear.setText("Năm sinh");
        CheckBoxYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(CheckBoxYear);
        CheckBoxYear.setBounds(320, 230, 100, 25);
        CheckBoxYear.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Nhập nội dung tìm kiếm:");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(40, 90, 290, 29);

        FieldSearch.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        FieldSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 102)));
        jPanel3.add(FieldSearch);
        FieldSearch.setBounds(60, 130, 340, 40);
        FieldSearch.setOpaque(false);

        btnCancelDialog1.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnCancelDialog1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelDialog1.setText("Hủy");
        btnCancelDialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnCancelDialog1);
        btnCancelDialog1.setBounds(240, 270, 140, 50);
        btnCancelDialog1.setBorder(new RoundedBorder(20));
        btnCancelDialog1.setOpaque(false);

        btnSearchDialog.setBackground(new java.awt.Color(255, 255, 255, 0));
        btnSearchDialog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSearchDialog.setText("Tìm kiếm");
        btnSearchDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(btnSearchDialog);
        btnSearchDialog.setBounds(50, 270, 140, 50);
        btnSearchDialog.setBorder(new RoundedBorder(20));
        btnSearchDialog.setOpaque(false);

        jLabel15.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/viewSearchView.png"));
        jLabel15.setText("=");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(0, 0, 450, 360);

        javax.swing.GroupLayout SearchDialogLayout = new javax.swing.GroupLayout(SearchDialog.getContentPane());
        SearchDialog.getContentPane().setLayout(SearchDialogLayout);
        SearchDialogLayout.setHorizontalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
        SearchDialogLayout.setVerticalGroup(
            SearchDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
        );

        StatisticView.setTitle("Thống kê");
        StatisticView.setResizable(false);
        StatisticView.setSize(new java.awt.Dimension(1250, 600));
        StatisticView.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                StatisticViewWindowOpened(evt);
            }
        });

        panelChart.setBackground(new java.awt.Color(102, 204, 255));

        tableStatistic.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableStatistic.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            columnNames2
        ));
        tableStatistic.setRowHeight(30);
        ScrollPaneStatistic.setViewportView(tableStatistic);

        lblTable.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTable.setText("Thống kê số lượng theo mục");

        lblChart.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChart.setText("Biểu đồ");

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));

        btnStatisticAge.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticAge.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticAge.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticAge.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Calendar.png"));
        btnStatisticAge.setText("Tuổi");
        btnStatisticAge.setBorder(null);
        btnStatisticAge.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticAgeActionPerformed(evt);
            }
        });

        btnStatisticUnder.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticUnder.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticUnder.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticUnder.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Undo.png"));
        btnStatisticUnder.setText("Quay lại");
        btnStatisticUnder.setBorder(null);
        btnStatisticUnder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticUnder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticUnderActionPerformed(evt);
            }
        });

        btnStatisticType.setBackground(new java.awt.Color(0, 0, 102));
        btnStatisticType.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnStatisticType.setForeground(new java.awt.Color(255, 255, 255));
        btnStatisticType.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/People.png"));
        btnStatisticType.setText("Loại đối tượng");
        btnStatisticType.setBorder(null);
        btnStatisticType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatisticType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnStatisticAge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStatisticUnder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStatisticType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(btnStatisticAge, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnStatisticType, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStatisticUnder, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTable, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                    .addComponent(ScrollPaneStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chart1, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                    .addComponent(lblChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTable, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChart, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPaneStatistic, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout StatisticViewLayout = new javax.swing.GroupLayout(StatisticView.getContentPane());
        StatisticView.getContentPane().setLayout(StatisticViewLayout);
        StatisticViewLayout.setHorizontalGroup(
            StatisticViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StatisticViewLayout.setVerticalGroup(
            StatisticViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý các đối tượng đặc biệt trong khu vực");
        setName("ManagerFrame"); // NOI18N
        setResizable(false);

        //jPanel1.setLayout(new AbsoluteLayout());
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(0, 102, 204, 175));

        btnAdd.setBackground(new java.awt.Color(39, 97, 143));
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

        btnCancelSearch.setBackground(new java.awt.Color(39, 97, 143));
        btnCancelSearch.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelSearch.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/cancel.png"));
        btnCancelSearch.setText("Hủy tìm kiếm");
        btnCancelSearch.setEnabled(false);
        btnCancelSearch.setToolTipText("");
        btnCancelSearch.setBorder(null);
        btnCancelSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSearchActionPerformed(evt);
            }
        });

        btnManagerUndo.setBackground(new java.awt.Color(39, 97, 143));
        btnManagerUndo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnManagerUndo.setForeground(new java.awt.Color(255, 255, 255));
        btnManagerUndo.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/LogOut.png"));
        btnManagerUndo.setText("Quay lại");
        btnManagerUndo.setToolTipText("");
        btnManagerUndo.setBorder(null);
        btnManagerUndo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManagerUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerUndoActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(39, 97, 143));
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

        btnClear.setBackground(new java.awt.Color(39, 97, 143));
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

        btnSearch.setBackground(new java.awt.Color(39, 97, 143));
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

        btnEdit.setBackground(new java.awt.Color(39, 97, 143));
        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Edit.png"));
        btnEdit.setText("Sửa");
        btnEdit.setBorder(null);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        ImageIcon imageIcon = new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/logo.png");
        Image image = imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon=new ImageIcon(image);
        jLabel14.setIcon(imageIcon);
        jLabel4.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnManagerUndo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCancelSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btnManagerUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 170, 660);
        //jPanel2.setOpaque(false);

        btnSortByOpeningDate.setBackground(new java.awt.Color(33, 91, 138));
        btnSortByOpeningDate.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByOpeningDate.setForeground(new java.awt.Color(255, 255, 255));
        btnSortByOpeningDate.setText("Sắp xếp theo ngày nhập trại");
        btnSortByOpeningDate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByOpeningDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByOpeningDateActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByOpeningDate);
        btnSortByOpeningDate.setBounds(780, 330, 210, 40);

        btnSortByName.setBackground(new java.awt.Color(33, 91, 138));
        btnSortByName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByName.setForeground(new java.awt.Color(255, 255, 255));
        btnSortByName.setText("Sắp xếp theo tên");
        btnSortByName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByNameActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByName);
        btnSortByName.setBounds(400, 330, 150, 40);

        btnStatistic.setBackground(new java.awt.Color(33, 91, 138));
        btnStatistic.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnStatistic.setForeground(new java.awt.Color(255, 255, 255));
        btnStatistic.setText("Thống kê");
        btnStatistic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });
        jPanel1.add(btnStatistic);
        btnStatistic.setBounds(1010, 330, 150, 40);
        //btnSortByID.setOpaque(false);

        btnSortByYear.setBackground(new java.awt.Color(33, 91, 138));
        btnSortByYear.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByYear.setForeground(new java.awt.Color(255, 255, 255));
        btnSortByYear.setText("Sắp xếp theo năm sinh");
        btnSortByYear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByYearActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByYear);
        btnSortByYear.setBounds(580, 330, 180, 40);

        btnSortByID.setBackground(new java.awt.Color(33, 91, 138));
        btnSortByID.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSortByID.setForeground(new java.awt.Color(255, 255, 255));
        btnSortByID.setText("Sắp xếp theo ID");
        btnSortByID.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSortByID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByIDActionPerformed(evt);
            }
        });
        jPanel1.add(btnSortByID);
        btnSortByID.setBounds(230, 330, 150, 40);
        //btnSortByID.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Ảnh:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(870, 60, 50, 42);

        cboCrime.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        cboCrime.setMaximumRowCount(10);
        cboCrime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "<none>",
            "Giết người",
            "Cướp tài sản",
            "Trộm cắp tài sản",
            "Lừa đảo chiếm đoạt tài sản",
            "Buôn bán ma túy",
            "Sản xuất, tàng trữ trái phép chất ma túy",
            "Gây rối trật tự công cộng",
            "Đánh bạc trái phép",
            "Xâm phạm an ninh quốc gia",
            "Tham nhũng",
            "Chống người thi hành công vụ",
            "Cưỡng đoạt tài sản",
            "Cố ý gây thương tích",
            "Hiếp dâm",
            "Tàng trữ vũ khí trái phép",
            "Làm giả giấy tờ, tài liệu",
            "Tổ chức nhập cảnh trái phép",
            "Tàng trữ văn hóa phẩm đồi trụy"
        })
    );
    cboCrime.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cboCrimeActionPerformed(evt);
        }
    });
    jPanel1.add(cboCrime);
    cboCrime.setBounds(270, 250, 240, 45);
    cboCrime.setOpaque(false);

    jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel13.setText("Tổng số phạm nhân:");
    jPanel1.add(jLabel13);
    jLabel13.setBounds(550, 20, 160, 21);

    btnImage.setBackground(new java.awt.Color(255, 255, 255, 0));
    btnImage.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
    btnImage.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/green pin.png"));
    btnImage.setText("<html>Thêm ảnh<br> ");
    btnImage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnImage.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnImageActionPerformed(evt);
        }
    });
    jPanel1.add(btnImage);
    btnImage.setBounds(1093, 60, 100, 50);
    btnImage.setOpaque(false);
    btnImage.setBorder(new RoundedBorder(20));

    BirthdayChooser.setBackground(new java.awt.Color(0, 204, 255));
    BirthdayChooser.setForeground(new java.awt.Color(102, 255, 255));
    BirthdayChooser.setDateFormatString("dd/MM/yyyy");
    BirthdayChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
    jPanel1.add(BirthdayChooser);
    BirthdayChooser.setBounds(270, 130, 220, 40);

    OpeningDateChooser.setBackground(new java.awt.Color(0, 204, 255));
    OpeningDateChooser.setForeground(new java.awt.Color(102, 255, 255));
    OpeningDateChooser.setDateFormatString("dd/MM/yyyy");
    OpeningDateChooser.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
    jPanel1.add(OpeningDateChooser);
    OpeningDateChooser.setBounds(430, 200, 250, 40);

    TextAreaAddress.setBackground(new java.awt.Color(255, 255, 255, 0));
    TextAreaAddress.setColumns(20);
    TextAreaAddress.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
    TextAreaAddress.setRows(5);
    TextAreaAddress.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 153)));
    jScrollPane2.setViewportView(TextAreaAddress);
    TextAreaAddress.setOpaque(false);

    jPanel1.add(jScrollPane2);
    jScrollPane2.setBounds(600, 110, 260, 70);
    jScrollPane2.setOpaque(false);

    FieldSum.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
    FieldSum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    FieldSum.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 51, 102)));
    FieldSum.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            FieldSumActionPerformed(evt);
        }
    });
    jPanel1.add(FieldSum);
    FieldSum.setBounds(710, 10, 70, 40);
    FieldSum.setOpaque(false);
    FieldSum.setEditable(false);

    jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel7.setText("Ngày nhập trại: (dd/MM/yyyy)");
    jPanel1.add(jLabel7);
    jLabel7.setBounds(180, 200, 250, 42);

    jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel6.setText("Tội danh:");
    jPanel1.add(jLabel6);
    jLabel6.setBounds(180, 260, 80, 37);

    jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel1.setText("Họ và tên:");
    jPanel1.add(jLabel1);
    jLabel1.setBounds(180, 70, 90, 30);

    lblImage.setBackground(new java.awt.Color(153, 153, 153));
    lblImage.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 51, 153)));
    jPanel1.add(lblImage);
    lblImage.setBounds(930, 10, 153, 153);

    FieldName.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
    FieldName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 51, 102)));
    FieldName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            FieldNameActionPerformed(evt);
        }
    });
    jPanel1.add(FieldName);
    FieldName.setBounds(270, 60, 220, 40);
    FieldName.setOpaque(false);

    jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel2.setText("ID:");
    jPanel1.add(jLabel2);
    jLabel2.setBounds(180, 20, 60, 21);

    jScrollPane1.setBackground(new java.awt.Color(0, 51, 153, 125));

    tablePrisoner.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
    tablePrisoner.setModel(new javax.swing.table.DefaultTableModel(
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
    tablePrisoner.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
    tablePrisoner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    tablePrisoner.setRowHeight(30);
    jScrollPane1.setViewportView(tablePrisoner);
    tablePrisoner.removeColumn(tablePrisoner.getColumnModel().getColumn(6));

    jPanel1.add(jScrollPane1);
    jScrollPane1.setBounds(180, 380, 1020, 270);

    jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel4.setText("Quê quán:");
    jPanel1.add(jLabel4);
    jLabel4.setBounds(510, 130, 90, 42);

    jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel3.setText("Ngày sinh:");
    jPanel1.add(jLabel3);
    jLabel3.setBounds(180, 130, 90, 42);

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
    FieldID.setBounds(230, 10, 70, 40);
    FieldID.setOpaque(false);

    jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel16.setText("Giới tính:");
    jLabel16.setMaximumSize(new java.awt.Dimension(25, 21));
    jLabel16.setMinimumSize(new java.awt.Dimension(25, 21));
    jLabel16.setPreferredSize(new java.awt.Dimension(25, 21));
    jPanel1.add(jLabel16);
    jLabel16.setBounds(510, 70, 80, 21);

    cboGender.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
    cboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác", " " }));
    cboGender.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cboGenderActionPerformed(evt);
        }
    });
    jPanel1.add(cboGender);
    cboGender.setBounds(610, 70, 77, 27);

    jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel17.setText("Kết án:");
    jPanel1.add(jLabel17);
    jLabel17.setBounds(740, 210, 70, 30);

    cboSentenceType.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
    cboSentenceType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
        "<none>", "Tù có thời hạn", "Tù chung thân", "Tử hình"
    })

    );
    cboSentenceType.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cboSentenceTypeActionPerformed(evt);
        }
    });
    jPanel1.add(cboSentenceType);
    cboSentenceType.setBounds(810, 200, 150, 40);
    jPanel1.add(spnSentenceYears);
    spnSentenceYears.setBounds(1050, 210, 80, 30);

    lblYears.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    lblYears.setText("Số năm:");
    jPanel1.add(lblYears);
    lblYears.setBounds(980, 210, 70, 30);

    jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
    jLabel18.setText("Trại giam:");
    jPanel1.add(jLabel18);
    jLabel18.setBounds(720, 270, 90, 30);

    cboPrison.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
    cboPrison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn trại giam", " Trại giam A", "Trại giam ", " " }));
    jPanel1.add(cboPrison);
    cboPrison.setBounds(820, 260, 190, 40);

    jLabel9.setIcon(new ImageIcon("src/main/java/com/mycompany/quanlydoituongdacbiet/view/Lovepik_com-500330964-blue-blazed-background.jpg"));
    jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jPanel1.add(jLabel9);
    jLabel9.setBounds(-60, 0, 1640, 890);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1207, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckBoxNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxNameActionPerformed

    private void btnSortByIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByIDActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSortByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByNameActionPerformed

    private void btnCancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelSearchActionPerformed

    private void FieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldNameActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void FieldIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldIDActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSortByOpeningDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByOpeningDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByOpeningDateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSortByYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSortByYearActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImageActionPerformed

    private void FieldSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldSumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldSumActionPerformed

    private void btnManagerUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerUndoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnManagerUndoActionPerformed

    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticActionPerformed

    private void StatisticViewWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StatisticViewWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_StatisticViewWindowOpened

    private void btnStatisticTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticTypeActionPerformed

    private void btnStatisticUnderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticUnderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticUnderActionPerformed

    private void btnStatisticAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStatisticAgeActionPerformed

    private void cboCrimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCrimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCrimeActionPerformed

    private void cboSentenceTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSentenceTypeActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cboSentenceTypeActionPerformed

    private void cboGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGenderActionPerformed

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
            java.util.logging.Logger.getLogger(PrisonerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrisonerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrisonerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrisonerView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrisonerView().setVisible(true);
            }
        });
    }
    //Hiển thị một hộp thoại thông báo
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    //bo góc cho thành phần Swing
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
    
    public void PrisonerImage()
    {
        String lastImagePath = "";
        JFileChooser chooser=new JFileChooser(lastImagePath);
        chooser.setDialogTitle("Chọn ảnh");
        // Giới hạn chọn tệp đến các tệp hình ảnh
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg")
                    || f.getName().toLowerCase().endsWith(".png")
                    || f.getName().toLowerCase().endsWith(".gif")
                    || f.isDirectory();
            }
            public String getDescription() {
                return "Hình ảnh (*.jpg, *.png, *.gif)";
            }
        });
        chooser.showOpenDialog(null);
        File f=chooser.getSelectedFile();
        filename=f.getAbsolutePath();
        lastImagePath = f.getPath();
        ImageIcon imageIcon=new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
        lblImage.setIcon(imageIcon);
        try
        {
            File image=new File(filename);
            FileInputStream fis=new FileInputStream(image);
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            byte[] buf=new byte[1024];
            for (int readNum;(readNum=fis.read(buf))!=-1;)
            {
                bos.write(buf, 0, readNum);
            }
            prisoner_image=bos.toByteArray();
        }
        catch (Exception e)
        {
            showMessage(e.toString());
        }
    }
    
    /**
     * hiển thị list prisoner vào bảng tablePrisoner
     * 
     * @param list
     */
    public void showListPrisoners(List<Prisoner> list) {
        int size = list.size();
        // với bảng tablePrisoner có 10 cột, 
        // khởi tạo mảng 2 chiều prisoner, trong đó:
        // số hàng: là kích thước của list prisoner 
        // số cột: là 11
        Object [][] prisoner = new Object[size][11];
        for (int i = 0; i < size; i++) {
            prisoner[i][0] = list.get(i).getId();
            prisoner[i][1] = list.get(i).getName();
            prisoner[i][2] = fDate.format(list.get(i).getBirthday());
            prisoner[i][3] = list.get(i).getGender();
            prisoner[i][4] = list.get(i).getAddress();
            prisoner[i][5] = list.get(i).getCrime();
            prisoner[i][6] = fDate.format(list.get(i).getImprisonmentDate());
            prisoner[i][7] = list.get(i).getSentenceType();
            prisoner[i][8] = list.get(i).getSentenceYears();
            prisoner[i][9] = list.get(i).getPrisonName();
            prisoner[i][10] = list.get(i).getPicture();
        }
        //jLabel1.setLayout(null);
        tablePrisoner.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablePrisoner.setModel(new DefaultTableModel(prisoner, columnNames));
        tablePrisoner.removeColumn(tablePrisoner.getColumnModel().getColumn(10));
    }
    
    public void showCountListPrisoners(List<Prisoner> list) {
        int size = list.size();
        FieldSum.setText(String.valueOf(size));
    }
    /**
     * điền thông tin của hàng được chọn từ bảng prisoner 
     * vào các trường tương ứng của prisoner.
     */
    public void fillPrisonerFromSelectedRow() throws ParseException {
        // lấy chỉ số của hàng được chọn 
        int row = tablePrisoner.getSelectedRow();
        if (row >= 0) {
            FieldID.setText(tablePrisoner.getModel().getValueAt(row, 0).toString());
            FieldName.setText(tablePrisoner.getModel().getValueAt(row, 1).toString());
            BirthdayChooser.setDate(fDate.parse(tablePrisoner.getModel().getValueAt(row, 2).toString()));
            cboGender.setSelectedItem(tablePrisoner.getModel().getValueAt(row, 3).toString());
            TextAreaAddress.setText(tablePrisoner.getModel().getValueAt(row, 4).toString());
            cboCrime.setSelectedItem(tablePrisoner.getModel().getValueAt(row, 5).toString());
            OpeningDateChooser.setDate(fDate.parse(tablePrisoner.getModel().getValueAt(row, 6).toString()));
            cboSentenceType.setSelectedItem(tablePrisoner.getModel().getValueAt(row, 7).toString());
            spnSentenceYears.setValue(Integer.parseInt(tablePrisoner.getModel().getValueAt(row, 8).toString()));
            cboPrison.setSelectedItem(tablePrisoner.getModel().getValueAt(row, 9).toString());
            
            //ImageIcon imageIcon=new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
            //lblImage.setIcon((Icon) tablePrisoner.getModel().getValueAt(row, 6));
            byte[] img=(byte[]) tablePrisoner.getModel().getValueAt(row, 10);
            prisoner_image=img;
            ImageIcon imageIcon=new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
            lblImage.setIcon(imageIcon);
            // enable Edit and Delete buttons
            btnEdit.setEnabled(true);
            btnDelete.setEnabled(true);
            // disable Add button
            btnAdd.setEnabled(false);
            btnClear.setEnabled(true);
        }
    }

    /**
     * xóa thông tin prisoner
     */
    public void clearPrisonerInfo() {
        FieldID.setText("");
        FieldName.setText("");
        BirthdayChooser.setDate(null);
        cboGender.setSelectedItem("Nam");
        TextAreaAddress.setText("");
        cboCrime.setSelectedItem("<none>");
        OpeningDateChooser.setDate(null);
        cboSentenceType.setSelectedItem("<none>");
        spnSentenceYears.setValue(0);
        spnSentenceYears.setEnabled(false);
        cboPrison.setSelectedItem("Chọn trại giam");
        lblImage.setIcon(new ImageIcon("default-image.png")); 
        prisoner_image=null;
        
        // disable Edit and Delete buttons
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        // enable Add button
        btnAdd.setEnabled(true);
    }
    
    public void searchNamePrisonerInfo() {
        //FrameSearch = new PrisonerView();
        SearchDialog.setVisible(true);
    }
    
    public void displayStatisticView() {
     
        StatisticView.setVisible(true);
        PrisonerView.this.setVisible(false);
        StatisticView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                StatisticView.dispose();
                PrisonerView.this.setVisible(true);
            }
        });
    }
    
    public void cancelDialogSearchPrisonerInfo() {
        SearchDialog.setVisible(false);
    }
    
    /**
     * hiện thị thông tin prisoner
     * 
     * @param prisoner
     */
    public void showPrisoner(Prisoner prisoner) 
    {
        FieldID.setText("" + prisoner.getId());
        FieldName.setText(prisoner.getName());
        BirthdayChooser.setDate(prisoner.getBirthday());
        cboGender.setSelectedItem(prisoner.getGender());
        TextAreaAddress.setText(prisoner.getAddress());
        OpeningDateChooser.setDate(prisoner.getImprisonmentDate());
        cboCrime.setSelectedItem(""+prisoner.getCrime());
        cboSentenceType.setSelectedItem(prisoner.getSentenceType());
        spnSentenceYears.setValue(prisoner.getSentenceYears());
        spnSentenceYears.setEnabled("Tù có thời hạn".equals(prisoner.getSentenceType()));
        cboPrison.setSelectedItem(prisoner.getPrisonName());
        byte[] img = prisoner.getPicture();
        if (img != null) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage()
                .getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH));
            lblImage.setIcon(imageIcon);
        } else {
            lblImage.setIcon(new ImageIcon("default-image.png")); // hoặc đặt ảnh mặc định
        }

        // enable Edit and Delete buttons
        
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        // disable Add button
        btnAdd.setEnabled(false);
    }
    
    /**
     * lấy thông tin prisoner
     * 
     * @return
     */
    public Prisoner getPrisonerInfo() {
        // validate prisoner
        if (!validateName() || !validateYear() || !validateAddress() || !validateImage()
                || !validateOpeningDate() || !validateCrime() || !validatePrison() || !validateSentenceType()
                || !validateSentenceYears()) {
            return null;
        }
        try {
            Prisoner prisoner = new Prisoner();
            if (FieldID.getText() != null && !"".equals(FieldID.getText())) {
                prisoner.setId(Integer.parseInt(FieldID.getText()));
            }
            prisoner.setName(capitalizeWords(FieldName.getText().trim()));
            prisoner.setBirthday(BirthdayChooser.getDate());
            prisoner.setGender(cboGender.getSelectedItem().toString());
            prisoner.setAddress(capitalizeWords(TextAreaAddress.getText().trim()));
            prisoner.setCrime(cboCrime.getSelectedItem().toString().trim());
            prisoner.setImprisonmentDate(OpeningDateChooser.getDate());
            
            // Xử lý kết án và số năm
            String sentenceType = cboSentenceType.getSelectedItem().toString();
            prisoner.setSentenceType(sentenceType);
            if ("Tù có thời hạn".equals(sentenceType)) {
                prisoner.setSentenceYears((int) spnSentenceYears.getValue());
            } else {
                prisoner.setSentenceYears(0);
            }

            prisoner.setPrisonName(cboPrison.getSelectedItem().toString());
            prisoner.setPicture(prisoner_image);
            return prisoner;
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
        return null;
    }
     //Kiểm tra họ tên
    private boolean validateName() {
        String name = FieldName.getText();
        if (name == null || "".equals(name.trim())) {
            FieldName.requestFocus();
            showMessage("Họ và tên không được trống.");
            return false;
        }
        return true;
    }
    //Ktra ngày sinh
    private boolean validateYear() {
        try {
            java.util.Date today=new java.util.Date();
            Date date=BirthdayChooser.getDate();
            if (date.compareTo(today)==1) {
                BirthdayChooser.requestFocus();
                showMessage("Ngày nhập không tồn tại hoặc lớn hơn ngày hôm nay");
                return false;
            }
        } catch (Exception e) {
            BirthdayChooser.requestFocus();
            showMessage("Ngày sinh không được trống");
            return false;
        }
        return true;
    }
    
    //Kiểm tra tội danh
    private boolean validateCrime() {
        String type = cboCrime.getSelectedItem().toString().trim();
        if (type.equals("<none>")) {
            cboCrime.requestFocus();
            showMessage("Bạn chưa chọn loại tội danh");
            return false;
        }
        return true;
    }
    
    //Kiểm tra quê quán
    private boolean validateAddress() {
        String address = TextAreaAddress.getText();
        if (address == null || "".equals(address.trim())) {
            TextAreaAddress.requestFocus();
            showMessage("Quê quán không được trống.");
            return false;
        }
        return true;
    }
    //Kiểm tra ngày nhập trại
    private boolean validateOpeningDate() {
        try {
            java.util.Date today=new java.util.Date();
            Date date=OpeningDateChooser.getDate();
            if (date.compareTo(today)==1) {
                OpeningDateChooser.requestFocus();
                showMessage("Ngày nhập không tồn tại hoặc lớn hơn ngày hôm nay");
                return false;
            }
        } catch (Exception e) {
            OpeningDateChooser.requestFocus();
            showMessage("Bạn đã nhập ngày sai định dạng");
            return false;
        }
        return true;
    }
    
    //Kiểm tra kết án
    private boolean validateSentenceType() {
        String type = cboSentenceType.getSelectedItem().toString();
        if (type == null || type.equals("<none>")) {
            cboSentenceType.requestFocus();
            showMessage("Bạn chưa chọn loại kết án.");
            return false;
        }
        return true;
    }
    //Kiểm tra số năm lãnh án
    private boolean validateSentenceYears() {
        String type = cboSentenceType.getSelectedItem().toString();
        int years = (int) spnSentenceYears.getValue();

        if ("Tù có thời hạn".equals(type)) {
            if (years <= 0) {
                spnSentenceYears.requestFocus();
                showMessage("Số năm kết án phải lớn hơn 0.");
                return false;
            }
        }
        return true;
    }
    //Kiểm tra trại giam
    private boolean validatePrison() {
        String prison = cboPrison.getSelectedItem().toString();
        if (prison == null || prison.equals("Chọn trại giam")) {
            cboPrison.requestFocus();
            showMessage("Bạn chưa chọn trại giam.");
            return false;
        }
        return true;
    }
    //Kiểm tra hình ảnh
    public boolean validateImage() {
        byte[]k=prisoner_image;
        if (k == null) {
            showMessage("Bạn chưa tải ảnh lên!");
            return false;
        }
        return true;
    }
    
    
    //Xử lý phần tìm kiếm
    public int getChooseSelectSearch(){
        if(CheckBoxName.isSelected()) return 1;
        else if(CheckBoxAddress.isSelected()) return 2;
        else if(CheckBoxYear.isSelected()) return 3;
        return 0;
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
    
    public void cancelSearchPrisoner()
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
    
    public void UnderViewPrisoner()
    {
        StatisticView.setVisible(false);
        PrisonerView.this.setVisible(true);
    }
    //Bảng thống kê theo tội danh
    public void showStatisticTypePrisoners(List<Prisoner> list) {
        //tableStatistic=new JTable();
        lblTable.setText("Thống kê số lượng theo tội danh");
        lblChart.setText("Biểu đồ thống kê số lượng theo tội danh");
        chart1.clear();
        int size1 = 18;
        if (tableStatistic.getRowCount()>10){
            size1 = size1 - (tableStatistic.getRowCount()-10);
        }
        chart1.setFont(new java.awt.Font("sansserif", 0, size1)); 
        int size = cboCrime.getItemCount();
        columnNames2 = new String [] {
        "Loại tội danh","Số lượng"};
        // với bảng tablePrisoner có 10 cột, 
        // khởi tạo mảng 2 chiều prisoner, trong đó:
        // số hàng: là kích thước của list prisoner 
        // số cột: là 11
        Map<String, Integer> countMap = new HashMap<>();
        for (Prisoner person : list) {
            if (countMap.containsKey(person.getCrime())) {
                int count = countMap.get(person.getCrime());
                countMap.put(person.getCrime(), count + 1);
            } else {
                countMap.put(person.getCrime(), 1);
            }
        }
        Object [][] statistic = new Object[countMap.size()][2];
        //Object[][] data = new Object[countMap.size()][2];
        int index = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            chart1.addData(new ModelChart(entry.getKey(), new double[]{ConvertToDouble(entry.getValue())}));
            statistic[index][0] = entry.getKey();
            statistic[index][1] = entry.getValue();
            index++;
        }
        tableStatistic.setModel(new DefaultTableModel(statistic, columnNames2));
        chart1.start();
    }
    //Bảng thống kê theo số tuổi
    public void showStatisticAgePrisoners(List<Prisoner> list) {
        java.util.Date referenceDate=new java.util.Date();
        //tableStatistic=new JTable();
        lblTable.setText("Thống kê số lượng theo tuổi");
        lblChart.setText("Biểu đồ thống kê số lượng theo tuổi");
        chart1.clear();
        int size1 = 18;
        if (tableStatistic.getRowCount()>10){
            size1 = size1 - (tableStatistic.getRowCount()-10);
        }
        chart1.setFont(new java.awt.Font("sansserif", 0, size1)); 
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int size = cboCrime.getItemCount();
        columnNames2 = new String[]{"Tuổi", "Số lượng"};
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Prisoner person : list) {
            int age = calculateAge(person.getBirthday(), referenceDate);
            if (countMap.containsKey(age)) {
                int count = countMap.get(age);
                countMap.put(age, count + 1);
            } else {
                countMap.put(age, 1);
            }
        }
        Object [][] statistic = new Object[countMap.size()][2];
        //Object[][] data = new Object[countMap.size()][2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            chart1.addData(new ModelChart(entry.getKey().toString(), new double[]{ConvertToDouble(entry.getValue())}));
            statistic[index][0] = entry.getKey();
            statistic[index][1] = entry.getValue();
            index++;
        }
        tableStatistic.setModel(new DefaultTableModel(statistic, columnNames2));
        chart1.start();
    }
    
    private int calculateAge(Date birthdate, Date referenceDate) {
    if ((birthdate != null) && (referenceDate != null)) {
        Calendar birthCalendar = Calendar.getInstance();
        Calendar referenceCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthdate);
        referenceCalendar.setTime(referenceDate);

        int birthYear = birthCalendar.get(Calendar.YEAR);
        int referenceYear = referenceCalendar.get(Calendar.YEAR);

        return referenceYear - birthYear;
    } else {
        return 0; // Hoặc giá trị mặc định khác tùy vào yêu cầu của bạn
    }
}
    
    private double ConvertToDouble(Object k)
    {
        return Double.valueOf(k.toString());
    }
    
    public void addAddPrisonerListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }
    
    public void addEditPrisonerListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }
    
    public void addDeletePrisonerListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }
    
    public void addClearListener(ActionListener listener) {
        btnClear.addActionListener(listener);
    }
    
    public void addSortByOpeningDateListener(ActionListener listener) {
        btnSortByOpeningDate.addActionListener(listener);
    }
    
    public void addSortByIDListener(ActionListener listener) {
        btnSortByID.addActionListener(listener);
    }
    
    public void addSortByNameListener(ActionListener listener) {
        btnSortByName.addActionListener(listener);
    }
    
    public void addSortByYearListener(ActionListener listener) {
        btnSortByYear.addActionListener(listener);
    }
    
    public void addSearchListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }
    
    public void addSearchDialogListener(ActionListener listener) {
        btnSearchDialog.addActionListener(listener);
    }
    
    public void addListPrisonerSelectionListener(ListSelectionListener listener) {
        tablePrisoner.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addSearchDiaLogPrisonerListener(ActionListener listener){
        btnSearchDialog.addActionListener(listener);
    }
    
    public void addCancelSearchPrisonerListener(ActionListener listener){
        btnCancelSearch.addActionListener(listener);
    }
    
    public void addImagePrisonerListener(ActionListener listener){
        btnImage.addActionListener(listener);
    }
    
    public void addCancelDialogListener(ActionListener listener){
        btnCancelDialog1.addActionListener(listener);
    }
    
    public void addUndoListener(ActionListener listener){
        btnManagerUndo.addActionListener(listener);
    }
    public void addStatisticListener(ActionListener listener){
        btnStatistic.addActionListener(listener);
    }
    
    public void addStatisticAgeListener(ActionListener listener){
        btnStatisticAge.addActionListener(listener);
    }
    
    public void addStatisticTypeListener(ActionListener listener){
        btnStatisticType.addActionListener(listener);
    }
    
    public void addStatisticUnderListener(ActionListener listener){
        btnStatisticUnder.addActionListener(listener);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser BirthdayChooser;
    private javax.swing.JCheckBox CheckBoxAddress;
    private javax.swing.JCheckBox CheckBoxName;
    private javax.swing.JCheckBox CheckBoxYear;
    private javax.swing.JTextField FieldID;
    private javax.swing.JTextField FieldName;
    private javax.swing.JTextField FieldSearch;
    private javax.swing.JTextField FieldSum;
    private com.toedter.calendar.JDateChooser OpeningDateChooser;
    private javax.swing.JScrollPane ScrollPaneStatistic;
    private javax.swing.JDialog SearchDialog;
    private javax.swing.JFrame StatisticView;
    private javax.swing.JTextArea TextAreaAddress;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancelDialog1;
    private javax.swing.JButton btnCancelSearch;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.ButtonGroup btnGroupSearch;
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnManagerUndo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchDialog;
    private javax.swing.JButton btnSortByID;
    private javax.swing.JButton btnSortByName;
    private javax.swing.JButton btnSortByOpeningDate;
    private javax.swing.JButton btnSortByYear;
    private javax.swing.JButton btnStatistic;
    private javax.swing.JButton btnStatisticAge;
    private javax.swing.JButton btnStatisticType;
    private javax.swing.JButton btnStatisticUnder;
    private javax.swing.JComboBox<String> cboCrime;
    private javax.swing.JComboBox<String> cboGender;
    private javax.swing.JComboBox<String> cboPrison;
    private javax.swing.JComboBox<String> cboSentenceType;
    private com.raven.chart.Chart chart1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChart;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblTable;
    private javax.swing.JLabel lblYears;
    private javax.swing.JPanel panelChart;
    private javax.swing.JSpinner spnSentenceYears;
    private javax.swing.JTable tablePrisoner;
    private javax.swing.JTable tableStatistic;
    private org.jdesktop.animation.timing.TimingTargetAdapter timingTargetAdapter1;
    // End of variables declaration//GEN-END:variables
}
