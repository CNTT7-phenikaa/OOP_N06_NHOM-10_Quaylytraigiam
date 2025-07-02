/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.ResidentXML;
import com.mycompany.quanlytraigiam.entity.Residents;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import com.mycompany.quanlytraigiam.view.ResidentView;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ManagerResidents 
{
    private static final String RESIDENT_FILE_NAME = "Residents.xml";
    private List<Residents> listResidents;
    private ResidentView residentView;
    
    public ManagerResidents()
    {
        this.listResidents = readListResidents();
        if (listResidents == null) {
            listResidents = new ArrayList<Residents>();
        }
    }
    
    public List<Residents> readListResidents() 
    {
        List<Residents> list = new ArrayList<Residents>();
        ResidentXML residentXML = (ResidentXML) FileUtils.readXMLFile(
                RESIDENT_FILE_NAME, ResidentXML.class);
        if (residentXML != null) 
        {
            list = residentXML.getResidents();
        }
        return list;
    }
    
    public void writeListResidents(List<Residents> residents) 
    {
        ResidentXML residentXML = new ResidentXML();
        residentXML.setResidents(residents);
        FileUtils.writeXMLtoFile(RESIDENT_FILE_NAME, residentXML);
    }
    
    public List<Residents> searchResidentName(String search){
        List<Residents>temp = new ArrayList<Residents>();
        for(Residents person : listResidents){
            if(person.getName().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
    
    /* Hiển thị listSpecialPersons theo nơi ở */
    public List<Residents> searchResidentAddress(String search){
        List<Residents>temp = new ArrayList<Residents>();
        for(Residents person : listResidents){
            if(person.getAddress().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
    
    public List<Residents> searchResidentIDFamily(String search){
        List<Residents>temp = new ArrayList<Residents>();
        for(Residents person : listResidents){
            if(person.getIDFamily().contains(search)){
                temp.add(person);
            }
        }
        return temp;
    }
     /* Hiển thị listSpecialPersons theo năm sinh */
    public List<Residents> searchResidentYear(String year) {
        List<Residents> temp = new ArrayList<>();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (Residents person : listResidents) {
            // Chuyển đổi ngày sinh thành chuỗi năm
            String personYearStr = yearFormat.format(person.getBirthday());

            // So sánh chuỗi năm với năm tìm kiếm
            if (personYearStr.equals(year)) {
                temp.add(person);
            }
        }

        return temp;
    }
    
    public void add(Residents resident) 
    {
        int max = 0;
        for (int i=0;i<listResidents.size();i++)
        {
            if(listResidents.get(i).getId()>max) max=listResidents.get(i).getId();
        }
        resident.setId(max+1);
        listResidents.add(resident);
        writeListResidents(listResidents);
    }
    
    /*public boolean validateAdd(Residents resident) {
        try {
            // Kiểm tra số CMT không trùng
            if (!isCMTUnique(resident.getCMT())) {
                throw new IllegalArgumentException("Số CMT đã tồn tại");
            }

            // Kiểm tra vai trò "Chủ hộ" không trùng số hộ khẩu
            if ("Chủ hộ".equals(resident.getRole()) && !isHouseholdUnique(resident.getIDFamily())) {
                throw new IllegalArgumentException("Số hộ khẩu đã tồn tại cho vai trò 'Chủ hộ'");
            }

            return true; // Điều kiện kiểm tra thành công
        } catch (IllegalArgumentException ex) {
            // Bắt ngoại lệ và xử lý thông báo
            showMessage("Lỗi: " + ex.getMessage());
            return false; // Điều kiện kiểm tra không thành công
        }
    }*/
    
    // Hàm kiểm tra số CMT không trùng
    public boolean isCMTUnique(Residents resident) {
        String cmt=resident.getCMT();
        for (Residents existingResident : listResidents) {
            if (existingResident.getCMT().equals(cmt)) {
                return false; // Trùng số CMT
            }
        }
        return true; // Số CMT không trùng
    }

    // Hàm kiểm tra số hộ khẩu không trùng cho vai trò "Chủ hộ"
    public boolean isHouseholdUnique(Residents resident) {
        String IDFamily=resident.getIDFamily();
        String role = resident.getRole();
        for (Residents existingResident : listResidents) {
            if ("Chủ hộ".equals(role) && existingResident.getIDFamily().equals(IDFamily) && existingResident.getRole().equals(role)) {
                return false; // Trùng số hộ khẩu cho vai trò "Chủ hộ"
            }
        }
        return true; // Số hộ khẩu không trùng cho vai trò "Chủ hộ"
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(residentView, message);
    }
    
    public void edit(Residents resident) throws ParseException 
    {
        SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        int size = listResidents.size();
        for (int i = 0; i < size; i++) 
        {
            if (listResidents.get(i).getId() == resident.getId()) 
            {
                listResidents.get(i).setIDFamily(resident.getIDFamily());
                listResidents.get(i).setName(resident.getName());
                listResidents.get(i).setRole(resident.getRole());
                listResidents.get(i).setBirthday(resident.getBirthday());
                listResidents.get(i).setAddress(resident.getAddress());
                listResidents.get(i).setSex(resident.getSex());
                listResidents.get(i).setTypeCMT(resident.getTypeCMT());
                listResidents.get(i).setCMT(resident.getCMT());
                listResidents.get(i).setBirthPlace(resident.getBirthPlace());
                listResidents.get(i).setPhoneNumber(resident.getPhoneNumber());

                writeListResidents(listResidents);
                break;
            }
        }
    }
    
    public boolean delete(Residents resident) {
        boolean isFound = false;
        int size = listResidents.size();
        for (int i = 0; i < size; i++) {
            if (listResidents.get(i).getId() == resident.getId()) {
                listResidents.remove(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            // Cập nhật lại ID của các đối tượng sau
            for (int i = 0; i < listResidents.size(); i++) {
                if (listResidents.get(i).getId() > resident.getId()) {
                    listResidents.get(i).setId(listResidents.get(i).getId() - 1);
                }
            }
            writeListResidents(listResidents);
            return true;
        }
        return false;
    }
    
    public void sortResidentsByName() 
    {
        Collections.sort(listResidents, new Comparator<Residents>() {
            public int compare(Residents p1, Residents p2) {
                Collator collator = Collator.getInstance(new Locale("vi", "VN"));
                // So sánh tên
                int result = collator.compare(p1.getLastName(), p2.getLastName());
                if (result == 0) {
                    // Nếu tên bằng nhau, so sánh họ lót
                    result = collator.compare(p1.getFirstName(), p2.getFirstName());
                }
                return result;
            }
        });
        //Collections.sort(listSpecialPersons, (p1, p2) -> collator.compare(p1.getLastName(), p2.getLastName()));
    }
    
    public void sortResidentsByIDFamily() {
        Collections.sort(listResidents, new Comparator<Residents>() {
            public int compare(Residents person1, Residents person2) {
                return person1.getIDFamily().compareTo(person2.getIDFamily());
            }
        });
    }
    
    public void sortResidentsByID() 
    {
        Collections.sort(listResidents, new Comparator<Residents>() 
        {
            public int compare(Residents person1, Residents person2) 
            {
                if (person1.getId() > person2.getId()) 
                {
                    return 1;
                }
                return -1;
            }
        });
    }
    
    public List<Residents> getListResidents() 
    {
        return this.listResidents;
    }
}
