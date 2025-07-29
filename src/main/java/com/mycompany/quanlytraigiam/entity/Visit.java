/*
 * Lớp Visit đã được sửa lỗi và cải tiến.
 * 1. Thêm visitId làm khóa chính duy nhất.
 * 2. Thay đổi kiểu dữ liệu của ngày/giờ thành LocalDate/LocalTime.
 * 3. Xóa phương thức getInmateID() bị lỗi.
 */
package com.mycompany.quanlytraigiam.entity;

// Cần import thêm các lớp này
import com.mycompany.quanlytraigiam.utils.LocalDateAdapter;
import com.mycompany.quanlytraigiam.utils.LocalTimeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "visit") // Đổi lại thành "visit" cho nhất quán
@XmlAccessorType(XmlAccessType.FIELD)
public class Visit {

    @XmlElement
    private int visitId; // KHÓA CHÍNH: ID duy nhất cho mỗi lượt thăm

    @XmlElement
    private String inmateId;

    @XmlElement
    private String prisonerName;

    @XmlElement
    private String visitorName;

    @XmlElement
    private String relationship;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class) // Adapter cho LocalDate
    private LocalDate visitDate;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalTimeAdapter.class) // Adapter cho LocalTime
    private LocalTime visitTime;

    @XmlElement
    private String notes;

    // Constructor mặc định cần thiết cho JAXB
    public Visit() {
    }

    // Constructor đầy đủ để tạo đối tượng
    public Visit(int visitId, String inmateId, String prisonerName, String visitorName, String relationship, LocalDate visitDate, LocalTime visitTime, String notes) {
        this.visitId = visitId;
        this.inmateId = inmateId;
        this.prisonerName = prisonerName;
        this.visitorName = visitorName;
        this.relationship = relationship;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.notes = notes;
    }

    // --- Getters và Setters ---

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getInmateId() {
        return inmateId;
    }

    public void setInmateId(String inmateId) {
        this.inmateId = inmateId;
    }

    public String getPrisonerName() {
        return prisonerName;
    }

    public void setPrisonerName(String prisonerName) {
        this.prisonerName = prisonerName;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visitTime = visitTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Visit{" + "visitId=" + visitId + ", inmateId='" + inmateId + '\'' + ", prisonerName='" + prisonerName + '\'' + '}';
    }

    // PHƯƠNG THỨC getInmateID() GÂY LỖI ĐÃ ĐƯỢC XÓA HOÀN TOÀN
}