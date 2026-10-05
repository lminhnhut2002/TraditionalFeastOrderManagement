package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Class phuc vu cho viec nhap va kiem tra du lieu tu ban phim. Ket hop voi
 * Acceptable de dam bao "low coupling - high cohesion".
 */
public class Inputter {

    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    /**
     * Nhap du lieu chuoi truc tiep tu ban phim boi nguoi su dung
     */
    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine();
    }

    /**
     * Nhap du lieu la so nguyen tu ban phim boi nguoi su dung
     */
    public int getInt(String mess) {
        int result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
            result = Integer.parseInt(temp);
        }
        return result;
    }

    /**
     * Nhap du lieu la so double tu ban phim boi nguoi su dung
     */
    public double getDouble(String mess) {
        double result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
            result = Double.parseDouble(temp);
        }
        return result;
    }

    /**
     * Phuong thuc cho phep nhap va kiem tra du lieu, nhap lai neu "khong khop"
     *
     * @param mess Thong bao huong dan nhap du lieu
     * @param pattern Dieu kien kiem tra du lieu
     * @param isLoop Yeu cau lap de nhap du lieu cho toi khi dung
     * @return String da hop le
     */
    public String inputAndLoop(String mess, String pattern, boolean isLoop) {
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if (more && (isLoop && result.length() > 0)) {
                System.out.println("Data is invalid !. Re-enter ...");
            }
        } while (isLoop && more);
        return result.trim();
    }

    /**
     * Nhap va kiem tra ngay thang (dd/MM/yyyy), lap lai neu khong hop le.
     *
     * @param mess Thong bao huong dan
     * @param futureOnly Yeu cau ngay phai o tuong lai
     */
    public Date getDate(String mess, boolean futureOnly) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        while (true) {
            String temp = getString(mess);
            if (!Acceptable.isValid(temp, Acceptable.DATE_VALID)) {
                System.out.println("Date format is invalid !. Re-enter (dd/MM/yyyy) ...");
                continue;
            }
            try {
                Date d = sdf.parse(temp);
                if (futureOnly && !d.after(new Date())) {
                    System.out.println("Event date must be in the future !. Re-enter ...");
                    continue;
                }
                return d;
            } catch (ParseException ex) {
                System.out.println("Date is invalid !. Re-enter ...");
            }
        }
    }

    /**
     * Cho phep nguoi dung nhan Enter de bo qua (giu gia tri cu khi update)
     */
    public String getOptionalString(String mess) {
        System.out.print(mess);
        return ndl.nextLine().trim();
    }

    public String getToTitleCase(String string) {
        String[] words = string.trim().split("\\s+");
        String result = "";
        for (String word : words) {
            result += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
        }
        return result.trim();
    }

    public int inputIntPrice(String mess) {
        while (true) {
            String tempPrice = getString(mess);
            tempPrice = tempPrice.replace(",", "");

            if (Acceptable.isValid(tempPrice, Acceptable.INTEGER_VALID)) {
                return Integer.parseInt(tempPrice);
            } else {
                System.out.println("Please try again.");
            }
        }

    }

}
