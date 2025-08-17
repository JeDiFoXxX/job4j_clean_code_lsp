package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {
    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenMenuHasItemsThenPrintOutputsCorrectly() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);

        String expected = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("1.Сходить в магазин")
                .add("----1.1.Купить продукты")
                .add("2.Покормить собаку")
                .toString();

        Printer printer = new Printer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        printer.print(menu);
        String text = outputStream.toString();
        System.setOut(System.out);

        assertThat(expected).isEqualTo(text);
    }
}