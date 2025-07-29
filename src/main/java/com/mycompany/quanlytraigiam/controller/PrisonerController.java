/*
 * Lớp PrisonerController đã được nâng cấp để:
 * 1. Làm việc với MainView.
 * 2. Sửa lại logic "Quay lại" để hiển thị đúng cửa sổ chính đã có.
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrisoner;
import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.view.MainView; // SỬA ĐỔI: Import MainView
import com.mycompany.quanlytraigiam.view.PrisonerView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PrisonerController {
    private ManagerPrisoner managerPrisoner;
    private PrisonerView prisonerView; // Đổi tên biến cho nhất quán (chữ 'p' viết thường)
    private MainView mainView; // SỬA ĐỔI: Sử dụng MainView

    // SỬA ĐỔI: Hàm khởi tạo nhận vào cả PrisonerView và MainView
    public PrisonerController(PrisonerView view, MainView mainView) {
        this.prisonerView = view;
        this.mainView = mainView;
        this.managerPrisoner = new ManagerPrisoner();

        // Gắn tất cả các listener
        view.addAddPrisonerListener(new AddPrisonerListener());
        view.addEditPrisonerListener(new EditPrisonerListener());
        view.addClearListener(new ClearPrisonerListener());
        view.addDeletePrisonerListener(new DeletePrisonerListener());
        view.addListPrisonerSelectionListener(new ListPrisonerSelectionListener());
        view.addSortByNameListener(new SortPrisonerNameListener());
        view.addSearchListener(new SearchPrisonerViewListener());
        view.addSearchDialogListener(new SearchPrisonerListener());
        view.addSortByYearListener(new SortPrisonerYearListener());
        view.addSortByIDListener(new SortPrisonerIDListener());
        view.addSortByOpeningDateListener(new SortPrisonerOpeningDateListener());
        view.addCancelSearchPrisonerListener(new CancelSearchPrisonerListener());
        view.addImagePrisonerListener(new ImagePrisonerListener());
        view.addCancelDialogListener(new CancelDialogSearchPrisonerListener());
        view.addUndoListener(new UndoListener());
        view.addStatisticListener(new StatisticViewListener());
        view.addStatisticTypeListener(new StatisticPrisonerTypeListener());
        view.addStatisticAgeListener(new StatisticPrisonerAgeListener());
        view.addStatisticUnderListener(new StatisticClearListener());
    }

    public void showPrisonerView() {
        List<Prisoner> prisonerList = managerPrisoner.getListPrisoners();
        prisonerView.setVisible(true);
        prisonerView.showListPrisoners(prisonerList);
        prisonerView.showCountListPrisoners(prisonerList);
    }
    
    // SỬA ĐỔI: Logic quay lại màn hình chính
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.dispose(); // Đóng cửa sổ hiện tại
            mainView.setVisible(true); // Hiển thị lại cửa sổ chính đã có
        }
    }

    class AddPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prisoner prisoner = prisonerView.getPrisonerInfo();
            if (prisoner != null) {
                managerPrisoner.add(prisoner);
                prisonerView.showPrisoner(prisoner);
                prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showMessage("Thêm thành công!");
            }
        }
    }

    class EditPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prisoner prisoner = prisonerView.getPrisonerInfo();
            if (prisoner != null) {
                try {
                    managerPrisoner.edit(prisoner);
                } catch (ParseException ex) {
                    Logger.getLogger(PrisonerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                prisonerView.showPrisoner(prisoner);
                prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showMessage("Cập nhật thành công!");
            }
        }
    }

    class DeletePrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prisoner prisoner = prisonerView.getPrisonerInfo();
            if (prisoner != null) {
                managerPrisoner.delete(prisoner);
                prisonerView.clearPrisonerInfo();
                prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                prisonerView.showMessage("Xóa thành công!");
            }
        }
    }
    
    class ImagePrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.PrisonerImage();
        }
    }

    class ClearPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.clearPrisonerInfo();
        }
    }
   
    class SortPrisonerNameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerPrisoner.sortPrisonerByName();
            prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class SortPrisonerYearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerPrisoner.sortPrisonerByBirthDay();
            prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class SortPrisonerIDListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerPrisoner.sortPrisonerByID();
            prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class SortPrisonerOpeningDateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            managerPrisoner.sortPrisonerByOpeningDate();
            prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class SearchPrisonerViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.searchNamePrisonerInfo();
        }
    }

    class StatisticViewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.displayStatisticView();
        }
    }

    class SearchPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Prisoner> temp;
            int check = prisonerView.getChooseSelectSearch();
            String search = prisonerView.validateSearch();
            switch (check) {
                case 1 -> temp = managerPrisoner.searchPrisonerName(search);
                case 2 -> temp = managerPrisoner.searchPrisonerAddress(search);
                case 3 -> temp = managerPrisoner.searchPrisonerYear(search);
                default -> temp = new ArrayList<>();
            }
            if (!temp.isEmpty()) {
                prisonerView.showListPrisoners(temp);
            } else {
                prisonerView.showMessage("Không tìm thấy kết quả!");
            }
        }
    }

    class CancelDialogSearchPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.cancelDialogSearchPrisonerInfo();
        }
    }
 
    class CancelSearchPrisonerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
            prisonerView.cancelSearchPrisoner();
        }
    }

    class ListPrisonerSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                try {
                    prisonerView.fillPrisonerFromSelectedRow();
                } catch (ParseException ex) {
                    Logger.getLogger(PrisonerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class StatisticPrisonerTypeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.displayStatisticView();
            prisonerView.showStatisticTypePrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class StatisticPrisonerAgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.displayStatisticView();
            prisonerView.showStatisticAgePrisoners(managerPrisoner.getListPrisoners());
        }
    }

    class StatisticClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            prisonerView.UnderViewPrisoner();
        }
    }
}