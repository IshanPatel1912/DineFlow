package service;

import dao.MenuDAO;
import model.MenuItem;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MenuService {

    private MenuDAO menuDAO;

    public MenuService() {
        this.menuDAO = new MenuDAO();
    }

    public boolean createMenuItem(String name, String description, double price, String category) {
        if (name == null || name.trim().isEmpty() || price < 0) {
            return false;
        }
        MenuItem item = new MenuItem(0, name, description, price, category, true);
        return menuDAO.addMenuItem(item);
    }

    public List<MenuItem> viewMenu() {
        return menuDAO.getAllMenuItems();
    }
    
    public Map<Integer, MenuItem> getMenuAsMap() {
        List<MenuItem> list = viewMenu();
        Map<Integer, MenuItem> map = new HashMap<>();
        for (MenuItem item : list) {
            map.put(item.getItemId(), item);
        }
        return map;
    }

    public boolean editMenuItem(int itemId, String name, String description, double price, String category, boolean isAvailable) {
        if (itemId <= 0 || name == null || name.trim().isEmpty() || price < 0) {
            return false;
        }
        MenuItem item = new MenuItem(itemId, name, description, price, category, isAvailable);
        return menuDAO.updateMenuItem(item);
    }

    public boolean removeMenuItem(int itemId) {
        if (itemId <= 0) {
            return false;
        }
        return menuDAO.deleteMenuItem(itemId);
    }
}