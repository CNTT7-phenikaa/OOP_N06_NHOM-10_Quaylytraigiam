/*
 * Lớp StatisticsController đã được cập nhật để làm việc với MainView.
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrisoner;
import com.mycompany.quanlytraigiam.action.ManagerVisit;
import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.entity.Visit;
import com.mycompany.quanlytraigiam.view.MainView; // SỬA ĐỔI: Import MainView
import com.mycompany.quanlytraigiam.view.StatisticsView;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class StatisticsController {
    private StatisticsView view;
    private MainView mainView; // SỬA ĐỔI: Sử dụng MainView
    private ManagerPrisoner managerPrisoner;
    private ManagerVisit managerVisit;

    // SỬA ĐỔI: Hàm khởi tạo nhận vào MainView
    public StatisticsController(StatisticsView view, MainView mainView) {
        this.view = view;
        this.mainView = mainView;
        
        // Gắn listener cho các nút
        view.addBackListener(e -> showMainView());
        view.addRefreshListener(e -> {
            calculateAndDisplayStatistics();
            JOptionPane.showMessageDialog(view, "Đã cập nhật dữ liệu thống kê!");
        });
        
        // Tính toán và hiển thị lần đầu
        calculateAndDisplayStatistics();
    }
    
    public void showStatisticsView() {
        view.setVisible(true);
    }
    
    private void showMainView() {
        view.dispose();
        mainView.setVisible(true);
    }
    
    private void calculateAndDisplayStatistics() {
        // Tải lại dữ liệu từ file mỗi lần làm mới để đảm bảo tính chính xác
        this.managerPrisoner = new ManagerPrisoner();
        this.managerVisit = new ManagerVisit();
        
        List<Prisoner> prisoners = managerPrisoner.getListPrisoners();
        List<Visit> visits = managerVisit.getVisits();

        int totalPrisoners = prisoners.size();
        long maleCount = prisoners.stream().filter(p -> "Nam".equalsIgnoreCase(p.getGender())).count();
        long femaleCount = prisoners.stream().filter(p -> "Nữ".equalsIgnoreCase(p.getGender())).count();
        long otherCount = totalPrisoners - maleCount - femaleCount;
        
        int totalVisits = visits.size();
        
        Map<String, Long> relationshipCounts = visits.stream()
            .map(Visit::getRelationship)
            .filter(r -> r != null && !r.trim().isEmpty())
            .collect(Collectors.groupingBy(String::trim, Collectors.counting()));
        
        StringBuilder relationshipStats = new StringBuilder();
        if (relationshipCounts.isEmpty()) {
            relationshipStats.append("Chưa có dữ liệu.");
        } else {
            relationshipCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    relationshipStats.append(String.format("- %s: %d lượt\n", entry.getKey(), entry.getValue()));
                });
        }
            
        view.setTotalPrisoners(totalPrisoners);
        view.setGenderStats((int)maleCount, (int)femaleCount, (int)otherCount);
        view.setTotalVisits(totalVisits);
        view.setRelationshipStats(relationshipStats.toString());
    }
}