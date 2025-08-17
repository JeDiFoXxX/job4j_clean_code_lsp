package ru.job4j.ood.isp.menu;

import ru.job4j.ood.isp.menu.Menu.MenuItemInfo;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        StringBuilder builder = new StringBuilder();
        for (MenuItemInfo menuItemInfo : menu) {
            String number = menuItemInfo.getNumber();
            String[] split = number.split("\\.");
            builder.append("----".repeat(split.length - 1))
                    .append(number)
                    .append(menuItemInfo.getName())
                    .append(System.lineSeparator());
        }
        System.out.print(builder);
    }
}
