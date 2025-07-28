package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.entity.PrisonXML;
import com.mycompany.quanlytraigiam.utils.FileUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManagerPrison {
    private static final String PRISON_FILE_NAME = "./Prisons.xml";
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
        int maxId = 0;
        for (Prison p : prisonList) {
            try {
                int currentId = Integer.parseInt(p.getId());
                if (currentId > maxId) {
                    maxId = currentId;
                }
            } catch (NumberFormatException e) {
                // Bỏ qua nếu id không phải số
            }
        }
        prison.setId(String.valueOf(maxId + 1));
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
        boolean result = prisonList.removeIf(prison -> prison.getId().equals(id));
        if (result) {
            writePrisonList();
        }
        return result;
    }

    public List<Prison> searchById(String id) {
        List<Prison> result = new ArrayList<>();
        for (Prison prison : getListPrisons()) {
            if (prison.getMaTraiGiam().toLowerCase().contains(id.toLowerCase())) {
                result.add(prison);
            }
        }
        return result;
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

    public List<Prison> searchByWarden(String keyword) {
        List<Prison> result = new ArrayList<>();
        for (Prison prison : prisonList) {
            if (prison.getQuanLiTruong().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(prison);
            }
        }
        return result;
    }

    public void sortByName() {
        Collections.sort(prisonList,
                Comparator.comparing(Prison::getTenTraiGiam, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        writePrisonList();
    }

    public void sortByCapacity() {
        Collections.sort(prisonList, (p1, p2) -> {
            int cap1 = p1.getSucChuaToiDa() != null ? p1.getSucChuaToiDa() : 0;
            int cap2 = p2.getSucChuaToiDa() != null ? p2.getSucChuaToiDa() : 0;
            return Integer.compare(cap2, cap1); // Đổi cap1, cap2 để sắp xếp giảm dần
        });
        writePrisonList();
    }

    public void sortByWarden() {
        Collections.sort(prisonList,
                Comparator.comparing(Prison::getQuanLiTruong, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        writePrisonList();
    }

    private void printPrisonList() {
        for (Prison prison : prisonList) {
            System.out.println("ID: " + prison.getId() +
                    ", Tên: " + prison.getTenTraiGiam() +
                    ", Sức chứa: " + prison.getSucChuaToiDa() +
                    ", Quản lý: " + prison.getQuanLiTruong());
        }
    }

    public List<Prison> getListPrisons() {
        return prisonList;
    }

    public Prison getPrisonById(String id) {
        for (Prison prison : prisonList) {
            if (prison.getId().equals(id)) {
                return prison;
            }
        }
        return null;
    }
}