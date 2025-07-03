/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.PrisonXML;
import com.mycompany.quanlytraigiam.entity.Prison;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import com.mycompany.quanlytraigiam.view.PrisonView;
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
public class ManagerPrison 
{
    private static final String RESIDENT_FILE_NAME = "Residents.xml";
    private List<Prison> listResidents;
    private PrisonView prisonView;
    
    public ManagerPrison()
    {
        this.listResidents = readListResidents();
        if (listResidents == null) {
            listResidents = new ArrayList<Prison>();
        }
    }
    
    public List<Prison> readListResidents() 
    {
        List<Prison> list = new ArrayList<Prison>();
        PrisonXML prisonXML = (PrisonXML) FileUtils.readXMLFile(RESIDENT_FILE_NAME, PrisonXML.class);
        if (prisonXML != null) 
        {
            list = prisonXML.getResidents();
        }
        return list;
    }
    
    public void writeListResidents(List<Prison> prisons) 
    {
        PrisonXML prisonXML = new PrisonXML();
        prisonXML.setResidents(prisons);
        FileUtils.writeXMLtoFile(RESIDENT_FILE_NAME, prisonXML);
    }
    
    public List<Prison> searchResidentName(String search){
        List<Prison>temp = new ArrayList<Prison>();
        for(Prison person : listResidents){
            if(person.getName().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
    
    /* Hiển thị listSpecialPersons theo nơi ở */
    public List<Prison> searchResidentAddress(String search){
        List<Prison>temp = new ArrayList<Prison>();
        for(Prison person : listResidents){
            if(person.getAddress().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
    
    public List<Prison> searchResidentIDFamily(String search){
        List<Prison>temp = new ArrayList<Prison>();
        for(Prison person : listResidents){
            if(person.getIDFamily().contains(search)){
                temp.add(person);
            }
        }
        return temp;
    }
     /* Hiển thị listSpecialPersons theo năm sinh */
    public List<Prison> searchResidentYear(String year) {
        List<Prison> temp = new ArrayList<>();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (Prison person : listResidents) {
            // Chuyển đổi ngày sinh thành chuỗi năm
            String personYearStr = yearFormat.format(person.getBirthday());

            // So sánh chuỗi năm với năm tìm kiếm
            if (personYearStr.equals(year)) {
                temp.add(person);
            }
        }

        return temp;
    }
    
    public void add(Prison prison) 
    {
        int max = 0;
        for (int i=0;i<listResidents.size();i++)
        {
            if(listResidents.get(i).getId()>max) max=listResidents.get(i).getId();
        }
        prison.setId(max+1);
        listResidents.add(prison);
        writeListResidents(listResidents);
    }
    
    /*public boolean validateAdd(Residents prison) {
        try {
            // Kiểm tra số CMT không trùng
            if (!isCMTUnique(prison.getCMT())) {
                throw new IllegalArgumentException("Số CMT đã tồn tại");
            }

            // Kiểm tra vai trò "Chủ hộ" không trùng số hộ khẩu
            if ("Chủ hộ".equals(prison.getRole()) && !isHouseholdUnique(prison.getIDFamily())) {
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
    public boolean isCMTUnique(Prison prison) {
        String cmt=prison.getCMT();
        for (Prison existingResident : listResidents) {
            if (existingResident.getCMT().equals(cmt)) {
                return false; // Trùng số CMT
            }
        }
        return true; // Số CMT không trùng
    }

    // Hàm kiểm tra số hộ khẩu không trùng cho vai trò "Chủ hộ"
    public boolean isHouseholdUnique(Prison prison) {
        String IDFamily=prison.getIDFamily();
        String role = prison.getRole();
        for (Prison existingResident : listResidents) {
            if ("Chủ hộ".equals(role) && existingResident.getIDFamily().equals(IDFamily) && existingResident.getRole().equals(role)) {
                return false; // Trùng số hộ khẩu cho vai trò "Chủ hộ"
            }
        }
        return true; // Số hộ khẩu không trùng cho vai trò "Chủ hộ"
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(prisonView, message);
    }
    
    public void edit(Prison prison) throws ParseException 
    {
        SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        int size = listResidents.size();
        for (int i = 0; i < size; i++) 
        {
            if (listResidents.get(i).getId() == prison.getId()) 
            {
                listResidents.get(i).setIDFamily(prison.getIDFamily());
                listResidents.get(i).setName(prison.getName());
                listResidents.get(i).setRole(prison.getRole());
                listResidents.get(i).setBirthday(prison.getBirthday());
                listResidents.get(i).setAddress(prison.getAddress());
                listResidents.get(i).setSex(prison.getSex());
                listResidents.get(i).setTypeCMT(prison.getTypeCMT());
                listResidents.get(i).setCMT(prison.getCMT());
                listResidents.get(i).setBirthPlace(prison.getBirthPlace());
                listResidents.get(i).setPhoneNumber(prison.getPhoneNumber());

                writeListResidents(listResidents);
                break;
            }
        }
    }
    
    public boolean delete(Prison prison) {
        boolean isFound = false;
        int size = listResidents.size();
        for (int i = 0; i < size; i++) {
            if (listResidents.get(i).getId() == prison.getId()) {
                listResidents.remove(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            // Cập nhật lại ID của các đối tượng sau
            for (int i = 0; i < listResidents.size(); i++) {
                if (listResidents.get(i).getId() > prison.getId()) {
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
        Collections.sort(listResidents, new Comparator<Prison>() {
            public int compare(Prison p1, Prison p2) {
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
        Collections.sort(listResidents, new Comparator<Prison>() {
            public int compare(Prison person1, Prison person2) {
                return person1.getIDFamily().compareTo(person2.getIDFamily());
            }
        });
    }
    
    public void sortResidentsByID() 
    {
        Collections.sort(listResidents, new Comparator<Prison>() 
        {
            public int compare(Prison person1, Prison person2) 
            {
                if (person1.getId() > person2.getId()) 
                {
                    return 1;
                }
                return -1;
            }
        });
    }
    
    public List<Prison> getListResidents() 
    {
        return this.listResidents;
    }
}
