/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement; 
/**
 *
 * @author Admin
 */
@XmlRootElement(name = "Visit") // Tên của thẻ XML đại diện cho một Visit

@XmlAccessorType(XmlAccessType.FIELD) // Truy cập trực tiếp các trường (field)

public class Visit {    

    private String inmateId;

    private String prisonerName;

    private String visitorName;

    private String relationship;

    private String visitDate; // Format: dd/MM/yyyy

    private String visitTime; // Format: HH:mm

    private String notes;



    public Visit () {

        // Constructor mặc định cần thiết cho JAXB

    }



    public Visit (String inmateID, String prisonerName, String visitorName, String relationship, String visitDate, String visitTime, String notes )

    {

        this.inmateId = inmateID;

        this.prisonerName = prisonerName;

        this.visitorName = visitorName;

        this.relationship = relationship;

        this.visitDate = visitDate;

        this.visitTime = visitTime;

        this.notes = notes;

    }



    // Standard Getters (đảm bảo không có tham số)

    public String getInmateId() { return inmateId; }

    public String getPrisonerName() { return prisonerName; }

    public String getVisitorName() { return visitorName; }

    public String getRelationship() { return relationship; }

    public String getVisitDate() { return visitDate; }

    public String getVisitTime() { return visitTime; }

    public String getNotes() { return notes; }

    

    // Standard Setters

    public void setInmateId(String inmateId){ this.inmateId = inmateId;}

    public void setPrisonerName(String prisonerName){this.prisonerName = prisonerName;}

    public void setVisitorName(String visitorName) {this.visitorName = visitorName;}

    public void setRelationship(String relationship){this.relationship = relationship;}

    public void setVisitDate(String visitDate) {this.visitDate = visitDate;}

    public void setVisitTime(String visitTime){this.visitTime = visitTime;}

    public void setNotes(String notes){this.notes = notes;}



    @Override 

    public String toString(){

        return "Visit{" +

               "inmateId='" + inmateId + '\'' +

               ", prisonerName='" + prisonerName + '\'' +

               ", visitorName='" + visitorName + '\'' +

               ", relationship='" + relationship + '\'' +

               ", visitDate='" + visitDate + '\'' +

               ", visitTime='" + visitTime + '\'' +

               ", notes='" + notes + '\'' +

               '}';

    }

    public Object getInmateID() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

   

