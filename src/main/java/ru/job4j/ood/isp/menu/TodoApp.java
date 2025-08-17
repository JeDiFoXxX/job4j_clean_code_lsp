package ru.job4j.ood.isp.menu;

import static ru.job4j.ood.isp.menu.Menu.ROOT;

public class TodoApp {
    private static final ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some action");

    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        menu.add(ROOT, "Сходить в аптеку", DEFAULT_ACTION);
        menu.add("Сходить в аптеку", "Купить лекарство", DEFAULT_ACTION);
        menu.add("Купить лекарство", "Купить Аспирин", DEFAULT_ACTION);
        menu.add(ROOT, "Убраться Дома", DEFAULT_ACTION);
        menu.add("Убраться дома", "Помыть посуду", DEFAULT_ACTION);

        Printer printer = new Printer();
        printer.print(menu);
    }
}
