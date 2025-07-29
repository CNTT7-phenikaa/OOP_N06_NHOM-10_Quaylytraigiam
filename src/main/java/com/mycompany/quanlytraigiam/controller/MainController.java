/*
 * Lớp MainController đã được nâng cấp toàn diện:
 * 1. Làm việc với giao diện MainView hiện đại.
 * 2. Thêm chức năng mở màn hình Thống kê.
 * 3. Cập nhật cách gọi các Controller con để chúng có thể quay lại MainView.
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.view.MainView; // SỬA ĐỔI: Import MainView
import com.mycompany.quanlytraigiam.view.PrisonerView;
import com.mycompany.quanlytraigiam.view.PrisonView;
import com.mycompany.quanlytraigiam.view.StatisticsView; // MỚI: Import StatisticsView
import com.mycompany.quanlytraigiam.view.VisitView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    // --- Khai báo các View con ---
    private PrisonerView managerView;
    private PrisonView prisonView;
    private VisitView visitView;
    private StatisticsView statisticsView; // MỚI

    private MainView mainView; // SỬA ĐỔI: Sử dụng MainView

    // SỬA ĐỔI: Hàm khởi tạo nhận vào MainView
    public MainController(MainView view) {
        this.mainView = view;
        
        // Gắn các listener cho các nút trên MainView
        view.addChoosePrisonerListener(new ChoosePrisonerListener());
        view.addChoosePrisonListener(new ChoosePrisonListener());
        view.addChooseVisitListener(new ChooseVisitListener());
        view.addStatisticsListener(new ChooseStatisticsListener()); // MỚI
    }

    public void showMainView() {
        mainView.setVisible(true);
    }

    class ChoosePrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerView = new PrisonerView();
            // Nâng cấp: Truyền mainView vào để PrisonerController có thể quay lại
            PrisonerController managerController = new PrisonerController(managerView, mainView);
            managerController.showPrisonerView();
            mainView.setVisible(false);
        }
    }
    
    class ChoosePrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView = new PrisonView();
            // Nâng cấp: Truyền mainView vào để PrisonController có thể quay lại
            PrisonController prisonController = new PrisonController(prisonView, mainView);
            prisonController.showPrisonView();
            mainView.setVisible(false);
        }
    }
    
    class ChooseVisitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            visitView = new VisitView();
            // Truyền mainView vào để VisitController có thể quay lại
            VisitController visitController = new VisitController(visitView, mainView);
            visitController.showVisitView();
            mainView.setVisible(false);
        }
    }

    // --- CLASS LISTENER MỚI CHO CHỨC NĂNG THỐNG KÊ ---
    class ChooseStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            statisticsView = new StatisticsView();
            // Truyền mainView vào để StatisticsController có thể quay lại
            StatisticsController statisticsController = new StatisticsController(statisticsView, mainView);
            statisticsController.showStatisticsView();
            mainView.setVisible(false);
        }
    }
}