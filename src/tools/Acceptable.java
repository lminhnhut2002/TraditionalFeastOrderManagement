package tools;

/**
 * Phuc vu cho viec validation du lieu theo yeu cau dua tren regular expression.
 * Interface chua cac hang so (mau du lieu) va static method isValid.
 */
public interface Acceptable {

    // Ma khach hang: bat dau boi C/G/K (hoa hoac thuong), theo sau la 4 chu so
    public final String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";

    // Ten: khong rong, tu 2 den 25 ky tu
    public final String NAME_VALID = "^.{2,25}$";

    // So dien thoai Viet Nam: 10 chu so, bat dau bang 0
    public final String PHONE_VALID = "^0\\d{9}$";

    // So dien thoai theo mang Viettel: 032-039, 086, 096-098
    public final String VIETTEL_VALID = "^(03[2-9]|086|09[6-8])\\d{7}$";

    // So dien thoai theo mang VNPT (Vinaphone): 081-085, 088, 091, 094
    public final String VNPT_VALID = "^(08[1-5]|088|091|094)\\d{7}$";

    // So dien thoai theo mang MobiFone: 070-079, 089, 090, 093
public final String MOBIFONE_VALID = "^(07[0-9]|089|090|093)\\d{7}$";
    
    // So nguyen bat ky
    public final String INTEGER_VALID = "^-?\\d+$";

    // So nguyen duong
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*$";

    // So thuc bat ky
    public final String DOUBLE_VALID = "^-?\\d+(\\.\\d+)?$";

    // So thuc duong
    public final String POSITIVE_DOUBLE_VALID = "^[0-9]*[1-9][0-9]*(\\.\\d+)?$|^0\\.\\d*[1-9]\\d*$";

    // Ma Set Menu: 5 ky tu chu + so, vi du PW003
    public final String MENU_ID_VALID = "^[A-Za-z]{2}\\d{3}$";

    // Email dung dinh dang chuan
    public final String EMAIL_VALID = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,}$";

    // Ngay thang dinh dang dd/MM/yyyy
    public final String DATE_VALID = "^\\d{2}/\\d{2}/\\d{4}$";

    /**
     * Kiem tra du lieu co trong data co phu hop voi mau pattern theo yeu cau khong
     * @param data Du lieu can kiem tra
     * @param pattern Mau du lieu duoc xem nhu dieu kien bat buoc
     * @return true is valid, false is invalid
     */
    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }

    /**
     * Kiem tra so dien thoai co thuoc mot nha mang Viet Nam hop le hay khong
     * (Viettel hoac VNPT), ket hop voi dieu kien 10 chu so
     */
    public static boolean isValidVNPhone(String phone) {
        if (!isValid(phone, PHONE_VALID)) {
            return false;
        }
        return isValid(phone, VIETTEL_VALID) || isValid(phone, VNPT_VALID) || isValid(phone, MOBIFONE_VALID);
    }
}
