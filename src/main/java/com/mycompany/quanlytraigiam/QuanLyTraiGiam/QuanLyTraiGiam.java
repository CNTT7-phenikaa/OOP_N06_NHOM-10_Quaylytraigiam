/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quanlytraigiam.QuanLyTraiGiam;

import com.mycompany.quanlytraigiam.controller.LoginController;
import com.mycompany.quanlytraigiam.view.LoginView;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author PC
 */
public class QuanLyTraiGiam 
{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginView view = new LoginView();
                LoginController controller = new LoginController(view);
                // hiển thị màn hình login
                controller.showLoginView();
            }
        });
    }
}
