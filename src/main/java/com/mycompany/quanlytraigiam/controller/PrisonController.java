package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrison;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.view.MainView;
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
    private MainView mainView;
    private ManagerPrison managerPrison;

    public PrisonController(PrisonView view) {
        this.prisonView = view;
        this.managerPrison = new ManagerPrison();

        view.addUndoListener(new UndoListener());
        view.addAddPrisonListener(new AddPrisonListener());
        view.addListPrisonSelectionListener(new ListPrisonSelectionListener());
        view.addEditPrisonListener(new EditPrisonListener());
        view.addClearListener(new ClearPrisonListener());
        view.addDeletePrisonListener(new DeletePrisonListener());
        view.addSortPrisonListener(new SortPrisonListener());
        view.addSearchListener(new SearchPrisonListener());
        view.addSearchDialogListener(new SearchPrisonDialogListener());
        view.addCancelSearchPrisonListener(new CancelSearchPrisonListener());
        view.addCancelDialogListener(new CancelDialogListener());
        view.addBackListener(new BackListener());
    }

    public void showPrisonView() {
        List<Prison> prisonList = managerPrison.getListPrisons();
        prisonView.setVisible(true);
        prisonView.showListPrisons(prisonList);
    }

    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            prisonView.setVisible(false);
        }
    }

    class AddPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prison prison = prisonView.getPrisonInfo();
            if (prison != null) {
                managerPrison.addPrison(prison);
                prisonView.showPrison(prison);
                prisonView.showListPrisons(managerPrison.getListPrisons());
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
                    prisonView.showListPrisons(managerPrison.getListPrisons());
                    prisonView.showMessage("Xóa trại giam thành công!");
                } else {
                    prisonView.showMessage("Không tìm thấy trại giam để xóa!");
                }
            }
        }
    }

    class ListPrisonSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            List<Prison> prisonList = managerPrison.getListPrisons();
            try {
                prisonView.fillPrisonFromSelectedRow(prisonList);
            } catch (ParseException ex) {
                Logger.getLogger(PrisonController.class.getName()).log(Level.SEVERE, null, ex);
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
                case 1: // Sắp xếp theo tên
                    managerPrison.sortByName();
                    break;
                case 2: // Sắp xếp theo sức chứa
                    managerPrison.sortByCapacity();
                    break;
                case 3: // Sắp xếp theo tên quản lý
                    managerPrison.sortByWarden();
                    break;
                default:
                    prisonView.showMessage("Vui lòng chọn tiêu chí sắp xếp");
                    return;
            }

            // Hiển thị danh sách sau khi sắp xếp
            List<Prison> sortedList = managerPrison.getListPrisons();
            prisonView.showListPrisons(sortedList);
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
            System.out.println("Nút Tìm kiếm được nhấn vào lúc: " + searchOption);
            if (keyword == null || keyword.trim().isEmpty()) {
                prisonView.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
                return;
            }

            List<Prison> result = new ArrayList<>();

            switch (searchOption) {
                case 1: // Tìm theo mã trại giam
                    result = managerPrison.searchById(keyword);
                    break;
                case 2: // Tìm theo tên trại giam
                    result = managerPrison.searchByName(keyword);
                    break;
                case 3: // Tìm theo địa chỉ
                    result = managerPrison.searchByAddress(keyword);
                    break;
                case 4: // Tìm theo quản lý trưởng
                    result = managerPrison.searchByWarden(keyword);
                    break;
                default:
                    prisonView.showMessage("Vui lòng chọn tiêu chí tìm kiếm!");
                    return;
            }

            // Hiển thị kết quả
            if (!result.isEmpty()) {
                prisonView.showListPrisons(result);
                prisonView.showMessage("Tìm thấy " + result.size() + " kết quả!");
            } else {
                prisonView.showMessage("Không tìm thấy kết quả phù hợp!");
            }
        }
    }

    class CancelSearchPrisonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.showListPrisons(managerPrison.getListPrisons());
            prisonView.cancelSearch();
        }
    }

    class CancelDialogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonView.closeSearchDialog(); // Đóng dialog
            prisonView.cancelSearch(); // Hủy tìm kiếm (nếu có)
            prisonView.showListPrisons(managerPrison.getListPrisons()); // Hiển thị lại danh sách ban đầu
        }
    }

    class BackListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            prisonView.setVisible(false);
        }
    }
}