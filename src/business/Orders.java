package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import model.Order;
import tools.FileUtils;

/**
 * Lop quan ly danh sach cac don hang dat tiec cua khach hang. Thua ke tu
 * HashSet collection (khong chua phan tu trung) va trien khai Workable
 * interface. Tinh duy nhat cua don hang duoc quyet dinh boi equals()/hashCode()
 * cua Order class (CustomerId + MenuId + EventDate).
 */
public class Orders extends HashSet<Order> implements Workable<Order> {

    private static final long serialVersionUID = 1L;

    private final String TABLE_HEADER
            = "-------------------------------------------------------------------------\n"
            + String.format("%-14s| %-11s| %-11s| %-8s| %10s| %6s| %12s\n",
                    "ID", "Event date", "CustomerID", "SetMenu", "Price", "Tables", "Cost")
            + "-------------------------------------------------------------------------";
    private final String TABLE_FOOTER
            = "-------------------------------------------------------------------------";

    private String pathFile;
    private boolean saved;

    public Orders() {
        super();
        this.pathFile = "feast_order_service.dat";
        this.saved = false;
    }

    public boolean isSaved() {
        return saved;
    }

    /**
     * Kiem tra trung don hang. Neu 2 don hang co 3 thuoc tinh giong nhau
     * (Customer ID - SetMenu ID - Event date) la trung
     *
     * @param x Order object
     * @return true: trung don hang
     */
    public boolean isDuplicate(Order x) {
        return this.contains(x);
    }

    /**
     * Them mot don hang moi vao danh sach don hang hien co neu khong bi trung
     */
    @Override
    public void addNew(Order x) {
        if (!this.isDuplicate(x)) {
            this.add(x);
            this.saved = false;
        }
    }



    @Override
    public void update(Order x) {
        Order old = searchById(x.getOrderCode());
        if (old != null) {
            this.remove(old);
            this.add(x);
            this.saved = false;
        }
    }

    @Override
    public Order searchById(String orderCode) {
        for (Order o : this) {
            if (o.getOrderCode().equalsIgnoreCase(orderCode)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void showAll() {
        showAll(this.toList());
    }

    /**
     * Hien thi danh sach don hang duoc yeu cau, sap xep theo ngay to chuc su
     * kien tang dan (Overloading)
     */
    public void showAll(Collection<Order> l) {
        if (l.isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        List<Order> sorted = new ArrayList<>(l);
        Collections.sort(sorted, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getEventDate().compareTo(o2.getEventDate());
            }
        });

        System.out.println("Orders information:");
        System.out.println(TABLE_HEADER);
        for (Order i : sorted) {
            System.out.println(i);
        }
        System.out.println(TABLE_FOOTER);
    }

    /**
     * Phuong thuc cho phep chuyen doi tu HashSet thanh List collection
     */
    private List<Order> toList() {
        return new ArrayList<>(this);
    }

    public void readFromFile() {
        this.clear();
        List<Order> l = FileUtils.readFile(this.pathFile);
        for (Order i : l) {
            this.add(i);
        }
        this.saved = true;
    }

    public void saveToFile() {
        FileUtils.saveToFile(toList(), pathFile);
        this.saved = true;
        System.out.println("Order data has been successfully saved to \"" + pathFile + "\".");
    }
    public List<Order> fillOrderPriceThan(int price){
        List<Order> listOrder = new ArrayList<>();
        for(Order order : this){
            if(order.getTotalCost() > price){
                listOrder.add(order);
            }
        }
        return listOrder;
    }
}
