package com.mycompany.quanlytraigiam.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SpecialPersons")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpecialPersonXML {
    
    private List<SpecialPerson> specialPerson;

    public List<SpecialPerson> getSpecialPerson() {
        return specialPerson;
    }

    public void setSpecialPerson(List<SpecialPerson> specialPerson) {
        this.specialPerson = specialPerson;
    }
}
