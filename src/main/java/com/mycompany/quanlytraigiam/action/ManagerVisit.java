/*
 * Lớp ManagerVisit đã được sửa lỗi và cải tiến để tương thích với Visit.java mới.
 */
package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.Visit;
import com.mycompany.quanlytraigiam.entity.VisitsXML;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerVisit {

    private static final String VISIT_FILE_NAME = "Visits.xml";
    private List<Visit> visits;
    private int nextId; // Biến để quản lý ID tự tăng

    public ManagerVisit() {
        this.visits = readVisitList();
        initializeIdGenerator();
    }

    private void initializeIdGenerator() {
        if (visits.isEmpty()) {
            nextId = 1;
        } else {
            // Tìm ID lớn nhất trong danh sách và +1 để làm ID tiếp theo
            nextId = visits.stream().mapToInt(Visit::getVisitId).max().orElse(0) + 1;
        }
    }

    private List<Visit> readVisitList() {
        VisitsXML visitsXML = (VisitsXML) FileUtils.readXMLFile(VISIT_FILE_NAME, VisitsXML.class);
        return (visitsXML != null && visitsXML.getVisits() != null) ? visitsXML.getVisits() : new ArrayList<>();
    }

    private void writeVisitList() {
        VisitsXML visitsXML = new VisitsXML();
        visitsXML.setVisits(visits);
        FileUtils.writeXMLtoFile(VISIT_FILE_NAME, visitsXML);
    }

    public void addVisit(Visit v) {
        v.setVisitId(nextId++); // Gán ID mới và tăng biến đếm
        visits.add(v);
        writeVisitList();
    }

    public List<Visit> getVisits() {
        return new ArrayList<>(visits);
    }

    public void updateVisit(Visit updatedVisit) {
        for (int i = 0; i < visits.size(); i++) {
            // SỬA LỖI: Dùng visitId để cập nhật, đây là cách duy nhất đáng tin cậy
            if (visits.get(i).getVisitId() == updatedVisit.getVisitId()) {
                visits.set(i, updatedVisit);
                writeVisitList();
                return; // Thoát ngay khi cập nhật thành công
            }
        }
    }

    public void deleteVisit(int visitId) {
        // SỬA LỖI: Dùng visitId để xóa
        boolean removed = visits.removeIf(v -> v.getVisitId() == visitId);
        if (removed) {
            writeVisitList();
        }
    }

    public List<Visit> searchVisits(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getVisits();
        }
        String lowerCaseKeyword = keyword.toLowerCase();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return visits.stream().filter(visit -> {
            // SỬA LỖI: Chuyển đổi ngày/giờ thành chuỗi trước khi tìm kiếm
            String visitDateStr = visit.getVisitDate().format(dateFormatter);
            String visitTimeStr = (visit.getVisitTime() != null) ? visit.getVisitTime().format(timeFormatter) : "";
            
            return visit.getPrisonerName().toLowerCase().contains(lowerCaseKeyword) ||
                   visit.getVisitorName().toLowerCase().contains(lowerCaseKeyword) ||
                   visit.getInmateId().toLowerCase().contains(lowerCaseKeyword) ||
                   visit.getRelationship().toLowerCase().contains(lowerCaseKeyword) ||
                   visit.getNotes().toLowerCase().contains(lowerCaseKeyword) ||
                   visitDateStr.contains(lowerCaseKeyword) ||
                   visitTimeStr.contains(lowerCaseKeyword);
        }).collect(Collectors.toList());
    }

    public void sortVisitsByInmateId() {
        visits.sort(Comparator.comparing(Visit::getInmateId));
    }

    public void sortVisitsByVisitDate() {
        // SỬA LỖI: Sắp xếp trực tiếp trên đối tượng LocalDate, không cần parse
        visits.sort(Comparator.comparing(Visit::getVisitDate));
    }

    public void sortVisitsByVisitTime() {
        // SỬA LỖI: Sắp xếp trực tiếp trên đối tượng LocalTime
        // Dùng nullsLast để các lượt thăm không có giờ sẽ bị đẩy xuống cuối
        visits.sort(Comparator.comparing(Visit::getVisitTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }
}