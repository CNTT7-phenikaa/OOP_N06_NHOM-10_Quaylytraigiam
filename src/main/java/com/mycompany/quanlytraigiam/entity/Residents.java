/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */

@XmlRootElement(name = "Resident")
@XmlAccessorType(XmlAccessType.FIELD)
public class Residents extends Person
{
    private String IDFamily;
    private String sex;
    private String role;
    private String birthPlace;
    private Date birthDay;
    private String typeCMT;
    private String CMT;
    private String phoneNumber;
    
    public Residents()
    {
        this.CMT="";
        this.typeCMT="<none>";
        this.phoneNumber="";
    }
    
    public Residents(int id, String name, Date birthday, String address, String IDFamily, String sex, String role, String birthPlace, String typeCMT, String CMT, String phoneNumber)
    {
        super();
        SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        this.id = id;
        this.name = name;
        this.birthDay = birthday;
        this.address = address;
        this.IDFamily = IDFamily;
        this.sex = sex;
        this.role = role;
        this.birthPlace = birthPlace;
        this.typeCMT = typeCMT;
        
    }
    
    public String getIDFamily()
    {
        return this.IDFamily;
    }
    
    public void setIDFamily(String number)
    {
        this.IDFamily = number;
    }
    
    public String getSex()
    {
        return this.sex;
    }
    
    public void setSex(String _sex)
    {
        this.sex=_sex;
    }
    
    public String getRole()
    {
        return this.role;
    }
    
    public void setRole(String _role)
    {
        this.role=_role;
    }
    
    public String getBirthPlace()
    {
        return this.birthPlace;
    }
    
    public void setBirthPlace(String _birthPlace)
    {
        this.birthPlace=_birthPlace;
    }
    
    public String getTypeCMT()
    {
        return this.typeCMT;
    }
    
    public void setTypeCMT(String typeCMT)
    {
        this.typeCMT = typeCMT;
    }
    
    public String getCMT()
    {
        return this.CMT;
    }
    
    public void setCMT(String cmt)
    {
        this.CMT=cmt;
    }
    
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
