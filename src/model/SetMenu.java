package model;

import java.io.Serializable;

/**
 * Lop mo ta thong tin mot Set Menu (thuc don dat tiec), du lieu duoc
 * cung cap tu file feastMenu.csv.
 */
public class SetMenu implements Serializable {

    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;

    public SetMenu() {
    }

    public SetMenu(String menuId, String menuName, double price, String ingredients) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------\n");
        sb.append("Code     :").append(menuId).append("\n");
        sb.append("Name     :").append(menuName).append("\n");
        sb.append(String.format("Price    : %,.0f Vnd%n", price));
        sb.append("Ingredients:\n");
        if (ingredients != null) {
            for (String part : ingredients.split(";")) {
                sb.append("+ ").append(part.trim()).append("\n");
            }
        }
        return sb.toString();
    }
}
