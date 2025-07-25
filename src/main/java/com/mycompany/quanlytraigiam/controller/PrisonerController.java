/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrisoner;
import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.view.LoginView;
import com.mycompany.quanlytraigiam.view.MainView;
import com.mycompany.quanlytraigiam.view.PrisonerView;
import java.util.List;
//////import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class PrisonerController 
{
    private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    private ManagerPrisoner managerPrisoner;
    private PrisonerView PrisonerView;
    private LoginView loginView;
    private MainView mainView;
    public PrisonerController(PrisonerView view) 
    {
        this.PrisonerView = view;
        managerPrisoner = new ManagerPrisoner();
        view.addAddPrisonerListener(new AddPrisonerListener());
        view.addEditPrisonerListener(new EditPrisonerListener());
        view.addClearListener(new ClearPrisonerListener());
        view.addDeletePrisonerListener(new DeletePrisonerListener());
        view.addListPrisonerSelectionListener(new ListPrisonerSelectionListener());
        view.addSortByNameListener(new SortPrisonerNameListener());
        //view.addSearchAddressListener(new SearchAddressPrisonerViewListener());
        //view.addSearchTypeListener(new SearchTypePrisonerViewListener());
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
    //Hàm hiển thị giao diện PrisonerView với dữ liệu 
    public void showPrisonerView() 
    {
        List<Prisoner> prisonerList = managerPrisoner.getListPrisoners();
        PrisonerView.setVisible(true);
        PrisonerView.showListPrisoners(prisonerList);
        PrisonerView.showCountListPrisoners(prisonerList);
    }
    //Xử lý sự kiện thêm phạm nhân
    class AddPrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Prisoner prisoner = PrisonerView.getPrisonerInfo();
            if (prisoner != null) 
            {
                managerPrisoner.add(prisoner);
                PrisonerView.showPrisoner(prisoner);
                PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showMessage("Thêm thành công!");
            }
        }
    }
    //Xử lý phần sửa thông tin phạm nhân
    class EditPrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Prisoner prisoner = PrisonerView.getPrisonerInfo();
            if (prisoner != null) 
            {
                try {
                    managerPrisoner.edit(prisoner);
                } catch (ParseException ex) {
                    Logger.getLogger(PrisonerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                PrisonerView.showPrisoner(prisoner);
                PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showMessage("Cập nhật thành công!");
            }
        }
    }
    //Xử lý phần xóa phạm nhân
    class DeletePrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Prisoner prisoner = PrisonerView.getPrisonerInfo();
            if (prisoner != null) 
            {
                managerPrisoner.delete(prisoner);
                PrisonerView.clearPrisonerInfo();
                PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showCountListPrisoners(managerPrisoner.getListPrisoners());
                PrisonerView.showMessage("Xóa thành công!");
            }
        }
    }
    
    //Xử lý chọn ảnh phạm nhân
    class ImagePrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.PrisonerImage();
        }
    }
    /**
     * Lớp ClearPrisonerListener 
     * chứa cài đặt cho sự kiện click button "Clear"
     * 
     */
    class ClearPrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.clearPrisonerInfo();
        }
    }

    
   //Sắp xếp phạm nhân theo tên
    class SortPrisonerNameListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerPrisoner.sortPrisonerByName();
            PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Sắp xếp phạm nhân theo năm sinh
    class SortPrisonerYearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerPrisoner.sortPrisonerByBirthDay();
            PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Sắp xếp phạm nhân theo ID
    class SortPrisonerIDListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerPrisoner.sortPrisonerByID();
            PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Sắp xếp theo ngày nhập trại
    class SortPrisonerOpeningDateListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerPrisoner.sortPrisonerByOpeningDate();
            PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Xử lý mở giao diện tình kiếm phạm nhân
    class SearchPrisonerViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.searchNamePrisonerInfo();
        }
    }
    //Mở phần giao diện thống kê
    class StatisticViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.displayStatisticView();
        }
    }
    //Xử lý logic tìm kiếm phạm nhân
    class SearchPrisonerListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            List<Prisoner> temp = new ArrayList<>();
            int check = PrisonerView.getChooseSelectSearch();
            String search = PrisonerView.validateSearch();
            if(check == 1){
                // Tìm kiếm theo tên
                temp = managerPrisoner.searchPrisonerName(search);
            }else if(check == 2){
                // Tìm kiếm theo địa chỉ
                temp = managerPrisoner.searchPrisonerAddress(search);
            }else if(check == 3){
                // Tìm kiếm theo năm sinh
                temp = managerPrisoner.searchPrisonerYear(search);
            }
            if(!temp.isEmpty())PrisonerView.showListPrisoners(temp);
            else PrisonerView.showMessage("Không tìm thấy kết quả!");
        }
    }
    //Xử lý phần hủy tìm kiếm
    class CancelDialogSearchPrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.cancelDialogSearchPrisonerInfo();
        }
    }
 
    class CancelSearchPrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.showListPrisoners(managerPrisoner.getListPrisoners());
            PrisonerView.cancelSearchPrisoner();
        }
    }
    //Quay về giao diện chính
    class UndoListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            PrisonerView.setVisible(false);
        }
    }
    /**
     * Lớp ListPrisonerSelectionListener 
     * chứa cài đặt cho sự kiện chọn Prisoner trong bảng Prisoner
     */
    class ListPrisonerSelectionListener implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            try {
                PrisonerView.fillPrisonerFromSelectedRow();
            } catch (ParseException ex) {
                Logger.getLogger(PrisonerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Xử lý thống kê phạm nhân theo loại án
    class StatisticPrisonerTypeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.displayStatisticView();
            PrisonerView.showStatisticTypePrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Xử lý thống kê phạm nhân theo tuổi
    class StatisticPrisonerAgeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.displayStatisticView();
            PrisonerView.showStatisticAgePrisoners(managerPrisoner.getListPrisoners());
        }
    }
    //Ẩn giao diện thống kê
    class StatisticClearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            PrisonerView.UnderViewPrisoner();
        }
    }
}
