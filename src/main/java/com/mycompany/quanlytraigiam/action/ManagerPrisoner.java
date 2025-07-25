/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlytraigiam.action;

import com.mycompany.quanlytraigiam.entity.Prisoner;
import com.mycompany.quanlytraigiam.entity.PrisonerXML;
import com.mycompany.quanlytraigiam.utils.FileUtils;
import com.mycompany.quanlytraigiam.view.PrisonerView;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 *
 * @author PC
 */
public class ManagerPrisoner 
{
    private static final String PRISONER_FILE_NAME = "Prisoner.xml";
    private List<Prisoner> listPrisoners;
    public ManagerPrisoner() {
        this.listPrisoners = readListPrisoners();
        if (listPrisoners == null) {
            listPrisoners = new ArrayList<Prisoner>();
        }
    }

    /**
     * Lưu các đối tượng Prisoner vào file Prisoner.xml
     * 
     * @param prisoners
     */
    public void writeListPrisoners(List<Prisoner> prisoners) 
    {
        PrisonerXML prisonerXML = new PrisonerXML();
        prisonerXML.setPrisoner(prisoners);
        FileUtils.writeXMLtoFile(PRISONER_FILE_NAME, prisonerXML);
    }

    /**
     * Đọc các đối tượng Prisoner từ file Prisoner.xml
     * 
     * @return list Prisoner
     */
    public List<Prisoner> readListPrisoners() 
    {
        List<Prisoner> list = new ArrayList<Prisoner>();
        PrisonerXML prisonerXML = (PrisonerXML) FileUtils.readXMLFile(PRISONER_FILE_NAME, PrisonerXML.class);
        if (prisonerXML != null) 
        {
            list = prisonerXML.getPrisoner();
        }
        return list;
    }
    
    /* Hiển thị listPrisoners theo tên */
    public List<Prisoner> searchPrisonerName(String search){
        List<Prisoner>temp = new ArrayList<Prisoner>();
        for(Prisoner person : listPrisoners){
            if(person.getName().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
    
    /* Hiển thị listPrisoners theo nơi ở */
    public List<Prisoner> searchPrisonerAddress(String search){
        List<Prisoner>temp = new ArrayList<Prisoner>();
        for(Prisoner person : listPrisoners){
            if(person.getAddress().toLowerCase().contains(search.toLowerCase())){
                temp.add(person);
            }
        }
        return temp;
    }
     /* Hiển thị listPrisoners theo năm sinh */
    public List<Prisoner> searchPrisonerYear(String year) {
        List<Prisoner> temp = new ArrayList<>();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        for (Prisoner person : listPrisoners) {
            // Chuyển đổi ngày sinh thành chuỗi năm
            String personYearStr = yearFormat.format(person.getBirthday());

            // So sánh chuỗi năm với năm tìm kiếm
            if (personYearStr.equals(year)) {
                temp.add(person);
            }
        }

        return temp;
    }
    
    /**
     * thêm Prisoner vào listPrisoners và lưu listPrisoners vào file
     * 
     * @param Prisoner
     */
    public void add(Prisoner prisoner) 
    {
        int max = 0;
        for (int i=0;i<listPrisoners.size();i++)
        {
            if(listPrisoners.get(i).getId()>max) max=listPrisoners.get(i).getId();
        }
        prisoner.setId(max+1);
        listPrisoners.add(prisoner);
        writeListPrisoners(listPrisoners);
    }

    /**
     * cập nhật Prisoner vào listPrisoners và lưu listPrisoners vào file
     * 
     * @param Prisoner
     */
    public void edit(Prisoner prisoner) throws ParseException 
    {
        SimpleDateFormat fDate=new SimpleDateFormat("dd/MM/yyyy");
        int size = listPrisoners.size();
        for (int i = 0; i < size; i++) 
        {
            if (listPrisoners.get(i).getId() == prisoner.getId()) 
            {
                listPrisoners.get(i).setName(prisoner.getName());
                listPrisoners.get(i).setBirthday(prisoner.getBirthday());
                listPrisoners.get(i).setAddress(prisoner.getAddress());
                listPrisoners.get(i).setOpeningDate(prisoner.getOpeningDate());
                listPrisoners.get(i).setType(prisoner.getType());
                listPrisoners.get(i).setImage(prisoner.getImage());
                writeListPrisoners(listPrisoners);
                break;
            }
        }
    }

    /**
     * xóa Prisoner từ listPrisoners và lưu listPrisoners vào file
     * 
     * @param Prisoner
     */
    
    public void image(Prisoner prisoner) 
    {
        
    }
      
    public boolean delete(Prisoner prisoner) {
         boolean isFound = false;
        int size = listPrisoners.size();
        for (int i = 0; i < size; i++) 
        {
            if (listPrisoners.get(i).getId() == prisoner.getId()) 
            {
                prisoner = listPrisoners.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) 
        {
            listPrisoners.remove(prisoner);
            writeListPrisoners(listPrisoners);
            return true;
        }
        return false;
    }

    
    
    /**
     * sắp xếp danh sách Prisoner theo name theo tứ tự tăng dần
     */
    
    
    public void sortPrisonerByName() 
    {
        Collections.sort(listPrisoners, new Comparator<Prisoner>() {
            public int compare(Prisoner p1, Prisoner p2) {
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
        //Collections.sort(listPrisoners, (p1, p2) -> collator.compare(p1.getLastName(), p2.getLastName()));
    }
    
    public void sortPrisonerByID() 
    {
        Collections.sort(listPrisoners, new Comparator<Prisoner>() 
        {
            public int compare(Prisoner Prisoner1, Prisoner Prisoner2) 
            {
                if (Prisoner1.getId() > Prisoner2.getId()) 
                {
                    return 1;
                }
                return -1;
            }
        });
    }
    
    public void sortPrisonerByOpeningDate() 
    {
        Collections.sort(listPrisoners, new Comparator<Prisoner>() 
        {
            public int compare(Prisoner Prisoner1, Prisoner Prisoner2) 
            {
                return Prisoner1.getOpeningDate().compareTo(Prisoner2.getOpeningDate());
            }
        });
    }
    
    /**
     * sắp xếp danh sách Prisoner theo năm sinh theo tứ tự tăng dần
     */
    public void sortPrisonerByBirthDay() {
        Collections.sort(listPrisoners, new Comparator<Prisoner>() {
            public int compare(Prisoner person1, Prisoner person2) {
                return person1.getBirthday().compareTo(person2.getBirthday());
            }
        });
    }

    public List<Prisoner> getListPrisoners() 
    {
        return listPrisoners;
    }

    public void setListPrisoners(List<Prisoner> listPrisoners) 
    {
        this.listPrisoners = listPrisoners;
    }
}
