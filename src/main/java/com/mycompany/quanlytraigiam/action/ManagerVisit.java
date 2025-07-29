/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.Visit;
import com.mycompany.quanlytraigiam.entity.VisitsXML; // Import VisitsXML
import com.mycompany.quanlytraigiam.utils.FileUtils; // Import FileUtils
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */



public class ManagerVisit {

    private static final String VISIT_FILE_NAME = "Visits.xml"; // Tên file XML để lưu trữ

    private List<Visit> visits;



    public ManagerVisit() {

        this.visits = readVisitList(); // Load visits from file on initialization

        if (this.visits == null) {

            this.visits = new ArrayList<>();

        }

    }



    // Phương thức đọc danh sách thăm nuôi từ file XML

    private List<Visit> readVisitList() {

        VisitsXML visitsXML = (VisitsXML) FileUtils.readXMLFile(VISIT_FILE_NAME, VisitsXML.class);

        if (visitsXML != null && visitsXML.getVisits() != null) {

            return visitsXML.getVisits();

        }

        return new ArrayList<>();

    }



    // Phương thức ghi danh sách thăm nuôi vào file XML

    private void writeVisitList() {

        VisitsXML visitsXML = new VisitsXML();

        visitsXML.setVisits(visits);

        FileUtils.writeXMLtoFile(VISIT_FILE_NAME, visitsXML);

    }



    public void addVisit(Visit V) {

        visits.add(V);

        writeVisitList(); // Save changes to file

    }



    public List<Visit> getVisits() {

        return new ArrayList<>(visits); // Trả về bản sao để tránh sửa đổi trực tiếp

    }



    public void updateVisit(Visit updatedVisit) {

        boolean found = false;

        for (int i = 0; i < visits.size(); i++) {

            // Sử dụng một cách duy nhất để xác định một Visit cụ thể,

            // ví dụ: InmateId, VisitDate, VisitTime kết hợp

            if (visits.get(i).getInmateId().equals(updatedVisit.getInmateId()) &&

                visits.get(i).getVisitDate().equals(updatedVisit.getVisitDate()) &&

                visits.get(i).getVisitTime().equals(updatedVisit.getVisitTime())) {

                visits.set(i, updatedVisit);

                found = true;

                break;

            }

        }

        if (found) {

            writeVisitList(); // Save changes to file

        }

    }



    public void deleteVisit(String inmateId, String visitDate, String visitTime) {

        boolean removed = visits.removeIf(v -> v.getInmateId().equals(inmateId) &&

                                             v.getVisitDate().equals(visitDate) &&

                                             v.getVisitTime().equals(visitTime));

        if (removed) {

            writeVisitList(); // Save changes to file

        }

    }

    

    // Tìm kiếm theo từ khóa chung

    public List<Visit> searchVisits(String keyword) {

        List<Visit> result = new ArrayList<>();

        String lowerCaseKeyword = keyword.toLowerCase();

        for (Visit visit : visits) {

            if (visit.getPrisonerName().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getVisitorName().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getInmateId().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getRelationship().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getVisitDate().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getVisitTime().toLowerCase().contains(lowerCaseKeyword) ||

                visit.getNotes().toLowerCase().contains(lowerCaseKeyword)) {

                result.add(visit);

            }

        }

        return result;

    }

    

    // Sắp xếp theo mã phạm nhân

    public void sortVisitsByInmateId() {

        Collections.sort(visits, Comparator.comparing(Visit::getInmateId));

    }



    // Sắp xếp theo ngày thăm (cần SimpleDateFormat để so sánh ngày tháng)

    public void sortVisitsByVisitDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Collections.sort(visits, (v1, v2) -> {

            try {

                Date date1 = sdf.parse(v1.getVisitDate());

                Date date2 = sdf.parse(v2.getVisitDate());

                return date1.compareTo(date2);

            } catch (ParseException e) {

                e.printStackTrace();

                return 0; // Xử lý lỗi hoặc log lỗi nếu ngày không hợp lệ

            }

        });

    }



    // Sắp xếp theo thời gian thăm

    public void sortVisitsByVisitTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Collections.sort(visits, (v1, v2) -> {

            try {

                Date time1 = sdf.parse(v1.getVisitTime());

                Date time2 = sdf.parse(v2.getVisitTime());

                return time1.compareTo(time2);

            } catch (ParseException e) {

                e.printStackTrace();

                return 0;

            }

        });

    }

}