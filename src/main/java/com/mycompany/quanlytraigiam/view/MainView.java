/*
 * Lớp MainView, phiên bản được thiết kế lại với giao diện hiện đại,
 * sử dụng nền gradient, phối màu tối và các biểu tượng chức năng.
 */
package com.mycompany.quanlytraigiam.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class MainView extends JFrame {

    // --- Bảng màu mới (Dark Theme) ---
    private final Color GRADIENT_START_COLOR = new Color(23, 35, 51);
    private final Color GRADIENT_END_COLOR = new Color(41, 57, 80);
    private final Color BUTTON_COLOR = new Color(52, 152, 219);
    private final Color BUTTON_HOVER_COLOR = new Color(82, 172, 239);
    private final Color TEXT_COLOR = Color.WHITE;
    
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 42);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 20);

    private JButton btnChoosePrison, btnChoosePrisoner, btnChooseVisit, btnStatistics;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Hệ thống Quản lý Trại giam");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // --- Sử dụng GradientPanel làm nền ---
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(20, 30));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // --- Tiêu đề ---
        JLabel titleLabel = new JLabel("Bảng Điều Khiển Chính", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // --- Panel chứa các nút chức năng ---
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        buttonPanel.setOpaque(false); // Làm trong suốt để thấy được nền gradient

        // Sử dụng phương thức trợ giúp để tạo các nút với icon
        btnChoosePrison = createStyledButton("Quản lý Trại giam","");
        btnChoosePrisoner = createStyledButton("Quản lý Phạm nhân","");
        btnChooseVisit = createStyledButton("Quản lý Thăm nuôi", "");
        btnStatistics = createStyledButton("Báo cáo Thống kê", "");

        buttonPanel.add(btnChoosePrison);
        buttonPanel.add(btnChoosePrisoner);
        buttonPanel.add(btnChooseVisit);
        buttonPanel.add(btnStatistics);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    /**
     * Phương thức trợ giúp để tạo một JButton với kiểu dáng và icon.
     */
    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        
        // Tải icon từ thư mục resources
        try {
            URL iconUrl = getClass().getResource(iconPath);
            if (iconUrl != null) {
                button.setIcon(new ImageIcon(iconUrl));
            } else {
                System.err.println("Không tìm thấy icon tại: " + iconPath);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải icon: " + iconPath);
        }
        
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Căn chỉnh để text nằm dưới icon
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(15);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        return button;
    }

    // --- Các phương thức listener để Controller có thể truy cập ---
    public void addChoosePrisonerListener(ActionListener listener) { btnChoosePrisoner.addActionListener(listener); }
    public void addChoosePrisonListener(ActionListener listener) { btnChoosePrison.addActionListener(listener); }
    public void addChooseVisitListener(ActionListener listener) { btnChooseVisit.addActionListener(listener); }
    public void addStatisticsListener(ActionListener listener) { btnStatistics.addActionListener(listener); }

    /**
     * Lớp nội bộ để vẽ nền gradient.
     */
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, GRADIENT_START_COLOR, w, h, GRADIENT_END_COLOR);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
}