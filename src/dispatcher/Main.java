package dispatcher;

import business.Customers;
import business.Orders;
import business.SetMenus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Customer;
import model.Order;
import model.SetMenu;
import tools.Acceptable;
import tools.Inputter;

public class Main {

    private static final Inputter ndl = new Inputter();
    private static final Customers customers = new Customers();
    private static final Orders orders = new Orders();
    private static final SetMenus setMenus = new SetMenus();

    public static void main(String[] args) {
        // Nap du lieu da co san khi khoi dong chuong trinh (neu co)
        customers.readFromFile();
        orders.readFromFile();
        setMenus.readFromFile();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = ndl.getInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    searchCustomerByName();
                    break;
                case 4:
                    displayFeastMenus();
                    break;
                case 5:
                    placeFeastOrder();
                    break;
                case 6:
                    updateOrder();
                    break;
                case 7:
                    saveDataToFile();
                    break;
                case 8:
                    displayLists();
                    break;
                case 9:
                    displayListOrdersHavePriceThan();
                    break;
                case 0:
                    saveDataToFile();
                    running = false;
                    System.out.println("Goodbye !");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=========== TRADITIONAL FEAST ORDER MANAGEMENT ===========");
        System.out.println("1. Register customers");
        System.out.println("2. Update customer information");
        System.out.println("3. Search for customer information by name");
        System.out.println("4. Display feast menus");
        System.out.println("5. Place a feast order");
        System.out.println("6. Update order information");
        System.out.println("7. Save data to file");
        System.out.println("8. Display Customer or Order lists");
        System.out.println("9. Display list orders have price than");
        System.out.println("0. Quit");
        System.out.println("============================================================");
    }

    // ---------------------------------------------------------------
    // Function 1: Register customers
    // ---------------------------------------------------------------
    private static void registerCustomer() {
        boolean isContinue = true;
        while (isContinue) {
            String id;
            while (true) {
                id = ndl.inputAndLoop("Customer code (C/G/K + 4 digits): ", Acceptable.CUS_ID_VALID, true);
                id = id.toUpperCase();
                if (customers.isExist(id)) {
                    System.out.println("This customer code already exists !. Please enter another one.");
                    continue;
                }
                break;
            }
            String name = ndl.inputAndLoop("Customer name [2-25 characters]: ", Acceptable.NAME_VALID, true);
            name = ndl.getToTitleCase(name);
            String phone;
            while (true) {
                phone = ndl.inputAndLoop("Phone number [10 digits]: ", Acceptable.PHONE_VALID, true);
                if (!Acceptable.isValidVNPhone(phone)) {
                    System.out.println("Phone number does not belong to a valid Vietnamese network operator !.");
                    continue;
                }
                break;
            }
            String email = ndl.inputAndLoop("Email address: ", Acceptable.EMAIL_VALID, true);

            Customer c = new Customer(id, name, phone, email);
            customers.addNew(c);
            System.out.println("Customer \"" + name + "\" has been registered successfully.");

            String ans = ndl.getString("Continue entering new customer? (Y/N): ");

            if (ans.equalsIgnoreCase("Y")) {
                isContinue = true;
            } else {
                customers.saveToFile();
                isContinue = false;
            }
        }
    }

