package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class chuyen thuc hien doc/ghi du lieu duoi dang Object file, dung ky
 * thuat "generic methods" de dung chung cho nhieu loai du lieu khac nhau
 * (Customer, Order, ...).
 */
public class FileUtils {

    /**
     * Phuong thuc cho phep doc du lieu co trong tap tin yeu cau, tra ve
     * duoi dang List<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> readFile(String filePath) {
        List<T> result = new ArrayList<>();
        FileInputStream fis = null;
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                System.out.println("File not found !.\"" + filePath + "\"");
                return result;
            }
            fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                T x = (T) ois.readObject();
                result.add(x);
            }
            ois.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    /**
     * Phuong thuc cho phep ghi du lieu vao object file
     */
    public static <T> void saveToFile(List<T> li, String filePath) {
        FileOutputStream fos = null;
        try {
            File f = new File(filePath);
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (T i : li) {
                oos.writeObject(i);
            }
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
