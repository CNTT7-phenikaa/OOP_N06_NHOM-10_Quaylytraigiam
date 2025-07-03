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

@XmlRootElement(name = "Prisoner")
@XmlAccessorType(XmlAccessType.FIELD)

public class Prisoner extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String crime;     //tội danh
    private Date imprisonmentDate; //ngày nhập trại
    private int sentenceYears; //năm lĩnh án
    private String prisonName; // tên trại giam

    public Prisoner() {
        super();
    }

    public Prisoner(int id, String name, Date birthday, String address,
                    String crime, Date imprisonmentDate, int sentenceYears, String prisonName) throws ParseException {
        super(id, name, birthday, address);
        this.crime = crime;
        this.imprisonmentDate = imprisonmentDate;
        this.sentenceYears = sentenceYears;
        this.prisonName = prisonName;
    }

    // Getter & Setter
    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public Date getImprisonmentDate() {
        return imprisonmentDate;
    }

    public void setImprisonmentDate(Date imprisonmentDate) {
        this.imprisonmentDate = imprisonmentDate;
    }

    public int getSentenceYears() {
        return sentenceYears;
    }

    public void setSentenceYears(int sentenceYears) {
        this.sentenceYears = sentenceYears;
    }

    public String getPrisonName() {
        return prisonName;
    }

    public void setPrisonName(String prisonName) {
        this.prisonName = prisonName;
    }
}
