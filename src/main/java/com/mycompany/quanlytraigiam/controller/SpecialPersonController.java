/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.controller;

import com.mycompany.quanlytraigiam.action.ManagerSpecialPerson;
import com.mycompany.quanlytraigiam.entity.SpecialPerson;
import com.mycompany.quanlytraigiam.view.LoginView;
import com.mycompany.quanlytraigiam.view.MainView;
import com.mycompany.quanlytraigiam.view.ManagerView;
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
public class SpecialPersonController 
{
    private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    private ManagerSpecialPerson managerSpecialPerson;
    private ManagerView specialPersonView;
    private LoginView loginView;
    private MainView mainView;
    public SpecialPersonController(ManagerView view) 
    {
        this.specialPersonView = view;
        managerSpecialPerson = new ManagerSpecialPerson();
        view.addAddSpecialPersonListener(new AddSpecialPersonListener());
        view.addEditSpecialPersonListener(new EditSpecialPersonListener());
        view.addClearListener(new ClearSpecialPersonListener());
        view.addDeleteSpecialPersonListener(new DeleteSpecialPersonListener());
        view.addListSpecialPersonSelectionListener(new ListSpecialPersonSelectionListener());
        view.addSortByNameListener(new SortSpecialPersonNameListener());
        //view.addSearchAddressListener(new SearchAddressSpecialPersonViewListener());
        //view.addSearchTypeListener(new SearchTypeSpecialPersonViewListener());
        view.addSearchListener(new SearchSpecialPersonViewListener());
        view.addSearchDialogListener(new SearchSpecialPersonListener());
        //view.addSearchDialogListener(new SearchSpecialPersonListener());
        view.addSortByYearListener(new SortSpecialPersonYearListener());
        view.addSortByIDListener(new SortSpecialPersonIDListener());
        view.addSortByOpeningDateListener(new SortSpecialPersonOpeningDateListener());
        view.addCancelSearchSpecialPersonListener(new CancelSearchSpecialPersonListener());
        view.addImageSpecialPersonListener(new ImageSpecialPersonListener());
        view.addCancelDialogListener(new CancelDialogSearchSpecialPersonListener());
        view.addUndoListener(new UndoListener());
        view.addStatisticListener(new StatisticViewListener());
        view.addStatisticTypeListener(new StatisticSpecialPersonTypeListener());
        view.addStatisticAgeListener(new StatisticSpecialPersonAgeListener());
        view.addStatisticUnderListener(new StatisticClearListener());
    }

    public void showManagerView() 
    {
        List<SpecialPerson> specialPersonList = managerSpecialPerson.getListSpecialPersons();
        specialPersonView.setVisible(true);
        specialPersonView.showListSpecialPersons(specialPersonList);
        specialPersonView.showCountListSpecialPersons(specialPersonList);
    }

    class AddSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            SpecialPerson specialPerson = specialPersonView.getSpecialPersonInfo();
            if (specialPerson != null) 
            {
                managerSpecialPerson.add(specialPerson);
                specialPersonView.showSpecialPerson(specialPerson);
                specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showCountListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showMessage("Thêm thành công!");
            }
        }
    }
    
    class EditSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            SpecialPerson specialPerson = specialPersonView.getSpecialPersonInfo();
            if (specialPerson != null) 
            {
                try {
                    managerSpecialPerson.edit(specialPerson);
                } catch (ParseException ex) {
                    Logger.getLogger(SpecialPersonController.class.getName()).log(Level.SEVERE, null, ex);
                }
                specialPersonView.showSpecialPerson(specialPerson);
                specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showCountListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showMessage("Cập nhật thành công!");
            }
        }
    }
    
    class DeleteSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            SpecialPerson specialPerson = specialPersonView.getSpecialPersonInfo();
            if (specialPerson != null) 
            {
                managerSpecialPerson.delete(specialPerson);
                specialPersonView.clearSpecialPersonInfo();
                specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showCountListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
                specialPersonView.showMessage("Xóa thành công!");
            }
        }
    }
    
    
    class ImageSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.SpecialPersonImage();
        }
    }
    /**
     * Lớp ClearSpecialPersonListener 
     * chứa cài đặt cho sự kiện click button "Clear"
     * 
     * @author viettuts.vn
     */
    class ClearSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.clearSpecialPersonInfo();
        }
    }

    /**
     * Lớp SortSpecialPersonGPAListener 
     * chứa cài đặt cho sự kiện click button "Sort By GPA"
     * 
     * @author viettuts.vn
     *
    /**
     * Lớp SortSpecialPersonGPAListener 
     * chứa cài đặt cho sự kiện click button "Sort By Name"
     * 
     * @author viettuts.vn
     */
    class SortSpecialPersonNameListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerSpecialPerson.sortSpecialPersonByName();
            specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    
    class SortSpecialPersonYearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerSpecialPerson.sortSpecialPersonByBirthDay();
            specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    
    class SortSpecialPersonIDListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerSpecialPerson.sortSpecialPersonByID();
            specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    
    class SortSpecialPersonOpeningDateListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerSpecialPerson.sortSpecialPersonByOpeningDate();
            specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    
    class SearchSpecialPersonViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.searchNameSpecialPersonInfo();
        }
    }
    
    class StatisticViewListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
        }
    }
    
    class SearchSpecialPersonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boolean kt = false;
            List<SpecialPerson> temp = new ArrayList<>();
            int check = specialPersonView.getChooseSelectSearch();
            String search = specialPersonView.validateSearch();
            if(check == 1){
                // Tìm kiếm theo tên
                temp = managerSpecialPerson.searchSpecialPersonName(search);
            }else if(check == 2){
                // Tìm kiếm theo địa chỉ
                temp = managerSpecialPerson.searchSpecialPersonAddress(search);
            }else if(check == 3){
                // Tìm kiếm theo loại đối tượng
                temp = managerSpecialPerson.searchSpecialPersonYear(search);
            }
            if(!temp.isEmpty())specialPersonView.showListSpecialPersons(temp);
            else specialPersonView.showMessage("Không tìm thấy kết quả!");
        }
    }
    
    class CancelDialogSearchSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.cancelDialogSearchSpecialPersonInfo();
        }
    }
    
    class CancelSearchSpecialPersonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.showListSpecialPersons(managerSpecialPerson.getListSpecialPersons());
            specialPersonView.cancelSearchSpecialPerson();
        }
    }
    
    class UndoListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            mainView = new MainView();
            MainController mainController = new MainController(mainView);
            mainController.showMainView();
            specialPersonView.setVisible(false);
        }
    }
    /**
     * Lớp ListSpecialPersonSelectionListener 
     * chứa cài đặt cho sự kiện chọn specialPerson trong bảng specialPerson
     */
    class ListSpecialPersonSelectionListener implements ListSelectionListener 
    {
        public void valueChanged(ListSelectionEvent e) 
        {
            try {
                specialPersonView.fillSpecialPersonFromSelectedRow();
            } catch (ParseException ex) {
                Logger.getLogger(SpecialPersonController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class StatisticSpecialPersonTypeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
            //managerSpecialPerson.sortSpecialPersonByID();
            specialPersonView.showStatisticTypeSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    class StatisticSpecialPersonAgeListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.displayStatisticView();
            //managerSpecialPerson.sortSpecialPersonByID();
            specialPersonView.showStatisticAgeSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
    class StatisticClearListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            specialPersonView.UnderViewSpecialPerson();
            //managerSpecialPerson.sortSpecialPersonByID();
            //specialPersonView.showStatisticAgeSpecialPersons(managerSpecialPerson.getListSpecialPersons());
        }
    }
}
