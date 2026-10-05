package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Customer;
import tools.FileUtils;

public class Customers extends ArrayList<Customer> implements Workable<Customer> {

    /**
     * Khai báo mã phiên bản của class để Java kiểm tra tính tương thích khi
     * deserialize object đã được lưu trong file .dat.
     */
    private static final long serialVersionUID = 1L;

    public final String TABLE_HEADER
            = "--------------------------------------------------------------------\n"
            + "Code  | Customer Name            | Phone       | Email                    \n"
            + "--------------------------------------------------------------------";
    private final String TABLE_FOOTER
            = "--------------------------------------------------------------------";

    private String pathFile;
    private boolean saved;

    public Customers() {
        super();
        this.pathFile = "customers.dat";
        this.saved = false;
    }

   

    public boolean isSaved() {
        return saved;
    }

    @Override
    public void addNew(Customer x) {
        if (!this.isExist(x.getId())) {
            this.add(x);
            this.saved = false;
        }

    }

    @Override
    public void update(Customer x) {

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equalsIgnoreCase(x.getId())) {
                this.set(i, x);
                this.saved = false;
                return;
            }
        }
    }

    @Override
    public Customer searchById(String id) {
        for (Customer c : this) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public boolean isExist(String id) {
        return searchById(id) != null;
    }

    /**
     * Loc danh sach khach hang theo ten (hoac mot phan cua ten), khong phan
     * biet hoa/thuong, sap xep theo alphabet
     */
    public List<Customer> filterByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Hien thi toan bo danh sach khach hang co trong he thong (sap xep theo
     * ten)
     */
    @Override
    public void showAll() {

        List<Customer> result = new ArrayList<>();
        for (Customer c : this) {
            result.add(c);
        }

        showAll(result);

    }

    /**
     * Hien thi danh sach khach hang theo yeu cau (Overloading)
     *
     * @param l Chua danh sach khach hang mong muon hien thi thong tin
     */
    public void showAll(List<Customer> list) {
        if (list.isEmpty()) {
            System.out.println("Does not have any customer information.");
            return;
        }
        Collections.sort(list, new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

        });
        System.out.println("Customers information:");
        System.out.println(TABLE_HEADER);
        for (Customer i : list) {
            System.out.println(i);
        }
        System.out.println(TABLE_FOOTER);
    }

    public void readFromFile() {
        this.clear();
        this.addAll(FileUtils.readFile(this.pathFile));
        this.saved = true;
    }

    public void saveToFile() {
        FileUtils.saveToFile(this, this.pathFile);
        this.saved = true;
        System.out.println("Customer data has been successfully saved to \"" + pathFile + "\".");
    }
}
