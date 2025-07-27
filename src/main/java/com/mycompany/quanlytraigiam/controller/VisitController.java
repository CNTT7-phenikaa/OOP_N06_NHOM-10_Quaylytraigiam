package com.mycompany.quanlytraigiam.controller;
import com.mycompany.quanlytraigiam.action.ManagerVisit;
import com.mycompany.quanlytraigiam.entity.Visit;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class VisitController {
    public void addVisit(Visit visit){
        ManagerVisit.addVisit(visit);
    }
    public List<Visit> getAllVisits()
    {
        return ManagerVisit.getAllvisits();
    }
}
