/*
 * Lớp PrisonController đã được nâng cấp để:
 * 1. Làm việc với MainView.
 * 2. Sửa lại logic "Quay lại" để hiển thị đúng cửa sổ chính đã có.
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrison;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.view.MainView; // SỬA ĐỔI: Import MainView
import com.mycompany.quanlytraigiam.view.PrisonView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PrisonController {
    private PrisonView prisonView;
    private MainView mainView; // SỬA ĐỔI: Sử dụng MainView
    private ManagerPrison managerPrison;

    // SỬA ĐỔI: Hàm khởi tạo nhận vào cả PrisonView và MainView
    public PrisonController(PrisonView view, MainView mainView) {
        this.prisonView = view;
        this.mainView = mainView;
        this.managerPrison = new ManagerPrison();

        // Gắn tất cả các listener cần thiết
        view.addAddPrisonListener(new AddPrisonListener());
        view.addEditPrisonListener(new EditPrisonListener());
        view.addDeletePrisonListener(new DeletePrisonListener());
        view.addClearListener(new ClearPrisonListener());
        view.addSortPrisonListener(new SortPrisonListener());
        view.addSearchListener(new SearchPrisonListener());
        view.addSearchDialogListener(new SearchPrisonDialogListener());
        view.addCancelSearchPrisonListener(new CancelSearchPrisonListener());
        view.addCancelDialogListener(new CancelDialogListener());
        
        // Gắn listener cho cả hai nút quay lại
        BackListener backListener = new BackListener();
        view.addBackListener(backListener);
        view.addUndoListener(backListener); // Hai nút cùng làm một chức năng
        
        view.addListPrisonSelectionListener(new ListPrisonSelectionListener());
    }

    public void showPrisonView() {
        List<Prison> prisonList = managerPrison.getListPrisons();
        prisonView.setVisible(true);
        prisonView.showListPrisons(prisonList);
        prisonView.showTotalPrisons(prisonList.size());
    }

    // SỬA ĐỔI: Logic quay lại màn hình chính
    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.dispose(); // Đóng cửa sổ hiện tại
            mainView.setVisible(true); // Hiển thị lại cửa sổ chính đã có
        }
    }
    
    // Lưu ý: Lớp UndoListener đã bị xóa vì chức năng của nó giống hệt BackListener

    class AddPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prison prison = prisonView.getPrisonInfo();
            if (prison != null) {
                managerPrison.addPrison(prison);
                prisonView.showPrison(prison);
                List<Prison> updatedList = managerPrison.getListPrisons();
                prisonView.showListPrisons(updatedList);
                prisonView.showTotalPrisons(updatedList.size());
                prisonView.showMessage("Thêm trại giam thành công!");
            }
        }
    }

    class EditPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prison prison = prisonView.getPrisonInfo();
            if (prison != null) {
                managerPrison.updatePrison(prison);
                prisonView.showPrison(prison);
                prisonView.showListPrisons(managerPrison.getListPrisons());
                prisonView.showTotalPrisons(managerPrison.getListPrisons().size());
                prisonView.showMessage("Cập nhật trại giam thành công!");
            }
        }
    }

    class DeletePrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prison prison = prisonView.getPrisonInfo();
            if (prison != null) {
                boolean result = managerPrison.deletePrison(prison.getId());
                if (result) {
                    prisonView.clearPrisonInfo();
                    List<Prison> updatedList = managerPrison.getListPrisons();
                    prisonView.showListPrisons(updatedList);
                    prisonView.showTotalPrisons(updatedList.size());
                    prisonView.showMessage("Xóa trại giam thành công!");
                } else {
                    prisonView.showMessage("Không tìm thấy trại giam để xóa!");
                }
            }
        }
    }

    class ListPrisonSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) { // Chỉ xử lý khi việc chọn đã hoàn tất
                List<Prison> prisonList = managerPrison.getListPrisons();
                try {
                    prisonView.fillPrisonFromSelectedRow(prisonList);
                } catch (ParseException ex) {
                    Logger.getLogger(PrisonController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class ClearPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.clearPrisonInfo();
        }
    }

    class SortPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int sortOption = prisonView.getSelectedSortOption();
            switch (sortOption) {
                case 1 -> managerPrison.sortByName();
                case 2 -> managerPrison.sortByCapacity();
                case 3 -> managerPrison.sortByWarden();
                default -> {
                    prisonView.showMessage("Vui lòng chọn tiêu chí sắp xếp");
                    return;
                }
            }
            List<Prison> sortedList = managerPrison.getListPrisons();
            prisonView.showListPrisons(sortedList);
            prisonView.showTotalPrisons(sortedList.size());
        }
    }

    class SearchPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.showSearchDialog();
        }
    }

    class SearchPrisonDialogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int searchOption = prisonView.getSelectedSearchOption();
            String keyword = prisonView.getSearchKeyword();
            if (keyword == null || keyword.trim().isEmpty()) {
                prisonView.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }
            List<Prison> result;
            switch (searchOption) {
                case 1 -> result = managerPrison.searchById(keyword);
                case 2 -> result = managerPrison.searchByName(keyword);
                case 3 -> result = managerPrison.searchByAddress(keyword);
                case 4 -> result = managerPrison.searchByWarden(keyword);
                default -> {
                    prisonView.showMessage("Vui lòng chọn tiêu chí tìm kiếm!");
                    return;
                }
            }
            prisonView.showListPrisons(result);
            prisonView.showTotalPrisons(result.size());
            if (result.isEmpty()) {
                prisonView.showMessage("Không tìm thấy kết quả phù hợp!");
            } else {
                prisonView.showMessage("Tìm thấy " + result.size() + " kết quả!");
            }
        }
    }

    class CancelSearchPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.showListPrisons(managerPrison.getListPrisons());
            prisonView.showTotalPrisons(managerPrison.getListPrisons().size());
            prisonView.cancelSearch();
        }
    }

    class CancelDialogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.closeSearchDialog();
            prisonView.cancelSearch();
            prisonView.showListPrisons(managerPrison.getListPrisons());
            prisonView.showTotalPrisons(managerPrison.getListPrisons().size());
        }
    }
}