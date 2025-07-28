/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import java.util.ArrayList;
/**
 *
 * @author PC
 */
// sửa lại cấu trúc XML
@XmlRootElement(name = "Prisons")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrisonXML {
    @XmlElement(name = "Prison")
    private List<Prison> prisons;

    public List<Prison> getPrisons() {
        return prisons;
    }

    public void setPrisons(List<Prison> prisons) {
        this.prisons = prisons;
    }
    
   //Đọc dữ liệu từ Prisons.xml chuyển sang phần chọn tên trại giam của ql phạm nhân
    public static List<String> getAllPrisonNames() {
    List<String> names = new ArrayList<>();
    PrisonXML prisonData = (PrisonXML) FileUtils.readXMLFile("Prisons.xml", PrisonXML.class);
    if (prisonData != null && prisonData.getPrisons() != null) {
        for (Prison prison : prisonData.getPrisons()) {
            names.add(prison.getTenTraiGiam());
        }
    }
    return names;
}

}
