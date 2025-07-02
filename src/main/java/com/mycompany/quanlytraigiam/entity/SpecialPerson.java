/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */

@XmlRootElement(name = "SpecialPerson")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpecialPerson extends Person implements Serializable
{
    private static final long serialVersionUID = 1L;
   // private int id;
  //  private String name;
  //  private int year;
  //  private String address;
    private Date OpeningDate;
    private String type;
    private byte[] picture;
    //private SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
    public SpecialPerson() 
    {
        
    }
    public SpecialPerson(int id, String name, Date birthday, String address, String OpeningDate, String type, byte[] image) throws ParseException 
    {
        super();
        SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.address = address;
        this.OpeningDate = fDate.parse(OpeningDate);
        this.type=type;
        this.picture=image;
    }
    
    public Date getOpeningDate() 
    {
        return this.OpeningDate;
    }

    public void setOpeningDate(Date OpeningDate) 
    {
        //SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        this.OpeningDate = OpeningDate;
    }
    
    public String getType() 
    {
        return this.type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }
    public byte[] getImage()
    {
        return picture;
    }
    
    public void setImage(byte[] image)
    {
        this.picture=image;
    }
}
