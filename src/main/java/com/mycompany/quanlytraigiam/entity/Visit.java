/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;

/**
 *
 * @author Admin
 */
public class Visit {    
    private String inmateId;
    private String prisonerName;
    private String visitorName;
    private String relationship;
    private String visitDate;
    private String visitTime;
    private String notes;
    public Visit (String inmateID, String visitorName, String relationship, String visitDate, String visitTime, String notes )
    {
        this.inmateId = inmateID;
        this.prisonerName  = prisonerName;
        this.visitorName = visitorName;
        this.relationship = relationship;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.notes = notes;
    }
    public String getInmateID(String inmateID)                     {return inmateID;}
    public String getPrisonerName(String prisonerName)             {return prisonerName;}
    public String getVisitorName(String visitorName)               {return visitorName;}
    public String getLastPrisonerName()
    {
        String fullPrisonerName = this.getPrisonerName();
        int lastSpaceIndex = fullPrisonerName.lastIndexOf(" ");
        String lastPrisonerName = fullPrisonerName.substring(lastSpaceIndex + 1);
        return lastPrisonerName;
    }
    public String getLastName()
    {
        String fullVisitorName = this.getVisitorName();
        int lastSpaceIndex = fullVisitorName.lastIndexOf(" ");
        String lastVisitorName = fullVisitorName.substring(lastSpaceIndex + 1);
        return lastVisitorName;
    }
    public String getFirstPrisonerName()
    {
        return this.getPrisonerName().replace(this.getLastName(), "");
    }
    public String getFirstVisitorName()
    {
        return this.getVisitorName().replace(this.getLastName(), "");
    }
    public String getRelationship(String relationship)             {return relationship;}
    public String getVisitDate(String visitDate)                   {return  visitDate;}
    public String getVisitTime(String visitTime)                   {return visitTime;}
    public String getNotes(String notes)                           {return notes;}
    
    
    public void setInmateID(String inmateID){ this.inmateId = inmateID;}
    public void setPrisonerName(String prisonerName){this.prisonerName = prisonerName;}
    public void setVisitorName(String visitorName) {this.visitorName = visitorName;}
    public void setRelationship(String relationship){this.relationship = relationship;}
    public void setVisitDate(String visitDate) {this.visitDate = visitDate;}
    public void setVisitTime(String visitTime){this.visitTime = visitTime;}
    public void setNotes(String notes){this.notes = notes;}

    private String getPrisonerName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String getVisitorName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
@Override 
    public String toString(){
        return prisonerName + " " + visitorName + " " + relationship + " " + visitDate + " " + visitTime;
    }
}


    

   

