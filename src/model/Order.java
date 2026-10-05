package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Lop mo ta thong tin don hang dat tiec duoc tao boi khach hang.
 * Ma don hang la duy nhat dua vao Time stamp [yyyyMMddHHmmss].
 */
public class Order implements Serializable {
    
    private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTables;
    private Date eventDate;
    private double price;

    /**
     * Tao ma don hang duy nhat dua vao Time stamp [epoch time]
     */
    private String generateOrderCode() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(now);
    }

    public Order() {
        this.orderCode = generateOrderCode();
        this.customerId = "";
        this.menuId = "";
        this.eventDate = new Date();
    }

    public Order(String customerId, String menuId, int numOfTables, Date eventDate, double price) {
        this.orderCode = generateOrderCode();
        this.customerId = customerId;
        this.menuId = menuId;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
        this.price = price;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalCost() {
        return price * numOfTables;
    }

    /**
     * Mot don hang duoc xem la trung khi CustomerId - MenuId - EventDate
     * dong thoi giong nhau (dung cho Orders extends HashSet).
     */
    // muc dich laf tao cho moi object 1 ma rieng de tim kiem nhanh hon
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.customerId);
        hash = 47 * hash + Objects.hashCode(this.menuId);
        hash = 47 * hash + Objects.hashCode(this.eventDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        if (!Objects.equals(this.menuId, other.menuId)) {
            return false;
        }
        return Objects.equals(this.eventDate, other.eventDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFomat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-8s| %-11s| %-11s| %-8s| %,10.0f| %6d| %,12.0f",
                orderCode, dateFomat.format(eventDate), customerId, menuId, price, numOfTables, getTotalCost());
    }
}
