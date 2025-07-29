package com.mycompany.quanlytraigiam.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Visits") 
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitsXML {

//    @XmlElement(name = "visit") // Tên của mỗi phần tử trong danh sách

    private List<Visit> visits;



    public VisitsXML() {

    }



    public VisitsXML(List<Visit> visits) {

        this.visits = visits;

    }



    public List<Visit> getVisits() {

        return visits;

    }



    public void setVisits(List<Visit> visits) {

        this.visits = visits;

    }

}