    // ---------------------------------------------------------------
    // Function 2: Update customer information
    // ---------------------------------------------------------------
    private static void updateCustomer() {
        boolean isContinue = true;
        while (isContinue) {
            String id = ndl.getString("Enter Customer Code to update: ");
            Customer old = customers.searchById(id);
            if (old == null) {
                System.out.println("This customer does not exist.");
            } else {
                String name = ndl.getOptionalString("New name [Enter to keep \"" + old.getName() + "\"]: ");
                if (!name.isEmpty()) {
                    if (Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                        old.setName(name);
                    } else {
                        System.out.println("Invalid name, keeping old value.");
                    }
                }
                String phone = ndl.getOptionalString("New phone [Enter to keep \"" + old.getPhone() + "\"]: ");
                if (!phone.isEmpty()) {
                    if (Acceptable.isValidVNPhone(phone)) {
                        old.setPhone(phone);
                    } else {
                        System.out.println("Invalid phone, keeping old value.");
                    }
                }
                String email = ndl.getOptionalString("New email [Enter to keep \"" + old.getEmail() + "\"]: ");
                if (!email.isEmpty()) {
                    if (Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                        old.setEmail(email);
                    } else {
                        System.out.println("Invalid email, keeping old value.");
                    }
                }
                customers.update(old);
                System.out.println("Customer information updated successfully.");
                System.out.println(old);
                System.out.println("\n");
            }
            String ans = ndl.getString("Continue updating another customer? (Y/N): ");

            if (ans.equalsIgnoreCase("Y")) {
                isContinue = true;
            } else {
                customers.saveToFile();
                isContinue = false;
            }

        }
    }

    // ---------------------------------------------------------------
    // Function 3: Search for customer information by name
    // ---------------------------------------------------------------
    private static void searchCustomerByName() {
        String name = ndl.getString("Enter customer name [or part of name] to search: ");
        List<Customer> result = customers.filterByName(name);
        System.out.println("Matching Customers: " + name);
        if (result.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            customers.showAll(result);
        }
    }

    // ---------------------------------------------------------------
    // Function 4: Display feast menus
    // ---------------------------------------------------------------
    private static void displayFeastMenus() {
        setMenus.showMenuList();
    }

    // ---------------------------------------------------------------
    // Function 5: Place a feast order
    // ---------------------------------------------------------------
    private static void placeFeastOrder() {
        boolean more = true;
        while (more) {
            String custId;
            while (true) {
                custId = ndl.inputAndLoop("Customer code: ", Acceptable.CUS_ID_VALID, true);
                custId = custId.toUpperCase();
                if (customers.searchById(custId) == null) {
                    System.out.println("This customer does not exist. Please register first.");
                    continue;
                }
                break;
            }
            String menuId;
            while (true) {
                menuId = ndl.getString("Set Menu code: ").trim();
                menuId = menuId.toUpperCase();
                if (menuId.isEmpty() || !setMenus.isValidMenuID(menuId)) {
                    System.out.println("This Set Menu code is invalid or not in the list.");
                    continue;
                }
                break;
            }

            int tables;
            while (true) {
                String temp = ndl.getString("Number of tables: ");
                if (!Acceptable.isValid(temp, Acceptable.POSITIVE_INT_VALID)) {
                    System.out.println("Number of tables must be an integer greater than 0.");
                    continue;
                }
                tables = Integer.parseInt(temp);
                break;
            }
            Date eventDate = ndl.getDate("Preferred event date [dd/MM/yyyy]: ", true);

            Customer cust = customers.searchById(custId);
            SetMenu menu = setMenus.get(menuId);
            Order order = new Order(custId, menuId, tables, eventDate, menu.getPrice());

            if (orders.isDuplicate(order)) {
                System.out.println("Dupplicate data!");
            } else {
                orders.addNew(order);
                printOrderDetail(order, cust, menu);
            }

            String ans = ndl.getString("Continue placing another order? (Y/N): ");

            if (ans.equalsIgnoreCase("Y")) {
                more = true;
            } else {
                orders.saveToFile();
                more = false;
            }
        }
    }

