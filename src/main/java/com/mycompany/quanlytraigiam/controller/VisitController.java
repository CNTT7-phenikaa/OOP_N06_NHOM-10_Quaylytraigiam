/*
 * Lớp VisitController đã được cập nhật để làm việc với MainView.
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrisoner;
import com.mycompany.quanlytraigiam.action.ManagerVisit;
import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.entity.Visit;
import com.mycompany.quanlytraigiam.view.MainView; // SỬA ĐỔI: Import MainView
import com.mycompany.quanlytraigiam.view.VisitView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VisitController {

    private ManagerVisit managerVisit;
    private ManagerPrisoner managerPrisoner;
    private VisitView visitView;
    private MainView mainView; // SỬA ĐỔI: Sử dụng MainView

    // SỬA ĐỔI: Hàm khởi tạo nhận vào MainView
    public VisitController(VisitView view, MainView mainView) {
        this.visitView = view;
        this.mainView = mainView;
        this.managerVisit = new ManagerVisit();
        this.managerPrisoner = new ManagerPrisoner();

        loadPrisonersToComboBox();
        
        visitView.addPrisonerSelectionListener(new PrisonerSelectionListener());
        visitView.addAddVisitListener(new AddVisitListener());
        visitView.addEditVisitListener(new EditVisitListener());
        visitView.addDeleteVisitListener(new DeleteVisitListener());
        visitView.addClearVisitListener(e -> visitView.clearVisitInfo());
        visitView.addSearchVisitListener(new SearchVisitListener());
        visitView.addBackListener(e -> showMainView());
        visitView.addListVisitSelectionListener(new VisitListSelectionListener());
        
        showVisitList();
    }
    
    private void loadPrisonersToComboBox() {
        List<Prisoner> prisoners = managerPrisoner.getListPrisoners();
        visitView.setPrisonerComboBox(prisoners);
    }

    public void showVisitView() {
        visitView.setVisible(true);
    }
    
    private void showMainView() {
        visitView.dispose();
        mainView.setVisible(true);
    }

    private void showVisitList() {
        List<Visit> visits = managerVisit.getVisits();
        visitView.showListVisits(visits);
    }

    class PrisonerSelectionListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                visitView.updateSelectedPrisonerInfo();
            }
        }
    }

    class AddVisitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Visit newVisit = visitView.getVisitInfo();
            if (newVisit != null) {
                managerVisit.addVisit(newVisit);
                showVisitList();
                visitView.showMessage("Thêm lượt thăm thành công!");
            }
        }
    }

    class EditVisitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visitView.getSelectedRow() == -1) {
                visitView.showMessage("Vui lòng chọn một lượt thăm để sửa.");
                return;
            }
            Visit updatedVisit = visitView.getVisitInfo();
            if (updatedVisit != null) {
                managerVisit.updateVisit(updatedVisit);
                showVisitList();
                visitView.showMessage("Cập nhật lượt thăm thành công!");
            }
        }
    }

    class DeleteVisitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = visitView.getSelectedRow();
            if (selectedRow != -1) {
                int visitId = (int) visitView.getValueAt(selectedRow, 0);
                int confirm = visitView.showMessageConfirm("Bạn có chắc chắn muốn xóa lượt thăm có ID " + visitId + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    managerVisit.deleteVisit(visitId);
                    showVisitList();
                    visitView.showMessage("Xóa lượt thăm thành công!");
                }
            } else {
                visitView.showMessage("Vui lòng chọn một lượt thăm để xóa.");
            }
        }
    }

    class SearchVisitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String keyword = visitView.getSearchKeyword();
            if (keyword != null) {
                List<Visit> searchResult = managerVisit.searchVisits(keyword);
                if (searchResult.isEmpty()) {
                    visitView.showMessage("Không tìm thấy lượt thăm nào phù hợp.");
                }
                visitView.showListVisits(searchResult);
            }
        }
    }

    class VisitListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                visitView.fillVisitFromSelectedRow();
            }
        }
    }
}