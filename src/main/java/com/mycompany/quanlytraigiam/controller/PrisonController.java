/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerPrison;
import com.mycompany.quanlytraigiam.action.ManagerPrison;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.view.LoginView;
import com.mycompany.quanlytraigiam.view.MainView;
import com.mycompany.quanlytraigiam.view.PrisonerView;
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

/**
 *
 * @author PC
 */
public class PrisonController 
{
    private LoginView loginView;
    private PrisonerView managerView;
    private PrisonView residentView;
    private MainView mainView;
    private ManagerPrison managerResidents;
    
    public PrisonController(PrisonView view)
    {
        this.residentView=view;
        this.managerResidents = new ManagerPrison();
        view.addUndoListener(new UndoListener());
        view.addAddResidentListener(new AddResidentListener());
        view.addListResidentSelectionListener(new ListResidentsSelectionListener());
        view.addEditResidentListener(new EditResidentListener());
        view.addClearListener(new ClearResidentListener());
        view.addDeletePrisonerListener(new DeletePrisonerListener());
        view.addSortPrisonerListener(new SortResidentsListener());
        view.addSearchListener(new SearchResidentViewListener());
        //view.addSearchListener(new SearchResidentViewListener());
        view.addSearchDialogListener(new SearchResidentListener());
        view.addCancelSearchResidentListener(new CancelSearchResidentListener());
        view.addCancelDialogListener(new CancelDialogSearchResidentListener());
    }
    
    public void showManagerView() 
    {
        List<Prison> residentsList = managerResidents.getListResidents();
        residentView.setVisible(true);
        residentView.showListResidents(residentsList);
        residentView.showCountListResidents(residentsList);
        residentView.showStatisticTypeCMT(residentsList);
        residentView.showStatisticIDFamily(residentsList);
    }
    
    class UndoListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            residentView.setVisible(false);
        }
    }
    
    class AddResidentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Prison residents = residentView.getResidentInfo();
            if (residents != null) {
                try {
                    if (!managerResidents.isCMTUnique(residents)) {
                        // Lấy thông báo từ ngoại lệ và hiển thị
                        throw new IllegalArgumentException("Lỗi: Số chứng minh thư đã tồn tại!");
                    }
                    
                    if (!managerResidents.isHouseholdUnique(residents)) {
                        // Lấy thông báo từ ngoại lệ và hiển thị
                        throw new IllegalArgumentException("Lỗi: Đã có chủ hộ đối với số hộ khẩu này");
                    }
                    
                    managerResidents.add(residents);
                    residentView.showResidents(residents);
                    residentView.showListResidents(managerResidents.getListResidents());
                    residentView.showCountListResidents(managerResidents.getListResidents());
                    residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                    residentView.showStatisticIDFamily(managerResidents.getListResidents());
                    residentView.showMessage("Thêm thành công!");
                } catch (IllegalArgumentException ex) {
                    residentView.showMessage(ex.getMessage());
                }
            }
        }
    }
    
    class EditResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Prison resident = residentView.getResidentInfo();
            if (resident != null) 
            {
                try {
                    managerResidents.edit(resident);
                } catch (ParseException ex) {
                    Logger.getLogger(PrisonerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                residentView.showResidents(resident);
                residentView.showListResidents(managerResidents.getListResidents());
                residentView.showCountListResidents(managerResidents.getListResidents());
                residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                residentView.showStatisticIDFamily(managerResidents.getListResidents());
                residentView.showMessage("Cập nhật thành công!");
            }
        }
    }
    
    class DeletePrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            Prison resident = residentView.getResidentInfo();
            if (resident != null) 
            {
                managerResidents.delete(resident);
                residentView.clearResidentInfo();
                residentView.showListResidents(managerResidents.getListResidents());
                residentView.showCountListResidents(managerResidents.getListResidents());
                residentView.showStatisticTypeCMT(managerResidents.getListResidents());
                residentView.showStatisticIDFamily(managerResidents.getListResidents());
                residentView.showMessage("Xóa thành công!");
            }
        }
    }
    
    class ListResidentsSelectionListener implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            List<Prison> residentsList = managerResidents.getListResidents();
            try {
                residentView.fillResidentFromSelectedRow(residentsList);
                //residentView.fillResidentFromSelectedRow();
                
            } catch (ParseException ex) {
                Logger.getLogger(PrisonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class ClearResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.clearResidentInfo();
        }
    }
    
    class SortResidentsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boolean kt = false;
            List<Prison> temp = new ArrayList<>();
            int check = residentView.getChooseSelectSort();
            if(check == 1){
                managerResidents.sortResidentsByID();
                residentView.showListResidents(managerResidents.getListResidents());
            }else if(check == 2){
                managerResidents.sortResidentsByName();
                residentView.showListResidents(managerResidents.getListResidents());
            }else if(check == 3){
                managerResidents.sortResidentsByIDFamily();
                residentView.showListResidents(managerResidents.getListResidents());
            } else
                residentView.showMessage("Bạn chưa chọn tiêu chí sắp xếp");
        }
    }
    
    class SearchResidentViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.searchResidentInfo();
        }
    }
    
    class CancelDialogSearchResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.cancelDialogSearchResidentInfo();
        }
    }
    
    class CancelSearchResidentListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            residentView.showListResidents(managerResidents.getListResidents());
            residentView.cancelSearchResident();
        }
    }
    
    class SearchResidentListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boolean kt = false;
            List<Prison> temp = new ArrayList<>();
            int check = residentView.getChooseSelectSearch();
            String search = residentView.validateSearch();
            if(check == 1){
                // Tìm kiếm theo tên
                temp = managerResidents.searchResidentIDFamily(search);
            }else if(check == 2){
                // Tìm kiếm theo địa chỉ
                temp = managerResidents.searchResidentName(search);
            }else if(check == 3){
                // Tìm kiếm theo loại đối tượng
                temp = managerResidents.searchResidentYear(search);
            }else if(check == 4){
                // Tìm kiếm theo loại đối tượng
                temp = managerResidents.searchResidentAddress(search);
            }
            if(!temp.isEmpty())residentView.showListResidents(temp);
            else residentView.showMessage("Không tìm thấy kết quả!");
        }
    }
}