    private static void printOrderDetail(Order order, Customer cust, SetMenu menu) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(
                "----------------------------------------------------------------\n"
                + "Customer order information [Order ID: " + order.getOrderCode() + "]\n"
                + "----------------------------------------------------------------\n"
                + "Code           : " + cust.getId() + "\n"
                + "Customer name  : " + cust.getName() + "\n"
                + "Phone number   : " + cust.getPhone() + "\n"
                + "Email          : " + cust.getEmail() + "\n"
                + "----------------------------------------------------------------\n"
                + "Code of Set Menu: " + menu.getMenuId() + "\n"
                + "Set menu name  : " + menu.getMenuName() + "\n"
                + "Event date     : " + df.format(order.getEventDate()) + "\n"
                + "Number of tables: " + order.getNumOfTables() + "\n"
                + String.format("Price          : %,.0f Vnd%n", menu.getPrice())
                + "Ingredients:\n"
        );
        for (String part : menu.getIngredients().split(";")) {
            System.out.println("+ " + part.trim());
        }
        System.out.println("----------------------------------------------------------------");
        System.out.printf("Total cost     : %,.0f Vnd%n", order.getTotalCost());
        System.out.println("----------------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // Function 6: Update order information
    // ---------------------------------------------------------------
    private static void updateOrder() {
        boolean more = true;
        while (more) {
            String orderId = ndl.getString("Enter Order ID to update: ");
            Order o = orders.searchById(orderId);
            if (o == null) {
                System.out.println("This Order does not exist.");
            } else if (o.getEventDate().before(new Date())) {
                System.out.println("Cannot update an order whose event date has already occurred.");
            } else {
                String menuId = ndl.getOptionalString("New Set Menu code [Enter to keep \"" + o.getMenuId() + "\"]: ");
                if (!menuId.isEmpty()) {
                    if (setMenus.isValidMenuID(menuId)) {
                        o.setMenuId(menuId);
                        o.setPrice(setMenus.get(menuId).getPrice());
                    } else {
                        System.out.println("Invalid Set Menu code, keeping old value.");
                    }
                }
                String tablesStr = ndl.getOptionalString("New number of tables [Enter to keep " + o.getNumOfTables() + "]: ");
                if (!tablesStr.isEmpty()) {
                    if (Acceptable.isValid(tablesStr, Acceptable.POSITIVE_INT_VALID)) {
                        o.setNumOfTables(Integer.parseInt(tablesStr));
                    } else {
                        System.out.println("Invalid number of tables, keeping old value.");
                    }
                }
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String dateStr = ndl.getOptionalString("New event date [Enter to keep " + df.format(o.getEventDate()) + "]: ");
                if (!dateStr.isEmpty()) {
                    if (Acceptable.isValid(dateStr, Acceptable.DATE_VALID)) {
                        try {
                            Date d = df.parse(dateStr);
                            if (d.after(new Date())) {
                                o.setEventDate(d);
                            } else {
                                System.out.println("Event date must be in the future, keeping old value.");
                            }
                        } catch (Exception ex) {
                            System.out.println("Invalid date, keeping old value.");
                        }
                    } else {
                        System.out.println("Invalid date format, keeping old value.");
                    }
                }
                orders.update(o);
                System.out.println("Order information updated successfully.");
            }
            String ans = ndl.getString("Continue updating another order? (Y/N): ");

            if (ans.equalsIgnoreCase("Y")) {
                more = true;
            } else {
                if (!orders.isSaved()) {
                    orders.saveToFile();
                }
                more = false;
            }
        }
    }

    // ---------------------------------------------------------------
    // Function 7: Save data to file
    // ---------------------------------------------------------------
    private static void saveDataToFile() {
        customers.saveToFile();
        orders.saveToFile();
    }

    // ---------------------------------------------------------------
    // Function 8: Display Customer or Order lists
    // ---------------------------------------------------------------
    private static void displayLists() {
        System.out.println("1. Display Customer list");
        System.out.println("2. Display Order list");
        int c = ndl.getInt("Enter your choice: ");
        if (c == 1) {
            customers.showAll();
        } else if (c == 2) {
            orders.showAll();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void displayListOrdersHavePriceThan() {
        boolean isContinue = true;
        while (isContinue) {
            int price = ndl.inputIntPrice("Price: ");
            List<Order> listOrders = orders.fillOrderPriceThan(price);
            if (listOrders.isEmpty()) {
                System.out.printf("No orders have total price than: %d" , price);
            } else {
                orders.showAll(listOrders);
            }
            String ans = ndl.getString("Continue processing (Y/N): ");
            isContinue = ans.equalsIgnoreCase("Y");
        }
    }

}
