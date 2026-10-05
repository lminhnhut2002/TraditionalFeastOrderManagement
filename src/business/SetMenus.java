package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SetMenu;

/**
 * Lop quan ly danh sach cac Set Menu duoc cung cap cho dich vu dat tiec, du
 * lieu duoc doc tu file feastMenu.csv. Su dung quan he "is A" voi
 * HashMap<String, SetMenu> (Key = menuId).
 */
public class SetMenus extends HashMap<String, SetMenu> {

    private static final long serialVersionUID = 1L;

    private String pathFile;

    public SetMenus() {
        super();
        this.pathFile = "feastMenu.csv";
    }

    public SetMenus(String pathFile) {
        super();
        this.pathFile = pathFile;
    }

    public boolean isValidMenuID(String menuId) {
        return this.containsKey(menuId);
    }
    
    

    /**
     * Phuong thuc co nhiem vu tach cac thanh phan co trong chuoi text doc duoc
     * tu file de chuyen thanh cac thuoc tinh tuong ung cua SetMenu object. Dinh
     * dang moi dong: Code,Name,Price,Ingredients (Ingredients cac mon cach nhau
     * boi dau cham phay ";")
     */
    public SetMenu dataToObject(String text) {
        String[] parts = text.split(",", 4);
        if (parts.length < 4) {
            return null;
        }
        try {
            String code = parts[0].trim();
            String name = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim().replace(",", ""));
            String ingredients = parts[3].trim();
            return new SetMenu(code, name, price, ingredients);
        } catch (NumberFormatException ex) {
            // Dong header hoac du lieu khong hop le -> bo qua
            return null;
        }
    }
    
   

    /**
     * Read SetMenu list from feastMenu.csv file
     */
    public void readFromFile() {
        // xoa du cu
        this.clear();
        File f = new File(this.pathFile);
        if (!f.exists()) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }
        try ( FileReader fr = new FileReader(f);  BufferedReader br = new BufferedReader(fr)) {
            String temp;
            boolean firstLine = true;
            while ((temp = br.readLine()) != null) {
                if (temp.trim().isEmpty()) {
                    continue;
                }
                SetMenu i = dataToObject(temp);
                if (i != null) {
                    this.put(i.getMenuId(), i);
                } else if (firstLine) {
                    // Bo qua dong header cua file CSV
                }
                firstLine = false;
            }
        } catch (IOException ex) {
            Logger.getLogger(SetMenus.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        }
    }

    /**
     * Hien thi danh sach set menu, sap xep tang dan theo gia (price)
     */
    public void showMenuList() {
        if (this.isEmpty()) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }
        List<SetMenu> list = new ArrayList<>(this.values());
       
        Collections.sort(list, new Comparator<SetMenu>() {
            @Override
            public int compare(SetMenu o1, SetMenu o2) {
                return  Double.compare(o1.getPrice(), o2.getPrice());
            }

        });
//        list.sort(Comparator.comparingDouble(SetMenu::getPrice));
        System.out.println("-----------------------------------------------------");
        System.out.println(" List of Set Menus for ordering party:");
        for (SetMenu m : list) {
            System.out.println(m);
        }
    }
    
   
}
