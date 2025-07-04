package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.entity.PrisonXML;
import com.mycompany.quanlytraigiam.utils.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManagerPrison {
    private static final String PRISON_FILE_NAME = "Prisons.xml";
    private List<Prison> prisonList;

    public ManagerPrison() {
        this.prisonList = readPrisonList();
        if (this.prisonList == null) {
            this.prisonList = new ArrayList<>();
        }
    }

    public List<Prison> readPrisonList() {
        PrisonXML prisonXML = (PrisonXML) FileUtils.readXMLFile(PRISON_FILE_NAME, PrisonXML.class);
        if (prisonXML != null) {
            return prisonXML.getPrisons();
        }
        return new ArrayList<>();
    }

    public void writePrisonList() {
        PrisonXML prisonXML = new PrisonXML();
        prisonXML.setPrisons(prisonList);
        FileUtils.writeXMLtoFile(PRISON_FILE_NAME, prisonXML);
    }

    public void addPrison(Prison prison) {
        prisonList.add(prison);
        writePrisonList();
    }

    public void updatePrison(Prison updatedPrison) {
        for (int i = 0; i < prisonList.size(); i++) {
            if (prisonList.get(i).getId().equals(updatedPrison.getId())) {
                prisonList.set(i, updatedPrison);
                writePrisonList();
                break;
            }
        }
    }

    public boolean deletePrison(String id) {
        return prisonList.removeIf(prison -> prison.getId().equals(id));
    }

    public List<Prison> searchByName(String keyword) {
        List<Prison> result = new ArrayList<>();
        for (Prison prison : prisonList) {
            if (prison.getTenTraiGiam().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(prison);
            }
        }
        return result;
    }

    public List<Prison> searchByAddress(String keyword) {
        List<Prison> result = new ArrayList<>();
        for (Prison prison : prisonList) {
            if (prison.getDiaChi().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(prison);
            }
        }
        return result;
    }

    public void sortByCapacity() {
        Collections.sort(prisonList, Comparator.comparingInt(p -> Integer.parseInt(p.getSucChuaToiDa())));
    }

    public List<Prison> getListPrisons() {
        return prisonList;
    }
} 