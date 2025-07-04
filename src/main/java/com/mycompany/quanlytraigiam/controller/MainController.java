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
import com.mycompany.quanlytraigiam.view.PrisonView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author PC
 */
public class MainController 
{
    private LoginView loginView;
    private PrisonerView managerView;
    private PrisonView prisonView;
    private MainView mainView;
    
    public MainController(MainView view)
    {
        this.mainView = view;
        view.addChoosePrisonerListener(new ChoosePrisonerListener());
        view.addChoosePrisonListener(new ChoosePrisonListener());
    }
    public void showMainView() 
    {
        mainView.setVisible(true);
    }
    class ChoosePrisonerListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            managerView = new PrisonerView();
            PrisonerController managerController = new PrisonerController(managerView);
            managerController.showManagerView();
            mainView.setVisible(false);
        }
    }
    
    class ChoosePrisonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            prisonView = new PrisonView();
            PrisonController prisonController = new PrisonController(prisonView);
            prisonController.showManagerView();
            mainView.setVisible(false);
        }
    }
}
