package com.mycompany.quanlytraigiam.controller;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
import com.mycompany.quanlytraigiam.action.ManagerVisit;

import com.mycompany.quanlytraigiam.entity.Visit;

import com.mycompany.quanlytraigiam.view.VisitView;

import com.mycompany.quanlytraigiam.view.MainView; // Nếu cần quay lại MainView

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.List;
import javax.swing.JOptionPane;

import javax.swing.event.ListSelectionEvent;

import javax.swing.event.ListSelectionListener;



public class VisitController {

    private ManagerVisit managerVisit;

    private VisitView visitView;

    private MainView MainView; // Có thể cần để quay lại màn hình chính
    private MainView mainView;



    public VisitController(VisitView view) {

        this.visitView = view;

        this.managerVisit = new ManagerVisit(); // Khởi tạo ManagerVisit

        

        // Thêm các Listener cho các nút

        view.addAddVisitListener(new AddVisitListener());

        view.addEditVisitListener(new EditVisitListener());

        view.addDeleteVisitListener(new DeleteVisitListener());

        view.addClearVisitListener(new ClearVisitListener());

        view.addSearchVisitListener(new SearchVisitListener());

        view.addListSelectionListener(new ListVisitSelectionListener());

        

        // Hiển thị danh sách thăm nuôi ban đầu

        showVisitList();

    }

    

    // Constructor cho việc quay lại MainView (tùy chọn)

    public VisitController(VisitView view, MainView mainView) {

        this(view);

        this.mainView = mainView;

    }



    public void showVisitView() {

        visitView.setVisible(true);

    }



    private void showVisitList() {

        List<Visit> visits = managerVisit.getVisits();

        visitView.showListVisits(visits);

        visitView.clearVisitInfo(); // Xóa trắng các trường sau khi hiển thị

    }



    // Listener cho nút "Thêm"

    class AddVisitListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            Visit newVisit = visitView.getVisitInfo();

            if (newVisit != null) {

                // Kiểm tra trùng lặp nếu cần (ví dụ: cùng mã phạm nhân, ngày, giờ)

                boolean exists = managerVisit.getVisits().stream()

                    .anyMatch(v -> v.getInmateId().equals(newVisit.getInmateId()) &&

                                   v.getVisitDate().equals(newVisit.getVisitDate()) &&

                                   v.getVisitTime().equals(newVisit.getVisitTime()));

                

                if (exists) {

                    visitView.showMessage("Lượt thăm này đã tồn tại.");

                } else {

                    managerVisit.addVisit(newVisit);

                    showVisitList();

                    visitView.showMessage("Thêm lượt thăm thành công!");

                }

            }

        }

    }



    // Listener cho nút "Sửa"

    class EditVisitListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            Visit updatedVisit = visitView.getVisitInfo();

            if (updatedVisit != null) {

                if (visitView.getSelectedInmateId() == null) {

                    visitView.showMessage("Vui lòng chọn một lượt thăm để sửa.");

                    return;

                }

                // Giả sử updateVisit cần xác định Visit cũ để sửa,

                // ở đây ta dùng thông tin từ form sau khi người dùng đã chỉnh sửa

                managerVisit.updateVisit(updatedVisit);

                showVisitList();

                visitView.showMessage("Cập nhật lượt thăm thành công!");

            }

        }

    }



    // Listener cho nút "Xóa"

    class DeleteVisitListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            String inmateId = (String) visitView.getSelectedInmateId();

            String visitDate = visitView.getSelectedVisitDate();

            String visitTime = visitView.getSelectedVisitTime();



            if (inmateId != null && visitDate != null && visitTime != null) {

                int confirm = visitView.showMessageConfirm("Bạn có chắc chắn muốn xóa lượt thăm này?");

                if (confirm == JOptionPane.YES_OPTION) {

                    managerVisit.deleteVisit(inmateId, visitDate, visitTime);

                    showVisitList();

                    visitView.showMessage("Xóa lượt thăm thành công!");

                }

            } else {

                visitView.showMessage("Vui lòng chọn một lượt thăm để xóa.");

            }

        }

    }



    // Listener cho nút "Xóa Trắng"

    class ClearVisitListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            visitView.clearVisitInfo();

        }

    }



    // Listener cho nút "Tìm kiếm"

    class SearchVisitListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent e) {

            String keyword = visitView.getSearchKeyword();

            if (keyword.isEmpty()) {

                showVisitList(); // Nếu trống thì hiển thị lại toàn bộ danh sách

            } else {

                List<Visit> searchResult = managerVisit.searchVisits(keyword);

                if (searchResult.isEmpty()) {

                    visitView.showMessage("Không tìm thấy lượt thăm nào phù hợp.");

                }

                visitView.showListVisits(searchResult);

            }

        }

    }



    // Listener khi chọn một hàng trong bảng

    class ListVisitSelectionListener implements ListSelectionListener {

        @Override

        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện chỉ xảy ra một lần khi chọn

                visitView.fillVisitFromSelectedRow();

            }

        }

    }

}
